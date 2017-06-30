package com.pzj.trade.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 供应商订单列表响应结果.
 * @author YRJ
 *
 */
public class SupplierOrdersOutModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String orderId;

	private String porderId;

	private int orderStatus;

	private BigDecimal totalAmount;

	private Timestamp createTime;

	/** 联系人 */
	private String contactee;

	/** 联系电话 */
	private String contact_mobile;

	/**是否直签，1-直签，2-非直签（即魔方)*/
	private int is_direct;

	/**
	 * 是否需要确认.
	 * 1. 不需要确认
	 * 2. 需要确认
	 * 3. 已确认. 
	 */
	private int needConfirm;

	private List<SupplierMerchOutModel> merchs = new ArrayList<SupplierMerchOutModel>();

	/** 分销商Id*/
	private long reseller_id;

	/** 渠道 */
	private long channel_id;
	/** 分销商名称 */
	private String reseller_name;
	/**渠道名称*/
	private String channel_name;

	public long getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(long channel_id) {
		this.channel_id = channel_id;
	}

	public String getReseller_name() {
		return reseller_name;
	}

	public void setReseller_name(String reseller_name) {
		this.reseller_name = reseller_name;
	}

	public String getChannel_name() {
		return channel_name;
	}

	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(long reseller_id) {
		this.reseller_id = reseller_id;
	}

	public SupplierOrdersOutModel() {
	}

	public SupplierOrdersOutModel(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPorderId() {
		return porderId;
	}

	public void setPorderId(String porderId) {
		this.porderId = porderId;
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

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
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

	public int getIs_direct() {
		return is_direct;
	}

	public void setIs_direct(int is_direct) {
		this.is_direct = is_direct;
	}

	public int getNeedConfirm() {
		return needConfirm;
	}

	public void setNeedConfirm(int needConfirm) {
		this.needConfirm = needConfirm;
	}

	public List<SupplierMerchOutModel> getMerchs() {
		return merchs;
	}

	public void setMerchs(List<SupplierMerchOutModel> merchs) {
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
		SupplierOrdersOutModel other = (SupplierOrdersOutModel) obj;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		return true;
	}
}
