package com.pzj.trade.order.vo;

import java.io.Serializable;

/**
 * 增加订单备注参数.
 * @author YRJ
 *
 */
public class OrderRemarkVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 订单Id */
    private String            order_id;
    /** 备注 */
    private String            remark;
    /** 操作人类型：1: 下单者; 2: 客服*/
    private int               operator_type;

    /** 操作者ID */
    private long              operator_id;

    /** 操作者名字 */
    private String            operator_name;

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

    @Override
    public String toString() {
        StringBuilder tostr = new StringBuilder();
        tostr.append(OrderRemarkVO.class.getSimpleName());
        tostr.append("[order_id=").append(order_id);
        tostr.append(", operator_type=").append(operator_type);
        tostr.append(", operator_id=").append(operator_id);
        tostr.append(", operator_name=").append(operator_name);
        tostr.append(", remark=").append(remark);
        tostr.append("]");
        return tostr.toString();
    }
}
