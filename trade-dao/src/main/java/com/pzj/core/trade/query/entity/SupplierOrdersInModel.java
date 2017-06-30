package com.pzj.core.trade.query.entity;

import java.sql.Timestamp;
import java.util.Date;

import com.pzj.framework.entity.PageableRequestBean;

/**
 * 供应商订单列表查询参数.
 * @author YRJ
 *
 */
public class SupplierOrdersInModel extends PageableRequestBean {

	private static final long serialVersionUID = 1L;

	/** 供应商ID */
	private long supplier_id;

	/** 订单Id */
	private String order_id;

	/** 订单状态 */
	private int order_status;

	/**
	 * 是否直签.
	 * <ul>
	 * <li>0. 全部</li>
	 * <li>1. 直签</li>
	 * <li>2. 分销</li>
	 * </ul>
	 */
	private int direct = 0;

	/** 下单开始日期 */
	private Date start_date;

	/** 下单结束日期 */
	private Date end_date;

	/** 产品名称 */
	private String merch_name;

	/** '订单商品状态.0:可消费; 1: 已消费; 2: 待退款; 3: 已退款; 4:待确认；5:已完成*/
	private Integer merch_state;

	/** 商品类型 */
	private int category;

	/** 开始出游/入住时间 */
	private Timestamp start_visit_time;

	/** 结束出游/入住时间 */
	private Timestamp end_visit_time;

	/** 联系人 */
	private String contactee;

	/** 联系电话 */
	private String contact_mobile;

	/** 渠道 */
	private long channel_id;

	/** 分销商Id*/
	private long reseller_id;

	/** 退款状态（0 退款中   ） */
	private Integer is_refunding;

	public Integer getIs_refunding() {
		return is_refunding;
	}

	public void setIs_refunding(Integer is_refunding) {
		this.is_refunding = is_refunding;
	}

	public long getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(long channel_id) {
		this.channel_id = channel_id;
	}

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(long reseller_id) {
		this.reseller_id = reseller_id;
	}

	public long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(long supplier_id) {
		this.supplier_id = supplier_id;
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

	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
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

	public String getMerch_name() {
		return merch_name;
	}

	public void setMerch_name(String merch_name) {
		this.merch_name = merch_name;
	}

	public Integer getMerch_state() {
		return merch_state;
	}

	public void setMerch_state(Integer merch_state) {
		this.merch_state = merch_state;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public Timestamp getStart_visit_time() {
		return start_visit_time;
	}

	public void setStart_visit_time(Timestamp start_visit_time) {
		this.start_visit_time = start_visit_time;
	}

	public Timestamp getEnd_visit_time() {
		return end_visit_time;
	}

	public void setEnd_visit_time(Timestamp end_visit_time) {
		this.end_visit_time = end_visit_time;
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
}
