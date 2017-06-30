package com.pzj.core.trade.clean.engine.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.clean.engine.converter.CleaningStateEnum;
import com.pzj.core.trade.clean.engine.handler.ConsumerToCleanHandler;
import com.pzj.core.trade.clean.engine.model.MerchCleanFrozenModel;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchCleanRelationEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.merch.write.MerchCleanRelationWriteMapper;
import com.pzj.trade.merch.write.MerchRefundFlowWriteMapper;

/**
 * 退款申请对应的结算信息
 * @author kangzl
 *
 */
@Component
public class RefundApplyCleanEvent {

	@Autowired
	private MerchCleanRelationWriteMapper merchCleanRelationWriteMapper;
	@Autowired
	private MerchRefundFlowWriteMapper merchRefundFlowWriteMapper;

	private final static Logger logger = LoggerFactory.getLogger(ConsumerToCleanHandler.class);

	/**
	 * 冻结清算信息
	 */
	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	public void frozenCleanflow(String refundId) {
		logger.info("call RefundApplyCleanEvent.frozenCleanflow,param:{}", JSONConverter.toJson(refundId));
		List<MerchCleanFrozenModel> frozenModels = converToCleanFlow(refundId);
		//如果输入的集合为空,任务终止
		if (Check.NuNCollections(frozenModels)) {
			return;
		}
		for (Iterator<MerchCleanFrozenModel> iter = frozenModels.iterator(); iter.hasNext();) {
			MerchCleanFrozenModel model = iter.next();
			MerchCleanRelationEntity entity = merchCleanRelationWriteMapper.queryCleanRelationByOrderIdAndMerchId(
					model.getOrderId(), model.getMerchId(), CleaningStateEnum.CLEANABLE.getState());
			if (!Check.NuNObject(entity)) {
				merchCleanRelationWriteMapper.updateCleanRelationStateById(entity.getClean_id(),
						CleaningStateEnum.FROZEN.getState());
			}
		}
	}

	/**
	 * 解冻清算信息
	 * @param frozenModels
	 */
	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	public void cancelfrozenCleanflow(String refundId) {
		logger.info("call RefundApplyCleanEvent.cancelfrozenCleanflow,param:{}", JSONConverter.toJson(refundId));
		List<MerchCleanFrozenModel> frozenModels = converToCleanFlow(refundId);
		//如果输入的集合为空,任务终止
		if (Check.NuNCollections(frozenModels)) {
			return;
		}
		for (Iterator<MerchCleanFrozenModel> iter = frozenModels.iterator(); iter.hasNext();) {
			MerchCleanFrozenModel model = iter.next();
			MerchCleanRelationEntity entity = merchCleanRelationWriteMapper.queryCleanRelationByOrderIdAndMerchId(
					model.getOrderId(), model.getMerchId(), CleaningStateEnum.FROZEN.getState());
			if (!Check.NuNObject(entity)) {
				merchCleanRelationWriteMapper.updateCleanRelationStateById(entity.getClean_id(),
						CleaningStateEnum.CLEANABLE.getState());
			}
		}
	}

	/**
	 * 退款成功后触发
	 * @param refundId
	 */
	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	public void confirmRefund(String refundId) {
		logger.info("call RefundApplyCleanEvent.confirmRefund,param:{}", JSONConverter.toJson(refundId));
		List<MerchCleanFrozenModel> frozenModels = converToCleanFlow(refundId);
		//如果输入的集合为空,任务终止
		if (Check.NuNCollections(frozenModels)) {
			return;
		}
		for (Iterator<MerchCleanFrozenModel> iter = frozenModels.iterator(); iter.hasNext();) {
			MerchCleanFrozenModel model = iter.next();
			MerchCleanRelationEntity entity = merchCleanRelationWriteMapper.queryCleanRelationByOrderIdAndMerchId(
					model.getOrderId(), model.getMerchId(), CleaningStateEnum.FROZEN.getState());
			if (!Check.NuNObject(entity)) {
				merchCleanRelationWriteMapper.updateCleanRelationStateById(entity.getClean_id(),
						CleaningStateEnum.ABANDONED.getState());
			}
		}
	}

	private List<MerchCleanFrozenModel> converToCleanFlow(String refundId) {
		List<MerchCleanFrozenModel> frozenModels = new ArrayList<MerchCleanFrozenModel>();
		List<RefundFlowEntity> refundflows = merchRefundFlowWriteMapper.queryRefundFlowsByRefundId(refundId);
		for (RefundFlowEntity flow : refundflows) {
			//如果是分销商的退款申请流水信息,跳过
			if (flow.getRefund_type() == 2) {
				continue;
			}
			MerchCleanFrozenModel model = new MerchCleanFrozenModel();
			model.setMerchId(flow.getMerch_id());
			model.setOrderId(flow.getOrder_id());
			frozenModels.add(model);
		}
		return frozenModels;
	}
}
