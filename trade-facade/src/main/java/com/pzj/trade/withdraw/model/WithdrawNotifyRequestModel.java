/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.withdraw.model;

import java.io.Serializable;

/**
 * 提现通知请求参数
 * 
 * @author dongchf
 * @version $Id: WithdrawNotifyRequestModel.java, v 0.1 2016年11月1日 下午6:12:35 dongchunfu Exp $
 */
public class WithdrawNotifyRequestModel implements Serializable {

    private static final long serialVersionUID = 5262247630133759732L;

    /** 提现任务ID */
    private Long              cashpostalId;

    public WithdrawNotifyRequestModel() {
        super();
    }

    public Long getCashpostalId() {
        return cashpostalId;
    }

    public void setCashpostalId(Long cashpostalId) {
        this.cashpostalId = cashpostalId;
    }

}
