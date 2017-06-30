package com.pzj.trade.withdraw.model;

import java.io.Serializable;
import java.util.Date;

public class WithdrawReqParameter implements Serializable {

    /**  */
    private static final long serialVersionUID = 4205321967048521574L;

    /**  人员ID 非空*/
    private long accountId;
    /**  开始时间*/
    private Date startTime;
    /**  结束时间*/
    private Date endTime;
    
    /**  分页大小*/
    private int pageSize=15;
    /**  分页页数 从0开始*/
    private int pageNum=0;
    
    
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
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
    public long getAccountId() {
        return accountId;
    }
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
