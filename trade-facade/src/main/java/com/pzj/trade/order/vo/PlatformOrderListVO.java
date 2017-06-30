package com.pzj.trade.order.vo;

import java.util.List;

public class PlatformOrderListVO extends OrderListVO {
	private static final long serialVersionUID = 1L;

	/** 魔方订单ID列表 */
	private List<String> reseller_order_ids;

	/** 供应商订单ID列表 */
	private List<String> supplier_order_ids;

	/** 产品ID列表 */
	private List<Long> product_ids;

	/** 导游ID集合 */
	private List<Long> guide_ids;

	/** 代下单标志：1:不需要代下单；2：需要代下单；3已经代下单 ---可用于查询需要代下单订单*/
	private int agent_flag;

	/**是否可手动清算.0: 不可;1: 可手动。只有清算规则为固定时间规则时, 此值有效 */
	private Integer is_manual;

	/** '订单商品状态.0:可消费; 1: 已消费; 2: 待退款; 3: 已退款; -1: 不可用;4:待确认；5：已完成*/
	private List<Integer> merch_states;

	/** 是否强制退款订单*/
	private boolean isForceOrderList;

	/** 订单类型. 1: 景区; 2: 票; 3: 住宿; 4: 小交通; 5: 特产;6:一日游 */
	private int merch_type;

	/** 订单类型. 景区.线路、旅拍、包车、班车、特产、导游、住宿、特色餐饮*/
	private List<Integer> merchTypes;

	/**
	 * 查询类型:
	 * 
	 * supplierQuery订单查询列表（供应）
	 * supplierQueryDownLine订单列表查询(供应线下)
	 * supplierQueryOnLine订单列表查询(供应线上)
	 * suppplierQueryRefund退款订单列表查询（供应）
	 * suppplierQueryIsForceRefund强制退款申请订单列表
	 * suppplierQueryConfirm手动确认订单列表查询(供应)
	 * suppplierQueryCheck批量核销_检票订单列表（供应）
	 * */
	private String query_type;

	public int getMerch_type() {
		return merch_type;
	}

	public void setMerch_type(int merch_type) {
		this.merch_type = merch_type;
	}

	public List<Integer> getMerchTypes() {
		return merchTypes;
	}

	public void setMerchTypes(List<Integer> merchTypes) {
		this.merchTypes = merchTypes;
	}

	public String getQuery_type() {
		return query_type;
	}

	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}

	public void setForceOrderList(boolean isForceOrderList) {
		this.isForceOrderList = isForceOrderList;
	}

	public boolean getIsForceOrderList() {
		return isForceOrderList;
	}

	public void setIsForceOrderList(boolean isForceOrderList) {
		this.isForceOrderList = isForceOrderList;
	}

	public List<Long> getGuide_ids() {
		return guide_ids;
	}

	public void setGuide_ids(List<Long> guide_ids) {
		this.guide_ids = guide_ids;
	}

	public List<Integer> getMerch_states() {
		return merch_states;
	}

	public void setMerch_states(List<Integer> merch_states) {
		this.merch_states = merch_states;
	}

	public List<String> getReseller_order_ids() {
		return reseller_order_ids;
	}

	public void setReseller_order_ids(List<String> reseller_order_ids) {
		this.reseller_order_ids = reseller_order_ids;
	}

	public List<String> getSupplier_order_ids() {
		return supplier_order_ids;
	}

	public void setSupplier_order_ids(List<String> supplier_order_ids) {
		this.supplier_order_ids = supplier_order_ids;
	}

	public List<Long> getProduct_ids() {
		return product_ids;
	}

	public void setProduct_ids(List<Long> product_ids) {
		this.product_ids = product_ids;
	}

	public int getAgent_flag() {
		return agent_flag;
	}

	public void setAgent_flag(int agent_flag) {
		this.agent_flag = agent_flag;
	}

	public Integer getIs_manual() {
		return is_manual;
	}

	public void setIs_manual(Integer is_manual) {
		this.is_manual = is_manual;
	}

}
