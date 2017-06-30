package com.pzj.trade.order.read;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.export.entity.SaaSOrderExportEntity;
import com.pzj.core.trade.query.entity.ResellerOrdersQueryModel;
import com.pzj.trade.merch.entity.MerchListEntity;
import com.pzj.trade.order.entity.OrderCountEntity;
import com.pzj.trade.order.entity.OrderEntity;

public interface OrderForResellerReadMapper {

	/**
	 * 通用的根据参数查询订单数量.
	 * 
	 * @param param
	 * @return
	 */
	OrderCountEntity getOrderCountByCondition(@Param(value = "param") ResellerOrdersQueryModel param);

	List<MerchListEntity> getOrderByCondition(@Param(value = "param") ResellerOrdersQueryModel param, @Param(value = "page_index") int page_index,
			@Param(value = "page_size") int page_size);

	public OrderEntity queryOrderDetailByOrderId(@Param(value = "order_id") String transaction_id, @Param(value = "reseller_id") long reseller_id,
			@Param(value = "order_level") int orderLevel, @Param(value = "transaction_id") String transactionId);

	public OrderEntity getSupplierOrdersByResellerOrderId(@Param(value = "transaction_id") String transaction_id, @Param(value = "order_level") int order_level);

	public OrderEntity getResellerOrdersByTransactionId(@Param(value = "transaction_id") String transaction_id);

	public List<OrderEntity> getSupplierOrdersByTransactionIds(@Param(value = "transaction_ids") List<String> transaction_ids,
			@Param(value = "order_level") int order_level);

	public OrderEntity queryResellerOrder(@Param(value = "p_order_id") String p_order_id, @Param(value = "supplier_id") long supplier_id);

	public List<OrderEntity> queryResellerOrders(@Param(value = "p_order_ids") List<String> p_order_ids, @Param(value = "supplier_ids") List<Long> supplier_ids);

	public OrderCountEntity queryOrderAmountReseller(@Param(value = "reseller_id") long reseller_id);

	ArrayList<SaaSOrderExportEntity> queryOrderExports(@Param(value = "param") ResellerOrdersQueryModel inModel);
}
