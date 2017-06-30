package com.pzj.core.trade.order;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.financeCenter.request.AccountOrdersReqModel;
import com.pzj.trade.order.entity.AgentOrderResponse;
import com.pzj.trade.order.entity.OrderDetailResponse;
import com.pzj.trade.order.entity.OrderListResponse;
import com.pzj.trade.order.entity.OrderStrategyResponse;
import com.pzj.trade.order.entity.RemarkResponse;
import com.pzj.trade.order.entity.ReportOrderResponse;
import com.pzj.trade.order.entity.SalesOrderResponse;
import com.pzj.trade.order.entity.SupplierOrderDetailResponse;
import com.pzj.trade.order.model.AppOrdersReqModel;
import com.pzj.trade.order.model.AppOrdersRespModel;
import com.pzj.trade.order.model.AppRebateOrdersReqModel;
import com.pzj.trade.order.model.AppRebateOrdersRespModel;
import com.pzj.trade.order.model.OrderMerchConfirmsReqModel;
import com.pzj.trade.order.model.OrderMerchConfirmsRespModel;
import com.pzj.trade.order.model.OrderRemarksPageReqModel;
import com.pzj.trade.order.model.ResellerOrderDetailReqModel;
import com.pzj.trade.order.model.ResellerOrderDetailRespModel;
import com.pzj.trade.order.model.ResellerOrdersReqModel;
import com.pzj.trade.order.model.ResellerOrdersRespModel;
import com.pzj.trade.order.model.SettlementOrdersReqModel;
import com.pzj.trade.order.model.SettlementOrdersRespModel;
import com.pzj.trade.order.model.SupplierOrderDetailReqModel;
import com.pzj.trade.order.model.SupplierOrdersReqModel;
import com.pzj.trade.order.model.SupplierOrdersRespModel;
import com.pzj.trade.order.service.OrderQueryService;
import com.pzj.trade.order.vo.OrderDetailVO;
import com.pzj.trade.order.vo.OrderListVO;
import com.pzj.trade.order.vo.PlatformOrderListVO;
import com.pzj.trade.order.vo.PlatformRefundOrderListVO;
import com.pzj.trade.order.vo.ReportOrderVO;
import com.pzj.trade.order.vo.SalesOrderVO;

public class OrderQueryServiceTest {

	static ApplicationContext context = null;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("classpath:/applicationContext-test.xml");
		System.out.println(context);
	}

	private OrderQueryService orderQueryService;

	@Before
	public void setUp() {
		orderQueryService = context.getBean(OrderQueryService.class);
	}

	/**
	 * 正确的查询订单详情.
	 */
	@Test
	public void testQueryOrderDetail() {
		//		ServiceContext context = ServiceContext.getServiceContext();
		final OrderDetailVO orderDetailVO = new OrderDetailVO();
		//		orderDetailVO.setOrder_id("MF1561539686");
		//		orderDetailVO.setCall_port(1);
		//		System.out.println(JSONConverter.toJson(context));
		//		System.out.println(JSONConverter.toJson(orderDetailVO));
		orderDetailVO.setOrder_id("1073417050300003");
		//		orderDetailVO.setCall_port(1);
		final Result<OrderDetailResponse> resp = orderQueryService.queryOrderDetail(orderDetailVO, null);
		Assert.assertNotNull(resp);

		System.out.println(JSONConverter.toJson(resp));
	}

	@Test
	public void testQuerySalesOrderDetail() {
		final SalesOrderVO s = new SalesOrderVO();
		s.setOrder_id("1093317042600010");
		s.setOrder_type(2);
		final Result<SalesOrderResponse> resp = orderQueryService.querySalesOrderDetail(s, null);
		Assert.assertNotNull(resp);
		System.out.println(JSONConverter.toJson(resp));
	}

	@Test
	public void testOrderListByPlatform() throws ParseException {
		final PlatformOrderListVO orderVO = new PlatformOrderListVO();
		//orderVO.setReseller_order_id("MF563172642");
		//orderVO.setMerch_state(2);
		//		orderVO.setOrder_id("MF1001358438");
		//orderVO.setAgent_flag(2);
		//		orderVO.setNeed_confirm(2);
		//		new Date();
		//		new SimpleDateFormat("yyyyMMdd");
		//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//		orderVO.setVisit_start_time(sdf.parse("20160802"));
		//		orderVO.setVisit_end_time(sdf.parse("20160804"));
		//orderVO.setMerch_name("123");
		//orderVO.setQr_code("TG22725301");
		//		List<String> supplier_order_ids = new ArrayList<String>();
		//		supplier_order_ids.add("2216619741563734");
		//		orderVO.setSupplier_order_ids(supplier_order_ids);
		//orderVO.setOrder_id("MF682577729");
		orderVO.setCurrent_page(1);
		orderVO.setPage_size(10);

		//		orderVO.setOrder_id("MF1464485272");
		//		orderVO.setOnline_pay(0);

		final Result<com.pzj.framework.entity.QueryResult<OrderListResponse>> resp = orderQueryService.queryOrdersOnPlatform(orderVO, null);

		System.out.println("===>" + resp);
		Assert.assertNotNull(resp);
		System.out.println("===>" + JSONConverter.toJson(resp.getData()));
	}

	@Test
	public void testqueryRefundOrdersOnPlatform() throws ParseException {
		final PlatformRefundOrderListVO orderVO = new PlatformRefundOrderListVO();
		//orderVO.setReseller_order_id("MF563172642");
		//orderVO.setMerch_state(2);
		//		orderVO.setOrder_id("MF1001358438");
		//orderVO.setAgent_flag(2);
		//		orderVO.setNeed_confirm(2);
		new Date();
		//				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//				orderVO.setVisit_start_time(sdf.parse("20160802"));
		//				orderVO.setVisit_end_time(sdf.parse("20160804"));
		//orderVO.setMerch_name("123");
		//orderVO.setQr_code("TG22725301");
		//		List<String> supplier_order_ids = new ArrayList<String>();
		//supplier_order_ids.add("MF794386255");
		//		orderVO.setSupplier_order_ids(supplier_order_ids);
		//orderVO.setOrder_id("MF682577729");
		//		List<String> supplier_order_ids = new ArrayList<String>();
		//		orderVO.setSupplier_order_ids(supplier_order_ids);
		//		orderVO.setPage_size(10);
		//		List<Long> guides = new ArrayList<Long>();
		//		guides.add(2216619736563754l);
		//		guides.add(2216619736563788l);
		//		orderVO.setGuide_ids(guides);
		final Result<com.pzj.framework.entity.QueryResult<OrderListResponse>> resp = orderQueryService.queryRefundAuditOrdersOnPlatform(orderVO, null);
		System.out.println("===>" + resp);
		Assert.assertNotNull(resp);
		System.out.println("===>" + JSONConverter.toJson(resp.getData()));
	}

	@Test
	public void testqueryForceRefundOrdersOnPlatform() throws ParseException {
		final PlatformRefundOrderListVO orderVO = new PlatformRefundOrderListVO();
		//orderVO.setReseller_order_id("MF563172642");
		//orderVO.setMerch_state(2);
		//		orderVO.setOrder_id("MF1001358438");
		//orderVO.setAgent_flag(2);
		//		orderVO.setNeed_confirm(2);
		new Date();
		//				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//				orderVO.setVisit_start_time(sdf.parse("20160802"));
		//				orderVO.setVisit_end_time(sdf.parse("20160804"));
		//orderVO.setMerch_name("123");
		//orderVO.setQr_code("TG22725301");
		//		List<String> supplier_order_ids = new ArrayList<String>();
		//supplier_order_ids.add("MF794386255");
		//		orderVO.setSupplier_order_ids(supplier_order_ids);
		//		orderVO.setOrder_id("MF1551767927");
		//		List<String> supplier_order_ids = new ArrayList<String>();
		//		orderVO.setSupplier_order_ids(supplier_order_ids);
		orderVO.setPage_size(1);
		//		orderVO.setRefund_state(0);
		orderVO.setPage_size(10);
		//		List<Long> guides = new ArrayList<Long>();
		//		guides.add(2216619736563754l);
		//		guides.add(2216619736563788l);
		//		orderVO.setGuide_ids(guides);
		final Result<com.pzj.framework.entity.QueryResult<OrderListResponse>> resp = orderQueryService.queryForceRefundOrdersOnPlatform(orderVO, null);
		//		System.out.println("===>" + resp);
		//		Assert.assertNotNull(resp);
		//		System.out.println("===>" + JSONConverter.toJson(resp.getData()));
		for (final OrderListResponse or : resp.getData().getRecords()) {
			System.out.println(or.getOrderId());
		}
	}

	@Test
	public void testSupplier() throws ParseException {
		final Result<QueryResult<SupplierOrderDetailResponse>> resp = orderQueryService.querySupplierOrderDetailByPlatform("1072217050200020", null);
		Assert.assertNotNull(resp);
		System.out.println("----------------------" + JSONConverter.toJson(resp));
	}

	@Test
	public void testOrderListByReseller() {
		final OrderListVO orderVO = new OrderListVO();

		orderVO.setOrder_id("1073417050300003");
		//		orderVO.setPage_size(10);
		//		orderVO.setReseller_id(2216619736763787l);
		//		orderVO.setOperator_id(2216619736763735l);
		final Result<QueryResult<OrderListResponse>> resp = orderQueryService.queryOrdersByReseller(orderVO, null);
		Assert.assertNotNull(resp);
		System.out.println(JSONConverter.toJson(resp));
	}

	@Test
	public void testQueryOrdersBySupplier() {
		final SupplierOrdersReqModel supplierOrdersReqModel = new SupplierOrdersReqModel();

		supplierOrdersReqModel.setOrder_id("MF755994619");
		supplierOrdersReqModel.setSupplier_id(2216619736763722l);
		supplierOrdersReqModel.setMerch_state(3);

		//		orderVO.setPage_size(10);
		//		orderVO.setReseller_id(3017465999654913l);
		final Result<SupplierOrdersRespModel> resp = orderQueryService.queryOrdersBySupplier(supplierOrdersReqModel, null);
		Assert.assertNotNull(resp);
		System.out.println(JSONConverter.toJson(resp));
	}

	@Test
	public void testSupplierOrderDetail() {
		final OrderListVO orderVO = new OrderListVO();

		orderVO.setOrder_id("1093317042600010");
		orderVO.setPage_size(10);
		orderVO.setVisit_start_time(new Date());
		orderVO.setVisit_end_time(new Date());
		final Result<QueryResult<SupplierOrderDetailResponse>> resp = orderQueryService.querySupplierOrderDetailByPlatform("MF1287590524", null);
		Assert.assertNotNull(resp);
		System.out.println(JSONConverter.toJson(resp));
	}

	@Test
	public void testGetOrderStrategy() {

		final Result<ArrayList<OrderStrategyResponse>> resp = orderQueryService.getOrderStrategy("MF116754593", "", null);
		Assert.assertNotNull(resp);
		System.out.println(JSONConverter.toJson(resp));
	}

	@Test
	public void testQueryOrderDetail2() {
		final OrderDetailVO orderDetailVO = new OrderDetailVO();
		orderDetailVO.setOrder_id("CSOMF936194874");
		orderDetailVO.setCall_port(2);
		final Result<OrderDetailResponse> resp = orderQueryService.queryOrderDetail(orderDetailVO, null);
		Assert.assertNotNull(resp);
		System.out.println(JSONConverter.toJson(resp));
	}

	@Test
	public void testqueryReportOrders() {
		final ReportOrderVO reportOrderVO = new ReportOrderVO();
		reportOrderVO.setIs_root(0);
		reportOrderVO.setResellerId(2216619741564110L);
		final List<ReportOrderResponse> l = orderQueryService.queryReportOrders(reportOrderVO, null);
		for (final ReportOrderResponse r : l) {
			System.out.println(ToStringBuilder.reflectionToString(r));
		}
		System.out.println(JSONConverter.toJson(reportOrderVO));
		System.out.println(JSONConverter.toJson(l));
	}

	@Test
	public void testqueryAgentOrders() {
		final Result<AgentOrderResponse> l = orderQueryService.queryAgentOrderByOrderId("MF1699850708", null);
		System.out.println(l);
	}

	@Test
	public void queryMerchStrategysByMerchId() {
		final List<String> list = new ArrayList<String>();
		list.add("P18145688");
		list.add("P595505068");
		final Map<String, Map<Long, Long>> l = orderQueryService.queryMerchStrategysByMerchId(list, null);
		System.out.println(JSONConverter.toJson(list));
		System.out.println(JSONConverter.toJson(l));
	}

	@Test
	public void testOrderListByApp() throws ParseException {
		final AppOrdersReqModel orderVO = new AppOrdersReqModel();
		//		orderVO.setReseller_id(2216619736563739l);
		orderVO.setOrderIds(new ArrayList<String>());
		orderVO.getOrderIds().add("1073417042500013");
		//		orderVO.getOrderIds().add("1073417041410002");
		//		orderVO.getOrderIds().add("1073417041410003");
		//		orderVO.getOrderIds().add("1073417041410004");
		//		orderVO.getOrderIds().add("1073417041410005");
		orderVO.setSupName("招财猫");
		final Result<QueryResult<AppOrdersRespModel>> resp = orderQueryService.queryOrdersByApp(orderVO, null);

		System.out.println("===>" + resp);
		Assert.assertNotNull(resp);
		System.out.println("===>" + JSONConverter.toJson(resp.getData()));
	}

	@Test
	public void testOrderListByAppRebate() throws ParseException {
		final AppRebateOrdersReqModel orderVO = new AppRebateOrdersReqModel();
		//		orderVO.setResellerId(2216619736763787l);
		//		Date date1 = new Date(1490976000000l);
		//		Date date2 = new Date(1493481600000l);
		//		orderVO.setCreateStartTime(date1);
		//		orderVO.setCreateEndTime(date2);
		//		orderVO.setOrderStatus(30);
		//		orderVO.setRebateFormtype(0);
		//		orderVO.setCurrentPage(1);
		//		orderVO.setPageSize(20);
		//		orderVO.setOrderId("1021517042500062");
		orderVO.setResellerId(2216619736763787l);
		//		orderVO.setOrderStatusList(new ArrayList<Integer>());
		//		orderVO.getOrderStatusList().add(10);
		//		orderVO.getOrderStatusList().add(40);
		orderVO.setSupName("saas");
		orderVO.setOrderId("1021517042500055");
		//		1021517042500055

		final Result<AppRebateOrdersRespModel> resp = orderQueryService.queryOrdersByAppRebate(orderVO, null);
		System.out.println("===>" + resp);
		Assert.assertNotNull(resp);
		System.out.println("===>" + JSONConverter.toJson(resp.getData()));
	}

	@Test
	public void testQueryOrdersBySaaSSupplier() throws ParseException {
		final PlatformOrderListVO orderVO = new PlatformOrderListVO();

		orderVO.setQuery_type("supplierQuery"); //需要特殊处理
		//		/**
		//		 * 查询类型:
		//		 * 
		//		 * supplierQuery订单查询列表（供应）
		//		 * supplierQueryDownLine订单列表查询(供应线下)
		//		 * supplierQueryOnLine订单列表查询(供应线上)
		//		 * suppplierQueryRefund退款订单列表查询(供应)
		//		 * suppplierQueryIsForceRefund强制退款申请订单列表
		//		 * suppplierQueryConfirm手动确认订单列表查询(供应)
		//		 * suppplierQueryCheck批量核销_检票订单列表（供应）
		//		 * */
		//		private String query_type;

		//		orderVO.setProduct_ids(new ArrayList<Long>());
		//		orderVO.getProduct_ids().add(854886276152135680l);
		//		orderVO.getProduct_ids().add(854886166978596864l);
		//		orderVO.getProduct_ids().add(854886276152135681l);
		//		orderVO.setTransactionId("MF1246906706");
		orderVO.setSupplier_id(2216619736763722l);
		//		orderVO.setMerch_state(0);
		orderVO.setIsForceOrderList(false);
		orderVO.setTransactionId("1072217050300003");
		//		orderVO.setQr_code("536365");
		orderVO.setVisit_start_time(new Date());
		orderVO.setVisit_end_time(new Date());
		//		orderVO.setIsDirectSale(1);
		final Result<QueryResult<OrderListResponse>> resp = orderQueryService.queryOrdersBySaaSSupplier(orderVO, null);
		System.out.println("===>" + resp);
		Assert.assertNotNull(resp);
		System.out.println("===>" + JSONConverter.toJson(resp.getData()));
	}

	@Test
	public void testQueryRefundAuditOrdersBySupllier() throws ParseException {
		final PlatformRefundOrderListVO orderVO = new PlatformRefundOrderListVO();
		//		orderVO.setOrder_id("1072217042110012");
		//		orderVO.setQuery_type("supplierQueryRefundResult");
		orderVO.setQuery_type("supplierQueryRefundAudit");
		orderVO.setTransactionId("1072217042410000");
		orderVO.setSupplier_id(2216619736763722l);
		final Result<QueryResult<OrderListResponse>> resp = orderQueryService.queryRefundAuditOrdersBySupllier(orderVO, null);
		System.out.println("===>" + resp);
		Assert.assertNotNull(resp);
		System.out.println("===>" + JSONConverter.toJson(resp.getData()));
	}

	@Test
	public void testQueryOrderDetailBySaaSSupplier() throws ParseException {
		final SupplierOrderDetailReqModel orderVO = new SupplierOrderDetailReqModel();

		orderVO.setOrder_id("1092117050300011");

		orderVO.setOrder_id("1032117041910001");

		orderVO.setOrder_id("1032117041910001");

		//		orderVO.setTransactionId("1072217050200020");
		//		orderVO.setSupplier_id(400);
		//		orderVO.setQuery_type("supplierQueryOnLine");
		final Result<SupplierOrderDetailResponse> resp = orderQueryService.queryOrderDetailBySaaSSupplier(orderVO, null);
		System.out.println("===>" + resp);
		Assert.assertNotNull(resp);
		System.out.println("===>" + JSONConverter.toJson(resp.getData()));
	}

	@Test
	public void testOrderMerchConfirms() throws ParseException {
		final OrderMerchConfirmsReqModel orderVO = new OrderMerchConfirmsReqModel();
		orderVO.setProduct_id(857076596059901957l);
		orderVO.setVoucherId(1476150884516l);
		final Result<ArrayList<OrderMerchConfirmsRespModel>> resp = orderQueryService.getOrderMerchConfirms(orderVO, null);
		System.out.println("===>" + resp);
		Assert.assertNotNull(resp);
		System.out.println("===>" + JSONConverter.toJson(resp.getData()));
	}

	@Test
	public void testQueryOrdersBySaaSReseller() throws ParseException {
		final ResellerOrdersReqModel orderVO = new ResellerOrdersReqModel();
		//		orderVO.setQueryType("resellerQueryDifferencePayReslut");
		/**
		 * 查询类型:
		 * 
		 *resellerQueryPayReslut 需要付款订单（分销）
		 *resellerQueryDifferencePayReslut 需要补差订单（分销）
		 * */
		//		private String queryType;
		orderVO.setPageSize(20);
		orderVO.setCurrentPage(1);

		orderVO.setResellerId(3908750088273921l);
		//		orderVO.setProductIds(new ArrayList<Long>());
		//		orderVO.getProductIds().add(854886276152135680l);
		//		orderVO.getProductIds().add(854886166978596864l);
		//		orderVO.getProductIds().add(854886276152135681l);
		//		orderVO.setResellerIds(new ArrayList<Long>());
		//		orderVO.getResellerIds().add(798043992343867393l);
		//		orderVO.setpSupplierId(3908660833484801l);
		orderVO.setpSupplierIds(new ArrayList<Long>());
		orderVO.getpSupplierIds().add(3908660833484801l);
		orderVO.setTransactionId("1092117042700006");

		final Result<ResellerOrdersRespModel> resp = orderQueryService.queryOrdersBySaaSReseller(orderVO, null);
		System.out.println("===>" + resp);
		Assert.assertNotNull(resp);
		System.out.println("===>" + JSONConverter.toJson(resp.getData()));
	}

	@Test
	public void testQueryOrderDetailByReseller() throws ParseException {
		final ResellerOrderDetailReqModel orderVO = new ResellerOrderDetailReqModel();
		orderVO.setOrder_id("1046517042600022");
		orderVO.setReseller_id(3908750088273921l);

		final Result<ResellerOrderDetailRespModel> resp = orderQueryService.queryOrderDetailByReseller(orderVO, null);
		System.out.println("===>" + resp);
		Assert.assertNotNull(resp);
		System.out.println("===>" + JSONConverter.toJson(resp.getData()));
	}

	//
	//	@Test
	//	public void testQueryOrderStatistics() throws ParseException {
	//		final OrderStatisticsReqModel orderVO = new OrderStatisticsReqModel();
	//		//		orderVO.setResellerId(2216619736563743l);
	//		final Result<OrderStatisticsRespModel> resp = orderQueryService.queryOrderStatistics(orderVO, null);
	//		System.out.println("===>" + resp);
	//		Assert.assertNotNull(resp);
	//		System.out.println("===>" + JSONConverter.toJson(resp.getData()));
	//	}

	@Test
	public void testQueryOrdersDetailBySettlement() throws ParseException {
		final SettlementOrdersReqModel orderVO = new SettlementOrdersReqModel();
		//		orderVO.setPayStartTime(new Date());
		//		orderVO.setPayEndTime(new Date());
		//		orderVO.setTransactionId("1092117042800012");
		//		orderVO.setTransactionId("1072217050200019");

		orderVO.setCurrentPage(2);
		orderVO.setPageSize(10);

		final Result<SettlementOrdersRespModel> resp = orderQueryService.queryOrdersDetailBySettlement(orderVO, null);

		System.out.println("===>" + resp);
		Assert.assertNotNull(resp);
		System.out.println("===>" + JSONConverter.toJson(resp.getData()));
	}

	//	@Test
	//	public void testQueryOrdersForAccount() throws ParseException {
	//		final AccountOrdersReqModel orderVO = new AccountOrdersReqModel();
	//		//		orderVO.setSupplierId(2216619741564215l);
	//		orderVO.setTransactionIds(new ArrayList<String>());
	//		//		orderVO.getTransactionIds().add("1092117050200006");
	//		orderVO.getTransactionIds().add("1072217050200019");
	//
	//
	//		final Result<QueryResult<AccountOrdersRespModel>> resp = orderQueryService.queryOrdersForAccount(orderVO, null);
	//
	//		System.out.println("===>" + resp);
	//		Assert.assertNotNull(resp);
	//		System.out.println("===>" + JSONConverter.toJson(resp.getData()));
	//	}

	@Test
	public void testQueryOrdersForAccount() throws ParseException {
		final AccountOrdersReqModel orderVO = new AccountOrdersReqModel();
		//		orderVO.setSupplierId(2216619741564215l);
		orderVO.setTransactionIds(new ArrayList<String>());
		//		orderVO.getTransactionIds().add("1092117050200006");
		orderVO.getTransactionIds().add("1072217050200019");
		//		orderVO.getTransactionIds().add("1021517042010001");
		//		orderVO.getTransactionIds().add("1021517042010002");
		//		orderVO.getTransactionIds().add("1021517042010003");
		//		orderVO.getTransactionIds().add("1021517042010004");
		//		orderVO.setOrderId(new ArrayList<String>());
		//		orderVO.getOrderId().add("MF1004659171");
		//		orderVO.getOrderId().add("MF1004799470");
		//		orderVO.getOrderId().add("MF1004837384");
		//		orderVO.getOrderId().add("MF1004868125");
		//		orderVO.getOrderId().add("MF1004945693");
		//		orderVO.getOrderId().add("MF1004982946");
		//		orderVO.getOrderId().add("MF1005021396");
		//		orderVO.getOrderId().add("MF1005158720");
		//		orderVO.getOrderId().add("MF1005193219");
		//		orderVO.getOrderId().add("MF1005278938");
		//		orderVO.getOrderId().add("MF1005228839");
		//		orderVO.getOrderId().add("MF1005324669");

		//
		//		final Result<QueryResult<AccountOrdersRespModel>> resp = orderQueryService.queryOrdersForAccount(orderVO, null);
		//
		//		System.out.println("===>" + resp);
		//		Assert.assertNotNull(resp);
		//		System.out.println("===>" + JSONConverter.toJson(resp.getData()));

		//		final Result<QueryResult<AccountOrdersRespModel>> resp = orderQueryService.queryOrdersForAccount(orderVO, null);
		//
		//		System.out.println("===>" + resp);
		//		Assert.assertNotNull(resp);
		//		System.out.println("===>" + JSONConverter.toJson(resp.getData()));

	}
	@Test
	public void testQueryRemarkByOrder() throws ParseException {
		final OrderRemarksPageReqModel orderVO = new OrderRemarksPageReqModel();
		orderVO.setOrderId(1052917041810005L);
		orderVO.setPageNum(2);
		Result<QueryResult<RemarkResponse>> result=orderQueryService.queryRemarkByOrder(orderVO, null);
		System.out.println(JSONConverter.toJson(result));
	}

}
