package com.pzj.core.trade.refund.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.order.entity.OrderRefundEntity;

public interface RefundFlowReadMapper {

	/**
	 * 获取订单的退款信息
	 *
	 * @param orderId
	 *            订单ID
	 * @param orderId
	 *            商品ID
	 * @param orderId
	 *            订单类型
	 * @return
	 */
	List<RefundFlowEntity> getRefundFlows(@Param("order_id") String orderId, @Param("merch_id") String merch_id, @Param("refund_type") int refund_type);

	List<RefundFlowEntity> getRefundFlowsByOrderId(@Param("order_id") String orderId, @Param("merch_id") String merch_id);

	/**
	 * 根据退款单号查询对应的流水.
	 * @param refundId
	 */
	List<RefundFlowEntity> queryRefundFlowByRefundId(String refundId);

	List<OrderRefundEntity> getOrderRefundInfo(@Param("refundId") String refundId);

}
