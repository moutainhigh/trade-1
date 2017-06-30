package com.pzj.trade.order.entity;

import java.io.Serializable;

public class ConfirmOrderEntity implements Serializable {

    /**  */
    private static final long serialVersionUID = -2370540823527345797L;

    /** 订单ID */
    private String            order_id;

    /** 交易ID */
    private String            transaction_id;

    /** 订单状态 */
    private int               order_status;

    /**父订单ID*/
    private String            p_order_id;

    /** 是否需要确认 */
    private int               confirm;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getP_order_id() {
        return p_order_id;
    }

    public void setP_order_id(String p_order_id) {
        this.p_order_id = p_order_id;
    }

    public int getConfirm() {
        return confirm;
    }

    public void setConfirm(int confirm) {
        this.confirm = confirm;
    }

}
