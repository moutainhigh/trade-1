package com.pzj.trade.payment.vo;

/**
 * 订单付款参数.
 * 
 * @author YRJ
 *
 */
public class PaymentVO extends ChildOrderPaymentModel {
	private static final long serialVersionUID = 1L;
	/** 支付方式. 当采用支付宝、微信付款时, 需要传递此值为1: 支付宝 或 2: 微信. 其他情况可暂时不传. */
	private int payType;
	/** 币种. 1: 人民币; 2: 美元, 默认为1. (必传)*/
	private int currency = 1;
	/** 请求来源, 用于判断本次请求是由原生APP发起还是由HTML(H5、PC)页面发起, 用于决定调用支付宝或微信的不同付款接口  (必传)*/
	private int source;
	/** 同步回调地址  (非必传)*/
	private String returnUrl;
	/** 支付失败同步回调地址  (非必传)*/
	private String failpayUrl;
	/** 前台页面授权RID (非必传)*/
	private String rid;
	/** 前台页面授权SID (非必传)*/
	private String sid;
	/** 微信客户端ID (非必传)*/
	private String appId;
	/** 微信私有串  (非必传)*/
	private String appSecret;

	/**
	 * 支付方式. 当采用支付宝、微信付款时, 需要传递此值为1: 支付宝 或 2: 微信. 其他情况可暂时不传.
	 * <ul>
	 * <li>0: 余额支付</li>
	 * <li>1: 支付宝</li>
	 * <li>2: 微信</li>
	 * </ul>
	 */
	public int getPayType() {
		return payType;
	}

	/**
	 * 支付方式, 当采用支付宝、微信付款时, 需要传递此值为1: 支付宝 或 2: 微信. 其他情况可暂时不传.
	 * <ul>
	 * <li>0: 余额支付</li>
	 * <li>1: 支付宝</li>
	 * <li>2: 微信</li>
	 * </ul>
	 */
	public void setPayType(final int payType) {
		this.payType = payType;
	}

	/**
	 * 币种. 1: 人民币; 2: 美元, 默认为1.
	 * 
	 * @return
	 */
	public int getCurrency() {
		return currency;
	}

	/**
	 * 币种. 1: 人民币; 2: 美元, 默认为1.
	 * 
	 * @param currency
	 */
	public void setCurrency(final int currency) {
		this.currency = currency;
	}

	/**
	 * 请求来源, 用于判断本次请求是由原生APP发起还是由HTML(H5、PC)页面发起, 用于决定调用支付宝或微信的不同付款接口
	 * <ul>
	 * <li>1: html页面</li>
	 * <li>2: 移动应用</li>
	 * </ul>
	 * 
	 * @return
	 */
	public int getSource() {
		return source;
	}

	/**
	 * 请求来源, 用于判断本次请求是由原生APP发起还是由HTML(H5、PC)页面发起, 用于决定调用支付宝或微信的不同付款接口
	 * <ul>
	 * <li>1: html页面</li>
	 * <li>2: 移动应用</li>
	 * </ul>
	 * 
	 * @return
	 */
	public void setSource(final int source) {
		this.source = source;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(final String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(final String appSecret) {
		this.appSecret = appSecret;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(final String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getFailpayUrl() {
		return failpayUrl;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(final String rid) {
		this.rid = rid;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public void setFailpayUrl(final String failpayUrl) {
		this.failpayUrl = failpayUrl;
	}

	@Override
	public String toString() {
		return "PaymentVO [resellerId=" + getResellerId() + ", orderId=" + getOrderId() + ", payType=" + payType
				+ ", currency=" + currency + ", source=" + source + ", returnUrl=" + returnUrl + ", failpayUrl=" + failpayUrl
				+ ", rid=" + rid + ", appId=" + appId + ", appSecret=" + appSecret + "]";
	}
}
