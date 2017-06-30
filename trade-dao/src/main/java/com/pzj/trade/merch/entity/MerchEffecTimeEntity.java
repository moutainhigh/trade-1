package com.pzj.trade.merch.entity;

/**
 * 产品游玩有效期实体.
 * @author YRJ
 *
 */
public class MerchEffecTimeEntity {

    /**
     * 商品ID.
     */
    private String merch_id;

    /**
     * 商品对应的订单ID.
     */
    private String order_id;

    /**
     * 该订单的唯一事务ID.
     */
    private String transaction_id;

    /**
     * 游玩有效期.
     */
    private long   effec_time;

    /**
     * 凭证ID.
     */
    private long   voucher_id;

    /**
     * 凭证类型
     */
    private int    vour_type;

    /**
     * 清算类型.
     */
    private int    clear_type;

    public String getMerch_id() {
        return merch_id;
    }

    public void setMerch_id(String merch_id) {
        this.merch_id = merch_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public long getEffec_time() {
        return effec_time;
    }

    public void setEffec_time(long effec_time) {
        this.effec_time = effec_time;
    }

    public long getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(long voucher_id) {
        this.voucher_id = voucher_id;
    }

    public int getVour_type() {
        return vour_type;
    }

    public void setVour_type(int vour_type) {
        this.vour_type = vour_type;
    }

    public int getClear_type() {
        return clear_type;
    }

    public void setClear_type(int clear_type) {
        this.clear_type = clear_type;
    }
}
