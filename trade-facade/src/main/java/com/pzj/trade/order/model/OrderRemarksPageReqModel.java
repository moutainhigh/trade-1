package com.pzj.trade.order.model;

import java.io.Serializable;

public class OrderRemarksPageReqModel implements Serializable {

    /**  */
    private static final long serialVersionUID = 4205321967048521574L;

    /**  订单ID 非空*/
    private long orderId;

    /**  分页大小*/
    private int pageSize=15;
    /**  分页页数 从0开始*/
    private int pageNum=0;
    
    
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
    
}
