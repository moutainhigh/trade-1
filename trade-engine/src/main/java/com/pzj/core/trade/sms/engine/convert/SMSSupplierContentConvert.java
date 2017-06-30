package com.pzj.core.trade.sms.engine.convert;

import org.springframework.stereotype.Component;

/**
 * 供应商相关短信,短信内容构造器
 * @author kangzl
 *
 */
@Component
public class SMSSupplierContentConvert {
	//	/**
	//	 *供应商的通知短信
	//	 */
	//	public static class OrderNotifyMsg {
	//		/**
	//		 * 默认的通知短信内容
	//		 */
	//		public static String getNotityMsgDefault(String productName, String travelDate, List<MerchStandardModel> standarModels, String orderUrl,
	//				String resellerPhone) {
	//			StringBuffer sb = new StringBuffer("您有新订单了！").append(productName).append("，");
	//			if (!Check.NuNString(travelDate)) {
	//				sb.append("出游日期：").append(travelDate).append(",");
	//			}
	//			for (MerchStandardModel model : standarModels) {
	//				sb.append(model.getStandardName()).append("：").append(model.getStandardNum()).append("份，");
	//			}
	//			sb.append("详询").append(resellerPhone);
	//			sb.append("，订单请戳：").append(orderUrl);
	//			return sb.toString();
	//		}
	//
	//		public static String getNotifiyMsgNeedConfirm(String productName, double orderMoney, String supplierOrderId, String orderUrl, String resellerPhone) {
	//			//StringBuffer sb = new StringBuffer("您即将赚取").append(SMSContent.get2DecimalPlaces(orderMoney)).append("元，");
	//			StringBuffer sb = new StringBuffer("您有新订单了，");
	//			sb.append("请及时确认！订单").append(supplierOrderId).append("，").append(productName).append("，");
	//			sb.append("详询").append(resellerPhone);
	//			sb.append("，订单请戳：").append(orderUrl);
	//			return sb.toString();
	//		}
	//
	//		public static String getRefundMsg(String productName, List<MerchStandardModel> standarModels, String orderUrl) {
	//			StringBuffer sb = new StringBuffer();
	//			sb.append("您的订单（").append(productName).append("）已被下单人退款：");
	//			for (MerchStandardModel model : standarModels) {
	//				sb.append(model.getStandardName()).append("（").append(model.getStandardNum()).append("份），");
	//			}
	//			sb.append("订单请戳：").append(orderUrl);
	//			return sb.toString();
	//		}
	//	}
}
