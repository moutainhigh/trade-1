package com.pzj.voucher.entity;

import java.io.Serializable;
import java.util.Date;

public class VoucherConfirmUsedTimes implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** 凭证ID */
	private Long voucherId;

	/** 使用次数 */
	private Integer usedTimes;

	/** 最多使用次数 */
	private Integer maxUseTimes;

	/**核销时间*/
	private Date confirmTime;

	/** 身份证号 */
	private String voucherContent;

	public Long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
	}

	public Integer getUsedTimes() {
		return usedTimes;
	}

	public void setUsedTimes(Integer usedTimes) {
		this.usedTimes = usedTimes;
	}

	public Integer getMaxUseTimes() {
		return maxUseTimes;
	}

	public void setMaxUseTimes(Integer maxUseTimes) {
		this.maxUseTimes = maxUseTimes;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getVoucherContent() {
		return voucherContent;
	}

	public void setVoucherContent(String voucherContent) {
		this.voucherContent = voucherContent;
	}

}
