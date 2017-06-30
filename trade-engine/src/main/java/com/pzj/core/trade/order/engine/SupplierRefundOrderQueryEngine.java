package com.pzj.core.trade.order.engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.base.service.sys.IChannelService;
import com.pzj.base.service.sys.IUserService;
import com.pzj.core.trade.order.engine.common.TimeCalculator;
import com.pzj.core.trade.order.engine.converter.OrderListBySupplierResponseConverter;
import com.pzj.core.trade.order.engine.event.RefundInfoEvent;
import com.pzj.core.trade.query.entity.SupplierOrdersQueryModel;
import com.pzj.framework.entity.QueryResult;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.MerchListEntity;
import com.pzj.trade.order.entity.ForceRefundOrderCountEntity;
import com.pzj.trade.order.entity.MerchResponse;
import com.pzj.trade.order.entity.OrderCountEntity;
import com.pzj.trade.order.entity.OrderListResponse;
import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.entity.RefundFlowResponse;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.OrderRefundForSupplierReadMapper;
import com.pzj.trade.order.read.OrderStrategyReadMapper;

/**
 * 供应商订单查询引擎.
 * @author YRJ
 *
 */
@Component(value = "supplierRefundOrderQueryEngine")
public class SupplierRefundOrderQueryEngine {

	private final static Logger logger = LoggerFactory.getLogger(SupplierRefundOrderQueryEngine.class);

	@Autowired
	@Resource(name = "orderRefundForSupplierReadMapper")
	private OrderRefundForSupplierReadMapper orderRefundForSupplierReadMapper;

	@Autowired
	private TimeCalculator timeCalculator;
	@Resource(name = "merchReadMapper")
	private MerchReadMapper merchReadMapper;
	@Autowired
	private IUserService userService;
	@Autowired
	private IChannelService iChannelService;
	@Resource(name = "refundInfoEvent")
	private RefundInfoEvent refundInfoEvent;

	@Resource(name = "orderListBySupplierResponseConverter")
	private OrderListBySupplierResponseConverter orderListBySupplierResponseConverter;
	@Resource(name = "orderStrategyReadMapper")
	private OrderStrategyReadMapper orderStrategyReadMapper;

	/**
	 * Sssa供应查询订单列表引擎.
	 * @param reqModel
	 */
	public QueryResult<OrderListResponse> queryRefundSaaSOrders(SupplierOrdersQueryModel reqModel) {

		final QueryResult<OrderListResponse> qr = new QueryResult<OrderListResponse>(reqModel.getCurrentPage(), reqModel.getPageSize());
		qr.setTotal(0);

		final List<ForceRefundOrderCountEntity> forceRefundOrderCountEntitys = orderRefundForSupplierReadMapper.querySaaSSupplierRefundOrderCount(reqModel);
		OrderCountEntity count = orderRefundForSupplierReadMapper.querySaaSSupplierOrderCount(reqModel);

		if (count != null) {
			qr.setTotal(forceRefundOrderCountEntitys.size());
		} else {
			return qr;
		}
		if (forceRefundOrderCountEntitys == null || forceRefundOrderCountEntitys.size() == 0) {
			qr.setRecords(new ArrayList<OrderListResponse>());
			return qr;
		}
		final List<String> paramOrderIds = new ArrayList<String>();
		final List<String> paramRefundIds = new ArrayList<String>();
		final List<String> paramTransactionIds = new ArrayList<String>();

		for (int i = reqModel.getPageIndex(); i < reqModel.getPageIndex() + reqModel.getPageSize(); i++) {
			if (i < forceRefundOrderCountEntitys.size()) {
				paramOrderIds.add(forceRefundOrderCountEntitys.get(i).getOrder_id());
				paramRefundIds.add(forceRefundOrderCountEntitys.get(i).getRefund_id());
				paramTransactionIds.add(forceRefundOrderCountEntitys.get(i).getTransaction_id());
			} else {
				break;
			}

		}
		reqModel.setOrder_ids(paramOrderIds);
		reqModel.setRefund_ids(paramRefundIds);
		List<MerchListEntity> orders = orderRefundForSupplierReadMapper.querySaaSOrderByCondition(reqModel);
		//根据订单查询出所有的商品
		final List<MerchEntity> merchEntitys = merchReadMapper.getMerchByTransactionIds(paramTransactionIds);
		reqModel.setOrder_ids(paramOrderIds);
		reqModel.setRefund_ids(paramRefundIds);
		Set<String> merchIds = new HashSet<String>();
		for (final MerchEntity merch : merchEntitys) {
			merchIds.add(merch.getMerch_id());
		}
		//查询所有订单商品的价格，结算价，渠道，政策
		List<OrderStrategyEntity> orderStrategyEntitys = orderStrategyReadMapper.getOrderStrategyByOrders(new ArrayList<String>(paramOrderIds),
				new ArrayList<String>(merchIds));
		final List<OrderListResponse> orderResponse = new ArrayList<OrderListResponse>();
		//组装订单商品信息
		for (final MerchListEntity orderMerch : orders) {
			final OrderListResponse order = orderListBySupplierResponseConverter.convert(orderMerch, null);
			for (final MerchEntity me : merchEntitys) {
				if (me.getTransaction_id() != null) {
					if (me.getTransaction_id().equals(order.getTransactionId())) {
						final List<MerchResponse> merchEntities = order.getMerchs();
						final MerchResponse merchEntity = orderListBySupplierResponseConverter.convertToMerchEntity(me, orderStrategyEntitys,
								order.getOrderId());
						// 查询商品的退款信息
						//						List<RefundFlowResponse> refundFlowResponses = refundInfoEvent.getRefundFlowByRefundId(orderMerch.getRefund_id());
						List<RefundFlowResponse> refundFlowResponses = refundInfoEvent.getRefundFlow(orderMerch.getOrder_id(), merchEntity.getMerchId(),
								orderMerch.getVersion(), orderMerch.getTransaction_id(), me.getRoot_merch_id());
						merchEntity.setRefundInfos(refundFlowResponses);
						merchEntities.add(merchEntity);
					}
				}
			}
			orderResponse.add(order);
		}
		if (!Check.NuNCollections(orderResponse)) {
			orderResponse.get(0).setAll_merch_num(count.getMerch_num());
			orderResponse.get(0).setAll_amount(count.getAmount());
		}
		qr.setRecords(orderResponse);
		return qr;
	}
}
