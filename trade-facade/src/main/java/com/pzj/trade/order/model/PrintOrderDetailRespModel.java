package com.pzj.trade.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 打印门票响应结果
 * @author GLG 
 * 
 */
public class PrintOrderDetailRespModel implements Serializable {

	private static final long serialVersionUID = -2306196522971689910L;
	/** 订单id*/
	private String orderId;
	/**订单金额	*/
	private BigDecimal totalAmount;

	/** 商品模型 */
	private List<PrintMerchDetailRespModel> printMerchDetailRespModel = new ArrayList<PrintMerchDetailRespModel>();

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<PrintMerchDetailRespModel> getPrintMerchDetailRespModel() {
		return printMerchDetailRespModel;
	}

	public void setPrintMerchDetailRespModel(List<PrintMerchDetailRespModel> printMerchDetailRespModel) {
		this.printMerchDetailRespModel = printMerchDetailRespModel;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PrintOrderDetailRespModel [orderId=");
		builder.append(orderId);
		builder.append(", totalAmount=");
		builder.append(totalAmount);
		builder.append(", printMerchDetailRespModel=");
		builder.append(printMerchDetailRespModel);
		builder.append("]");
		return builder.toString();
	}
}
