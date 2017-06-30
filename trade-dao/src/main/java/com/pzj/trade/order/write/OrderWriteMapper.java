package com.pzj.trade.order.write;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.order.entity.AgentOrderEntity;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.entity.OrderNumEntity;
import com.pzj.trade.order.entity.OrderRefundEditModel;
import com.pzj.trade.order.entity.PurchaseOrderEntity;
import com.pzj.trade.order.entity.RemarkEntity;
import com.pzj.trade.order.entity.TradingOrderEntity;
import com.pzj.trade.order.model.OrderRefundModel;

public interface OrderWriteMapper {

	/**
	 * 获取订单信息加锁
	 *
	 * @param order_id
	 * @return
	 */
	OrderEntity getOrderEntityForUpdate(String order_id);

	/**
	 * 获取订单数量信息
	 * @author DongChunfu
	 *
	 * @param order_id 订单ID
	 * @return 订单数量信息
	 */
	OrderNumEntity getOrderNumEntityForUpdate(String order_id);

	OrderEntity getOrderEntityNotLock(String order_id);

	/**
	 * 获取交易中，初始供应商对应的订单
	 * @param transactionId
	 * @return
	 */
	OrderEntity getInitialSupplierOrderByTransactionId(String transactionId);

	PurchaseOrderEntity getPurchaseOrderInfoNotLock(@Param(value = "purchaseOrderId") String purchaseOrderId);

	OrderEntity getOrderListByPorderId(String p_order_id);

	/**
	 * 根据交易ID查询所有的子订单.
	 * @param transactionId
	 * @return
	 */
	List<OrderEntity> queryAllChildOrdersByTransactionId(String transaction_id);

	/**
	 * 根据订单号查询对应的代下单的订单号.
	 *
	 * @param order_id
	 * @return
	 */
	AgentOrderEntity queryAgentOrderByOrderId(String order_id);

	List<OrderEntity> getOrderListByIds(@Param("orderIds") List<String> orderIds);

	OrderRefundModel getOrderRefundModel(@Param("saleOrderId") String saleOrderId, @Param("refundId") String refundId);

	/**
	 * 通过订单ID更新订单状态
	 *
	 * @param order_id
	 *            订单ID
	 * @param value
	 *            订单目标状态.
	 */
	void updateOrderStatusByOrderId(@Param(value = "order_id") String order_id, @Param(value = "order_status") int order_status);

	/**
	 * 更新主订单的支付方式
	 * @param saleOrderId
	 * @param payWay
	 * @param thirdPayType
	 * @param thirdCode
	 */
	void updateSaleOrderPayway(@Param(value = "saleOrderId") String saleOrderId, @Param(value = "payWay") int payWay,
			@Param(value = "thirdPayType") int thirdPayType, @Param(value = "thirdCode") String thirdCode);

	/**
	 * 通过交易ID更新订单状态.
	 *
	 * @param order_id
	 *            订单ID
	 * @param value
	 *            订单目标状态.
	 */
	void updateOrderStatusByTransactionId(@Param(value = "transaction_id") String transaction_id,
			@Param(value = "order_status") int order_status);

	/**
	 * 更新订单支付状态.
	 * @param order_id
	 */
	void updateOrderStatusForPay(@Param(value = "order_id") String order_id);

	/**
	 * 以消费的商品退款操作,回滚订单信息
	 *
	 * @param transactionId
	 */
	void updateOrderNumForConsumeRollback(@Param(value = "transactionId") String transactionId);

	/**
	 * 更新订单状态和检票数量.
	 *
	 * @param order_id
	 *            待更新的订单ID列表
	 * @param order_status
	 *            订单目标状态.
	 */
	public int updateOrderPayState(@Param(value = "order_id") String order_id, @Param(value = "third_code") String third_code,
			@Param(value = "third_pay_type") int third_pay_type, @Param("pay_way") int payWay,
			@Param("pay_state") int pay_state, @Param("pay_time") Date pay_time);

	public void updateChildOrderMakeUpPayTime(@Param("childOrderId") String childOrderId, @Param("paytime") Date paytime);

	/**
	 * 更新订单退款数量、核销数量、退款金额信息.
	 * @param editModel
	 */
	void updateOrderRefundAmountAndNum(OrderRefundEditModel editModel);

	/**
	 * 更新订单对应的商品数量信息
	 *
	 * @param orderlist
	 */
	void updateOrderOfRefund(@Param("orderModels") List<OrderRefundModel> orderlist);

	void updateOrderStatusOfRefundFinish(@Param("transactionId") String transactionId, @Param("isRefunded") boolean isRefunded);

	/**
	 * 保存订单.
	 *
	 * @param orderEntity
	 */
	void insert(@Param(value = "orders") List<TradingOrderEntity> orders);

	/**
	 * 保存多级订单.
	 *
	 * @param orderEntity
	 */
	void insertMultiOrder(@Param(value = "orders") List<TradingOrderEntity> orders);

	/**
	 * 保存订单备注信息.
	 *
	 * @param remark
	 */
	void insertOrderRemark(RemarkEntity remark);

	/**
	 * 保存订单备注信息列表.
	 *
	 * @param remark
	 */
	void insertOrderRemarks(@Param(value = "remarks") List<RemarkEntity> remark);

	/**
	 * 更新订单二次确认字段.
	 *
	 * @param transactionId
	 * @param ack
	 */
	void updateOrderToConfirmedByTransactionId(@Param(value = "transactionId") String transactionId,
			@Param(value = "ack") int ack);

	/**
	 * 更新代下单的第三方订单号
	 *
	 * @param order_id
	 * @param agent_order_id
	 */
	void updateAgentOrderByOrderId(@Param(value = "order_id") String order_id,
			@Param(value = "agent_order_id") String agent_order_id, @Param(value = "operator_id") long operator_id);

	/**
	 * 在核销的时候更新订单的核销数量与结算价格
	 *
	 * @param order
	 */
	void updateOrderOfConsumer(@Param(value = "orders") List<OrderEntity> orders);

	/**
	 * 新订单核销完毕更新订单状态
	 * @author DongChunfu
	 *
	 * @param newOrder
	 */
	void newOrderConfrimFinishUpdateData(@Param(value = "order") OrderEntity order);

	/**
	 * 新增代下单的第三方订单号
	 *
	 * @param order_id
	 * @param agent_order_id
	 */
	void insertAgentOrder(@Param(value = "param") AgentOrderEntity agentOrderEntity);

	/**
	 * 更新订单的代下单标志
	 */
	void updateAgentOrderFlagByOrderId(@Param(value = "order_id") String order_id, @Param(value = "agent_flag") int agent_flag);

	/**
	 * 更新订单备注
	 */
	void updateOrderRemark(@Param(value = "remark") RemarkEntity remark);

	/**
	 * 查询退款需要的所有订单信息
	 *
	 * @param orderId
	 *            订单ID
	 * @return 订单实体
	 */
	OrderEntity getOrderInfoForRefundById(@Param(value = "orderId") String orderId);

	/**
	 * 获取订单信息加锁
	 *
	 * @param order_id
	 * @return
	 */
	OrderEntity getOrderInfoForRefundAuditLock(@Param(value = "orderId") String orderId);

	/**
	 * 根据子订单ID查询对应的上级订单.
	 * @param orderId
	 * @return
	 */
	OrderEntity getParentOrderByChildId(@Param(value = "order_id") String orderId);
}
