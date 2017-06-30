package com.pzj.trade.merch.entity;

/**
 * 待核销商品.
 * @author YRJ
 * @author DongChunfu
 * @date 2017年3月23日14:17:52
 */
public class ConfirmMerchEntity extends MerchEffecTimeEntity {

	/**跟商品ID*/
	private String root_merch_id;

	/**商品状态*/
	private int merch_state;

	/**产品ID*/
	private long product_id;

	/**产品总数量*/
	private int total_num;

	/**已确认数量*/
	private int check_num;

	/**已退款数量*/
	private int refund_num;
	/**结算价*/
	private double settlement_price;

	/**退款状态*/
	private int is_refunding;

	/**商品单价*/
	private double price;

	/**商品的退款金额*/
	private double refund_amount;

	/*******************非持久化参数*******************/
	/**自动核销属性*/
	private int auto_confirm;
	/**版本*/
	private int version;
	/**商品类型*/
	private int merch_type;

	public String getRoot_merch_id() {
		return root_merch_id;
	}

	public void setRoot_merch_id(final String root_merch_id) {
		this.root_merch_id = root_merch_id;
	}

	public int getMerch_state() {
		return merch_state;
	}

	public void setMerch_state(final int merch_state) {
		this.merch_state = merch_state;
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

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(final long product_id) {
		this.product_id = product_id;
	}

	public double getSettlement_price() {
		return settlement_price;
	}

	public void setSettlement_price(final double settlement_price) {
		this.settlement_price = settlement_price;
	}

	public int getIs_refunding() {
		return is_refunding;
	}

	public void setIs_refunding(final int is_refunding) {
		this.is_refunding = is_refunding;
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

	public int getAuto_confirm() {
		return auto_confirm;
	}

	public void setAuto_confirm(final int auto_confirm) {
		this.auto_confirm = auto_confirm;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public int getMerch_type() {
		return merch_type;
	}

	public void setMerch_type(final int merch_type) {
		this.merch_type = merch_type;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ConfirmMerchEntity [root_merch_id=");
		builder.append(root_merch_id);
		builder.append(", merch_state=");
		builder.append(merch_state);
		builder.append(", product_id=");
		builder.append(product_id);
		builder.append(", total_num=");
		builder.append(total_num);
		builder.append(", check_num=");
		builder.append(check_num);
		builder.append(", refund_num=");
		builder.append(refund_num);
		builder.append(", settlement_price=");
		builder.append(settlement_price);
		builder.append(", is_refunding=");
		builder.append(is_refunding);
		builder.append(", price=");
		builder.append(price);
		builder.append(", refund_amount=");
		builder.append(refund_amount);
		builder.append(", auto_confirm=");
		builder.append(auto_confirm);
		builder.append(", version=");
		builder.append(version);
		builder.append(", merch_type=");
		builder.append(merch_type);
		builder.append("]");
		return builder.toString();
	}

}
