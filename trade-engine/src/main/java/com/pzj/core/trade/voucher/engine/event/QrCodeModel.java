package com.pzj.core.trade.voucher.engine.event;

public class QrCodeModel {

	private String qrCode;

	private String qrCodeUrl;

	public QrCodeModel(String qrCode, String url) {
		this.qrCode = qrCode;
		qrCodeUrl = url;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	@Override
	public String toString() {
		StringBuilder tostr = new StringBuilder();
		tostr.append(QrCodeModel.class.getSimpleName());
		tostr.append("[");
		tostr.append("qrCode=").append(qrCode);
		tostr.append(", qrCodeUrl=").append(qrCodeUrl);
		tostr.append("]");
		return tostr.toString();
	}
}
