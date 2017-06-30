package com.pzj.voucher.engine.model;

public class ProductInfo {

	private long prodId;
	private String merchName;
	private int merchNum;
	private int varie;

	public long getProdId() {
		return prodId;
	}

	public void setProdId(final long prodId) {
		this.prodId = prodId;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(final String merchName) {
		this.merchName = merchName;
	}

	public int getMerchNum() {
		return merchNum;
	}

	public void setMerchNum(final int merchNum) {
		this.merchNum = merchNum;
	}

	public int getVarie() {
		return varie;
	}

	public void setVarie(final int varie) {
		this.varie = varie;
	}

	@Override
	public String toString() {
		final StringBuilder tostr = new StringBuilder();
		tostr.append(prodId).append(",");
		tostr.append(merchName).append(",");
		tostr.append(merchNum).append(",");
		tostr.append("-").append(",");
		tostr.append(varie);
		return tostr.toString();
	}
}
