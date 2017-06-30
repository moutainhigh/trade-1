package com.pzj.trade.order.entity;

import java.util.Date;

public class ReportOrderEntity {

    /** 订单ID*/
    private String orderId;
    /** 订单检票状态*/
    private int    order_status;
    /** 销售端口*/
    private int    sale_port;
    /** 对方订单号,即第三方支付编号*/
    private String third_code;
    /** 分销商ID*/
    private String reseller_id;
    /** 订单创建时间*/
    private Date   create_time;
    /** 订单支付时间*/
    private Date   pay_time;
    /**总张数 */
    private int    total_num;
    /** 检票张数*/
    private int    checked_num;
    /** 退票张数*/
    private int    refund_num;
    /** 订单总金额*/
    private double total_amount;
    /** 退票金额*/
    private double refund_amount;
    /** 渠道类型. 1: 直签; 2: 直销; 3: 魔方分销*/
    private double channel_type;

    /** 应收 =订单总金额-退票金额 */
    private double receivable;
    /** 实收 根据订单id取pay_flow表中收款方法=!账扣（pay_type='4'),pay_amount的汇总 */
    private double third_amount;
    /** 实收-付款 */
    private double third_amount_pay;
    /** 实收-退款 */
    private double third_amount_refund;
    /** 帐扣 根据订单id取pay_flow表中收款方法=账扣（pay_type='4'),pay_amount的汇总 */
    private double balance_amount;
    /** 帐扣-付款 */
    private double balance_amount_pay;
    /** 帐扣-退款 */
    private double balance_amount_refund;
    /**未收 =应收-实收-账扣  */
    private double unreceive;
    /**商品ID */
    private String merch_id;
    /**父商品ID */
    private String root_merch_id;
    /**产品名称 */
    private String merch_name;
    /**产品 类别 */
    private int    merch_type;
    /** 渠道ID*/
    private long   channelId;
    /** 订单商品表检票数量*/
    private int    merch_checked_num;
    /** 订单商品表单价*/
    private double merch_price;
    /** 订单商品表退票金额*/
    private double merch_refund_amount;

    private Date   update_time;
    /** 检票时间*/
    private Date   check_time;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public int getSale_port() {
        return sale_port;
    }

    public void setSale_port(int sale_port) {
        this.sale_port = sale_port;
    }

    public String getThird_code() {
        return third_code;
    }

    public void setThird_code(String third_code) {
        this.third_code = third_code;
    }

    public String getReseller_id() {
        return reseller_id;
    }

    public void setReseller_id(String reseller_id) {
        this.reseller_id = reseller_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getPay_time() {
        return pay_time;
    }

    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public int getChecked_num() {
        return checked_num;
    }

    public void setChecked_num(int checked_num) {
        this.checked_num = checked_num;
    }

    public int getRefund_num() {
        return refund_num;
    }

    public void setRefund_num(int refund_num) {
        this.refund_num = refund_num;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public double getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(double refund_amount) {
        this.refund_amount = refund_amount;
    }

    public double getReceivable() {
        return receivable;
    }

    public void setReceivable(double receivable) {
        this.receivable = receivable;
    }

    public double getThird_amount() {
        return third_amount;
    }

    public void setThird_amount(double third_amount) {
        this.third_amount = third_amount;
    }

    public double getBalance_amount() {
        return balance_amount;
    }

    public void setBalance_amount(double balance_amount) {
        this.balance_amount = balance_amount;
    }

    public double getUnreceive() {
        return unreceive;
    }

    public void setUnreceive(double unreceive) {
        this.unreceive = unreceive;
    }

    public String getMerch_id() {
        return merch_id;
    }

    public void setMerch_id(String merch_id) {
        this.merch_id = merch_id;
    }

    public String getMerch_name() {
        return merch_name;
    }

    public void setMerch_name(String merch_name) {
        this.merch_name = merch_name;
    }

    public int getMerch_type() {
        return merch_type;
    }

    public void setMerch_type(int merch_type) {
        this.merch_type = merch_type;
    }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public int getMerch_checked_num() {
        return merch_checked_num;
    }

    public void setMerch_checked_num(int merch_checked_num) {
        this.merch_checked_num = merch_checked_num;
    }

    public double getMerch_price() {
        return merch_price;
    }

    public void setMerch_price(double merch_price) {
        this.merch_price = merch_price;
    }

    public double getMerch_refund_amount() {
        return merch_refund_amount;
    }

    public void setMerch_refund_amount(double merch_refund_amount) {
        this.merch_refund_amount = merch_refund_amount;
    }

    public double getChannel_type() {
        return channel_type;
    }

    public void setChannel_type(double channel_type) {
        this.channel_type = channel_type;
    }

    public String getRoot_merch_id() {
        return root_merch_id;
    }

    public void setRoot_merch_id(String root_merch_id) {
        this.root_merch_id = root_merch_id;
    }

    public double getThird_amount_pay() {
        return third_amount_pay;
    }

    public void setThird_amount_pay(double third_amount_pay) {
        this.third_amount_pay = third_amount_pay;
    }

    public double getThird_amount_refund() {
        return third_amount_refund;
    }

    public void setThird_amount_refund(double third_amount_refund) {
        this.third_amount_refund = third_amount_refund;
    }

    public double getBalance_amount_pay() {
        return balance_amount_pay;
    }

    public void setBalance_amount_pay(double balance_amount_pay) {
        this.balance_amount_pay = balance_amount_pay;
    }

    public double getBalance_amount_refund() {
        return balance_amount_refund;
    }

    public void setBalance_amount_refund(double balance_amount_refund) {
        this.balance_amount_refund = balance_amount_refund;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Date getCheck_time() {
        return check_time;
    }

    public void setCheck_time(Date check_time) {
        this.check_time = check_time;
    }

}
