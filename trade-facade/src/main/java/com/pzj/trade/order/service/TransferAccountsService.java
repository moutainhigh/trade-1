/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.order.model.TransferAccountsReqModel;
import com.pzj.trade.order.vo.OrderTransferAccountsVO;

/**
 * 查询转账明细服务
 *
 * @author DongChunfu
 * @version $Id: TransferAccountsService.java, v 0.1 2017年3月25日 下午12:36:30 DongChunfu Exp $
 */
public interface TransferAccountsService {
	/**
	 * 查询分账明细集合
	 *
	 * @param reqModel 分账明细请求参数(必填)
	 * @param context 服务上下文(可控)
	 * @return 分账明细集合
	 */
	Result<QueryResult<OrderTransferAccountsVO>> queryTransferAccountsDetail(TransferAccountsReqModel reqModel,
			ServiceContext context);
}
