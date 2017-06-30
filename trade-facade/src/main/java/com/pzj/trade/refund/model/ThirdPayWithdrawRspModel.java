package com.pzj.trade.refund.model;

import java.io.Serializable;
import java.util.List;

public class ThirdPayWithdrawRspModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2075883570941005243L;
    /**
     * 提现标识（非空）
     */
    private Long withdrawId;
    /**
     * 提现对应的退款集合（非空）
     */
    private List<String> refundIdList;
    /**
     * 成功失败的标识
     */
    private Boolean rspResult;
    

    public Long getWithdrawId() {
        return withdrawId;
    }

    public void setWithdrawId(Long withdrawId) {
        this.withdrawId = withdrawId;
    }

    public List<String> getRefundIdList() {
        return refundIdList;
    }

    public void setRefundIdList(List<String> refundIdList) {
        this.refundIdList = refundIdList;
    }

    public Boolean getRspResult() {
        return rspResult;
    }

    public void setRspResult(Boolean rspResult) {
        this.rspResult = rspResult;
    }

}
