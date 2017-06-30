package com.pzj.core.trade.voucher.entity;

import java.io.Serializable;
import java.util.List;

public class VoucherConfirmEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;

	/** 凭证ID */
	private Long voucherId;

	private List<Long> voucherIdList;

	/** 检票点ID */
	private Long childProductId;

	/** 产品ID */
	private Long productId;

	/** 供应商ID */
	private Long supplierId;

	/** 使用次数 */
	private Integer usedTimes;

	/** 最多使用次数 */
	private Integer maxUseTimes;

	public Long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
	}

	public Long getChildProductId() {
		return childProductId;
	}

	public void setChildProductId(Long childProductId) {
		this.childProductId = childProductId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Long> getVoucherIdList() {
		return voucherIdList;
	}

	public void setVoucherIdList(List<Long> voucherIdList) {
		this.voucherIdList = voucherIdList;
	}

}
