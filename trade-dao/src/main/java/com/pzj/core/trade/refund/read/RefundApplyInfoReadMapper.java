package com.pzj.core.trade.refund.read;

/**
 * 退款附加信息
 * 
 * @author DongChunfu
 * @date 2016年12月23日
 */
public interface RefundApplyInfoReadMapper {
	/**
	 * 根据退款ID查询退款拒绝原因
	 * 
	 * @param refundId
	 *            退款ID
	 * @return 拒绝原因
	 */
	public String queryReasonByRefundId(String refundId);
}
