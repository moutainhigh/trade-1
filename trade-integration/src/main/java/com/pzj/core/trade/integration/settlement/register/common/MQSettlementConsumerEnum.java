package com.pzj.core.trade.integration.settlement.register.common;

/**
 * MQ消费端对应的topc枚举信息
 * @author kangzl
 *
 */
public enum MQSettlementConsumerEnum {
	rebateInAccountNotify("rebateInAccountNotify", "返利金到账消息topic", "afterRebateTransferredCallbackService");

	private String tagId;
	private String msg;
	private String relBeanId;

	public static final String topic = "settlement";

	private MQSettlementConsumerEnum(String tagId, String msg, String relBeanId) {
		this.relBeanId = relBeanId;
		this.msg = msg;
		this.tagId = tagId;
	}

	public String getRelBeanId() {
		return relBeanId;
	}

	public String getTagId() {
		return tagId;
	}

	public String getMsg() {
		return msg;
	}

}
