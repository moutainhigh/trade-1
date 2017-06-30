package com.pzj.trade.sms.common;

public enum SmsCardTypeEnum {
    
    notCard(1,"不限"),notQRCode(2,"非二维码"),useQRCode(3,"二维码");
    private SmsCardTypeEnum(int key,String msg){
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
}
