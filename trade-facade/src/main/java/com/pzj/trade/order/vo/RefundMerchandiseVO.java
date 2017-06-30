package com.pzj.trade.order.vo;

import java.io.Serializable;

/**
 * 退款商品
 * 
 * @author kangzl
 *
 */
public class RefundMerchandiseVO implements Serializable {

	private static final long serialVersionUID = -819491325801863139L;

	/** 商品ID */
	private String merchId;

	/** 商品数量 */
	private int refundNum;

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(final String merchId) {
		this.merchId = merchId;
	}

	public int getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(final int refundNum) {
		this.refundNum = refundNum;
	}

	@Override
	public String toString() {
		final StringBuilder tostr = new StringBuilder(RefundMerchandiseVO.class.getSimpleName());
		tostr.append("[merchId=").append(merchId);
		tostr.append(", refundNum=").append(refundNum);
		tostr.append("]");
		return tostr.toString();
	}
}
