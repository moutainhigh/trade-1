package com.pzj.trade.order.common;

public enum NeedDockConfirmEnum {
    needConfirm(2, "需要确认"), notConfirm(1, "不要确认");

    private int    value;

    private String msg;

    public int getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

    private NeedDockConfirmEnum(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }
}
