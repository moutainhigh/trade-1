package com.pzj.trade.order.vo;

import java.io.Serializable;

/**
 * 代下单参数.
 * @author CHJ
 *
 */
public class AgentOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 订单ID 必填*/
    private String            order_id;

    /**
     * 第三方订单ID.
     */
    private String            agent_order_id;

    /** 操作者ID 必填*/
    private long              operator_id;

    /** 操作原因*/
    private String            operator_reason;

    /** 是否自动*/
    private boolean           isAuto           = false;

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

    public boolean getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(boolean isAuto) {
        this.isAuto = isAuto;
    }

}
