package com.pzj.core.trade.voucher.entity;

public class ExtendVoucherEntity {

	/** ID */
	private long id;

	/** 核销凭证ID */
	private Long voucher_id;

	/** 供应商ID */
	private Long supplier_id;

	/** 凭证属性名称  */
	private String voucher_attr;

	/** 凭证属性值  */
	private String voucher_attr_content;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getVoucher_id() {
		return voucher_id;
	}

	public void setVoucher_id(Long voucher_id) {
		this.voucher_id = voucher_id;
	}

	public Long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(Long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getVoucher_attr() {
		return voucher_attr;
	}

	public void setVoucher_attr(String voucher_attr) {
		this.voucher_attr = voucher_attr;
	}

	public String getVoucher_attr_content() {
		return voucher_attr_content;
	}

	public void setVoucher_attr_content(String voucher_attr_content) {
		this.voucher_attr_content = voucher_attr_content;
	}
}
