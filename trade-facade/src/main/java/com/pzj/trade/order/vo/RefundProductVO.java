package com.pzj.trade.order.vo;

import java.io.Serializable;

public class RefundProductVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4094676216877205531L;
	public Integer refundFlag;
	public Double refundMoney;
	public Integer settlementFlag;
	public Double settlementMoney;
	public Integer getRefundFlag() {
		return refundFlag;
	}
	public void setRefundFlag(Integer refundFlag) {
		this.refundFlag = refundFlag;
	}
	public Double getRefundMoney() {
		return refundMoney;
	}
	public void setRefundMoney(Double refundMoney) {
		this.refundMoney = refundMoney;
	}
	public Integer getSettlementFlag() {
		return settlementFlag;
	}
	public void setSettlementFlag(Integer settlementFlag) {
		this.settlementFlag = settlementFlag;
	}
	public Double getSettlementMoney() {
		return settlementMoney;
	}
	public void setSettlementMoney(Double settlementMoney) {
		this.settlementMoney = settlementMoney;
	}
	
	
}
