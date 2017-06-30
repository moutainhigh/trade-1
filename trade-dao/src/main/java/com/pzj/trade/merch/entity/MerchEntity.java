package com.pzj.trade.merch.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.entity.TouristEntity;

/**
 * 订单商品信息.
 * @author YRJ
 */
public class MerchEntity implements Serializable {

	private static final long serialVersionUID = 3919847927349737739L;

	/**商品ID*/
	private String merch_id;

	/** 商品名称 */
	private String merch_name;

	/** 规格名称 */
	private String sku_name;

	/**产品类型/** 订单类型. 1: 景区; 2: 票; 3: 住宿; 4: 小交通; 5: 特产;6:一日游 */
	private int merch_type;

	/**交易主商品ID*/
	@Deprecated
	private String root_merch_id;

	private String transaction_id;

	/**订单ID*/
	private String order_id;

	/**订单商品状态. 0:可消费; 1: 已消费; 2: 待退款; 3: 已退款; -1: 不可用*/
	private int merch_state = -1;

	/** 商品是否存在退款中的 0否  1是 */
	private int is_refunding = 0;

	/** 商品状态描述 */
	private String merch_state_msg;

	/**产品ID*/
	private long product_id;

	/**父产品ID. 若是组合产品,这里存组合产品的ID*/
	private long p_product_id;

	/**是否自动核销：0否1是*/
	private int auto_confirm;

	/**原始供应商ID*/
	private long supplier_id;

	/**产品团散标志：1团 0散-只对票有效*/
	private int product_varie;

	/**渠道ID*/
	@Deprecated
	private long channel_id;

	/**政策ID*/
	@Deprecated
	private long strategy_id;

	/**属性ID*/
	private long voucher_id;

	/**产品总数量*/
	private int total_num;

	/**已确认数量*/
	private int check_num;

	/**已退款数量*/
	private int refund_num;

	/**
	 * 退款中的商品数量
	 */
	private int refunding_num;

	/**购买时的产品单价*/
	/**第一层（分销侧的）购买时的产品单价*/
	private double price;

	/**实际退款金额*/
	@Deprecated
	private double refund_amount;

	/**优惠金额-单价*/
	@Deprecated
	private double discount_amount;

	/**优惠的说明*/
	@Deprecated
	private String discount_remark;

	/** 去掉后返之后的结算价*/
	@Deprecated
	private double settlement_price;

	/**创建时间*/
	private Date create_time;

	/** 更新时间 */
	private Date update_time;

	/**凭证类型. 0: 未凭证; 1: 联系人信息; 2: 魔方码*/
	private int vour_type;

	/**核销方式 :1：按订单2：按规格3：按份数*/
	private int verification_type;

	/**商品清算类型. 1: 核销自动清算; 2: 核销之后固定时间核销*/
	@Deprecated
	private int clear_type;

	/**是否可手动清算.0: 不可;1: 可手动.只有清算规则为固定时间规则时, 此值有效.*/
	@Deprecated
	private int is_manual = 1;

	/**二维码  */
	private String qr_code;

	/** 姓名 */
	private String contact_name;

	/** 游客电话 */
	private String contact_mobile;

	/** 逾期清算时间*/
	@Deprecated
	private long effec_time;

	private Date check_time;

	/**是否已清算 0：未清算，1：已清算*/
	private int is_cleaned;
	/** 游玩开始时间 */
	private Date start_time;
	/** 游玩结束时间 */
	private Date expire_time;

	/** 观演开始时间 */
	private Date show_start_time;
	/** 观演结束时间 */
	private Date show_end_time;

	/** 游客 */
	private List<TouristEntity> tourists = new ArrayList<TouristEntity>();

	/** 政策 */
	private List<OrderStrategyEntity> strategy = new ArrayList<OrderStrategyEntity>();

	/**版本 */
	private int version;

	public MerchEntity() {
	}

	public MerchEntity(final String merchId) {
		this.merch_id = merchId;
		this.root_merch_id = merchId;
	}

	public String getMerch_id() {
		return merch_id;
	}

	public void setMerch_id(final String merch_id) {
		this.merch_id = merch_id;
	}

	public String getMerch_name() {
		return merch_name;
	}

	public void setMerch_name(final String merch_name) {
		this.merch_name = merch_name;
	}

	public String getSku_name() {
		return sku_name;
	}

	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}

	public void setRoot_merch_id(final String root_merch_id) {
		this.root_merch_id = root_merch_id;
	}

	public String getRoot_merch_id() {
		return root_merch_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(final String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(final String order_id) {
		this.order_id = order_id;
	}

	public int getMerch_state() {
		return merch_state;
	}

	public void setMerch_state(final int merch_state) {
		this.merch_state = merch_state;
	}

	public String getMerch_state_msg() {
		return merch_state_msg;
	}

	public void setMerch_state_msg(final String merch_state_msg) {
		this.merch_state_msg = merch_state_msg;
	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(final long product_id) {
		this.product_id = product_id;
	}

	public long getP_product_id() {
		return p_product_id;
	}

	public void setP_product_id(final long p_product_id) {
		this.p_product_id = p_product_id;
	}

	@Deprecated
	public long getChannel_id() {
		return channel_id;
	}

	@Deprecated
	public void setChannel_id(final long channel_id) {
		this.channel_id = channel_id;
	}

	@Deprecated
	public long getStrategy_id() {
		return strategy_id;
	}

	@Deprecated
	public void setStrategy_id(final long strategy_id) {
		this.strategy_id = strategy_id;
	}

	public long getVoucher_id() {
		return voucher_id;
	}

	public void setVoucher_id(final long voucher_id) {
		this.voucher_id = voucher_id;
	}

	public int getTotal_num() {
		return total_num;
	}

	public void setTotal_num(final int total_num) {
		this.total_num = total_num;
	}

	public int getCheck_num() {
		return check_num;
	}

	public void setCheck_num(final int check_num) {
		this.check_num = check_num;
	}

	public int getRefund_num() {
		return refund_num;
	}

	public void setRefund_num(final int refund_num) {
		this.refund_num = refund_num;
	}

	public int getRefunding_num() {
		return refunding_num;
	}

	public void setRefunding_num(int refunding_num) {
		this.refunding_num = refunding_num;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	public double getRefund_amount() {
		return refund_amount;
	}

	public void setRefund_amount(final double refund_amount) {
		this.refund_amount = refund_amount;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(final Date create_time) {
		this.create_time = create_time;
	}

	public int getMerch_type() {
		return merch_type;
	}

	public void setMerch_type(final int merch_type) {
		this.merch_type = merch_type;
	}

	public String getDiscount_remark() {
		return discount_remark;
	}

	public void setDiscount_remark(final String discount_remark) {
		this.discount_remark = discount_remark;
	}

	public int getProduct_varie() {
		return product_varie;
	}

	public void setProduct_varie(final int product_varie) {
		this.product_varie = product_varie;
	}

	public double getSettlement_price() {
		return settlement_price;
	}

	public void setSettlement_price(final double settlement_price) {
		this.settlement_price = settlement_price;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(final Date update_time) {
		this.update_time = update_time;
	}

	public String getQr_code() {
		return qr_code;
	}

	public void setQr_code(final String qr_code) {
		this.qr_code = qr_code;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(final String contact_name) {
		this.contact_name = contact_name;
	}

	public String getContact_mobile() {
		return contact_mobile;
	}

	public void setContact_mobile(final String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}

	public int getVour_type() {
		return vour_type;
	}

	public void setVour_type(final int vour_type) {
		this.vour_type = vour_type;
	}

	public int getVerification_type() {
		return verification_type;
	}

	public void setVerification_type(int verification_type) {
		this.verification_type = verification_type;
	}

	public int getClear_type() {
		return clear_type;
	}

	public void setClear_type(final int clear_type) {
		this.clear_type = clear_type;
	}

	public int getIs_manual() {
		return is_manual;
	}

	public void setIs_manual(final int is_manual) {
		this.is_manual = is_manual;
	}

	public long getEffec_time() {
		return effec_time;
	}

	public void setEffec_time(final long effec_time) {
		this.effec_time = effec_time;
	}

	public int getIs_refunding() {
		return is_refunding;
	}

	public void setIs_refunding(final int is_refunding) {
		this.is_refunding = is_refunding;
	}

	public double getDiscount_amount() {
		return discount_amount;
	}

	public void setDiscount_amount(final double discount_amount) {
		this.discount_amount = discount_amount;
	}

	public Date getCheck_time() {
		return check_time;
	}

	public void setCheck_time(final Date check_time) {
		this.check_time = check_time;
	}

	public int getIs_cleaned() {
		return is_cleaned;
	}

	public void setIs_cleaned(final int is_cleaned) {
		this.is_cleaned = is_cleaned;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(final Date start_time) {
		this.start_time = start_time;
	}

	public Date getExpire_time() {
		return expire_time;
	}

	public void setExpire_time(final Date expire_time) {
		this.expire_time = expire_time;
	}

	/**
	 * Getter method for property <tt>auto_confirm</tt>.
	 * 
	 * @return property value of auto_confirm
	 */
	public int getAuto_confirm() {
		return auto_confirm;
	}

	/**
	 * Setter method for property <tt>auto_confirm</tt>.
	 * 
	 * @param auto_confirm value to be assigned to property auto_confirm
	 */
	public void setAuto_confirm(int auto_confirm) {
		this.auto_confirm = auto_confirm;
	}

	/**
	 * Getter method for property <tt>supplier_id</tt>.
	 * 
	 * @return property value of supplier_id
	 */
	public long getSupplier_id() {
		return supplier_id;
	}

	/**
	 * Setter method for property <tt>supplier_id</tt>.
	 * 
	 * @param supplier_id value to be assigned to property supplier_id
	 */
	public void setSupplier_id(long supplier_id) {
		this.supplier_id = supplier_id;
	}

	/**
	 * Getter method for property <tt>version</tt>.
	 * 
	 * @return property value of version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * Setter method for property <tt>version</tt>.
	 * 
	 * @param version value to be assigned to property version
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * Getter method for property <tt>tourists</tt>.
	 * 
	 * @return property value of tourists
	 */
	public List<TouristEntity> getTourists() {
		return tourists;
	}

	/**
	 * Setter method for property <tt>tourists</tt>.
	 * 
	 * @param tourists value to be assigned to property tourists
	 */
	public void setTourists(List<TouristEntity> tourists) {
		this.tourists = tourists;
	}

	/**
	 * Getter method for property <tt>strategy</tt>.
	 * 
	 * @return property value of strategy
	 */
	public List<OrderStrategyEntity> getStrategy() {
		return strategy;
	}

	/**
	 * Setter method for property <tt>strategy</tt>.
	 * 
	 * @param strategy value to be assigned to property strategy
	 */
	public void setStrategy(List<OrderStrategyEntity> strategy) {
		this.strategy = strategy;
	}

	/**
	 * Getter method for property <tt>show_start_time</tt>.
	 * 
	 * @return property value of show_start_time
	 */
	public Date getShow_start_time() {
		return show_start_time;
	}

	/**
	 * Setter method for property <tt>show_start_time</tt>.
	 * 
	 * @param show_start_time value to be assigned to property show_start_time
	 */
	public void setShow_start_time(Date show_start_time) {
		this.show_start_time = show_start_time;
	}

	/**
	 * Getter method for property <tt>show_end_time</tt>.
	 * 
	 * @return property value of show_end_time
	 */
	public Date getShow_end_time() {
		return show_end_time;
	}

	/**
	 * Setter method for property <tt>show_end_time</tt>.
	 * 
	 * @param show_end_time value to be assigned to property show_end_time
	 */
	public void setShow_end_time(Date show_end_time) {
		this.show_end_time = show_end_time;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("MerchEntity [");
		if (merch_id != null) {
			builder.append("merch_id=");
			builder.append(merch_id);
			builder.append(", ");
		}
		if (merch_name != null) {
			builder.append("merch_name=");
			builder.append(merch_name);
			builder.append(", ");
		}
		if (sku_name != null) {
			builder.append("sku_name=");
			builder.append(sku_name);
			builder.append(", ");
		}
		builder.append("merch_type=");
		builder.append(merch_type);
		builder.append(", ");
		if (root_merch_id != null) {
			builder.append("root_merch_id=");
			builder.append(root_merch_id);
			builder.append(", ");
		}
		if (transaction_id != null) {
			builder.append("transaction_id=");
			builder.append(transaction_id);
			builder.append(", ");
		}
		if (order_id != null) {
			builder.append("order_id=");
			builder.append(order_id);
			builder.append(", ");
		}
		builder.append("merch_state=");
		builder.append(merch_state);
		builder.append(", is_refunding=");
		builder.append(is_refunding);
		builder.append(", ");
		if (merch_state_msg != null) {
			builder.append("merch_state_msg=");
			builder.append(merch_state_msg);
			builder.append(", ");
		}
		builder.append("product_id=");
		builder.append(product_id);
		builder.append(", p_product_id=");
		builder.append(p_product_id);
		builder.append(", product_varie=");
		builder.append(product_varie);
		builder.append(", channel_id=");
		builder.append(channel_id);
		builder.append(", strategy_id=");
		builder.append(strategy_id);
		builder.append(", voucher_id=");
		builder.append(voucher_id);
		builder.append(", total_num=");
		builder.append(total_num);
		builder.append(", check_num=");
		builder.append(check_num);
		builder.append(", refund_num=");
		builder.append(refund_num);
		builder.append(", price=");
		builder.append(price);
		builder.append(", refund_amount=");
		builder.append(refund_amount);
		builder.append(", discount_amount=");
		builder.append(discount_amount);
		builder.append(", ");
		if (discount_remark != null) {
			builder.append("discount_remark=");
			builder.append(discount_remark);
			builder.append(", ");
		}
		builder.append("settlement_price=");
		builder.append(settlement_price);
		builder.append(", ");
		if (create_time != null) {
			builder.append("create_time=");
			builder.append(create_time);
			builder.append(", ");
		}
		if (update_time != null) {
			builder.append("update_time=");
			builder.append(update_time);
			builder.append(", ");
		}
		builder.append("vour_type=");
		builder.append(vour_type);
		builder.append(", clear_type=");
		builder.append(clear_type);
		builder.append(", is_manual=");
		builder.append(is_manual);
		builder.append(", ");
		if (qr_code != null) {
			builder.append("qr_code=");
			builder.append(qr_code);
			builder.append(", ");
		}
		if (contact_name != null) {
			builder.append("contact_name=");
			builder.append(contact_name);
			builder.append(", ");
		}
		if (contact_mobile != null) {
			builder.append("contact_mobile=");
			builder.append(contact_mobile);
			builder.append(", ");
		}
		builder.append("effec_time=");
		builder.append(effec_time);
		builder.append(", ");
		if (check_time != null) {
			builder.append("check_time=");
			builder.append(check_time);
			builder.append(", ");
		}
		builder.append("is_cleaned=");
		builder.append(is_cleaned);
		builder.append(", ");
		if (start_time != null) {
			builder.append("start_time=");
			builder.append(start_time);
			builder.append(", ");
		}
		if (expire_time != null) {
			builder.append("expire_time=");
			builder.append(expire_time);
		}
		builder.append("]");
		return builder.toString();
	}

}
