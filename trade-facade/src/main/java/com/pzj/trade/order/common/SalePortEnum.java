package com.pzj.trade.order.common;

/**
 * 销售端口.
 * @author YRJ
 *
 */
public enum SalePortEnum {
	/**
	 * 线下窗口.
	 */
	OFFLINE_WINDOW(1, false, "线下窗口", "线下窗口"),
	/**
	 * 二维码微店.
	 */
	TWODIM_CODE_MICSHOP(2, true, "二维码微店", "微店"),
	/**
	 * 旅行社PC端.
	 */
	TRAVEL_AGENCY_PC(3, true, "PC分销端", "PC分销端"),
	/**
	 * 导游APP.
	 */
	TOUR_GUIDE_APP(4, true, "导游APP", "卖游翁App"),
	/**
	 * 商户APP.
	 */
	MERCHANT_APP(5, true, "商户APP", "卖游翁App"),
	/**
	 * 导游微店.
	 */
	TOUR_GUIDE_MICSHOP(6, true, "导游微店", "微店"),
	/**
	 * 商户微店.
	 */
	MERCHANT_MICSHOP(7, true, "商户微店", "微店"),
	/**
	 * OTA.
	 */
	OTA(8, true, "OTA", "OTA对接"),
	/**
	 * APP.
	 */
	APP(9, true, "APP", "卖游翁App");

	private int value;

	/** 是否为线上订单. */
	private boolean online;

	private String name;

	private String group;

	public int getValue() {
		return value;
	}

	public boolean isOnline() {
		return online;
	}

	public String getName() {
		return name;
	}

	public String getGroup() {
		return group;
	}

	private SalePortEnum(int value, boolean online, String name, String group) {
		this.value = value;
		this.online = online;
		this.name = name;
		this.group = group;
	}

	/**
	 * 获取销售端口.
	 * @param value
	 * @return
	 */
	public static SalePortEnum getSalePort(int value) {
		for (SalePortEnum sale_port : SalePortEnum.values()) {
			if (sale_port.getValue() == value) {
				return sale_port;
			}
		}
		/*throw new IllegalArgumentException("销售端口有误");*/
		return null;
	}

	public static boolean isMicShop(int value) {
		if (value == TWODIM_CODE_MICSHOP.getValue() || value == TOUR_GUIDE_MICSHOP.getValue()
				|| value == MERCHANT_MICSHOP.getValue()) {
			return true;
		}
		return false;
	}
}
