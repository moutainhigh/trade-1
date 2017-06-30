package com.pzj.trade.order.entity;

import java.io.Serializable;

/**
 * 订单第三方支付信息.
 * @author YRJ
 *
 */
public class OrderThirdPayEntity implements Serializable {

    /**  */
    private static final long serialVersionUID = 429976548643453344L;

    /**订单ID*/
    private String pay_id;

    /**订单支付者的资金帐号*/
    private long   object_id;

    /**第三方支付订单号*/
    private String deal_id;

    /**第三方收款人ID*/
    private String seller_email;
    
    /** 手续费  */
    private double poundage;

    public String getPay_id() {
        return pay_id;
    }

    public void setPay_id(String pay_id) {
        this.pay_id = pay_id;
    }

    public long getObject_id() {
        return object_id;
    }

    public void setObject_id(long object_id) {
        this.object_id = object_id;
    }

    public String getDeal_id() {
        return deal_id;
    }

    public void setDeal_id(String deal_id) {
        this.deal_id = deal_id;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public void setSeller_email(String seller_email) {
        this.seller_email = seller_email;
    }

    public double getPoundage() {
        return poundage;
    }

    public void setPoundage(double poundage) {
        this.poundage = poundage;
    }
    
    
}
