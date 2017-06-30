package com.pzj.trade.refund.vo;

import java.io.Serializable;

public class MerchVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 商品名称 */
	private String merchName;

	/** 退款数量 */
	private Integer refundNum;

	/** 退款单价 */
	private Double price;

	/** 退款总价 */
	private Double totalPrice;

	/**产品ID*/
	private long product_id;

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}

	public MerchVO() {
		super();
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(final String merchName) {
		this.merchName = merchName;
	}

	public Integer getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(final Integer refundNum) {
		this.refundNum = refundNum;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(final Double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
