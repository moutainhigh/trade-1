package com.pzj.trade.merch.write;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.merch.entity.MerchRefundEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;

/**
 * 商品退款记录写库操作.
 *
 * @author YRJ
 *
 */
public interface MerchRefundFlowWriteMapper {

	/**
	 *
	 * 根据订单号及商品退款状态获取到对应的退款记录.
	 *
	 * @param orderId
	 * @param refundState
	 */
	int getRefundAuditingMerchCount(@Param(value = "orderId") String orderId, @Param(value = "refundState") int refundState);

	int getRefundAuditingOfMerch(@Param(value = "orderId") String orderId, @Param("merchId") String merchId,
			@Param(value = "refundState") int refundState);

	/**
	 * 根据指定的商品列表及审核状态值找出对应的审核记录.
	 *
	 * @param merchIds
	 * @param state
	 * @return
	 */

	/**
	 * 获取订单最后一次退款的信息
	 *
	 * @param merchId
	 * @return	 */
	Date getLastRefundOrderMerch(@Param(value = "merchId") String merchId);

	List<MerchRefundEntity> getRefundMerchByState(@Param(value = "merchIds") List<String> merchIds,
			@Param(value = "state") int state);

	RefundFlowEntity getMerchRefundEntityOfMerch(@Param(value = "merchId") String merchId,
			@Param(value = "refundId") String refundId);

	/**
	 * 获取退款的商品信息
	 *
	 * @param orderId
	 *            订单ID
	 * @param refundId
	 *            退款单好
	 * @return
	 */
	List<RefundFlowEntity> getOrderMerchRefund(@Param("orderId") String orderId, @Param("refundId") String refundId);

	/**
	 * 保存退款商品流水信息.
	 *
	 * @param orderEntity
	 */
	void insertOrderMerchRefundFlow(@Param(value = "refundMerchList") List<RefundFlowEntity> refundMerchList);

	/**
	 * 修改退款商品流水信息的状态
	 *
	 * @param orderId
	 *            订单ID
	 * @param refundId
	 *            退款单号
	 * @param refundState
	 *            退款行为状态
	 */
	// void updateOrderMerchRefundFlowStatus(@Param("refundId") String refundId,
	// @Param("refundState") int refundState);

	/**
	 * 修改退款申请的审核状态
	 *
	 * @param orderId
	 * @param refundId
	 * @param refundState
	 */
	// void updateMerchRefundApplyAuditStatue(@Param("refundId") String
	// refundId, @Param("auditState") int auditState);

	/**
	 * 根据退款ID获取退款流水记录
	 *
	 * @param refundId
	 * @return
	 */
	public List<RefundFlowEntity> queryRefundFlowsByRefundId(String refundId);

	/**
	 * 根据退款ID获取相应退款单ID
	 *
	 * @param refundId
	 *            退款ID
	 * @param refundType
	 *            退款类型
	 * @return 订单ID
	 */
	public String queryOrderIdByRefundId(@Param("refundId") String refundId, @Param("refundType") int refundType);

}