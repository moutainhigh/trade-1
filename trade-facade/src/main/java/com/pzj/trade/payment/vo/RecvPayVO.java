package com.pzj.trade.payment.vo;

import java.io.Serializable;

/**
 * 收付款请求参数.
 * @author YRJ
 *
 */
public class RecvPayVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 付款者ID */
    private long              payerId;

    private int               accountType;

    /**
     * 收款类型
     */
    private int               receiveType;

    /** 收付款金额 */
    private double            money;

    /** 币种. 1: 人民币; 2: 美元 */
    private int               currency;

    /** 单据号 */
    private String            docNo;

    private int               source           = 2;

    private String            bankAccount;

    /**
     * 获取付款者ID.
     * @return
     */
    public long getPayerId() {
        return payerId;
    }

    /**
     * 设置付款者ID.
     * @param payerId
     */
    public void setPayerId(long payerId) {
        this.payerId = payerId;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public int getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }

    /**
     * 获取收付款金额
     * @return
     */
    public double getMoney() {
        return money;
    }

    /**
     * 设置收付款金额
     * @param money
     */
    public void setMoney(double money) {
        this.money = money;
    }

    /**
     * 获取币种. 1: 人民币; 2: 美元
     * @return
     */
    public int getCurrency() {
        return currency;
    }

    /**
     * 设置币种.1: 人民币; 2: 美元
     * @param currency
     */
    public void setCurrency(int currency) {
        this.currency = currency;
    }

    /**
     * 获取单据号
     * @return
     */
    public String getDocNo() {
        return docNo;
    }

    /**
     * 设置单据号
     * @param docNo
     */
    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    /** 获取请求来源, 默认为2: 结算系统.
     * <ul>
     * <li>1. 支付中心</li>
     * <li>2. 结算系统</li>
     * <li>3. 分销商系统</li>
     * <li>4. 供应商系统</li>
     * </ul>
     */
    public int getSource() {
        return source;
    }

    /** 设置请求来源, 默认为2: 结算系统.
     * <ul>
     * <li>1. 支付中心</li>
     * <li>2. 结算系统</li>
     * <li>3. 分销商系统</li>
     * <li>4. 供应商系统</li>
     * </ul>
     */
    public void setSource(int source) {
        this.source = source;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
}
