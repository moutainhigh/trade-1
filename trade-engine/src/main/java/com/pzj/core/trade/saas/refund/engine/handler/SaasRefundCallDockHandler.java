package com.pzj.core.trade.saas.refund.engine.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.IsPartyRefundEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.exception.DockConfirmRefundException;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.write.RefundApplyWriteMapper;
import com.pzj.dock.supplier.service.OrderDockingService;
import com.pzj.dock.supplier.vo.OrderDetail;
import com.pzj.dock.supplier.vo.PlaceOrder;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.merch.write.MerchRefundFlowWriteMapper;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;

@Component
public class SaasRefundCallDockHandler {
	private static final Logger logger = LoggerFactory.getLogger(SaasRefundCallDockHandler.class);

	@Autowired
	private OrderWriteMapper orderWriteMapper;
	@Autowired
	private RefundApplyWriteMapper refundApplyWriteMapper;
	@Autowired
	private MerchRefundFlowWriteMapper merchRefundFlowWriteMapper;
	@Autowired
	private MerchWriteMapper merchWriteMapper;

	@Autowired
	private OrderDockingService orderDockingService;

	public int callDockRefund(final String refundId) {
		final RefundApplyEntity refundapply = refundApplyWriteMapper.getRefundApplyEntityByRefundId(refundId);
		final OrderEntity initialSupplierOrder = orderWriteMapper.getInitialSupplierOrderByTransactionId(refundapply
				.getTransactionId());
		final List<RefundFlowEntity> refundflows = merchRefundFlowWriteMapper.getOrderMerchRefund(
				initialSupplierOrder.getOrder_id(), refundId);
		final PlaceOrder dockAuditParam = buildDockConfirmParam(refundflows, refundapply, initialSupplierOrder);
		return callDock(dockAuditParam);
	}

	/**
	 * 构建对接请求参数
	 *
	 * @param refundFlows 退款流水
	 * @param refundId 退款ID
	 * @return 对接请求参数
	 */
	private PlaceOrder buildDockConfirmParam(final List<RefundFlowEntity> refundFlows, final RefundApplyEntity refundApply,
			final OrderEntity initialSupplierOrder) {

		PlaceOrder placeorder = new PlaceOrder();
		placeorder = new PlaceOrder();
		placeorder.setFlowId(refundApply.getRefundId());
		placeorder.setOrderNo(initialSupplierOrder.getOrder_id());
		placeorder.setSupplierId(initialSupplierOrder.getSupplier_id());
		placeorder.setDetails(new ArrayList<OrderDetail>());
		placeorder.setAllRefund(refundApply.getIsParty() == IsPartyRefundEnum.totalRefund.getKey() ? true : false);
		for (final RefundFlowEntity refundFlow : refundFlows) {
			final MerchEntity purchaseMerch = merchWriteMapper.getMerchByMerchId(refundFlow.getMerch_id());
			final OrderDetail detail = new OrderDetail();
			detail.setCommodityID(purchaseMerch.getMerch_id());
			detail.setVoucherId(purchaseMerch.getVoucher_id());
			detail.setNumber(refundFlow.getRefund_num());
			placeorder.getDetails().add(detail);
		}
		return placeorder;
	}

	private int callDock(final PlaceOrder dockAuditParam) {
		logger.info("call dock refund confirm,reqModel:{}." + JSONConverter.toJson(dockAuditParam));
		Result<Integer> result = null;
		try {
			result = orderDockingService.refundOrder(dockAuditParam);
			logger.info("call dock refund confirm result ,reqModel:{},result:{}.", JSONConverter.toJson(dockAuditParam), result);
			if (!result.isOk() || Check.NuNObject(result.getData())) {
				throw new DockConfirmRefundException(RefundErrorCode.DOCK_CONFIRM_REFUND_ERROR_CODE, "调用对接系统异常");
			}
		} catch (final Throwable t) {
			logger.error("call dock refund confirm exception,reqModel:" + JSONConverter.toJson(dockAuditParam), t);
			throw new DockConfirmRefundException(RefundErrorCode.DOCK_CONFIRM_REFUND_ERROR_CODE, "调用对接系统异常");
		}
		return result.getData();
	}
}
