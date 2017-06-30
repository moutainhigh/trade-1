/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.export.model;

import java.io.Serializable;

/**
 * 导出订单验证请求参数
 *
 * @author DongChunfu
 * @version $Id: OrderExportVerifyReqModel.java, v 0.1 2017年2月20日 上午10:45:12 DongChunfu Exp $
 */
public class OrderExportVerifyReqModel implements Serializable {

	private static final long serialVersionUID = 4145982885831073262L;

	/**主键*/
	private int id;

	public OrderExportVerifyReqModel() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

}
