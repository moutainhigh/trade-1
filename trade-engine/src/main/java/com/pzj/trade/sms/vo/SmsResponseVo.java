package com.pzj.trade.sms.vo;

import java.io.Serializable;

public class SmsResponseVo implements Serializable {

    /**  */
    private static final long serialVersionUID = 7482944840801184232L;
    
    /** 发送对象类型 */
    private int sendUserType;
    
    /** 凭证类型  是否是二维码 */
    private int cardType;
    
    /**  短信触发条件类型*/
    private int smsTouchOffType;
    
    /**  销售端口（微店）*/
    private int salePort;
    

    public int getSendUserType() {
        return sendUserType;
    }

    public void setSendUserType(int sendUserType) {
        this.sendUserType = sendUserType;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public int getSmsTouchOffType() {
        return smsTouchOffType;
    }

    public void setSmsTouchOffType(int smsTouchOffType) {
        this.smsTouchOffType = smsTouchOffType;
    }

    public int getSalePort() {
        return salePort;
    }

    public void setSalePort(int salePort) {
        this.salePort = salePort;
    }
    
}
