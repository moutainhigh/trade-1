package com.pzj.core.trade.sms.engine.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.pzj.commons.utils.MD5Utils;
import com.pzj.commons.utils.ShortenUrlUtils;
import com.pzj.core.trade.sms.engine.content.SMSContent;
import com.pzj.framework.toolkit.Check;

public class SMSContentTool {
	/**
	 * 获取产品名称
	 * @param spuModel
	 * @return
	 */
	public static String getFormatProductName(String productName) {
		if (Check.NuNString(productName) && productName.length() > 20) {
			productName = productName.substring(0, 20) + "...";
		}
		return productName;
	}

	/**
	 * 返回出游日期
	 * @param travelDate
	 * @return
	 */
	public static String gettravelDate(Date travelDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String strTravelDate = format.format(travelDate);
		return strTravelDate;
	}

	/**
	 * 返回核销时间
	 * @param consumerDate
	 * @return
	 */
	public static String getConsumerDate(Date consumerDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String strTravelDate = format.format(consumerDate);
		return strTravelDate;
	}

	//	/**
	//	 * 获取魔方码的连接地址
	//	 * @param resellerOrderId
	//	 * @param mftourCode
	//	 * @param orderCreateTime
	//	 * @return
	//	 */
	//	public static String getMfourCodeURL(String resellerOrderId, long codeId, Date orderCreateTime) {
	//		String tokenid = getTokenId(orderCreateTime);
	//		String mfoururl = SMSContent.mftourCodeUrl + "/home/view/" + codeId + "?token=" + tokenid + "&tmp=1";
	//		return ShortenUrlUtils.getShortenUrl(mfoururl);
	//	}

	/**
	 * 获取订单详情的连接地址
	 * @param orderId
	 * @param orderCreateTime
	 * @return
	 */
	public static String getContacteeOrderURL(String orderId, Date orderCreateTime) {
		String tokenid = getTokenId(orderCreateTime);
		String orderUrl = SMSContent.mftourCodeUrl + "/order/detail/" + orderId + "?token=" + tokenid + "&tmp=1";
		return ShortenUrlUtils.getShortenUrl(orderUrl);
	}

	public static String getSupplierOrderURL(String orderId, Date orderCreateTime) {
		String tokenid = getTokenId(orderCreateTime);
		String orderUrl = SMSContent.mftourCodeUrl + "/order/detailForSupplier/" + orderId + "?token=" + tokenid + "&tmp=1";
		return ShortenUrlUtils.getShortenUrl(orderUrl);
	}

	public static String getResellerOrderURL(String orderId, Date orderCreateTime) {
		String tokenid = getTokenId(orderCreateTime);
		String orderUrl = SMSContent.mftourCodeUrl + "/order/detailForDistributor/" + orderId + "?token=" + tokenid + "&tmp=1";
		return ShortenUrlUtils.getShortenUrl(orderUrl);
	}

	/**
	 * 获取令牌信息
	 * @param orderCreateTime
	 * @return
	 */
	private static String getTokenId(Date orderCreateTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createtime = format.format(orderCreateTime);
		String tokenid = MD5Utils.encrypt(createtime);
		return tokenid;
	}
}
