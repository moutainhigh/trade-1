package com.pzj.core.trade.clean.engine.model;

public class SkuNormalConsumerModel {

	/**
	 * 核销后清算规则类型
	 */
	private int verificationRuleType;
	/**
	 * 条件核销类型
	 */
	private int fixedVerificationType;
	/**
	 * 核销后固定时间 : 时间数值
	 */
	private int fixedVerificationTime;
	/**
	 * 核销后固定时间：时间单位
	 */
	private int fixedVerificationUnit;

	public int getVerificationRuleType() {
		return verificationRuleType;
	}

	public void setVerificationRuleType(int verificationRuleType) {
		this.verificationRuleType = verificationRuleType;
	}

	public int getFixedVerificationType() {
		return fixedVerificationType;
	}

	public void setFixedVerificationType(int fixedVerificationType) {
		this.fixedVerificationType = fixedVerificationType;
	}

	public int getFixedVerificationTime() {
		return fixedVerificationTime;
	}

	public void setFixedVerificationTime(int fixedVerificationTime) {
		this.fixedVerificationTime = fixedVerificationTime;
	}

	public int getFixedVerificationUnit() {
		return fixedVerificationUnit;
	}

	public void setFixedVerificationUnit(int fixedVerificationUnit) {
		this.fixedVerificationUnit = fixedVerificationUnit;
	}
}
