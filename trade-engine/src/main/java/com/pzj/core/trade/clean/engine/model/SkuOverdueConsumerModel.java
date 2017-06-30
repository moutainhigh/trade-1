package com.pzj.core.trade.clean.engine.model;

public class SkuOverdueConsumerModel {

	/**
	 * 逾期清算规则日期类型
	 */
	private int overdueVerificationType;
	/**
	 * 逾期清算规则日期
	 */
	private int overdueVerificationTime;
	/**
	 * 逾期清算规则金额类型
	 */
	private int overdueVerificationFeetype;
	/**
	 * 逾期清算规则金额
	 */
	private double overdueVerificationFeevalue;

	public int getOverdueVerificationType() {
		return overdueVerificationType;
	}

	public void setOverdueVerificationType(int overdueVerificationType) {
		this.overdueVerificationType = overdueVerificationType;
	}

	public int getOverdueVerificationTime() {
		return overdueVerificationTime;
	}

	public void setOverdueVerificationTime(int overdueVerificationTime) {
		this.overdueVerificationTime = overdueVerificationTime;
	}

	public int getOverdueVerificationFeetype() {
		return overdueVerificationFeetype;
	}

	public void setOverdueVerificationFeetype(int overdueVerificationFeetype) {
		this.overdueVerificationFeetype = overdueVerificationFeetype;
	}

	public double getOverdueVerificationFeevalue() {
		return overdueVerificationFeevalue;
	}

	public void setOverdueVerificationFeevalue(double overdueVerificationFeevalue) {
		this.overdueVerificationFeevalue = overdueVerificationFeevalue;
	}
}
