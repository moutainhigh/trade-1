package com.pzj.trade.common;
/**
 * 用户类型
 **@author dufangxing
 **@version 2017年5月10日下午2:47:58
 */
public enum UserType {

	SASS(13,"saas");
	private UserType(int type,String desc){
		this.type=type;
		this.desc=desc;
	}
	private int type;
	private String desc;
	
	public int getType(){
		return type;
	}
	public String getDesc(){
		return desc;
	}
}
