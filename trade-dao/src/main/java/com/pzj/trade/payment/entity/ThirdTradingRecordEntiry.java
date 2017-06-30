package com.pzj.trade.payment.entity;

import java.io.Serializable;

/**
 * 第三方支付记录
 * @author kangzl
 * @version $Id: ThirdTradingRecordEntiry.java, v 0.1 2016年5月20日 上午11:29:19 kangzl Exp $
 */
public class ThirdTradingRecordEntiry implements Serializable {
    /**  */
    private static final long serialVersionUID = -4409342995218785248L;
    private String            pay_id;
    private double            real_amount;
    private long              object_id;
    private int               third_pay_type;
    private int               result;
    private String            bank_id;
    private String            deal_id;
    private double            poundage;
    private double            refund_amount;
    private String            seller_email;

    public String getPay_id() {
        return pay_id;
    }

    public void setPay_id(String pay_id) {
        this.pay_id = pay_id;
    }

    public double getReal_amount() {
        return real_amount;
    }

    public void setReal_amount(double real_amount) {
        this.real_amount = real_amount;
    }

    public long getObject_id() {
        return object_id;
    }

    public void setObject_id(long object_id) {
        this.object_id = object_id;
    }

    public int getThird_pay_type() {
        return third_pay_type;
    }

    public void setThird_pay_type(int third_pay_type) {
        this.third_pay_type = third_pay_type;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getDeal_id() {
        return deal_id;
    }

    public void setDeal_id(String deal_id) {
        this.deal_id = deal_id;
    }

    public double getPoundage() {
        return poundage;
    }

    public void setPoundage(double poundage) {
        this.poundage = poundage;
    }

    public double getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(double refund_amount) {
        this.refund_amount = refund_amount;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public void setSeller_email(String seller_email) {
        this.seller_email = seller_email;
    }

}
