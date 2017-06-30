package com.pzj.trade.payment.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.payment.entity.FreezeFlowEntity;

public interface FreezeFlowReadMapper {
	/**
	 * 获取支付记录信息
	 *
	 * @param order_id
	 * @param freeze_state
	 * @return
	 */
	FreezeFlowEntity getFreezeFlow(@Param("order_id") String order_id, @Param("receive_type") Integer receive_type,
			@Param("freeze_state") Integer freeze_state);

	/**
	 * 获取支付记录信息
	 *
	 * @param order_id
	 * @param freeze_state
	 * @return
	 */
	FreezeFlowEntity getPayFreezeFlowByOrderId(@Param("order_id") String order_id);

	List<FreezeFlowEntity> getFreezeFlowsByOrderId(@Param("order_id") String order_id);

	/**
	 * 根据退款ID获取流水
	 *
	 * @param refundId
	 *            退款ID
	 * @return
	 */
	FreezeFlowEntity getFreezeFlowByRefundId(String refundId);

}
