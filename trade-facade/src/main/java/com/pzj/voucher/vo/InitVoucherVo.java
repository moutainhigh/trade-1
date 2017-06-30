package com.pzj.voucher.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class InitVoucherVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 身份证号或二维码  */
	@Deprecated
	private String voucherContent;

	/** 凭证介质类型：（ 1 联系人  2 二维码或条码及其辅助码  3 身份证号)参照VoucherContentType */
	@Deprecated
	private Integer voucherContentType;

	/** 凭证人手机号 */
	private String contactMobile;

	/** 凭证人姓名 */
	private String contactName;

	/** 凭证有效开始时间 */
	@Deprecated
	private Date startTime;

	/** 凭证有效过期时间  */
	@Deprecated
	private Date expireTime;

	/** 演艺开始时间 */
	private Date showStartTime;

	/** 演艺结束时间 */
	private Date showEndTime;

	/** 产品线(景区、演艺、住宿、特产、小交通、线路)  */
	private Integer voucherCategory;

	/** 供应商id */
	private Long supplierId;

	/** 创建时间 */
	@Deprecated
	private Date createTime;

	/** 特产收货地址  */
	@Deprecated
	private String address;

	/** 景区名称 */
	public String scenic;

	/** 景区Id  */
	public String scenicId;

	private List<VoucherProductInfoVO> productInfoList;

	//	/** 散票 0 ，团票 1 */
	//	@Deprecated
	private Integer ticketVarie = 0;

	@Deprecated
	public String getVoucherContent() {
		return voucherContent;
	}

	@Deprecated
	public void setVoucherContent(String voucherContent) {
		this.voucherContent = voucherContent;
	}

	@Deprecated
	public Integer getVoucherContentType() {
		return voucherContentType;
	}

	@Deprecated
	public void setVoucherContentType(Integer voucherContentType) {
		this.voucherContentType = voucherContentType;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Deprecated
	public Date getStartTime() {
		return startTime;
	}

	@Deprecated
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Deprecated
	public Date getExpireTime() {
		return expireTime;
	}

	@Deprecated
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Date getShowStartTime() {
		return showStartTime;
	}

	public void setShowStartTime(Date showStartTime) {
		this.showStartTime = showStartTime;
	}

	public Date getShowEndTime() {
		return showEndTime;
	}

	public void setShowEndTime(Date showEndTime) {
		this.showEndTime = showEndTime;
	}

	public Integer getVoucherCategory() {
		return voucherCategory;
	}

	public void setVoucherCategory(Integer voucherCategory) {
		this.voucherCategory = voucherCategory;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	@Deprecated
	public Date getCreateTime() {
		return createTime;
	}

	@Deprecated
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Deprecated
	public String getAddress() {
		return address;
	}

	@Deprecated
	public void setAddress(String address) {
		this.address = address;
	}

	public List<VoucherProductInfoVO> getProductInfoList() {
		return productInfoList;
	}

	public void setProductInfoList(List<VoucherProductInfoVO> productInfoList) {
		this.productInfoList = productInfoList;
	}

	@Deprecated
	public Integer getTicketVarie() {
		return ticketVarie;
	}

	@Deprecated
	public void setTicketVarie(Integer ticketVarie) {
		this.ticketVarie = ticketVarie;
	}

	public String getScenic() {
		return scenic;
	}

	public void setScenic(String scenic) {
		this.scenic = scenic;
	}

	public String getScenicId() {
		return scenicId;
	}

	public void setScenicId(String scenicId) {
		this.scenicId = scenicId;
	}

}
