package com.pzj.trade.order.entity;

import java.util.Date;

/**
 * 订单备注.
 * @author YRJ
 *
 */
public class RemarkEntity {

    private long   remark_id;
    private String order_id;
    private String remark;
    private int    operator_type;
    private long   operator_id;
    private String operator_name;
    private Date   create_time;

    public long getRemark_id() {
        return remark_id;
    }

    public void setRemark_id(long remark_id) {
        this.remark_id = remark_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getOperator_type() {
        return operator_type;
    }

    public void setOperator_type(int operator_type) {
        this.operator_type = operator_type;
    }

    public long getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(long operator_id) {
        this.operator_id = operator_id;
    }

    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
