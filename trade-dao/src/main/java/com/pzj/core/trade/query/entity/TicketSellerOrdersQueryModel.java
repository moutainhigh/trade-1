package com.pzj.core.trade.query.entity;

import java.util.Date;
import java.util.List;

import com.pzj.framework.toolkit.Check;

/**
 * 售票员订单列表查询模型.
 * @author YRJ
 *
 */
public class TicketSellerOrdersQueryModel {

	private String order_id;

	/**
	 * 操作者ID（销售员）.
	 * <p>代表当前登录者, 只查询该销售员操作生成的订单.</p>
	 */
	private long operator_id;

	/**
	 * 订单状态.
	 * <ul>
	 * <li>1. 下单中</li>
	 * <li>10. 已支付</li>
	 * <li>20. 已取消</li>
	 * <li>30. 已退款</li>
	 * <li>40. 已完成</li>
	 * </ul>
	 */
	private int order_status;

	/**
	 * 商品状态.
	 * <ul>
	 * <li>-1. 不可用</li>
	 * <li>0. 待消费</li>
	 * <li>3. 已退款</li>
	 * <li>4. 待确认</li>
	 * <li>5. 已完成</li>
	 * </ul>
	 */
	private Integer merch_state;

	/**
	 * 退款中
	 */
	private boolean refunding;
	/** 
	 * 产品类型. 不对该值做合法性判断.
	 */
	private int merch_type;

	/**
	 * 产品名称, 支持模糊查询.
	 */
	private String merch_name;

	/** 分销商ID列表*/
	private List<Long> resellerIds;

	/** 联系人 */
	private String contactee;

	/** 联系电话 */
	private String contact_mobile;

	/** 联系人身份证号 */
	private String idcard_no;

	/** 开始出游/入住时间 */
	private Date start_travel_time;

	/** 结束出游/入住时间 */
	private Date end_travel_time;

	/** 下单开始日期 */
	private Date start_date;

	/** 下单结束日期 */
	private Date end_date;

	/**
	 * 团散.
	 * <ul>
	 * <li>-1. 全部</li>
	 * <li>1. 团票</li>
	 * <li>0. 散票</li>
	 * </ul>
	 */
	private String product_varie;

	/**
	 * 支付方式, 该查询条件只支持现金和后付方式的查询. 若该值为0, 则支付方式限定在现金和后付方式.
	 * <ul>
	 * <li>0. 全部</li>
	 * <li>5. 现金</li>
	 * <li>6. 后付（签单）</li>
	 * </ul>
	 */
	private int pay_way;

	/** 导游ID列表 */
	private List<Long> guide_ids;

	/** 二维码  */
	private List<Long> voucher_ids;

	public TicketSellerOrdersQueryModel() {
	}

	public TicketSellerOrdersQueryModel(String orderId) {
		this.order_id = orderId;
	}

	/**
	 * 是否为精准查询. 当为精准查询时, 只需要精准查询订单信息并根据返回值做合法性判断即可, 减少数据库操作.
	 * @return
	 */
	public boolean accurate() {
		return !Check.NuNStrStrict(order_id);
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public long getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(long operator_id) {
		this.operator_id = operator_id;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public Integer getMerch_state() {
		return merch_state;
	}

	public void setMerch_state(Integer merch_state) {
		this.merch_state = merch_state;
	}

	public int getMerch_type() {
		return merch_type;
	}

	public void setMerch_type(int merch_type) {
		this.merch_type = merch_type;
	}

	public String getMerch_name() {
		return merch_name;
	}

	public void setMerch_name(String merch_name) {
		this.merch_name = merch_name;
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

	public String getIdcard_no() {
		return idcard_no;
	}

	public void setIdcard_no(String idcard_no) {
		this.idcard_no = idcard_no;
	}

	public Date getStart_travel_time() {
		return start_travel_time;
	}

	public void setStart_travel_time(Date start_travel_time) {
		this.start_travel_time = start_travel_time;
	}

	public Date getEnd_travel_time() {
		return end_travel_time;
	}

	public void setEnd_travel_time(Date end_travel_time) {
		this.end_travel_time = end_travel_time;
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

	public String getProduct_varie() {
		return product_varie;
	}

	public void setProduct_varie(String product_varie) {
		this.product_varie = product_varie;
	}

	/**
	 * Getter method for property <tt>resellerIds</tt>.
	 * 
	 * @return property value of resellerIds
	 */
	public List<Long> getResellerIds() {
		return resellerIds;
	}

	/**
	 * Setter method for property <tt>resellerIds</tt>.
	 * 
	 * @param resellerIds value to be assigned to property resellerIds
	 */
	public void setResellerIds(List<Long> resellerIds) {
		this.resellerIds = resellerIds;
	}

	/**
	 * Getter method for property <tt>pay_way</tt>.
	 * 
	 * @return property value of pay_way
	 */
	public int getPay_way() {
		return pay_way;
	}

	/**
	 * Setter method for property <tt>pay_way</tt>.
	 * 
	 * @param pay_way value to be assigned to property pay_way
	 */
	public void setPay_way(int pay_way) {
		this.pay_way = pay_way;
	}

	/**
	 * Getter method for property <tt>guide_ids</tt>.
	 * 
	 * @return property value of guide_ids
	 */
	public List<Long> getGuide_ids() {
		return guide_ids;
	}

	/**
	 * Getter method for property <tt>refunding</tt>.
	 * 
	 * @return property value of refunding
	 */
	public boolean isRefunding() {
		return refunding;
	}

	/**
	 * Setter method for property <tt>refunding</tt>.
	 * 
	 * @param refunding value to be assigned to property refunding
	 */
	public void setRefunding(boolean refunding) {
		this.refunding = refunding;
	}

	/**
	 * Setter method for property <tt>guide_ids</tt>.
	 * 
	 * @param guide_ids value to be assigned to property guide_ids
	 */
	public void setGuide_ids(List<Long> guide_ids) {
		this.guide_ids = guide_ids;
	}

	public List<Long> getVoucher_ids() {
		return voucher_ids;
	}

	public void setVoucher_ids(List<Long> voucher_ids) {
		this.voucher_ids = voucher_ids;
	}
}
