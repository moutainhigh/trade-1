package com.pzj.trade.order.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.query.entity.AccountOrdersOrdersQueryModel;
import com.pzj.trade.order.entity.AccountOrdersEntity;

/**
 * 
 * @author GLG
 *
 */
public interface OrderForAccountReadMapper {

	/**
	 * 售票员分页查询订单.
	 * @param inModel
	 * @param page_index
	 * @param page_size
	 * @return
	 */
	List<AccountOrdersEntity> queryOrders(@Param(value = "param") AccountOrdersOrdersQueryModel inModel);

}
