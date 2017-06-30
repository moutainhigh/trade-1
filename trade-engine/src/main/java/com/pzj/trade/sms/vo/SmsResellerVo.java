package com.pzj.trade.sms.vo;

public class SmsResellerVo {
    /** 分销商手机号 */
    private String resellerPhone;
    
    private long resellerId;

    public String getResellerPhone() {
        return resellerPhone;
    }

    public void setResellerPhone(String resellerPhone) {
        this.resellerPhone = resellerPhone;
    }

    public long getResellerId() {
        return resellerId;
    }

    public void setResellerId(long resellerId) {
        this.resellerId = resellerId;
    }
}
