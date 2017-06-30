package com.pzj.trade.withdraw.entity;

import java.io.Serializable;
import java.util.Date;

public class WithdrawResponse implements Serializable {

    /**  */
    private static final long serialVersionUID = 2095622638079348208L;

    /**  用户ID*/
    private long              accountId;
    /**  提现编号*/
    private long              withdrawId;
    /**  提现目标金额*/
    private double            targetWithdrawMoney;
    /**  已提现金额*/
    private double            withdrawMoney;

    /**  提现类型 1线下提现 2线上退款提现*/
    private int               withdrawType     = 1;

    /**  提现状态 1提现中 2成功 3失败*/
    private int               withdrawStatus;

    /**  创建时间*/
    private Date              createTime;
    /**  更新时间*/
    private Date              updateTime;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getWithdrawId() {
        return withdrawId;
    }

    public void setWithdrawId(long withdrawId) {
        this.withdrawId = withdrawId;
    }

    public double getTargetWithdrawMoney() {
        return targetWithdrawMoney;
    }

    public void setTargetWithdrawMoney(double targetWithdrawMoney) {
        this.targetWithdrawMoney = targetWithdrawMoney;
    }

    public double getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(double withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public int getWithdrawStatus() {
        return withdrawStatus;
    }

    public void setWithdrawStatus(int withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getWithdrawType() {
        return withdrawType;
    }

    public void setWithdrawType(int withdrawType) {
        this.withdrawType = withdrawType;
    }

}
