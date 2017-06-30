package com.pzj.trade.order.vo;

import java.io.Serializable;

/**
 * 退款消息.
 * @author CHJ
 *
 */
public class RefundFlowVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 订单ID 必填*/
    private String            order_id;

    /**
     * 商品ID.
     */
    private String            merch_id;

    /** 订单类型：1采购 2销售*/
    private int               refund_type;

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

    public int getRefund_type() {
        return refund_type;
    }

    public void setRefund_type(int refund_type) {
        this.refund_type = refund_type;
    }

}
