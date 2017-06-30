package com.pzj.trade.order.entity;

/**
 * 游客编辑模型.
 * @author YRJ
 *
 */
public class TouristEditModel {

	private long tourist_id;

	private String order_id;

	private String name;

	private String mobile;

	private String idcard;

	public long getTourist_id() {
		return tourist_id;
	}

	public void setTourist_id(final long tourist_id) {
		this.tourist_id = tourist_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(final String order_id) {
		this.order_id = order_id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(final String mobile) {
		this.mobile = mobile;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(final String idcard) {
		this.idcard = idcard;
	}
}
