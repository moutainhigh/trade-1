package com.pzj.trade.payment.common;

public enum ACCOUNT_SOURCEFLOW_TYPE {
    PayCenter(1, "支付中心"), Settlement(2, "结算系统"), Reseller(3, "分销商系统"), Supplier(4, "供应商系统");
    private ACCOUNT_SOURCEFLOW_TYPE(int key, String msg) {
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
