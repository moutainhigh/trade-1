package com.pzj.trade.payment.write;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.payment.entity.FreezeFlowEntity;

public interface FreezeFlowWriteMapper {

	/**
	 * 获取支付记录信息
	 * @param order_id
	 * @param freeze_state
	 * @return
	 */
	FreezeFlowEntity getFreezeFlow(@Param("order_id") String order_id, @Param("receive_type") int receive_type, @Param("freeze_state") int freeze_state);

	/**
	 * 支付信息的唯一验签
	 * @param sign_id
	 * @return
	 */
	FreezeFlowEntity getFreezingFlowBySignIdForRefund(@Param("sign_id") String sign_id);

	/**
	 * 新增订单支付记录信息
	 * @param freezeFlowEntity
	 */
	void insertFreezeFlow(FreezeFlowEntity freezeFlowEntity);

	/**
	* 更新支付记录状态
	* @param order_id
	*/
	void updateFreezeFlowStatus(@Param("order_id") String order_id, @Param("sign_id") String sign_id, @Param("freeze_state") int freeze_state);

	/**
	 * 保存第三方支付返回的相关内容
	 * @param flowId
	 * @param thirdContent
	 */
	void updateFreezeFlowThirdContent(@Param("flowId")long flowId,@Param("thirdContent")String thirdContent);
}
