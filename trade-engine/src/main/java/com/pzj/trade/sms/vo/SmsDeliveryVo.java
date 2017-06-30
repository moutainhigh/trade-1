package com.pzj.trade.sms.vo;

import com.pzj.trade.order.model.DeliveryWay;

public class SmsDeliveryVo {

    /**
     * 配送方式
     */
    private int deliveryWay=DeliveryWay.NULL_SEND_TYPE.getKey().intValue();
    
    /**
     * 快递单号
     */
    private String expressNo;
    
    /**
     * 快递公司名称
     */
    private String expressCompany;

    public int getDeliveryWay() {
        return deliveryWay;
    }

    public void setDeliveryWay(int deliveryWay) {
        this.deliveryWay = deliveryWay;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }
}
