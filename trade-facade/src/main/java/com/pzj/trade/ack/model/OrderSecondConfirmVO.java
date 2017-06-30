/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.ack.model;


/**
 * 二次确认服务的输入参数
 * @author Administrator
 * @version $Id: OrderSecondConfirmVO.java, v 0.1 2016年8月3日 上午11:44:10 Administrator Exp $
 */
@Deprecated
public class OrderSecondConfirmVO extends AckModel {

    private static final long serialVersionUID = -6401549986792758784L;

    private String            thirdCode;

    /**
     * Getter method for property <tt>thirdCode</tt>.
     * 
     * @return property value of thirdCode
     */
    @Override
    public String getThirdCode() {
        return thirdCode;
    }

    /**
     * Setter method for property <tt>thirdCode</tt>.
     * 
     * @param thirdCode value to be assigned to property thirdCode
     */
    @Override
    public void setThirdCode(String thirdCode) {
        this.thirdCode = thirdCode;
    }

    @Override
    public String toString() {
        StringBuilder tostr = new StringBuilder(super.toString());
        tostr.append("[thirdCode=").append(thirdCode);
        tostr.append("]");
        return tostr.toString();
    }
}
