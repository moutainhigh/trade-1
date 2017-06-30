package com.pzj.core.trade.order.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pzj.framework.converter.ObjectConverter;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.order.common.MerchStateEnum;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.common.SalePortEnum;
import com.pzj.trade.order.entity.MerchResponse;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.entity.SalesOrderResponse;

/**
 * 销售订单详情查询结果转换器.
 * @author YRJ
 *
 */
@Component(value = "salesOrderResponseConverter")
public class SalesOrderResponseConverter implements ObjectConverter<OrderEntity, List<MerchEntity>, SalesOrderResponse> {

	@Override
	public SalesOrderResponse convert(OrderEntity order, List<MerchEntity> merchs) {
		SalesOrderResponse orderResponse = copyOrderInformation(order);

		if (merchs != null) {
			List<MerchResponse> merchResponses = new ArrayList<MerchResponse>();
			for (MerchEntity merch : merchs) {
				MerchResponse merchResponse = new MerchResponse();
				merchResponse.setMerchId(merch.getMerch_id());
				merchResponse.setMerchState(merch.getMerch_state());
				merchResponse.setMerchStateMsg(MerchStateEnum.getMerchState(merch.getMerch_state()).getMsg());
				merchResponse.setOrderId(merch.getOrder_id());
				merchResponse.setProductId(merch.getProduct_id());
				merchResponse.setParentProductId(merch.getP_product_id());
				merchResponse.setChannelId(merch.getChannel_id());
				merchResponse.setStrategyId(merch.getStrategy_id());
				merchResponse.setVoucherId(merch.getVoucher_id());
				merchResponse.setTotalNum(merch.getTotal_num());
				merchResponse.setCheckNum(merch.getCheck_num());
				merchResponse.setRefundNum(merch.getRefund_num());
				merchResponse.setPrice(merch.getPrice());
				merchResponse.setRefundAmount(merch.getRefund_amount());
				merchResponse.setSettlement_price(merch.getSettlement_price());
				merchResponse.setVour_type(merch.getVour_type());
				merchResponse.setIs_cleaned(merch.getIs_cleaned());
				merchResponses.add(merchResponse);
			}
			orderResponse.setMerchs(merchResponses);
		}

		return orderResponse;
	}

	private SalesOrderResponse copyOrderInformation(OrderEntity order) {
		SalesOrderResponse orderResponse = new SalesOrderResponse();
		orderResponse.setOrderId(order.getOrder_id());
		orderResponse.setTransactionId(order.getTransaction_id());
		orderResponse.setPorderId(order.getP_order_id());
		orderResponse.setPayerId(order.getPayer_id());
		orderResponse.setOperatorId(order.getOperator_id());
		orderResponse.setSupplierId(order.getSupplier_id());
		orderResponse.setResellerId(order.getReseller_id());
		orderResponse.setTravel(order.getTravel());
		orderResponse.setTravelDepartId(order.getTravel_depart_id());
		orderResponse.setGuideId(order.getGuide_id());
		orderResponse.setOrderStatus(OrderStatusEnum.getOrderStatus(order.getOrder_status()).getMsg());
		orderResponse.setConfirm(order.getNeed_confirm() == 2);
		orderResponse.setTotalAmount(order.getTotal_amount());
		orderResponse.setRefundAmount(order.getRefund_amount());
		orderResponse.setTotalNum(order.getTotal_num());
		orderResponse.setCheckedNum(order.getChecked_num());
		orderResponse.setRefundNum(order.getRefund_num());
		orderResponse.setOrderType(order.getOrder_type());
		orderResponse.setSalePort(order.getSale_port());
		orderResponse.setSalePortContent(SalePortEnum.getSalePort(order.getSale_port()).getName());
		orderResponse.setCreateTime(order.getCreate_time());
		orderResponse.setThirdCode(order.getThird_code());
		orderResponse.setConfirmTime(order.getConfirm_time());
		orderResponse.setContactee(order.getContactee());
		orderResponse.setContactMobile(order.getContact_mobile());
		orderResponse.setThirdPayType(order.getThird_pay_type());
		orderResponse.setIs_direct(order.getIs_direct());
		if (order.getVersion() != null) {
			orderResponse.setVersion(Integer.valueOf(order.getVersion()));
		}

		return orderResponse;
	}

}
