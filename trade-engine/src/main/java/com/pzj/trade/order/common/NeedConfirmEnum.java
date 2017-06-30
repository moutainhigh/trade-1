package com.pzj.trade.order.common;

/**
 * 订单是否需要确认.
 * @author YRJ
 *
 */
public enum NeedConfirmEnum {
    NEED(2, "需要"), NOT_NEED(1, "不需要");
    private int    value;
    private String remark;

    private NeedConfirmEnum(int value, String remark) {
        this.value = value;
        this.remark = remark;
    }

    public int getValue() {
        return value;
    }

    public String getRemark() {
        return remark;
    }
}
