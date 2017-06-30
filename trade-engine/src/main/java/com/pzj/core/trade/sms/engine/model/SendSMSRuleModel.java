package com.pzj.core.trade.sms.engine.model;

/**
 * 短信发送规则模型
 * @author kangzl
 *
 */
public class SendSMSRuleModel {
	private long skuId;

	/**
	 * 联系人的确认短信
	 */
	private boolean contractConfirmMsg = false;
	/**
	 * 联系人的凭证短信
	 */
	private boolean contractVoucherMsg = false;
	/**
	 * 供应商短信
	 */
	private boolean supplierMsg = false;

	/**
	 * 产品名称
	 */
	private String prodcutName;

	/**
	 * 规格名称
	 */
	private String skuName;

	/**
	 * 凭证类型. 0: 未凭证; 1: 联系人信息; 2: 魔方码  3:身份证
	 */
	private int vourType;

	/**
	 * 核销方式 :1：按订单2：按规格3：按份数
	 */
	private int verificationType;

	/**
	 * 产品类型 1: 景区; 2: 票; 3: 住宿; 4: 小交通; 5: 特产;6:一日游 
	 */
	private int merchType;

	public long getSkuId() {
		return skuId;
	}

	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}

	public boolean isContractConfirmMsg() {
		return contractConfirmMsg;
	}

	public void setContractConfirmMsg(boolean contractConfirmMsg) {
		this.contractConfirmMsg = contractConfirmMsg;
	}

	public boolean isContractVoucherMsg() {
		return contractVoucherMsg;
	}

	public void setContractVoucherMsg(boolean contractVoucherMsg) {
		this.contractVoucherMsg = contractVoucherMsg;
	}

	public boolean isSupplierMsg() {
		return supplierMsg;
	}

	public void setSupplierMsg(boolean supplierMsg) {
		this.supplierMsg = supplierMsg;
	}

	public String getProdcutName() {
		return prodcutName;
	}

	public void setProdcutName(String prodcutName) {
		this.prodcutName = prodcutName;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public int getVourType() {
		return vourType;
	}

	public void setVourType(int vourType) {
		this.vourType = vourType;
	}

	public int getVerificationType() {
		return verificationType;
	}

	public void setVerificationType(int verificationType) {
		this.verificationType = verificationType;
	}

	public int getMerchType() {
		return merchType;
	}

	public void setMerchType(int merchType) {
		this.merchType = merchType;
	}
}
