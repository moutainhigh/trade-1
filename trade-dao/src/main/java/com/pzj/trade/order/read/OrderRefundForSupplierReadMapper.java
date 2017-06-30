package com.pzj.trade.order.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.query.entity.SupplierOrdersQueryModel;
import com.pzj.trade.merch.entity.MerchListEntity;
import com.pzj.trade.order.entity.ForceRefundOrderCountEntity;
import com.pzj.trade.order.entity.OrderCountEntity;

public interface OrderRefundForSupplierReadMapper {

	List<MerchListEntity> querySaaSOrderByCondition(@Param(value = "param") SupplierOrdersQueryModel inModel);

	OrderCountEntity querySaaSSupplierOrderCount(@Param(value = "param") SupplierOrdersQueryModel inModel);

	List<ForceRefundOrderCountEntity> querySaaSSupplierRefundOrderCount(@Param(value = "param") SupplierOrdersQueryModel param);

}
