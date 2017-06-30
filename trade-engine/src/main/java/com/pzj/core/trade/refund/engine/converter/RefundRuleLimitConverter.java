package com.pzj.core.trade.refund.engine.converter;

import static com.pzj.core.product.common.global.ProductRuleGroupEnum.PRODUCT_RULE_REFUND;
import static com.pzj.core.product.common.global.ProductRuleGroupEnum.PRODUCT_RULE_REFUND_REVIEW;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.product.common.global.ProductRuleGroupEnum;
import com.pzj.core.product.common.global.ProductRuleItem.RefundReviewTypeEnum;
import com.pzj.core.product.common.global.ProductRuleItem.RefundRuleEnum;
import com.pzj.core.product.common.model.response.ProductRuleOutModel;
import com.pzj.core.product.common.service.IProductRuleService;
import com.pzj.core.sku.common.constants.SkuProductGlobal.RefundAllowedTypeEnum;
import com.pzj.core.sku.common.constants.SkuProductGlobal.RefundAnyTimeTypeEnum;
import com.pzj.core.trade.order.engine.RuleReader;
import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.common.RefundFlowAuditStateEnum;
import com.pzj.core.trade.refund.engine.common.RefundInitPartyEnum;
import com.pzj.core.trade.refund.engine.exception.QueryRefundRuleException;
import com.pzj.core.trade.refund.engine.exception.RefundMerchRuleException;
import com.pzj.core.trade.refund.engine.handler.RefundApplyRuleLimitHandler;
import com.pzj.core.trade.refund.engine.model.MerchCalculateModel;
import com.pzj.core.trade.refund.engine.model.RefundApplyMerchModel;
import com.pzj.core.trade.refund.engine.model.RefundRuleLimit;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.order.common.OrderConfirmEnum;
import com.pzj.trade.order.entity.OrderEntity;

/**
 * 调用产品服务获取所有退款规则
 *
 * @author DongChunfu
 * @date 2016年12月3日
 */
@Component(value = "refundRuleLimitConverter")
public class RefundRuleLimitConverter {

	private static final Logger logger = LoggerFactory.getLogger(RefundRuleLimitConverter.class);

	@Autowired
	private IProductRuleService productRuleService;

	@Resource(name = "refundApplyRuleLimitHandler")
	private RefundApplyRuleLimitHandler refundApplyRuleLimitHandler;

	ProductRuleGroupEnum[] REFUND_GROUPS = {
	/* 退款规则 */PRODUCT_RULE_REFUND,
	/* 退款审核 */PRODUCT_RULE_REFUND_REVIEW };

	/**
	 * 根据产品退款规则退款规则,构建退款金额信息
	 * @param merchs
	 */
	public void assembleRuleLimit(final List<RefundApplyMerchModel> merchs, final RefundApplyEntity refundApply,
			final OrderEntity order, final Date refundApplyTime, final int isForce) {
		Map<Long, MerchCalculateModel> refundRuleCache = new HashMap<Long, MerchCalculateModel>();
		boolean isAuidt = false;
		Date expireTime = null;
		for (final RefundApplyMerchModel merch : merchs) {
			if (refundRuleCache.containsKey(merch.getProductId())) {
				continue;
			}
			MerchCalculateModel model = null;
			expireTime = merch.getExpireTime();
			if (isForce == RefundApplyTypeEnum.GENERAL.getType()
					&& order.getNeed_confirm() != OrderConfirmEnum.CONFIRMABLE.getValue()
					&& refundApply.getInitParty() != RefundInitPartyEnum.CONFIRM.getParty()) {
				final ProductRuleOutModel ruleModel = queryRefundRuleLimit(merch.getProductId());
				logger.info("商品[" + merch.getMerchId() + "], 产品ID[" + merch.getProductId() + "]. 退款规则模型 -->"
						+ (JSONConverter.toJson(ruleModel)));
				final RefundRuleLimit ruleLimit = setRefundRuleLimitAttribute(ruleModel);
				isAuidt = ruleLimit.isAudit();
				model = refundApplyRuleLimitHandler.refundable(ruleLimit, merch, refundApply.getIsParty(), refundApplyTime);
			} else {
				//强制退款的规则为全额退款
				model = new MerchCalculateModel(10000, 0);
			}
			refundRuleCache.put(merch.getProductId(), model);
		}
		//计算当前退款申请对应的审核状态
		refundApply.setRefundAuditState(convertRefundApplyAuditState(refundApply, order, isAuidt, expireTime, refundApplyTime)
				.getState());
		refundApply.setCreateTime(refundApplyTime);
		convertMerchCalculate(merchs, refundRuleCache);
	}

	private void convertMerchCalculate(final List<RefundApplyMerchModel> merchs,
			final Map<Long, MerchCalculateModel> refundRuleCache) {
		for (RefundApplyMerchModel merch : merchs) {
			merch.setMerchCalculateModel(refundRuleCache.get(merch.getProductId()));
		}
	}

	public RefundRuleLimit doRuleLimit(long productId) {
		final ProductRuleOutModel ruleModel = queryRefundRuleLimit(productId);
		logger.info(" 产品ID[" + productId + "]. 退款规则模型 -->" + (JSONConverter.toJson(ruleModel)));
		return setRefundRuleLimitAttribute(ruleModel);
	}

	/**
	 * 根据产品ID获取退款规则模型列表.
	 * 
	 * @param prodId
	 * @return
	 */
	private ProductRuleOutModel queryRefundRuleLimit(final long prodId) {
		Result<ProductRuleOutModel> result = null;
		try {
			result = productRuleService.getBySkuId(prodId, Arrays.asList(REFUND_GROUPS));
		} catch (final Throwable e) {
			throw new QueryRefundRuleException(10312, "根据产品ID[" + prodId + "]获取退款规则模型失败.", e);
		}

		if (!result.isOk()) {
			throw new QueryRefundRuleException(10312, "根据产品ID[" + prodId + "]获取退款规则模型失败, 返回错误码[" + result.getErrorCode()
					+ "], 错误描述[" + result.getErrorMsg() + "].");
		}

		final ProductRuleOutModel ruleModel = result.getData();
		if (ruleModel == null) {
			throw new QueryRefundRuleException(10312, "根据产品ID[" + prodId + "]获取退款规则模型失败, 返回错误码[" + result.getErrorCode()
					+ "], 错误描述[" + result.getErrorMsg() + "], 规则模型为空.");
		}
		return ruleModel;
	}

	private RefundRuleLimit setRefundRuleLimitAttribute(final ProductRuleOutModel ruleModels) {
		final RefundRuleLimit ruleLimit = new RefundRuleLimit();
		{
			//退款审核规则
			final ProductRuleOutModel outModel = RuleReader.readProdRuleInstByName(
					RefundReviewTypeEnum.refundReviewType.getName(), ruleModels);
			if (outModel != null) {
				ruleLimit.setRefundReview(outModel.getValue());
			}
		}
		{
			//是否可退, 全局判断.
			ProductRuleOutModel outModel = RuleReader
					.readProdRuleInstByName(RefundRuleEnum.refundAllowed.getName(), ruleModels);
			if (outModel != null) {
				if (outModel.getValue() == RefundAllowedTypeEnum.REFUND_AALLOWED_NO.getValue()) {
					throw new RefundMerchRuleException(RefundErrorCode.CAN_NOT_REFUND_BEFORE_DATE_ERROR_CODE, "所选择的商品不可退款");
				}
				ruleLimit.setRefundAllowed(outModel.getValue());
			}
			//是否随时可退.
			outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.refundAnyTime.getName(), ruleModels);
			if (outModel != null) {
				ruleLimit.setRefundAnyTime(outModel.getValue());
			}
			// 随时可退数量类型 (整单,部分)
			outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.refundAnyTimeQuantityType.getName(), ruleModels);
			if (outModel != null) {
				ruleLimit.setRefundAnyTimeQuantityType(outModel.getValue());
			}
			//随时可退, 收取的手续费金额.
			outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.refundAnyTimeFee.getName(), ruleModels);
			if (outModel != null) {
				ruleLimit.setRefundAnyTimeFee(outModel.getValue());
			}
			outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.refundDateType.getName(), ruleModels);
			if (outModel != null) {
				ruleLimit.setRefundDateType(outModel.getValue());
			}
			//若产品上的任意可退款规则被设置,则无需处理其他的退款规则
			if (ruleLimit.getRefundAnyTime() == RefundAnyTimeTypeEnum.REFUND_ANTTIME_YES.getValue()) {
				return ruleLimit;
			}
		}
		{
			//退款日期前退款规则:是否可退
			ProductRuleOutModel outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.isNeedPrerefund.getName(),
					ruleModels);
			if (outModel != null) {
				ruleLimit.setIsNeedPrerefund(outModel.getValue());
			}
			//退款日期前天数
			outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.prerefundDays.getName(), ruleModels);
			if (outModel != null) {
				ruleLimit.setPrerefundDays(outModel.getValue());
			}
			//退款日期前当天时刻-(时）
			outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.prerefundHour.getName(), ruleModels);
			if (outModel != null) {
				ruleLimit.setPrerefundHour(outModel.getValue());
			}
			//退款日期前当天时刻-(分）
			outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.prerefundMinute.getName(), ruleModels);
			if (outModel != null) {
				ruleLimit.setPrerefundMinute(outModel.getValue());
			}
		}
		{
			//退款日期后退款规则:是否可退
			ProductRuleOutModel outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.isNeedProrefund.getName(),
					ruleModels);
			if (outModel != null) {
				ruleLimit.setIsNeedProrefund(outModel.getValue());
			}
			//退款日期后天数
			outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.prorefundDays.getName(), ruleModels);
			if (outModel != null) {
				ruleLimit.setProrefundDays(outModel.getValue());
			}
			//退款日期后当天时刻-(时）
			outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.prorefundHour.getName(), ruleModels);
			if (outModel != null) {
				ruleLimit.setProrefundHour(outModel.getValue());
			}
			//退款日期后当天时刻-（分）
			outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.prorefundMinute.getName(), ruleModels);
			if (outModel != null) {
				ruleLimit.setProrefundMinute(outModel.getValue());
			}
		}
		{
			//退款日期前退款数量类型
			ProductRuleOutModel outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.prerefundQuantityType.getName(),
					ruleModels);
			if (outModel != null) {
				ruleLimit.setPrerefundQuantityType(outModel.getValue());
			}
			//退款日期前给供应商退款金额类型
			outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.prerefundSupplierFeetype.getName(), ruleModels);
			if (outModel != null) {
				ruleLimit.setPrerefundSupplierFeetype(outModel.getValue());
			}
			//退款日期前给供应商退款金额数值
			outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.prerefundSupplierFeevalue.getName(), ruleModels);
			if (outModel != null) {
				ruleLimit.setPrerefundSupplierFeevalue(outModel.getValue());
			}
			//退款日期前给分销商退款金额类型
			outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.prerefundDistributorFeetype.getName(), ruleModels);
			if (outModel != null) {
				ruleLimit.setPrerefundDistributorFeetype(outModel.getValue());
			}
			//退款日期前给分销商退款金额数值
			outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.prerefundDistributorFeevalue.getName(), ruleModels);
			if (outModel != null) {
				ruleLimit.setPrerefundDistributorFeevalue(outModel.getValue());
			}
		}
		{
			//退款日期后退款数量类型
			ProductRuleOutModel outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.prorefundQuantityType.getName(),
					ruleModels);
			if (outModel != null) {
				ruleLimit.setProrefundQuantityType(outModel.getValue());
			}
			//退款日期后给供应商退款金额类型
			outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.prorefundSupplierFeetype.getName(), ruleModels);
			if (outModel != null) {
				ruleLimit.setProrefundSupplierFeetype(outModel.getValue());
			}
			//退款日期后给供应商退款金额数值
			outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.prorefundSupplierFeevalue.getName(), ruleModels);
			if (outModel != null) {
				ruleLimit.setProrefundSupplierFeevalue(outModel.getValue());
			}
			//退款日期后给分销商退款金额类型
			outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.prorefundDistributorFeetype.getName(), ruleModels);
			if (outModel != null) {
				ruleLimit.setProrefundDistributorFeetype(outModel.getValue());
			}
			//退款日期后给分销商退款金额数值
			outModel = RuleReader.readProdRuleInstByName(RefundRuleEnum.prorefundDistributorFeevalue.getName(), ruleModels);
			if (outModel != null) {
				ruleLimit.setProrefundDistributorFeevalue(outModel.getValue());
			}
		}
		return ruleLimit;
	}

	/**
	 * 计算退款流水初始状态值.
	 * @param merch
	 * @return
	 */
	private RefundFlowAuditStateEnum convertRefundApplyAuditState(final RefundApplyEntity refundApply,
			final OrderEntity rootOrder, final boolean isAudit, final Date expireTime, final Date refundApplyTime) {
		//1. 强制退款, 强制做平台审核操作.
		if (RefundApplyTypeEnum.FORCE.getType() == refundApply.getIsForce()) {
			return RefundFlowAuditStateEnum.PLATFORM_AUDIT;
		}
		/*以下为普通退款, 退款单初始审核状态计算.*/
		//2. 商品超过有效期后的退款,强制做平台审核操作.
		if (refundApplyTime.after(expireTime)) {
			return RefundFlowAuditStateEnum.PLATFORM_AUDIT;
		}
		//3. 设置为需要审核, 且退款申请不为"二次确认拒绝", 则执行平台审核.
		if (isAudit && refundApply.getInitParty() != RefundInitPartyEnum.CONFIRM.getParty()) {
			return RefundFlowAuditStateEnum.PLATFORM_AUDIT;
		}
		// 4. 平台不需审核情况下,订单包含对接产品,并且退款的发起状态在二次确认之后
		if (rootOrder.getIs_dock() == 1 && refundApply.getInitParty() != RefundInitPartyEnum.CONFIRM.getParty()
				&& rootOrder.getNeed_confirm() != OrderConfirmEnum.CONFIRMABLE.getValue()) {
			return RefundFlowAuditStateEnum.DOCK_AUDITING;
		}
		//5. 平台不需要审核, 非对接产品直接为审核完成.
		{
			return RefundFlowAuditStateEnum.FINISHED;
		}
	}
}
