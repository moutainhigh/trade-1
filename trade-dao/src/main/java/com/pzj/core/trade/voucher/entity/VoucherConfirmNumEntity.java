package com.pzj.core.trade.voucher.entity;

/**
 * 检票点基本信息.
 * @author YRJ
 *
 */
public class VoucherConfirmNumEntity {
    /**
     * 主键ID.
     */
    private int  id;

    /**
     * 凭证ID.
     */
    private long voucher_id;

    /**
     * 检票点.
     */
    private long ticket_point;

    /**
     * 已使用次数.
     */
    private int  used_times;

    /**
     * 最大使用次数.
     */
    private int  max_use_times;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(long voucher_id) {
        this.voucher_id = voucher_id;
    }

    public long getTicket_point() {
        return ticket_point;
    }

    public void setTicket_point(long ticket_point) {
        this.ticket_point = ticket_point;
    }

    public int getUsed_times() {
        return used_times;
    }

    public void setUsed_times(int used_times) {
        this.used_times = used_times;
    }

    public int getMax_use_times() {
        return max_use_times;
    }

    public void setMax_use_times(int max_use_times) {
        this.max_use_times = max_use_times;
    }
}
