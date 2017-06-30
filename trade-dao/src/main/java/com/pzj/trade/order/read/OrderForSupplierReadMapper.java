package com.pzj.trade.order.read;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.export.entity.SaaSOrderExportEntity;
import com.pzj.core.trade.query.entity.SupplierOrdersInModel;
import com.pzj.core.trade.query.entity.SupplierOrdersQueryModel;
import com.pzj.trade.merch.entity.MerchListEntity;
import com.pzj.trade.merch.entity.SupplierOrderListEntity;
import com.pzj.trade.order.entity.OrderCountEntity;
import com.pzj.trade.order.entity.OrderEntity;

public interface OrderForSupplierReadMapper {

	List<SupplierOrderListEntity> queryOrderByCondition(@Param(value = "param") SupplierOrdersInModel inModel, @Param(value = "page_index") int page_index,
			@Param(value = "page_size") int page_size);

	OrderCountEntity querySupplierOrderCount(SupplierOrdersInModel inModel);

	/**
	 * 
	 * @param order_id
	 * @param supplier_id
	 * @return
	 */
	OrderEntity queryOrderDetailByOrderId(@Param(value = "order_id") String order_id, @Param(value = "supplier_id") long supplier_id,
			@Param(value = "transaction_id") String transaction_id);

	List<MerchListEntity> querySaaSOrderByCondition(@Param(value = "param") SupplierOrdersQueryModel inModel, @Param(value = "page_index") int page_index,
			@Param(value = "page_size") int page_size);

	OrderCountEntity querySaaSSupplierOrderCount(SupplierOrdersQueryModel inModel);

	OrderCountEntity queryOrderAmountSupplierId(@Param(value = "supplier_id") long supplier_id);

	ArrayList<SaaSOrderExportEntity> queryOrderExports(SupplierOrdersQueryModel inModel);

}
