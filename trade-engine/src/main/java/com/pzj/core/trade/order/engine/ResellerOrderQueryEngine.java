package com.pzj.core.trade.order.engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.converter.OrderListByResellerResponseConverter;
import com.pzj.core.trade.order.engine.converter.OrderListResponseConverter;
import com.pzj.core.trade.order.engine.converter.ResellerOrdersArgsConverter;
import com.pzj.core.trade.order.engine.event.RefundInfoEvent;
import com.pzj.core.trade.query.entity.ResellerOrdersQueryModel;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.entity.QueryResult;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchListEntity;
import com.pzj.trade.order.entity.MerchResponse;
import com.pzj.trade.order.entity.OrderCountEntity;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.entity.OrderListParameter;
import com.pzj.trade.order.entity.OrderListResponse;
import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.entity.RefundFlowResponse;
import com.pzj.trade.order.model.ResellerMerchOutModel;
import com.pzj.trade.order.model.ResellerOrdersOutModel;
import com.pzj.trade.order.model.ResellerOrdersReqModel;
import com.pzj.trade.order.model.ResellerOrdersRespModel;
import com.pzj.trade.order.read.OrderForResellerReadMapper;
import com.pzj.trade.order.read.OrderReadMapper;
import com.pzj.trade.order.read.OrderStrategyReadMapper;
import com.pzj.trade.order.vo.OrderListVO;

/**
 * 查询SaaS分销订单查询引擎.
 * @author GLG
 *
 */
@Component(value = "resellerOrderQueryEngine")
public class ResellerOrderQueryEngine {
	private final static Logger logger = LoggerFactory.getLogger(ResellerOrderQueryEngine.class);

	@Resource(name = "orderForResellerReadMapper")
	private OrderForResellerReadMapper orderForResellerReadMapper;

	@Resource(name = "orderListByResellerResponseConverter")
	private OrderListByResellerResponseConverter orderListByResellerResponseConverter;

	@Resource(name = "refundInfoEvent")
	private RefundInfoEvent refundInfoEvent;

	@Resource(name = "orderStrategyReadMapper")
	private OrderStrategyReadMapper orderStrategyReadMapper;
	@Resource(name = "orderReadMapper")
	private OrderReadMapper orderReadMapper;
	@Resource(name = "orderListResponseConverter")
	private OrderListResponseConverter orderListResponseConverter;

	@Resource(name = "resellerOrdersArgsConverter")
	private ResellerOrdersArgsConverter resellerOrdersArgsConverter;

	public ResellerOrdersRespModel querySaaSOrders(final ResellerOrdersQueryModel resellerOrdersQueryModel, final ResellerOrdersReqModel orderVO) {
		if (logger.isInfoEnabled()) {
			logger.info("订单列表查询参数：parameter:{},ordervo:{}", JSONConverter.toJson(resellerOrdersQueryModel), JSONConverter.toJson(orderVO));
		}

		final ResellerOrdersRespModel respModel = new ResellerOrdersRespModel();
		// 1. 先查询订单数量.
		final OrderCountEntity orderCountEntity = orderForResellerReadMapper.getOrderCountByCondition(resellerOrdersQueryModel);
		if (orderCountEntity.getOrder_num() == 0) {
			return respModel;
		}
		respModel.setTotalNum(orderCountEntity.getOrder_num());
		respModel.setTotalAmount(orderCountEntity.getAmount().doubleValue());
		respModel.setTotalMerchNum(orderCountEntity.getMerch_num());

		QueryResult<MerchListEntity> qr = new QueryResult<MerchListEntity>(orderVO.getCurrentPage(), orderVO.getPageSize());
		qr.setTotal(respModel.getTotalNum());
		// 2. 订单数量大于0时, 查询真实的订单信息.
		final List<MerchListEntity> merchs = orderForResellerReadMapper.getOrderByCondition(resellerOrdersQueryModel, orderVO.getPageIndex(),
				orderVO.getPageSize());
		if (merchs == null || merchs.size() == 0) {
			return respModel;
		}
		Set<String> orderIds = new HashSet<String>();
		Set<String> merchIds = new HashSet<String>();
		Set<String> transactionIds = new HashSet<String>();
		Set<String> p_order_ids = new HashSet<String>();
		Set<Long> reseller_ids = new HashSet<Long>();
		for (final MerchListEntity merch : merchs) {
			merchIds.add(merch.getMerch_id());
			orderIds.add(merch.getOrder_id());
			transactionIds.add(merch.getTransaction_id());
			p_order_ids.add(merch.getP_order_id());
			reseller_ids.add(merch.getReseller_id());
		}
		//查询所有订单商品的价格，结算价，渠道，政策
		List<OrderStrategyEntity> orderStrategyEntitys = orderStrategyReadMapper.getOrderStrategyByOrders(new ArrayList<String>(orderIds),
				new ArrayList<String>(merchIds));
		//查询所有的初始供应商信息
		List<OrderEntity> originOrderEntitys = orderForResellerReadMapper.getSupplierOrdersByTransactionIds(new ArrayList<String>(transactionIds), 1);
		//查询所有的我的分销商信息
		List<OrderEntity> childOrderEntitys = orderForResellerReadMapper.queryResellerOrders(new ArrayList<String>(p_order_ids), new ArrayList<Long>(
				reseller_ids));
		final Map<String, ResellerOrdersOutModel> orders = new LinkedHashMap<String, ResellerOrdersOutModel>();

		for (final MerchListEntity merch : merchs) {
			ResellerOrdersOutModel order = orders.get(merch.getOrder_id());
			if (order == null) {
				order = orderListByResellerResponseConverter.convert(merch, null);
				orders.put(merch.getOrder_id(), order);
			}
			//将所有的初始供应商id放到order中
			for (OrderEntity originOrderEntity : originOrderEntitys) {
				if (order.getTransactionId().equals(originOrderEntity.getTransaction_id())) {
					order.setOrigin_supplier_id(originOrderEntity.getSupplier_id());
				}
			}
			//将所有的我的分销商商id放到order中
			for (OrderEntity childOrderEntity : childOrderEntitys) {
				if (order.getPorderId().equals(childOrderEntity.getOrder_id()) && order.getResellerId() == childOrderEntity.getSupplier_id()) {
					order.setChildResellerId(childOrderEntity.getReseller_id());
					//					if (childOrderEntity.getTotal_amount() > 0) {
					//						order.setTotalAmount(BigDecimal.valueOf(childOrderEntity.getTotal_amount()));
					//					}
				}
			}
			final List<ResellerMerchOutModel> merchEntities = order.getMerchs();
			final ResellerMerchOutModel merchEntity = orderListByResellerResponseConverter.convertToMerch(merch, orderStrategyEntitys);

			// 查询商品的退款信息
			if (merchEntity.getRefundNum() > 0) {
				List<RefundFlowResponse> refundFlowResponses = refundInfoEvent.getRefundFlow(order.getOrderId(), merchEntity.getMerchId(), merch.getVersion(),
						merch.getTransaction_id(), merch.getRoot_merch_id());
				merchEntity.setRefundInfos(refundFlowResponses);

			} else {
				merchEntity.setRefundInfos(new ArrayList<RefundFlowResponse>());
			}

			merchEntities.add(merchEntity);
		}

		qr.setRecords(merchs);
		final List<ResellerOrdersOutModel> resellerOrdersOutModels = new ArrayList<ResellerOrdersOutModel>();
		for (final Entry<String, ResellerOrdersOutModel> entry : orders.entrySet()) {
			resellerOrdersOutModels.add(entry.getValue());
		}
		respModel.setCurrentPage(qr.getCurrentPage());
		respModel.setMaxPage((int) qr.getTotalPage());
		respModel.setOrders(resellerOrdersOutModels);
		return respModel;
	}

	public QueryResult<OrderListResponse> queryOrdersByReseller(final OrderListVO orderVO) {

		final OrderListParameter queryModel = resellerOrdersArgsConverter.convert(orderVO, null);

		final QueryResult<OrderListResponse> qr = new QueryResult<OrderListResponse>(orderVO.getCurrent_page(), orderVO.getPage_size());
		qr.setTotal(0);
		// 1. 先查询订单数量.
		final OrderCountEntity orderCountEntity = orderReadMapper.getOrderCountByCondition(queryModel);
		if (orderCountEntity.getOrder_num() == 0) {
			return qr;
		}
		qr.setTotal(orderCountEntity.getOrder_num());

		// 2. 订单数量大于0时, 查询真实的订单信息.
		final List<MerchListEntity> merchs = orderReadMapper.getOrderByCondition(queryModel, orderVO.getPage_index(), orderVO.getPage_size());
		//查询所有订单商品的价格，结算价，渠道，政策
		final Set<String> orderIds = new HashSet<String>();
		final Set<String> merchIds = new HashSet<String>();
		for (final MerchListEntity merch : merchs) {
			merchIds.add(merch.getMerch_id());
			orderIds.add(merch.getOrder_id());
		}
		final List<OrderStrategyEntity> orderStrategyEntitys = orderStrategyReadMapper.getOrderStrategyByOrders(new ArrayList<String>(orderIds),
				new ArrayList<String>(merchIds));

		final Map<String, OrderListResponse> orders = new LinkedHashMap<String, OrderListResponse>();
		for (final MerchListEntity merch : merchs) {
			OrderListResponse order = orders.get(merch.getOrder_id());
			if (order == null) {
				order = orderListResponseConverter.convert(merch, null);
				orders.put(merch.getOrder_id(), order);
			}

			final List<MerchResponse> merchEntities = order.getMerchs();
			final MerchResponse merchEntity = orderListResponseConverter.convertToMerch(merch, orderStrategyEntitys);

			// 查询商品的退款信息
			if (merchEntity.getRefundNum() > 0) {
				List<RefundFlowResponse> refundFlowResponses = refundInfoEvent.getRefundFlow(order.getOrderId(), merchEntity.getMerchId(), merch.getVersion(),
						merch.getTransaction_id(), merch.getRoot_merch_id());
				merchEntity.setRefundInfos(refundFlowResponses);
			} else {
				merchEntity.setRefundInfos(new ArrayList<RefundFlowResponse>());
			}
			merchEntities.add(merchEntity);
		}

		final List<OrderListResponse> orderResponse = new ArrayList<OrderListResponse>();
		for (final Entry<String, OrderListResponse> entry : orders.entrySet()) {
			orderResponse.add(entry.getValue());
		}
		if (!Check.NuNCollections(orderResponse)) {
			orderResponse.get(0).setAll_merch_num(orderCountEntity.getMerch_num());
			orderResponse.get(0).setAll_amount(orderCountEntity.getAmount());
		}
		qr.setRecords(orderResponse);
		return qr;
	}
}
