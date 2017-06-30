package com.pzj.trade.payment.common;

/**
 * 支付行为状态
 * @author kangzl
 *
 */
public enum PayFlowStateEnum {
    Paying(1, "未完成"), PaySuccess(2, "成功"), Payfailure(3, "失败");

    private PayFlowStateEnum(int key, String msg) {
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
