package com.pzj.trade.merch.entity;

/**
 * 商品退款申请记录.
 * @author YRJ
 *
 */
public class MerchRefundEntity {

    /**
     * 退款申请记录ID.
     */
    private String refund_id;

    /**
     * 订单ID.
     */
    private String order_id;

    /**
     * 商品ID.
     */
    private String merch_id;

    /**
     * 退款申请记录状态值.
     */
    private int    refund_state;

    public String getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getMerch_id() {
        return merch_id;
    }

    public void setMerch_id(String merch_id) {
        this.merch_id = merch_id;
    }

    public int getRefund_state() {
        return refund_state;
    }

    public void setRefund_state(int refund_state) {
        this.refund_state = refund_state;
    }
}
