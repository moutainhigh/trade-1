package com.pzj.trade.order.entity;

import java.io.Serializable;
import java.util.Date;

public class AgentOrderEntity implements Serializable {

    /**  */
    private static final long serialVersionUID = -873398649018087399L;
    /** 分销商订单ID*/
    private String            order_id;
    /** 代下单订单号*/
    private String            agent_order_id;
    /** 操作人*/
    private long              operator_id;
    /** 操作原因*/
    private String            operator_reason;
    /** '是否自动 1：手动 2：自动'*/
    private int               is_auto;
    /** 创建时间*/
    private Date              create_time;
    /** 更新时间*/
    private Date              update_time;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAgent_order_id() {
        return agent_order_id;
    }

    public void setAgent_order_id(String agent_order_id) {
        this.agent_order_id = agent_order_id;
    }

    public long getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(long operator_id) {
        this.operator_id = operator_id;
    }

    public String getOperator_reason() {
        return operator_reason;
    }

    public void setOperator_reason(String operator_reason) {
        this.operator_reason = operator_reason;
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

    public int getIs_auto() {
        return is_auto;
    }

    public void setIs_auto(int is_auto) {
        this.is_auto = is_auto;
    }

}
