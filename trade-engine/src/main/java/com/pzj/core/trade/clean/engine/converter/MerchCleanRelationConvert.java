package com.pzj.core.trade.clean.engine.converter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.strategy.common.global.StrategyGlobal;
import com.pzj.core.trade.clean.engine.exception.MerchCleanException;
import com.pzj.core.trade.clean.engine.model.MerchCleanAssistModel;
import com.pzj.core.trade.clean.engine.model.MerchCleanCreatorModel;
import com.pzj.core.trade.clean.engine.model.SkuConsumerRuleModel;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchCleanRelationEntity;
import com.pzj.trade.merch.write.MerchCleanRelationWriteMapper;
import com.pzj.trade.order.entity.OrderEntity;

/**
 * 生成代结算信息
 * @author kangzl
 *
 */
@Component("merchCleanRelationConvert")
public class MerchCleanRelationConvert {

	@Autowired
	private MerchCleanRelationWriteMapper merchCleanRelationWriteMapper;

	/**
	 * 生成清算记录
	 * @param merch
	 * @param ruleCache
	 * @param assistModel
	 * @return
	 */
	public MerchCleanRelationEntity convert(final String transactionId, final MerchCleanCreatorModel merch,
			final Map<Long, SkuConsumerRuleModel> ruleCache, final MerchCleanAssistModel assistModel, OrderEntity rootOrder) {

		if (Check.NuNObject(merch)) {
			throw new MerchCleanException(10409, "商品核销信息为空");
		}
		if (Check.NuNObject(ruleCache) || Check.NuNObject(ruleCache.get(merch.getProductId()))) {
			throw new MerchCleanException(10409, "商品的核销规则信息为空");
		}
		if (Check.NuNObject(assistModel)) {
			throw new MerchCleanException(10409, "生成待清算信息的辅助参数为空");
		}
		long consumerTime = System.currentTimeMillis();
		long expireTime = merch.getExpireDate().getTime();

		SkuConsumerRuleModel ruleModel = ruleCache.get(merch.getProductId());
		MerchCleanRelationEntity relationEntity = new MerchCleanRelationEntity();

		relationEntity.setOrder_id(merch.getOrderId());
		relationEntity.setMerch_id(merch.getMerchId());
		relationEntity.setTransaction_id(transactionId);
		relationEntity.setClean_state(CleaningStateEnum.CLEANABLE.getState());
		relationEntity.setClean_count(0);
		relationEntity.setRefund_clean_num(merch.getRefundNum());
		relationEntity.setRefund_clean_amount(merch.getRefundAmount());
		relationEntity.setEffec_time(System.currentTimeMillis());
		//默认设置为正常清算
		relationEntity.setClean_type(1);
		//判断是否是强制退款
		if (assistModel.getIsForceRefund() == 1) {
			//系统中存在已经清算的记录的数据,才走对应的强制退款流程
			MerchCleanRelationEntity relation = merchCleanRelationWriteMapper.queryCleanRelationByOrderIdAndMerchId(
					merch.getOrderId(), merch.getMerchId(), CleaningStateEnum.CLEANED.getState());
			if (!Check.NuNObject(relation)) {
				relationEntity.setClean_type(2);
			}
		} else {
			int settmentType = ruleModel.getMerchSettlementType(consumerTime, expireTime);
			relationEntity.setEffec_time(ruleModel.getMerchCleanTime(consumerTime, expireTime));
			//正常核销
			if (settmentType == 1) {
				relationEntity.setNormal_clean_num(merch.getCheckNum());
				relationEntity.setNormal_clean_amount(merch.getPrice() * merch.getCheckNum());
				relationEntity.setOverdue_clean_num(0);
				relationEntity.setOverdue_clean_amount(0);
				//逾期核销
			} else {
				relationEntity.setNormal_clean_num(0);
				relationEntity.setNormal_clean_amount(0);
				double overduePrice = ruleModel.getMerchOverdueCleanPrice(merch.getPrice());
				relationEntity.setOverdue_clean_num(merch.getCheckNum());
				relationEntity.setOverdue_clean_amount(overduePrice * relationEntity.getOverdue_clean_num());
			}
		}
		//判断订单是否进行支付
		if (assistModel.getIsOrderPaied() == StrategyGlobal.IsNeedPaymentEnum.Not.getId() || rootOrder.getTotal_amount() == 0
				|| (rootOrder.getTotal_amount() == rootOrder.getRefund_amount() && rootOrder.getChecked_num() > 0)) {//同产品一致，0为不需要支付
			//		if (assistModel.getIsOrderPaied() == StrategyGlobal.IsNeedPaymentEnum.Not.getId() || rootOrder.getTotal_amount() == 0
			//				|| (rootOrder.getTotal_amount() == rootOrder.getRefund_amount() && rootOrder.getChecked_num() > 0)) {//同产品一致，0为不需要支付
			//			relationEntity.setClean_type(3);
			//		}
			relationEntity.setClean_type(3);
		}
		return relationEntity;
	}
}
