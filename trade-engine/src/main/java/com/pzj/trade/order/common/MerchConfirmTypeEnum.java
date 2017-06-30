package com.pzj.trade.order.common;

public enum MerchConfirmTypeEnum {
    
    triggerConfirm(0,"触发式核销"),timerConfirm(1,"按核销时间核销");
    
    private MerchConfirmTypeEnum(int key,String msg){
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
