package com.pzj.voucher.param;

import java.io.Serializable;

/**
 * 
 * Voucher激活接口参数对象
 * @author Mf-CX05
 * @version $Id: ActivationVoucherParamVO.java, v 0.1 2016年8月3日 下午6:05:32 Mf-CX05 Exp $
 */
public class ActivationVoucherParamVO implements Serializable{

    /**  */
    private static final long serialVersionUID = 1L;
    
    /**
     * 交易号
     */
    private String transactionId;
    
    /**
     * 系统内部订单号
     */
    private String orderId;
    
    /**
     * 第三方订单号
     * 与第三方对接系统时候使用
     */
    private String externalOrderId;
    
    /**
     * 第三方辅助码
     * 与第三方对接系统时候使用
     */
    private String auxiliaryCode;

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
        this.externalOrderId = externalOrderId;
    }

    public String getAuxiliaryCode() {
        return auxiliaryCode;
    }

    public void setAuxiliaryCode(String auxiliaryCode) {
        this.auxiliaryCode = auxiliaryCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
}
