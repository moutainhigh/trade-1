package com.pzj.core.trade.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.core.trade.book.ServiceTestData;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.book.model.TouristModel;
import com.pzj.trade.order.entity.OrderResponse;
import com.pzj.trade.order.model.ContacteeModel;
import com.pzj.trade.order.model.FilledModel;
import com.pzj.trade.order.model.MultiOrderInModel;
import com.pzj.trade.order.model.MultiOrderProductModel;
import com.pzj.trade.order.service.OrderService;

/**
 * 多级分销订单测试类.
 * @author chj
 *
 */
public class OrderServiceMultiTest {

	ApplicationContext context;

	OrderService orderService;

	@Before
	public void setUp() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
		orderService = context.getBean(OrderService.class);

		Assert.assertNotNull(orderService);
	}

	@Test
	public void testOneLevel() {
		MultiOrderInModel model = ServiceTestData.getInstance().createTestData("/createOrder/oneLevel.json",
				MultiOrderInModel.class);
		Result<OrderResponse> result = orderService.createMultilevelOrder(model, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void testThreeLevel() {
		MultiOrderInModel model = ServiceTestData.getInstance().createTestData("/createOrder/threeLevel.json",
				MultiOrderInModel.class);
		Result<OrderResponse> result = orderService.createMultilevelOrder(model, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void testRealIDcard() {
		MultiOrderInModel model = ServiceTestData.getInstance().createTestData("/createOrder/851694567296937984.json",
				MultiOrderInModel.class);
		Result<OrderResponse> result = orderService.createMultilevelOrder(model, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void aaa() {
		OrderServiceMultiTest o = new OrderServiceMultiTest();
		System.out.println(JSONConverter.toJson(o.getMultiOrderInModel()));
	}

	/**
	 * 
	 * @return
	 */
	public MultiOrderInModel getMultiOrderInModel() {
		MultiOrderInModel orderVO = new MultiOrderInModel();
		orderVO.setBookId("1");
		orderVO.setGuideId(123);
		orderVO.setGuideIdName("i am guide");
		orderVO.setOperatorId(123L);
		orderVO.setPayerId(123);
		orderVO.setRemark("i am remark");
		orderVO.setTicketOfficeId(123);
		orderVO.setTouristSourceCity("beijing");
		orderVO.setTravel(123);
		orderVO.setTravelDate(new Date().getTime());
		orderVO.setTravelDepartName("depart");

		orderVO.setVourType(1);
		orderVO.setResellerId(3579271797276673L);//need set
		orderVO.setSalePort(1);

		setTourists(orderVO);

		setProducts(orderVO);

		setContact(orderVO);

		setFilleds(orderVO);

		return orderVO;
	}

	/**
	 * 
	 * @param orderVO
	 */
	private void setProducts(MultiOrderInModel orderVO) {
		List<MultiOrderProductModel> products = new ArrayList<MultiOrderProductModel>();
		products.add(getProduct());
		orderVO.setProducts(products);
	}

	/**
	 * 
	 * @return
	 */
	private MultiOrderProductModel getProduct() {
		MultiOrderProductModel product = new MultiOrderProductModel();
		product.setParentUserId(2216619741563734L);//
		product.setSubUserId(3579271797276673L);//
		product.setStrategyRelationId(844749751128190976L);//
		product.setPrice(100);
		product.setProductId(839809607457910784L);//
		product.setProductNum(2);
		product.setScenicId(1);
		List<Long> seat = new ArrayList<Long>();
		seat.add(1L);
		seat.add(2L);
		product.setSeats(seat);
		return product;
	}

	/**
	 * 
	 * @param orderVO
	 */
	private void setFilleds(MultiOrderInModel orderVO) {
		List<FilledModel> filleds = new ArrayList<FilledModel>();
		FilledModel f = new FilledModel();
		f.setAttr_key("testgroup");
		f.setAttr_key("testkey");
		f.setAttr_value("testvalue");
		filleds.add(f);
		orderVO.setFilleds(filleds);
	}

	/**
	 * 
	 * @param orderVO
	 */
	private void setContact(MultiOrderInModel orderVO) {
		ContacteeModel contact = new ContacteeModel();
		contact.setIdcardNo("111111111111111111");
		contact.setContactee("chai");
		contact.setContactMobile("18600000000");
		orderVO.setContactee(contact);
	}

	/**
	 * 
	 * @param orderVO
	 */
	private void setTourists(MultiOrderInModel orderVO) {
		List<TouristModel> tourists = new ArrayList<TouristModel>();
		TouristModel tourist = new TouristModel();
		tourist.setProductId(839809607457910784L);
		tourist.setTourist("chai");
		tourist.setIdcardNo("1111111111111111113");
		tourist.setTouristMobile("18000000000");
		tourists.add(tourist);
		TouristModel tourist2 = new TouristModel();
		tourist2.setProductId(839809607457910784L);
		tourist2.setTourist("chai2");
		tourist2.setIdcardNo("1111111111111111114");
		tourist2.setTouristMobile("18000000000");
		tourists.add(tourist2);
		orderVO.setTourists(tourists);
	}
}
