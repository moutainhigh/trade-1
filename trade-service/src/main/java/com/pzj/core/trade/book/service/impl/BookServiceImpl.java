package com.pzj.core.trade.book.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.book.engine.BookEngine;
import com.pzj.core.trade.book.engine.exception.BookException;
import com.pzj.core.trade.book.engine.exception.BookStockException;
import com.pzj.core.trade.book.engine.model.BookCreateEModel;
import com.pzj.core.trade.book.resolver.StockResponseResolver;
import com.pzj.core.trade.exception.TradeException;
import com.pzj.core.trade.log.common.LogEventEnum;
import com.pzj.core.trade.log.dao.entity.OperLogEntity;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.trade.book.common.BookStatusEnum;
import com.pzj.trade.book.model.BookCancelModel;
import com.pzj.trade.book.model.BookEditModel;
import com.pzj.trade.book.model.BookInModel;
import com.pzj.trade.book.model.BookResponse;
import com.pzj.trade.book.model.SparpreisCheckModel;
import com.pzj.trade.book.service.BookService;

@Service(value = "bookService")
public class BookServiceImpl implements BookService {

	private final static Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

	@Resource(name = "bookInValidater")
	private ObjectConverter<BookInModel, Void, Boolean> bookInValidater;

	@Resource(name = "bookInResolver")
	private ObjectConverter<BookInModel, Void, BookCreateEModel> bookInResolver;

	@Resource(name = "bookEditValidater")
	private ObjectConverter<BookEditModel, Void, Boolean> bookEditValidater;

	@Resource(name = "bookEditResolver")
	private ObjectConverter<BookEditModel, Void, BookCreateEModel> bookEditResolver;

	@Resource(name = "bookCancelValidater")
	private ObjectConverter<BookCancelModel, Void, Boolean> bookCancelValidater;

	@Resource(name = "sparpreisCheckValidater")
	private ObjectConverter<SparpreisCheckModel, Void, Boolean> sparpreisCheckValidater;

	@Resource(name = "bookEngine")
	private BookEngine bookEngine;

	@Override
	public Result<BookResponse> createBook(BookInModel model, ServiceContext context) {
		try {
			//1. 验证下单参数的合法性.
			bookInValidater.convert(model, null);

			final String args = JSONConverter.toJson(model);
			logger.info("[BookService.createBook]预约单创建参数: " + args);

			//2. 根据购买的商品构建预约单模型.
			final BookCreateEModel createModel = bookInResolver.convert(model, null);

			//3. 预约单构建流程.
			final String bookId = bookEngine.createBook(createModel);
			logger.info("[BookService.createBook]预约单创建成功, 预约单id: " + bookId);

			return new Result<BookResponse>(new BookResponse(bookId));

		} catch (final BookStockException e) {
			logger.error("[BookService.createBook]预约单创建失败,库存服务异常, reqModel: " + (JSONConverter.toJson(model)), e);
			Result<BookResponse> result = new Result<BookResponse>(e.getErrCode(), e.getMessage());
			result.setData(StockResponseResolver.INTANCE.convertBookR(e.getStockException()));
			return result;

		} catch (final Throwable e) {
			logger.error("[BookService.createBook]预约单创建失败, reqModel: " + (JSONConverter.toJson(model)), e);

			TradeException ex = null;
			if (!(e instanceof TradeException)) {
				ex = new BookException(e.getMessage());
			} else {
				ex = (TradeException) e;
			}
			return new Result<BookResponse>(ex.getErrCode(), ex.getMessage());
		}
	}

	@Override
	public Result<BookResponse> editBooker(BookEditModel model, ServiceContext context) {
		try {
			//1. 验证下单参数的合法性.
			bookEditValidater.convert(model, null);

			final String args = JSONConverter.toJson(model);
			logger.info("[BookService.editBooker]预约单更新参数: " + args);

			//2. 根据购买的商品构建预约单模型.
			final BookCreateEModel createModel = bookEditResolver.convert(model, null);

			//3. 预约单构建流程.
			final String bookId = bookEngine.doEdit(createModel);
			logger.info("[BookService.editBooker]预约单更新成功, 预约单: " + bookId);

			return new Result<BookResponse>(new BookResponse(bookId));

		} catch (final BookStockException e) {
			logger.error("[BookService.createBook]预约单创建失败,库存服务异常, reqModel: " + (JSONConverter.toJson(model)), e);
			Result<BookResponse> result = new Result<BookResponse>(e.getErrCode(), e.getMessage());
			result.setData(StockResponseResolver.INTANCE.convertBookR(e.getStockException()));
			return result;

		} catch (final Throwable e) {
			logger.error("[BookService.editBooker]预约单更新失败, reqModel: " + (JSONConverter.toJson(model)), e);
			TradeException ex = null;
			if (!(e instanceof TradeException)) {
				ex = new BookException(e.getMessage());
			} else {
				ex = (TradeException) e;
			}
			return new Result<BookResponse>(ex.getErrCode(), ex.getMessage());
		}
	}

	@Override
	public Result<Boolean> audit(SparpreisCheckModel model, ServiceContext context) {
		try {
			//1.验证参数有效性
			sparpreisCheckValidater.convert(model, null);
			logger.info("[BookService.audit]免票特价票审核通过参数: " + JSONConverter.toJson(model));

			//2.构建取消模型
			OperLogEntity log = new OperLogEntity(model.getBookId(), model.getOperatorId(),
					LogEventEnum.BOOK_AUDIT.getEvent(), BookStatusEnum.BOOKING.getStatus());

			//3.预约单取消流程.
			bookEngine.doAudit(model.getBookId(), model.getAuditor(), log);

			return new Result<Boolean>(true);

		} catch (final Throwable e) {
			logger.error("[BookService.audit]免票特价票审核通过失败, reqModel: " + (JSONConverter.toJson(model)), e);
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
	public Result<Boolean> refuse(SparpreisCheckModel model, ServiceContext context) {
		try {
			//1.验证参数有效性
			sparpreisCheckValidater.convert(model, null);
			logger.info("[BookService.refuse]免票特价票拒绝参数: " + JSONConverter.toJson(model));

			//2.构建取消模型
			OperLogEntity log = new OperLogEntity(model.getBookId(), model.getOperatorId(),
					LogEventEnum.BOOK_REFUSE.getEvent(), BookStatusEnum.REFUSED.getStatus());
			log.setContext(model.getReason());

			//3.预约单取消流程.
			bookEngine.doCancel(model.getBookId(), model.getAuditor(), log);

			return new Result<Boolean>(true);

		} catch (final Throwable e) {
			logger.error("[BookService.refuse]免票特价票拒绝失败, reqModel: " + (JSONConverter.toJson(model)), e);
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
	public Result<Boolean> cancel(BookCancelModel model, ServiceContext context) {
		try {
			//1.验证参数有效性
			bookCancelValidater.convert(model, null);
			logger.info("[BookService.cancel]免票特价票拒绝取消参数: " + JSONConverter.toJson(model));

			//2.构建取消模型
			OperLogEntity log = new OperLogEntity(model.getBookId(), model.getOperatorId(),
					LogEventEnum.BOOK_CANCEL.getEvent(), BookStatusEnum.CANCELED.getStatus());

			//3.预约单取消流程.
			bookEngine.doCancel(model.getBookId(), null, log);

			return new Result<Boolean>(true);

		} catch (final Throwable e) {
			logger.error("[BookService.cancel]免票特价票拒绝取消失败, reqModel: " + (JSONConverter.toJson(model)), e);
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
	public Result<Boolean> cancelPreBook(BookCancelModel model, ServiceContext context) {
		try {
			//1.验证参数有效性
			bookCancelValidater.convert(model, null);
			logger.info("[BookService.cancelPreBook]预约单的前置订单取消参数: " + JSONConverter.toJson(model));

			//2.构建取消模型
			OperLogEntity log = new OperLogEntity(null, model.getOperatorId(), LogEventEnum.BOOK_CANCEL.getEvent(),
					BookStatusEnum.CANCELED.getStatus());

			//3.预约单取消流程.
			bookEngine.doCancelPreBook(model.getBookId(), null, log);

			return new Result<Boolean>(true);

		} catch (final Throwable e) {
			logger.error("[BookService.cancelPreBook]预约单的前置订单取消失败, reqModel: " + (JSONConverter.toJson(model)), e);
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
