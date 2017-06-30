package com.pzj.core.trade.saas.refund.engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundFlowAuditStateEnum;
import com.pzj.core.trade.refund.engine.common.RefundFlowStateEnum;
import com.pzj.core.trade.refund.engine.common.RefundLogEventEnum;
import com.pzj.core.trade.refund.engine.converter.RefundApplyEntityBuilder;
import com.pzj.core.trade.refund.engine.converter.RefundApplyMerchBuilder;
import com.pzj.core.trade.refund.engine.converter.RefundFlowEntityBuilder;
import com.pzj.core.trade.refund.engine.converter.RefundFreezeFlowBuilder;
import com.pzj.core.trade.refund.engine.converter.RefundMerchUpdateBuilder;
import com.pzj.core.trade.refund.engine.converter.RefundOperInfoEntityBuilder;
import com.pzj.core.trade.refund.engine.converter.RefundOperLogEntityBuilder;
import com.pzj.core.trade.refund.engine.converter.RefundOrderUpdateModelBuilder;
import com.pzj.core.trade.refund.engine.converter.RefundRuleLimitConverter;
import com.pzj.core.trade.refund.engine.filter.RefundApplyParamFilter;
import com.pzj.core.trade.refund.engine.handler.RefundOperLogMQHandler;
import com.pzj.core.trade.refund.engine.model.RefundApplyMerchModel;
import com.pzj.core.trade.refund.engine.model.RefundApplyResultModel;
import com.pzj.core.trade.refund.engine.model.RefundOperMQLogModel;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.entity.RefundApplyInfoEntity;
import com.pzj.core.trade.refund.entity.RefundOperLog;
import com.pzj.core.trade.refund.write.RefundApplyInfoWriteMapper;
import com.pzj.core.trade.refund.write.RefundApplyWriteMapper;
import com.pzj.core.trade.refund.write.RefundOperLogWriteMapper;
import com.pzj.core.trade.saas.refund.engine.handler.SaasAccountRefundCancelHandler;
import com.pzj.core.trade.saas.refund.engine.handler.SaasAccountRefundFrozenHandler;
import com.pzj.core.trade.saas.refund.engine.handler.SaasRefundCallDockHandler;
import com.pzj.core.trade.saas.refund.engine.handler.SaasRefundFinishHandler;
import com.pzj.core.trade.saas.refund.engine.handler.SaasRefundRefusedHandler;
import com.pzj.framework.toolkit.Check;
import com.pzj.settlement.balance.response.PaymentResponse;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.merch.model.MerchRefundModel;
import com.pzj.trade.merch.write.MerchRefundFlowWriteMapper;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.model.OrderRefundModel;
import com.pzj.trade.order.model.RefundMerchStrategyModel;
import com.pzj.trade.order.read.OrderStrategyReadMapper;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.write.FreezeFlowWriteMapper;
import com.pzj.trade.refund.model.RefundApplyReqModel;

/**
 * saas1.1.0系统的退款处理引擎
 * @author kangzl
 *
 */
@Component("sassRefundApplyEngine")
public class SassRefundApplyEngine {
	private static final Logger logger = LoggerFactory.getLogger(SassRefundApplyEngine.class);
	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private OrderStrategyReadMapper orderStrategyReadMapper;

	@Autowired
	private RefundApplyMerchBuilder refundApplyMerchBuilder;

	@Autowired
	private RefundApplyEntityBuilder refundApplyEntityBuilder;

	@Autowired
	private RefundOperInfoEntityBuilder refundOperInfoEntityBuilder;

	@Autowired
	private RefundFlowEntityBuilder refundFlowEntityBuilder;

	@Autowired
	private RefundMerchUpdateBuilder refundMerchUpdateBuilder;

	@Autowired
	private RefundOrderUpdateModelBuilder orderUpdateModelBuilder;

	@Autowired
	private RefundOperLogEntityBuilder refundOperLogEntityBuilder;
	@Autowired
	private RefundFreezeFlowBuilder refundFreezeFlowBuilder;

	@Autowired
	private RefundRuleLimitConverter refundRuleLimitConverter;

	@Autowired
	private RefundApplyParamFilter refundApplyParamFilter;

	@Autowired
	private SaasAccountRefundFrozenHandler saasAccountRefundFrozenHandler;

	@Autowired
	private SaasAccountRefundCancelHandler sassAccountRefundCancelHandler;

	@Autowired
	private SaasRefundFinishHandler saasRefundFinishHandler;

	@Autowired
	private SaasRefundRefusedHandler saasRefundRefusedHandler;

	@Autowired
	private SaasRefundCallDockHandler saasRefundCallDockHandler;

	@Autowired
	private RefundOperLogMQHandler refundOperLogMQHandler;

	@Autowired
	private MerchWriteMapper merchWriteMapper;

	@Autowired
	private RefundApplyWriteMapper refundApplyWriteMapper;

	@Autowired
	private RefundApplyInfoWriteMapper refundApplyInfoWriteMapper;

	@Autowired
	private MerchRefundFlowWriteMapper merchRefundFlowWriteMapper;

	@Autowired
	private RefundOperLogWriteMapper refundOperLogWriteMapper;

	@Autowired
	private FreezeFlowWriteMapper freezeFlowWriteMapper;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED)
	public RefundApplyResultModel doRefundApply(final RefundApplyReqModel reqModel) {
		final OrderEntity saleOrder = orderWriteMapper.getOrderEntityForUpdate(reqModel.getOrderId());
		refundApplyParamFilter.dofilter(reqModel, saleOrder);
		//1. 根据主订单及请求参数构建主订单的商品退款模型.
		List<RefundApplyMerchModel> refundMerches = refundApplyMerchBuilder.generateRefundApplyMerchModel(reqModel, saleOrder);
		final String refundId = UUID.randomUUID().toString().replace("-", "");
		final Date refundApplyTime = new Date();
		//2. 分装退款申请实体
		RefundApplyEntity refundApply = refundApplyEntityBuilder.generateRefundApplyEntity(reqModel, refundId, saleOrder,
				refundMerches, refundApplyTime);
		RefundApplyInfoEntity refundApplyInfo = refundOperInfoEntityBuilder.generateRefundApplyInfoEntity(reqModel, refundId);

		//3. 获取产品对应的退款金额信息,将金额进行放置于refundMerches集合的实体中
		refundRuleLimitConverter.assembleRuleLimit(refundMerches, refundApply, saleOrder, refundApplyTime, reqModel
				.getRefundType().intValue());
		initRefundState(refundApply);
		//4.获取本次退款所关联的退款商品政策数据 
		List<RefundMerchStrategyModel> merchStrategies = getMerchStrategys(saleOrder.getTransaction_id(), refundMerches);
		//5.构建商品的退款申请流水信息
		List<RefundFlowEntity> refundflows = refundFlowEntityBuilder.generateRefundFlowEntity(refundId, refundMerches,
				refundApply, merchStrategies);
		//6.获取商品信息中有关退款的数量与金额
		List<MerchRefundModel> merchUpdateModels = refundMerchUpdateBuilder.generateRefundMerchModels(refundMerches, reqModel
				.getRefundType().intValue());
		//7.订单信息中有关退款的数量与金额
		List<OrderRefundModel> orderUpdateModels = orderUpdateModelBuilder.generateRefundOrderModels(refundMerches,
				merchStrategies, saleOrder.getOrder_status(), reqModel.getRefundType().intValue());
		//8.订单信息中有关退款的数量与金额
		RefundOperLog refundOperLog = refundOperLogEntityBuilder.generateRefundApplyOperLogEntity(reqModel, refundId,
				refundApply, saleOrder);
		//9.保存数据信息
		refundBussnisCommit(refundApply, refundApplyInfo, refundflows, merchUpdateModels, orderUpdateModels, refundOperLog);
		PaymentResponse paymentResponse = null;
		try {
			paymentResponse = saasAccountRefundFrozenHandler.applyAccountFrozen(reqModel, saleOrder, refundApply,
					merchStrategies, refundMerches);
			if (!Check.NuNObject(paymentResponse)) {
				FreezeFlowEntity freezeflow = refundFreezeFlowBuilder.generateRefundFreezeFlow(refundId, saleOrder,
						paymentResponse);
				freezeFlowWriteMapper.insertFreezeFlow(freezeflow);
			}
			//退款的状态为对接审核中,调用对接审核服务
			if (refundApply.getRefundAuditState() == RefundFlowAuditStateEnum.DOCK_AUDITING.getState()) {
				callDockRefund(refundId, refundApply);
				initRefundState(refundApply);
			}
			if (refundApply.getRefundAuditState() == RefundFlowAuditStateEnum.FINISHED.getState()) {
				refundApply.setRefundState(RefundFlowStateEnum.SUCCESS.getState());
				saasRefundFinishHandler.doFinishHandler(refundApply, saleOrder.getOrder_id());
			} else if (refundApply.getRefundAuditState() == RefundFlowAuditStateEnum.REFUSED.getState()) {
				refundApply.setRefundState(RefundFlowStateEnum.FAIL.getState());
				saasRefundRefusedHandler.doRefusedHandler(refundApply, saleOrder);
			} else {
				if (refundApply.getIsForce() == RefundApplyTypeEnum.FORCE.getType()) {
					RefundOperMQLogModel refundOperModel = new RefundOperMQLogModel();
					refundOperModel.setRefundId(refundId);
					refundOperModel.setRootOrderId(reqModel.getOrderId());
					refundOperModel.setOperationId(reqModel.getInitiatorId());
					refundOperModel.setEvent(RefundLogEventEnum.FORCE_REFUND_APPLY);
					refundOperLogMQHandler.sendOperLog(refundOperModel);
				}
			}
			refundApplyWriteMapper.updateRefundApplyRelationStatue(refundApply.getRefundId(), refundApply.getRefundState(),
					refundApply.getRefundAuditState());
			logger.info("退款申请处理完毕:{}.", refundId);
			//返回退款申请操作对应的申请类型
			RefundApplyResultModel result = new RefundApplyResultModel();
			result.setAuditState(refundApply.getRefundAuditState());
			result.setRefundId(refundId);
			return result;
		} catch (RuntimeException e) {
			logger.error("调用或许流程信息异常,e:", e);
			if (!Check.NuNObject(paymentResponse)) {
				sassAccountRefundCancelHandler.cancelApplyAccountFrozen(refundId);
			}
			throw e;
		}
	}

	private List<RefundMerchStrategyModel> getMerchStrategys(String transactionId, List<RefundApplyMerchModel> refundMerches) {
		List<String> merchIds = new ArrayList<String>();
		for (RefundApplyMerchModel model : refundMerches) {
			merchIds.add(model.getMerchId());
		}
		List<RefundMerchStrategyModel> models = orderStrategyReadMapper.getRefundMerchStrategys(transactionId, merchIds);
		Map<String, RefundMerchStrategyModel> strageyModelCache = new HashMap<String, RefundMerchStrategyModel>();
		for (RefundMerchStrategyModel model : models) {
			strageyModelCache.put(model.getOrderId() + "-" + model.getMerchId(), model);
		}
		for (RefundMerchStrategyModel model : models) {
			if (1 == model.getOrderLevel()) {
				continue;
			}
			for (RefundMerchStrategyModel pmodel : models) {
				if (pmodel.getOrderId().equals(pmodel.getTransactionId())) {
					continue;
				}
				if (pmodel.getPorderId().equals(model.getOrderId())) {
					model.setParentMerchStrategyModel(strageyModelCache.get(pmodel.getOrderId() + "-" + model.getMerchId()));
					break;
				}
			}
		}
		return models;
	}

	protected void callDockRefund(final String refundId, RefundApplyEntity refundApply) {
		int dockAuditState = saasRefundCallDockHandler.callDockRefund(refundId);
		refundApply.setRefundAuditState(dockAuditState);
	}

	protected void initRefundState(final RefundApplyEntity refundApply) {
		if (refundApply.getRefundAuditState() == RefundFlowAuditStateEnum.FINISHED.getState()) {
			refundApply.setRefundState(RefundFlowStateEnum.SUCCESS.getState());
		} else if (refundApply.getRefundAuditState() == RefundFlowAuditStateEnum.REFUSED.getState()) {
			refundApply.setRefundState(RefundFlowStateEnum.FAIL.getState());
		} else {
			refundApply.setRefundState(RefundFlowStateEnum.REFUNDING.getState());
		}
	}

	private void refundBussnisCommit(final RefundApplyEntity applyEntity, final RefundApplyInfoEntity applyInfo,
			final List<RefundFlowEntity> refundFlows, final List<MerchRefundModel> merchUpdateModels,
			final List<OrderRefundModel> orderUpdateModels, final RefundOperLog refundOperLog) {
		refundApplyWriteMapper.addRefundApply(applyEntity);
		refundApplyInfoWriteMapper.addRefundRefuseInfo(applyInfo);

		merchRefundFlowWriteMapper.insertOrderMerchRefundFlow(refundFlows);
		refundOperLogWriteMapper.addRefundOperLog(refundOperLog);

		orderWriteMapper.updateOrderOfRefund(orderUpdateModels);
		merchWriteMapper.updateMerchesOfRefund(merchUpdateModels);
	}
}
