/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.refund.write;

import com.pzj.core.trade.refund.entity.RefundOperLog;

/**
 * 
 * @author DongChunfu
 * @version $Id: RefundOperLogWriteMapper.java, v 0.1 2016年11月30日 下午6:16:33 dongchunfu Exp $
 */
public interface RefundOperLogWriteMapper {
	/**
	 * 新增退款操作日志
	 * @param refundOperLog
	 */
	void addRefundOperLog(RefundOperLog refundOperLog);
}
