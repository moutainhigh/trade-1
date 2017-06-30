package com.pzj.core.trade.sms.engine.convert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.product.common.global.ProductRuleGroupEnum;
import com.pzj.core.product.common.global.ProductRuleItem.PlaytimeEnum;
import com.pzj.core.product.common.model.response.ProductRuleOutModel;
import com.pzj.core.product.common.model.response.SkuProductOutModel;
import com.pzj.core.product.common.model.response.SpuProductOutModel;
import com.pzj.core.product.common.service.IProductRuleService;
import com.pzj.core.product.common.service.ISpuProductService;
import com.pzj.core.product.common.util.ProductRuleUtils;
import com.pzj.core.sku.common.constants.SkuProductGlobal;
import com.pzj.core.sku.common.constants.SkuRuleGLobal;
import com.pzj.core.trade.sms.engine.model.SendSMSRuleModel;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.write.OrderStrategyWriteMapper;
import com.pzj.trade.sms.exception.SmsException;

/**
 * 获取产品的短信发送规则
 * @author kangzl
 *
 */
@Component("sendSMSRuleConvert")
public class SendSMSRuleConvert {

	private final static Logger logger = LoggerFactory.getLogger(SendSMSRuleConvert.class);
	@Autowired
	private IProductRuleService productRuleService;

	@Autowired
	private ISpuProductService spuProductService;

	@Autowired
	private OrderStrategyWriteMapper orderStrategyWriteMapper;

	//	@Autowired
	//	private Strategy4TradeService strategy4TradeSerivce;

	@Autowired
	private ISpuProductService iSpuProductService;

	/**
	 * 获取产品规则信息
	 * @param merches
	 * @return
	 */
	public Map<Long, SendSMSRuleModel> getRuleCache(final List<MerchEntity> merches) {
		final Map<Long, SendSMSRuleModel> ruleCache = new HashMap<Long, SendSMSRuleModel>();
		for (final MerchEntity merch : merches) {
			final long skuid = merch.getProduct_id();
			if (Check.NuNObject(ruleCache.get(skuid))) {
				final SendSMSRuleModel ruleModel = convert(skuid);
				ruleModel.setVourType(merch.getVour_type());
				ruleModel.setVerificationType(merch.getVerification_type());
				ruleModel.setMerchType(merch.getMerch_type());
				ruleCache.put(skuid, ruleModel);
			}
		}
		return ruleCache;
	}

	/**
	 * 获取产品规则信息
	 * @param skuid
	 * @return
	 */
	public SendSMSRuleModel convert(final long skuid) {
		final List<ProductRuleGroupEnum> gropenums = new ArrayList<ProductRuleGroupEnum>();
		gropenums.add(ProductRuleGroupEnum.PRODUCT_RULE_MESSAGE_RULE);
		final ProductRuleOutModel productRuleModel = getProductRuleModel(skuid, gropenums);
		final SendSMSRuleModel repModel = new SendSMSRuleModel();
		disposeNodes(productRuleModel, repModel);
		final SkuProductOutModel skuModel = getSkuOutModel(skuid);
		repModel.setProdcutName(skuModel.getProductName());
		repModel.setSkuName(skuModel.getSkuName());
		repModel.setSkuId(skuid);
		return repModel;
	}

	public Map<Long, Double> getSettlementPriceCache(final List<MerchEntity> merches, final OrderEntity buyerOrder, final int salePort) {
		final Map<Long, Double> settlementPriceCache = new HashMap<Long, Double>();
		for (final MerchEntity merch : merches) {
			final long skuid = merch.getProduct_id();
			OrderStrategyEntity strategy = orderStrategyWriteMapper.getSingleOrderStrategy(buyerOrder.getOrder_id(), merch.getRoot_merch_id());
			settlementPriceCache.put(skuid, strategy.getSettlement_price().doubleValue());
		}
		return settlementPriceCache;
	}

	/**
	 * 效验规则被定义为是否需要发送当前短信
	 * @param ruleCache
	 * @param messageType
	 * @return
	 */
	public boolean checkSMSSend(final Map<Long, SendSMSRuleModel> ruleCache, final int messageType) {
		boolean reuslt = false;
		for (final Entry<Long, SendSMSRuleModel> entry : ruleCache.entrySet()) {
			final SendSMSRuleModel ruleModel = entry.getValue();
			if (messageType == SkuRuleGLobal.MessageTypeEnum.contractConfirmMsg.getValue()) {
				if (ruleModel.isContractConfirmMsg()) {
					reuslt = true;
					break;
				}
			} else if (messageType == SkuRuleGLobal.MessageTypeEnum.contractVoucherMsg.getValue()) {
				if (ruleModel.isContractVoucherMsg()) {
					reuslt = true;
					break;
				}
			} else if (messageType == SkuRuleGLobal.MessageTypeEnum.supplierMsg.getValue()) {
				if (ruleModel.isSupplierMsg()) {
					reuslt = true;
					break;
				}
			}
		}
		return reuslt;
	}

	/**
	 * 迭代处理规则节点信息
	 * @param ruleBaseModel
	 * @param repModel
	 */
	private void disposeNodes(final ProductRuleOutModel ruleBaseModel, final SendSMSRuleModel repModel) {
		if (Check.NuNObject(ruleBaseModel)) {
			return;
		}
		if (!Check.NuNObject(ruleBaseModel.getItemRule())) {
			final int value = ruleBaseModel.getValue();
			initModelValue(value, repModel);
		}
		final List<ProductRuleOutModel> ruleNodes = ruleBaseModel.getNodes();
		if (!Check.NuNCollections(ruleNodes)) {
			for (final ProductRuleOutModel node : ruleNodes) {
				disposeNodes(node, repModel);
			}
		}
	}

	private void initModelValue(final int value, final SendSMSRuleModel repModel) {
		if (value == SkuRuleGLobal.MessageTypeEnum.contractConfirmMsg.getValue()) {
			repModel.setContractConfirmMsg(true);
		} else if (value == SkuRuleGLobal.MessageTypeEnum.contractVoucherMsg.getValue()) {
			repModel.setContractVoucherMsg(true);
		} else if (value == SkuRuleGLobal.MessageTypeEnum.supplierMsg.getValue()) {
			repModel.setSupplierMsg(true);
		}
	}

	/**
	 * 获取sku相关信息
	 * @param skuid
	 * @return
	 */
	private SkuProductOutModel getSkuOutModel(final long skuid) {
		logger.info("call spuProductService.getSkuById sendParma:{}", skuid);
		final Result<SkuProductOutModel> skuResult = spuProductService.getSkuById(skuid);
		logger.info("call spuProductService.getSkuById getResult:{}", JSONConverter.toJson(skuResult));
		if (!skuResult.isOk() || Check.NuNObject(skuResult.getData())) {
			logger.error("通过skuId:{},无法获取相关的sku信息", skuid);
			throw new SmsException("获取sku信息异常");
		}
		return skuResult.getData();
	}

	public SpuProductOutModel getSpuModel(final long skuid) {
		final SkuProductOutModel skuModel = getSkuOutModel(skuid);
		final long spuid = skuModel.getSpuId();
		logger.info("call iSpuProductService.getSpuById sendParma--->skuid:{},spuid:{}", skuid, spuid);
		final Result<SpuProductOutModel> spuResult = iSpuProductService.getSpuById(spuid);
		if (!spuResult.isOk() || Check.NuNObject(spuResult.getData())) {
			logger.error("通过spuId:{},无法获取相关的spu信息", spuid);
			throw new SmsException("获取spu信息异常");
		}
		return spuResult.getData();
	}

	/**
	 * 验证是否需要填写游玩有效期
	 * @param skuid
	 * @return
	 */
	public boolean checkIsNeedPaytime(final long skuid) {
		final boolean flag = false;
		final List<ProductRuleGroupEnum> gropenums = new ArrayList<ProductRuleGroupEnum>();
		gropenums.add(ProductRuleGroupEnum.PRODUCT_RULE_PLAY_TIME);
		final ProductRuleOutModel ruleModel = getProductRuleModel(skuid, gropenums);
		final List<ProductRuleOutModel> productRules = ProductRuleUtils.get(ruleModel, PlaytimeEnum.isNeedPlaytime, skuid);
		if (productRules.size() != 1) {
			throw new SmsException("获取产品是否需要填写游玩时间规则异常");
		}
		final ProductRuleOutModel rule = productRules.get(0);
		if (rule.getValue() != SkuProductGlobal.NeedPlayTimeTypeEnum.NEED_PLAYTIME.getValue()) {
			return true;
		}
		return flag;
	}

	/**
	 * 获取产品规则的相关信息
	 * @param skuid
	 * @return
	 */
	private ProductRuleOutModel getProductRuleModel(final long skuid, final List<ProductRuleGroupEnum> gropenums) {
		logger.info("call productRuleService.getBySkuId sendParam:{}", JSONConverter.toJson(gropenums));
		final Result<ProductRuleOutModel> result = productRuleService.getBySkuId(skuid, gropenums);
		logger.info("call productRuleService.getBySkuId getResult:{}", JSONConverter.toJson(result));
		if (!result.isOk()) {
			throw new SmsException("获取sku的短信发送规则信息失败");
		}
		return result.getData();
	}
}
