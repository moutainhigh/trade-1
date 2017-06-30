package com.pzj.core.trade.voucher.entity;

public class VoucherBindEntity {

	private String transactionId;

	private long voucherId;

	private String mfcode;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(long voucherId) {
		this.voucherId = voucherId;
	}

	public String getMfcode() {
		return mfcode;
	}

	public void setMfcode(String mfcode) {
		this.mfcode = mfcode;
	}
}
