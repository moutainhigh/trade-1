/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.book.engine.model;

import com.pzj.core.trade.book.dao.entity.BookEntity;

/**
 * 
 * @author Administrator
 * @version $Id: OrderBook.java, v 0.1 2017年3月13日 下午2:02:22 Administrator Exp $
 */
public class OrderBook {
	/**
	 * 预约单
	 */
	private BookEntity book;
	/**
	 * 前置订单
	 */
	private BookEntity preOrder;

	/**
	 * Getter method for property <tt>book</tt>.
	 * 
	 * @return property value of book
	 */
	public BookEntity getBook() {
		return book;
	}

	/**
	 * Setter method for property <tt>book</tt>.
	 * 
	 * @param book value to be assigned to property book
	 */
	public void setBook(BookEntity book) {
		this.book = book;
	}

	/**
	 * Getter method for property <tt>preOrder</tt>.
	 * 
	 * @return property value of preOrder
	 */
	public BookEntity getPreOrder() {
		return preOrder;
	}

	/**
	 * Setter method for property <tt>preOrder</tt>.
	 * 
	 * @param preOrder value to be assigned to property preOrder
	 */
	public void setPreOrder(BookEntity preOrder) {
		this.preOrder = preOrder;
	}

}
