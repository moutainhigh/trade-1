package com.pzj.trade.payment.common;

public enum THIRD_PART_PAY_RESULT {

    success(1, "成功"), failure(2, "失败"), notPay(0, "未支付"), cancel(-1, "取消");
    private THIRD_PART_PAY_RESULT(int key, String msg) {
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
