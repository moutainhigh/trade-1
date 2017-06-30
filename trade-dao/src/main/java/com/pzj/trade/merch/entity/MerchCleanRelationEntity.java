package com.pzj.trade.merch.entity;

import java.util.Date;

/**
 * 商品清算关系记录.
 * @author YRJ
 *
 */
public class MerchCleanRelationEntity {

	/**
	 * 主键ID.
	 */
	private long clean_id;
	/**
	 * 订单ID.
	 */
	private String order_id;
	/**
	 * 事务ID.
	 */
	private String transaction_id;
	/**
	 * 商品ID.
	 */
	private String merch_id;
	/**
	 * 清算时间点.
	 */
	private long effec_time;
	/**
	 * 清算类别. 1: 普通清算; 2: 强制退款清算 ,3.订单未支付商品清算
	 */
	private int clean_type;
	/**
	 * 清算状态. 0: 未清算; 1: 已清算; 2: 清算失败; 3：冻结(强制退款,退已经核销的商品,在退款申请事触发）
	 */
	private int clean_state;
	/**
	 * 是否可手动清算.0: 不可;1: 可手动.只有清算规则为固定时间规则时, 此值有效.
	 */
	private int is_manual;

	/**
	 * 清算次数
	 */
	private int clean_count;

	/**
	 * 订单是否进行过支付0未支付,1已支付(默认)
	 */
	private int is_paied = 1;

	/**
	 * 正常清算数量
	 */
	private int normal_clean_num;
	/**
	 * 逾期清算数量
	 */
	private int overdue_clean_num;
	/**
	 * 退款清算数量
	 */
	private int refund_clean_num;

	/**
	 * 正常结算金额
	 */
	private double normal_clean_amount;
	/**
	 * 逾期结算金额
	 */
	private double overdue_clean_amount;
	/**
	 * 退款结算金额
	 */
	private double refund_clean_amount;

	/**
	 * 是否是为负数的结算单 0否（默认）1是，强制退款生成
	 * */
	private int is_minus_clean;

	private Date create_time;

	public int getIs_minus_clean() {
		return is_minus_clean;
	}

	public void setIs_minus_clean(int is_minus_clean) {
		this.is_minus_clean = is_minus_clean;
	}

	public long getClean_id() {
		return clean_id;
	}

	public void setClean_id(long clean_id) {
		this.clean_id = clean_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getMerch_id() {
		return merch_id;
	}

	public void setMerch_id(String merch_id) {
		this.merch_id = merch_id;
	}

	public long getEffec_time() {
		return effec_time;
	}

	public void setEffec_time(long effec_time) {
		this.effec_time = effec_time;
	}

	public int getClean_type() {
		return clean_type;
	}

	public void setClean_type(int clean_type) {
		this.clean_type = clean_type;
	}

	public int getClean_state() {
		return clean_state;
	}

	public void setClean_state(int clean_state) {
		this.clean_state = clean_state;
	}

	public int getIs_manual() {
		return is_manual;
	}

	public void setIs_manual(int is_manual) {
		this.is_manual = is_manual;
	}

	public int getClean_count() {
		return clean_count;
	}

	public void setClean_count(int clean_count) {
		this.clean_count = clean_count;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getNormal_clean_num() {
		return normal_clean_num;
	}

	public void setNormal_clean_num(int normal_clean_num) {
		this.normal_clean_num = normal_clean_num;
	}

	public int getOverdue_clean_num() {
		return overdue_clean_num;
	}

	public void setOverdue_clean_num(int overdue_clean_num) {
		this.overdue_clean_num = overdue_clean_num;
	}

	public int getRefund_clean_num() {
		return refund_clean_num;
	}

	public void setRefund_clean_num(int refund_clean_num) {
		this.refund_clean_num = refund_clean_num;
	}

	public double getNormal_clean_amount() {
		return normal_clean_amount;
	}

	public void setNormal_clean_amount(double normal_clean_amount) {
		this.normal_clean_amount = normal_clean_amount;
	}

	public double getOverdue_clean_amount() {
		return overdue_clean_amount;
	}

	public void setOverdue_clean_amount(double overdue_clean_amount) {
		this.overdue_clean_amount = overdue_clean_amount;
	}

	public double getRefund_clean_amount() {
		return refund_clean_amount;
	}

	public void setRefund_clean_amount(double refund_clean_amount) {
		this.refund_clean_amount = refund_clean_amount;
	}

	@Override
	public String toString() {
		StringBuilder tostr = new StringBuilder(MerchCleanRelationEntity.class.getSimpleName());
		tostr.append("[");
		tostr.append("clean_id=").append(clean_id);
		tostr.append(",order_id=").append(order_id);
		tostr.append(",transaction_id=").append(transaction_id);
		tostr.append(",merch_id=").append(merch_id);
		tostr.append(",effec_time=").append(effec_time);
		tostr.append(",clean_type=").append(clean_type);
		tostr.append(",clean_state=").append(clean_state);
		tostr.append(",is_manual=").append(is_manual);
		tostr.append(",normal_clean_num=").append(normal_clean_num);
		tostr.append(",normal_clean_amount=").append(normal_clean_amount);
		tostr.append(",overdue_clean_num=").append(overdue_clean_num);
		tostr.append(",overdue_clean_amount=").append(overdue_clean_amount);
		tostr.append(",refund_clean_num=").append(refund_clean_num);
		tostr.append(",refund_clean_amount=").append(refund_clean_amount);
		tostr.append("]");
		return tostr.toString();
	}

	public int getIs_paied() {
		return is_paied;
	}

	public void setIs_paied(int is_paied) {
		this.is_paied = is_paied;
	}
}
