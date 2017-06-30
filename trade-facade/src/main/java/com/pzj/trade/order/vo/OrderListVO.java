package com.pzj.trade.order.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单列表查询参数.
 * @author YRJ
 *
 */
public class OrderListVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private static int MAX_PAGESIZE = 20;

	/** 当前页码. */
	private int current_page = 1;

	/** 每页显示的最大数量, 默认为10 */
	private int page_size = MAX_PAGESIZE;
	/** 订单Id */
	private String order_id;
	private String transactionId;
	/** 订单状态 */
	private int order_status;
	/** 订单状态 列表*/
	private List<Integer> order_status_list;

	/** 下单开始日期 */
	private Date start_date;

	/** 下单结束日期 */
	private Date end_date;

	/** 检票开始日期 */
	private Date confirm_date_start;

	/** 检票结束日期 */
	private Date confirm_date_end;

	@Deprecated
	private int category;
	@Deprecated
	private List<Integer> categoryList;

	/** 联系人 */
	private String contactee;

	/** 联系电话 */
	private String contact_mobile;
	/** 联系人身份证号 */
	private String idcard_no;

	/** 产品名称 */
	private String merch_name;

	/** 操作人ID */
	private long operator_id;

	/** 渠道 */
	private long channel_id;
	/** 渠道id集合 */
	private List<Long> channel_ids;

	/** 列表结算是否按照创建时间降序排列  ---待确认、代下单、手动清算的订单列表查询是正序查询，所以请设置为false*/
	private boolean sortDesc = true;

	public boolean getSortDesc() {
		return sortDesc;
	}

	public void setSortDesc(boolean sortDesc) {
		this.sortDesc = sortDesc;
	}

	public List<Long> getChannel_ids() {
		return channel_ids;
	}

	public void setChannel_ids(List<Long> channel_ids) {
		this.channel_ids = channel_ids;
	}

	/** 团散 */
	private String product_varie;
	/** 订单来源：1、线下窗口 3、PC分销端  5、商户APP 7、商户微店 */
	private int sale_port;

	/** 订单来源列表：1、线下窗口 3、PC分销端  5、商户APP 7、商户微店*/
	private List<Integer> sale_port_list;
	/** '订单商品状态.0:可消费; 1: 已消费; 2: 待退款; 3: 已退款; -1: 不可用;4:待确认；5：已完成*/
	private Integer merch_state;

	/** 是否需要确认. 1: 不需要确认; 2: 待确认;3:已确认---可用于查询待确认订单*/
	private int need_confirm;

	/**是否景区端 ：true，景区端，只显示已完成订单；false，通用端口，可以显示各种类型的订单,默认false*/
	private boolean isScenic = false;
	/** 分销商Id*/
	private long reseller_id;
	/** 分销商Id列表，用于接收多个分销商ID*/
	private List<Long> reseller_ids;

	/** 供应商ID */
	private long supplier_id;
	/** 供应商Id列表，用于接收多个供应商ID*/
	private List<Long> supplier_ids;
	private List<Long> voucher_ids;
	/** 出游/入住开始时间 */
	private Date visit_start_time;
	/** 出游/入住结束时间 */
	private Date visit_end_time;
	/** 二维码  或者身份证号*/
	private String qr_code;

	/**是否直签，1-直签，2-非直签（即魔方)*/
	@Deprecated
	private int is_direct;
	@Deprecated
	private Integer online_pay;

	/** 售票点 */
	private long salePiont;
	/** 售票员id */
	private long salePersonId;

	/**是否直营 1是，0否*/
	private Integer isDirectSale;

	/** 导游ID */
	private long guide_id;

	/** 产品ID列表 */
	private long product_id;

	/**售卖方式 0. 普通订单 1. 预约单 2. 免票 3. 特价票*/
	private Integer order_source;

	/**售票员id集合*/
	private List<Long> salePersonIds;

	/**售票点id集合*/
	private List<Long> salePiontIds;
	/**支付方式. 1：第三方/余额 2：签单 3：现金*/
	private Integer payWay;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public List<Long> getSalePiontIds() {
		return salePiontIds;
	}

	public void setSalePiontIds(List<Long> salePiontIds) {
		this.salePiontIds = salePiontIds;
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

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public Integer getOrder_source() {
		return order_source;
	}

	public void setOrder_source(Integer order_source) {
		this.order_source = order_source;
	}

	public Integer getIsDirectSale() {
		return isDirectSale;
	}

	public void setIsDirectSale(Integer isDirectSale) {
		this.isDirectSale = isDirectSale;
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

	public List<Long> getSalePersonIds() {
		return salePersonIds;
	}

	public void setSalePersonIds(List<Long> salePersonIds) {
		this.salePersonIds = salePersonIds;
	}

	public void setScenic(boolean isScenic) {
		this.isScenic = isScenic;
	}

	public int getIs_direct() {
		return is_direct;
	}

	public void setIs_direct(int is_direct) {
		this.is_direct = is_direct;
	}

	public Integer getOnline_pay() {
		return online_pay;
	}

	public void setOnline_pay(Integer online_pay) {
		this.online_pay = online_pay;
	}

	public int getCurrent_page() {
		return current_page <= 0 ? 1 : current_page;
	}

	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}

	public int getPage_index() {
		return (getCurrent_page() - 1) * getPage_size();
	}

	public int getPage_size() {
		return page_size <= 0 ? MAX_PAGESIZE : (page_size > MAX_PAGESIZE ? MAX_PAGESIZE : page_size);
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
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

	public List<Long> getVoucher_ids() {
		return voucher_ids;
	}

	public void setVoucher_ids(List<Long> voucher_ids) {
		this.voucher_ids = voucher_ids;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
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

	public List<Integer> getOrder_status_list() {
		return order_status_list;
	}

	public void setOrder_status_list(List<Integer> order_status_list) {
		this.order_status_list = order_status_list;
	}

	public List<Integer> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Integer> categoryList) {
		this.categoryList = categoryList;
	}

	public long getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(long channel_id) {
		this.channel_id = channel_id;
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

	public String getIdcard_no() {
		return idcard_no;
	}

	public void setIdcard_no(String idcard_no) {
		this.idcard_no = idcard_no;
	}

	public Integer getMerch_state() {
		return merch_state;
	}

	public void setMerch_state(Integer merch_state) {
		this.merch_state = merch_state;
	}

	public int getNeed_confirm() {
		return need_confirm;
	}

	public void setNeed_confirm(int need_confirm) {
		this.need_confirm = need_confirm;
	}

	public boolean getIsScenic() {
		return isScenic;
	}

	public void setIsScenic(boolean isScenic) {
		this.isScenic = isScenic;
	}

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(long reseller_id) {
		this.reseller_id = reseller_id;
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

	public long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public Date getVisit_start_time() {
		return visit_start_time;
	}

	public void setVisit_start_time(Date visit_start_time) {
		this.visit_start_time = visit_start_time;
	}

	public Date getVisit_end_time() {
		return visit_end_time;
	}

	public void setVisit_end_time(Date visit_end_time) {
		this.visit_end_time = visit_end_time;
	}

	public String getQr_code() {
		return qr_code;
	}

	public void setQr_code(String qr_code) {
		this.qr_code = qr_code;
	}

	public static int getMAX_PAGESIZE() {
		return MAX_PAGESIZE;
	}

	public static void setMAX_PAGESIZE(int mAX_PAGESIZE) {
		MAX_PAGESIZE = mAX_PAGESIZE;
	}

	public void setPage_index(int page_index) {
	}

	public List<Integer> getSale_port_list() {
		return sale_port_list;
	}

	public void setSale_port_list(List<Integer> sale_port_list) {
		this.sale_port_list = sale_port_list;
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

}
