/**
 *
 */
package com.pzj.core.trade.refund.engine.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.DateUtil;
import com.pzj.core.trade.refund.engine.common.MerchStateEnum;
import com.pzj.core.trade.refund.engine.common.MerchTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.common.RefundFlowAuditStateEnum;
import com.pzj.core.trade.refund.engine.common.RefundInitPartyEnum;
import com.pzj.core.trade.refund.engine.common.RefundRuleTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundUserTypeEnum;
import com.pzj.core.trade.refund.engine.converter.RefundMerchCalculatPriceConverter;
import com.pzj.core.trade.refund.engine.converter.RefundRuleLimitConverter;
import com.pzj.core.trade.refund.engine.exception.CanNotRefundAfterDateException;
import com.pzj.core.trade.refund.engine.exception.CanNotRefundBeforeDateException;
import com.pzj.core.trade.refund.engine.exception.PartRefundException;
import com.pzj.core.trade.refund.engine.exception.PurchMerchNotFoundException;
import com.pzj.core.trade.refund.engine.exception.RefundDateNotInRangeException;
import com.pzj.core.trade.refund.engine.exception.VoucherNotFoundException;
import com.pzj.core.trade.refund.engine.model.RefundMerchModel;
import com.pzj.core.trade.refund.engine.model.RefundRuleLimit;
import com.pzj.core.trade.voucher.read.VoucherReadMapper;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.voucher.entity.VoucherEntity;

/**
 * 退款流水处理器
 *
 * @author DongChunfu
 * @date 2016年12月3日
 */
@Component(value = "refundFlowHandler")
public class RefundFlowHandler {

	private static final Logger logger = LoggerFactory.getLogger(RefundFlowHandler.class);

	@Resource(name = "refundMerchCalculatPriceConverter")
	private RefundMerchCalculatPriceConverter refundMerchCalculatPriceConverter;

	@Autowired
	private VoucherReadMapper voucherReadMapper;

	@Autowired
	private MerchWriteMapper merchWriteMapper;

	@Resource(name = "refundRuleLimitConverter")
	private RefundRuleLimitConverter refundRuleLimitHandler;

	@Resource(name = "dateUtil")
	private DateUtil dateUtil;

	/**
	 *
	 * @param refundMerches 退款商品集
	 * @param isParty 部分退款
	 * @param initParty 退款发起方
	 * @param sellOrder 销售订单
	 * @param auditState 审核状态
	 * @param isForce 强制退款
	 * @return List 退款流水记录
	 */
	public List<RefundFlowEntity> makeFlow(final RefundMerchModel[] refundMerches, final int isParty, final int initParty,
			final OrderEntity sellOrder, final Map<String, Integer> auditState, final int isForce) {

		final String refundId = UUID.randomUUID().toString().replace("-", "");
		final List<RefundFlowEntity> refundFlows = new ArrayList<RefundFlowEntity>();

		final Map<Long, RefundRuleLimit> refundRuleCache = new HashMap<Long, RefundRuleLimit>();// 退款规则缓存
		final Date refundDate = new Date();

		for (final RefundMerchModel refundMerch : refundMerches) {
			final long productId = refundMerch.getProductId();
			RefundRuleLimit refundRuleLimit = refundRuleCache.get(productId);
			if (Check.NuNObject(refundRuleLimit)) {
				if (isForce == RefundApplyTypeEnum.FORCE.getType().intValue()) {
					refundRuleLimit = new RefundRuleLimit();
				} else {
					refundRuleLimit = refundRuleLimitHandler.doRuleLimit(productId);
				}
				refundRuleCache.put(productId, refundRuleLimit);
			}
			// 判断当前退款规则是否可退,返回退款规则类型,时间点前或后或没有
			final int refundRuleType = doRuleLimit(refundRuleLimit, refundMerch, isParty, initParty, isForce);

			// 运价生成退款流水记录
			final List<RefundFlowEntity> doRuleflows = convertRefundMerchFlow(refundMerch, refundId, refundDate,
					refundRuleType, refundRuleLimit, sellOrder, isParty, auditState, initParty, isForce);

			refundFlows.addAll(doRuleflows);
		}

		return refundFlows;

	}

	/**
	 * 执行退款规则
	 *
	 * @param refundRuleLimit
	 *            退款规则
	 * @param refundMerchModel
	 *            退款商品
	 * @param isParty
	 *            整单/部分退
	 * @param initParty
	 *            发起方
	 * @return 退款规则类型
	 */
	private int doRuleLimit(final RefundRuleLimit refundRuleLimit, final RefundMerchModel refundMerchModel, final int isParty,
			final int initParty, final int isForce) {

		final MerchStateEnum merchState = MerchStateEnum.getMerchState(refundMerchModel.getMerchState());
		//二次确认拒绝发起的退款，不走相关的退款规则
		if (initParty == RefundInitPartyEnum.CONFIRM.getParty()) {
			return RefundRuleTypeEnum.NO_RULE.getType();
		}
		final Boolean doRule = merchState.doRule(isForce);
		if (!doRule) {
			return RefundRuleTypeEnum.NO_RULE.getType();
		}
		// 是否可退
		return refundable(refundRuleLimit, refundMerchModel, isParty, initParty);
	}

	/**
	 * 判断当前规则是否可退
	 *
	 * @param refundRuleLimit
	 *            退款规则
	 * @param refundMerchModel
	 *            退款商品
	 * @param isParty
	 *            整单/部分退
	 * @return 退款规则类型
	 */
	private int refundable(final RefundRuleLimit refundRuleLimit, final RefundMerchModel refundMerchModel, final int isParty,
			final int initParty) {
		final long voucherId = refundMerchModel.getVoucherId();
		final VoucherEntity voucher = voucherReadMapper.selectByPrimaryKey(voucherId);

		if (null == voucher) {
			throw new VoucherNotFoundException(RefundErrorCode.VOUCHER_NOT_FOUND_ERROR_CODE, "商品核销凭证异常");
		}

		final String orderId = refundMerchModel.getOrderId();
		final Integer refundDateType = refundRuleLimit.getRefundDateType();
		if (1 == refundDateType) { // 游玩日期
			final Date targetDate = voucher.getStartTime();
			return judgeCanRefund(refundRuleLimit, targetDate, "游玩", orderId, refundMerchModel.getMerchName(), isParty,
					initParty);
		} else {// 有效期后
			final Date targetDate = voucher.getExpireTime();
			return judgeCanRefund(refundRuleLimit, targetDate, "有效期", orderId, refundMerchModel.getMerchName(), isParty,
					initParty);
		}

	}

	private int judgeCanRefund(final RefundRuleLimit refundRuleLimit, final Date targetDate, final String refundDateType,
			final String orderId, final String productName, final int isParty, final int initParty) {

		final Date beforPointDate = dateUtil.getBeforPointDate(refundRuleLimit, targetDate);
		final Date afterPointDate = dateUtil.getAfterPointDate(refundRuleLimit, targetDate);
		final Date currentDate = new Date();

		// 时间范围判断
		if (currentDate.getTime() > beforPointDate.getTime() && currentDate.getTime() < afterPointDate.getTime()) {
			throw new RefundDateNotInRangeException(RefundErrorCode.REFUND_DATE_NOT_INRANGE_ERROR_CODE,
			/*"当前产品:[" + productName + "]不在可退时间范围内."*/"当前时间点不可退");
		}

		if (currentDate.getTime() < beforPointDate.getTime()) {// 时间点前

			if (refundRuleLimit.getIsNeedPrerefund() == 0) {
				throw new CanNotRefundBeforeDateException(RefundErrorCode.CAN_NOT_REFUND_BEFORE_DATE_ERROR_CODE,
				/*"此产品:[" + productName + "]" + refundDateType + "前不可退."*/"当前时间点不可退");
			}

			if (refundRuleLimit.getPrerefundQuantityType() == 1) {
				if (isParty == 1) {
					throw new PartRefundException(RefundErrorCode.PART_REFUND_ERROR_CODE, "只能对此订单进行整单退款.");
				}
			}

			return RefundRuleTypeEnum.BEFORE.getType();

		} else {// 时间点后

			if (initParty == RefundInitPartyEnum.GENERAL.getParty()) {
				throw new CanNotRefundBeforeDateException(RefundErrorCode.CAN_NOT_REFUND_BEFORE_DATE_ERROR_CODE,
				/*"此产品:[" + productName + "]" + refundDateType + "前不可退."*/"当前时间点不可退");
			}

			if (refundRuleLimit.getIsNeedProrefund() == 0) {
				throw new CanNotRefundAfterDateException(RefundErrorCode.CAN_NOT_REFUND_AFTER_DATE_ERROR_CODE,
				/*"此产品:[" + productName + "]" + refundDateType + "后不可退."*/"当前时间点不可退");
			}

			if (refundRuleLimit.getProrefundQuantityType() == 1) {
				if (isParty == 1) {
					throw new PartRefundException(RefundErrorCode.PART_REFUND_ERROR_CODE, "只能对此订单进行整单退款.");
				}
			}

			return RefundRuleTypeEnum.AFTER.getType();
		}
	}

	/**
	 * 生成退款流水
	 *
	 * @param sellMerch
	 *            销售商品
	 * @param refundId
	 *            退款ID
	 * @param refundDate
	 *            退款日期
	 * @param refundRuleType
	 *            退款规则类型
	 * @param refundRuleLimit
	 *            退款规则
	 * @param orderEntity
	 *            销售订单
	 * @param isParty	整单/部分退款
	 * @param auditState	退款审核状态(回传)
	 * @param initParty		退款发起方标识
	 * @param isForce	强制退款标识
	 * 
	 * @return 退款流水实体
	 */
	private List<RefundFlowEntity> convertRefundMerchFlow(final RefundMerchModel sellMerch, final String refundId,
			final Date refundDate, final int refundRuleType, final RefundRuleLimit refundRuleLimit,
			final OrderEntity orderEntity, final int isParty, final Map<String, Integer> auditState, final int initParty,
			final int isForce) {

		final int nextState = calculateRefundFlowState(sellMerch, refundRuleLimit, orderEntity, initParty, isForce);
		refundAuditStateHandle(auditState, nextState);

		// 获取对应采购订单的商品
		final MerchEntity purchMerch = merchWriteMapper.getPurchaseMerchBySellMerchId(sellMerch.getMerchId());
		if (null == purchMerch) {
			logger.error("对应商品的采购单为空,orderId:{},merchId:{}.", orderEntity.getOrder_id(), sellMerch.getMerchId());
			throw new PurchMerchNotFoundException(RefundErrorCode.PURCH_MERCH_NOT_FOUND_ERROR_CODE, "退款商品采购订单不存在");
		}
		final RefundMerchModel purchMerchModel = covertMerchEntity(purchMerch, sellMerch);

		// 根据退款规则计算退款价格
		final Double sellRefundPrice = refundMerchCalculatPriceConverter.convert(sellMerch, refundDate, refundRuleLimit,
				refundRuleType, MerchTypeEnum.SELL.getType());

		final Double purchRefundPrice = refundMerchCalculatPriceConverter.convert(purchMerchModel, refundDate, refundRuleLimit,
				refundRuleType, MerchTypeEnum.PURCHASE.getType());

		final RefundFlowEntity sellRefundFlow = buildRefundFlow(sellMerch, refundDate, sellRefundPrice, refundId,
				refundRuleType, isParty);
		final RefundFlowEntity purchaseRefundFlow = buildRefundFlow(purchMerchModel, refundDate, purchRefundPrice, refundId,
				refundRuleType, isParty);

		final List<RefundFlowEntity> flows = new ArrayList<RefundFlowEntity>(2);
		flows.add(purchaseRefundFlow);
		flows.add(sellRefundFlow);
		return flows;

	}

	/**
	 * 生成退款流水
	 *
	 * @param refundMerchModel
	 *            退款商品模型
	 * @param refundDate
	 *            退款日期
	 * @param refundPrice
	 *            退款价格
	 * @param refundId
	 *            退款ID
	 * @param refundAuditState
	 *            退款审核状态
	 * @param refundRuleType
	 *            退款规则类型
	 * @return
	 */
	private RefundFlowEntity buildRefundFlow(final RefundMerchModel refundMerchModel, final Date refundDate,
			final Double refundPrice, final String refundId, final int refundRuleType, final int isParty) {
		final String merchId = refundMerchModel.getMerchId();

		final RefundFlowEntity refundFlow = new RefundFlowEntity();
		refundFlow.setMerch_id(merchId);
		refundFlow.setRefund_id(refundId);
		refundFlow.setOrder_id(refundMerchModel.getOrderId());

		if (refundMerchModel.getMerchId().equalsIgnoreCase(refundMerchModel.getRootMerchId())) {
			refundFlow.setRefund_type(RefundUserTypeEnum.resellerRefund.getKey());
		} else {
			refundFlow.setRefund_type(RefundUserTypeEnum.supplierRefund.getKey());
		}
		refundFlow.setRefund_price(refundPrice);
		refundFlow.setCreate_time(refundDate);
		refundFlow.setRefund_num(refundMerchModel.getApplyNum());
		refundFlow.setRefund_rule_type(refundRuleType);
		return refundFlow;
	}

	/**
	 * 退款状态计算方法
	 *
	 * @param refundMerche
	 * @return
	 */
	private int calculateRefundFlowState(final RefundMerchModel refundMerche, final RefundRuleLimit refundRuleLimit,
			final OrderEntity orderEntity, final int initParty, final int isForce) {
		//强制退款
		if (isForce == RefundApplyTypeEnum.FORCE.getType()) {
			if (refundRuleLimit.isAudit()) {
				return RefundFlowAuditStateEnum.PLATFORM_AUDIT.getState();
			}
			return RefundFlowAuditStateEnum.FINANCE_AUDIT.getState();
		}

		// 普通退款
		if (refundRuleLimit.isAudit() && initParty != RefundInitPartyEnum.CONFIRM.getParty()) {
			return RefundFlowAuditStateEnum.PLATFORM_AUDIT.getState();
		}

		if (orderEntity.getIs_dock() == 1) {
			return RefundFlowAuditStateEnum.DOCK_AUDITING.getState();
		}

		return RefundFlowAuditStateEnum.FINISHED.getState();
	}

	/**
	 * 处理采购商品信息
	 *
	 * @param purchaseMerch
	 *            采购商品
	 * @param refundMerche
	 *            对应退款模型
	 * @return 退款模型
	 */
	private RefundMerchModel covertMerchEntity(final MerchEntity purchaseMerch, final RefundMerchModel refundMerche) {
		final RefundMerchModel purchaseMerchModel = RefundMerchModel.newInstance();

		purchaseMerchModel.setMerchId(purchaseMerch.getMerch_id());
		purchaseMerchModel.setRootMerchId(purchaseMerch.getRoot_merch_id());
		purchaseMerchModel.setProductId(purchaseMerch.getProduct_id());
		purchaseMerchModel.setVoucherId(purchaseMerch.getVoucher_id());
		purchaseMerchModel.setMerchState(purchaseMerch.getMerch_state());
		purchaseMerchModel.setApplyNum(refundMerche.getApplyNum());
		purchaseMerchModel.setRefundNum(refundMerche.getRefundNum());
		purchaseMerchModel.setOrderId(purchaseMerch.getOrder_id());
		purchaseMerchModel.setPrice(purchaseMerch.getPrice());

		return purchaseMerchModel;

	}

	private void refundAuditStateHandle(final Map<String, Integer> currentState, final Integer nextState) {

		final RefundFlowAuditStateEnum nextAuditState = RefundFlowAuditStateEnum.getRefundFlowStateEnum(nextState);
		final RefundFlowAuditStateEnum currentAuditState = RefundFlowAuditStateEnum.getRefundFlowStateEnum(currentState
				.get("currentState"));
		if (nextAuditState.compareTo(currentAuditState) < 0) {
			currentState.put("currentState", nextAuditState.getState());
		}
	}

}
