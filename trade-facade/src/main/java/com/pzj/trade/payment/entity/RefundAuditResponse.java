package com.pzj.trade.payment.entity;

import java.io.Serializable;
import java.util.Date;

public class RefundAuditResponse implements Serializable  {
	/**
	  * @Fields serialVersionUID : TODO
	  */
	private static final long serialVersionUID = 743447045396073817L;
	// 退款单id
	private String refundId;
	// 退款金额
	private Double refundAmount;
	// 支付宝退款参数
	private String detailData;
	// 退款时间
	private Date refundDate;
	// 订单id
	private String orderId;
	// 退款状态（0发起，1成功,2拒绝,3不需要审核）
	private Byte refundState;
	// 退款单创建时间
	private Date createDate;
	// 支付方式
	private String payType;
	// 备注
	private String remark;
	// 用户id
	private Long objectId;
	// 支付宝退款流水号
	private String batchNo;

	private String retunValue;
	// 0退款操作，1提现操作
	private Byte refundType;
	// 第三方支付总金额
	private Double bankAmount;
	
	//新增退款人ID
    private Long              auditorId;
    //新增退款人姓名
    private String            auditorName;
	
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public Double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getDetailData() {
		return detailData;
	}
	public void setDetailData(String detailData) {
		this.detailData = detailData;
	}
	public Date getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Byte getRefundState() {
		return refundState;
	}
	public void setRefundState(Byte refundState) {
		this.refundState = refundState;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getRetunValue() {
		return retunValue;
	}
	public void setRetunValue(String retunValue) {
		this.retunValue = retunValue;
	}
	public Byte getRefundType() {
		return refundType;
	}
	public void setRefundType(Byte refundType) {
		this.refundType = refundType;
	}
	public Double getBankAmount() {
		return bankAmount;
	}
	public void setBankAmount(Double bankAmount) {
		this.bankAmount = bankAmount;
	}
    public Long getAuditorId() {
        return auditorId;
    }
    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }
    public String getAuditorName() {
        return auditorName;
    }
    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

}
