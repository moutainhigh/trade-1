package com.pzj.voucher.entity;

import java.io.Serializable;

/**
 * 
 * voucher保存第三方订单的信息VO
 * @author Mf-CX05
 * @version $Id: VoucherDockInfoEntity.java, v 0.1 2016年8月3日 下午5:35:23 Mf-CX05 Exp $
 */
public class VoucherDockInfoEntity implements Serializable{
    /**  */
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 交易号
     */
    private String transactionId;

    /** 第三方订单号 */
    private String externalOrderId;

    /** 第三方辅助码  */
    private String auxiliaryCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getExternalOrderId() {
        return externalOrderId;
    }

    public void setExternalOrderId(String externalOrderId) {
        this.externalOrderId = externalOrderId == null ? null : externalOrderId.trim();
    }

    public String getAuxiliaryCode() {
        return auxiliaryCode;
    }

    public void setAuxiliaryCode(String auxiliaryCode) {
        this.auxiliaryCode = auxiliaryCode == null ? null : auxiliaryCode.trim();
    }
}