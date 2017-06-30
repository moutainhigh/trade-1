package com.pzj.trade.sms.common;

import com.pzj.trade.sms.exception.SmsException;

public enum SmsSendTypeEnum {
    orderContact(1,"订单联系人"),orderReseller(2,"订单分销商"),orderSupplier(3,"订单供应商");
    private SmsSendTypeEnum(int key,String msg){
        this.key=key;
        this.msg=msg;
    }
    private int key;
    private String msg;
    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public static SmsSendTypeEnum getSmsSendType(int key){
        SmsSendTypeEnum[] arrs=SmsSendTypeEnum.values();
        for(SmsSendTypeEnum e:arrs){
            if(e.getKey()==key){
                return e;
            }
        }
        throw new SmsException("无法找到发送对象");
    }
}
