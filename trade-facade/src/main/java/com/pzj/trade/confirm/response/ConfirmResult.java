package com.pzj.trade.confirm.response;

import java.io.Serializable;

/**
 * 核销结果.
 * @author YRJ
 *
 */
public class ConfirmResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID.
     */
    private String            orderId;

    /**
     * 商品ID.
     */
    private String            merchId;

    /**
     * 核销结果.
     * <ul>
     * <li>true: 核销成功.</li>
     * <li>false: 核销失败.</li>
     * </ul>
     */
    private boolean           writtenOff;

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

    public boolean isWrittenOff() {
        return writtenOff;
    }

    public void setWrittenOff(boolean writtenOff) {
        this.writtenOff = writtenOff;
    }

    @Override
    public String toString() {
        StringBuilder tostr = new StringBuilder();
        tostr.append(ConfirmResult.class.getSimpleName());
        tostr.append("[orderId=").append(orderId);
        tostr.append(", merchId=").append(merchId);
        tostr.append(", written-off=").append(writtenOff);
        tostr.append("]");
        return tostr.toString();
    }
}
