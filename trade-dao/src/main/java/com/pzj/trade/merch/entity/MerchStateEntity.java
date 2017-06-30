package com.pzj.trade.merch.entity;

/**
 * 商品状态实体类.
 * @author YRJ
 *
 */
public class MerchStateEntity {

    /**
     * 商品ID.
     */
    private String merch_id;
    /**
     * 商品ID.
     */
    private String root_merch_id;

    /**
     * 事务ID.
     */
    private String transaction_id;
    /**
     * 商品状态.
     */
    private int    merch_state;
    /**
     * 是否已清算.
     */
    private int    is_cleaned;

    public String getMerch_id() {
        return merch_id;
    }

    public void setMerch_id(String merch_id) {
        this.merch_id = merch_id;
    }

    public String getRoot_merch_id() {
        return root_merch_id;
    }

    public void setRoot_merch_id(String root_merch_id) {
        this.root_merch_id = root_merch_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getMerch_state() {
        return merch_state;
    }

    public void setMerch_state(int merch_state) {
        this.merch_state = merch_state;
    }

    public int getIs_cleaned() {
        return is_cleaned;
    }

    public void setIs_cleaned(int is_cleaned) {
        this.is_cleaned = is_cleaned;
    }

}
