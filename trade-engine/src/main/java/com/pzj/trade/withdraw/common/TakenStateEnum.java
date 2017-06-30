package com.pzj.trade.withdraw.common;

public enum TakenStateEnum {
    canTaken(0, "可提现"), takening(1, "正在提现"), takened(2, "已经提现");
    private TakenStateEnum(int key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    private int    key;
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
