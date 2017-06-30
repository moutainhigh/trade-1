package com.pzj.core.trade.book;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.trade.book.model.BookCancelModel;
import com.pzj.trade.book.model.BookEditModel;
import com.pzj.trade.book.model.BookInModel;
import com.pzj.trade.book.model.BookResponse;
import com.pzj.trade.book.model.SparpreisCheckModel;
import com.pzj.trade.book.service.BookService;

public class BookServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(BookServiceTest.class);

	ApplicationContext context;

	BookService bookService;

	@Before
	public void setUp() {
		context = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/*.xml");
		bookService = (BookService) context.getBean("bookService");

		Assert.assertNotNull(bookService);
	}

	/**
	 * 创建预约单测试
	 */
	@Test
	public void testCreateBooks() {
		BookInModel model = ServiceTestData.getInstance().createTestData("/book/BookCreate.json", BookInModel.class);

		Result<BookResponse> result = bookService.createBook(model, null);
		assertNotNull(result);
		assertNotNull(result.getErrorCode());
		assertNotNull(result.getData());
		assertEquals(true, result.isOk());

		System.out.println(result.getData().toString());

	}

	/**
	 * 更新预约单测试
	 */
	@Test
	public void testUpdateBooks() {
		BookEditModel model = ServiceTestData.getInstance().createTestData("/book/BookEdit.json", BookEditModel.class);
		Result<BookResponse> result = bookService.editBooker(model, null);
		assertNotNull(result);
		assertNotNull(result.getErrorCode());
		assertNotNull(result.getData());
		assertEquals(true, result.isOk());

		System.out.println(result.getData().toString());

	}

	@Test
	public void testCreatePerBook() {
		BookInModel model = ServiceTestData.getInstance().createTestData("/book/PerOrderCreate.json",
				BookInModel.class);
		Result<BookResponse> result = bookService.createBook(model, null);
		assertNotNull(result);
		assertNotNull(result.getErrorCode());
		assertNotNull(result.getData());
		assertEquals(true, result.isOk());

		System.out.println(result.getData().toString());

	}

	/**
	 * 创建前置订单信息
	 */
	@Test
	public void testUpdatePerBook() {
		BookInModel model = ServiceTestData.getInstance().createTestData("/book/PerOrderUpdate.json",
				BookInModel.class);
		Result<BookResponse> result = bookService.createBook(model, null);
		assertNotNull(result);
		assertNotNull(result.getErrorCode());
		assertNotNull(result.getData());
		assertEquals(true, result.isOk());

		System.out.println(result.getData().toString());

	}

	@Test
	public void testCancalBook() {
		BookCancelModel model = ServiceTestData.getInstance().createTestData("/book/BookCancel.json",
				BookCancelModel.class);
		Result<Boolean> result = bookService.cancel(model, null);
		assertNotNull(result);
		assertNotNull(result.getErrorCode());
		assertNotNull(result.getData());
		assertEquals(true, result.isOk());

		System.out.println(result.getData().toString());
	}

	@Test
	public void testAuditBook() {
		SparpreisCheckModel model = ServiceTestData.getInstance().createTestData("/book/SparpreisCheck.json",
				SparpreisCheckModel.class);
		Result<Boolean> result = bookService.audit(model, null);
		assertNotNull(result);
		assertNotNull(result.getErrorCode());
		assertNotNull(result.getData());
		assertEquals(true, result.isOk());

		System.out.println(result.getData().toString());
	}

	@Test
	public void testRefuseBook() {
		SparpreisCheckModel model = ServiceTestData.getInstance().createTestData("/book/SparpreisRefuse.json",
				SparpreisCheckModel.class);
		Result<Boolean> result = bookService.refuse(model, null);
		assertNotNull(result);
		assertNotNull(result.getErrorCode());
		assertNotNull(result.getData());
		assertEquals(true, result.isOk());

		System.out.println(result.getData().toString());
	}

}
