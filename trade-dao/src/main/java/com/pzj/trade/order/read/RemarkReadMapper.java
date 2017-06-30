package com.pzj.trade.order.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.order.entity.RemarkEntity;
import com.pzj.trade.order.model.OrderRemarksPageReqModel;

public interface RemarkReadMapper {

	/**
	 * 根据订单ID查询相关备注信息.
	 * 
	 * @param orderId
	 * @return
	 */
	List<RemarkEntity> getRemarkByOrderId(String orderId);

	/**
	 * 根据订单ID查询订单备注总条数
	 * @param orderId
	 * @return
	 */
	int queryRemarkPageTotalByOrderId(@Param(value = "orderId") long orderId);

	/**
	 * 根据订单分页查询订单备注信息
	 * @param orderVO
	 * @return
	 */
	List<RemarkEntity> queryRemarkPageByOrderVO(@Param(value = "param") OrderRemarksPageReqModel orderVO,@Param("pageIndex")int pageIndex,@Param("pageSize")int pageSize);

}
