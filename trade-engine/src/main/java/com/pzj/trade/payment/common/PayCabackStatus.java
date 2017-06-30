package com.pzj.trade.payment.common;

public enum PayCabackStatus {

    refund(1, "退款"), cashPostal(2, "提现");

    private PayCabackStatus(int key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    private int key;

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

    private String msg;
}
