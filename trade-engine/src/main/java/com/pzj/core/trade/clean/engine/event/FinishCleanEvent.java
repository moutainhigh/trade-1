package com.pzj.core.trade.clean.engine.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.clean.engine.converter.CleaningStateEnum;
import com.pzj.core.trade.clean.engine.converter.MerchCleanRelationConvert;
import com.pzj.core.trade.clean.engine.converter.MerchEntityToMerchCleanModelTool;
import com.pzj.core.trade.clean.engine.handler.MerchCleanConvert;
import com.pzj.core.trade.clean.engine.model.MerchCleanAssistModel;
import com.pzj.core.trade.clean.engine.model.MerchCleanCreatorModel;
import com.pzj.core.trade.clean.engine.model.SkuConsumerRuleModel;
import com.pzj.core.trade.context.utils.MoneyUtils;
import com.pzj.core.trade.refund.engine.common.RefundingEnum;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchCleanRelationEntity;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.write.MerchCleanRelationWriteMapper;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;

/**
 * 商品退款,或核销完成后触发
 * @author kangzl
 *
 */
@Component("finishCleanEvent")
public class FinishCleanEvent {
	@Autowired
	private MerchWriteMapper merchWriteMapper;
	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private MerchCleanConvert merchCleanConvert;
	@Autowired
	private MerchCleanRelationConvert merchCleanRelationConvert;

	@Autowired
	private MerchCleanRelationWriteMapper merchCleanRelationWriteMapper;

	/**
	 * 当交易的相关属性都已经完结后触发
	 * @param transactionId
	 */
	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	public List<MerchCleanRelationEntity> disponseOrderFinish(String transactionId, MerchCleanAssistModel assistModel) {
		//获取销售订单
		OrderEntity resellerOrder = orderWriteMapper.getOrderEntityNotLock(transactionId);
		List<MerchEntity> resellerMerches = merchWriteMapper.getMerchByOrderId(resellerOrder.getOrder_id());
		if (!checkOrderIsFinish(resellerOrder, resellerMerches)) {
			//全额退款的商品默认为已清算
			return null;
		}
		List<MerchCleanRelationEntity> totalRelations = new ArrayList<MerchCleanRelationEntity>();
		//查询销售订单商品对应的相关结算信息
		for (MerchEntity resellerMerch : resellerMerches) {
			List<MerchCleanRelationEntity> relations = disposeResellerMerch(resellerMerch, assistModel, resellerOrder);
			if (!Check.NuNCollections(relations)) {
				totalRelations.addAll(relations);
			}
		}
		return totalRelations;
	}

	/**
	 * 效验商品是否已经完结
	 * @param order
	 * @param merches
	 * @return
	 */
	private boolean checkOrderIsFinish(final OrderEntity resellerOrder, final List<MerchEntity> merches) {
		//订单状态已经是已完结
		if (resellerOrder.getOrder_status() == OrderStatusEnum.Finished.getValue()) {
			return false;
		}
		for (MerchEntity merch : merches) {
			//如果村咋退款中的商品则任务终止
			if (merch.getIs_refunding() == RefundingEnum.REFUNDING.getValue()) {
				return false;
			}
		}
		//订单给分销商全额退款,不需要生成结算信息
		if (resellerOrder.getTotal_amount() == resellerOrder.getRefund_amount() && resellerOrder.getTotal_num() == resellerOrder.getRefund_num()) {
			//全额退款的订单默认为已清算
			merchWriteMapper.updateCleanedByTransactionId(resellerOrder.getTransaction_id());
			return false;
		}
		if (resellerOrder.getRefund_num() + resellerOrder.getChecked_num() == resellerOrder.getTotal_num() && resellerOrder.getChecked_num() > 0) {
			return true;
		}
		if (resellerOrder.getRefund_num() == resellerOrder.getTotal_num() && resellerOrder.getRefund_amount() != resellerOrder.getTotal_amount()) {
			return true;
		}
		return false;
	}

	private List<MerchCleanRelationEntity> disposeResellerMerch(final MerchEntity resellerMerch, final MerchCleanAssistModel assistModel,
			OrderEntity resellerOrder) {
		List<MerchEntity> supplierMerches = merchWriteMapper.getMerchsByRootMerchId(resellerMerch.getMerch_id());
		List<MerchCleanRelationEntity> relations = new ArrayList<MerchCleanRelationEntity>();
		final Map<Long, SkuConsumerRuleModel> skuConsumerRuleCache = new HashMap<Long, SkuConsumerRuleModel>();
		String transactionId = null;
		for (MerchEntity merch : supplierMerches) {
			if (merch.getMerch_id().equals(resellerMerch.getMerch_id())) {
				continue;
			}
			//判断商品是否已经存在对应的清算信息,如果存在则不用做相关的结算记录补偿操作
			//商品的待清算记录
			MerchCleanRelationEntity oldRelationa = merchCleanRelationWriteMapper.queryCleanRelationByOrderIdAndMerchId(merch.getOrder_id(),
					merch.getMerch_id(), CleaningStateEnum.CLEANABLE.getState());
			if (!Check.NuNObject(oldRelationa)) {
				continue;
			}
			//商品的已清算记录
			MerchCleanRelationEntity oldRelationb = merchCleanRelationWriteMapper.queryCleanRelationByOrderIdAndMerchId(merch.getOrder_id(),
					merch.getMerch_id(), CleaningStateEnum.CLEANED.getState());
			if (!Check.NuNObject(oldRelationb)) {
				continue;
			}
			MerchCleanCreatorModel cleanModel = MerchEntityToMerchCleanModelTool.getCleanModel(merch, resellerMerch);
			//全额退款
			if (MoneyUtils.getMoenyNumber(cleanModel.getRootPrice() * cleanModel.getRootTotalNum()) == MoneyUtils.getMoenyNumber(cleanModel
					.getRootRefundAmount()) && cleanModel.getRootRefundNum() == cleanModel.getTotalNum()) {
				merchCleanConvert.addConsumerRuleToCach(cleanModel.getProductId(), cleanModel.getVourType(), skuConsumerRuleCache);
				MerchCleanRelationEntity relation = merchCleanRelationConvert.convert(transactionId, cleanModel, skuConsumerRuleCache, assistModel,
						resellerOrder);
				relations.add(relation);
			}
		}
		return relations;
	}
}
