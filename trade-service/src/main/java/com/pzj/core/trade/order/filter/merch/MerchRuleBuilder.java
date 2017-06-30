package com.pzj.core.trade.order.filter.merch;

import static com.pzj.core.product.common.global.ProductRuleGroupEnum.PRODUCT_RULE_CONSUMER_CARD_TYPE;
import static com.pzj.core.product.common.global.ProductRuleGroupEnum.PRODUCT_RULE_IS_NEED_TAKE_ORDER;
import static com.pzj.core.product.common.global.ProductRuleGroupEnum.PRODUCT_RULE_IS_ONE_NOTE;
import static com.pzj.core.product.common.global.ProductRuleGroupEnum.PRODUCT_RULE_OVER_DUE;
import static com.pzj.core.product.common.global.ProductRuleGroupEnum.PRODUCT_RULE_PLAY_TIME;
import static com.pzj.core.product.common.global.ProductRuleGroupEnum.PRODUCT_RULE_TWICE_SURE;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.product.common.global.IsDockedEnum;
import com.pzj.core.product.common.global.ProductRuleGroupEnum;
import com.pzj.core.product.common.global.ProductRuleItem.ConsumerCardTypeEnum;
import com.pzj.core.product.common.global.ProductRuleItem.IsNeedTakeOrderEnum;
import com.pzj.core.product.common.global.ProductRuleItem.PlaytimeEnum;
import com.pzj.core.product.common.global.ProductRuleItem.TwiceSureEnum;
import com.pzj.core.product.common.model.response.ProductRuleOutModel;
import com.pzj.core.product.common.service.IProductRuleService;
import com.pzj.core.sku.common.constants.SkuProductGlobal.IsTwiceSureTypeEnum;
import com.pzj.core.sku.common.constants.SkuProductGlobal.NeedPlayTimeTypeEnum;
import com.pzj.core.trade.order.engine.RuleReader;
import com.pzj.core.trade.order.engine.exception.OrderException;
import com.pzj.core.trade.order.engine.exception.PlayTimeException;
import com.pzj.core.trade.order.engine.model.MerchModel;
import com.pzj.core.trade.order.filter.strategy.ProductOverdueTimeUtil;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;

/**
 * 根据商品模型, 构建商品的规则信息.
 * @author YRJ
 *
 */
@Component(value = "merchRuleBuilder")
class MerchRuleBuilder implements ObjectConverter<MerchModel, Void, MerchModel> {

	private final static Logger logger = LoggerFactory.getLogger(MerchRuleBuilder.class);

	@Autowired
	private IProductRuleService productRuleService;

	@Autowired
	private PlayTimeCalculator playTimeCalculator;

	ProductRuleGroupEnum[] GROUPS = {
	/* 是否需要代下单 */PRODUCT_RULE_IS_NEED_TAKE_ORDER,
	/* 凭证类型 */PRODUCT_RULE_CONSUMER_CARD_TYPE,
	/* 是否一证一票 */PRODUCT_RULE_IS_ONE_NOTE,
	/* 是否需要二次确认 */PRODUCT_RULE_TWICE_SURE,
	/*	逾期规则*/PRODUCT_RULE_OVER_DUE,
	/*游玩日期*/PRODUCT_RULE_PLAY_TIME };

	@Override
	public MerchModel convert(final MerchModel merchModel, final Void y) {
		final ProductRuleOutModel ruleModel = getProductRuleModel(merchModel.getProdId());
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
	private ProductRuleOutModel getProductRuleModel(final long prodId) {
		Result<ProductRuleOutModel> result;
		try {
			result = productRuleService.getBySkuId(prodId, Arrays.asList(GROUPS));
		} catch (final Throwable e) {
			throw new OrderException(10500, "根据产品ID[" + prodId + "]获取产品规则模型失败.", e);
		}

		if (!result.isOk()) {
			throw new OrderException(10500, "根据产品ID[" + prodId + "]获取产品规则模型失败, 返回错误码[" + result.getErrorCode() + "], 错误描述[" + result.getErrorMsg() + "].");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("根据产品ID[" + prodId + "]获取产品规则模型 -->" + (JSONConverter.toJson(result)));
		}

		final ProductRuleOutModel ruleModel = result.getData();
		if (ruleModel == null) {
			throw new OrderException(10500, "根据产品ID[" + prodId + "]获取产品规则模型失败, 返回错误码[" + result.getErrorCode() + "], 错误描述[" + result.getErrorMsg()
					+ "], 规则模型为空.");
		}

		return ruleModel;
	}

	private void setMerchRuleAttribute(final MerchModel merchModel, final ProductRuleOutModel ruleModels) {
		{
			final ProductRuleOutModel outModel = RuleReader.readProdRuleInstByName(IsNeedTakeOrderEnum.isNeedTakeOrder$.getName(), ruleModels);
			if (outModel != null) {
				merchModel.setAgent(outModel.getValue());//详见: IsNeedTakeOrderTypeEnum
			}
		}
		{
			final ProductRuleOutModel outModel = RuleReader.readProdRuleInstByName(ConsumerCardTypeEnum.consumerCardType.getName(), ruleModels);
			if (outModel != null) {
				merchModel.setVourType(outModel.getValue());//详见: ConsumerTypeEnum
			}
		}
		//		{
		//			ProductRuleOutModel outModel = RuleReader.readProdRuleInstByName(IsOneVoteEnum.isOneVote$.getName(), ruleModels);
		//			if (outModel != null) {
		//				merchModel.setOneVote(outModel.getValue());
		//				if (outModel.getValue() == 1) {
		//					merchModel.setVourType(VourTypeEnum.CARDID.getVourType());
		//				} else {
		//					merchModel.setVourType(VourTypeEnum.MFCODE.getVourType());
		//				}
		//			}
		//		}
		{
			final ProductRuleOutModel outModel = RuleReader.readProdRuleInstByName(TwiceSureEnum.twiceSure$.getName(), ruleModels);
			if (outModel != null) {
				merchModel.setNeedConfirm(IsTwiceSureTypeEnum.IS_TWICE_SURE.getValue() == outModel.getValue() ? 2 : 1);//产品服务上, 1代表需要确认, 0代表不需要. 交易服务上, 1代表不需要, 2代表需要. 产品服务的更优.
				if (merchModel.getIsDock() == IsDockedEnum.YES.getValue()) {
					merchModel.setNeedConfirm(2);//对接产品都需要二次确认.
				}
			}
		}
		{
			final ProductRuleOutModel outModel = RuleReader.readProdRuleInstByName(PlaytimeEnum.isNeedPlaytime.getName(), ruleModels);
			if (outModel == null) {
				throw new PlayTimeException(10504, "产品[" + merchModel.getProdId() + "][是否需要出游日期]规则为空, 无法计算出游及游玩有效期.");
			}
			logger.info("出游日期规则" + JSONConverter.toJson(outModel));
			merchModel.setIsPlayTime(outModel.getValue().intValue() == NeedPlayTimeTypeEnum.NEED_PLAYTIME.getValue());//详见: NeedPlayTimeTypeEnum

			final PlayTimeCalculator.PlayTime playTime = playTimeCalculator.calculate(merchModel, outModel, merchModel.getPlayDate());
			merchModel.setPlayDate(playTime.getPlayStartTime());
			merchModel.setExpireTime(playTime.getPlayEndTime());
			//merchModel.setPlayEndTime(playTime.getPlayEndTime());
		}
		{

			final long effectTime = ProductOverdueTimeUtil.getMerchOverdueTime(merchModel.getVourType(), ruleModels, merchModel.getExpireTime());
			logger.info("逾期清算时间点：{},逾期时间：{},产品有效期截至时间:{}", effectTime, new Date(effectTime).toLocaleString(), merchModel.getExpireTime().toLocaleString());
			merchModel.setEffecTime(effectTime);
		}
	}
}
