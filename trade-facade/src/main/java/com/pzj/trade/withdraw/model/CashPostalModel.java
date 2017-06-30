package com.pzj.trade.withdraw.model;

import java.io.Serializable;

/**
 * 提现申请参数
 * @author YRJ
 *
 */
public class CashPostalModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 账户ID.
	 */
	private long accountId;

	/**
	 * 用户类型.
	 * <ul>
	 * <li>1: 供应商</li>
	 * 
	 * <li>2: 分销商</li>
	 * 
	 * <li>3:saas用户</lis>
	 * </ul>
	 */
	private int userType;

	/**
	 * 分销商类型.
	 * <ul>
	 * <li>1: 二维码</li>
	 * </ul>
	 */
	private int resellerType;

	/**
	 * 提现金额.
	 */
	private double cashPostalMoney;

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getResellerType() {
		return resellerType;
	}

	/**
	 * 是否为二维码账户.
	 * @return
	 */
	public boolean isQR() {
		return ResellerAccountType.QR_CODE.getType() == resellerType;
	}

	public void setResellerType(int resellerType) {
		this.resellerType = resellerType;
	}

	public double getCashPostalMoney() {
		return cashPostalMoney;
	}

	public void setCashPostalMoney(double cashPostalMoney) {
		this.cashPostalMoney = cashPostalMoney;
	}

	@Override
	public String toString() {
		StringBuilder tostr = new StringBuilder();
		tostr.append("[accountId: ").append(accountId);
		tostr.append(", userType: ").append(userType);
		tostr.append(", resellerType").append(resellerType);
		tostr.append(", money").append(cashPostalMoney);
		tostr.append("]");
		return tostr.toString();
	}
}
