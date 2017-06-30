package com.pzj.core.trade.book.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.book.dao.entity.BookEntity;
import com.pzj.core.trade.book.dao.entity.BookQueryEntity;
import com.pzj.core.trade.book.engine.BookQueryEngine;
import com.pzj.core.trade.book.engine.exception.BookException;
import com.pzj.core.trade.book.engine.model.BookQueryEModel;
import com.pzj.core.trade.book.engine.model.BookQueryResultEModel;
import com.pzj.core.trade.book.resolver.BookQueryResolver;
import com.pzj.core.trade.book.validator.BookQueryValidater;
import com.pzj.core.trade.exception.TradeException;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.entity.QueryResult;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.model.BookDetailModel;
import com.pzj.trade.book.model.BookQueryInModel;
import com.pzj.trade.book.model.BookQueryOutModel;
import com.pzj.trade.book.model.DeliveryCodeVModel;
import com.pzj.trade.book.model.SparpreisQueryInModel;
import com.pzj.trade.book.model.SparpreisQueryOutModel;
import com.pzj.trade.book.service.BookQueryService;

@Service(value = "bookQueryService")
public class BookQueryServiceImpl implements BookQueryService {

	private final static Logger logger = LoggerFactory.getLogger(BookQueryServiceImpl.class);

	@Resource(name = "deliveryCodeValicater")
	private ObjectConverter<DeliveryCodeVModel, Void, Boolean> deliveryCodeValicater;

	@Resource(name = "deliveryCodeResolver")
	private ObjectConverter<DeliveryCodeVModel, Void, BookQueryEntity> deliveryCodeResolver;

	@Resource(name = "bookQueryEngine")
	private BookQueryEngine bookEngine;

	@Override
	public Result<QueryResult<BookQueryOutModel>> queryBooks(BookQueryInModel model, ServiceContext context) {
		try {
			//1. 验证查询参数
			BookQueryValidater.INTANCE.validBook(model);

			final String args = JSONConverter.toJson(model);
			logger.info("预约单分页查询参数:{} ", args);

			//2. 构建预约单查询模型.
			final BookQueryEntity queryModel = BookQueryResolver.INTANCE.convertQueryModel(model);

			//3. 预约单构建流程.
			final BookQueryResultEModel resultModel = bookEngine.queryPageByParam(queryModel);

			//4.构建预约单查询结果集
			QueryResult<BookQueryOutModel> resultData = BookQueryResolver.INTANCE.convertBookResult(resultModel);
			logger.debug("预约单分页查询结果: " + JSONConverter.toJson(resultData));

			return new Result<QueryResult<BookQueryOutModel>>(resultData);

		} catch (final Throwable e) {
			logger.error("预约单分页查询失败, reqModel: " + (JSONConverter.toJson(model)), e);
			TradeException ex = null;
			if (!(e instanceof TradeException)) {
				ex = new BookException(e.getMessage());
			} else {
				ex = (TradeException) e;
			}
			return new Result<QueryResult<BookQueryOutModel>>(ex.getErrCode(), ex.getMessage());
		}
	}

	@Override
	public Result<BookDetailModel> queryBookByBookId(String bookId, ServiceContext context) {
		try {
			//1. 验证查询参数
			BookQueryValidater.INTANCE.validBookId(bookId);
			logger.info("预约单详情查询参数: {}", bookId);

			//2. 查询预约单
			final BookQueryEModel resultModel = bookEngine.queryByBookId(bookId);

			//3.构建预约单查询结果集
			final BookDetailModel resultData = BookQueryResolver.INTANCE.convertBookDetail(resultModel);
			logger.debug("预约单详情查询结果: " + JSONConverter.toJson(resultData));

			return new Result<BookDetailModel>(resultData);

		} catch (final Throwable e) {
			logger.error("预约单详情查询失败, reqModel: " + bookId, e);
			TradeException ex = null;
			if (!(e instanceof TradeException)) {
				ex = new BookException(e.getMessage());
			} else {
				ex = (TradeException) e;
			}
			return new Result<BookDetailModel>(ex.getErrCode(), ex.getMessage());
		}
	}

	@Override
	public Result<QueryResult<SparpreisQueryOutModel>> querySparpreis(SparpreisQueryInModel model,
			ServiceContext context) {
		try {
			//1. 验证查询参数
			BookQueryValidater.INTANCE.validSparpreis(model);

			final String args = JSONConverter.toJson(model);
			logger.info("特价票、免票订单分页查询参数:{}", args);

			//2. 构建预约单查询模型.
			final BookQueryEntity queryModel = BookQueryResolver.INTANCE.convertQueryModel(model);

			//3. 预约单构建流程.
			final BookQueryResultEModel resultModel = bookEngine.queryPageByParam(queryModel);

			//4.构建预约单查询结果集
			QueryResult<SparpreisQueryOutModel> resultData = BookQueryResolver.INTANCE
					.convertSparpreisResult(resultModel);
			logger.debug("特价票、免票订单分页查询结果: " + JSONConverter.toJson(resultData));

			return new Result<QueryResult<SparpreisQueryOutModel>>(resultData);

		} catch (final Throwable e) {
			logger.error("特价票、免票订单分页查询失败, reqModel: " + (JSONConverter.toJson(model)), e);
			TradeException ex = null;
			if (!(e instanceof TradeException)) {
				ex = new BookException(e.getMessage());
			} else {
				ex = (TradeException) e;
			}
			return new Result<QueryResult<SparpreisQueryOutModel>>(ex.getErrCode(), ex.getMessage());
		}
	}

	@Override
	public Result<Boolean> validateCode(DeliveryCodeVModel model, ServiceContext context) {
		try {
			//1.验证参数有效性
			deliveryCodeValicater.convert(model, null);
			logger.info("特价票、免票订单验证提货码参数: {}", JSONConverter.toJson(model));

			//3.预约单取消流程.
			BookQueryEntity queryModel = deliveryCodeResolver.convert(model, null);
			List<BookEntity> queryResult = bookEngine.queryByParam(queryModel);
			logger.debug("特价票、免票订单验证提货码参数: " + JSONConverter.toJson(queryResult));

			return new Result<Boolean>(!Check.NuNCollections(queryResult));

		} catch (final Throwable e) {
			logger.error("特价票、免票订单验证提货码失败, reqModel: " + (JSONConverter.toJson(model)), e);
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
