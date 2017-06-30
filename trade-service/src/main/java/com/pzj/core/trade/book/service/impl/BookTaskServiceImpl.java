package com.pzj.core.trade.book.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.book.dao.entity.BookEntity;
import com.pzj.core.trade.book.engine.BookEngine;
import com.pzj.core.trade.book.engine.BookQueryEngine;
import com.pzj.core.trade.book.engine.exception.BookException;
import com.pzj.core.trade.exception.TradeException;
import com.pzj.core.trade.log.common.LogEventEnum;
import com.pzj.core.trade.log.dao.entity.OperLogEntity;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.common.BookStatusEnum;
import com.pzj.trade.book.service.BookTaskService;

@Service(value = "bookTaskService")
public class BookTaskServiceImpl implements BookTaskService {
	private final static Logger logger = LoggerFactory.getLogger(BookTaskServiceImpl.class);

	@Resource(name = "bookEngine")
	private BookEngine bookEngine;

	@Resource(name = "bookQueryEngine")
	private BookQueryEngine bookQueryEngine;

	@Override
	public Result<Boolean> cancelPreBook(final Integer minute, final Long operator) {
		try {
			//1.获取失效的前置订单
			List<BookEntity> books = bookQueryEngine.queryInvalidPreOrder(minute);
			logger.debug("失效的前置订单:" + JSONConverter.toJson(books));

			//2.逐个取消
			if (Check.NuNCollections(books)) {
				return new Result<Boolean>(true);
			}
			final OperLogEntity log = new OperLogEntity(null, operator, LogEventEnum.BOOK_CANCEL.getEvent(),
					BookStatusEnum.CANCELED.getStatus());
			for (BookEntity entity : books) {
				log.setOrderId(entity.getBookId());
				boolean result = bookEngine.doCancel(entity.getBookId(), null, log);
				if (!result) {
					logger.error("{}定时取消前置订单失败，预约单号[{}]", new Date(), entity.getBookId());
				}
			}

			return new Result<Boolean>(true);
		} catch (final Throwable e) {
			logger.error("{}定时取消前置订单失败，异常信息：{}", new Date(), e);
			TradeException ex = null;
			if (!(e instanceof TradeException)) {
				ex = new BookException(e.getMessage());
			} else {
				ex = (TradeException) e;
			}
			return new Result<Boolean>(ex.getErrCode(), ex.getMessage());
		}
	}

	@Override
	public Result<Boolean> cancelSparpreisNotAudit(Integer hour, final Long operator) {
		try {
			//1.获取未审核的免票特价票
			List<BookEntity> books = bookQueryEngine.querySparpreisNotAudit(hour);
			logger.debug("获取未审核的免票特价票:" + JSONConverter.toJson(books));

			//2.逐个取消
			if (Check.NuNCollections(books)) {
				return new Result<Boolean>(true);
			}
			final OperLogEntity log = new OperLogEntity(null, operator, LogEventEnum.BOOK_CANCEL.getEvent(),
					BookStatusEnum.CANCELED.getStatus());
			for (BookEntity entity : books) {
				log.setOrderId(entity.getBookId());
				boolean result = bookEngine.doCancel(entity.getBookId(), null, log);
				if (!result) {
					logger.error("{}定时取消免票特价票失败，预约单号[{}]", new Date(), entity.getBookId());
				}
			}

			return new Result<Boolean>(true);
		} catch (final Throwable e) {
			logger.error("{}定时取消免票特价票失败，异常信息：{}", new Date(), e);
			TradeException ex = null;
			if (!(e instanceof TradeException)) {
				ex = new BookException(e.getMessage());
			} else {
				ex = (TradeException) e;
			}
			return new Result<Boolean>(ex.getErrCode(), ex.getMessage());
		}
	}

}
