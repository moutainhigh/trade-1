package com.pzj.core.trade.order.engine;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.query.entity.OrderMerchDetailQueryModel;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.model.RefundApplyQueryModel;
import com.pzj.core.trade.refund.read.RefundApplyReadMapper;
import com.pzj.core.trade.refund.read.RefundFlowReadMapper;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.merch.entity.MerchListEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.OrderCountEntity;
import com.pzj.trade.order.entity.SettlementOrderMerchDetailEntity;
import com.pzj.trade.order.model.SettlementOrdersOutModel;
import com.pzj.trade.order.model.SettlementOrdersReqModel;
import com.pzj.trade.order.model.SettlementOrdersRespModel;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.OrderStrategyReadMapper;
import com.pzj.trade.order.read.SettlementOrdersReadMapper;

/**
 * 订单商品明细引擎.
 * @author YRJ
 *
 */
@Component(value = "orderMerchDetailEngine")
public class OrderMerchDetailEngine {

	@Resource(name = "orderMerchDetailQueryModelConverter")
	private ObjectConverter<SettlementOrdersReqModel, Void, OrderMerchDetailQueryModel> orderMerchDetailQueryModelConverter;

	@Resource(name = "settlementOrdersReadMapper")
	private SettlementOrdersReadMapper settlementOrdersReadMapper;

	@Resource(name = "merchReadMapper")
	private MerchReadMapper merchReadMapper;

	@Resource(name = "refundApplyReadMapper")
	private RefundApplyReadMapper refundApplyReadMapper;

	@Resource(name = "refundFlowReadMapper")
	private RefundFlowReadMapper refundFlowReadMapper;

	@Resource(name = "orderStrategyReadMapper")
	private OrderStrategyReadMapper orderStrategyReadMapper;

	public SettlementOrdersRespModel queryOrders(final SettlementOrdersReqModel reqModel) {
		final OrderMerchDetailQueryModel queryModel = orderMerchDetailQueryModelConverter.convert(reqModel, null);

		final SettlementOrdersRespModel respModel = new SettlementOrdersRespModel();

		final OrderCountEntity countEntity = settlementOrdersReadMapper.queryOrderTotalNum(queryModel);
		if (countEntity == null || countEntity.getOrder_num() == 0) {
			return respModel;
		}
		respModel.setCurrentPage(reqModel.getCurrentPage());
		respModel.setTotalNum(countEntity.getOrder_num());
		//		respModel.setTotalAmount(countEntity.getAmount().doubleValue());
		//		respModel.setTotalMerchNum(countEntity.getMerch_num());

		QueryResult<MerchListEntity> qr = new QueryResult<MerchListEntity>(reqModel.getCurrentPage(), reqModel.getPageSize());
		qr.setTotal(respModel.getTotalNum());

		final List<String> orders = settlementOrdersReadMapper.queryOrderIds(queryModel, reqModel.getPageIndex(), reqModel.getPageSize());
		if (orders == null || orders.size() == 0) {
			return respModel;
		} else {
			queryModel.setTransaction_ids(orders);
			queryModel.setTransaction_id(null);
		}

		//查询出所有的商品明细
		List<SettlementOrderMerchDetailEntity> settlementOrders = settlementOrdersReadMapper.queryOrderMerchsByUser(queryModel);
		if (settlementOrders == null) {
			return respModel;
		}

		respModel.setTotalNum(countEntity.getOrder_num());
		final List<SettlementOrdersOutModel> orderModels = new ArrayList<SettlementOrdersOutModel>();
		for (final SettlementOrderMerchDetailEntity order : settlementOrders) {
			final SettlementOrdersOutModel orderModel = convertOrdersOutModel(order);
			orderModels.add(orderModel);

			if (order.getRefund_num() > 0) {//当存在退款数量时, 认为有退款申请单.
				final List<SettlementOrdersOutModel> refundOrderModels = onloadRefundRecordByOrderId(order.getTransaction_id(), order);
				if (refundOrderModels != null) {
					orderModels.addAll(refundOrderModels);
					//					filledMerchAttritesToRefundMerchModels(merchs, refundOrderModels);
				}
			}
		}

		respModel.setMaxPage((int) qr.getTotalPage());
		respModel.setOrders(orderModels);
		System.out.println("===>" + JSONConverter.toJson(respModel));
		return respModel;

	}

	private SettlementOrdersOutModel convertOrdersOutModel(final SettlementOrderMerchDetailEntity order) {
		final SettlementOrdersOutModel outModel = new SettlementOrdersOutModel();
		outModel.setOrderId(order.getOrder_id());
		outModel.setTransactionId(order.getTransaction_id());
		//2017.05.18 将固定的SaaS订单状态已支付改为实际的订单状态
		//		outModel.setOrderStatus("已支付");
		outModel.setOrderStatus(OrderStatusEnum.getOrderStatus(order.getOrder_status()).getMsg());
		outModel.setOrderType("SaaS订单");
		outModel.setUserId(order.getUser_id());
		outModel.setCreateTime(order.getCreate_time());
		outModel.setPayTime(order.getPay_time());
		if (order.getOrder_status() == OrderStatusEnum.Finished.getValue()) {
			outModel.setConfirmTime(order.getConfirm_time());
		}
		outModel.setMerchId(order.getMerch_id());
		outModel.setMerchName(order.getMerch_name());
		outModel.setSkuName(order.getSku_name());
		outModel.setProductId(order.getProduct_id());
		outModel.setTotalNum(order.getTotal_num());
		outModel.setSalePrice(order.getSale_price());
		outModel.setPurchPrice(order.getPurch_price());
		outModel.setRebate(order.getAfter_rebate_amount());

		if (order.getOsupplier_id() == order.getUser_id()) {//如果是初始供应商的话，将采购单价，应收返利置为0
			outModel.setRebate(0);
			outModel.setPurchPrice(0);
		}
		if (order.getTransaction_id().equals(order.getOrder_id()) && order.getOsupplier_id() != order.getUser_id()) {
			outModel.setSalePrice(0);
		}
		return outModel;
	}

	/**
	 * 加载退款申请单.
	 * @param transactionId
	 * @param refund
	 */
	private List<SettlementOrdersOutModel> onloadRefundRecordByOrderId(final String transactionId, SettlementOrderMerchDetailEntity order) {
		final RefundApplyQueryModel queryModel = new RefundApplyQueryModel();
		queryModel.setOrderId(transactionId);
		queryModel.setRefundStates(new int[] { 1, 2 });//只获取退款中、退款成功的数据.
		final List<RefundApplyEntity> refundEntities = refundApplyReadMapper.queryRefundApplyByOrderId(queryModel);
		if (refundEntities == null) {
			return null;
		}

		final List<SettlementOrdersOutModel> orderModels = new ArrayList<SettlementOrdersOutModel>();
		for (final RefundApplyEntity refundEntity : refundEntities) {
			final List<RefundFlowEntity> flows = refundFlowReadMapper.queryRefundFlowByRefundId(refundEntity.getRefundId());

			for (final RefundFlowEntity flow : flows) {
				if (flow.getMerch_id().equals(order.getMerch_id()) && flow.getOrder_id().equals(order.getOrder_id())) {
					final SettlementOrdersOutModel orderModel = convertToOrderOutModel(transactionId, refundEntity, order, flow);
					orderModels.add(orderModel);
				}
			}
		}
		return orderModels;
	}

	private SettlementOrdersOutModel convertToOrderOutModel(final String transactionId, final RefundApplyEntity refundEntity,
			SettlementOrderMerchDetailEntity order, RefundFlowEntity flow) {
		final SettlementOrdersOutModel orderModel = new SettlementOrdersOutModel();
		orderModel.setTransactionId(transactionId);
		orderModel.setOrderStatus(refundEntity.getRefundState() == 1 ? "已创建" : "已审核");
		orderModel.setOrderType("退款单");
		orderModel.setCreateTime(new Timestamp(refundEntity.getCreateTime().getTime()));
		if (refundEntity.getRefundState() == 2) {//已完成情况下, 才存在完成时间.
			orderModel.setConfirmTime(new Timestamp(refundEntity.getUpdateTime().getTime()));
		}
		orderModel.setOrderId(order.getOrder_id());
		if (flow.getMerch_id().equals(order.getMerch_id())) {
			orderModel.setMerchId(flow.getMerch_id());
			orderModel.setTotalNum(flow.getRefund_num());
			orderModel.setMerchName(order.getMerch_name());
			orderModel.setSkuName(order.getSku_name());
			orderModel.setProductId(order.getProduct_id());
			orderModel.setUserId(order.getUser_id());
			orderModel.setSalePrice(order.getSale_price());
			orderModel.setPurchPrice(order.getPurch_price());
			orderModel.setRebate(order.getAfter_rebate_amount());

			if (order.getOsupplier_id() == order.getUser_id()) {//如果是初始供应商的话，将采购单价，应收返利置为0
				orderModel.setRebate(0);
				orderModel.setPurchPrice(0);
			}
			if (order.getTransaction_id().equals(order.getOrder_id()) && order.getOsupplier_id() != order.getUser_id()) {
				orderModel.setSalePrice(0);
			}

		}
		return orderModel;
	}

}
