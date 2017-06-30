package com.pzj.core.trade.query.entity;

import java.util.Date;
import java.util.List;

/**
 * 查询SaaS分销订单列表参数.
 * @author GLG
 *
 */
public class ResellerOrdersQueryModel {

	/** 订单Id */
	private String order_id;
	/** 订单状态 */
	private int order_status;
	/** 订单状态 列表*/
	private List<Integer> order_status_list;

	/** 下单开始日期 */
	private Date start_date;

	/** 下单结束日期 */
	private Date end_date;

	/** 联系人 */
	private String contactee;

	/** 联系电话 */
	private String contact_mobile;

	/** 产品名称 */
	private String merch_name;

	/** 操作人ID */
	private long operator_id;

	/** 团散 */
	private String product_varie;
	/** 销售端口 */
	private int sale_port;

	/** 销售端口列表 ，新增*/
	private List<Integer> sale_port_list;
	/** '订单商品状态.0:可消费; 1: 已消费; 2: 待退款; 3: 已退款; -1: 不可用;4:待确认；5：已完成*/
	private Integer merch_state;

	/** 分销商Id*/
	private long reseller_id;

	/** 供应商ID */
	private long p_supplier_id;

	/** 初始供应商ID */
	private long supplier_id;

	/** 出游/入住开始时间 */
	private String visit_start_time;
	/** 出游/入住结束时间 */
	private String visit_end_time;

	/**
	 * 支付方式. 0: 纯余额; 1. 支付宝; 2. 微信; 4: 混合支付; 5: 现金; 6: 后付.

	 */
	private Integer pay_way;

	/**
	 * 支付方式. 0: 纯余额; 1. 支付宝; 2. 微信; 4: 混合支付; 5: 现金; 6: 后付.

	 */
	private List<Integer> pay_ways;

	/** 商品类型 */
	private int merch_type;

	/** 产品ID */
	private long product_id;

	/** 产品ID集合 */
	private List<Long> product_ids;

	/** 分销商Id列表，用于接收多个分销商ID*/
	private List<Long> reseller_ids;

	/** 供应商Id列表，用于接收多个供应商ID*/
	private List<Long> supplier_ids;

	/** 供应商Id列表，用于接收多个供应商ID*/
	private List<Long> p_supplier_ids;

	/** 退款状态（0 退款中   ） */
	private Integer is_refunding;

	/** 是否需要确认. 1: 不需要; 2: 需要', */
	private int need_confirm;

	/**商品清算类型. 1: 核销自动清算; 2: 核销之后固定时间核销 */
	private int clear_type;

	/**
	 * 根据条件里是否有产品的属性来设置值。如果有，设置为true，映射语句会关联merch表。如果没有，不关联，节省时间。
	 */
	private boolean haveMerchFilter = false;

	/**
	 * 查询类型:
	 * 
	 *resellerQueryPayReslut 需要付款订单（分销）
	 *resellerQueryDifferencePayReslut 需要补差订单（分销）
	 * */
	private String query_type;

	/** 订单类型. 景区.线路、旅拍、包车、班车、特产、导游、住宿、特色餐饮*/
	private List<Integer> merchTypes;

	/** '订单商品状态.0:可消费; 1: 已消费; 2: 待退款; 3: 已退款; -1: 不可用;4:待确认；5：已完成*/
	private List<Integer> merch_states;

	/** 检票开始日期 */
	private Date confirm_date_start;

	/** 检票结束日期 */
	private Date confirm_date_end;
	/** 付款锁. 0: 未支付; 1: 已锁定,2:支付成功 */
	private Integer pay_state;

	/**
	 * 根据条件里是否有产品的属性来设置值。如果有，设置为true，映射语句会关联merch表。如果没有，不关联，节省时间。
	 */
	private boolean haveParentOrderFilter = false;
	/**判断是否需要拿到最初是的分销订单1、是 0、否*/
	private Integer isRootResellerOrder;

	/** 列表结算是否按照创建时间降序排列  */
	private boolean sortDesc = true;

	public boolean getSortDesc() {
		return sortDesc;
	}

	public void setSortDesc(boolean sortDesc) {
		this.sortDesc = sortDesc;
	}

	private String transaction_id;

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public List<Integer> getPay_ways() {
		return pay_ways;
	}

	public void setPay_ways(List<Integer> pay_ways) {
		this.pay_ways = pay_ways;
	}

	public List<Long> getProduct_ids() {
		return product_ids;
	}

	public void setProduct_ids(List<Long> product_ids) {
		this.product_ids = product_ids;
	}

	public Integer getIsRootResellerOrder() {
		return isRootResellerOrder;
	}

	public void setIsRootResellerOrder(Integer isRootResellerOrder) {
		this.isRootResellerOrder = isRootResellerOrder;
	}

	public Integer getPay_state() {
		return pay_state;
	}

	public void setPay_state(Integer pay_state) {
		this.pay_state = pay_state;
	}

	public boolean getHaveParentOrderFilter() {
		return haveParentOrderFilter;
	}

	public void setHaveParentOrderFilter(boolean haveParentOrderFilter) {
		this.haveParentOrderFilter = haveParentOrderFilter;
	}

	public Integer getIs_refunding() {
		return is_refunding;
	}

	public void setIs_refunding(Integer is_refunding) {
		this.is_refunding = is_refunding;
	}

	public int getNeed_confirm() {
		return need_confirm;
	}

	public void setNeed_confirm(int need_confirm) {
		this.need_confirm = need_confirm;
	}

	public int getClear_type() {
		return clear_type;
	}

	public void setClear_type(int clear_type) {
		this.clear_type = clear_type;
	}

	public List<Integer> getMerchTypes() {
		return merchTypes;
	}

	public void setMerchTypes(List<Integer> merchTypes) {
		this.merchTypes = merchTypes;
	}

	public List<Integer> getMerch_states() {
		return merch_states;
	}

	public void setMerch_states(List<Integer> merch_states) {
		this.merch_states = merch_states;
	}

	public Date getConfirm_date_start() {
		return confirm_date_start;
	}

	public void setConfirm_date_start(Date confirm_date_start) {
		this.confirm_date_start = confirm_date_start;
	}

	public Date getConfirm_date_end() {
		return confirm_date_end;
	}

	public void setConfirm_date_end(Date confirm_date_end) {
		this.confirm_date_end = confirm_date_end;
	}

	public boolean getHaveMerchFilter() {
		return haveMerchFilter;
	}

	public void setHaveMerchFilter(boolean haveMerchFilter) {
		this.haveMerchFilter = haveMerchFilter;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public List<Integer> getOrder_status_list() {
		return order_status_list;
	}

	public void setOrder_status_list(List<Integer> order_status_list) {
		this.order_status_list = order_status_list;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getContactee() {
		return contactee;
	}

	public void setContactee(String contactee) {
		this.contactee = contactee;
	}

	public String getContact_mobile() {
		return contact_mobile;
	}

	public void setContact_mobile(String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}

	public String getMerch_name() {
		return merch_name;
	}

	public void setMerch_name(String merch_name) {
		this.merch_name = merch_name;
	}

	public long getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(long operator_id) {
		this.operator_id = operator_id;
	}

	public String getProduct_varie() {
		return product_varie;
	}

	public void setProduct_varie(String product_varie) {
		this.product_varie = product_varie;
	}

	public int getSale_port() {
		return sale_port;
	}

	public void setSale_port(int sale_port) {
		this.sale_port = sale_port;
	}

	public List<Integer> getSale_port_list() {
		return sale_port_list;
	}

	public void setSale_port_list(List<Integer> sale_port_list) {
		this.sale_port_list = sale_port_list;
	}

	public Integer getMerch_state() {
		return merch_state;
	}

	public void setMerch_state(Integer merch_state) {
		this.merch_state = merch_state;
	}

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(long reseller_id) {
		this.reseller_id = reseller_id;
	}

	public long getP_supplier_id() {
		return p_supplier_id;
	}

	public void setP_supplier_id(long p_supplier_id) {
		this.p_supplier_id = p_supplier_id;
	}

	public long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getVisit_start_time() {
		return visit_start_time;
	}

	public void setVisit_start_time(String visit_start_time) {
		this.visit_start_time = visit_start_time;
	}

	public String getVisit_end_time() {
		return visit_end_time;
	}

	public void setVisit_end_time(String visit_end_time) {
		this.visit_end_time = visit_end_time;
	}

	public Integer getPay_way() {
		return pay_way;
	}

	public void setPay_way(Integer pay_way) {
		this.pay_way = pay_way;
	}

	public int getMerch_type() {
		return merch_type;
	}

	public void setMerch_type(int merch_type) {
		this.merch_type = merch_type;
	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}

	public List<Long> getReseller_ids() {
		return reseller_ids;
	}

	public void setReseller_ids(List<Long> reseller_ids) {
		this.reseller_ids = reseller_ids;
	}

	public List<Long> getSupplier_ids() {
		return supplier_ids;
	}

	public void setSupplier_ids(List<Long> supplier_ids) {
		this.supplier_ids = supplier_ids;
	}

	public List<Long> getP_supplier_ids() {
		return p_supplier_ids;
	}

	public void setP_supplier_ids(List<Long> p_supplier_ids) {
		this.p_supplier_ids = p_supplier_ids;
	}

	public String getQuery_type() {
		return query_type;
	}

	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}

}
