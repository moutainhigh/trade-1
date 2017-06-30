package com.pzj.trade.clearing.model;

import java.io.Serializable;

/**
 * 商品清算请求参数.
 * @author YRJ
 *
 */
public class CleaningModel implements Serializable {

    /**  */
    private static final long serialVersionUID = -2403963280352707755L;

    /*
     * 订单ID.
     */
    private String            orderId;

    /**
     * 商品ID.
     */
    private String            merchId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }

    @Override
    public String toString() {
        StringBuilder tostr = new StringBuilder();
        tostr.append("[orderId=").append(orderId);
        tostr.append(", merchId=").append(merchId);
        tostr.append("]");
        return tostr.toString();
    }
}
