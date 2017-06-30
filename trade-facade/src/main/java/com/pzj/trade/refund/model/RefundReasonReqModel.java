package com.pzj.trade.refund.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 退款原因, 当且仅当发起强制退款时, 可以填写退款原因说明.
 * @author YRJ
 *
 */
public class RefundReasonReqModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 退款原因说明, 描述该笔退款申请产生的原因.
	 * 最大长度为:200字符.
	 * 可选的.
	 */
	private String reason;

	/**
	 * 退款原因说明图片.
	 * 最多5张图片
	 * 可选的.
	 */
	private String[] pics;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String[] getPics() {
		return pics;
	}

	public void setPics(String[] pics) {
		this.pics = pics;
	}

	@Override
	public String toString() {
		StringBuilder tostr = new StringBuilder(RefundReasonReqModel.class.getSimpleName());
		tostr.append("[reason=").append(getReason());
		tostr.append(", pics=").append(Arrays.toString(getPics()));
		tostr.append("]");
		return tostr.toString();
	}
}
