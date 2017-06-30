/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.financeCenter.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pzj.trade.order.entity.RefundFlowResponse;

/**
 * 
 * @author GLG
 * @version $Id: AccountMerchRespModel.java, v 0.1 2017年3月7日 下午6:18:52 Administrator Exp $
 */
public class AccountMerchRespModel implements Serializable {

	/**  */
	private static final long serialVersionUID = -5308970392467171466L;

	/**sup名称*/
	private String supProductName;

	/**sku名称*/
	private String skuProductName;
	/** 规格总份数 */
	private int totalNum;
	/** 产品id */
	private long productId;

	/** 出游/入住开始时间 */
	private Date visitStartTime;
	/** 出游/入住结束时间 */
	private Date visitEndTime;

	/** 退款信息 */
	private List<RefundFlowResponse> refundInfos = new ArrayList<RefundFlowResponse>();

	public List<RefundFlowResponse> getRefundInfos() {
		return refundInfos;
	}

	public void setRefundInfos(List<RefundFlowResponse> refundInfos) {
		this.refundInfos = refundInfos;
	}

	public String getSkuProductName() {
		return skuProductName;
	}

	public void setSkuProductName(String skuProductName) {
		this.skuProductName = skuProductName;
	}

	public Date getVisitStartTime() {
		return visitStartTime;
	}

	public void setVisitStartTime(Date visitStartTime) {
		this.visitStartTime = visitStartTime;
	}

	public Date getVisitEndTime() {
		return visitEndTime;
	}

	public void setVisitEndTime(Date visitEndTime) {
		this.visitEndTime = visitEndTime;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getSupProductName() {
		return supProductName;
	}

	public void setSupProductName(String supProductName) {
		this.supProductName = supProductName;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

}
