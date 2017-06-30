package com.pzj.trade.sms.common;

/**
 * 发送短信的触发时间点
 * @author Administrator
 * @version $Id: SmsTouchOffTypeEnum.java, v 0.1 2016年8月23日 上午11:10:06 Administrator Exp $
 */
public enum SmsTouchOffTypeEnum {

    paymentNotConfirm(1,"payment_order_sms.ftl","付款成功,不需二次确认"),paymentNeedConfirm(2,"payment_order_sms.ftl","付款成功,需要二次确认"),confirmPass(3,"second_confirmpass_sms.ftl","二次确认通过"),confirmRefuse(4,"second_confirmrefuse_sms.ftl","二次确认拒绝"),sendOverMerch(5,"sendover_merch_sms.ftl","商品发货");
    private SmsTouchOffTypeEnum(int key,String tmplfile,String msg){
        this.key=key;
        this.msg=msg;
        this.tmplfile=tmplfile;
    }
    
    private int key;
    private String msg;
    private String tmplfile;
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
    public String getTmplfile() {
        return tmplfile;
    }
    public void setTmplfile(String tmplfile) {
        this.tmplfile = tmplfile;
    }
    
    public static SmsTouchOffTypeEnum getSmsTouchOffTypeEnum(int key){
        SmsTouchOffTypeEnum[] list=SmsTouchOffTypeEnum.values();
        for(SmsTouchOffTypeEnum item:list){
            if(item.getKey()==key){
                return item;
            }
        }
        throw null;
    }
}
