package com.pzj.trade.order.common;

/**
 * 订单状态.
 * @author YRJ
 *
 */
public enum OrderStatusEnum {

    /**
     * 待付款.
     */
    Unpaid(1, true, false, "下单中"),
    /**
     * 已付款.
     */
    AlreadyPaid(10, false, true, "已支付"),
    /**
     * 已取消.
     */
    Cancelled(20, false, false, "已取消"),
    /**
     * 已退款.
     */
    Refunded(30, false, false, "已退款"),
    /**
     * 已完成.
     */
    Finished(40, false, false, "已完成");
    
    /** 订单状态值 */
    private int     value;

    /** 是否可取消 */
    private boolean cancelable;

    /** 是否可二次确认 */
    private boolean confirmable;

    /** 订单状态描述 */
    private String  msg;

    public int getValue() {
        return value;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public boolean isConfirmable() {
        return confirmable;
    }

    public String getMsg() {
        return msg;
    }

    private OrderStatusEnum(int value, boolean cancelable, boolean confirmable, String msg) {
        this.value = value;
        this.cancelable = cancelable;
        this.confirmable = confirmable;
        this.msg = msg;
    }

    public static OrderStatusEnum getOrderStatus(int value) {
        for (OrderStatusEnum order_status : OrderStatusEnum.values()) {
            if (order_status.getValue() == value) {
                return order_status;
            }
        }
        throw new java.lang.IllegalStateException("订单状态错误. value: " + value);
    }
}
