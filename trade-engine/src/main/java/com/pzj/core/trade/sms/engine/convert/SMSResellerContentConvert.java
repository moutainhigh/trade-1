package com.pzj.core.trade.sms.engine.convert;


/**
 * 分销商相关短信,短信内容构造器
 * @author kangzl
 *
 */
public class SMSResellerContentConvert {
	//	/**
	//	 *通知短信
	//	 */
	//	public static class OrderNoticeSMS {
	//		/**
	//		 * 微点的商品激活（商品的状态变成待消费状态）的短信
	//		 * @param productName
	//		 * @param resellerOrderId
	//		 * @param contateeName
	//		 * @param contacteePhone
	//		 * @return
	//		 */
	//		public static String getNoticeMicroshopMsg(String productName, String resellerOrderId, String contateeName, String contacteePhone, String supplierPhone) {
	//			StringBuffer sb = new StringBuffer("您有新订单了：订单").append(resellerOrderId);
	//			sb.append("（").append(productName).append("），");
	//			sb.append("联系人（");
	//			if (!Check.NuNString(contateeName)) {
	//				sb.append(contateeName).append("，");
	//			}
	//			sb.append(contacteePhone);
	//			sb.append("），详询").append(supplierPhone);
	//			return sb.toString();
	//		}
	//	}
	//
	//	/**
	//	 *订单发生退款操作发送短信
	//	 */
	//	public static class OrderRefundSMS {
	//		/**
	//		 * 订单被二次确认拒绝后，
	//		 * @param resellerOrderId
	//		 * @param contacteePhone
	//		 * @return
	//		 */
	//		public static String getRefuseConfirmOfPCAndAPP(String prodcutName, String resellerOrderId, String contacteePhone, String supplierPhone) {
	//			StringBuffer sb = new StringBuffer("您的").append(prodcutName).append("未预订成功，系统已自动退款，");
	//			sb.append("请及时给游客").append(contacteePhone).append("退款，");
	//			sb.append("详询").append(supplierPhone);
	//			return sb.toString();
	//		}
	//	}
}
