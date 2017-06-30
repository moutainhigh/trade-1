package com.pzj.core.trade.refund.engine.common;

public enum RefundUserTypeEnum {
    supplierRefund(1,"供应商退款"),resellerRefund(2,"分销商退款");
    private RefundUserTypeEnum(int key,String msg){
        this.key=key;
        this.msg=msg;
    }
    private int key;
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
    public static boolean checkHas(int key){
        boolean flag=false;
        RefundUserTypeEnum[] result=RefundUserTypeEnum.values();
        for(RefundUserTypeEnum usertype:result){
            if(usertype.getKey()==key){
                flag=true;
                break;
            }
        }
        return flag;
    }
}
