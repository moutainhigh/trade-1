package com.pzj.trade.order.entity;

import java.io.Serializable;

/**
 * 供应商订单商品.
 * @author YRJ
 *
 */
public class SupplierMerchResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String            merchId;
    private String            merchName;
    private int               merchState       = 0;
    private long              strategyId;

    private int               num;
    private double            price;
    private double            amount;

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }

    public String getMerchName() {
        return merchName;
    }

    public void setMerchName(String merchName) {
        this.merchName = merchName;
    }

    public int getMerchState() {
        return merchState;
    }

    public void setMerchState(int merchState) {
        this.merchState = merchState;
    }

    public long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(long strategyId) {
        this.strategyId = strategyId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
