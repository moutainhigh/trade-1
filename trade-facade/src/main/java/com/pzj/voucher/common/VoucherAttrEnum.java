package com.pzj.voucher.common;

public enum VoucherAttrEnum {

	ATTR_PRODUCT_INFO("productInfo", "产品属性"), //格式:productId, productName, productNum, 景区名称, 团散, 游玩开始时间or演出开始时间,游玩结束时间or演出结束时间
	//ATTR_ADDRESS("address", "收货地址"), //  特产收货地址
	ATTR_STOCK_ID("stockId", "库存Id"), //库存ID
	ATTR_STOCK_INFO("stockInfo", "库存信息"), //库存信息
	ATTR_PRODUCT("productId", "产品ID"), //
	ATTR_PRODUCT_NUM("productNum", "产品数量"), //
	//ATTR_PRODUCT_GROUP_ID("productGroupId", "产品组Id"), //
	ATTR_TICKET_SCENIC_INFO("scenic", "门票景区信息"), //格式:
	ATTR_TICKET_SCENIC_ID_INFO("scenicId", "门票景区Id信息"), //格式:
	ATTR_TICKET_SCENIC_NAME_INFO("scenicName", "门票景区Id信息"), //格式:
	ATTR_TICKET_VARIE_INFO("ticketVarie", "门票团散信息"), //格式:
	//ATTR_ART_REGION_INFO("region", "演艺区域信息"), //格式:
	ATTR_ART_SCREENING_INFO("screening", "演艺场次信息"), //格式:
	ATTR_ART_SEAT_INFO("seatNumbers", "演艺座位信息"), //格式:
	ATTR_ART_STOCK_ID("stockId", "演艺库存Id"), ATTR_ART_THEATER_INFO("theaterInfo", "演艺信息"), // 格式{scenicId:scenicId, productId:productId, screening:screening,region:region, seatNumbers:seatNumbers}

	;

	VoucherAttrEnum(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	private String value;
	private String desc;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
