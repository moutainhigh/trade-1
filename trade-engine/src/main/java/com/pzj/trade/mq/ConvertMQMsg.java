package com.pzj.trade.mq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.withdraw.engine.UserTypeEnum;

public class ConvertMQMsg {

	/**
	 * 创建支付流水消息内容
	 * @param flow_id
	 * @return
	 */
	public static String convertPayFlowMsg(final long flow_id) {
		final Map<String, String> json = new HashMap<String, String>();
		json.put("flowId", String.valueOf(flow_id));
		return JSONConverter.toJson(json);
	}

	/**
	 * 创建订单状态变化时的消息内容（订单变为，已退款，已支付）
	 * @param order
	 * @return
	 */
	public static String convertOrderChangeMsg(final OrderEntity order) {
		final Map<String, Object> json = new HashMap<String, Object>();
		//分销商id
		json.put("resellerId", String.valueOf(order.getReseller_id()));
		//交易id
		json.put("transactionId", String.valueOf(order.getTransaction_id()));
		//渠道id
		//json.put("channelType", String.valueOf(order.getChannel_type()));
		//分销商名称,此值获取不到
		json.put("resellerName", "");
		//供应商id
		json.put("supplierId", String.valueOf(order.getSupplier_id()));
		//供应商商名称,此值获取不到
		json.put("supplierName", String.valueOf(order.getReseller_id()));
		//订单id
		json.put("orderId", String.valueOf(order.getOrder_id()));
		//订单金额
		json.put("orderAmount", order.getTotal_amount());
		//退款金额
		json.put("refundAmount", order.getRefund_amount());
		System.out.println("=================================" + JSONConverter.toJson(json));
		return JSONConverter.toJson(json);
	}

	/**
	 * 创建商品消费成功后发送的消息内容
	 * @param order_id
	 * @param merch_id
	 * @return
	 */
	public static String convertMerchConsumerMsg(final String order_id, final String merch_id) {
		final Map<String, String> json = new HashMap<String, String>();
		json.put("orderId", String.valueOf(order_id));
		json.put("merchId", String.valueOf(merch_id));
		return JSONConverter.toJson(json);
	}

	/**
	 *
	 * 创建订单消费成功发送的消息
	 * @param order_id
	 * @return
	 */
	public static String convertOrderConsumerMsg(final String order_id) {
		final Map<String, String> json = new HashMap<String, String>();
		json.put("orderId", String.valueOf(order_id));
		return JSONConverter.toJson(json);
	}

	/**
	 *
	 * 创建提现发送的消息
	 */
	public static String convertCashPostalMsg(final long account_id, final long plate_id, final long postal_id, final int user_type, final boolean isQRCode,
			final double taken_money) {
		final Map<String, Object> json = new HashMap<String, Object>();
		json.put("accountUnit", 1);
		json.put("accountUnitId", String.valueOf(plate_id));
		json.put("withdrawMoney", taken_money);
		if (user_type == UserTypeEnum.Reseller.getValue() && isQRCode) {
			json.put("isQrCode", "Y");
		} else {
			json.put("isQrCode", "N");
		}
		json.put("accountType", user_type);
		json.put("userName", null);
		json.put("userId", String.valueOf(account_id));
		json.put("withdrawId", postal_id);
		return JSONConverter.toJson(json);
	}

	/**
	 * 订单提现退款金额冻结消息
	 * @param refundId
	 * @param orderId
	 * @param refundAmount
	 * @return
	 */
	public static String convertFrozenDrawingOrderMsg(final String refundId, final String orderId, final long accountId, final boolean isAudit,
			final double refundAmount) {
		final Map<String, Object> json = new HashMap<String, Object>();
		json.put("refundId", refundId);
		json.put("accountId", accountId);
		json.put("orderId", orderId);
		json.put("isAudit", isAudit);
		json.put("refundAmount", refundAmount);
		return JSONConverter.toJson(json);
	}

	public static String convertVoucherOrder(final String transactionId, final long voucherId) {
		final Map<String, Object> message = new HashMap<String, Object>();
		message.put("transactionId", transactionId);
		message.put("voucherId", voucherId);
		return JSONConverter.toJson(message);
	}

	/**
	 * 与华清宫项目对接发送的消息
	 * @param orderlist
	 * @return
	 */
	public static String convertCreateDockingOrder(final OrderEntity order) {
		final List<Map<String, Object>> resultlist = new ArrayList<Map<String, Object>>();
		//for (OrderEntity entity : order) {
		//过滤销售订单信息
		//			if (order.getOrder_id().equals(order.getP_order_id())) {
		//				continue;
		//			}
		final Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("dockingOrderId", order.getOrder_id());
		obj.put("supplierId", String.valueOf(order.getSupplier_id()));
		obj.put("porderId", order.getP_order_id());
		resultlist.add(obj);
		//}
		final Map<String, Object> result = new HashMap<String, Object>();
		result.put("dockingOrderIdList", resultlist);
		return JSONConverter.toJson(result);
	}
}
