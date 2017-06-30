package com.pzj.voucher.common;

import java.io.Serializable;

public class ExecuteResult<T> implements Serializable {

	/**  */
	private static final long serialVersionUID = 2878102504359393426L;
	/** 状态码  */
	/** 成功 */
	public static final Integer SUCCESS = 10000; // 成功
	/** 程序异常 */
	public static final Integer EXCEPTION = 10001; //程序异常
	/** 数量不足 */
	public static final Integer INSUFFICIENT = 10002; //数量不足
	/** 无效凭证 */
	public static final Integer INVALIDVOUCHER = 10003; //无效凭证
	/** 没有发现凭证 */
	public static final Integer NOTFOUNDVOUCHER = 10004; //没有发现凭证
	/** 不可用凭证 */
	public static final Integer DISABLED = 10005; //不可用凭证
	/** 执行失败 */
	public static final Integer FAIL = -10000; //执行失败

	/** 执行结果状态 */
	private Integer stateCode;

	/** 错误信息 */
	private String message;

	/** 执行成功返回值  */
	private T data;

	/** 异常信息  */
	private Exception exception;

	public ExecuteResult() {
	}

	public ExecuteResult(Integer stateCode, String message) {
		this.stateCode = stateCode;
		this.message = message;
	}

	public ExecuteResult(Integer stateCode, String message, T data) {
		this.stateCode = stateCode;
		this.message = message;
		this.data = data;
	}

	public Integer getStateCode() {
		return stateCode;
	}

	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	@Override
	public String toString() {
		return "ExecuteResult [stateCode=" + stateCode + ", message=" + message + "]";
	}

}
