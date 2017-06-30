package com.pzj.core.trade.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.cglib.beans.BeanCopier;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.pzj.core.trade.agent.engine.AgentOrderEngine;
import com.pzj.core.trade.exception.TradeException;
import com.pzj.core.trade.order.engine.AppOrderQueryEngine;
import com.pzj.core.trade.order.engine.AppRebateOrderQueryEngine;
import com.pzj.core.trade.order.engine.DeliveryEngine;
import com.pzj.core.trade.order.engine.OrderExtendAttrEngine;
import com.pzj.core.trade.order.engine.OrderMerchConfirmQueryEngine;
import com.pzj.core.trade.order.engine.OrderMerchDetailEngine;
import com.pzj.core.trade.order.engine.OrderQueryDetailEngine;
import com.pzj.core.trade.order.engine.OrderQuerysOnPlatformEngine;
import com.pzj.core.trade.order.engine.PrintTicketQueryEngine;
import com.pzj.core.trade.order.engine.ResellerOrderDetailEngine;
import com.pzj.core.trade.order.engine.ResellerOrderQueryEngine;
import com.pzj.core.trade.order.engine.SupplierOrderDetailEngine;
import com.pzj.core.trade.order.engine.SupplierOrderQueryEngine;
import com.pzj.core.trade.order.engine.SupplierRefundOrderQueryEngine;
import com.pzj.core.trade.order.engine.TicketSellerOrderQueryEngine;
import com.pzj.core.trade.order.engine.converter.OrderListResponseConverter;
import com.pzj.core.trade.order.engine.converter.PlatformOrdersQueryArgsConverter;
import com.pzj.core.trade.order.engine.converter.SupplierOrderQueryArgsConverter;
import com.pzj.core.trade.order.engine.event.RemarkQueryEvent;
import com.pzj.core.trade.order.engine.event.VoucherQueryEvent;
import com.pzj.core.trade.order.engine.exception.OrderException;
import com.pzj.core.trade.query.entity.AppOrdersOrdersQueryModel;
import com.pzj.core.trade.query.entity.AppRebateOrdersOrdersQueryModel;
import com.pzj.core.trade.query.entity.ResellerOrdersQueryModel;
import com.pzj.core.trade.query.entity.SupplierOrdersQueryModel;
import com.pzj.core.trade.query.entity.TicketSellerOrdersQueryModel;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.read.RefundApplyReadMapper;
import com.pzj.core.trade.refund.read.RefundFlowReadMapper;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.entity.QueryResult;
import com.pzj.framework.exception.ServiceException;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.confirm.read.ConfirmCodeReadMapper;
import com.pzj.trade.merch.entity.MerchCleanRelationEntity;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.merch.read.MerchCleanReadMapper;
import com.pzj.trade.order.entity.AgentOrderResponse;
import com.pzj.trade.order.entity.MerchCleanRelationResponse;
import com.pzj.trade.order.entity.MerchResponse;
import com.pzj.trade.order.entity.OrderDetailResponse;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.entity.OrderListParameter;
import com.pzj.trade.order.entity.OrderListResponse;
import com.pzj.trade.order.entity.OrderResponse;
import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.entity.OrderStrategyResponse;
import com.pzj.trade.order.entity.RefundFlowResponse;
import com.pzj.trade.order.entity.RemarkResponse;
import com.pzj.trade.order.entity.ReportOrderEntity;
import com.pzj.trade.order.entity.ReportOrderResponse;
import com.pzj.trade.order.entity.SalesOrderResponse;
import com.pzj.trade.order.entity.SupplierOrderDetailResponse;
import com.pzj.trade.order.entity.SupplierOrderEntity;
import com.pzj.trade.order.model.AppOrdersReqModel;
import com.pzj.trade.order.model.AppOrdersRespModel;
import com.pzj.trade.order.model.AppRebateOrdersReqModel;
import com.pzj.trade.order.model.AppRebateOrdersRespModel;
import com.pzj.trade.order.model.OperatorOrdersReqModel;
import com.pzj.trade.order.model.OrderMerchConfirmsReqModel;
import com.pzj.trade.order.model.OrderMerchConfirmsRespModel;
import com.pzj.trade.order.model.OrderRemarksPageReqModel;
import com.pzj.trade.order.model.PrintOrderDetailReqModel;
import com.pzj.trade.order.model.PrintOrderDetailRespModel;
import com.pzj.trade.order.model.ResellerOrderDetailReqModel;
import com.pzj.trade.order.model.ResellerOrderDetailRespModel;
import com.pzj.trade.order.model.ResellerOrdersReqModel;
import com.pzj.trade.order.model.ResellerOrdersRespModel;
import com.pzj.trade.order.model.SettlementOrdersReqModel;
import com.pzj.trade.order.model.SettlementOrdersRespModel;
import com.pzj.trade.order.model.SupplierOrderDetailReqModel;
import com.pzj.trade.order.model.SupplierOrdersReqModel;
import com.pzj.trade.order.model.SupplierOrdersRespModel;
import com.pzj.trade.order.model.TicketSellerOrderDetailReqModel;
import com.pzj.trade.order.model.TicketSellerOrdersRespModel;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.OrderReadMapper;
import com.pzj.trade.order.read.OrderStrategyReadMapper;
import com.pzj.trade.order.read.RefundOrderReadForPlatformMapper;
import com.pzj.trade.order.service.OrderQueryService;
import com.pzj.trade.order.vo.OrderDetailVO;
import com.pzj.trade.order.vo.OrderListVO;
import com.pzj.trade.order.vo.PlatformOrderListVO;
import com.pzj.trade.order.vo.PlatformRefundOrderListVO;
import com.pzj.trade.order.vo.ReportOrderVO;
import com.pzj.trade.order.vo.SalesOrderVO;
import com.pzj.voucher.service.VoucherService;

/**
 * 订单查询服务实现.
 * <ul>
 * 功能如下:
 * <li>订单列表查询.</li>
 * <li>订单详情查询.</li>
 * </ul>
 *
 * @author YRJ
 *
 */
@Service(value = "orderQueryService")
public class OrderQueryServiceImpl implements OrderQueryService {
	private final static Logger logger = LoggerFactory.getLogger(OrderQueryServiceImpl.class);

	@Resource(name = "orderReadMapper")
	private OrderReadMapper orderReadMapper;

	@Resource(name = "supplierOrdersArgsValidator")
	private ObjectConverter<SupplierOrdersReqModel, Void, Result<Boolean>> supplierOrdersArgsValidator;
	@Resource(name = "resellerOrderListArgsConverter")
	private ObjectConverter<ResellerOrdersReqModel, Void, ResellerOrdersQueryModel> resellerOrderListArgsConverter;
	@Autowired
	private SupplierRefundOrderQueryEngine supplierRefundOrderQueryEngine;
	@Autowired
	private SupplierOrderQueryEngine supplierOrderQueryEngine;

	@Autowired
	private ResellerOrderQueryEngine resellerOrderQueryEngine;

	@Resource(name = "merchReadMapper")
	private MerchReadMapper merchReadMapper;

	@Resource(name = "refundFlowReadMapper")
	private RefundFlowReadMapper refundFlowReadMapper;

	@Resource(name = "merchCleanReadMapper")
	private MerchCleanReadMapper merchCleanReadMapper;

	@Resource(name = "refundApplyReadMapper")
	private RefundApplyReadMapper refundApplyReadMapper;

	@Resource(name = "salesOrderResponseConverter")
	private ObjectConverter<OrderEntity, List<MerchEntity>, SalesOrderResponse> salesOrderResponseConverter;

	@Resource(name = "orderStrategyReadMapper")
	private OrderStrategyReadMapper orderStrategyReadMapper;

	@Resource(name = "orderListResponseConverter")
	private OrderListResponseConverter orderListResponseConverter;

	@Resource(name = "voucherService")
	private VoucherService voucherService;

	@Resource(name = "voucherQueryEvent")
	private VoucherQueryEvent voucherQueryEvent;

	@Resource(name = "confirmCodeReadMapper")
	private ConfirmCodeReadMapper confirmCodeReadMapper;

	/** 填单项 */
	@Autowired
	private OrderExtendAttrEngine orderExtendAttrEngine;
	/** 填单项 */
	@Autowired
	private OrderQuerysOnPlatformEngine orderQuerysOnPlatformEngine;

	@Autowired
	private DeliveryEngine deliveryEngine;

	@Autowired
	private AgentOrderEngine agentOrderEngine;

	@Resource(name = "refundOrderReadForPlatformMapper")
	private RefundOrderReadForPlatformMapper refundOrderReadForPlatformMapper;

	@Resource(name = "remarkQueryEvent")
	private RemarkQueryEvent remarkQueryEvent;

	@Override
	public Result<SupplierOrdersRespModel> queryOrdersBySupplier(final SupplierOrdersReqModel reqModel, final ServiceContext context) {
		final Result<Boolean> legal = supplierOrdersArgsValidator.convert(reqModel, null);
		if (!(legal.isOk())) {
			logger.warn("供应商订单列表查询失败. reqModel: " + JSONConverter.toJson(reqModel));
			return new Result<SupplierOrdersRespModel>(legal.getErrorCode(), legal.getErrorMsg());
		}
		SupplierOrdersRespModel respModel = null;
		try {
			respModel = supplierOrderQueryEngine.queryOrders(reqModel);
			logger.debug("供应商订单列表结果-->" + JSONConverter.toJson(respModel));
		} catch (final Throwable e) {
			logger.error("供应商订单列表查询失败. reqModel: " + JSONConverter.toJson(reqModel), e);

			if (e instanceof TradeException) {
				final TradeException ex = (TradeException) e;
				return new Result<SupplierOrdersRespModel>(ex.getErrCode(), ex.getMessage());
			}

			return new Result<SupplierOrdersRespModel>(10500, "供应商订单列表查询失败.");
		}
		return /* result */new Result<SupplierOrdersRespModel>(respModel);
	}

	@Override
	public Result<QueryResult<OrderListResponse>> queryOrdersByReseller(final OrderListVO orderVO, final ServiceContext context) {
		if (orderVO == null) {
			return new Result<QueryResult<OrderListResponse>>(10601, "查询参数为空, 无法获取订单列表信息");
		}
		if (logger.isInfoEnabled()) {
			logger.info("分销端查询订单参数：" + JSON.toJSONString(orderVO));
		}
		QueryResult<OrderListResponse> orders = null;

		try {
			orders = resellerOrderQueryEngine.queryOrdersByReseller(orderVO);
		} catch (final Throwable e) {
			logger.error("分销端查询订单列表, 失败. reqModel: " + JSONConverter.toJson(orderVO), e);

			if (e instanceof TradeException) {
				final TradeException ex = (TradeException) e;
				return new Result<QueryResult<OrderListResponse>>(ex.getErrCode(), ex.getMessage());
			}
			return new Result<QueryResult<OrderListResponse>>(10500, "分销端查询订单列表查询失败.");
		}

		return new Result<QueryResult<OrderListResponse>>(orders);

	}

	@Override
	public Result<ResellerOrdersRespModel> queryOrdersBySaaSReseller(final ResellerOrdersReqModel orderVO, final ServiceContext context) {

		final ResellerOrdersQueryModel resellerOrdersQueryModel = resellerOrderListArgsConverter.convert(orderVO, null);

		if (resellerOrdersQueryModel == null) {
			return new Result<ResellerOrdersRespModel>(10601, "请指定合法的查询条件");
		}
		logger.info("分销端订单列表查询参数为. reqModel: " + JSONConverter.toJson(orderVO));
		ResellerOrdersRespModel qr = null;
		try {
			qr = resellerOrderQueryEngine.querySaaSOrders(resellerOrdersQueryModel, orderVO);
			logger.debug("分销端订单列表结果-->" + JSONConverter.toJson(qr));
		} catch (final Throwable e) {
			logger.error("分销端订单列表查询失败. reqModel: " + JSONConverter.toJson(orderVO), e);
			if (e instanceof TradeException) {
				final TradeException ex = (TradeException) e;
				return new Result<ResellerOrdersRespModel>(ex.getErrCode(), ex.getMessage());
			}
			return new Result<ResellerOrdersRespModel>(10500, "分销端订单列表查询失败.");
		}

		if (logger.isInfoEnabled()) {
			logger.info("分销端订单列表查询结果. reqModel: " + (orderVO) + ", respModel: " + (JSONConverter.toJson(qr)));
		}
		return new Result<ResellerOrdersRespModel>(qr);
	}

	@Resource(name = "platformOrdersQueryArgsConverter")
	private PlatformOrdersQueryArgsConverter platformOrdersQueryArgsConverter;

	@Override
	public Result<QueryResult<OrderListResponse>> queryOrdersOnPlatform(final PlatformOrderListVO orderVO, final ServiceContext context) {
		final OrderListParameter queryModel = platformOrdersQueryArgsConverter.convert(orderVO, null);
		if (orderVO == null) {
			return new Result<QueryResult<OrderListResponse>>(10601, "请指定合法的查询条件");
		}
		if (logger.isInfoEnabled()) {
			logger.info("支撑平台订单列表. reqModel: " + (orderVO));
		}
		QueryResult<OrderListResponse> orders = null;
		try {
			orders = orderQuerysOnPlatformEngine.queryOrdersOnPlatform(queryModel, orderVO);
		} catch (final Throwable e) {
			logger.error("支撑平台订单列表, 失败. reqModel: " + JSONConverter.toJson(orderVO), e);

			if (e instanceof TradeException) {
				final TradeException ex = (TradeException) e;
				return new Result<QueryResult<OrderListResponse>>(ex.getErrCode(), ex.getMessage());
			}
			return new Result<QueryResult<OrderListResponse>>(10500, "支撑平台订单列表查询失败.");
		}

		return new Result<QueryResult<OrderListResponse>>(orders);

	}

	@Resource(name = "orderQueryDetailEngine")
	private OrderQueryDetailEngine orderQueryDetailEngine;

	@Override
	public Result<OrderDetailResponse> queryOrderDetail(final OrderDetailVO orderDetailVO, final ServiceContext context) {
		// 1. 判断参数的合法性.
		if (Check.NuNObject(orderDetailVO) || Check.NuNObject(orderDetailVO.getOrder_id(), orderDetailVO.getCall_port())) {
			logger.warn("Illegal parameter: " + JSON.toJSONString(orderDetailVO));
			return new Result<OrderDetailResponse>(14001, "请求参数为空, 无法获取到通用订单详细信息");
		}
		OrderDetailResponse orderDetailResponse = null;
		try {
			orderDetailResponse = orderQueryDetailEngine.queryOrderDetail(orderDetailVO);
		} catch (final Throwable e) {
			if (!(e instanceof OrderException)) {
				throw new ServiceException("查询通用订单详情失败", e);
			}
			final OrderException ex = (OrderException) e;
			return new Result<OrderDetailResponse>(ex.getErrCode(), ex.getMessage());
		}
		if (orderDetailResponse != null) {
			return new Result<OrderDetailResponse>(orderDetailResponse);
		}
		return new Result<OrderDetailResponse>(10401, "查询订单详情为空");
	}

	/**
	 * 组装退款信息
	 *
	 * @param merch
	 * @param RefundFlowEntityList
	 */
	private MerchResponse assembleRefundInfo(final MerchResponse merch, final List<RefundFlowEntity> refundFlowEntityList) {
		merch.setRefundInfos(new ArrayList<RefundFlowResponse>());
		for (final RefundFlowEntity refundEntity : refundFlowEntityList) {
			final RefundFlowResponse refundResponse = new RefundFlowResponse();
			refundResponse.setRefund_id(refundEntity.getRefund_id());
			refundResponse.setOrder_id(refundEntity.getOrder_id());
			refundResponse.setMerchandise_id(refundEntity.getMerch_id());
			refundResponse.setRefund_num(refundEntity.getRefund_num());
			final RefundApplyEntity refundApplyEntity = refundApplyReadMapper.queryRefundApplyByRefundIdByFlow(refundEntity.getRefund_id());
			if (refundApplyEntity != null) {
				refundResponse.setRefund_state(refundApplyEntity.getRefundState());
				refundResponse.setRefundAuditState(refundApplyEntity.getRefundAuditState());
			}
			refundResponse.setRefund_price(refundEntity.getRefund_price());
			refundResponse.setRefund_type(refundEntity.getRefund_type());
			refundResponse.setAmount(refundEntity.getRefund_num() * refundEntity.getRefund_price());
			refundResponse.setRefundRuleType(refundEntity.getRefund_rule_type());
			refundResponse.setCreate_time(refundEntity.getCreate_time());
			merch.getRefundInfos().add(refundResponse);
		}
		return merch;
	}

	@Override
	public Result<QueryResult<SupplierOrderDetailResponse>> querySupplierOrderDetailByPlatform(final String orderId, final ServiceContext context) {
		// 1. 判断参数的合法性.
		if (Check.NuNStrStrict(orderId)) {
			logger.warn("Illegal orderId: " + (orderId));
			return new Result<QueryResult<SupplierOrderDetailResponse>>();
		}
		QueryResult<SupplierOrderDetailResponse> qr = null;
		try {
			qr = orderQueryDetailEngine.querySupplierOrderDetailByPlatform(orderId);
		} catch (final Throwable e) {
			if (!(e instanceof OrderException)) {
				throw new ServiceException("querySupplierOrderDetailByPlatform查询订单详情失败", e);
			}
			final OrderException ex = (OrderException) e;
			return new Result<QueryResult<SupplierOrderDetailResponse>>(ex.getErrCode(), ex.getMessage());
		}
		if (qr != null) {
			return new Result<QueryResult<SupplierOrderDetailResponse>>(qr);
		}
		return new Result<QueryResult<SupplierOrderDetailResponse>>(10401, "querySupplierOrderDetailByPlatform查询订单详情为空");

	}

	@Override
	public Result<SalesOrderResponse> querySalesOrderDetail(final SalesOrderVO salesOrderVO, final ServiceContext context) {
		logger.info("查询销售订单详情-salesOrderVO:{}", JSON.toJSONString(salesOrderVO));
		// 判断参数
		if (Check.NuNObject(salesOrderVO)) {
			return new Result<SalesOrderResponse>(14001, "参数orderId不能为空.");
		}
		//		// 查询订单信息
		final OrderEntity orderEntity = orderReadMapper.getOrderById(salesOrderVO.getOrder_id());
		if (Check.NuNObject(orderEntity)) {
			return new Result<SalesOrderResponse>(14001, "orderId" + salesOrderVO.getOrder_id() + "没有对应的订单.");
		}
		// 查询商品信息
		List<MerchEntity> merchs = new ArrayList<MerchEntity>();
		if (Check.NuNStrStrict(salesOrderVO.getMerch_id())) {// merchId为空，按照orderId查询
			merchs = merchReadMapper.getMerchByOrderId(salesOrderVO.getOrder_id());
		} else {// merchId非空
			final MerchEntity merch = merchReadMapper.getMerchByMerchId(salesOrderVO.getMerch_id());
			merchs.add(merch);
		}

		final SalesOrderResponse salesOrderResponse = salesOrderResponseConverter.convert(orderEntity, merchs);

		// 查询商品的退款信息
		if (orderEntity.getRefund_num() > 0) {
			for (final MerchResponse merchResponse : salesOrderResponse.getMerchs()) {
				final List<RefundFlowEntity> refundFlowEntityList = refundFlowReadMapper.getRefundFlows(orderEntity.getOrder_id(), merchResponse.getMerchId(),
						salesOrderVO.getOrder_type());
				assembleRefundInfo(merchResponse, refundFlowEntityList);

			}
		}
		for (final MerchResponse merchResponse : salesOrderResponse.getMerchs()) {

			final List<MerchCleanRelationEntity> merchcleanEntitys = merchCleanReadMapper.queryCleanRelationsByOrderIdAndMerchId(merchResponse.getOrderId(),
					merchResponse.getMerchId());
			assembleMerchClean(merchResponse, merchcleanEntitys);

		}

		// 设置原始供应商原始分销商查询
		if (orderEntity.getOrder_id().equals(orderEntity.getP_order_id())) {// 传来的是父订单
			salesOrderResponse.setOriginResellerId(orderEntity.getReseller_id());
			salesOrderResponse.setOriginSupplierId(orderEntity.getSupplier_id());
			final List<SupplierOrderEntity> supplierOrderEntityList = orderReadMapper.getSupplierOrdersByResellerOrderId(orderEntity.getOrder_id());
			if (!Check.NuNCollections(supplierOrderEntityList)) {
				salesOrderResponse.setOriginSupplierId(supplierOrderEntityList.get(0).getSupplier_id());
			}
		} else {// 传来的是子订单
				// 查询父订单信息
			final OrderEntity pOrderEntity = orderReadMapper.getOrderById(orderEntity.getP_order_id());
			salesOrderResponse.setOriginSupplierId(orderEntity.getSupplier_id());
			salesOrderResponse.setOriginResellerId(pOrderEntity.getReseller_id());
		}

		return new Result<SalesOrderResponse>(salesOrderResponse);

	}

	@Override
	public Result<ArrayList<OrderStrategyResponse>> getOrderStrategy(final String orderId, final String merchId, final ServiceContext context) {
		logger.debug("查询订单政策-orderId:{}，merchId：{}", orderId, merchId);
		// 判断参数
		if (Check.NuNStrStrict(orderId)) {
			return new Result<ArrayList<OrderStrategyResponse>>(14001, "参数orderId不能为空.");
		}

		final ArrayList<OrderStrategyResponse> list = new ArrayList<OrderStrategyResponse>();
		final List<OrderStrategyEntity> orderStrategyEntity = orderStrategyReadMapper.getOrderStrategys(orderId, merchId);
		if (Check.NuNCollections(orderStrategyEntity)) {
			return new Result<ArrayList<OrderStrategyResponse>>(list);
		}
		for (final OrderStrategyEntity item : orderStrategyEntity) {
			final OrderStrategyResponse orderStrategyResponse = new OrderStrategyResponse();
			BeanCopier.create(OrderStrategyEntity.class, OrderStrategyResponse.class, false).copy(item, orderStrategyResponse, null);
			orderStrategyResponse.setRemark(item.getDiscountRemark());
			list.add(orderStrategyResponse);
		}

		return new Result<ArrayList<OrderStrategyResponse>>(list);
	}

	@Override
	public List<ReportOrderResponse> queryReportOrders(final ReportOrderVO reportOrderVO, final ServiceContext context) {
		logger.debug("订单报表查询参数：" + ToStringBuilder.reflectionToString(reportOrderVO, ToStringStyle.SHORT_PREFIX_STYLE));
		// 验证参数
		if (reportOrderVO == null) {
			return null;
		}
		final List<ReportOrderEntity> reportOrderEntityList = orderReadMapper.getReportOrder(reportOrderVO.getStart_date(), reportOrderVO.getEnd_date(),
				reportOrderVO.getResellerId(), reportOrderVO.getMerch_state(), reportOrderVO.getIs_root());

		final List<ReportOrderResponse> list = new ArrayList<ReportOrderResponse>();
		if (reportOrderEntityList != null) {
			for (final ReportOrderEntity item : reportOrderEntityList) {
				final ReportOrderResponse reportOrderResponse = new ReportOrderResponse();
				item.setThird_amount(item.getThird_amount_pay() - item.getThird_amount_refund());
				item.setBalance_amount(item.getBalance_amount_pay() - item.getBalance_amount_refund());
				// 未收 =应收-实收-账扣
				item.setUnreceive(item.getReceivable() - item.getThird_amount() - item.getBalance_amount());
				BeanCopier.create(ReportOrderEntity.class, ReportOrderResponse.class, false).copy(item, reportOrderResponse, null);
				list.add(reportOrderResponse);
			}
		}

		return list;
	}

	@Override
	public Result<ArrayList<OrderDetailResponse>> queryOrderDetailOnPlatform(final String order_id, final ServiceContext context) {
		//		logger.debug("大平台查询订单详情参数：order_id:" + order_id);
		//		if (Check.NuNStrStrict(order_id)) {
		//			return new Result<ArrayList<OrderDetailResponse>>(14001, "请求参数为空, 无法获取到订单详细信息");
		//		}
		//		// 查询所有订单
		//		final ArrayList<OrderEntity> orderEntityList = orderReadMapper.getTransactionOrderById(order_id);
		//		if (orderEntityList == null) {
		//			return new Result<ArrayList<OrderDetailResponse>>(14001, "订单不存在");
		//		}
		//		final ArrayList<OrderDetailResponse> orderDetailResponseList = new ArrayList<OrderDetailResponse>();
		//		// 循环订单
		//		for (final OrderEntity orderEntity : orderEntityList) {
		//			// 查询商品和政策
		//			final List<MerchEntity> merchs = merchReadMapper.getMerchWithStrategyByOrderId(orderEntity.getOrder_id());
		//			// 查询备注
		//			final List<RemarkEntity> remarkEntityList = orderReadMapper.getRemarkByOrderId(orderEntity.getOrder_id());
		//			// 备注转换
		//			final List<RemarkResponse> RemarkResponseList = new ArrayList<RemarkResponse>();
		//			for (final RemarkEntity remark : remarkEntityList) {
		//				final RemarkResponse item = new RemarkResponse();
		//				BeanCopier.create(RemarkEntity.class, RemarkResponse.class, false).copy(remark, item, null);
		//				RemarkResponseList.add(item);
		//			}
		//			final OrderDetailResponse order = orderDetailResponseConverter.convert(orderEntity, merchs);
		//			if (!Check.NuNCollections(remarkEntityList)) {
		//				order.setRemarks(RemarkResponseList);
		//			}
		//			orderDetailResponseList.add(order);
		//		}
		//
		//		return new Result<ArrayList<OrderDetailResponse>>(orderDetailResponseList);
		return new Result<ArrayList<OrderDetailResponse>>(null);
	}

	@Override
	@Deprecated
	public Map<String, Map<Long, Long>> queryMerchStrategysByMerchId(final List<String> merchList, final ServiceContext context) {
		logger.debug("查询参数为：merch_id list:{}", merchList);
		final Map<String, Map<Long, Long>> map = new HashMap<String, Map<Long, Long>>();
		final List<MerchEntity> merchs = merchReadMapper.getMerchStrategysByMerchId(merchList);
		for (final MerchEntity item : merchs) {
			Map<Long, Long> childmap = map.get(item.getRoot_merch_id());
			if (childmap == null) {
				childmap = new HashMap<Long, Long>();
				childmap.put(item.getProduct_id(), item.getStrategy_id());
			} else {
				childmap.put(item.getProduct_id(), item.getStrategy_id());
			}
			map.put(item.getRoot_merch_id(), childmap);
		}
		return map;
	}

	@Override
	public Result<AgentOrderResponse> queryAgentOrderByOrderId(final String order_id, final ServiceContext context) {
		if (Check.NuNStrStrict(order_id)) {
			return new Result<AgentOrderResponse>(10401, "订单[" + order_id + "]为空, 不予查询对应的第三方订单号.");
		}

		AgentOrderResponse agent = null;
		try {
			logger.info("查询第三方订单信息, 订单[" + order_id + "].");
			agent = agentOrderEngine.queryAgentOrderByOrderId(order_id);
		} catch (final Throwable e) {
			logger.error("订单号[" + order_id + "], 查询第三方订单信息失败.", e);
		}

		logger.info("查询第三方订单信息, 订单[" + order_id + "], result: " + JSONConverter.toJson(agent));
		return new Result<AgentOrderResponse>(agent);
	}

	/**
	 * @see com.pzj.trade.order.service.OrderQueryService#queryRefundOrdersOnPlatform(com.pzj.trade.order.vo.PlatformRefundOrderListVO,
	 *      com.pzj.framework.context.ServiceContext)
	 */
	@Override
	public Result<QueryResult<OrderListResponse>> queryRefundAuditOrdersOnPlatform(final PlatformRefundOrderListVO orderVO, final ServiceContext context) {
		final OrderListParameter queryModel = platformOrdersQueryArgsConverter.convertRefundOrders(orderVO, false);
		if (orderVO == null) {
			return new Result<QueryResult<OrderListResponse>>(10601, "支撑平台查询退款审核订单列表,请指定合法的查询条件");
		}
		if (logger.isInfoEnabled()) {
			logger.info("支撑平台查询退款审核订单列表. reqModel: " + (orderVO));
		}
		QueryResult<OrderListResponse> orders = null;
		try {
			orders = orderQuerysOnPlatformEngine.queryRefundAuditOrdersOnPlatform(queryModel, orderVO);
		} catch (final Throwable e) {
			logger.error("支撑平台查询退款审核订单列表, 失败. reqModel: " + JSONConverter.toJson(orderVO), e);

			if (e instanceof TradeException) {
				final TradeException ex = (TradeException) e;
				return new Result<QueryResult<OrderListResponse>>(ex.getErrCode(), ex.getMessage());
			}
			return new Result<QueryResult<OrderListResponse>>(10500, "支撑平台查询退款审核订单列表失败.");
		}

		return new Result<QueryResult<OrderListResponse>>(orders);

	}

	/**
	 * 组装商品清算关系记录信息
	 *
	 * @param merch
	 * @param merchcleanEntitys
	 */
	private MerchResponse assembleMerchClean(final MerchResponse merch, final List<MerchCleanRelationEntity> merchcleanEntitys) {
		merch.setMerchCleanRelations(new ArrayList<MerchCleanRelationResponse>());

		for (final MerchCleanRelationEntity merchCleanRelationEntity : merchcleanEntitys) {
			final MerchCleanRelationResponse merchCleanRelationResponse = new MerchCleanRelationResponse();
			merchCleanRelationResponse.setClean_state(merchCleanRelationEntity.getClean_state());
			merchCleanRelationResponse.setIs_minus_clean(merchCleanRelationEntity.getIs_minus_clean());
			merchCleanRelationResponse.setNormal_clean_amount(merchCleanRelationEntity.getNormal_clean_amount());
			merchCleanRelationResponse.setNormal_clean_num(merchCleanRelationEntity.getNormal_clean_num());
			merchCleanRelationResponse.setOverdue_clean_amount(merchCleanRelationEntity.getOverdue_clean_amount());
			merchCleanRelationResponse.setOverdue_clean_num(merchCleanRelationEntity.getOverdue_clean_num());
			merchCleanRelationResponse.setRefund_clean_amount(merchCleanRelationEntity.getRefund_clean_amount());
			merchCleanRelationResponse.setRefund_clean_num(merchCleanRelationEntity.getRefund_clean_num());
			merchCleanRelationResponse.setClean_type(merchCleanRelationEntity.getClean_type());
			merch.getMerchCleanRelations().add(merchCleanRelationResponse);
		}
		return merch;
	}

	@Override
	public List<OrderResponse> queryOrdersByOrderId(final String order_id, final ServiceContext context) {
		//		logger.debug("根据orderId{}查询同一个交易号下的订单信息", order_id);
		//		final List<OrderEntity> orderList = orderReadMapper.getTransactionOrderById(order_id);
		//		if (Check.NuNCollections(orderList)) {
		//			return null;
		//		}
		//		final List<OrderResponse> orderResponseList = new ArrayList<OrderResponse>();
		//		for (final OrderEntity order : orderList) {
		//			final OrderResponse orderResponse = new OrderResponse();
		//			orderResponse.setOrderId(order.getOrder_id());
		//			orderResponse.setReseller_id(order.getReseller_id());
		//			orderResponse.setSupplier_id(order.getSupplier_id());
		//			orderResponseList.add(orderResponse);
		//		}
		//		return orderResponseList;
		return null;
	}

	@Override
	public Result<QueryResult<OrderListResponse>> queryForceRefundOrdersOnPlatform(final PlatformRefundOrderListVO orderVO, final ServiceContext context) {
		if (orderVO == null) {
			return new Result<QueryResult<OrderListResponse>>(10601, "支撑平台查询已强制退款订单列表,请指定合法的查询条件");
		}
		if (logger.isInfoEnabled()) {
			logger.info("支撑平台查询已强制退款订单列表. reqModel: " + (orderVO));
		}
		final OrderListParameter queryModel = platformOrdersQueryArgsConverter.convertRefundOrders(orderVO, true);
		QueryResult<OrderListResponse> orders = null;
		try {
			orders = orderQuerysOnPlatformEngine.queryForceRefundOrdersOnPlatform(queryModel, orderVO);
		} catch (final Throwable e) {
			logger.error("支撑平台查询已强制退款订单列表, 失败. reqModel: " + JSONConverter.toJson(orderVO), e);

			if (e instanceof TradeException) {
				final TradeException ex = (TradeException) e;
				return new Result<QueryResult<OrderListResponse>>(ex.getErrCode(), ex.getMessage());
			}
			return new Result<QueryResult<OrderListResponse>>(10500, "支撑平台查询已强制退款订单列表失败.");
		}

		return new Result<QueryResult<OrderListResponse>>(orders);
	}

	@Resource(name = "ticketSellerArgsValidator")
	private ObjectConverter<OperatorOrdersReqModel, Void, TicketSellerOrdersQueryModel> ticketSellerArgsValidator;

	@Resource(name = "ticketSellerOrderQueryEngine")
	private TicketSellerOrderQueryEngine ticketSellerOrderQueryEngine;

	@Override
	public Result<TicketSellerOrdersRespModel> queryOrdersByTicketSeller(final OperatorOrdersReqModel reqModel, final ServiceContext context) {
		final TicketSellerOrdersQueryModel queryModel = ticketSellerArgsValidator.convert(reqModel, null);
		if (queryModel == null) {
			return new Result<TicketSellerOrdersRespModel>(10601, "请指定合法的查询条件");
		}

		if (logger.isInfoEnabled()) {
			logger.info("订单列表-售票员. reqModel: " + (reqModel));
		}

		TicketSellerOrdersRespModel respModel = null;
		if (!Check.NuNObject(reqModel.getQrCode()) && Check.NuNCollections(queryModel.getVoucher_ids())) {
			return new Result<TicketSellerOrdersRespModel>(respModel);
		}
		try {
			respModel = ticketSellerOrderQueryEngine.queryOrders(queryModel, reqModel);
		} catch (final Throwable e) {
			logger.error("订单列表-售票员, 失败. reqModel: " + JSONConverter.toJson(reqModel), e);

			if (e instanceof TradeException) {
				final TradeException ex = (TradeException) e;
				return new Result<TicketSellerOrdersRespModel>(ex.getErrCode(), ex.getMessage());
			}
			return new Result<TicketSellerOrdersRespModel>(10500, "售票员-订单列表查询失败.");
		}

		if (logger.isInfoEnabled()) {
			logger.info("订单列表-售票员, 结果. reqModel: " + (reqModel) + ", respModel: " + (JSONConverter.toJson(respModel)));
		}

		return new Result<TicketSellerOrdersRespModel>(respModel);
	}

	@Override
	public Result<SupplierOrderDetailResponse> queryOrderDetailByTicketSeller(final TicketSellerOrderDetailReqModel reqModel, final ServiceContext context) {
		if (Check.NuNObject(reqModel)) {
			logger.warn("订单详情-售票员, 查询参数为空.");
			return new Result<SupplierOrderDetailResponse>(10601, "请指定合法的查询条件");
		}
		if (Check.NuNStrStrict(reqModel.getOrderId()) || reqModel.getOperatorId() <= 0) {
			logger.warn("订单详情-售票员, orderId: " + reqModel.getOrderId() + ", operatorId: " + reqModel.getOperatorId());
			return new Result<SupplierOrderDetailResponse>(10601, "请指定合法的条件, 订单号: [" + reqModel.getOrderId() + "], 操作者: [" + reqModel.getOperatorId() + "]");
		}

		if (logger.isInfoEnabled()) {
			logger.info("订单详情-售票员. orderId: " + (reqModel.getOrderId()));
		}

		SupplierOrderDetailResponse respModel = null;
		try {
			respModel = supplierOrderDetailEngine.queryOrderDetailByOrderId(reqModel.getOrderId(), 0, null, null);
		} catch (final Throwable e) {
			logger.error("订单详情-售票员, 失败. reqModel: " + JSONConverter.toJson(reqModel), e);

			if (e instanceof TradeException) {
				final TradeException ex = (TradeException) e;
				return new Result<SupplierOrderDetailResponse>(ex.getErrCode(), ex.getMessage());
			}
			return new Result<SupplierOrderDetailResponse>(10500, "售票员-订单详情查询失败.");
		}

		if (logger.isInfoEnabled()) {
			logger.info("订单列表-售票员, 结果. orderId: " + (reqModel.getOrderId()) + ", respModel: " + (JSONConverter.toJson(respModel)));
		}
		return new Result<SupplierOrderDetailResponse>(respModel);
	}

	@Resource(name = "printTicketQueryEngine")
	private PrintTicketQueryEngine printTicketQueryEngine;

	/** 
	 * @see com.pzj.trade.order.service.OrderQueryService#queryOrderDetailByPrint(com.pzj.trade.order.model.PrintOrderDetailReqModel, com.pzj.framework.context.ServiceContext)
	 */
	@Override
	public Result<PrintOrderDetailRespModel> queryOrderDetailByPrint(final PrintOrderDetailReqModel reqModel, final ServiceContext context) {
		if (reqModel == null || (Check.NuNObject(reqModel.getOrderId(), reqModel.getOperatorId()))) {
			logger.warn("查询打印票据参数, reqModel: " + (reqModel));
			return new Result<PrintOrderDetailRespModel>(10601, "票查询失败, 请指定正确的票编号[" + (reqModel == null ? null : reqModel.getOrderId()) + "].");
		}
		if (logger.isInfoEnabled()) {
			logger.info("查询打印票据参数, reqModel: " + (reqModel));
		}

		PrintOrderDetailRespModel respModel = null;
		try {
			respModel = printTicketQueryEngine.queryTicketDetailByOrderId(reqModel.getOrderId());
		} catch (final Throwable e) {
			if (!(e instanceof OrderException)) {
				throw new ServiceException("票据获取失败, 请稍后打印.", e);
			}

			final OrderException ex = (OrderException) e;
			return new Result<PrintOrderDetailRespModel>(ex.getErrCode(), ex.getMessage());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("查询打印票据, reqModel: " + (reqModel) + ", respModel: " + (respModel));
		}

		if (respModel != null) {
			return new Result<PrintOrderDetailRespModel>(respModel);
		}
		return new Result<PrintOrderDetailRespModel>(10401, "票据不存在, 不能打印");
	}

	@Resource(name = "resellerOrderDetailEngine")
	private ResellerOrderDetailEngine resellerOrderDetailEngine;

	/** 
	 * @see com.pzj.trade.order.service.OrderQueryService#queryOrderDetailByReseller(com.pzj.trade.order.model.ResellerOrderDetailReqModel, com.pzj.framework.context.ServiceContext)
	 */
	@Override
	public Result<ResellerOrderDetailRespModel> queryOrderDetailByReseller(final ResellerOrderDetailReqModel resellerOrderDetailReqModel,
			final ServiceContext context) {

		if (resellerOrderDetailReqModel == null) {
			logger.warn("查询分销订单详情参数, resellerOrderDetailReqModel: " + (resellerOrderDetailReqModel));
			return new Result<ResellerOrderDetailRespModel>(10601, "查询分销订单详情失败, 请填写正确的入参参数");
		} else {
			if (Check.NuNObject(resellerOrderDetailReqModel.getOrder_id()) && Check.NuNObject(resellerOrderDetailReqModel.getTransactionId())) {
				return new Result<ResellerOrderDetailRespModel>(10601, "查询分销订单详情失败, 请填写正确的入参参数,order_id或transactionId为空");
			}
			if (resellerOrderDetailReqModel.getReseller_id() <= 0 && !Check.NuNObject(resellerOrderDetailReqModel.getTransactionId())) {
				return new Result<ResellerOrderDetailRespModel>(10601, "查询分销订单详情失败, 请填写正确的入参参数,resellerId为空");
			}
		}
		logger.info("查询分销订单详情参数为. reqModel: " + JSONConverter.toJson(resellerOrderDetailReqModel));
		ResellerOrderDetailRespModel respModel = null;

		try {
			respModel = resellerOrderDetailEngine.queryOrderDetailByOrderId(resellerOrderDetailReqModel.getOrder_id(),
					resellerOrderDetailReqModel.getReseller_id(), resellerOrderDetailReqModel.getOrderLevel(), resellerOrderDetailReqModel.getTransactionId());

			logger.debug("查询分销订单详情返回值是为. reqModel: " + JSONConverter.toJson(respModel));
		} catch (final Throwable e) {
			if (!(e instanceof OrderException)) {
				throw new ServiceException("查询分销订单详情失败", e);
			}

			final OrderException ex = (OrderException) e;
			return new Result<ResellerOrderDetailRespModel>(ex.getErrCode(), ex.getMessage());
		}

		if (respModel != null) {
			return new Result<ResellerOrderDetailRespModel>(respModel);
		}

		return new Result<ResellerOrderDetailRespModel>(10401, "查询分销订单详情为空");
	}

	@Resource(name = "supplierOrderDetailEngine")
	private SupplierOrderDetailEngine supplierOrderDetailEngine;

	/** 
	 * @see com.pzj.trade.order.service.OrderQueryService#queryOrderDetailBySaaSSupplier(com.pzj.trade.order.model.SupplierOrderDetailReqModel, com.pzj.framework.context.ServiceContext)
	 */
	@Override
	public Result<SupplierOrderDetailResponse> queryOrderDetailBySaaSSupplier(final SupplierOrderDetailReqModel supplierOrderDetailReqModel,
			final ServiceContext context) {
		if (supplierOrderDetailReqModel == null) {
			logger.warn("查询供应订单详情参数, resellerOrderDetailReqModel: " + (supplierOrderDetailReqModel));
			return new Result<SupplierOrderDetailResponse>(10601, "查询供应订单详情失败, 请指定正确的订单编号["
					+ (supplierOrderDetailReqModel == null ? null : supplierOrderDetailReqModel.getOrder_id()) + "].");
		}
		SupplierOrderDetailResponse respModel = null;
		logger.info("查询供应订单详情参数为. reqModel: " + JSONConverter.toJson(supplierOrderDetailReqModel));
		try {
			respModel = supplierOrderDetailEngine.queryOrderDetailByOrderId(supplierOrderDetailReqModel.getOrder_id(),
					supplierOrderDetailReqModel.getSupplier_id(), supplierOrderDetailReqModel.getQuery_type(), supplierOrderDetailReqModel.getTransactionId());
			logger.info("查询供应订单详情结果-->" + JSONConverter.toJson(respModel));
		} catch (final Throwable e) {
			if (!(e instanceof OrderException)) {
				throw new ServiceException("查询供应订单详情失败", e);
			}

			final OrderException ex = (OrderException) e;
			return new Result<SupplierOrderDetailResponse>(ex.getErrCode(), ex.getMessage());
		}

		if (respModel != null) {
			return new Result<SupplierOrderDetailResponse>(respModel);
		}

		return new Result<SupplierOrderDetailResponse>(10401, "查询供应订单详情为空");
	}

	@Resource(name = "orderMerchConfirmQueryEngine")
	private OrderMerchConfirmQueryEngine orderMerchConfirmQueryEngine;

	@Override
	public Result<ArrayList<OrderMerchConfirmsRespModel>> getOrderMerchConfirms(final OrderMerchConfirmsReqModel orderMerchConfirmsReqModel,
			final ServiceContext context) {

		if (orderMerchConfirmsReqModel == null || (orderMerchConfirmsReqModel.getProduct_id() == 0) || (orderMerchConfirmsReqModel.getVoucherId() == 0)) {
			logger.warn("查询商品核销信息参数, resellerOrderDetailReqModel: " + (orderMerchConfirmsReqModel));
			return new Result<ArrayList<OrderMerchConfirmsRespModel>>(10601, "查询商品核销信息失败, 请指定正确的参数["
					+ (orderMerchConfirmsReqModel == null ? null : orderMerchConfirmsReqModel.getProduct_id()) + orderMerchConfirmsReqModel.getVoucherId()
					+ "].");
		}
		final ArrayList<OrderMerchConfirmsRespModel> merchConfirmsRespModels = orderMerchConfirmQueryEngine.queryMerchConfirms(
				orderMerchConfirmsReqModel.getProduct_id(), orderMerchConfirmsReqModel.getVoucherId());
		return new Result<ArrayList<OrderMerchConfirmsRespModel>>(merchConfirmsRespModels);
	}

	@Resource(name = "appRebateArgsConverter")
	private ObjectConverter<AppRebateOrdersReqModel, Void, AppRebateOrdersOrdersQueryModel> appRebateArgsConverter;

	@Resource(name = "appRebateOrderQueryEngine")
	private AppRebateOrderQueryEngine appRebateOrderQueryEngine;

	/** 
	 * @see com.pzj.trade.order.service.OrderQueryService#queryOrdersByAppRebate(com.pzj.trade.order.model.AppRebateOrdersReqModel, com.pzj.framework.context.ServiceContext)
	 */
	@Override
	public Result<AppRebateOrdersRespModel> queryOrdersByAppRebate(final AppRebateOrdersReqModel reqModel, final ServiceContext context) {

		final AppRebateOrdersOrdersQueryModel queryModel = appRebateArgsConverter.convert(reqModel, null);
		if (queryModel == null) {
			return new Result<AppRebateOrdersRespModel>(10601, "请指定合法的查询条件");
		}

		//		if (logger.isInfoEnabled()) {
		//			logger.info("app返利-订单列表查询. reqModel: " + (reqModel));
		//		}
		AppRebateOrdersRespModel respModel = null;
		try {
			respModel = appRebateOrderQueryEngine.queryOrders(queryModel, reqModel);
		} catch (final Throwable e) {
			logger.error("app返利订单列表查询, 失败. reqModel: " + JSONConverter.toJson(reqModel), e);

			if (e instanceof TradeException) {
				final TradeException ex = (TradeException) e;
				return new Result<AppRebateOrdersRespModel>(ex.getErrCode(), ex.getMessage());
			}
			return new Result<AppRebateOrdersRespModel>(10500, "app返利订单列表查询失败.");
		}

		//		if (logger.isInfoEnabled()) {
		//			logger.info("app返利订单列表查询结果. reqModel: " + (reqModel) + ", respModel: " + (JSONConverter.toJson(respModel)));
		//		}

		return new Result<AppRebateOrdersRespModel>(respModel);

	}

	@Resource(name = "appOrdersArgsConverter")
	private ObjectConverter<AppOrdersReqModel, Void, AppOrdersOrdersQueryModel> appOrdersArgsConverter;

	@Resource(name = "appOrderQueryEngine")
	private AppOrderQueryEngine appOrderQueryEngine;

	/** 
	 * @see com.pzj.trade.order.service.OrderQueryService#queryOrdersByApp(com.pzj.trade.order.model.AppOrdersReqModel, com.pzj.framework.context.ServiceContext)
	 */
	@Override
	public Result<QueryResult<AppOrdersRespModel>> queryOrdersByApp(final AppOrdersReqModel reqModel, final ServiceContext context) {

		final AppOrdersOrdersQueryModel queryModel = appOrdersArgsConverter.convert(reqModel, null);
		if (queryModel == null) {
			return new Result<QueryResult<AppOrdersRespModel>>(10601, "请指定合法的查询条件");
		}
		final QueryResult<AppOrdersRespModel> qr = new QueryResult<AppOrdersRespModel>(0, 0);
		try {
			final List<AppOrdersRespModel> orders = appOrderQueryEngine.queryOrders(queryModel);

			qr.setRecords(orders);
		} catch (final Throwable e) {
			logger.error("app可用余额明细订单列表查询, 失败. reqModel: " + JSONConverter.toJson(reqModel), e);

			if (e instanceof TradeException) {
				final TradeException ex = (TradeException) e;
				return new Result<QueryResult<AppOrdersRespModel>>(ex.getErrCode(), ex.getMessage());
			}
			return new Result<QueryResult<AppOrdersRespModel>>(10500, "app可用余额明细列表查询失败.");
		}

		return new Result<QueryResult<AppOrdersRespModel>>(qr);

	}

	@Resource(name = "supplierOrderQueryArgsConverter")
	private SupplierOrderQueryArgsConverter supplierOrderQueryArgsConverter;

	/** 
	 * @see com.pzj.trade.order.service.OrderQueryService#queryOrdersBySaaSSupplier(com.pzj.trade.order.vo.PlatformOrderListVO, com.pzj.framework.context.ServiceContext)
	 */
	@Override
	public Result<QueryResult<OrderListResponse>> queryOrdersBySaaSSupplier(final PlatformOrderListVO orderVO, final ServiceContext context) {
		final SupplierOrdersQueryModel queryModel = supplierOrderQueryArgsConverter.convert(orderVO, null);
		supplierOrderQueryArgsConverter.supplierQueryTypeConvert(orderVO, queryModel);//根据查询类型区分查询的订单列表
		if (queryModel == null) {
			return new Result<QueryResult<OrderListResponse>>(10601, "请指定合法的查询条件");
		}

		if (logger.isInfoEnabled()) {
			logger.info("SaaS供应订单列表. reqModel: " + JSONConverter.toJson(orderVO));
		}
		QueryResult<OrderListResponse> orders = null;
		try {
			orders = supplierOrderQueryEngine.querySaaSOrders(queryModel);
		} catch (final Throwable e) {
			logger.error("SaaS供应订单列表, 失败. reqModel: " + JSONConverter.toJson(orderVO), e);

			if (e instanceof TradeException) {
				final TradeException ex = (TradeException) e;
				return new Result<QueryResult<OrderListResponse>>(ex.getErrCode(), ex.getMessage());
			}
			return new Result<QueryResult<OrderListResponse>>(10500, "SaaS供应订单列表查询失败.");
		}

		if (logger.isInfoEnabled()) {
			logger.info("SaaS供应订单列表, 结果. reqModel: " + JSONConverter.toJson(orderVO) + ", respModel: " + (JSONConverter.toJson(orders)));
		}
		return new Result<QueryResult<OrderListResponse>>(orders);
	}

	/** 
	 * @see com.pzj.trade.order.service.OrderQueryService#queryRefundAuditOrdersBySupllier(com.pzj.trade.order.vo.PlatformRefundOrderListVO, com.pzj.framework.context.ServiceContext)
	 */
	@Override
	public Result<QueryResult<OrderListResponse>> queryRefundAuditOrdersBySupllier(final PlatformRefundOrderListVO orderVO, final ServiceContext context) {
		final SupplierOrdersQueryModel queryModel = supplierOrderQueryArgsConverter.convert(orderVO, null);
		supplierOrderQueryArgsConverter.supplierQueryRefundTypeConvert(orderVO, queryModel);
		if (queryModel == null) {
			return new Result<QueryResult<OrderListResponse>>(10601, "请指定合法的查询条件");
		}
		if (logger.isInfoEnabled()) {
			logger.info("SaaS供应退款订单列表. reqModel: " + JSONConverter.toJson(orderVO));
		}
		QueryResult<OrderListResponse> orders = null;
		try {
			orders = supplierRefundOrderQueryEngine.queryRefundSaaSOrders(queryModel);
		} catch (final Throwable e) {
			logger.error("SaaS供应退款订单列表, 失败. reqModel: " + JSONConverter.toJson(orderVO), e);

			if (e instanceof TradeException) {
				final TradeException ex = (TradeException) e;
				return new Result<QueryResult<OrderListResponse>>(ex.getErrCode(), ex.getMessage());
			}
			return new Result<QueryResult<OrderListResponse>>(10500, "SaaS供应退款订单列表查询失败.");
		}

		if (logger.isInfoEnabled()) {
			logger.info("SaaS供应退款订单列表, 结果. reqModel: " + JSONConverter.toJson(orderVO) + ", respModel: " + (JSONConverter.toJson(orders)));
		}
		return new Result<QueryResult<OrderListResponse>>(orders);

	}

	@Resource(name = "orderMerchDetailEngine")
	private OrderMerchDetailEngine orderMerchDetailEngine;

	@Override
	public Result<SettlementOrdersRespModel> queryOrdersDetailBySettlement(final SettlementOrdersReqModel reqModel, final ServiceContext context) {
		if (reqModel == null) {
			return new Result<SettlementOrdersRespModel>();
		}

		if (logger.isInfoEnabled()) {
			logger.info("订单商品明细表, 入參. reqModel: " + (reqModel));
		}

		SettlementOrdersRespModel respModel = null;
		try {
			respModel = orderMerchDetailEngine.queryOrders(reqModel);
		} catch (final Throwable e) {
			logger.error("订单商品明细列表, 失败. reqModel: " + JSONConverter.toJson(reqModel), e);

			if (e instanceof TradeException) {
				final TradeException ex = (TradeException) e;
				return new Result<SettlementOrdersRespModel>(ex.getErrCode(), ex.getMessage());
			}
			return new Result<SettlementOrdersRespModel>(10500, "SaaS供应订单列表查询失败.");
		}

		return new Result<SettlementOrdersRespModel>(respModel);
	}

	@Override
	public Result<QueryResult<RemarkResponse>> queryRemarkByOrder(OrderRemarksPageReqModel orderVO, ServiceContext context) {
		logger.info("订单备注明细查询, 入參. orderVO: " + JSONConverter.toJson(orderVO));
		if (Check.NuNObject(orderVO)) {
			return new Result<QueryResult<RemarkResponse>>(10501, "参数不能为空！");
		}
		if (Check.NuNObject(orderVO.getOrderId())) {
			return new Result<QueryResult<RemarkResponse>>(10502, "订单ID不能为空！");
		}
		final int totalNum = remarkQueryEvent.queryRemarkPageTotalByOrderId(orderVO.getOrderId());
		final List<RemarkResponse> remarkResponses = remarkQueryEvent.queryRemarkPageByOrderVO(orderVO);

		QueryResult<RemarkResponse> result = new QueryResult<RemarkResponse>(orderVO.getPageNum(), orderVO.getPageSize());
		result.setTotal(totalNum);
		result.setRecords(remarkResponses);
		return new Result<QueryResult<RemarkResponse>>(result);
	}

}
