package com.pzj.trade.order.common;

public enum MerchDockAuditStateEnum {
    /**
     * 审核中.
     */
    AUDITING(8),
    /**
     * 已通过审核
     */
    AUDITED(9),
    /**
     * 未通过审核.
     */
    REFUSED(7);

    private int state;

    public int getState() {
        return state;
    }

    private MerchDockAuditStateEnum(int state) {
        this.state = state;
    }
}
