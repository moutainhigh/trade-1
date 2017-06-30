package com.pzj.trade.payment.common;

/**
 * 资金账户流水的业务类型
 * @author kangzl
 *
 */
public enum ACCOUNT_BIZFLOW_TYPE {
	Pay(1,"付款"),
	Refund(2,"退款"),
	TradeService(3,"交易服务");
	private ACCOUNT_BIZFLOW_TYPE(int key,String msg){
		this.key=key;
		this.msg=msg;
	}
	private int key;
	private String msg;
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
