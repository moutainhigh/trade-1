package com.pzj.trade.order.model;

import java.util.Date;

import com.pzj.framework.entity.PageableRequestBean;

/**
 * 供应商订单列表请求参数.
 * @author YRJ
 *
 */
public class SupplierOrdersReqModel extends PageableRequestBean {

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

	/** 订单类型. 景区.线路、旅拍、包车、班车、特产、导游、住宿、特色餐饮 */
	private int category;

	/** 出游/入住时间 */
	private Date visit_time;

	/** 联系人 */
	private String contactee;

	/** 联系电话 */
	private String contact_mobile;

	/** 渠道 */
	private long channel_id;

	/** 分销商Id*/
	private long reseller_id;
	/**是否直营 1是，0否*/
	private Integer isDirectSale;
	/** 销售端口 */
	private int sale_port;

	/** 团散 */
	private String product_varie;
	/** 支付方式 */
	private int pay_type;

	/** 导游ID */
	private long guide_id;

	/** 产品ID列表 */
	private long product_id;
	/** 导游手机 */
	private String guide_mobile;

	/** 二维码  */
	private String qr_code;

	/** 联系人身份证号 */
	private String idcard_no;
	/** 售票点 */
	private long salePiont;
	/** 售票员id */
	private long salePersonId;
	/**  订单创建时间 */
	private Date create_time;
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

	public int getSale_port() {
		return sale_port;
	}

	public void setSale_port(int sale_port) {
		this.sale_port = sale_port;
	}

	public String getProduct_varie() {
		return product_varie;
	}

	public void setProduct_varie(String product_varie) {
		this.product_varie = product_varie;
	}

	public int getPay_type() {
		return pay_type;
	}

	public void setPay_type(int pay_type) {
		this.pay_type = pay_type;
	}

	public long getGuide_id() {
		return guide_id;
	}

	public void setGuide_id(long guide_id) {
		this.guide_id = guide_id;
	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}

	public String getGuide_mobile() {
		return guide_mobile;
	}

	public void setGuide_mobile(String guide_mobile) {
		this.guide_mobile = guide_mobile;
	}

	public String getQr_code() {
		return qr_code;
	}

	public void setQr_code(String qr_code) {
		this.qr_code = qr_code;
	}

	public String getIdcard_no() {
		return idcard_no;
	}

	public void setIdcard_no(String idcard_no) {
		this.idcard_no = idcard_no;
	}

	public long getSalePiont() {
		return salePiont;
	}

	public void setSalePiont(long salePiont) {
		this.salePiont = salePiont;
	}

	public long getSalePersonId() {
		return salePersonId;
	}

	public void setSalePersonId(long salePersonId) {
		this.salePersonId = salePersonId;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getQuery_type() {
		return query_type;
	}

	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}

	public Integer getIsDirectSale() {
		return isDirectSale;
	}

	public void setIsDirectSale(Integer isDirectSale) {
		this.isDirectSale = isDirectSale;
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

	public Date getVisit_time() {
		return visit_time;
	}

	public void setVisit_time(Date visit_time) {
		this.visit_time = visit_time;
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
