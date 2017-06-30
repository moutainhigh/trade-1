package com.pzj.core.trade.refund.engine.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.common.RefundUserTypeEnum;
import com.pzj.core.trade.refund.engine.exception.DockConfirmRefundException;
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

/**
 * 对接退款审核处理器
 *
 * @author DongChunfu
 * @date 2016年12月15日
 */
@Component(value = "dockRefundConfirmHandler")
public class DockRefundConfirmHandler {

	private static final Logger logger = LoggerFactory.getLogger(DockRefundConfirmHandler.class);

	@Autowired
	private MerchRefundFlowWriteMapper merchRefundFlowWriteMapper;

	@Autowired
	private MerchWriteMapper merchWriteMapper;

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private OrderDockingService orderDockingService;

	public int confirm(final String refundId) {

		final List<RefundFlowEntity> refundFlows = merchRefundFlowWriteMapper.queryRefundFlowsByRefundId(refundId);

		return callDock(refundFlows, refundId);

	}

	private int callDock(final List<RefundFlowEntity> refundFlows, final String refundId) {

		final PlaceOrder param = buildDockConfirmParam(refundFlows, refundId);
		logger.info("call dock refund confirm,reqModel:{}." + JSONConverter.toJson(param));
		Result<Integer> result = null;
		try {
			result = orderDockingService.refundOrder(param);
			logger.info("call dock refund confirm result ,reqModel:{},result:{}.", JSONConverter.toJson(param), result);
			if (!result.isOk() || Check.NuNObject(result.getData())) {
				throw new DockConfirmRefundException(RefundErrorCode.DOCK_CONFIRM_REFUND_ERROR_CODE, "调用对接系统异常");
			}
		} catch (final Throwable t) {
			logger.error("call dock refund confirm exception,reqModel:" + JSONConverter.toJson(param), t);
			throw new DockConfirmRefundException(RefundErrorCode.DOCK_CONFIRM_REFUND_ERROR_CODE, t.getMessage(), t);
		}

		return result.getData();
	}

	/**
	 * 构建对接请求参数
	 *
	 * @param refundFlows 退款流水
	 * @param refundId 退款ID
	 * @return 对接请求参数
	 */
	private PlaceOrder buildDockConfirmParam(final List<RefundFlowEntity> refundFlows, final String refundId) {

		final List<RefundFlowEntity> resellerRefundFlows = RefundFlowEntity.filterFlows(refundFlows,
				RefundUserTypeEnum.resellerRefund.getKey());

		OrderEntity purchaseOrder = orderWriteMapper.getOrderEntityNotLock(resellerRefundFlows.get(0).getOrder_id());
		PlaceOrder placeorder = new PlaceOrder();
		placeorder.setFlowId(refundId);
		placeorder.setOrderNo(purchaseOrder.getOrder_id());
		placeorder.setSupplierId(purchaseOrder.getSupplier_id());
		placeorder.setDetails(new ArrayList<OrderDetail>());
		for (final RefundFlowEntity refundFlow : resellerRefundFlows) {
			final MerchEntity purchaseMerch = merchWriteMapper.getPurchaseMerchBySellMerchId(refundFlow.getMerch_id());

			final OrderDetail detail = new OrderDetail();
			detail.setCommodityID(purchaseMerch.getMerch_id());
			detail.setVoucherId(purchaseMerch.getVoucher_id());
			detail.setNumber(refundFlow.getRefund_num());
			placeorder.getDetails().add(detail);
		}
		return placeorder;
	}
}
