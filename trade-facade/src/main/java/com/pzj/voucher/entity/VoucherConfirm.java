package com.pzj.voucher.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class VoucherConfirm implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long id;

	/** 凭证ID */
	private Long voucherId;

	/**凭证ID集合，查询使用*/
	private List<Long> voucherIdVoucher;

	/** 子产品ID */
	private Long childProductId;

	/** 产品ID */
	private Long productId;

	/** 供应商ID */
	private Long supplierId;

	/** 使用次数 */
	private Integer usedTimes;

	/** 最多使用次数 */
	private Integer maxUseTimes;

	/**核销时间*/
	private Date confirmTime;

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(final Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(final Long voucherId) {
		this.voucherId = voucherId;
	}

	public Long getChildProductId() {
		return childProductId;
	}

	public void setChildProductId(final Long childProductId) {
		this.childProductId = childProductId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(final Long productId) {
		this.productId = productId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(final Long supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getUsedTimes() {
		return usedTimes;
	}

	public void setUsedTimes(final Integer usedTimes) {
		this.usedTimes = usedTimes;
	}

	public Integer getMaxUseTimes() {
		return maxUseTimes;
	}

	public void setMaxUseTimes(final Integer maxUseTimes) {
		this.maxUseTimes = maxUseTimes;
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public List<Long> getVoucherIdVoucher() {
		return voucherIdVoucher;
	}

	public void setVoucherIdVoucher(final List<Long> voucherIdVoucher) {
		this.voucherIdVoucher = voucherIdVoucher;
	}

}
