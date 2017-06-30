package com.pzj.core.trade.voucher.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * voucher简化实体
 * @author Administrator
 * @version $Id: VoucherBriefEntity.java, v 0.1 2017年3月16日 下午2:09:42 Administrator Exp $
 */
public class VoucherBriefEntity implements Serializable {

	/**  */
	private static final long serialVersionUID = -2473474228382308264L;

	/** 凭证ID */
	private Long voucherId;

	/** 凭证  */
	private String voucherContent;

	/** 凭证介质类型：（ 1 手机号   2 二维码或条码及其辅助码  3 身份证号)  */
	private Integer voucherContentType;

	/** 凭证有效开始时间 */
	private Date startTime;

	/** 凭证有效过期时间  */
	private Date expireTime;

	/** 演艺开始时间 */
	private Date showStartTime;

	/** 演艺结束时间 */
	private Date showEndTime;

	/** 凭证状态(-1:不可用；0：可以使用；1核销完毕；2：退单) 参照 VoucherState枚举对象 */
	private Integer voucherState = -1;

	/** 产品线(景区、演艺、住宿、特产、小交通、线路) */
	private Integer voucherCategory;

	/** 供应商id */
	private Long supplierId;

	/** 交易号 */
	private String transactionId;

	/** 记录检票点和核销数量 */
	private List<VoucherConfirmBriefEntity> voucherConfirms = new ArrayList<VoucherConfirmBriefEntity>();

	/** 记录voucher扩展信息 */
	private VoucherExtendBriefEntity voucherExtend;
	/**产品id，当核销方式是按规格核销时，关联商品和voucher时使用*/
	private long productId;

	/**
			 * Getter method for property <tt>voucherId</tt>.
			 * 
			 * @return property value of voucherId
			 */
	public Long getVoucherId() {
		return voucherId;
	}

	/**
	 * Setter method for property <tt>voucherId</tt>.
	 * 
	 * @param voucherId value to be assigned to property voucherId
	 */
	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
	}

	/**
	 * Getter method for property <tt>voucherContent</tt>.
	 * 
	 * @return property value of voucherContent
	 */
	public String getVoucherContent() {
		return voucherContent;
	}

	/**
	 * Setter method for property <tt>voucherContent</tt>.
	 * 
	 * @param voucherContent value to be assigned to property voucherContent
	 */
	public void setVoucherContent(String voucherContent) {
		this.voucherContent = voucherContent;
	}

	/**
	 * Getter method for property <tt>voucherContentType</tt>.
	 * 
	 * @return property value of voucherContentType
	 */
	public Integer getVoucherContentType() {
		return voucherContentType;
	}

	/**
	 * Setter method for property <tt>voucherContentType</tt>.
	 * 
	 * @param voucherContentType value to be assigned to property voucherContentType
	 */
	public void setVoucherContentType(Integer voucherContentType) {
		this.voucherContentType = voucherContentType;
	}

	/**
	 * Getter method for property <tt>startTime</tt>.
	 * 
	 * @return property value of startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * Setter method for property <tt>startTime</tt>.
	 * 
	 * @param startTime value to be assigned to property startTime
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * Getter method for property <tt>expireTime</tt>.
	 * 
	 * @return property value of expireTime
	 */
	public Date getExpireTime() {
		return expireTime;
	}

	/**
	 * Setter method for property <tt>expireTime</tt>.
	 * 
	 * @param expireTime value to be assigned to property expireTime
	 */
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	/**
	 * Getter method for property <tt>showStartTime</tt>.
	 * 
	 * @return property value of showStartTime
	 */
	public Date getShowStartTime() {
		return showStartTime;
	}

	/**
	 * Setter method for property <tt>showStartTime</tt>.
	 * 
	 * @param showStartTime value to be assigned to property showStartTime
	 */
	public void setShowStartTime(Date showStartTime) {
		this.showStartTime = showStartTime;
	}

	/**
	 * Getter method for property <tt>showEndTime</tt>.
	 * 
	 * @return property value of showEndTime
	 */
	public Date getShowEndTime() {
		return showEndTime;
	}

	/**
	 * Setter method for property <tt>showEndTime</tt>.
	 * 
	 * @param showEndTime value to be assigned to property showEndTime
	 */
	public void setShowEndTime(Date showEndTime) {
		this.showEndTime = showEndTime;
	}

	/**
	 * Getter method for property <tt>voucherState</tt>.
	 * 
	 * @return property value of voucherState
	 */
	public Integer getVoucherState() {
		return voucherState;
	}

	/**
	 * Setter method for property <tt>voucherState</tt>.
	 * 
	 * @param voucherState value to be assigned to property voucherState
	 */
	public void setVoucherState(Integer voucherState) {
		this.voucherState = voucherState;
	}

	/**
	 * Getter method for property <tt>voucherCategory</tt>.
	 * 
	 * @return property value of voucherCategory
	 */
	public Integer getVoucherCategory() {
		return voucherCategory;
	}

	/**
	 * Setter method for property <tt>voucherCategory</tt>.
	 * 
	 * @param voucherCategory value to be assigned to property voucherCategory
	 */
	public void setVoucherCategory(Integer voucherCategory) {
		this.voucherCategory = voucherCategory;
	}

	/**
	 * Getter method for property <tt>supplierId</tt>.
	 * 
	 * @return property value of supplierId
	 */
	public Long getSupplierId() {
		return supplierId;
	}

	/**
	 * Setter method for property <tt>supplierId</tt>.
	 * 
	 * @param supplierId value to be assigned to property supplierId
	 */
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	/**
	 * Getter method for property <tt>transactionId</tt>.
	 * 
	 * @return property value of transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * Setter method for property <tt>transactionId</tt>.
	 * 
	 * @param transactionId value to be assigned to property transactionId
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * Getter method for property <tt>voucherConfirms</tt>.
	 * 
	 * @return property value of voucherConfirms
	 */
	public List<VoucherConfirmBriefEntity> getVoucherConfirms() {
		return voucherConfirms;
	}

	/**
	 * Setter method for property <tt>voucherConfirms</tt>.
	 * 
	 * @param voucherConfirms value to be assigned to property voucherConfirms
	 */
	public void setVoucherConfirms(List<VoucherConfirmBriefEntity> voucherConfirms) {
		this.voucherConfirms = voucherConfirms;
	}

	/**
	 * Getter method for property <tt>voucherExtend</tt>.
	 * 
	 * @return property value of voucherExtend
	 */
	public VoucherExtendBriefEntity getVoucherExtend() {
		return voucherExtend;
	}

	/**
	 * Setter method for property <tt>voucherExtend</tt>.
	 * 
	 * @param voucherExtend value to be assigned to property voucherExtend
	 */
	public void setVoucherExtend(VoucherExtendBriefEntity voucherExtend) {
		this.voucherExtend = voucherExtend;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

}
