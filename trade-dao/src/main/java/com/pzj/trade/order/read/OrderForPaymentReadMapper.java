package com.pzj.trade.order.read;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.order.entity.OrderEntity;

public interface OrderForPaymentReadMapper {
	List<OrderEntity> queryChildOrderNotPaymentForCance(@Param("cancelTime")Date cancelTime,@Param("lastDate")Date lastDate);
}
