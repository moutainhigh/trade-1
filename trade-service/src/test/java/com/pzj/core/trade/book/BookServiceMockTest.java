package com.pzj.core.trade.book;

import mockit.Deencapsulation;
import mockit.NonStrictExpectations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.order.build.CheckinPointAssembler;
import com.pzj.core.trade.order.build.MerchAssembler;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.book.model.BookInModel;
import com.pzj.trade.book.service.BookService;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class })
@ContextConfiguration(locations = "/META-INF/spring/*.xml")
public class BookServiceMockTest {

	ApplicationContext context;

	@Autowired
	BookService bookService;

	@Autowired
	MerchAssembler merchBuilder;

	@Autowired
	CheckinPointAssembler checkinPointAssembler;

	@Test
	public void testMockit() {

		new NonStrictExpectations(merchBuilder) {
			{
				Deencapsulation.invoke(merchBuilder, "getProductOutModel", 813592458135965696l);
				result = BookProductData.getProduct(813592458135965696l);
				System.out.println(JSONConverter.toJson(result));

				/*Deencapsulation.invoke(checkinPointAssembler, "getProductOutModel", 813592458135965696l);
				result = null;*/
			}
		};

		final BookInModel model = ServiceTestData.getInstance().createTestData("/bookMock/BookCreateMock.json", BookInModel.class);
		bookService.createBook(model, null);

	}
}