package com.pzj.trade.sms.vo;

import java.util.Set;

public class SmsSupplierVo {
    /** 供应商手机号 */
    private String      supplierPhone;

    /** 供应商联系人电话 */
    private Set<String> contacteePhone;

    private long        supplierId;

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public Set<String> getContacteePhone() {
        return contacteePhone;
    }

    public void setContacteePhone(Set<String> contacteePhone) {
        this.contacteePhone = contacteePhone;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }
}
