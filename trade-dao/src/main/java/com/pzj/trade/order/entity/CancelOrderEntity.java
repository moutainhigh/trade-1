package com.pzj.trade.order.entity;

import java.io.Serializable;

public class CancelOrderEntity implements Serializable {

    /**  */
    private static final long serialVersionUID = -8071364228529470406L;

    /** 订单ID */
    private String            order_id;

    /** 交易ID */
    private String            transaction_id;

    /** 订单状态 */
    private int               order_status;

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
}
