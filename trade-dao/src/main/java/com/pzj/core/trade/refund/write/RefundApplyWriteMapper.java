package com.pzj.core.trade.refund.write;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.refund.entity.RefundApplyEntity;

/**
 * 退款申请写操作相关接口
 * 
 * @author DongChunfu
 * @date 2016年12月23日
 */
public interface RefundApplyWriteMapper {

	public void addRefundApply(RefundApplyEntity refundApply);

	public RefundApplyEntity getRefundApplyEntityByRefundId(@Param(value = "refundId") String refundId);

	public void updateRefundApplyRelationStatue(@Param("refundId") String refundId,
			@Param("refundState") Integer refundState, @Param("auditState") Integer auditState);
}
