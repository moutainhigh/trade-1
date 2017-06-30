package com.pzj.voucher.engine.model;

/**
 * 库存信息模型.
 * @author YRJ
 *
 */
public class StockInfoModel {

	private long productId;

	private long stockId;

	private int productNum;

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getStockId() {
		return stockId;
	}

	public void setStockId(long stockId) {
		this.stockId = stockId;
	}

	public int getProductNum() {
		return productNum;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}
}
