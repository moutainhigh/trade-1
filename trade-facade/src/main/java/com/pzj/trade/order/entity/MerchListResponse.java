package com.pzj.trade.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 订单列表响应消息.
 * @author YRJ
 *
 */
public class MerchListResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    /*商品ID*/
    private String            merch_id;
    /*商品状态*/
    private int               merch_state;
    /*商品名*/
    private String            merch_name;
    /*产品ID*/
    private long              product_id;
    /*渠道ID*/
    private long              channel_id;
    /*政策ID*/
    private long              strategy_id;
    /*vouerchId*/
    private long              voucher_id;
    /*总数量*/
    private int               total_num;
    /*已检数量*/
    private int               check_num;
    /*退款数量*/
    private int               refund_num;
    /*单价*/
    private double            price;
    /*退款金额*/
    private BigDecimal        refund_amount;
    /*订单ID*/
    private String            order_id;
    /*订单状态*/
    private int               order_status;
    /*创建时间*/
    private Timestamp         create_time;
    /*支付时间*/
    private Timestamp         pay_time;
    /*核销时间*/
    private Timestamp         confirm_time;
    /*商品大类*/
    private int               merch_type;
    /** 联系人 */
    private String            contactee;

    /** 联系电话 */
    private String            contact_mobile;

    public String getMerch_id() {
        return merch_id;
    }

    public void setMerch_id(String merch_id) {
        this.merch_id = merch_id;
    }

    public int getMerch_state() {
        return merch_state;
    }

    public void setMerch_state(int merch_state) {
        this.merch_state = merch_state;
    }

    public String getMerch_name() {
        return merch_name;
    }

    public void setMerch_name(String merch_name) {
        this.merch_name = merch_name;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public long getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(long channel_id) {
        this.channel_id = channel_id;
    }

    public long getStrategy_id() {
        return strategy_id;
    }

    public void setStrategy_id(long strategy_id) {
        this.strategy_id = strategy_id;
    }

    public long getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(long voucher_id) {
        this.voucher_id = voucher_id;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public int getCheck_num() {
        return check_num;
    }

    public void setCheck_num(int check_num) {
        this.check_num = check_num;
    }

    public int getRefund_num() {
        return refund_num;
    }

    public void setRefund_num(int refund_num) {
        this.refund_num = refund_num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public BigDecimal getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(BigDecimal refund_amount) {
        this.refund_amount = refund_amount;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Timestamp getPay_time() {
        return pay_time;
    }

    public void setPay_time(Timestamp pay_time) {
        this.pay_time = pay_time;
    }

    public Timestamp getConfirm_time() {
        return confirm_time;
    }

    public void setConfirm_time(Timestamp confirm_time) {
        this.confirm_time = confirm_time;
    }

    public int getMerch_type() {
        return merch_type;
    }

    public void setMerch_type(int merch_type) {
        this.merch_type = merch_type;
    }

    public String getContactee() {
        return contactee;
    }

    public void setContactee(String contactee) {
        this.contactee = contactee;
    }

    public String getContact_mobile() {
        return contact_mobile;
    }

    public void setContact_mobile(String contact_mobile) {
        this.contact_mobile = contact_mobile;
    }

}
