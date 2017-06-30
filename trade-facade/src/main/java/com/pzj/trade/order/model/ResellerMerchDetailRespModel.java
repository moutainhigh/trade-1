package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pzj.trade.order.entity.MerchCleanRelationResponse;
import com.pzj.trade.order.entity.OrderStrategyResponse;
import com.pzj.trade.order.entity.RefundFlowResponse;

/**
 * 分销商订单详情结果商品模型
 * @author GLG
 */
public class ResellerMerchDetailRespModel implements Serializable {

	/**  */
	private static final long serialVersionUID = 1L;
	/**商品id*/
	private String merchId;
	/**商品名称*/
	private String merchName;
	/**商品类型*/
	private int merchType;
	/**父商品id*/
	private String rootMerchId;
	/**订单id*/
	private String orderId;
	/**商品状态*/
	private int merchState = 0;
	/**商品状态信息*/
	private String merchStateMsg;
	/**产品id*/
	private long productId;
	/**父产品id*/
	private long parentProductId;
	/**核销凭证id*/
	private long voucherId;
	/**总数量id*/
	private int totalNum;
	/**已检数量*/
	private int checkNum;
	/**已退数量*/
	private int refundNum;
	/**商品价格（实际）*/
	private double price;
	/**退款金额*/
	private double refundAmount;
	/**'团:1 散：0'*/
	private String product_varie;
	/**商品政策信息*/
	private List<OrderStrategyResponse> orderStrategyList = new ArrayList<OrderStrategyResponse>();

	/** 更新时间 */
	private Date update_time;
	/**结算价,非政策结算价，是购买价-后返之和*/
	private double settlement_price;

	/** 消费/检票时间 */
	private Date check_time;
	/** 清算时间 */
	private Date clear_time;

	/**二维码  */
	private String qr_code;
	/**是否可手动清算.0: 不可;1: 可手动  */
	private int is_manual = 1;
	/** 姓名 */
	@Deprecated
	private String contact_name;

	/** 游客电话 */
	@Deprecated
	private String contact_mobile;
	/** 游客身份证号 */
	@Deprecated
	private String voucher_content;
	/** 出游/入住时间 */
	private Date start_time;
	/** 出游/入住结束时间 */
	private Date expire_time;
	/** 游玩时长 */
	private double visit_time;
	/** 场次 */
	private String screening;
	/**区域  */
	private String region;
	/** 座位 */
	private String seatNumbers;
	/** 退款信息 */
	private List<RefundFlowResponse> refundInfos = new ArrayList<RefundFlowResponse>();
	/** 政策id */
	private long strategyId;
	/** 渠道id*/
	private long channelId;

	/**凭证类型. 0: 未凭证; 1: 联系人信息; 2: 魔方码 */
	private int vour_type;
	/**是否逾期核销(设置， 默认0：不是逾期，1是逾期 ) */
	private int is_overdue;
	/**  商品是否存在退款中的 0否  1是*/
	private int is_refunding;

	/**是否已清算 0：未清算，1：已清算*/
	private int is_cleaned;

	/** 我的分销商政策id */
	private long resellerStrategyId;
	/** 我的供应商政策id */
	private long supplierStrategyId;

	/** 订单商品清算关系信息 */
	private List<MerchCleanRelationResponse> merchCleanRelations = new ArrayList<MerchCleanRelationResponse>();
	@Deprecated
	/**商品价格信息*/
	private List<ResellerPriceDetailRespModel> resellerPriceDetailRespModels = new ArrayList<ResellerPriceDetailRespModel>();
	/**游客信息*/
	private List<OrderTouristOutModel> orderTouristOutModels = new ArrayList<OrderTouristOutModel>();

	public List<OrderTouristOutModel> getOrderTouristOutModels() {
		return orderTouristOutModels;
	}

	public void setOrderTouristOutModels(List<OrderTouristOutModel> orderTouristOutModels) {
		this.orderTouristOutModels = orderTouristOutModels;
	}

	public List<ResellerPriceDetailRespModel> getResellerPriceDetailRespModels() {
		return resellerPriceDetailRespModels;
	}

	public void setResellerPriceDetailRespModels(List<ResellerPriceDetailRespModel> resellerPriceDetailRespModels) {
		this.resellerPriceDetailRespModels = resellerPriceDetailRespModels;
	}

	public long getResellerStrategyId() {
		return resellerStrategyId;
	}

	public void setResellerStrategyId(long resellerStrategyId) {
		this.resellerStrategyId = resellerStrategyId;
	}

	public long getSupplierStrategyId() {
		return supplierStrategyId;
	}

	public void setSupplierStrategyId(long supplierStrategyId) {
		this.supplierStrategyId = supplierStrategyId;
	}

	public List<MerchCleanRelationResponse> getMerchCleanRelations() {
		return merchCleanRelations;
	}

	public void setMerchCleanRelations(List<MerchCleanRelationResponse> merchCleanRelations) {
		this.merchCleanRelations = merchCleanRelations;
	}

	/**结算价*/
	private double detailPrice;
	/**后返*/
	private double rebate_amount;

	/**现返、周期返*/
	private int rebate_object;

	public double getDetailPrice() {
		return detailPrice;
	}

	public void setDetailPrice(double detailPrice) {
		this.detailPrice = detailPrice;
	}

	public double getRebate_amount() {
		return rebate_amount;
	}

	public void setRebate_amount(double rebate_amount) {
		this.rebate_amount = rebate_amount;
	}

	public int getRebate_object() {
		return rebate_object;
	}

	public void setRebate_object(int rebate_object) {
		this.rebate_object = rebate_object;
	}

	/**
	* 商品ID
	* @return
	*/
	public String getMerchId() {
		return merchId;
	}

	/**
	 * 设置商品ID
	 * @param merchId
	 */
	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	/**
	 * 商品名称
	 * @return
	 */
	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	/**
	 * 商品类型
	 * @return
	 */
	public int getMerchType() {
		return merchType;
	}

	public void setMerchType(int merchType) {
		this.merchType = merchType;
	}

	/**
	 * 获取跟商品ID
	 * @return
	 */
	public String getRootMerchId() {
		return rootMerchId;
	}

	/**
	 * 设置跟商品ID
	 * @param rootMerchId
	 */
	public void setRootMerchId(String rootMerchId) {
		this.rootMerchId = rootMerchId;
	}

	/**
	 * 订单ID
	 * @return
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * 订单ID
	 * @param orderId
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * 商品状态.0:可消费; 1: 已消费; 2: 待退款; 3: 已退款; -1: 不可用
	 * @return
	 */
	public int getMerchState() {
		return merchState;
	}

	/**
	 * 商品状态.0:可消费; 1: 已消费; 2: 待退款; 3: 已退款; -1: 不可用
	 * @param merchState
	 */
	public void setMerchState(int merchState) {
		this.merchState = merchState;
	}

	/**
	 * 商品状态描述
	 * @return
	 */
	public String getMerchStateMsg() {
		return merchStateMsg;
	}

	/**
	 * 商品状态描述
	 * @param merchStateMsg
	 */
	public void setMerchStateMsg(String merchStateMsg) {
		this.merchStateMsg = merchStateMsg;
	}

	/**
	 * 产品ID
	 * @return
	 */
	public long getProductId() {
		return productId;
	}

	/**
	 * 产品ID
	 * @param productId
	 */
	public void setProductId(long productId) {
		this.productId = productId;
	}

	/**
	 * 父产品ID
	 * @return
	 */
	public long getParentProductId() {
		return parentProductId;
	}

	/**
	 * 父产品ID
	 * @param parentProductId
	 */
	public void setParentProductId(long parentProductId) {
		this.parentProductId = parentProductId;
	}

	/**
	 * 渠道ID
	 * @return
	 */
	public long getChannelId() {
		return channelId;
	}

	/**
	 * 渠道ID
	 * @param channelId
	 */
	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}

	/**
	 * 政策ID
	 * @return  
	 */
	public long getStrategyId() {
		return strategyId;
	}

	/**
	 * 政策ID
	 * @param strategyId
	 */
	public void setStrategyId(long strategyId) {
		this.strategyId = strategyId;
	}

	/**
	 * 服务属性ID
	 * @return
	 */
	public long getVoucherId() {
		return voucherId;
	}

	/**
	 * 服务属性ID
	 * @param voucherId
	 */
	public void setVoucherId(long voucherId) {
		this.voucherId = voucherId;
	}

	/**
	 * 购买商品总数量
	 * @return
	 */
	public int getTotalNum() {
		return totalNum;
	}

	/**
	 * 购买商品总数量
	 * @param totalNum
	 */
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	/**
	 * 已确认数量
	 * @return
	 */
	public int getCheckNum() {
		return checkNum;
	}

	/**
	 * 已确认数量
	 * @param checkNum
	 */
	public void setCheckNum(int checkNum) {
		this.checkNum = checkNum;
	}

	/**
	 * 已退款数量
	 * @return
	 */
	public int getRefundNum() {
		return refundNum;
	}

	/**
	 * 已退款数量
	 * @param refundNum
	 */
	public void setRefundNum(int refundNum) {
		this.refundNum = refundNum;
	}

	/**
	 * 购买时的产品单价
	 * @return
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * 购买时的产品单价
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * 实际退款金额
	 * @return
	 */
	public double getRefundAmount() {
		return refundAmount;
	}

	/**
	 * 实际退款金额
	 * @param refundAmount
	 */
	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getContact_mobile() {
		return contact_mobile;
	}

	public void setContact_mobile(String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}

	public List<OrderStrategyResponse> getOrderStrategyList() {
		return orderStrategyList;
	}

	public void setOrderStrategyList(List<OrderStrategyResponse> orderStrategyList) {
		this.orderStrategyList = orderStrategyList;
	}

	public String getProduct_varie() {
		return product_varie;
	}

	public void setProduct_varie(String product_varie) {
		this.product_varie = product_varie;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public double getSettlement_price() {
		return settlement_price;
	}

	public void setSettlement_price(double settlement_price) {
		this.settlement_price = settlement_price;
	}

	public Date getCheck_time() {
		return check_time;
	}

	public void setCheck_time(Date check_time) {
		this.check_time = check_time;
	}

	public String getQr_code() {
		return qr_code;
	}

	public void setQr_code(String qr_code) {
		this.qr_code = qr_code;
	}

	public int getIs_manual() {
		return is_manual;
	}

	public void setIs_manual(int is_manual) {
		this.is_manual = is_manual;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getVoucher_content() {
		return voucher_content;
	}

	public void setVoucher_content(String voucher_content) {
		this.voucher_content = voucher_content;
	}

	public double getVisit_time() {
		return visit_time;
	}

	public void setVisit_time(double visit_time) {
		this.visit_time = visit_time;
	}

	public String getScreening() {
		return screening;
	}

	public void setScreening(String screening) {
		this.screening = screening;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSeatNumbers() {
		return seatNumbers;
	}

	public void setSeatNumbers(String seatNumbers) {
		this.seatNumbers = seatNumbers;
	}

	public List<RefundFlowResponse> getRefundInfos() {
		return refundInfos;
	}

	public void setRefundInfos(List<RefundFlowResponse> refundInfos) {
		this.refundInfos = refundInfos;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getExpire_time() {
		return expire_time;
	}

	public void setExpire_time(Date expire_time) {
		this.expire_time = expire_time;
	}

	public int getVour_type() {
		return vour_type;
	}

	public void setVour_type(int vour_type) {
		this.vour_type = vour_type;
	}

	public int getIs_overdue() {
		return is_overdue;
	}

	public void setIs_overdue(int is_overdue) {
		this.is_overdue = is_overdue;
	}

	public Date getClear_time() {
		return clear_time;
	}

	public void setClear_time(Date clear_time) {
		this.clear_time = clear_time;
	}

	public int getIs_refunding() {
		return is_refunding;
	}

	public void setIs_refunding(int is_refunding) {
		this.is_refunding = is_refunding;
	}

	public int getIs_cleaned() {
		return is_cleaned;
	}

	public void setIs_cleaned(int is_cleaned) {
		this.is_cleaned = is_cleaned;
	}
}
