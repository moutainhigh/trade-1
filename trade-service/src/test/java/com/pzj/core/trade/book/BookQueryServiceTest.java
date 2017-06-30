package com.pzj.core.trade.book;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.book.model.BookDetailModel;
import com.pzj.trade.book.model.BookQueryInModel;
import com.pzj.trade.book.model.BookQueryOutModel;
import com.pzj.trade.book.model.DeliveryCodeVModel;
import com.pzj.trade.book.model.SparpreisQueryInModel;
import com.pzj.trade.book.model.SparpreisQueryOutModel;
import com.pzj.trade.book.service.BookQueryService;

public class BookQueryServiceTest {

	ApplicationContext context;

	BookQueryService bookQueryService;

	@Before
	public void setUp() {
		context = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/*.xml");
		bookQueryService = (BookQueryService) context.getBean("bookQueryService");

		Assert.assertNotNull(bookQueryService);
	}

	@Test
	public void testQueryBooks() {
		final BookQueryInModel model = ServiceTestData.getInstance().createTestData("/book/BookQuery.json",
				BookQueryInModel.class);
		final Result<QueryResult<BookQueryOutModel>> result = bookQueryService.queryBooks(model, null);
		assertNotNull(result);
		assertNotNull(result.getErrorCode());
		assertNotNull(result.getData());
		assertEquals(true, result.isOk());

		System.out.println(result.getData().toString());

	}

	@Test
	public void testQuerySparpreis() {
		final SparpreisQueryInModel model = ServiceTestData.getInstance().createTestData("/book/SparpreisQuery.json",
				SparpreisQueryInModel.class);
		final Result<QueryResult<SparpreisQueryOutModel>> result = bookQueryService.querySparpreis(model, null);
		assertNotNull(result);
		assertNotNull(result.getErrorCode());
		assertNotNull(result.getData());
		assertEquals(true, result.isOk());

		System.out.println(result.getData().toString());

	}

	@Test
	public void testQueryBookByBookId() {
		final String bookId = "2273717052500001";
		final Result<BookDetailModel> result = bookQueryService.queryBookByBookId(bookId, null);
		assertNotNull(result);
		assertNotNull(result.getErrorCode());
		assertNotNull(result.getData());
		assertEquals(true, result.isOk());

		System.out.println(result.getData().toString());

	}

	@Test
	public void testValidateCode() {
		final DeliveryCodeVModel code = ServiceTestData.getInstance().createTestData("/book/ValidateCode.json",
				DeliveryCodeVModel.class);
		final Result<Boolean> result = bookQueryService.validateCode(code, null);
		assertNotNull(result);
		assertNotNull(result.getErrorCode());
		assertNotNull(result.getData());
		assertEquals(true, result.isOk());

		System.out.println(result.getData().toString());

	}

}
