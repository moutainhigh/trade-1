package com.pzj.trade.payment.entity;

import java.io.Serializable;
import java.util.Date;

public class CashPostalEntity implements Serializable {

    /**  */
    private static final long serialVersionUID = -1712258885994000883L;

    private long              postal_id;
    private long              account_id;
    private double            target_postal_money;
    private double            postal_money;
    
    private String            settlement_sign_id;
    
    private int               postal_status;
    //1线下提现 2第三方提现
    private int               withdraw_type;
    

    private Date              create_time;
    private Date              update_time;

    public long getPostal_id() {
        return postal_id;
    }

    public void setPostal_id(long postal_id) {
        this.postal_id = postal_id;
    }

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public int getWithdraw_type() {
        return withdraw_type;
    }

    public void setWithdraw_type(int withdraw_type) {
        this.withdraw_type = withdraw_type;
    }

    public double getTarget_postal_money() {
        return target_postal_money;
    }

    public void setTarget_postal_money(double target_postal_money) {
        this.target_postal_money = target_postal_money;
    }

    public double getPostal_money() {
        return postal_money;
    }

    public void setPostal_money(double postal_money) {
        this.postal_money = postal_money;
    }

    public int getPostal_status() {
        return postal_status;
    }

    public void setPostal_status(int postal_status) {
        this.postal_status = postal_status;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getSettlement_sign_id() {
        return settlement_sign_id;
    }

    public void setSettlement_sign_id(String settlement_sign_id) {
        this.settlement_sign_id = settlement_sign_id;
    }
}
