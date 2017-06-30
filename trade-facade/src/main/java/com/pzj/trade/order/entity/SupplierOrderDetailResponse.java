package com.pzj.trade.order.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pzj.trade.order.model.DeliveryDetailModel;
import com.pzj.trade.order.model.FilledModel;

/**
 * 供应商订单详情
 * @author YRJ
 *
 */
public class SupplierOrderDetailResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	/**订单ID*/
	private String order_id;

	/**供应商订单ID*/
	private String supplier_order_id;

	/**分销商订单ID*/
	private String reseller_order_id;

	/**订单支付者的资金帐号*/
	private long payer_id;

	/**操作者*/
	private long operator_id;

	/**供应商ID*/
	private long supplier_id;

	/**原始供应商ID*/
	private long origin_supplier_id;

	/**分销商ID(旅行社)*/
	private long reseller_id;

	/**分销商手机*/
	private String reseller_mobile;

	/**旅行社*/
	private long travel;

	/**旅行社部门*/
	private long travel_depart_id;

	/**导游ID*/
	private long guide_id;

	/**导游手机*/
	private String guide_mobile;

	/**支付方式:0: 纯余额; 1. 支付宝; 2. 微信; 4: 混合支付; 5: 现金; 6: 后付
	 * (其中0: 纯余额; 1. 支付宝; 2. 微信; 4: 混合支付 对应属于‘第三方/余额’,6: 后付对应‘签单’,5: 现金对应‘现金’)       */
	private Integer payWay;

	/**订单状态*/
	private int order_status;

	/** 是否需要确认. 1: 不需要; 2: 需要;3:已确认' */
	private int confirm;

	/**订单总金额*/
	private double total_amount;

	/**微单余额支付金额*/
	private double wshop_balance_amount;
	/**app余额支付金额*/
	private double app_balance_amount;

	/**已退款总金额*/
	private double refund_amount;

	/**订单包含的商品总数量*/
	private int total_num;

	/**已确认的商品数量*/
	private int checked_num;

	/**已退款的商品数量*/
	private int refund_num;

	/** 订单来源：1、线下窗口 3、PC分销端  5、商户APP 7、商户微店 */
	private int sale_port;

	/**创建时间*/
	private Date create_time;

	/**支付时间*/
	private Date pay_time;

	/**第三方支付类型:0-余额，1-支付宝，2-微信*/
	private int third_pay_type;

	/**订单关闭时间（取消、已退款、已完成）*/
	private Date confirm_time;

	/** 已消费金额 */
	private double usedAmount;

	/** 渠道ID */
	private long channel_id;

	/**代下单标志：1:不需要代下单；2：需要代下单；3已经代下单 */
	private int agent_flag;

	/**结算价,非政策结算价，是购买价-后返之和*/
	private double settlement_price;

	/** 魔方码 */
	private String mftour_code;

	/** 验证状态. 0:待验证; 1: 已验证; 2: 已过期 */
	private int code_state;

	/** 填单项列表*/
	private List<FilledModel> filledModelList;
	/** 配送信息*/
	private DeliveryDetailModel delivery_info;

	/**是否直签，1-直签，2-非直签（即魔方)*/
	private int is_direct;

	/**直签是否需要线上支付，1是，0否*/
	private Integer online_pay;

	/** 售票点 */
	private long salePiont;
	/** 售票员id */
	private long salePersonId;
	/**后返金额*/
	private double rebateAmount;

	/**返利方式（1：前返   2：后返）*/
	private int rebateMethod;

	/**交易ID，此次级联订单所用的整个交易ID，多个交易相同*/
	private String transaction_id = "";

	/**父订单ID*/
	private String p_order_id = "";

	/**销售端口（APP, OTA, 微店）名称*/
	private String sale_port_content = "";

	/** 联系人 */
	private String contactee = "";

	/** 联系电话 */
	private String contact_mobile = "";

	/** 联系人身份证 */
	private String idcard_no = "";
	/** 备注 */
	private List<RemarkResponse> remarks = new ArrayList<RemarkResponse>();

	private List<MerchResponse> merchs = new ArrayList<MerchResponse>();

	/** 客源地信息*/
	private String touristSource;

	public String getTouristSource() {
		return touristSource;
	}

	public void setTouristSource(String touristSource) {
		this.touristSource = touristSource;
	}

	public String getReseller_mobile() {
		return reseller_mobile;
	}

	public void setReseller_mobile(String reseller_mobile) {
		this.reseller_mobile = reseller_mobile;
	}

	public String getGuide_mobile() {
		return guide_mobile;
	}

	public void setGuide_mobile(String guide_mobile) {
		this.guide_mobile = guide_mobile;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getSupplier_order_id() {
		return supplier_order_id;
	}

	public void setSupplier_order_id(String supplier_order_id) {
		this.supplier_order_id = supplier_order_id;
	}

	public String getReseller_order_id() {
		return reseller_order_id;
	}

	public void setReseller_order_id(String reseller_order_id) {
		this.reseller_order_id = reseller_order_id;
	}

	public long getPayer_id() {
		return payer_id;
	}

	public void setPayer_id(long payer_id) {
		this.payer_id = payer_id;
	}

	public long getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(long operator_id) {
		this.operator_id = operator_id;
	}

	public long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public long getOrigin_supplier_id() {
		return origin_supplier_id;
	}

	public void setOrigin_supplier_id(long origin_supplier_id) {
		this.origin_supplier_id = origin_supplier_id;
	}

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(long reseller_id) {
		this.reseller_id = reseller_id;
	}

	public long getTravel() {
		return travel;
	}

	public void setTravel(long travel) {
		this.travel = travel;
	}

	public long getTravel_depart_id() {
		return travel_depart_id;
	}

	public void setTravel_depart_id(long travel_depart_id) {
		this.travel_depart_id = travel_depart_id;
	}

	public long getGuide_id() {
		return guide_id;
	}

	public void setGuide_id(long guide_id) {
		this.guide_id = guide_id;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public int getConfirm() {
		return confirm;
	}

	public void setConfirm(int confirm) {
		this.confirm = confirm;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public double getWshop_balance_amount() {
		return wshop_balance_amount;
	}

	public void setWshop_balance_amount(double wshop_balance_amount) {
		this.wshop_balance_amount = wshop_balance_amount;
	}

	public double getApp_balance_amount() {
		return app_balance_amount;
	}

	public void setApp_balance_amount(double app_balance_amount) {
		this.app_balance_amount = app_balance_amount;
	}

	public double getRefund_amount() {
		return refund_amount;
	}

	public void setRefund_amount(double refund_amount) {
		this.refund_amount = refund_amount;
	}

	public int getTotal_num() {
		return total_num;
	}

	public void setTotal_num(int total_num) {
		this.total_num = total_num;
	}

	public int getChecked_num() {
		return checked_num;
	}

	public void setChecked_num(int checked_num) {
		this.checked_num = checked_num;
	}

	public int getRefund_num() {
		return refund_num;
	}

	public void setRefund_num(int refund_num) {
		this.refund_num = refund_num;
	}

	public int getSale_port() {
		return sale_port;
	}

	public void setSale_port(int sale_port) {
		this.sale_port = sale_port;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getPay_time() {
		return pay_time;
	}

	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}

	public int getThird_pay_type() {
		return third_pay_type;
	}

	public void setThird_pay_type(int third_pay_type) {
		this.third_pay_type = third_pay_type;
	}

	public Date getConfirm_time() {
		return confirm_time;
	}

	public void setConfirm_time(Date confirm_time) {
		this.confirm_time = confirm_time;
	}

	public double getUsedAmount() {
		return usedAmount;
	}

	public void setUsedAmount(double usedAmount) {
		this.usedAmount = usedAmount;
	}

	public long getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(long channel_id) {
		this.channel_id = channel_id;
	}

	public int getAgent_flag() {
		return agent_flag;
	}

	public void setAgent_flag(int agent_flag) {
		this.agent_flag = agent_flag;
	}

	public double getSettlement_price() {
		return settlement_price;
	}

	public void setSettlement_price(double settlement_price) {
		this.settlement_price = settlement_price;
	}

	public String getMftour_code() {
		return mftour_code;
	}

	public void setMftour_code(String mftour_code) {
		this.mftour_code = mftour_code;
	}

	public int getCode_state() {
		return code_state;
	}

	public void setCode_state(int code_state) {
		this.code_state = code_state;
	}

	public List<FilledModel> getFilledModelList() {
		return filledModelList;
	}

	public void setFilledModelList(List<FilledModel> filledModelList) {
		this.filledModelList = filledModelList;
	}

	public DeliveryDetailModel getDelivery_info() {
		return delivery_info;
	}

	public void setDelivery_info(DeliveryDetailModel delivery_info) {
		this.delivery_info = delivery_info;
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

	public double getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(double rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public int getRebateMethod() {
		return rebateMethod;
	}

	public void setRebateMethod(int rebateMethod) {
		this.rebateMethod = rebateMethod;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getP_order_id() {
		return p_order_id;
	}

	public void setP_order_id(String p_order_id) {
		this.p_order_id = p_order_id;
	}

	public String getSale_port_content() {
		return sale_port_content;
	}

	public void setSale_port_content(String sale_port_content) {
		this.sale_port_content = sale_port_content;
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

	public List<RemarkResponse> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<RemarkResponse> remarks) {
		this.remarks = remarks;
	}

	public List<MerchResponse> getMerchs() {
		return merchs;
	}

	public void setMerchs(List<MerchResponse> merchs) {
		this.merchs = merchs;
	}

}
