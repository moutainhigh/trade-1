package com.pzj.trade.book.service;

import com.pzj.framework.context.Result;

public interface BookTaskService {
	/**
	 * 取消10分钟内不出票的前置订单，释放库存
	 * 
	 * @return
	 */
	Result<Boolean> cancelPreBook(final Integer minute, final Long operator);

	/**
	 * 若干小时未审核的免票特价票凭证自动取消，释放库存
	 * 
	 * @return
	 */
	Result<Boolean> cancelSparpreisNotAudit(final Integer hour, final Long operator);

}
