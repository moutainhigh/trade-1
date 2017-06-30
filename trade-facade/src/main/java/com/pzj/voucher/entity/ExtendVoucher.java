package com.pzj.voucher.entity;

import java.io.Serializable;

public class ExtendVoucher implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** ID */
    private Long   id;

    /** 核销凭证ID */
    private Long   voucherId;

    /** 供应商ID */
    private Long   supplierId;

    /** 凭证属性名称  */
    private String voucherAttr;

    /** 凭证属性值  */
    private String voucherAttrContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getVoucherAttr() {
        return voucherAttr;
    }

    public void setVoucherAttr(String voucherAttr) {
        this.voucherAttr = voucherAttr == null ? null : voucherAttr.trim();
    }

    public String getVoucherAttrContent() {
        return voucherAttrContent;
    }

    public void setVoucherAttrContent(String voucherAttrContent) {
        this.voucherAttrContent = voucherAttrContent == null ? null : voucherAttrContent.trim();
    }
}
