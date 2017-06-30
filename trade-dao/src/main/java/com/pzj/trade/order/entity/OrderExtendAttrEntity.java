/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.order.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * 填单项
 *
 */
public class OrderExtendAttrEntity implements Serializable {

    /**  */
    private static final long serialVersionUID = -4863456400804447955L;
    /** 交易ID */
    private String            transaction_id;
    /** 采购订单ID */
    private String            order_id;
    /**  组*/
    private String            attr_group;
    /** 键 */
    private String            attr_key;
    /**  值*/
    private String            attr_value;
    /** 创建时间 */
    private Date              create_time;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAttr_group() {
        return attr_group;
    }

    public void setAttr_group(String attr_group) {
        this.attr_group = attr_group;
    }

    public String getAttr_key() {
        return attr_key;
    }

    public void setAttr_key(String attr_key) {
        this.attr_key = attr_key;
    }

    public String getAttr_value() {
        return attr_value;
    }

    public void setAttr_value(String attr_value) {
        this.attr_value = attr_value;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

}
