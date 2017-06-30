/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.order.vo;

import java.io.Serializable;

/**
 * 退款流程封装的请求参数
 * @author Administrator
 * @version $Id: RefundReqVo.java, v 0.1 2016年8月3日 下午6:02:06 Administrator Exp $
 */
public class RefundReqVo implements Serializable {

    private static final long serialVersionUID = -7982449475851585787L;

    /** 主订单ID */
    private String            rootOrderId;

    /** 子订单ID */
    private String            childOrderId;

    /** 退款唯一凭证  */
    private String            refundId;

    public String getRootOrderId() {
        return rootOrderId;
    }

    public void setRootOrderId(String rootOrderId) {
        this.rootOrderId = rootOrderId;
    }

    public String getChildOrderId() {
        return childOrderId;
    }

    public void setChildOrderId(String childOrderId) {
        this.childOrderId = childOrderId;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

}
