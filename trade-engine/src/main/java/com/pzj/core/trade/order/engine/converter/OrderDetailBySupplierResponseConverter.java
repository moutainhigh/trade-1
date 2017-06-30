package com.pzj.core.trade.order.engine.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.common.PayWayForConvertEnum;
import com.pzj.core.trade.refund.engine.common.MerchStateEnum;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.common.SalePortEnum;
import com.pzj.trade.order.entity.MerchResponse;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.entity.OrderStrategyResponse;
import com.pzj.trade.order.entity.RefundFlowResponse;
import com.pzj.trade.order.entity.SupplierOrderDetailResponse;
import com.pzj.trade.order.read.OrderStrategyReadMapper;

/**
 * 订单详情查询结果转换器.
 * @author YRJ
 *
 */
@Component(value = "orderDetailBySupplierResponseConverter")
public class OrderDetailBySupplierResponseConverter implements
		ObjectConverter<OrderEntity, List<MerchEntity>, SupplierOrderDetailResponse> {
	@Resource(name = "orderStrategyReadMapper")
	private OrderStrategyReadMapper orderStrategyReadMapper;

	@Override
	public SupplierOrderDetailResponse convert(OrderEntity order, List<MerchEntity> merchs) {
		SupplierOrderDetailResponse orderResponse = copyOrderInformation(order, merchs.get(0));

		if (merchs != null && merchs.size() != 0) {
			Set<String> orderIds = new HashSet<String>();
			orderIds.add(order.getOrder_id());
			Set<String> merchIds = new HashSet<String>();
			for (final MerchEntity merch : merchs) {
				merchIds.add(merch.getMerch_id());
			}
			//查询所有订单商品的价格，结算价，渠道，政策
			List<OrderStrategyEntity> orderStrategyEntitys = orderStrategyReadMapper.getOrderStrategyByOrders(
					new ArrayList<String>(orderIds), new ArrayList<String>(merchIds));
			Map<String, MerchResponse> merchList = new HashMap<String, MerchResponse>();
			for (MerchEntity merch : merchs) {
				MerchResponse merchResponse = merchList.get(merch.getMerch_id());
				if (merchResponse == null) {
					merchResponse = new MerchResponse();
					merchResponse.setMerchId(merch.getMerch_id());
					merchResponse.setMerchState(merch.getMerch_state());
					merchResponse.setMerchName(merch.getMerch_name());
					merchResponse.setMerchType(merch.getMerch_type());
					merchResponse.setMerchStateMsg(MerchStateEnum.getMerchState(merch.getMerch_state()).getMsg());
					merchResponse.setOrderId(order.getOrder_id());
					merchResponse.setProductId(merch.getProduct_id());
					merchResponse.setRootMerchId(merch.getRoot_merch_id());
					merchResponse.setVoucherId(merch.getVoucher_id());
					merchResponse.setTotalNum(merch.getTotal_num());
					merchResponse.setCheckNum(merch.getCheck_num());
					merchResponse.setRefundNum(merch.getRefund_num());
					merchResponse.setPrice(merch.getPrice());
					merchResponse.setRefundAmount(merch.getRefund_amount());
					merchResponse.setUpdate_time(merch.getUpdate_time());
					merchResponse.setProduct_varie(String.valueOf(merch.getProduct_varie()));
					merchResponse.setSettlement_price(merch.getSettlement_price());
					merchResponse.setVour_type(merch.getVour_type());
					merchResponse.setIs_refunding(merch.getIs_refunding());
					merchResponse.setRefundInfos(new ArrayList<RefundFlowResponse>());
					merchResponse.setCheck_time(merch.getCheck_time());
					merchResponse.setIs_cleaned(merch.getIs_cleaned());
					merchResponse.setStart_time(merch.getStart_time());
					merchResponse.setExpire_time(merch.getExpire_time());
					if (merch.getIs_cleaned() == 1) {
						merchResponse.setClear_time(merch.getUpdate_time());
					}

					OrderStrategyResponse orderStrategyResponse = new OrderStrategyResponse();
					orderStrategyResponse.setOrderId(order.getOrder_id());
					orderStrategyResponse.setMerchId(orderStrategyResponse.getMerchId());
					orderStrategyResponse.setRemark(merch.getDiscount_remark());

					List<OrderStrategyResponse> list = new ArrayList<OrderStrategyResponse>();
					list.add(orderStrategyResponse);
					merchResponse.setOrderStrategyList(list);
					merchList.put(merchResponse.getMerchId(), merchResponse);
					//将价格信息和渠道信息放入商品中
					for (OrderStrategyEntity orderStrategyEntity : orderStrategyEntitys) {
						if (order.getOrder_id().equals(orderStrategyEntity.getOrderId())
								&& merch.getMerch_id().equals(orderStrategyEntity.getMerchId())) {
							if (orderStrategyEntity.getPrice() != null) {
								merchResponse.setPrice(orderStrategyEntity.getPrice().doubleValue());//商品单价
							}
							if (orderStrategyEntity.getSettlement_price() != null) {
								merchResponse.setSettlement_price(orderStrategyEntity.getSettlement_price().doubleValue()); //商品结算价
							}
							merchResponse.setChannelId(orderStrategyEntity.getChannelId()); //渠道id
							merchResponse.setStrategyId(orderStrategyEntity.getStrategyId());
							orderStrategyResponse.setChannelId(orderStrategyEntity.getChannelId());
							orderStrategyResponse.setStrategyId(orderStrategyEntity.getStrategyId());
						}
					}
				} else {
					OrderStrategyResponse orderStrategyResponse = new OrderStrategyResponse();
					orderStrategyResponse.setRemark(merch.getDiscount_remark());
					merchResponse.getOrderStrategyList().add(orderStrategyResponse);
				}

			}
			double usedAmount = 0.0;
			List<MerchResponse> merchResponseList = new ArrayList<MerchResponse>();
			for (Iterator<Entry<String, MerchResponse>> i = merchList.entrySet().iterator(); i.hasNext();) {
				Entry<String, MerchResponse> entry = i.next();
				merchResponseList.add(entry.getValue());
				MerchResponse m = entry.getValue();
				usedAmount += m.getCheckNum() * m.getPrice();

			}
			orderResponse.setUsedAmount(usedAmount);
			orderResponse.setChannel_id(merchResponseList.get(0).getChannelId());
			orderResponse.setMerchs(merchResponseList);

		}

		return orderResponse;
	}

	private SupplierOrderDetailResponse copyOrderInformation(OrderEntity order, MerchEntity merch) {
		SupplierOrderDetailResponse orderResponse = new SupplierOrderDetailResponse();
		orderResponse.setOrder_id(order.getOrder_id());
		orderResponse.setTransaction_id(order.getTransaction_id());
		orderResponse.setP_order_id(order.getP_order_id());
		orderResponse.setPayer_id(order.getPayer_id());
		orderResponse.setOperator_id(order.getOperator_id());
		orderResponse.setSupplier_id(order.getSupplier_id());
		orderResponse.setReseller_id(order.getReseller_id());
		orderResponse.setTravel(order.getTravel());
		orderResponse.setTravel_depart_id(order.getTravel_depart_id());
		orderResponse.setGuide_id(order.getGuide_id());
		orderResponse.setOrder_status(OrderStatusEnum.getOrderStatus(order.getOrder_status()).getValue());
		orderResponse.setConfirm(order.getNeed_confirm());
		orderResponse.setTotal_amount(order.getTotal_amount());
		orderResponse.setRefund_amount(order.getRefund_amount());
		orderResponse.setTotal_num(order.getTotal_num());
		orderResponse.setChecked_num(order.getChecked_num());
		orderResponse.setRefund_num(order.getRefund_num());
		orderResponse.setSale_port(order.getSale_port());
		orderResponse.setSale_port_content(SalePortEnum.getSalePort(order.getSale_port()).getName());
		orderResponse.setCreate_time(order.getCreate_time());
		orderResponse.setPay_time(order.getPay_time());
		orderResponse.setThird_pay_type(order.getThird_pay_type());
		orderResponse.setContactee(order.getContactee());
		orderResponse.setContact_mobile(order.getContact_mobile());
		orderResponse.setIdcard_no(order.getIdcard_no());
		orderResponse.setSettlement_price(order.getSettlement_price());
		orderResponse.setSalePersonId(order.getOperator_id());
		orderResponse.setSalePiont(order.getTicket_office_id());
		orderResponse.setOrigin_supplier_id(merch.getSupplier_id());
		convertPayWay(orderResponse, order);
		return orderResponse;
	}

	/**
	 * 对传给前端的的支付方式进行转换
	 * 0: 纯余额; 1. 支付宝; 2. 微信; 4: 混合支付 对应：  1：第三方/余额;
	 *  6: 后付  对应：2：签单
	 * 5: 现金  对应： 3现金;
	 * */
	private void convertPayWay(SupplierOrderDetailResponse orderResponse, OrderEntity order) {
		/**支付方式. 1：第三方/余额 2：签单 3：现金*/
		orderResponse.setPayWay(PayWayForConvertEnum.getConvertPayWay(order.getPay_way() + "").getConvertPayWay());
	}

}
