package com.pzj.core.trade.book.engine;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.book.dao.entity.BookEntity;
import com.pzj.core.trade.book.dao.entity.BookQueryEntity;
import com.pzj.core.trade.book.dao.read.BookRMapper;
import com.pzj.core.trade.book.engine.model.BookQueryEModel;
import com.pzj.core.trade.book.engine.model.BookQueryResultEModel;
import com.pzj.core.trade.book.engine.model.OrderBook;
import com.pzj.core.trade.log.common.LogEventEnum;
import com.pzj.core.trade.log.dao.entity.OperLogEntity;
import com.pzj.core.trade.log.dao.read.OperLogRMapper;
import com.pzj.core.trade.order.engine.exception.OrderException;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.common.BookStatusEnum;
import com.pzj.trade.book.common.BookTypeEnum;

/**
 * 查询预约单/特价票、免票订单
 * 
 * @author Administrator
 * @version $Id: BookQueryEngine.java, v 0.1 2017年3月9日 下午3:39:09 Administrator Exp $
 */
@Component(value = "bookQueryEngine")
public class BookQueryEngine {
	private final static Logger logger = LoggerFactory.getLogger(BookQueryEngine.class);

	@Resource(name = "bookRMapper")
	private BookRMapper bookRMapper;

	@Resource(name = "operLogRMapper")
	private OperLogRMapper operLogRMapper;

	public BookQueryResultEModel queryPageByParam(BookQueryEntity param) {
		int totalNum = bookRMapper.countBooksByParam(param);
		BookQueryResultEModel result = new BookQueryResultEModel.Bulider()
				.setCurrentPage(param.getPage().getCurrentPage()).setPageSize(param.getPage().getPageSize())
				.setTotal(totalNum).bulid();
		if (totalNum == 0) {
			return result;
		}

		List<BookEntity> data = bookRMapper.selectBooksByParam(param);
		result.setData(data);

		return result;

	}

	/**
	 * 根据预约单ID查询预约单以及它的父预约单
	 * 
	 * @param bookId
	 * @return
	 */
	public OrderBook queryOrderBookByBookId(String bookId) {
		if (Check.NuNString(bookId)) {
			return null;
		}
		OrderBook orderBook = new OrderBook();
		BookEntity book = bookRMapper.selectByBookId(bookId);
		if (Check.NuNObject(book)) {
			return null;
		}
		if (book.getBookId().equals(book.getTransactionId())) {
			orderBook.setPreOrder(book);
			orderBook.setBook(book);
		} else {
			BookEntity srcBook = bookRMapper.selectByBookId(book.getTransactionId());
			orderBook.setBook(srcBook);
			orderBook.setPreOrder(book);
		}
		if (orderBook.getBook().getBookStatus() != BookStatusEnum.BOOKING.getStatus()) {
			throw new OrderException(15001,
					"该预约单状态:" + BookStatusEnum.getMsg(orderBook.getBook().getBookStatus()) + "不能出票");
		}
		logger.info("根据bookId查询预约单和前置订单:" + JSONConverter.toJson(orderBook));
		return orderBook;
	}

	public BookQueryEModel queryByBookId(String bookId) {
		BookQueryEModel model = new BookQueryEModel();
		BookEntity entity = bookRMapper.selectByBookId(bookId);
		if (Check.NuNObject(entity)) {
			return model;
		}
		model.setEntity(entity);

		OperLogEntity log = operLogRMapper.selectCurrentDrawLog(bookId);
		if (!Check.NuNObject(log) && LogEventEnum.BOOK_REFUSE.getEvent().equals(log.getEvent())) {
			model.setLog(log);
		}

		return model;

	}

	/**
	 * 查询待出票的过期前置订单(过期：据创建)
	 * 
	 * 
	 * @return
	 */
	public List<BookEntity> queryInvalidPreOrder(final Integer minute) {
		BookQueryEntity entity = new BookQueryEntity();
		entity.setBookStatus(BookStatusEnum.BOOKING.getStatus());
		entity.setBookType(BookTypeEnum.BOOK_PRE_ORDER.getType());
		return bookRMapper.selectInvalidBook(entity, minute * 60 * 1000);
	}

	/**
	 * 查询未审核的免票特价票
	 * 
	 * 
	 * @return
	 */
	public List<BookEntity> querySparpreisNotAudit(final Integer hour) {
		BookQueryEntity entity = new BookQueryEntity();
		entity.setBookStatus(BookStatusEnum.AUDITING.getStatus());
		entity.setBookTypes(Arrays.asList(BookTypeEnum.FREE_TICKET.getType(), BookTypeEnum.CHEAP_TICKET.getType()));
		Long overdueTime = System.currentTimeMillis() + hour * 3600000;
		return bookRMapper.selectquerySparpreisNotCheck(entity, overdueTime);
	}

	public List<BookEntity> queryByParam(BookQueryEntity param) {

		return bookRMapper.selectBooksByParam(param);
	}

}
