package com.pzj.trade.merch.common;

/**
 * 商品凭证类型.
 * @author GLG
 *
 */
public enum VerificationVourTypeEnum {

	/**
	 * 联系人按订单.
	 */
	CONTACT_BY_ORDER(1),
	/**
	 * 魔方码按订单
	 */
	MFCODE_BY_ORDER(2),

	/**
	 * 魔方码按规格
	 */
	MFCODE_BY_SKU(3),

	/**
	 * 魔方码按份数
	 */
	MFCODE_BY_NUM(4),
	/**
	 * 游客身份证按份数.
	 */
	CARDID_BY_NUM(5);

	/**
	 * 凭证类型.
	 */
	private int vourType;

	public int getVourType() {
		return vourType;
	}

	private VerificationVourTypeEnum(int vourType) {
		this.vourType = vourType;
	}

}
