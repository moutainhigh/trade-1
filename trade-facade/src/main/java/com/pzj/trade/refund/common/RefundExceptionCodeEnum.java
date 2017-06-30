package com.pzj.trade.refund.common;

public enum RefundExceptionCodeEnum {
    
    vldParamException(40001,"退款参数验证异常"),refundServiceExcetion(40002,"退款系统异常");
    
    private RefundExceptionCodeEnum(int key,String msg){
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
