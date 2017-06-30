package com.pzj.trade.order.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pzj.framework.toolkit.Check;

/**
 * 查询订单列表参数.
 * @author YRJ
 *
 */
public class OrderListParameter {

	private String order_id;

	/** 供应商ID */
	private long supplier_id;

	/** 供应商Id列表，用于接收多个供应商ID*/
	private List<Long> supplier_ids;

	/** 分销商ID */
	private long reseller_id;

	/** 分销商Id列表，用于接收多个分销商ID*/
	private List<Long> reseller_ids;

	/** 订单状态 */
	private int order_status;

	/** 是否分销端查询，默认fasle*/
	private boolean isReseller = false;

	/** 下单开始日期 */
	private Date start_date;

	/** 下单结束日期 */
	private Date end_date;

	/** 检票开始日期 */
	private Date confirm_date_start;

	/** 检票结束日期 */
	private Date confirm_date_end;

	/** 联系人 */
	private String contactee;

	/** 联系电话 */
	private String contact_mobile;
	/** 联系人身份证号 */
	private String idcard_no;

	/** 操作人ID */
	private long operator_id;

	private int sale_port;

	/** 销售端口列表 ，新增*/
	private List<Integer> sale_port_list;

	/** 是否需要确认. 1: 不需要; 2: 需要', */
	private int need_confirm;

	/** 订单状态 列表*/
	private List<Integer> order_status_list;

	/** 魔方订单ID */
	private List<String> reseller_order_ids;

	/** 订单ID */
	private List<String> order_ids;

	/** 退款申请ID */
	private List<String> refund_ids;

	/** 供应商订单ID */
	private List<String> supplier_order_ids;

	/** 导游ID集合 */
	private List<Long> guide_ids;

	/** 导游ID */
	private long guide_id;

	/** 代下单标志：1:不需要代下单；2：需要代下单；3已经代下单 ---可用于查询需要代下单订单*/
	private int agent_flag;

	/** 列表结算是否按照创建时间降序排列  ---待确认、代下单、手动核销的订单列表查询是正序查询，所以请设置为false*/
	private boolean sortDesc = true;

	/** 产品ID列表 */
	private List<Long> product_ids;

	private List<Long> voucher_ids;

	private List<String> merch_ids;

	//private List<String> manual_order_ids;

	/** 产品名称 */
	private String merch_name;

	/** 渠道 */
	private long channel_id;
	/** 团散 */
	private String product_varie;
	/** 销售端口 */

	/** '订单商品状态. 0:待消费; 1: 已消费; 2: 待退款; 3: 已退款; -1: 不可用', */
	private Integer merch_state;

	/** '订单商品状态.0:可消费; 1: 已消费; 2: 待退款; 3: 已退款; -1: 不可用;4:待确认；5：已完成*/
	private List<Integer> merch_states;

	/** 退款状态（0 退款中   ） */
	private Integer is_refunding;

	/**是否可手动清算.0: 不可;1: 可手动 */
	private Integer is_manual;
	/**商品清算类型. 1: 核销自动清算; 2: 核销之后固定时间核销 */
	private int clear_type;

	/**
	 * 根据条件里是否有产品的属性来设置值。如果有，设置为true，映射语句会关联merch表。如果没有，不关联，节省时间。
	 */
	private boolean haveMerchFilter = false;

	/**是否直签，1-直签，2-非直签（即魔方)*/
	private int is_direct;

	/**直签是否需要线上支付，1是，0否*/
	private Integer online_pay;

	/** 精确日期（仅限供订查询有效期开始日期的数据使用）,出游开始日期  */
	private String accurateStartTime;
	/** 精确日期（仅限供订查询有效期开始日期的数据使用）,出游结束日期  */
	private String accurateEndTime;

	/** 订单类型. 1: 景区; 2: 票; 3: 住宿; 4: 小交通; 5: 特产;6:一日游 */
	private int merch_type;

	/** 订单类型. 景区.线路、旅拍、包车、班车、特产、导游、住宿、特色餐饮*/
	private List<Integer> merchTypes;
	/** 是否是已强制退款订单*/
	private boolean isforceOrder = false;
	/**强制退款状态(0：全部,1：退款中, 2：成功, 3：失败)*/
	private int refund_state;

	public List<String> getRefund_ids() {
		return refund_ids;
	}

	public void setRefund_ids(List<String> refund_ids) {
		this.refund_ids = refund_ids;
	}

	public List<String> getOrder_ids() {
		return order_ids;
	}

	public void setOrder_ids(List<String> order_ids) {
		this.order_ids = order_ids;
	}

	public List<Long> getGuide_ids() {
		return guide_ids;
	}

	public void setGuide_ids(List<Long> guide_ids) {
		this.guide_ids = guide_ids;
	}

	public int getRefund_state() {
		return refund_state;
	}

	public void setRefund_state(int refund_state) {
		this.refund_state = refund_state;
	}

	public boolean isIsforceOrder() {
		return isforceOrder;
	}

	public void setIsforceOrder(boolean isforceOrder) {
		this.isforceOrder = isforceOrder;
	}

	public List<Integer> getMerch_states() {
		return merch_states;
	}

	public void setMerch_states(List<Integer> merch_states) {
		this.merch_states = merch_states;
	}

	public List<Integer> getMerchTypes() {
		return merchTypes;
	}

	public void setMerchTypes(List<Integer> merchTypes) {
		this.merchTypes = merchTypes;
	}

	public int getMerch_type() {
		return merch_type;
	}

	public void setMerch_type(int merch_type) {
		this.merch_type = merch_type;
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

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(long reseller_id) {
		this.reseller_id = reseller_id;
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

	public void setVoucherIds(List<Long> voucher_ids) {
		if (Check.NuNCollections(voucher_ids)) {
			voucher_ids = new ArrayList<Long>();
			voucher_ids.add(-1l);
		}
		this.setVoucher_ids(voucher_ids);
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

	public long getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(long operator_id) {
		this.operator_id = operator_id;
	}

	public String getMerch_name() {
		return merch_name;
	}

	public void setMerch_name(String merch_name) {
		this.merch_name = merch_name;
	}

	public List<Integer> getOrder_status_list() {
		return order_status_list;
	}

	public void setOrder_status_list(List<Integer> order_status_list) {
		this.order_status_list = order_status_list;
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

	public int getNeed_confirm() {
		return need_confirm;
	}

	public void setNeed_confirm(int need_confirm) {
		this.need_confirm = need_confirm;
	}

	public Integer getMerch_state() {
		return merch_state;
	}

	public void setMerch_state(Integer merch_state) {
		this.merch_state = merch_state;
	}

	public boolean getIsReseller() {
		return isReseller;
	}

	public void setIsReseller(boolean isReseller) {
		this.isReseller = isReseller;
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

	public long getGuide_id() {
		return guide_id;
	}

	public void setGuide_id(long guide_id) {
		this.guide_id = guide_id;
	}

	public int getAgent_flag() {
		return agent_flag;
	}

	public void setAgent_flag(int agent_flag) {
		this.agent_flag = agent_flag;
	}

	public boolean getSortDesc() {
		return sortDesc;
	}

	public void setSortDesc(boolean sortDesc) {
		this.sortDesc = sortDesc;
	}

	public boolean getHaveMerchFilter() {
		return haveMerchFilter;
	}

	public void setHaveMerchFilter(boolean haveMerchFilter) {
		this.haveMerchFilter = haveMerchFilter;
	}

	public Integer getIs_refunding() {
		return is_refunding;
	}

	public void setIs_refunding(Integer is_refunding) {
		this.is_refunding = is_refunding;
	}

	public List<Integer> getSale_port_list() {
		return sale_port_list;
	}

	public void setSale_port_list(List<Integer> sale_port_list) {
		this.sale_port_list = sale_port_list;
	}

	public Integer getIs_manual() {
		return is_manual;
	}

	public void setIs_manual(Integer is_manual) {
		this.is_manual = is_manual;
	}

	public int getClear_type() {
		return clear_type;
	}

	public void setClear_type(int clear_type) {
		this.clear_type = clear_type;
	}

	public List<String> getMerch_ids() {
		return merch_ids;
	}

	public void setMerch_ids(List<String> merch_ids) {
		this.merch_ids = merch_ids;
	}

	//	public List<String> getManual_order_ids() {
	//		return manual_order_ids;
	//	}
	//
	//	public void setManual_order_ids(List<String> manual_order_ids) {
	//		this.manual_order_ids = manual_order_ids;
	//	}

	public List<Long> getSupplier_ids() {
		return supplier_ids;
	}

	public void setSupplier_ids(List<Long> supplier_ids) {
		this.supplier_ids = supplier_ids;
	}

	public List<Long> getReseller_ids() {
		return reseller_ids;
	}

	public void setReseller_ids(List<Long> reseller_ids) {
		this.reseller_ids = reseller_ids;
	}

}
