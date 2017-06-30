package com.pzj.trade.payment.vo;

public class ThirdPayOrderVo {
    /**
     * 1: 支付宝; 2: 微信; 3: 异度（中信）;  5: 电汇;
     */
    private int    payType;
    /**
     * 支付金额
     */
    private double payPrice;

    /**
     * 币种. 1: 人民币; 2: 美元; ……
     */
    private int    currency;

    /**
     * 收款银行账户
     */
    private String bank_account;

    public ThirdPayOrderVo() {
        super();
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(double payPrice) {
        this.payPrice = payPrice;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

}
