package com.pzj.trade.order.model;

import java.io.Serializable;

/**
 * 
 * @author GLG
 */
public class ResellerPriceDetailRespModel implements Serializable {
	/**  */
	private static final long serialVersionUID = -935824531902028132L;
	/** 分销商ID */
	private long reseller_id;
	/**供应商ID*/
	private long supplier_id;
	/**建议零售价*/
	private double price;
	/**结算价*/
	private double detailPrice;
	/**后返*/
	private double rebate_amount;
	/**总数量*/
	private int total_num;
	/**现返、周期返*/
	private int rebate_object;
	/**true分销  false供应*/
	private boolean isReseller;

	/** 政策id */
	private long strategyId;

	public long getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(long strategyId) {
		this.strategyId = strategyId;
	}

	public boolean isReseller() {
		return isReseller;
	}

	public void setReseller(boolean isReseller) {
		this.isReseller = isReseller;
	}

	public int getRebate_object() {
		return rebate_object;
	}

	public void setRebate_object(int rebate_object) {
		this.rebate_object = rebate_object;
	}

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(long reseller_id) {
		this.reseller_id = reseller_id;
	}

	public long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDetailPrice() {
		return detailPrice;
	}

	public void setDetailPrice(double detailPrice) {
		this.detailPrice = detailPrice;
	}

	public double getRebate_amount() {
		return rebate_amount;
	}

	public void setRebate_amount(double rebate_amount) {
		this.rebate_amount = rebate_amount;
	}

	public int getTotal_num() {
		return total_num;
	}

	public void setTotal_num(int total_num) {
		this.total_num = total_num;
	}

}
