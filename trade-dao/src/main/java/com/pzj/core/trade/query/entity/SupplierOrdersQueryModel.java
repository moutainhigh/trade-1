package com.pzj.core.trade.query.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pzj.framework.entity.PageableRequestBean;
import com.pzj.framework.toolkit.Check;

/**
 * SaaS供应订单列表查询参数.
 * @author GLG
 *
 */
public class SupplierOrdersQueryModel extends PageableRequestBean {

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

	/** 操作人ID */
	private long operator_id;

	/** 联系人身份证号 */
	private String idcard_no;

	/** 订单状态 列表*/
	private List<Integer> order_status_list;

	/** 团散 */
	private String product_varie;

	/** 销售端口 */
	private int sale_port;

	/** 是否需要确认. 1: 不需要确认; 2: 待确认;3:已确认---可用于查询待确认订单*/
	private int need_confirm;

	/** 产品ID列表 */
	private long product_id;

	/** 精确日期（仅限供订查询有效期开始日期的数据使用）,出游开始日期  */
	private String accurateStartTime;
	/** 精确日期（仅限供订查询有效期开始日期的数据使用）,出游结束日期  */
	private String accurateEndTime;

	/** 订单类型. 1: 景区; 2: 票; 3: 住宿; 4: 小交通; 5: 特产;6:一日游 */
	private int merch_type;

	/** '订单商品状态.0:可消费; 1: 已消费; 2: 待退款; 3: 已退款; -1: 不可用;4:待确认；5：已完成*/
	private List<Integer> merch_states;

	/**
	 * 根据条件里是否有产品的属性来设置值。如果有，设置为true，映射语句会关联merch表。如果没有，不关联，节省时间。
	 */
	private boolean haveMerchFilter = false;

	/** 订单类型. 景区.线路、旅拍、包车、班车、特产、导游、住宿、特色餐饮*/
	private List<Integer> merchTypes;

	/** 导游ID */
	private long guide_id;

	/** 导游ID集合 */
	private List<Long> guide_ids;

	/** 分销商Id列表，用于接收多个分销商ID*/
	private List<Long> reseller_ids;

	/**是否直营 1是，0否*/
	private Integer is_direct_sale;

	/** 售票点 */
	private long sale_piont;
	/** 售票员id */
	private long sale_person_id;

	/**订单来源 0. 普通订单 1. 预约单 2. 免票 3. 特价票*/
	private Integer order_source;

	/**售票员id集合*/
	private List<Long> sale_person_ids;

	/**售票点id集合*/
	private List<Long> sale_piont_ids;
	/**支付方式. 0: 纯余额; 1. 支付宝; 2. 微信; 4: 混合支付; 5: 现金; 6: 后付*/
	private Integer pay_way;

	/** 二维码  或者身份证号*/
	private String qr_code;

	private List<Long> voucher_ids;

	/** 是否是已强制退款订单*/
	private boolean isforceOrder = false;
	/**线上1、线下0*/
	private Integer is_online;
	/**是否特产1、是 0、否*/
	private Integer is_native_product;
	/**1、退款审核 2、退款审核结果*/
	private int refund_order_type;

	/** 销售端口列表*/
	private List<Integer> sale_port_list;

	/** 订单ID */
	private List<String> order_ids;

	/** 退款申请ID */
	private List<String> refund_ids;

	/** 渠道id集合 */
	private List<Long> channel_ids;

	/**
	 * 支付方式. 0: 纯余额; 1. 支付宝; 2. 微信; 4: 混合支付; 5: 现金; 6: 后付.

	 */
	private List<Integer> pay_ways;

	private String transaction_id;
	/**  产品id集合 */
	private List<Long> product_ids;
	/**   查询类型*/
	private int query_type;

	public int getQuery_type() {
		return query_type;
	}

	public void setQuery_type(int query_type) {
		this.query_type = query_type;
	}

	public List<Long> getProduct_ids() {
		return product_ids;
	}

	public void setProduct_ids(List<Long> product_ids) {
		this.product_ids = product_ids;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public List<Long> getSale_piont_ids() {
		return sale_piont_ids;
	}

	public void setSale_piont_ids(List<Long> sale_piont_ids) {
		this.sale_piont_ids = sale_piont_ids;
	}

	public List<Integer> getPay_ways() {
		return pay_ways;
	}

	public void setPay_ways(List<Integer> pay_ways) {
		this.pay_ways = pay_ways;
	}

	public List<Long> getChannel_ids() {
		return channel_ids;
	}

	public void setChannel_ids(List<Long> channel_ids) {
		this.channel_ids = channel_ids;
	}

	/** 列表结算是否按照创建时间降序排列  ---待确认、代下单、手动核销的订单列表查询是正序查询，所以请设置为false*/
	private boolean sortDesc = true;

	public boolean getSortDesc() {
		return sortDesc;
	}

	public void setSortDesc(boolean sortDesc) {
		this.sortDesc = sortDesc;
	}

	public List<String> getOrder_ids() {
		return order_ids;
	}

	public void setOrder_ids(List<String> order_ids) {
		this.order_ids = order_ids;
	}

	public List<String> getRefund_ids() {
		return refund_ids;
	}

	public void setRefund_ids(List<String> refund_ids) {
		this.refund_ids = refund_ids;
	}

	public int getRefund_order_type() {
		return refund_order_type;
	}

	public void setRefund_order_type(int refund_order_type) {
		this.refund_order_type = refund_order_type;
	}

	public long getSale_piont() {
		return sale_piont;
	}

	public void setSale_piont(long sale_piont) {
		this.sale_piont = sale_piont;
	}

	public long getSale_person_id() {
		return sale_person_id;
	}

	public void setSale_person_id(long sale_person_id) {
		this.sale_person_id = sale_person_id;
	}

	public List<Long> getSale_person_ids() {
		return sale_person_ids;
	}

	public void setSale_person_ids(List<Long> sale_person_ids) {
		this.sale_person_ids = sale_person_ids;
	}

	public List<Integer> getSale_port_list() {
		return sale_port_list;
	}

	public void setSale_port_list(List<Integer> sale_port_list) {
		this.sale_port_list = sale_port_list;
	}

	public Integer getIs_online() {
		return is_online;
	}

	public void setIs_online(Integer is_online) {
		this.is_online = is_online;
	}

	public Integer getIs_native_product() {
		return is_native_product;
	}

	public void setIs_native_product(Integer is_native_product) {
		this.is_native_product = is_native_product;
	}

	public boolean isIsforceOrder() {
		return isforceOrder;
	}

	public void setIsforceOrder(boolean isforceOrder) {
		this.isforceOrder = isforceOrder;
	}

	public List<Long> getVoucher_ids() {
		return voucher_ids;
	}

	public void setVoucher_ids(List<Long> voucher_ids) {
		this.voucher_ids = voucher_ids;
	}

	public void setVoucherIds(List<Long> voucher_ids) {
		if (Check.NuNCollections(voucher_ids)) {
			voucher_ids = new ArrayList<Long>();
			voucher_ids.add(-1l);
		}
		this.setVoucher_ids(voucher_ids);
	}

	public String getQr_code() {
		return qr_code;
	}

	public void setQr_code(String qr_code) {
		this.qr_code = qr_code;
	}

	public Integer getOrder_source() {
		return order_source;
	}

	public void setOrder_source(Integer order_source) {
		this.order_source = order_source;
	}

	public Integer getPay_way() {
		return pay_way;
	}

	public void setPay_way(Integer pay_way) {
		this.pay_way = pay_way;
	}

	public Integer getIs_direct_sale() {
		return is_direct_sale;
	}

	public void setIs_direct_sale(Integer is_direct_sale) {
		this.is_direct_sale = is_direct_sale;
	}

	public List<Long> getReseller_ids() {
		return reseller_ids;
	}

	public void setReseller_ids(List<Long> reseller_ids) {
		this.reseller_ids = reseller_ids;
	}

	public long getGuide_id() {
		return guide_id;
	}

	public void setGuide_id(long guide_id) {
		this.guide_id = guide_id;
	}

	public List<Long> getGuide_ids() {
		return guide_ids;
	}

	public void setGuide_ids(List<Long> guide_ids) {
		this.guide_ids = guide_ids;
	}

	public List<Integer> getMerchTypes() {
		return merchTypes;
	}

	public void setMerchTypes(List<Integer> merchTypes) {
		this.merchTypes = merchTypes;
	}

	public boolean isHaveMerchFilter() {
		return haveMerchFilter;
	}

	public void setHaveMerchFilter(boolean haveMerchFilter) {
		this.haveMerchFilter = haveMerchFilter;
	}

	public int getMerch_type() {
		return merch_type;
	}

	public void setMerch_type(int merch_type) {
		this.merch_type = merch_type;
	}

	public List<Integer> getMerch_states() {
		return merch_states;
	}

	public void setMerch_states(List<Integer> merch_states) {
		this.merch_states = merch_states;
	}

	public String getAccurateStartTime() {
		return accurateStartTime;
	}

	public void setAccurateStartTime(String accurateStartTime) {
		this.accurateStartTime = accurateStartTime;
	}

	public String getAccurateEndTime() {
		return accurateEndTime;
	}

	public void setAccurateEndTime(String accurateEndTime) {
		this.accurateEndTime = accurateEndTime;
	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}

	public List<Integer> getOrder_status_list() {
		return order_status_list;
	}

	public void setOrder_status_list(List<Integer> order_status_list) {
		this.order_status_list = order_status_list;
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

	public int getNeed_confirm() {
		return need_confirm;
	}

	public void setNeed_confirm(int need_confirm) {
		this.need_confirm = need_confirm;
	}

	public String getIdcard_no() {
		return idcard_no;
	}

	public void setIdcard_no(String idcard_no) {
		this.idcard_no = idcard_no;
	}

	public long getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(long operator_id) {
		this.operator_id = operator_id;
	}

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
