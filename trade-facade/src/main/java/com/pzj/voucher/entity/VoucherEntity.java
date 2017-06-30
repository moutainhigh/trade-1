package com.pzj.voucher.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.pzj.voucher.lineVo.TheaterProductVoucherVO;

public class VoucherEntity implements Serializable {

	private static final long serialVersionUID = -4220823775685574216L;

	/** 凭证ID */
	private Long voucherId;

	/** 身份证号或二维码  */
	private String voucherContent;

	/** 身份证号或二维码集合  */
	private List<String> voucherContentList;

	/** 凭证介质类型：（ 1手机号    2 二维码或条码及其辅助码  3身份证号 )  参照VoucherContentType */
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
	private Integer voucherState;

	/** 产品线(景区、演艺、住宿、特产、小交通、线路)  参照ProductCategory 枚举对象 （取值  GlobalDict.ProductCategory）  */
	private Integer voucherCategory;

	/** 供应商id */
	private Long supplierId;

	/** 交易号 */
	private String transactionId;

	/** 创建时间 */
	private Date createTime;

	/** 记录核销的产品和核销数量 */
	private List<VoucherConfirm> voucherConfirmList;

	/** 精确日期（仅限供订查询有效期开始日期的数据使用）  */
	private String accurateStartTime;

	private String accurateEndTime;

	/** 根据voucherId批量查询使用 */
	private List<Long> voucherIdList;

	/** 凭证拓展熟悉集合 */
	private List<ExtendVoucher> extendVoucherList;

	/* 搜索条件  */
	/** 创建开始时间 */
	private Date createStartTime;

	/** 创建结束时间 */
	private Date createEndTime;

	/** 演艺产品信息 */
//	@Deprecated
	//private TheaterProductVoucherVO theaterProductVoucherVO;
	/**针对非一证一票的voucher存储是一个voucher对应多个产品信息，所以要存储为多个*/
	private List<TheaterProductVoucherVO> theaterProductVoucherVOList;
	/** 特产产品信息 */
	//private SpecialityProductVoucherVo specialityProductVoucherVo;
	/**
	 * 实际核销时间(首次核销时间)
	 */
	private Date voucherTime;

	//景区ID
	private long sceneId;

	public Long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(final Long voucherId) {
		this.voucherId = voucherId;
	}

	public String getVoucherContent() {
		return voucherContent;
	}

	public void setVoucherContent(final String voucherContent) {
		this.voucherContent = voucherContent == null ? null : voucherContent.trim();
	}

	public Integer getVoucherContentType() {
		return voucherContentType;
	}

	public void setVoucherContentType(final Integer voucherContentType) {
		this.voucherContentType = voucherContentType;
	}

	//	public String getContactMobile() {
	//		return contactMobile;
	//	}
	//
	//	public void setContactMobile(String contactMobile) {
	//		this.contactMobile = contactMobile == null ? null : contactMobile.trim();
	//	}
	//
	//	public String getContactName() {
	//		return contactName;
	//	}
	//
	//	public void setContactName(String contactName) {
	//		this.contactName = contactName == null ? null : contactName.trim();
	//	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(final Date startTime) {
		this.startTime = startTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(final Date expireTime) {
		this.expireTime = expireTime;
	}

	public Date getShowStartTime() {
		return showStartTime;
	}

	public void setShowStartTime(final Date showStartTime) {
		this.showStartTime = showStartTime;
	}

	public Date getShowEndTime() {
		return showEndTime;
	}

	public void setShowEndTime(final Date showEndTime) {
		this.showEndTime = showEndTime;
	}

	public Integer getVoucherState() {
		return voucherState;
	}

	public void setVoucherState(final Integer voucherState) {
		this.voucherState = voucherState;
	}

	public Integer getVoucherCategory() {
		return voucherCategory;
	}

	public void setVoucherCategory(final Integer voucherCategory) {
		this.voucherCategory = voucherCategory;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(final Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(final String transactionId) {
		this.transactionId = transactionId == null ? null : transactionId.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(final Date createTime) {
		this.createTime = createTime;
	}

	public List<ExtendVoucher> getExtendVoucherList() {
		return extendVoucherList;
	}

	public void setExtendVoucherList(final List<ExtendVoucher> extendVoucherList) {
		this.extendVoucherList = extendVoucherList;
	}

	public String getAccurateStartTime() {
		return accurateStartTime;
	}

	public void setAccurateStartTime(final String accurateStartTime) {
		this.accurateStartTime = accurateStartTime;
	}

	public String getAccurateEndTime() {
		return accurateEndTime;
	}

	public void setAccurateEndTime(final String accurateEndTime) {
		this.accurateEndTime = accurateEndTime;
	}

	public List<VoucherConfirm> getVoucherConfirmList() {
		return voucherConfirmList;
	}

	public void setVoucherConfirmList(final List<VoucherConfirm> voucherConfirmList) {
		this.voucherConfirmList = voucherConfirmList;
	}

	public List<Long> getVoucherIdList() {
		return voucherIdList;
	}

	public void setVoucherIdList(final List<Long> voucherIdList) {
		this.voucherIdList = voucherIdList;
	}

	//	public ScenicPruductVoucherVO getScenicPruductVoucherVO() {
	//		return scenicPruductVoucherVO;
	//	}
	//
	//	public void setScenicPruductVoucherVO(ScenicPruductVoucherVO scenicPruductVoucherVO) {
	//		this.scenicPruductVoucherVO = scenicPruductVoucherVO;
	//	}

	//	@Deprecated
	//	public TheaterProductVoucherVO getTheaterProductVoucherVO() {
	//		return theaterProductVoucherVO;
	//	}
	//
	//	@Deprecated
	//	public void setTheaterProductVoucherVO(TheaterProductVoucherVO theaterProductVoucherVO) {
	//		this.theaterProductVoucherVO = theaterProductVoucherVO;
	//	}

	//	public SpecialityProductVoucherVo getSpecialityProductVoucherVo() {
	//		return specialityProductVoucherVo;
	//	}
	//
	//	public void setSpecialityProductVoucherVo(SpecialityProductVoucherVo specialityProductVoucherVo) {
	//		this.specialityProductVoucherVo = specialityProductVoucherVo;
	//	}

	public Date getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(final Date createStartTime) {
		this.createStartTime = createStartTime;
	}

	public Date getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(final Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	public List<TheaterProductVoucherVO> getTheaterProductVoucherVOList() {
		return theaterProductVoucherVOList;
	}

	public void setTheaterProductVoucherVOList(final List<TheaterProductVoucherVO> theaterProductVoucherVOList) {
		this.theaterProductVoucherVOList = theaterProductVoucherVOList;
	}

	public Date getVoucherTime() {
		return voucherTime;
	}

	public void setVoucherTime(final Date voucherTime) {
		this.voucherTime = voucherTime;
	}

	public List<String> getVoucherContentList() {
		return voucherContentList;
	}

	public void setVoucherContentList(final List<String> voucherContentList) {
		this.voucherContentList = voucherContentList;
	}

	/**
	 * Getter method for property <tt>sceneId</tt>.
	 * 
	 * @return property value of sceneId
	 */
	public long getSceneId() {
		return sceneId;
	}

	/**
	 * Setter method for property <tt>sceneId</tt>.
	 * 
	 * @param sceneId value to be assigned to property sceneId
	 */
	public void setSceneId(long sceneId) {
		this.sceneId = sceneId;
	}
}
