package com.pzj.trade.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OperatorOrdersOutModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String orderId;

	/** */
	private String pOrderId;

	private int orderStatus;

	private BigDecimal totalAmount;

	private Date createTime;

	/** 联系人 */
	private String contactee;

	/** 联系电话 */
	private String contact_mobile;

	private List<OperatorMerchOutModel> merchs = new ArrayList<OperatorMerchOutModel>();

	/** 分销商Id*/
	private long reseller_id;

	/** 分销商名称 */
	private String reseller_name;

	public String getReseller_name() {
		return reseller_name;
	}

	public void setReseller_name(String reseller_name) {
		this.reseller_name = reseller_name;
	}

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(long reseller_id) {
		this.reseller_id = reseller_id;
	}

	public OperatorOrdersOutModel() {
	}

	public OperatorOrdersOutModel(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getpOrderId() {
		return pOrderId;
	}

	public void setpOrderId(String pOrderId) {
		this.pOrderId = pOrderId;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContactee() {
		return contactee;
	}

	public void setContactee(String contactee) {
		this.contactee = contactee;
	}

	public String getContact_mobile() {
		return contact_mobile;
	}

	public void setContact_mobile(String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}

	public List<OperatorMerchOutModel> getMerchs() {
		return merchs;
	}

	public void setMerchs(List<OperatorMerchOutModel> merchs) {
		this.merchs = merchs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperatorOrdersOutModel other = (OperatorOrdersOutModel) obj;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		return true;
	}
}
