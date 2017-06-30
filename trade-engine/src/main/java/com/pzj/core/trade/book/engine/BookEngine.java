package com.pzj.core.trade.book.engine;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.book.dao.entity.BookEntity;
import com.pzj.core.trade.book.dao.read.BookRMapper;
import com.pzj.core.trade.book.dao.write.BookWMapper;
import com.pzj.core.trade.book.engine.calculate.BookCancelCalculate;
import com.pzj.core.trade.book.engine.calculate.BookCreateCalculate;
import com.pzj.core.trade.book.engine.calculate.BookUpdateCalculate;
import com.pzj.core.trade.book.engine.calculate.PreOrderCancelCalculate;
import com.pzj.core.trade.book.engine.calculate.PreOrderCreateCalculate;
import com.pzj.core.trade.book.engine.calculate.PreOrderUpdateCalculate;
import com.pzj.core.trade.book.engine.converter.BookJsonConverter;
import com.pzj.core.trade.book.engine.converter.BookStockConverter;
import com.pzj.core.trade.book.engine.exception.BookException;
import com.pzj.core.trade.book.engine.model.BookCreateEModel;
import com.pzj.core.trade.book.engine.model.BookJsonEModel;
import com.pzj.core.trade.log.common.LogEventEnum;
import com.pzj.core.trade.log.dao.entity.OperLogEntity;
import com.pzj.core.trade.log.engine.OperLogEngine;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.common.BookStatusEnum;
import com.pzj.trade.book.common.BookTypeEnum;

/**
 * 预约单/特价票、免票创建
 * 
 * @author Administrator
 * @version $Id: BookEngine.java, v 0.1 2017年3月7日 上午10:25:01 Administrator Exp $
 */
@Component(value = "bookEngine")
public class BookEngine {

	@Resource(name = "bookWMapper")
	private BookWMapper bookWMapper;

	@Resource(name = "bookRMapper")
	private BookRMapper bookRMapper;

	@Resource(name = "operLogEngine")
	private OperLogEngine operLogEngine;

	@Resource
	private BookCreateCalculate bookCreateCalculate;

	@Resource
	private BookUpdateCalculate bookUpdateCalculate;

	@Resource
	private PreOrderCreateCalculate preOrderCreateCalculate;

	@Resource
	private PreOrderUpdateCalculate preOrderUpdateCalculate;

	@Resource
	private BookCancelCalculate bookCancelCalculate;

	@Resource
	private PreOrderCancelCalculate preOrderCancelCalculate;

	private void insert(BookEntity entity, OperLogEntity log) {
		//创建预约单记录
		if (entity.getBookDate() <= 0)
			entity.setBookDate(new Date().getTime());
		entity.setUpdateTime(entity.getBookDate());
		bookWMapper.insert(entity);

		//记录日志
		operLogEngine.generateLog(log, BookStatusEnum.INITIAL.getStatus(), entity);
	}

	/**
	 * 
	 * 
	 * @param entity
	 * @param log
	 * @param prev 预约单的操作前状态
	 */
	private void update(final BookEntity entity, final OperLogEntity log, final int prev) {
		//更新预约单
		entity.setUpdateTime(new Date().getTime());
		bookWMapper.updateByPrimaryKey(entity);

		//记录日志
		operLogEngine.generateLog(log, prev, entity);
	}

	/**
	 * 操作步骤：
	 * 1.创建预约单,免票特价票，窗口前置订单
	 * 2.占座
	 * 
	 * @param book
	 * @return
	 */
	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 3)
	public String createBook(BookCreateEModel book) {
		if (book.getEntity().getBookType() == BookTypeEnum.BOOK_PRE_ORDER.getType()
				&& !Check.NuNString(book.getEntity().getSrcBookId())) {

			createFrontOrder(book);
			return book.getEntity().getSrcBookId();
		}

		insert(book.getEntity(), book.generateLog(LogEventEnum.BOOK_CREATE.getEvent()));
		//占座
		bookCreateCalculate.occupyStock(book.getEntity());

		return book.getEntity().getBookId();

	}

	/**
	 * 1.查询库存是否已有该预约单的前置订单。有更新，无新建
	 * 2.新建/更新记录
	 * 3.占库存
	 * 
	 * 
	 * 
	 * @param book
	 * @return
	 */
	private void createFrontOrder(final BookCreateEModel book) {
		//获取前置订单
		final BookEntity preBook = bookRMapper.selectBySrcBookId(book.getEntity().getSrcBookId());
		//获取预约单
		final BookEntity oldBook = bookRMapper.selectValidByBookId(book.getEntity().getSrcBookId());

		if (Check.NuNObject(preBook)) {
			insert(book.getEntity(), book.generateLog(LogEventEnum.BOOK_CREATE.getEvent()));

			//占座
			preOrderCreateCalculate.occupyStock(book.getEntity(), oldBook);

		} else {
			book.getEntity().setBookId(preBook.getBookId());
			update(book.getEntity(), book.generateLog(LogEventEnum.BOOK_UPDATE.getEvent()), preBook.getBookStatus());

			//占座
			preOrderUpdateCalculate.occupyStock(book.getEntity(), oldBook, preBook.getNeedStockEntity());

		}

	}

	/**
	 * 操作步骤：
	 * 1.更新预约单记录
	 * 2.占座
	 * 
	 * 
	 * @param book
	 * @return
	 */
	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 3)
	public String doEdit(BookCreateEModel book) {
		//1.获取数据库里的预约单/特价票、免票订单
		BookEntity havbook = bookRMapper.selectByBookId(book.getEntity().getBookId());
		if (Check.NuNObject(havbook)) {
			throw new BookException("库里没有找到预约单/特价票、免票订单，单号[" + book.getEntity().getBookId() + "]");
		}

		//2.更新预约单记录
		update(book.getEntity(), book.generateLog(LogEventEnum.BOOK_UPDATE.getEvent()), havbook.getBookStatus());

		//3.占座更新
		if (Check.NuNString(book.getEntity().getTransactionId())) {
			book.getEntity().setTransactionId(havbook.getTransactionId());
		}

		if (BookStockConverter.INTANCE.isNeedStockFDiffTDate(book.getEntity(), havbook)) {
			bookCancelCalculate.occupyStock(havbook.getNeedStockEntity());
			bookCreateCalculate.occupyStock(book.getEntity());
		} else {
			bookUpdateCalculate.occupyStock(book.getEntity(), havbook.getNeedStockEntity());
		}

		return book.getEntity().getBookId();

	}

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 3)
	public boolean doAudit(String bookId, String auditor, OperLogEntity log) {

		//1.获取预约单信息
		BookEntity havBook = bookRMapper.selectByBookId(bookId);
		if (Check.NuNObject(havBook)) {
			throw new BookException("库里没有找到预约单/特价票、免票订单，单号[" + bookId + "]");
		}

		//2.组装Json数据
		BookEntity entity = new BookEntity(bookId, log.getNext());
		if (!Check.NuNString(auditor)) {
			BookJsonEModel json = BookJsonConverter.INTANCE.getBookJson(havBook);
			json.setAuditor(auditor);
			json.setAuditorReason(log.getContext());
			entity.setBookDetail(JSONConverter.toJson(json));
		}

		//3.更新预约单记录
		update(entity, log, havBook.getBookStatus());
		return true;

	}

	/**
	 * 操作步骤：
	 * 1.取消预约单
	 * 2.取消占座(还没做)
	 * 
	 * 
	 * @param book
	 * @return
	 */
	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 3)
	public boolean doCancel(String bookId, String auditor, OperLogEntity log) {

		//1.获取预约单信息,取消预约单的座位
		final BookEntity havBook = bookRMapper.selectByBookId(bookId);
		if (Check.NuNObject(havBook)) {
			throw new BookException("库里没有找到预约单/特价票、免票订单，单号[" + bookId + "]");
		}
		if (havBook.getBookStatus() == BookStatusEnum.DRAWNED.getStatus()
				|| havBook.getBookStatus() == BookStatusEnum.CANCELED.getStatus()) {
			throw new BookException("预约单/特价票、免票订单已" + BookStatusEnum.getMsg(havBook.getBookStatus()) + "，不能取消 ");
		}

		//2.组装Json数据
		BookEntity entity = new BookEntity(bookId, log.getNext());
		if (!Check.NuNString(auditor)) {
			BookJsonEModel json = BookJsonConverter.INTANCE.getBookJson(havBook);
			json.setAuditor(auditor);
			json.setAuditorReason(log.getContext());
			entity.setBookDetail(JSONConverter.toJson(json));
		}

		//3.更新预约单记录
		update(entity, log, havBook.getBookStatus());

		//3.取消占座
		if (havBook.getBookType() == BookTypeEnum.BOOK_PRE_ORDER.getType()) {
			final BookEntity oldBook = bookRMapper.selectValidByBookId(havBook.getSrcBookId());
			preOrderCancelCalculate.occupyStock(havBook.getNeedStockEntity(), oldBook);
		} else {
			final BookEntity preBook = bookRMapper.selectValidBySrcBookId(bookId);
			bookCancelCalculate.occupyStock(havBook, preBook);
		}

		return true;

	}

	/**
	 * 操作步骤：
	 * 1.取消预约单
	 * 2.取消占座(还没做)
	 * 
	 * 
	 * @param book
	 * @return
	 */
	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 3)
	public boolean doCancelPreBook(String bookId, String auditor, OperLogEntity log) {

		//1.获取预约单信息
		final BookEntity oldBook = bookRMapper.selectByBookId(bookId);
		if (Check.NuNObject(oldBook)) {
			throw new BookException("库里没有找到预约单，单号[" + bookId + "]");
		}

		//2.获取预约单的前置订单,取消前置订单的座位
		final BookEntity havBook = bookRMapper.selectValidBySrcBookId(bookId);
		if (Check.NuNObject(havBook)) {
			return true;
		}

		//2.组装Json数据
		BookEntity entity = new BookEntity(havBook.getBookId(), log.getNext());
		if (!Check.NuNString(auditor)) {
			BookJsonEModel json = BookJsonConverter.INTANCE.getBookJson(havBook);
			json.setAuditor(auditor);
			json.setAuditorReason(log.getContext());
			entity.setBookDetail(JSONConverter.toJson(json));
		}

		//3.更新预约单记录
		log.setOrderId(havBook.getBookId());
		update(entity, log, havBook.getBookStatus());

		//4.取消占座
		preOrderCancelCalculate.occupyStock(havBook.getNeedStockEntity(), oldBook);

		return true;

	}

}
