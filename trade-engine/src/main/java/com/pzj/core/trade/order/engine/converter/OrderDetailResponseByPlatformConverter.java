package com.pzj.core.trade.order.engine.converter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.event.MerchCleanRelationEvent;
import com.pzj.core.trade.order.engine.event.RefundInfoEvent;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.trade.order.entity.MerchCleanRelationResponse;
import com.pzj.trade.order.entity.MerchResponse;
import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.entity.RefundFlowResponse;
import com.pzj.trade.order.entity.SupplierOrderDetailResponse;
import com.pzj.trade.order.entity.SupplierOrderEntity;
import com.pzj.trade.order.read.OrderStrategyReadMapper;

/**
 * 订单详情查询结果转换器.
 * @author YRJ
 *
 */
@Component(value = "orderDetailResponseByPlatformConverter")
public class OrderDetailResponseByPlatformConverter implements ObjectConverter<SupplierOrderEntity, Void, SupplierOrderDetailResponse> {
	@Resource(name = "orderStrategyReadMapper")
	private OrderStrategyReadMapper orderStrategyReadMapper;
	@Resource(name = "refundInfoEvent")
	private RefundInfoEvent refundInfoEvent;
	@Resource(name = "merchCleanRelationEvent")
	private MerchCleanRelationEvent merchCleanRelationEvent;

	@Override
	public SupplierOrderDetailResponse convert(SupplierOrderEntity order, Void y) {

		SupplierOrderDetailResponse supplierOrderDetailResponse = new SupplierOrderDetailResponse();
		supplierOrderDetailResponse.setSupplier_order_id(order.getSupplier_order_id());
		supplierOrderDetailResponse.setReseller_order_id(order.getReseller_order_id());
		supplierOrderDetailResponse.setOrder_status(order.getOrder_status());
		supplierOrderDetailResponse.setCreate_time(order.getCreate_time());
		supplierOrderDetailResponse.setSupplier_id(order.getSupplier_id());
		supplierOrderDetailResponse.setPay_time(order.getPay_time());
		supplierOrderDetailResponse.setPayer_id(order.getPayer_id());
		supplierOrderDetailResponse.setThird_pay_type(order.getThird_pay_type());
		supplierOrderDetailResponse.setTotal_amount(order.getTotal_amount());
		supplierOrderDetailResponse.setRefund_amount(order.getRefund_amount());
		supplierOrderDetailResponse.setUsedAmount(supplierOrderDetailResponse.getUsedAmount() + order.getMerch_check_num() * order.getPrice());
		supplierOrderDetailResponse.setMerchs(new ArrayList<MerchResponse>());
		supplierOrderDetailResponse.setPayWay(order.getPay_way());
		return supplierOrderDetailResponse;
	}

	public MerchResponse convertMerch(SupplierOrderEntity order, List<OrderStrategyEntity> orderStrategyEntitys) {

		final MerchResponse merchResponse = new MerchResponse();
		merchResponse.setMerchId(order.getMerch_id());
		merchResponse.setMerchName(order.getMerch_name());
		merchResponse.setStrategyId(order.getStrategy_id());
		merchResponse.setTotalNum(order.getMerch_total_num());
		merchResponse.setRefundNum(order.getMerch_refund_num());
		merchResponse.setPrice(order.getPrice());
		merchResponse.setMerchState(order.getMerch_state());
		merchResponse.setRefundInfos(new ArrayList<RefundFlowResponse>());
		merchResponse.setProductId(order.getProduct_id());
		merchResponse.setStrategyId(order.getStrategy_id());
		merchResponse.setMerchType(order.getMerch_type());
		merchResponse.setProduct_varie(order.getProduct_varie());
		merchResponse.setIs_refunding(order.getIs_refunding());
		// List<OrderMerchRefundResponse> refundInfos
		// 查询商品的退款信息
		if (merchResponse.getRefundNum() > 0) {
			List<RefundFlowResponse> refundFlowResponses = refundInfoEvent.getRefundFlow(order.getSupplier_order_id(), order.getMerch_id(), order.getVersion(),
					order.getTransaction_id(), order.getRoot_merch_id());
			merchResponse.setRefundInfos(refundFlowResponses);
		} else {
			merchResponse.setRefundInfos(new ArrayList<RefundFlowResponse>());
		}
		//设置订单商品对应的结算信息
		List<MerchCleanRelationResponse> merchCleanRelationResponses = merchCleanRelationEvent.getMerchCleanRelation(merchResponse.getOrderId(),
				merchResponse.getMerchId());
		merchResponse.setMerchCleanRelations(merchCleanRelationResponses);
		//将价格信息和渠道信息放入商品中
		for (OrderStrategyEntity orderStrategyEntity : orderStrategyEntitys) {
			if (order.getSupplier_order_id().equals(orderStrategyEntity.getOrderId()) && order.getMerch_id().equals(orderStrategyEntity.getMerchId())) {
				if (orderStrategyEntity.getPrice() != null) {
					merchResponse.setPrice(orderStrategyEntity.getPrice().doubleValue());//商品单价
				}
				if (orderStrategyEntity.getSettlement_price() != null) {
					merchResponse.setSettlement_price(orderStrategyEntity.getSettlement_price().doubleValue()); //商品结算价
				}
				merchResponse.setChannelId(orderStrategyEntity.getChannelId()); //渠道id
				merchResponse.setStrategyId(orderStrategyEntity.getStrategyId());
			}
		}

		return merchResponse;
	}

}
