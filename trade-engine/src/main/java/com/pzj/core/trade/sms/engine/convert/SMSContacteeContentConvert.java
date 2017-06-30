package com.pzj.core.trade.sms.engine.convert;

import java.util.List;

import com.pzj.core.trade.sms.engine.model.MerchStandardModel;

/**
 * 分销商相关短信,短信内容构造器
 * @author kangzl
 *
 */
public class SMSContacteeContentConvert {

	/**
	 * 订单的凭证短信
	 * @author kangzl
	 *
	 */
	public static class OrderVoucherContentSMS {
		/**
		 * 二维码订单（凭证短信）
		 * @param productName
		 * @param supllierPhone
		 * @param mfcode
		 * @param mfcodeUrl
		 * @return
		 */
		public static String getOrderMfcodeMsg(String productName, String supllierPhone, String mfcode, String mfcodeUrl, String resellerPhone) {
			StringBuffer sb = new StringBuffer();
			sb.append("您购买了").append(productName).append("，验证码").append(mfcode).append("，详情：").append(mfcodeUrl);
			return sb.toString();
		}

		/**
		 * @param productName
		 * @param supllierPhone
		 * @param mfcode
		 * @param mfcodeUrl
		 * @param resellerPhone
		 * @return
		 */
		public static String getOrderOnePersonOneMfcodeMsg(String productName, String mfcodeUrl) {
			StringBuffer sb = new StringBuffer();
			sb.append("您购买了").append(productName).append("，详情及验证码：").append(mfcodeUrl);
			return sb.toString();
		}

		/**
		 * 对接系统的,并且有第三方辅助码的订单（凭证短信）
		 * @param thirdCode
		 * @return
		 */
		public static String getOrderDockMsg(final String thirdCode, final String supplierPhone) {
			final StringBuffer sb = new StringBuffer();
			sb.append(thirdCode).append("祝您旅途愉快！详询").append(supplierPhone);
			return sb.toString();
		}

		/**
		 * 默认的短信（凭证短信）
		 * @param productName
		 * @param supllierPhone
		 * @return
		 */
		public static String getDefulatMsg(final String productName, final String resellerPhone, final String orderURL) {
			final StringBuffer sb = new StringBuffer();
			sb.append("您购买了").append(productName).append(",详情");
			sb.append(orderURL).append("，查询电话");
			sb.append(resellerPhone);
			return sb.toString();
		}
	}

	/**
	 *订单支付成功后发送的短信,如果不是二次确认,直接发送凭证短信
	 */
	public static class OrderPaymentContentSMS {
		/**
		 * 订单需要二次确认,
		 * @return
		 */
		public static String getNeedConfirmMsg(final String productName, final String supplierPhone) {
			final StringBuffer sb = new StringBuffer();
			sb.append("您的").append(productName).append("正在为您确认，详询");
			sb.append(supplierPhone);
			return sb.toString();
		}
	}

	/**
	 * 订单商品核销发送的短信
	 * @author kangzl
	 *
	 */
	public static class OrderConsumerContentSMS {
		/**
		 * 自提
		 * @param productName
		 * @return
		 */
		public static String getDeliveryOwnerPickMsg(final String productName, final String resellerPhone, final String orderURL) {
			final StringBuffer sb = new StringBuffer();
			sb.append("主银，我是").append(productName).append("，正在奔向你的怀抱，");
			sb.append("详情：").append(orderURL).append("，查询电话").append(resellerPhone);
			return sb.toString();
		}

		//		/**
		//		 * 默认的发货信息内容
		//		 * @param productName
		//		 * @param courierName
		//		 * @param courierCode
		//		 * @return
		//		 */
		//		public static String getDeliveryDefaultMsg(String productName, String courierName, String courierCode, String orderURL) {
		//			StringBuffer sb = new StringBuffer();
		//			sb.append("主银，我是").append(productName).append("，正在奔向你的怀抱，");
		//			sb.append("快递公司：").append(courierName).append("快递编号：").append(courierCode);
		//			sb.append("，详情：").append(orderURL);
		//			return sb.toString();
		//		}

		//		/**
		//		 * 魔方码核销后向订单联系人发送的短信
		//		 * @param productName
		//		 * @return
		//		 */
		//		public static String getMfcodeConsumerMsg(String productName, String consumerDate, String resellerPhone) {
		//			StringBuffer sb = new StringBuffer();
		//			sb.append("您购买的").append(productName).append("，已于").append(consumerDate);
		//			sb.append("被消费。如有问题，详询").append(resellerPhone);
		//			return sb.toString();
		//		}
	}

	/**
	 * 订单退款发送的相关短信
	 * @author kangzl
	 *
	 */
	public static class OrderRefundContentSMS {
		/**
		 * 二次确认拒绝触发的退款(非微店)
		 * @param productName
		 * @param resellerPhone
		 * @return
		 */
		public static String getRefuseConfirmDefaultMsg(final String productName, final String resellerPhone) {
			final StringBuffer sb = new StringBuffer();
			sb.append("您的").append(productName).append("未预订成功，请及时联系销售方").append(resellerPhone).append("退款");
			return sb.toString();
		}

		/**
		 *  二次确认拒绝触发的退款(微店)
		 * @param productName
		 * @return
		 * @author YRJ 20170527 将未预定成功字样补充上.
		 */
		public static String getRefuseConfirmMicroshopMsg(final String productName, final String resellerPhone) {
			final StringBuffer sb = new StringBuffer();
			sb.append("您的").append(productName).append("未预订成功，系统已自动退款，详询").append(resellerPhone);
			return sb.toString();
		}

		/**
		 * 分销商整单申请退款
		 * @param productName
		 * @param resellerPhone
		 * @return
		 */
		public static String getResellerTotalRefundDefaultMsg(final String productName, final String resellerPhone) {
			final StringBuffer sb = new StringBuffer();
			sb.append("您的").append(productName).append("订单退款申请已成功，请及时联系销售方").append(resellerPhone).append("退款");
			return sb.toString();
		}

		/**
			 * 分销商整单申请退款（微店）
			 * @param productName
			 * @return
		 */
		public static String getResellerTotalRefundMicroshoptMsg(final String productName, final String resellerPhone) {
			final StringBuffer sb = new StringBuffer();
			sb.append("您的").append(productName).append("已退款，5日内返还至支付账户，详询").append(resellerPhone);
			return sb.toString();
		}

		/**
		 * 分销商部分申请退款（微店）
		 * @param productName
		 * @param resellerPhone
		 * @param standarModels
		 * @return
		 */
		public static String getResellerPartRefundMicroshoptMsg(final String productName, final String resellerPhone,
				final List<MerchStandardModel> standarModels) {
			final StringBuffer sb = new StringBuffer();
			sb.append("您的").append(productName).append("订单中的");
			for (final MerchStandardModel model : standarModels) {
				sb.append(model.getStandardName()).append(model.getStandardNum()).append("份，");
			}
			sb.append("已退款。5日内返还至支付账户，详询").append(resellerPhone);
			return sb.toString();
		}

		/**
		 * 分销商部分申请退款（非微店）
		 * @param productName
		 * @param resellerPhone
		 * @param standarModels
		 * @return
		 */
		public static String getResellerPartRefundDefaultMsg(final String productName, final String resellerPhone, final List<MerchStandardModel> standarModels) {
			final StringBuffer sb = new StringBuffer();
			sb.append("您的").append(productName).append("订单中的");
			for (final MerchStandardModel model : standarModels) {
				sb.append(model.getStandardName()).append(model.getStandardNum()).append("份，");
			}
			sb.append("已退款。请及时联系销售方").append(resellerPhone).append("退款");
			return sb.toString();
		}
	}
}
