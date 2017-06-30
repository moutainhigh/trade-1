package com.pzj.core.trade.refund.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.refund.engine.TradeCleanEngine;
import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundAuditPartyEnum;
import com.pzj.core.trade.refund.engine.common.RefundAuditResultEnum;
import com.pzj.core.trade.refund.engine.common.RefundInitPartyEnum;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.write.RefundApplyWriteMapper;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.order.vo.RefundMerchandiseVO;
import com.pzj.trade.order.vo.RefundReqVo;
import com.pzj.trade.payment.read.FreezeFlowReadMapper;
import com.pzj.trade.payment.write.FreezeFlowWriteMapper;
import com.pzj.trade.refund.model.RefundApplyReqModel;
import com.pzj.trade.refund.model.RefundAuditReqModel;
import com.pzj.trade.refund.service.RefundApplyService;
import com.pzj.trade.refund.service.RefundAuditService;
import com.pzj.trade.refund.service.RefundService;

@Service("refundService")
public class RefundServiceImpl implements RefundService {

	@Resource(name = "freezeFlowReadMapper")
	private FreezeFlowReadMapper freezeFlowReadMapper;

	@Resource(name = "freezeFlowWriteMapper")
	private FreezeFlowWriteMapper freezeFlowWriteMapper;

	@Autowired
	private RefundApplyWriteMapper refundApplyWriteMapper;

	@Autowired
	private RefundAuditService refundAuditService;

	@Autowired
	private RefundApplyService refundApplyService;

	@Resource(name = "tradeCleanEngine")
	private TradeCleanEngine tradeCleanEngine;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Result<Boolean> refundMoney(final String orderId, final ServiceContext context) {

		final RefundApplyReqModel reqModel = new RefundApplyReqModel();
		reqModel.setOrderId(orderId);
		reqModel.setInitParty(RefundInitPartyEnum.GENERAL.getParty());
		reqModel.setRefundType(RefundApplyTypeEnum.GENERAL.getType());
		reqModel.setInitiatorId(888888888L);
		return refundApplyService.refundApply(reqModel, context);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Result<Boolean> refundMoney(final String orderId, final List<RefundMerchandiseVO> refundMerchandiseList,
			final ServiceContext context) {

		final RefundApplyReqModel reqModel = new RefundApplyReqModel();
		reqModel.setOrderId(orderId);
		reqModel.setInitParty(RefundInitPartyEnum.GENERAL.getParty());
		reqModel.setRefundType(RefundApplyTypeEnum.GENERAL.getType());
		reqModel.setInitiatorId(888888888L);
		reqModel.setRefundMerches(refundMerchandiseList);
		return refundApplyService.refundApply(reqModel, context);
	}

	/**
	 * 退款审核确认
	 *
	 * @param refundId
	 * @return
	 */
	@Override
	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 4)
	public Result<Boolean> refundConfirm(final RefundReqVo vo, final ServiceContext context) {

		final RefundAuditReqModel refundAuditReqModel = new RefundAuditReqModel();
		RefundApplyEntity refundApply = refundApplyWriteMapper.getRefundApplyEntityByRefundId(vo.getRefundId());
		refundAuditReqModel.setAuditorParty(RefundAuditPartyEnum.DOCK.getParty());
		refundAuditReqModel.setAuditorId(5656565656L);
		refundAuditReqModel.setAuditResult(RefundAuditResultEnum.PASS.getResult());
		refundAuditReqModel.setRefundId(vo.getRefundId());
		refundAuditReqModel.setSaleOrderId(refundApply.getTransactionId());
		return refundAuditService.refundAudit(refundAuditReqModel, context);

	}

	/**
	 * 退款业务回滚
	 *
	 * @param refundId
	 * @return
	 */
	@Override
	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 4)
	public Result<Boolean> refundRollback(final RefundReqVo vo, final ServiceContext context) {

		final RefundAuditReqModel refundAuditReqModel = new RefundAuditReqModel();
		RefundApplyEntity refundApply = refundApplyWriteMapper.getRefundApplyEntityByRefundId(vo.getRefundId());
		refundAuditReqModel.setAuditorParty(RefundAuditPartyEnum.DOCK.getParty());
		refundAuditReqModel.setAuditorId(56565656L);
		refundAuditReqModel.setAuditResult(RefundAuditResultEnum.REFUSE.getResult());
		refundAuditReqModel.setSaleOrderId(refundApply.getTransactionId());
		refundAuditReqModel.setRefundId(vo.getRefundId());

		return refundAuditService.refundAudit(refundAuditReqModel, context);

	}

	@Override
	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 4)
	public Result<Boolean> refundConsumerMerch(final String orderId, final List<RefundMerchandiseVO> refundMerchandiseList,
			final ServiceContext context) {

		final RefundApplyReqModel reqModel = new RefundApplyReqModel();
		reqModel.setOrderId(orderId);
		reqModel.setInitParty(RefundInitPartyEnum.SUPPORT.getParty());
		reqModel.setRefundType(RefundApplyTypeEnum.GENERAL.getType());
		reqModel.setInitiatorId(888888888L);
		reqModel.setRefundMerches(refundMerchandiseList);
		return refundApplyService.refundApply(reqModel, context);

	}

}
