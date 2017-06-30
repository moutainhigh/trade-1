/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.refund.model;

import java.io.Serializable;

/**
 * 
 * @author dongchf
 * @version $Id: RefundNotifyRequestModel.java, v 0.1 2016年11月1日 下午5:59:52 dongchunfu Exp $
 */
public class RefundNotifyRequestModel implements Serializable {

    private static final long serialVersionUID = -5082130726236430594L;

    /** 退款ID */
    private String            refundId;

    public RefundNotifyRequestModel() {
        super();
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

}
