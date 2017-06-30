package com.pzj.core.trade.export.engine.exception;

import com.pzj.core.trade.exception.TradeException;

/**
 * 订单导出异常基类
 *
 * @author DongChunfu
 * @date 2017年2月9日
 */
public class OrderExportException extends TradeException {

	public OrderExportException(final int errCode, final String message) {
		super(errCode, message);
	}

	public OrderExportException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}

	private static final long serialVersionUID = 1L;

}
