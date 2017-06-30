package com.pzj.core.trade.export.engine.exception;

/**
 * 订单导出请求参数异常
 *
 * @author DongChunfu
 * @date 2017年2月9日
 */
public class OrderExportParamException extends OrderExportException {

	public OrderExportParamException(final int errCode, final String message) {
		super(errCode, message);
	}

	public OrderExportParamException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}

	private static final long serialVersionUID = 1L;

}
