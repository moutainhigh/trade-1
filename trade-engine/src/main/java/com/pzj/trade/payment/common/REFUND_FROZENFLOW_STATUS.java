package com.pzj.trade.payment.common;

/**
 * 退款
 * @author kangzl
 *
 */
public enum REFUND_FROZENFLOW_STATUS {
    Refunding(1, "退款冻结"), RefundSuccess(2, "退款成功"), Refundfailure(3, "退款失败");
    private REFUND_FROZENFLOW_STATUS(int key, String msg) {
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
