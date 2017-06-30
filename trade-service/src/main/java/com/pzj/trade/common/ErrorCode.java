package com.pzj.trade.common;
/**
 * 用户类型
 **@author dufangxing
 **@version 2017年5月10日下午2:47:58
 */
public enum ErrorCode {

	/**
	 * 成功
	 */
	OK(10000,"OK"),
	/**
	 * 用户信息不存在
	 */
	USER_NOT_EXIST(50011,"用户信息不存在"),
	/**
	 * 账号不能为空
	 */
	ACC_NOT_EMPTY(40011,"账号不能为空"),
	/**
	 * 时间不能为空
	 */
	TIME_NOT_EMPTY(40013,"时间不能为空"),
	/**
	 * 参数不能为空
	 */
	PARAM_NOT_NULL(40010,"参数不能为空");
	private ErrorCode(int code,String desc){
		this.code=code;
		this.desc=desc;
	}
	private int code;
	private String desc;
	
	public int getCode(){
		return code;
	}
	public String getDesc(){
		return desc;
	}
}
