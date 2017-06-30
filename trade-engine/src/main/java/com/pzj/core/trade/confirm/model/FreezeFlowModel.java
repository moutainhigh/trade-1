package com.pzj.core.trade.confirm.model;

import java.io.Serializable;

public class FreezeFlowModel implements Serializable {

    /**  */
    private static final long serialVersionUID = -8818767240570350213L;
    private long              freeze_id;
    /**订单id*/
    private String            order_id;

    private long              payer_id;
    /** 支付类型 */
    private int               receive_type;
    /**支付冻结唯一流水号*/
    private String            sign_id;

    /**冻结状态 1：支付完毕  0：待支付   2 支付取消*/
    private int               freeze_state;

    /**余额支付金额*/
    private double            balance_amount;

    /**第三方支付金额*/
    private double            third_amount;

    /**销售端口*/
    private int               sale_port;
    /**分销商ID*/
    private long              reseller_id;

    public long getFreeze_id() {
        return freeze_id;
    }

    public void setFreeze_id(long freeze_id) {
        this.freeze_id = freeze_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getSign_id() {
        return sign_id;
    }

    public void setSign_id(String sign_id) {
        this.sign_id = sign_id;
    }

    public int getFreeze_state() {
        return freeze_state;
    }

    public void setFreeze_state(int freeze_state) {
        this.freeze_state = freeze_state;
    }

    public double getBalance_amount() {
        return balance_amount;
    }

    public void setBalance_amount(double balance_amount) {
        this.balance_amount = balance_amount;
    }

    public double getThird_amount() {
        return third_amount;
    }

    public int getReceive_type() {
        return receive_type;
    }

    public void setReceive_type(int receive_type) {
        this.receive_type = receive_type;
    }

    public void setThird_amount(double third_amount) {
        this.third_amount = third_amount;
    }

    public long getPayer_id() {
        return payer_id;
    }

    public void setPayer_id(long payer_id) {
        this.payer_id = payer_id;
    }

    public int getSale_port() {
        return sale_port;
    }

    public void setSale_port(int sale_port) {
        this.sale_port = sale_port;
    }

    public long getReseller_id() {
        return reseller_id;
    }

    public void setReseller_id(long reseller_id) {
        this.reseller_id = reseller_id;
    }

}
