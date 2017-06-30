package com.pzj.trade.withdraw.common;

public enum WithdrawTypeEnum {
    onlineWithdraw(2,"线上第三方退款提现"),notlineWithdraw(1,"线下提现");
    private WithdrawTypeEnum(int key,String msg){
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
