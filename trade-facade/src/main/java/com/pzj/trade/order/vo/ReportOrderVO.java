package com.pzj.trade.order.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单列表查询参数.
 * @author YRJ
 *
 */
public class ReportOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 商品更新 开始时间*/
    private Date              start_date;
    /** 商品更新 结束时间*/
    private Date              end_date;
    /** 分销商Id*/
    private long              resellerId;
    /** 商品状态：0：未检 1：已检*/
    private Integer           merch_state;
    /**1:关联采购和销售merch 0:只返回销售merch*/
    private int               is_root;

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public long getResellerId() {
        return resellerId;
    }

    public void setResellerId(long resellerId) {
        this.resellerId = resellerId;
    }

    public Integer getMerch_state() {
        return merch_state;
    }

    public void setMerch_state(Integer merch_state) {
        this.merch_state = merch_state;
    }

    public int getIs_root() {
        return is_root;
    }

    public void setIs_root(int is_root) {
        this.is_root = is_root;
    }

}
