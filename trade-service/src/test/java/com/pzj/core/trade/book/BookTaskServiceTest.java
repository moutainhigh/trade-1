package com.pzj.core.trade.book;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.trade.book.service.BookTaskService;

public class BookTaskServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(BookTaskServiceTest.class);

	ApplicationContext context;

	BookTaskService bookTaskService;

	@Before
	public void setUp() {
		context = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/*.xml");
		bookTaskService = (BookTaskService) context.getBean("bookTaskService");

		Assert.assertNotNull(bookTaskService);
	}

	/**
	 * 创建预约单测试
	 */
	@Test
	public void testCancelPreBook() {
		int minute = 10;
		long operator = 123456789l;
		Result<Boolean> result = bookTaskService.cancelPreBook(minute, operator);
		assertNotNull(result);
		assertNotNull(result.getErrorCode());
		assertNotNull(result.getData());
		assertEquals(true, result.isOk());

		System.out.println(result.getData().toString());

	}

	public static void main(String[] args) {
		String dateStr = "Fri Apr 07 20:40:28 GMT+08:00 2017";
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", java.util.Locale.US);
		Date date = null;
		try {
			date = sdf.parse(dateStr);

			System.out.println("date = " + date.getTime());

			Date date1 = new Date(1491568828000l);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("date1 = " + format.format(date1));
			System.out.println("date2 = " + (format.parse("2017-04-06").getTime()));
		} catch (ParseException e) {
			logger.error("", e);
		}

	}

}
