package com.pzj.trade.book.model;

import java.io.Serializable;

public class DeliveryCodeVModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @apiDefine DeliveryCodeVModel  DeliveryCodeVModel 提货码验证入参
	 * 
	 * @apiParam (DeliveryCodeVModel) {String} bookId  特价票，免票订单ID  必填
	
	 * @apiParam (DeliveryCodeVModel) {String} deliveryCode 提货码
	 * 
	 */
	private String bookId;

	private String deliveryCode;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(final String bookId) {
		this.bookId = bookId;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(final String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

}
