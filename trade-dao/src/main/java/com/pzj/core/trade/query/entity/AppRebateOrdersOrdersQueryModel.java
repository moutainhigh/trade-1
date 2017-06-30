/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.query.entity;

import java.util.Date;
import java.util.List;

/**
 * app返利金订单列表查询模型.
 * @author GLG
 * @version $Id: AppRebateOrdersOrdersQueryModel.java, v 0.1 2017年3月14日 下午5:30:10 Administrator Exp $
 */
public class AppRebateOrdersOrdersQueryModel {

	/**订单id*/
	private String order_id;
	/**返利来源：1微店返利、2卖油翁返利*/
	private int rebate_form_type;
	/**分销商id*/
	private long reseller_id;
	/**订单创建开始时间*/
	private Date create_start_time;
	/**订单创建结束时间*/
	private Date create_end_time;

	/**订单id集合*/
	private List<String> order_ids;

	/** 产品名称*/
	private String supName;
	/**订单状态*/
	private int orderStatus;

	/**订单状态*/
	private List<Integer> order_status_list;

	public List<Integer> getOrder_status_list() {
		return order_status_list;
	}

	public void setOrder_status_list(List<Integer> order_status_list) {
		this.order_status_list = order_status_list;
	}

	public String getOrder_id() {
		return order_id;
	}

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<String> getOrder_ids() {
		return order_ids;
	}

	public void setOrder_ids(List<String> order_ids) {
		this.order_ids = order_ids;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public int getRebate_form_type() {
		return rebate_form_type;
	}

	public void setRebate_form_type(int rebate_form_type) {
		this.rebate_form_type = rebate_form_type;
	}

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(long reseller_id) {
		this.reseller_id = reseller_id;
	}

	public Date getCreate_start_time() {
		return create_start_time;
	}

	public void setCreate_start_time(Date create_start_time) {
		this.create_start_time = create_start_time;
	}

	public Date getCreate_end_time() {
		return create_end_time;
	}

	public void setCreate_end_time(Date create_end_time) {
		this.create_end_time = create_end_time;
	}
	//
	//	public int getRebate_state() {
	//		return rebate_state;
	//	}
	//
	//	public void setRebate_state(int rebate_state) {
	//		this.rebate_state = rebate_state;
	//	}

}
