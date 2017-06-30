package com.pzj.core.trade.refund.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundFlowAuditStateEnum;
import com.pzj.core.trade.refund.engine.common.RefundLogEventEnum;
import com.pzj.core.trade.refund.engine.exception.RefundException;
import com.pzj.core.trade.refund.engine.handler.DockRefundConfirmHandler;
import com.pzj.core.trade.refund.engine.handler.JudgeWetherPartRefundHandler;
import com.pzj.core.trade.refund.engine.handler.RefundFlowHandler;
import com.pzj.core.trade.refund.engine.handler.RefundFlowsCommitHandler;
import com.pzj.core.trade.refund.engine.handler.RefundOperLogMQHandler;
import com.pzj.core.trade.refund.engine.handler.RefundRuleLimitHandler;
import com.pzj.core.trade.refund.engine.handler.SettlRefundCancleHandler;
import com.pzj.core.trade.refund.engine.handler.SettlRefundFrozenHandler;
import com.pzj.core.trade.refund.engine.model.RefundApplyResultModel;
import com.pzj.core.trade.refund.engine.model.RefundMerchModel;
import com.pzj.core.trade.refund.engine.model.RefundOperMQLogModel;
import com.pzj.core.trade.refund.engine.validator.RefundLegitimacyValidator;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.entity.RefundMerchRequiredEntity;
import com.pzj.framework.exception.ServiceException;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.mq.RefundMQMSgConverter;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.refund.model.RefundApplyReqModel;

/**
 * 退款申请执行引擎, 负责申请退款业务逻辑处理.
 *
 * @author DongChunfu
 * @date 2016年12月15日
 */
@Component(value = "refundApplyEngine")
public class RefundApplyEngine {

	private static final Logger logger = LoggerFactory.getLogger(RefundApplyEngine.class);

	@Resource(name = "refundLegitimacyValidator")
	private RefundLegitimacyValidator refundLegitimacyValidator;

	@Autowired
	private MerchReadMapper merchReadMapper;

	@Resource(name = "refundRuleLimitHandler")
	private RefundRuleLimitHandler refundRuleLimitHandler;

	@Resource(name = "refundFlowHandler")
	private RefundFlowHandler refundFlowHandler;

	@Resource(name = "judgeWetherPartRefundHandler")
	private JudgeWetherPartRefundHandler judgeWetherPartRefundHandler;

	@Resource(name = "refundFlowsCommitHandler")
	private RefundFlowsCommitHandler refundFlowsCommitHandler;

	@Resource(name = "settlRefundFrozenHandler")
	private SettlRefundFrozenHandler settlRefundFrozenHandler;

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Resource(name = "dockRefundConfirmHandler")
	private DockRefundConfirmHandler dockRefundConfirmHandler;

	@Resource(name = "addRefundApplyEngine")
	private AddRefundApplyEngine addRefundApplyEngine;

	@Resource(name = "settlRefundCancleHandler")
	private SettlRefundCancleHandler settlRefundCancleHandler;

	@Resource(name = "refundOperLogEngine")
	private RefundOperLogEngine refundOperLogEngine;

	@Resource(name = "refundMQMSgConverter")
	private RefundMQMSgConverter refundMQMSgConverter;

	@Autowired
	private RefundOperLogMQHandler refundOperLogMQHandler;

	/**
	 * 根据销售订单ID查询订单的所有销售商品信息.
	 *
	 * @param sellOrderId
	 *            销售订单ID
	 * @return List 销售商品集
	 */
	public List<RefundMerchRequiredEntity> queryRefundRequiredEntitiesBySellOrderId(final String sellOrderId) {
		return merchReadMapper.getSellMerchesOfSellOrder(sellOrderId);
	}

	/**
	 * 退款申请处理器
	 *
	 * @param refundMerches
	 *            退款商品集
	 * @param reqModel
	 *            退款请求参数
	 * @return Map 含退款ID,退款审核状态信息
	 */
	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED)
	public RefundApplyResultModel doHandler(final RefundMerchModel[] refundMerches, final RefundApplyReqModel reqModel) {

		final RefundApplyResultModel result = new RefundApplyResultModel();

		final Integer isForce = reqModel.getRefundType();

		final String orderId = reqModel.getOrderId();
		final OrderEntity sellOrder = orderWriteMapper.getOrderEntityForUpdate(orderId);

		final Integer initParty = reqModel.getInitParty();

		// 退款类型 0 普通退款 1强制退款 3消费退款
		final int refundType = refundLegitimacyValidator.convert(refundMerches, initParty, sellOrder, isForce);

		final int isParty = judgeWetherPartRefundHandler.judge(sellOrder, refundMerches);

		// 对象传引用接收审核状态
		final Map<String, Integer> auditState = new HashMap<>();
		auditState.put("currentState", RefundFlowAuditStateEnum.FINISHED.getState());

		final List<RefundFlowEntity> refundFlows = refundFlowHandler.makeFlow(refundMerches, isParty, initParty, sellOrder,
				auditState, isForce);
		final String refundId = refundFlows.get(0).getRefund_id();
		result.setRefundId(refundId);
		settlRefundFrozenHandler.handler(refundFlows, refundId, reqModel, sellOrder, refundMerches);
		RefundApplyEntity refundApply;
		try {

			refundFlowsCommitHandler.commite(refundFlows, sellOrder, refundType);

			refundApply = addRefundApplyEngine.add(reqModel, refundId, isParty, auditState.get("currentState"));

			refundOperLogEngine.addRefundOperLog(reqModel, auditState.get("currentState"), auditState.get("currentState"),
					refundId);
			result.setAuditState(auditState.get("currentState"));

		} catch (final Throwable t) {

			settlRefundCancleHandler.handler(sellOrder, isForce, refundId);

			logger.error("退款申请处理失败,reqModel: " + reqModel, t);

			if (!(t instanceof RefundException)) {
				throw new ServiceException(t.getMessage(), t);
			}
			throw t;//后续转换处理
		}

		if (logger.isInfoEnabled()) {
			logger.info("退款申请处理成功:{}.", refundId);
		}

		if (reqModel.getRefundType() == RefundApplyTypeEnum.FORCE.getType()) {
			RefundOperMQLogModel model = new RefundOperMQLogModel();
			model.setRefundId(refundId);
			model.setRootOrderId(orderId);
			model.setEvent(RefundLogEventEnum.FORCE_REFUND_APPLY);
			model.setOperationId(reqModel.getInitiatorId());
			refundOperLogMQHandler.sendOperLog(model);
		}
		return result;
	}
}
