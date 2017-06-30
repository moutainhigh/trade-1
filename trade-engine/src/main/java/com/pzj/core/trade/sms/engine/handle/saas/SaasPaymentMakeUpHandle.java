package com.pzj.core.trade.sms.engine.handle.saas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.sms.engine.common.SmsSendTypeEnum;
import com.pzj.core.trade.sms.engine.convert.SendSMSAroundConvert;
import com.pzj.core.trade.sms.engine.model.SMSSendModel;
import com.pzj.trade.order.entity.OrderEntity;

@Component
public class SaasPaymentMakeUpHandle {
	@Autowired
	private SendSMSAroundConvert sendSMSAroundConvert;

	public void sendSMS(OrderEntity order, double currentOrderAmount, double makeUpAmount) {
		//		final String orderURL = SMSContentTool.getOrderURL(order.getOrder_id(), order.getCreate_time());
		StringBuffer sb = new StringBuffer("尊敬的用户您好，您的订单");
		sb.append(order.getOrder_id()).append("需要补差价，应付金额：").append(currentOrderAmount).append("元，，需补差价：").append(makeUpAmount)
				.append("请您在60分钟内登录魔方SaaS系统完成补差");
		//需要补差的人应该是本级订单的分销商，但补差的人应该获取到当前人的供应商电话信息
		long currentSupplierId = order.getReseller_id();
		//供应商电话
		final String supllierPhone = sendSMSAroundConvert.getSupplierPhone(currentSupplierId);
		//供应商联系人电话
		final List<String> supplierContactee = sendSMSAroundConvert.getSupplierContacteePhone(currentSupplierId);
		supplierContactee.add(supllierPhone);
		for (final String phoneNum : supplierContactee) {
			final SMSSendModel sendModel = new SMSSendModel();
			sendModel.setMsg(sb.toString());
			sendModel.setPhoneNum(phoneNum);
			sendModel.setSaleOrderId(order.getOrder_id());
			sendSMSAroundConvert.sendSMS(sendModel, SmsSendTypeEnum.orderSupplier);
		}
	}
}
