package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pzj.trade.order.entity.MerchCleanRelationResponse;
import com.pzj.trade.order.entity.OrderStrategyResponse;
import com.pzj.trade.order.entity.RefundFlowResponse;

/**
 *售票员商品详情模型
 */
public class ResellerMerchOutModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String merchId;
	private String merchName;
	private int merchType;
	private String rootMerchId;
	private String orderId;
	private int merchState = 0;
	private String merchStateMsg;
	private long productId;
	private long parentProductId;
	private long channelId;
	private long strategyId;
	private long voucherId;
	private int totalNum;
	private int checkNum;

	private int refundNum;
	private double price;
	private double refundAmount;

	private String product_varie; //'团:1 散：0'
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
	private final int is_manual = 1;
	/** 姓名 */
	private String contact_name;

	/** 游客电话 */
	private String contact_mobile;
	/** 游客身份证号 */
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

	/**凭证类型. 0: 未凭证; 1: 联系人信息; 2: 魔方码 */
	private int vour_type;
	/**是否逾期核销(设置， 默认0：不是逾期，1是逾期 ) */
	private int is_overdue;
	/**  商品是否存在退款中的 0否  1是*/
	private int is_refunding;

	/**是否已清算 0：未清算，1：已清算*/
	private int is_cleaned;

	/** 订单商品清算关系信息 */
	private List<MerchCleanRelationResponse> merchCleanRelations = new ArrayList<MerchCleanRelationResponse>();

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	public int getMerchType() {
		return merchType;
	}

	public void setMerchType(int merchType) {
		this.merchType = merchType;
	}

	public String getRootMerchId() {
		return rootMerchId;
	}

	public void setRootMerchId(String rootMerchId) {
		this.rootMerchId = rootMerchId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMerchStateMsg() {
		return merchStateMsg;
	}

	public void setMerchStateMsg(String merchStateMsg) {
		this.merchStateMsg = merchStateMsg;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getParentProductId() {
		return parentProductId;
	}

	public void setParentProductId(long parentProductId) {
		this.parentProductId = parentProductId;
	}

	public long getChannelId() {
		return channelId;
	}

	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}

	public long getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(long strategyId) {
		this.strategyId = strategyId;
	}

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(long voucherId) {
		this.voucherId = voucherId;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(int checkNum) {
		this.checkNum = checkNum;
	}

	public int getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(int refundNum) {
		this.refundNum = refundNum;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
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

	public Date getClear_time() {
		return clear_time;
	}

	public void setClear_time(Date clear_time) {
		this.clear_time = clear_time;
	}

	public String getQr_code() {
		return qr_code;
	}

	public void setQr_code(String qr_code) {
		this.qr_code = qr_code;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getContact_mobile() {
		return contact_mobile;
	}

	public void setContact_mobile(String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}

	public String getVoucher_content() {
		return voucher_content;
	}

	public void setVoucher_content(String voucher_content) {
		this.voucher_content = voucher_content;
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

	public int getMerchState() {
		return merchState;
	}

	public void setMerchState(int merchState) {
		this.merchState = merchState;
	}

	public List<OrderStrategyResponse> getOrderStrategyList() {
		return orderStrategyList;
	}

	public int getIs_manual() {
		return is_manual;
	}

	public List<RefundFlowResponse> getRefundInfos() {
		return refundInfos;
	}

	public List<MerchCleanRelationResponse> getMerchCleanRelations() {
		return merchCleanRelations;
	}

	public void setOrderStrategyList(List<OrderStrategyResponse> orderStrategyList) {
		this.orderStrategyList = orderStrategyList;
	}

	public void setRefundInfos(List<RefundFlowResponse> refundInfos) {
		this.refundInfos = refundInfos;
	}

	public void setMerchCleanRelations(List<MerchCleanRelationResponse> merchCleanRelations) {
		this.merchCleanRelations = merchCleanRelations;
	}
}
