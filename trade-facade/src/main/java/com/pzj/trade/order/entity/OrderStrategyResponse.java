package com.pzj.trade.order.entity;

import java.io.Serializable;

public class OrderStrategyResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String            orderId;
    private String            merchId;
    private long              channelId;
    private long              strategyId;
    private double            discountAmount;
    private int               discountType;
    private String            remark;

    /**
     * 获取订单ID.
     * @return
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单ID
     * @param orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取商品ID
     * @return
     */
    public String getMerchId() {
        return merchId;
    }

    /**
     * 设置商品ID
     * @param merchId
     */
    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }

    /**
     * 获取渠道ID
     * @return
     */
    public long getChannelId() {
        return channelId;
    }

    /**
     * 设置渠道ID
     * @param channelId
     */
    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    /**
     * 获取政策ID
     * @return
     */
    public long getStrategyId() {
        return strategyId;
    }

    /**
     * 设置政策ID
     * @param strategyId
     */
    public void setStrategyId(long strategyId) {
        this.strategyId = strategyId;
    }

    /**
     * 获取优惠金额
     * @return
     */
    public double getDiscountAmount() {
        return discountAmount;
    }

    /**
     * 设置优惠金额
     * @param discountAmount
     */
    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * 获取优惠类型.
     * 1. 前返; 2: 代金券; 3: 满减码; 4: 红包; 5: 通用抵用券
     * @return
     */
    public int getDiscountType() {
        return discountType;
    }

    /**
     * 设置优惠类型.
     * 1. 前返; 2: 代金券; 3: 满减码; 4: 红包; 5: 通用抵用券
     * @param discountType
     */
    public void setDiscountType(int discountType) {
        this.discountType = discountType;
    }

    /**
     * 获取优惠的说明，例如使用的满减码
     * @return
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置优惠的说明，例如使用的满减码.
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
