package com.pzj.trade.withdraw.engine;

/**
 * 用户类型.
 * @author YRJ
 *
 */
public enum UserTypeEnum {
	Supplier(1, "供应商", true), Reseller(2, "分销商", true), MFTOUR(3, "魔方", false), PZJ(4, "票之家", false)/*, Proxy(5, "票之家")*/, SaasUser(
			6, "saas用户", true);

	private int value;

	private String name;

	/**
	 * 可提现操作的.
	 */
	private boolean withdrawable;

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public boolean isWithdrawable() {
		return withdrawable;
	}

	private UserTypeEnum(int value, String name, boolean withdrawable) {
		this.value = value;
		this.name = name;
		this.withdrawable = withdrawable;
	}

	public static UserTypeEnum getUserType(int value) {
		for (UserTypeEnum userType : UserTypeEnum.values()) {
			if (userType.getValue() == value) {
				return userType;
			}
		}
		throw new java.lang.IllegalStateException("用户类型错误. value: " + value);
	}
}
