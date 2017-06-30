package com.pzj.trade.payment.entity;

import java.util.Date;

/**
 * 提现退款订单流水表
 * @author kangzl
 *
 */
public class WithdrawOrderFlow {
    
    /**
     * 流水ID
     */
    private long flow_id;
    /**
     * 订单ID
     */
    private String order_id;
    /**
     * 提现ID
     */
    private long withdraw_id;
    /**
     * 订单退款申请ID
     */
    private String refund_id;
    
    /**
     * 第三方支付类型
     */
    private int third_pay_type;
    /**
     * 订单退款金额
     */
    private double refund_money;
    /**
     * 退款提现对应的状态（1 正在退款提现 2成功 3失败）
     */
    private int flow_status;
    /**
     * 创建时间
     */
    private Date create_time;
    /**
     * 更新时间
     */
    private Date update_time;
    
    public long getFlow_id() {
        return flow_id;
    }
    public void setFlow_id(long flow_id) {
        this.flow_id = flow_id;
    }
    public String getOrder_id() {
        return order_id;
    }
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
    public long getWithdraw_id() {
        return withdraw_id;
    }
    public void setWithdraw_id(long withdraw_id) {
        this.withdraw_id = withdraw_id;
    }
    public String getRefund_id() {
        return refund_id;
    }
    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }
    public double getRefund_money() {
        return refund_money;
    }
    public void setRefund_money(double refund_money) {
        this.refund_money = refund_money;
    }
    public int getFlow_status() {
        return flow_status;
    }
    public void setFlow_status(int flow_status) {
        this.flow_status = flow_status;
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
    public int getThird_pay_type() {
        return third_pay_type;
    }
    public void setThird_pay_type(int third_pay_type) {
        this.third_pay_type = third_pay_type;
    }
}
