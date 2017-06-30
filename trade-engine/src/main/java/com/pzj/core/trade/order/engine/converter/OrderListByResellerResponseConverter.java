package com.pzj.core.trade.order.engine.converter;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pzj.framework.converter.ObjectConverter;
import com.pzj.trade.merch.entity.MerchListEntity;
import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.model.ResellerMerchOutModel;
import com.pzj.trade.order.model.ResellerOrdersOutModel;

/**
 * 订单列表查询条件转换.
 * @author GLG
 *
 */
@Component(value = "orderListByResellerResponseConverter")
public class OrderListByResellerResponseConverter implements ObjectConverter<MerchListEntity, Void, ResellerOrdersOutModel> {

	@Override
	public ResellerOrdersOutModel convert(MerchListEntity merch, Void y) {
		ResellerOrdersOutModel order = new ResellerOrdersOutModel();
		order.setOrderId(merch.getOrder_id());
		order.setPorderId(merch.getP_order_id());
		order.setOrderStatus(merch.getOrder_status());
		order.setPayer_id(merch.getPayer_id());
		order.setOperator_id(merch.getOperator_id());
		order.setSupplier_id(merch.getSupplier_id());
		order.setTotalNum(merch.getTotal_num());
		order.setCheckedNum(merch.getChecked_num());
		order.setRefundNum(merch.getOrder_refund_num());
		order.setTotalAmount(BigDecimal.valueOf(merch.getTotal_amount()));//金额
		order.setRefundAmount(BigDecimal.valueOf(merch.getOrder_refund_amount()));
		order.setCreateTime(merch.getCreate_time());
		order.setPayTime(merch.getPay_time());
		order.setConfirmTime(merch.getConfirm_time());
		order.setContactee(merch.getContactee());
		order.setContact_mobile(merch.getContact_mobile());
		order.setCategory(merch.getMerch_type());
		order.setResellerId(merch.getReseller_id());
		order.setSalesPort(merch.getSale_port());
		order.setIdcard_no(merch.getIdcard_no());
		order.setResellerId(merch.getReseller_id());
		order.setTravel(merch.getTravel());
		order.setTravel_depart_id(merch.getTravel_depart_id());
		order.setGuide_id(merch.getGuide_id());
		order.setSettlement_price(merch.getOrder_settlement_price());
		order.setNeed_confirm(merch.getNeed_confirm());
		order.setAgent_flag(merch.getAgent_flag());
		order.setOrder_type(merch.getOrder_type());
		order.setIs_direct(merch.getIs_direct());
		order.setOnline_pay(merch.getOnline_pay());
		order.setIs_dock(merch.getIs_dock());
		order.setRefund_state(merch.getRefund_state());
		order.setTransactionId(merch.getTransaction_id());
		return order;
	}

	public ResellerMerchOutModel convertToMerch(MerchListEntity merch, List<OrderStrategyEntity> orderStrategyEntitys) {
		ResellerMerchOutModel merchEntity = new ResellerMerchOutModel();
		merchEntity.setOrderId(merch.getOrder_id());
		merchEntity.setMerchId(merch.getMerch_id());
		merchEntity.setMerchState(merch.getMerch_state());
		merchEntity.setProductId(merch.getProduct_id());
		merchEntity.setPrice(merch.getPrice());
		merchEntity.setMerchName(merch.getMerch_name());
		merchEntity.setMerchType(merch.getMerch_type());
		merchEntity.setTotalNum(merch.getMerch_total_num());
		merchEntity.setProduct_varie(merch.getProduct_varie());
		merchEntity.setVoucherId(merch.getVoucher_id());
		merchEntity.setChannelId(merch.getChannel_id());
		merchEntity.setSettlement_price(merch.getSettlement_price());
		merchEntity.setUpdate_time(merch.getUpdate_time());
		merchEntity.setRefundNum(merch.getRefund_num());
		merchEntity.setCheckNum(merch.getCheck_num());
		merchEntity.setRefundAmount(merch.getRefund_amount());
		merchEntity.setVour_type(merch.getVour_type());
		merchEntity.setIs_refunding(merch.getIs_refunding());
		merchEntity.setCheck_time(merch.getCheck_time());
		merchEntity.setIs_cleaned(merch.getIs_cleaned());
		merchEntity.setStart_time(merch.getStart_time());
		merchEntity.setExpire_time(merch.getExpire_time());
		//将价格信息和渠道信息放入商品中
		for (OrderStrategyEntity orderStrategyEntity : orderStrategyEntitys) {
			if (merch.getOrder_id().equals(orderStrategyEntity.getOrderId()) && merch.getMerch_id().equals(orderStrategyEntity.getMerchId())) {
				if (orderStrategyEntity.getPrice() != null) {
					merchEntity.setPrice(orderStrategyEntity.getPrice().doubleValue());//商品单价
				}
				if (orderStrategyEntity.getSettlement_price() != null) {
					merchEntity.setSettlement_price(orderStrategyEntity.getSettlement_price().doubleValue()); //商品结算价
				}
				merchEntity.setChannelId(orderStrategyEntity.getChannelId()); //渠道id
				merchEntity.setStrategyId(orderStrategyEntity.getStrategyId());
			}
		}

		return merchEntity;
	}

}
