package com.pzj.trade.clearing.common;

public enum SettlStateEnum {
    CLEANABLE(0, "待清算"), CLEANED(1, "已清算"), CLEAN_FAIL(2, "清算失败"), CLEAN_CANCEL(3, "清算废弃");
    private SettlStateEnum(int key, String msg) {
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
