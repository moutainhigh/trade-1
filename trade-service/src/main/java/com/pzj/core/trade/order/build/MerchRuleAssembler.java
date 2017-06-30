package com.pzj.core.trade.order.build;

import static com.pzj.core.product.common.global.ProductRuleGroupEnum.PRODUCT_RULE_AUTO_VERIFICATION;
import static com.pzj.core.product.common.global.ProductRuleGroupEnum.PRODUCT_RULE_CONSUMER_CARD_TYPE;
import static com.pzj.core.product.common.global.ProductRuleGroupEnum.PRODUCT_RULE_GAIN_LIMIT;
import static com.pzj.core.product.common.global.ProductRuleGroupEnum.PRODUCT_RULE_IS_NEED_TAKE_ORDER;
import static com.pzj.core.product.common.global.ProductRuleGroupEnum.PRODUCT_RULE_IS_ONE_NOTE;
import static com.pzj.core.product.common.global.ProductRuleGroupEnum.PRODUCT_RULE_OVER_DUE;
import static com.pzj.core.product.common.global.ProductRuleGroupEnum.PRODUCT_RULE_PASSENGER_FILLED;
import static com.pzj.core.product.common.global.ProductRuleGroupEnum.PRODUCT_RULE_PLAY_TIME;
import static com.pzj.core.product.common.global.ProductRuleGroupEnum.PRODUCT_RULE_TWICE_SURE;
import static com.pzj.core.product.common.global.ProductRuleGroupEnum.PRODUCT_RULE_VERIFACTION_RULE;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.product.common.global.IsDockedEnum;
import com.pzj.core.product.common.global.ProductRuleGroupEnum;
import com.pzj.core.product.common.global.ProductRuleItem.AutoVerficationEnum;
import com.pzj.core.product.common.global.ProductRuleItem.GainLimitEnum;
import com.pzj.core.product.common.global.ProductRuleItem.PassengerFilledTypeEnum;
import com.pzj.core.product.common.global.ProductRuleItem.PlaytimeEnum;
import com.pzj.core.product.common.global.ProductRuleItem.TwiceSureEnum;
import com.pzj.core.product.common.model.response.ProductRuleOutModel;
import com.pzj.core.product.common.model.response.SpuProductOutModel;
import com.pzj.core.product.common.service.IProductRuleService;
import com.pzj.core.product.common.service.ISpuProductService;
import com.pzj.core.sku.common.constants.SkuProductGlobal;
import com.pzj.core.sku.common.constants.SkuProductGlobal.IsTwiceSureTypeEnum;
import com.pzj.core.sku.common.constants.SkuProductGlobal.NeedPlayTimeTypeEnum;
import com.pzj.core.trade.order.engine.RuleReader;
import com.pzj.core.trade.order.engine.exception.OrderException;
import com.pzj.core.trade.order.engine.exception.PlayTimeException;
import com.pzj.core.trade.order.engine.model.GainLimitModel;
import com.pzj.core.trade.order.engine.model.MerchBaseModel;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.common.VerificationVourTypeEnum;
import com.pzj.trade.merch.common.VourTypeEnum;

/**
 * 根据商品模型, 构建商品的规则信息.
 * @author chj
 *
 */
@Component
class MerchRuleAssembler {

	private final static Logger logger = LoggerFactory.getLogger(MerchRuleAssembler.class);

	@Autowired
	private IProductRuleService productRuleService;

	@Autowired
	private PlayDateCalculator playDateCalculator;

	@Autowired
	private ISpuProductService iSpuProductService;

	ProductRuleGroupEnum[] GROUPS = {
	/* 是否需要代下单 */PRODUCT_RULE_IS_NEED_TAKE_ORDER,
	/* 凭证类型 */PRODUCT_RULE_CONSUMER_CARD_TYPE,
	/* 是否一证一票 */PRODUCT_RULE_IS_ONE_NOTE,
	/* 是否需要二次确认 */PRODUCT_RULE_TWICE_SURE,
	/*	逾期规则*/PRODUCT_RULE_OVER_DUE,
	/*游玩日期*/PRODUCT_RULE_PLAY_TIME,
	/*购买限制*/PRODUCT_RULE_GAIN_LIMIT,
	/*游客填单项*/PRODUCT_RULE_PASSENGER_FILLED,
	/*核销 */PRODUCT_RULE_VERIFACTION_RULE, PRODUCT_RULE_AUTO_VERIFICATION };

	public MerchBaseModel assemble(MerchBaseModel merchModel) {
		ProductRuleOutModel ruleModel = getProductRuleModel(merchModel.getProdId());
		logger.info("产品规则:" + JSONConverter.toJson(ruleModel));
		if (ruleModel == null) {
			throw new OrderException(10500, "产品ID[" + merchModel.getProdId() + "]获取产品规则模型为空.");
		}

		setMerchRuleAttribute(merchModel, ruleModel);
		return merchModel;
	}

	/**
	 * 从产品服务获取产品规则模型.
	 * @param prodId
	 * @return
	 */
	private ProductRuleOutModel getProductRuleModel(long prodId) {
		Result<ProductRuleOutModel> result;
		try {
			result = productRuleService.getBySkuId(prodId, Arrays.asList(GROUPS));
		} catch (Throwable e) {
			throw new OrderException(10500, "根据产品ID[" + prodId + "]获取产品规则模型失败.", e);
		}

		if (!result.isOk()) {
			throw new OrderException(10500, "根据产品ID[" + prodId + "]获取产品规则模型失败, 返回错误码[" + result.getErrorCode() + "], 错误描述[" + result.getErrorMsg() + "].");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("根据产品ID[" + prodId + "]获取产品规则模型 -->" + (JSONConverter.toJson(result)));
		}

		ProductRuleOutModel ruleModel = result.getData();
		if (ruleModel == null) {
			throw new OrderException(10500, "根据产品ID[" + prodId + "]获取产品规则模型失败, 返回错误码[" + result.getErrorCode() + "], 错误描述[" + result.getErrorMsg()
					+ "], 规则模型为空.");
		}

		return ruleModel;
	}

	private void setMerchRuleAttribute(MerchBaseModel merchModel, ProductRuleOutModel ruleModels) {
		//		{
		//			ProductRuleOutModel outModel = RuleReader.readProdRuleInstByName(ConsumerCardTypeEnum.consumerCardType.getName(), ruleModels);
		//			if (outModel != null) {
		//				merchModel.setVourType(outModel.getValue());//详见: ConsumerTypeEnum
		//			} else {
		//				logger.error("获取产品的凭证类型为空");
		//				throw new OrderException(10500, "产品supID[" + merchModel.getSpuId() + "]获取产品凭证类型为空");
		//			}
		//		}
		//商品是否自动核销，新增规则
		{
			ProductRuleOutModel outModel = RuleReader.readProdRuleInstByName(AutoVerficationEnum.isAutoVerification.getName(), ruleModels);
			if (outModel != null) {
				if (outModel.getValue() == SkuProductGlobal.AutoVerificationTypeEnum.YES.getValue()) {
					merchModel.setAutoConfirm(1);
				} else {
					merchModel.setAutoConfirm(0);
				}
			}
		}

		{
			ProductRuleOutModel outModel = RuleReader.readProdRuleInstByName(TwiceSureEnum.twiceSure$.getName(), ruleModels);
			if (outModel != null) {
				merchModel.setNeedConfirm(IsTwiceSureTypeEnum.IS_TWICE_SURE.getValue() == outModel.getValue() ? 2 : 1);//产品服务上, 1代表需要确认, 0代表不需要. 交易服务上, 1代表不需要, 2代表需要. 产品服务的更优.
				if (merchModel.getIsDock() == IsDockedEnum.YES.getValue()) {
					merchModel.setNeedConfirm(2);//对接产品都需要二次确认.
				}
			}
		}
		{
			ProductRuleOutModel outModel = RuleReader.readProdRuleInstByName(PlaytimeEnum.isNeedPlaytime.getName(), ruleModels);
			if (outModel == null) {
				throw new PlayTimeException(10504, "产品[" + merchModel.getProdId() + "][是否需要出游日期]规则为空, 无法计算出游及游玩有效期.");
			}
			logger.info("出游日期规则" + JSONConverter.toJson(outModel));
			merchModel.setPlayTime(outModel.getValue().intValue() == NeedPlayTimeTypeEnum.NEED_PLAYTIME.getValue());//详见: NeedPlayTimeTypeEnum

			PlayDateCalculator.PlayTime playTime = playDateCalculator.calculate(merchModel, outModel, merchModel.getPlayDate());
			merchModel.setPlayDate(playTime.getPlayStartTime());
			merchModel.setExpireTime(playTime.getPlayEndTime());
		}
		{
			ProductRuleOutModel numModel = RuleReader.readProdRuleInstByName(GainLimitEnum.gainPeopleTimePurchaseNum.getName(), ruleModels);
			ProductRuleOutModel unitModel = RuleReader.readProdRuleInstByName(GainLimitEnum.gainPeopleTimeLimitUnit.getName(), ruleModels);
			ProductRuleOutModel valueModel = RuleReader.readProdRuleInstByName(GainLimitEnum.gainPeopleTimeLimitValue.getName(), ruleModels);
			if (numModel != null && numModel.getValue() > -1) {
				if (Check.NuNObject(unitModel, valueModel)) {
					throw new OrderException(15001, "限购信息不完善");
				}
				GainLimitModel gainLimitModel = new GainLimitModel();
				gainLimitModel.setNum(numModel.getValue());
				gainLimitModel.setUnit(unitModel.getValue());

				gainLimitModel.setValue(valueModel.getValue());
				merchModel.setGainLimitModel(gainLimitModel);
			}
		}

		{//游客填单项
			ProductRuleOutModel outModel = RuleReader.readProdRuleInstByName(PassengerFilledTypeEnum.passengerFilled.getName(), ruleModels);
			if (outModel != null) {
				merchModel.setPassengerType(outModel.getValue());
			}
		}
		{
			//获取核销方式
			final Result<SpuProductOutModel> spuResult = iSpuProductService.getSpuById(merchModel.getSpuId());
			if (!spuResult.isOk() || Check.NuNObject(spuResult.getData())) {
				logger.error("通过spuId:{},无法获取相关的spu信息", merchModel.getSpuId());
				throw new OrderException(10500, "根据产品supID[" + merchModel.getSpuId() + "]获取产品失败, 返回错误码[" + spuResult.getErrorCode() + "], 错误描述["
						+ spuResult.getErrorMsg() + "], 规则模型为空.");
			} else {
				if (spuResult.getData().getVerificationType() == null) {
					logger.info("产品supID[" + merchModel.getSpuId() + "获取到的核销方式是null");
					merchModel.setVerificationType(1);
				} else {
					logger.info("产品supID[" + merchModel.getSpuId() + "]获取的核销方式是：" + spuResult.getData().getVerificationType());
					merchModel.setVerificationType(spuResult.getData().getVerificationType());
				}

			}
		}

		//获取产品凭证类型与核销方式组合类型
		judgeVourType(merchModel);
	}

	private void judgeVourType(MerchBaseModel merchModel) {
		int verificationVourType = 0;

		//此处拆单的逻辑需要修改，改为根据核销方式和凭证类型进行拆分
		if (merchModel.getVourType() == VourTypeEnum.CARDID.getVourType()) {//凭证类型是身份证，那么核销方式只能是按份数进行核销
			verificationVourType = VerificationVourTypeEnum.CARDID_BY_NUM.getVourType();
			merchModel.setVerificationType(SkuProductGlobal.VerificationTypeEnum.VERIFICATION_BY_NUM.getValue());//将支付方式改为按份数
		} else if (merchModel.getVourType() == VourTypeEnum.MFCODE.getVourType()) {//凭证类型是二维码
			if (merchModel.getVerificationType() == SkuProductGlobal.VerificationTypeEnum.VERIFICATION_BY_NUM.getValue()) {
				verificationVourType = VerificationVourTypeEnum.MFCODE_BY_NUM.getVourType();
			} else if (merchModel.getVerificationType() == SkuProductGlobal.VerificationTypeEnum.VERIFICATION_BY_SKU.getValue()) {
				verificationVourType = VerificationVourTypeEnum.MFCODE_BY_SKU.getVourType();
			} else {
				verificationVourType = VerificationVourTypeEnum.MFCODE_BY_ORDER.getVourType();
				merchModel.setVerificationType(SkuProductGlobal.VerificationTypeEnum.VERIFICATION_BY_ORDER.getValue());//将核销方式改为按订单
			}
		} else if (merchModel.getVourType() == 4) {//手撕票的特殊情况，按照二维码按订单方式生成merch，voucher
			verificationVourType = VerificationVourTypeEnum.MFCODE_BY_ORDER.getVourType();
			merchModel.setVerificationType(SkuProductGlobal.VerificationTypeEnum.VERIFICATION_BY_ORDER.getValue());//将核销方式改为按订单
			merchModel.setVourType(VourTypeEnum.MFCODE.getVourType());
		} else {//凭证类型是联系人，核销方式只能是按订单
			verificationVourType = VerificationVourTypeEnum.CONTACT_BY_ORDER.getVourType();
		}
		merchModel.setVerificationVourType(verificationVourType);
	}

	public static void main(String[] args) {
		System.out.println(1 == NeedPlayTimeTypeEnum.NEED_PLAYTIME.getValue());
	}
}
