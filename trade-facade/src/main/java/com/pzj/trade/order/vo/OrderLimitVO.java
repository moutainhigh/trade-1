package com.pzj.trade.order.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单查询参数.用于下单领票人限制判断
 * @author CHJ
 *
 */
public class OrderLimitVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**领票人电话  */
    private String            contact_mobile;

    /** 领票人身份证号 */
    private String            idcard_no;

    /** 产品ID */
    private long              product_id;

    /** 下单时间起 */
    private Date              start_time;

    /** 下单时间止 */
    private Date              end_time;

    public String getContact_mobile() {
        return contact_mobile;
    }

    public void setContact_mobile(String contact_mobile) {
        this.contact_mobile = contact_mobile;
    }

    public String getIdcard_no() {
        return idcard_no;
    }

    public void setIdcard_no(String idcard_no) {
        this.idcard_no = idcard_no;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

}
