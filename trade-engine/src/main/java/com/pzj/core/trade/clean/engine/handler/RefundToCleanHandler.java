package com.pzj.core.trade.clean.engine.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.clean.engine.converter.CleaningStateEnum;
import com.pzj.core.trade.clean.engine.converter.MerchCleanRelationConvert;
import com.pzj.core.trade.clean.engine.converter.MerchEntityToMerchCleanModelTool;
import com.pzj.core.trade.clean.engine.event.FinishCleanEvent;
import com.pzj.core.trade.clean.engine.model.MerchCleanAssistModel;
import com.pzj.core.trade.clean.engine.model.MerchCleanCreatorModel;
import com.pzj.core.trade.clean.engine.model.SkuConsumerRuleModel;
import com.pzj.core.trade.clean.engine.validator.MerchCleanValidator;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.write.RefundApplyWriteMapper;
import com.pzj.framework.context.Result;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchCleanRelationEntity;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.merch.write.MerchCleanRelationWriteMapper;
import com.pzj.trade.merch.write.MerchRefundFlowWriteMapper;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.common.MerchStateEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;

@Component("refundToCleanHandler")
public class RefundToCleanHandler {

	private final static Logger logger = LoggerFactory.getLogger(ConsumerToCleanHandler.class);
	@Autowired
	private MerchRefundFlowWriteMapper merchRefundFlowWriteMapper;
	@Autowired
	private RefundApplyWriteMapper refundApplyWriteMapper;
	@Autowired
	private MerchCleanValidator merchCleanValidator;
	@Autowired
	private MerchCleanConvert merchCleanConvert;
	@Autowired
	private MerchWriteMapper merchWriteMapper;
	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private MerchCleanRelationConvert merchCleanRelationConvert;
	@Autowired
	private MerchCleanRelationWriteMapper merchCleanRelationWriteMapper;
	@Autowired
	private FinishCleanEvent finishCleanEvent;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	public Result<Boolean> convertClean(String saleOrderId, String refundId) {
		List<MerchCleanCreatorModel> merchModels = convertMerch(refundId);
		MerchCleanAssistModel assistModel = convertRefundApply(saleOrderId, refundId);
		final Map<Long, SkuConsumerRuleModel> skuConsumerRuleCache = new HashMap<Long, SkuConsumerRuleModel>();
		List<MerchCleanRelationEntity> merchRelations = new ArrayList<MerchCleanRelationEntity>();
		OrderEntity saleOrder = orderWriteMapper.getOrderEntityNotLock(saleOrderId);
		for (MerchCleanCreatorModel merch : merchModels) {
			//判断商品是否需要生成结算信息
			if (!merchCleanValidator.checkCreateCleanEntity(merch, assistModel)) {
				continue;
			}
			//全额退款不生产结算信息(强制退款除外)
			if (saleOrder.getTotal_amount() == saleOrder.getRefund_amount()
					&& saleOrder.getRefund_num() == saleOrder.getTotal_num()) {
				boolean hasTotalRefund = true;
				if (assistModel.getIsForceRefund() == 1) {
					MerchCleanRelationEntity entity = merchCleanRelationWriteMapper.queryCleanRelationByOrderIdAndMerchId(
							merch.getOrderId(), merch.getMerchId(), CleaningStateEnum.CLEANED.getState());
					if (!Check.NuNObject(entity)) {
						hasTotalRefund = false;
					}
				}
				if (hasTotalRefund) {
					break;
				}
			}
			merchCleanConvert.addConsumerRuleToCach(merch.getProductId(), merch.getVourType(), skuConsumerRuleCache);
			MerchCleanRelationEntity relation = merchCleanRelationConvert.convert(saleOrderId, merch, skuConsumerRuleCache,
					assistModel, saleOrder);
			merchRelations.add(relation);
			disposeRefundCleanRelation(merch, assistModel);
		}
		if (!Check.NuNCollections(merchRelations)) {
			merchCleanRelationWriteMapper.insertMerchCleanRelation(merchRelations);
		}
		List<MerchCleanRelationEntity> finishRelation = finishCleanEvent.disponseOrderFinish(saleOrderId, assistModel);
		if (!Check.NuNCollections(finishRelation)) {
			merchCleanRelationWriteMapper.insertMerchCleanRelation(finishRelation);
		}
		return new Result<Boolean>();
	}

	/**
	 * 退款处理清算信息
	 * @param relation
	 * @param assistModel
	 */
	private void disposeRefundCleanRelation(final MerchCleanCreatorModel merch, final MerchCleanAssistModel assistModel) {
		//如果商品是已经消费的状态,将商品对应的冻结清算记录设置为废弃状态
		if (merch.getMerchState() == MerchStateEnum.CONSUMED.getState()) {
			MerchCleanRelationEntity cleanEntity = merchCleanRelationWriteMapper.queryCleanRelationByOrderIdAndMerchId(
					merch.getOrderId(), merch.getMerchId(), CleaningStateEnum.FROZEN.getState());
			if (!Check.NuNObject(cleanEntity)) {
				int clean_state = CleaningStateEnum.ABANDONED.getState();
				//如果退款是强制退款
				if (assistModel.getIsForceRefund() == 1) {
					clean_state = CleaningStateEnum.CLEANED.getState();
				}
				merchCleanRelationWriteMapper.updateCleanRelationStateById(cleanEntity.getClean_id(), clean_state);
			}
		}
	}

	/**
	 * 获取清算相关信息
	 * @param refundId
	 * @return
	 */
	private List<MerchCleanCreatorModel> convertMerch(String refundId) {
		List<MerchCleanCreatorModel> models = new ArrayList<MerchCleanCreatorModel>();
		List<RefundFlowEntity> refundflows = merchRefundFlowWriteMapper.queryRefundFlowsByRefundId(refundId);
		for (RefundFlowEntity flow : refundflows) {
			//如果是分销商的退款流水信息,过滤
			if (flow.getRefund_type() == 2) {
				continue;
			}
			MerchEntity merch = merchWriteMapper.getMerchByMerchId(flow.getMerch_id());
			MerchEntity rootMerch = merchWriteMapper.getMerchByMerchId(merch.getRoot_merch_id());
			MerchCleanCreatorModel model = MerchEntityToMerchCleanModelTool.getCleanModel(merch, rootMerch);
			models.add(model);
		}
		return models;
	}

	private MerchCleanAssistModel convertRefundApply(String saleOrderId, String refundId) {
		MerchCleanAssistModel model = new MerchCleanAssistModel();
		RefundApplyEntity applyEntity = refundApplyWriteMapper.getRefundApplyEntityByRefundId(refundId);
		model.setIsForceRefund(applyEntity.getIsForce());
		model.setIsNottotalRefund(applyEntity.getIsParty());
		model.setRefundResouce(applyEntity.getInitParty());
		OrderEntity orderEntity = orderWriteMapper.getOrderEntityNotLock(saleOrderId);
		model.setIsOrderPaied(orderEntity.getOnline_pay());
		return model;
	}
}
