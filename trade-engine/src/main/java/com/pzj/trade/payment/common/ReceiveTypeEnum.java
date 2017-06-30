package com.pzj.trade.payment.common;

/**
 * 收款类型.
 * @author YRJ
 *
 */
public enum ReceiveTypeEnum {

    Payment(1, "付款"), Refund(2, "退款"), Withdraw(3, "提现"), Stored(4, "预存"), Brokerage(5, "佣金收款"), //
    AfterSett(6, "后结收款"), PrePayment(9, "预付"), SupplierWithdraw(10, "供应商提现"), ResellerWithdraw(611, "分销商提现");

    private int    value;

    private String name;

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    private ReceiveTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
