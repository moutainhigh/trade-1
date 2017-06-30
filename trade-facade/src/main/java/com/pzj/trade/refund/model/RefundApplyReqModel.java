/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.refund.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.vo.RefundMerchandiseVO;

/**
 * 退款申请请求参数
 * 
 * @author DongChunfu
 * @date 2016年12月14日
 */
public class RefundApplyReqModel implements Serializable {
	private static final long serialVersionUID = -1L;

	/** 订单ID(必填) */
	private String orderId;

	/** 退款发起人ID(必填) */
	private Long initiatorId;

	/**
	 * 退款发起方(必填).
	 * <ul>
	 * <li>1. 用户发起</li>
	 * <li>2. 平台发起</li>
	 * <li>3. 对接拒绝</li>
	 * <ul>
	 */
	private int initParty = 1;

	/**
	 * 退款类型(必填).
	 * <ul>
	 * <li>0. 普通退款</li>
	 * <li>1. 强制退款</li>
	 * <ul>
	 */
	private int refundType = 0;

	/**
	 * 待退款商品信息(部分退时必填).
	 * <p>
	 * 可以依据此值判断退款是否为整单退还是部分退.
	 * </p>
	 * <ul>
	 * 整单退、部分退的逻辑判断规则
	 * <li>1. 当此值为空, 则视为订单上可退商品全部退款.</li>
	 * <li>2. 此值非空, 判断订单上剩余可退商品数量是否恒等于申请的退款数量, 若相等则视为全部退款; 否则视为部分退.</li>
	 * </ul>
	 * 
	 */
	private List<RefundMerchandiseVO> refundMerches;

	/**
	 * 退款原因说明(选填,强制退款时建议填写).
	 * <p>
	 * 当且仅当, 强制退款时此值有效. 非强制退款时, 此值被忽略.
	 * </p>
	 */
	private RefundReasonReqModel reason;

	public RefundApplyReqModel() {
		super();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public Long getInitiatorId() {
		return initiatorId;
	}

	public void setInitiatorId(final Long initiatorId) {
		this.initiatorId = initiatorId;
	}

	public Integer getInitParty() {
		return initParty;
	}

	public void setInitParty(final Integer initParty) {
		this.initParty = initParty;
	}

	public List<RefundMerchandiseVO> getRefundMerches() {
		if(Check.NuNCollections(refundMerches)){
			refundMerches=new ArrayList<RefundMerchandiseVO>();
		}
		return refundMerches;
	}

	public void setRefundMerches(final List<RefundMerchandiseVO> refundMerches) {
		this.refundMerches = refundMerches;
	}

	public void addRefundMerch(final RefundMerchandiseVO refundMerch) {
		getRefundMerches().add(refundMerch);
	}

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(final Integer refundType) {
		this.refundType = refundType;
	}

	public RefundReasonReqModel getReason() {
		return reason;
	}

	public void setReason(final RefundReasonReqModel reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		final StringBuilder tostr = new StringBuilder(RefundApplyReqModel.class.getSimpleName());
		tostr.append("[orderId=").append(orderId);
		tostr.append(", initiatorId=").append(initiatorId);
		tostr.append(", initParty=").append(initParty);
		tostr.append(", refundType=").append(refundType);
		tostr.append(", refundMerches=").append(Arrays.toString(getRefundMerches().toArray(new RefundMerchandiseVO[0])));
		tostr.append(", reason=").append(reason);
		tostr.append("]");
		return tostr.toString();
	}
}
