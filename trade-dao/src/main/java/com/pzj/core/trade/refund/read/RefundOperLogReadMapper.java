package com.pzj.core.trade.refund.read;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.refund.entity.RefundOperLog;

/**
 *
 * @author DongChunfu
 * @date 2016年12月26日
 */
public interface RefundOperLogReadMapper {
	/**
	 * 统计处于当前状态的退款ID的数量,用于判断是哪个方拒绝的
	 *
	 * @param prev
	 * @param later
	 * @param refundId
	 * @return
	 */
	int queryByStatesNum(@Param(value = "prev") int prev, @Param(value = "later") int later,
			@Param(value = "refundId") String refundId);

	/**
	 * 根据各状态查询创建的日期
	 * @param prev
	 * @param later
	 * @param refundId
	 * @return
	 */
	RefundOperLog queryDateByStates(@Param(value = "prev") Integer prev, @Param(value = "later") Integer later,
			@Param(value = "refundId") String refundId);

	/**
	 * 查询申请时间
	 * @param later
	 * @param refundId
	 * @return
	 */
	Date queryApplyDate(@Param(value = "later") Integer later, @Param(value = "refundId") String refundId);

}
