package com.pzj.core.trade.refund.engine.model;

import java.util.Calendar;
import java.util.Date;

/**
 * 执行退款规则限制结果
 * 
 * @author DongChunfu
 * @date 2016年12月4日
 */
public class RefundRuleLimit {

	/**
	 * 是否允许退款.
	 * <p>对应产品退款规则: 是否允许退款.</p>
	 */
	private int refundAllowed;
	
	/**
	 * 1 随时可退
	 * 2 前时间点的退款
	 * 3 后时间点的退款
	 * 4 无退款规则
	 */
	private int refundRuleType;

	/**
	 * 随时可退.
	 */
	private Integer refundAnyTime;

	/**
	 * 随时可退佣金.
	 */
	private int refundAnyTimeFee;

	/**
	 * 日期前退款的日期类型
	 * 
	 * <li>1: 出游日期</li>
	 * <li>2: 产品有效期</li>
	 */
	private Integer refundDateType;
	
	/**
	 * 随时可退整单退部分退规则
	 * * 
	 * <li>1,整单退</li>
	 * <li>2,部分退</li>
	 */
	private Integer refundAnyTimeQuantityType;

	/**
	 * 退款日期前退款规则:是否可退
	 * 
	 * <li>0,不退</li>
	 * <li>1,可退</li>
	 */
	private Integer isNeedPrerefund;

	/** 退款日期前天数 */
	private Integer prerefundDays;

	/** 退款日期前当天时刻-(分） */
	private Integer prerefundMinute;

	/** 退款日期前当天时刻-(时） */
	private Integer prerefundHour;

	/**
	 * 退款日期前退款数量类型
	 * 
	 * <li>1,整单退</li>
	 * <li>2,部分退</li>
	 */
	private Integer prerefundQuantityType;

	/**
	 * 退款日期前给供应商退款金额类型
	 * 
	 * <li>1,固定比例</li>
	 * <li>2,固定金额</li>
	 * <li>3,全额退款</li>
	 * <li>4,不退款</li>
	 */
	private Integer prerefundSupplierFeetype;

	/** 退款日期前给供应商退款金额数值 */
	private Integer prerefundSupplierFeevalue;

	/**
	 * 退款日期前给分销商退款金额类型
	 * 
	 * <li>1,固定比例</li>
	 * <li>2,固定金额</li>
	 * <li>3,全额退款</li>
	 * <li>4,不退款</li>
	 */
	private Integer prerefundDistributorFeetype;

	/** 退款日期前给分销商退款金额数值 */
	private Integer prerefundDistributorFeevalue;

	/**
	 * 退款日期后退款规则:是否可退
	 * 
	 * <li>0,不退</li>
	 * <li>1,可退</li>
	 */
	private Integer isNeedProrefund;

	/** 退款日期后天数 */
	private Integer prorefundDays;

	/** 退款日期后当天时刻-(时） */
	private Integer prorefundHour;

	/** 退款日期后当天时刻-（分） */
	private Integer prorefundMinute;

	/**
	 * 退款日期后退款数量类型
	 * 
	 * <li>1,整单退</li>
	 * <li>2,部分退</li>
	 */
	private Integer prorefundQuantityType;

	/**
	 * 退款日期后给供应商退款金额类型
	 * 
	 * <li>1,固定比例</li>
	 * <li>2,固定金额</li>
	 * <li>3,全额退款</li>
	 * <li>4,不退款</li>
	 */
	private Integer prorefundSupplierFeetype;

	/** 退款日期后给供应商退款金额数值 */
	private Integer prorefundSupplierFeevalue;
	/**
	 * 退款日期后给分销商退款金额类型
	 * 
	 * <li>1,固定比例</li>
	 * <li>2,固定金额</li>
	 * <li>3,全额退款</li>
	 * <li>4,不退款</li>
	 */
	private Integer prorefundDistributorFeetype;

	/** 退款日期后给分销商退款金额数值 */
	private Integer prorefundDistributorFeevalue;

	/**
	 * 退款是否需要运营审核
	 * 
	 * <li>0: 不需要</li>
	 * <li>1: 需要</li>
	 */
	private Integer refundReview;

	public RefundRuleLimit() {
		super();
	}

	public int getRefundAllowed() {
		return refundAllowed;
	}

	public void setRefundAllowed(final int refundAllowed) {
		this.refundAllowed = refundAllowed;
	}

	/**
	 * 是否允许退款.
	 * @return
	 */
	public boolean refunable() {
		return refundAllowed == 1;
	}

	public int getRefundAnyTime() {
		return refundAnyTime == null ? 0 : refundAnyTime.intValue();
	}

	public boolean refundAnyTime() {
		return refundAnyTime == 1;
	}

	public void setRefundAnyTime(final Integer refundAnyTime) {
		this.refundAnyTime = refundAnyTime;
	}

	public int getRefundAnyTimeFee() {
		return refundAnyTimeFee;
	}

	public void setRefundAnyTimeFee(final int refundAnyTimeFee) {
		this.refundAnyTimeFee = refundAnyTimeFee;
	}

	public Integer getRefundDateType() {
		return refundDateType;
	}

	public void setRefundDateType(final Integer refundDateType) {
		this.refundDateType = refundDateType;
	}

	public int getPrerefundDays() {
		return prerefundDays == null ? 0 : prerefundDays.intValue();
	}

	public void setPrerefundDays(final Integer prerefundDays) {
		this.prerefundDays = prerefundDays;
	}

	public int getPrerefundMinute() {
		return prerefundMinute == null ? 0 : prerefundMinute.intValue();
	}

	public void setPrerefundMinute(final Integer prerefundMinute) {
		this.prerefundMinute = prerefundMinute;
	}

	public int getPrerefundHour() {
		return prerefundHour == null ? 0 : prerefundHour.intValue();
	}

	public void setPrerefundHour(final Integer prerefundHour) {
		this.prerefundHour = prerefundHour;
	}

	public Integer getIsNeedProrefund() {
		return isNeedProrefund;
	}

	/**
	 * 后退款规则可退.
	 * @return
	 */
	public boolean proRefundable() {
		return isNeedProrefund == 1;
	}

	public void setIsNeedProrefund(final Integer isNeedProrefund) {
		this.isNeedProrefund = isNeedProrefund;
	}

	public Integer getPrerefundQuantityType() {
		return prerefundQuantityType;
	}

	public void setPrerefundQuantityType(final Integer prerefundQuantityType) {
		this.prerefundQuantityType = prerefundQuantityType;
	}

	public Integer getPrerefundSupplierFeetype() {
		return prerefundSupplierFeetype;
	}

	public void setPrerefundSupplierFeetype(final Integer prerefundSupplierFeetype) {
		this.prerefundSupplierFeetype = prerefundSupplierFeetype;
	}

	public Integer getPrerefundSupplierFeevalue() {
		return prerefundSupplierFeevalue;
	}

	public void setPrerefundSupplierFeevalue(final Integer prerefundSupplierFeevalue) {
		this.prerefundSupplierFeevalue = prerefundSupplierFeevalue;
	}

	public Integer getPrerefundDistributorFeetype() {
		return prerefundDistributorFeetype;
	}

	public void setPrerefundDistributorFeetype(final Integer prerefundDistributorFeetype) {
		this.prerefundDistributorFeetype = prerefundDistributorFeetype;
	}

	public Integer getPrerefundDistributorFeevalue() {
		return prerefundDistributorFeevalue;
	}

	public void setPrerefundDistributorFeevalue(final Integer prerefundDistributorFeevalue) {
		this.prerefundDistributorFeevalue = prerefundDistributorFeevalue;
	}

	public Integer getIsNeedPrerefund() {
		return isNeedPrerefund;
	}

	/**
	 * 前退款规则可退.
	 * @return
	 */
	public boolean preRefundable() {
		return isNeedPrerefund == 1;
	}

	public void setIsNeedPrerefund(final Integer isNeedPrerefund) {
		this.isNeedPrerefund = isNeedPrerefund;
	}

	public int getProrefundDays() {
		return prorefundDays == null ? 0 : prorefundDays.intValue();
	}

	public void setProrefundDays(final Integer prorefundDays) {
		this.prorefundDays = prorefundDays;
	}

	public int getProrefundHour() {
		return prorefundHour == null ? 0 : prorefundHour.intValue();
	}

	public void setProrefundHour(final Integer prorefundHour) {
		this.prorefundHour = prorefundHour;
	}

	public int getProrefundMinute() {
		return prorefundMinute == null ? 0 : prorefundMinute.intValue();
	}

	public void setProrefundMinute(final Integer prorefundMinute) {
		this.prorefundMinute = prorefundMinute;
	}

	public Integer getProrefundQuantityType() {
		return prorefundQuantityType;
	}

	public void setProrefundQuantityType(final Integer prorefundQuantityType) {
		this.prorefundQuantityType = prorefundQuantityType;
	}

	public Integer getProrefundSupplierFeetype() {
		return prorefundSupplierFeetype;
	}

	public void setProrefundSupplierFeetype(final Integer prorefundSupplierFeetype) {
		this.prorefundSupplierFeetype = prorefundSupplierFeetype;
	}

	public Integer getProrefundSupplierFeevalue() {
		return prorefundSupplierFeevalue;
	}

	public void setProrefundSupplierFeevalue(final Integer prorefundSupplierFeevalue) {
		this.prorefundSupplierFeevalue = prorefundSupplierFeevalue;
	}

	public Integer getProrefundDistributorFeetype() {
		return prorefundDistributorFeetype;
	}

	public void setProrefundDistributorFeetype(final Integer prorefundDistributorFeetype) {
		this.prorefundDistributorFeetype = prorefundDistributorFeetype;
	}

	public Integer getProrefundDistributorFeevalue() {
		return prorefundDistributorFeevalue;
	}

	public void setProrefundDistributorFeevalue(final Integer prorefundDistributorFeevalue) {
		this.prorefundDistributorFeevalue = prorefundDistributorFeevalue;
	}

	public Integer getRefundReview() {
		return refundReview;
	}

	/**
	 * 是否需要审核.
	 * @return
	 */
	public boolean isAudit() {
		return refundReview == 1;
	}

	public void setRefundReview(final Integer refundReview) {
		this.refundReview = refundReview;
	}

	/**
	 * 计算前退款时间点.
	 */
	public Date beforeRefundMaxTime(final Date refundTime) {
		//		if (isNeedPrerefund != 1) {
		//			return null;
		//		}
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(refundTime);
		calendar.add(Calendar.DAY_OF_YEAR, -(getPrerefundDays()));
		calendar.add(Calendar.HOUR_OF_DAY, getPrerefundHour());
		calendar.add(Calendar.MINUTE, getPrerefundMinute());
		return calendar.getTime();
	}

	/**
	 * 计算后退款时间点.
	 */
	public Date afterRefundMaxTime(final Date refundTime) {
		//		if (isNeedProrefund != 1) {
		//			return null;
		//		}
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(refundTime);
		calendar.add(Calendar.DAY_OF_YEAR, getProrefundDays());
		calendar.add(Calendar.HOUR_OF_DAY, getProrefundHour());
		calendar.add(Calendar.MINUTE, getProrefundMinute());
		return calendar.getTime();
	}

	public int getRefundRuleType() {
		return refundRuleType;
	}

	public void setRefundRuleType(int refundRuleType) {
		this.refundRuleType = refundRuleType;
	}

	public Integer getRefundAnyTimeQuantityType() {
		return refundAnyTimeQuantityType;
	}

	public void setRefundAnyTimeQuantityType(Integer refundAnyTimeQuantityType) {
		this.refundAnyTimeQuantityType = refundAnyTimeQuantityType;
	}
}
