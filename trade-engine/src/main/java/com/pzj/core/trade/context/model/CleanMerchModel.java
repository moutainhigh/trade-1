package com.pzj.core.trade.context.model;

import com.pzj.core.trade.context.common.TradeTaskEnum;
import com.pzj.core.trade.context.exe.base.ExecuteBaseModel;

public class CleanMerchModel extends ExecuteBaseModel {
	public CleanMerchModel() {
		super.setTask(TradeTaskEnum.merchClened);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 9028320518664974866L;
	private String supplierMerchId;
	private String supplierOrderId;

	public String getSupplierMerchId() {
		return supplierMerchId;
	}

	public void setSupplierMerchId(String supplierMerchId) {
		this.supplierMerchId = supplierMerchId;
	}

	public String getSupplierOrderId() {
		return supplierOrderId;
	}

	public void setSupplierOrderId(String supplierOrderId) {
		this.supplierOrderId = supplierOrderId;
	}
}
