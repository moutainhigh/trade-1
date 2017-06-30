package com.pzj.trade.confirm.model;

import java.io.Serializable;

/**
 * 逾期的订单商品信息
 * @author kangzl
 * @version $Id: MerchOverdueModel.java, v 0.1 2016年9月14日 下午4:00:13 Administrator Exp $
 */
public class MerchOverdueResponseModel implements Serializable {
    /**  */
    private static final long serialVersionUID = 2463183846194103610L;
    private long merch_id;
    private long voucher_id;
    private long effec_time;
    
    public long getMerch_id() {
        return merch_id;
    }
    public void setMerch_id(long merch_id) {
        this.merch_id = merch_id;
    }
    public long getVoucher_id() {
        return voucher_id;
    }
    public void setVoucher_id(long voucher_id) {
        this.voucher_id = voucher_id;
    }
    public long getEffec_time() {
        return effec_time;
    }
    public void setEffec_time(long effec_time) {
        this.effec_time = effec_time;
    }
}
