package com.pzj.trade.payment.common;

public enum ThirdPayRefundInterfaceEnum {
    AlibabaPay(1, "支付宝的退款接口"), TencentPay(2, "微信的退款接口"), BankPay(3, "银行的退款接口");
    private ThirdPayRefundInterfaceEnum(int key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    private int    key;
    private String msg;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
