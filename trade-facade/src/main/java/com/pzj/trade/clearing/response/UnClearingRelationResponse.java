package com.pzj.trade.clearing.response;

import java.io.Serializable;

/**
 * 未清算记录响应值.
 * @author YRJ
 *
 */
public class UnClearingRelationResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID.
     */
    private long              cleanId;
    /**
     * 订单ID.
     */
    private String            orderId;
    /**
     * 事务ID.
     */
    private String            transactionId;
    /**
     * 商品ID.
     */
    private String            merchId;
    /**
     * 预期清算时间.
     */
    private long              effecTime;
    /**
     * 清算类别. 1: 正常清算; 2: 逾期清算
     */
    private int               cleanType;
    /**
     * 清算状态. 0: 未清算; 1: 已清算; 2: 清算失败.
     */
    private int               cleanState;
    /**
     * 是否可手动清算.0: 不可;1: 可手动.只有清算规则为固定时间规则时, 此值有效.
     */
    private int               isManual;

    /**
     * 清算次数
     */
    private int               cleanCount;

    public long getCleanId() {
        return cleanId;
    }

    public void setCleanId(long cleanId) {
        this.cleanId = cleanId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }

    public long getEffecTime() {
        return effecTime;
    }

    public void setEffecTime(long effecTime) {
        this.effecTime = effecTime;
    }

    public int getCleanType() {
        return cleanType;
    }

    public void setCleanType(int cleanType) {
        this.cleanType = cleanType;
    }

    public int getCleanState() {
        return cleanState;
    }

    public void setCleanState(int cleanState) {
        this.cleanState = cleanState;
    }

    public int getIsManual() {
        return isManual;
    }

    public void setIsManual(int isManual) {
        this.isManual = isManual;
    }

    public int getCleanCount() {
        return cleanCount;
    }

    public void setCleanCount(int cleanCount) {
        this.cleanCount = cleanCount;
    }

}
