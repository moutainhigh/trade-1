package com.pzj.core.trade.refund.write;

import com.pzj.core.trade.refund.entity.RefundApplyInfoEntity;

/**
 * 退款申请信息写操作
 * 
 * @author DongChunfu
 * @date 2016年12月25日
 */
public interface RefundApplyInfoWriteMapper {
	/**
	 * 新增退款附属信息
	 * 
	 * @param entity
	 */
	void addRefundRefuseInfo(RefundApplyInfoEntity entity);
}
