package com.pzj.trade.order.entity;

import java.io.Serializable;
import java.util.Date;

public class MftourCodeEntity implements Serializable {

    /**  */
    private static final long serialVersionUID = 3372595145689342015L;
    /**  */
    /**
     * 主键ID.
     */
    private long              code_id;
    /** 验证码*/
    private String            mf_code;

    /** 交易ID*/
    private String            transaction_id;

    /** 订单ID*/
    private String            order_id;

    /** 商品ID*/
    private String            merch_id;

    /** 供应商ID*/
    private long              supplier_id;

    /** 验证状态.0: 待验证;1: 已验证;2: 已过期*/
    private int               code_state;
    /** '验证来源. 1:扫码验证; 2: 平台手动验证'*/
    private int               source;
    /** 创建时间*/
    private Date              create_time;
    /** 更新时间*/
    private Date              update_time;

    public String getMf_code() {
        return mf_code;
    }

    public void setMf_code(String mf_code) {
        this.mf_code = mf_code;
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

    public long getSupplier_id() {
        return supplier_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public void setSupplier_id(long supplier_id) {
        this.supplier_id = supplier_id;
    }

    public int getCode_state() {
        return code_state;
    }

    public void setCode_state(int code_state) {
        this.code_state = code_state;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public long getCode_id() {
        return code_id;
    }

    public void setCode_id(long code_id) {
        this.code_id = code_id;
    }

}
