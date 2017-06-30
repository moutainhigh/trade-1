package com.pzj.trade.order.vo;

import java.io.Serializable;
import java.util.Date;

//政策实体.
public class Strategy implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8133541514444494657L;
    /**
     *
     */
    /** 政策ID 必填*/
    private long              id;
    /** 政策渠道价 必填*/
    private double            price;
    /** 政策结算价必填*/
    private double            settlement_price;
    /** 政策开始时间 必填*/
    private Date              begin_date;
    /** 政策结束时间 必填*/
    private Date              end_date;
    /** 政策供应商 必填*/
    private long              supplier_id;
    /** 政策状态 必填*/
    private int               status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSettlement_price() {
        return settlement_price;
    }

    public void setSettlement_price(double settlement_price) {
        this.settlement_price = settlement_price;
    }

    public Date getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(Date begin_date) {
        this.begin_date = begin_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public long getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(long supplier_id) {
        this.supplier_id = supplier_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
