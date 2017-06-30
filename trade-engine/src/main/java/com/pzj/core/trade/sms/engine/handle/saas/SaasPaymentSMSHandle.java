package com.pzj.core.trade.sms.engine.handle.saas;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.sms.engine.convert.SendSMSAroundConvert;
import com.pzj.core.trade.sms.engine.convert.SendSMSRuleConvert;
import com.pzj.core.trade.sms.engine.handle.PaymentSMSHandle;
import com.pzj.core.trade.sms.engine.model.SendSMSRuleModel;
import com.pzj.core.trade.sms.engine.tools.SMSContentTool;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;

@Component("saasPaymentSMSHandle")
public class SaasPaymentSMSHandle extends PaymentSMSHandle {

	private final static Logger logger = LoggerFactory.getLogger(SaasPaymentSMSHandle.class);

	@Autowired
	private OrderWriteMapper orderWriteMapper;
	@Autowired
	private MerchWriteMapper merchWriteMapper;
	@Autowired
	private SendSMSRuleConvert sendSMSRuleConvert;
	@Autowired
	private SendSMSAroundConvert sendSMSAroundConvert;

	@Override
	public void sendPaymentSMS(final String saleOrderId) {
		try {
			final OrderEntity orderEntity = orderWriteMapper.getOrderEntityNotLock(saleOrderId);
			final List<MerchEntity> merches = merchWriteMapper.getMerchByOrderId(saleOrderId);
			//产品id
			final MerchEntity defaultMerch = merches.get(0);
			final Map<Long, SendSMSRuleModel> ruleCache = sendSMSRuleConvert.getRuleCache(merches);
			//产品ID
			final long rootSkuid = merches.get(0).getProduct_id();
			final String productName = SMSContentTool.getFormatProductName(ruleCache.get(defaultMerch.getProduct_id()).getProdcutName());
			//发送联系人短信
			sendContactSMS(productName, orderEntity, ruleCache, rootSkuid);
			//发送供应商短信
			//			sendSupplierSMS(productName, orderEntity, ruleCache);
			//发送分销商短信
			//			sendResellerSMS(productName, orderEntity);
		} catch (final Exception e) {
			logger.error("call PaymentSMSHandle.sendPaymentSMS reqParam:" + saleOrderId + ",errorContent:", e);
		}
	}

	//	/**
	//	 * 向供应商发送短信
	//	 * @param orderEntity
	//	 * @param merches
	//	 * @param ruleCache
	//	 */
	//	@Override
	//	protected void sendSupplierSMS(final String productName, final OrderEntity saleOrderEntity, final Map<Long, SendSMSRuleModel> ruleCache) {
	//		if (!sendSMSRuleConvert.checkSMSSend(ruleCache, SkuRuleGLobal.MessageTypeEnum.supplierMsg.getValue())) {
	//			return;
	//		}
	//		final OrderEntity buyerOrder = orderWriteMapper.getInitialSupplierOrderByTransactionId(saleOrderEntity.getOrder_id());
	//		String suplierMsg = null;
	//		final List<MerchEntity> buyerMerches = merchWriteMapper.getMerchByOrderId(buyerOrder.getOrder_id());
	//		final List<MerchStandardModel> standards = getOrderStatndar(buyerMerches, ruleCache);
	//		final String orderURL = SMSContentTool.getSupplierOrderURL(buyerOrder.getOrder_id(), buyerOrder.getCreate_time());
	//		//分销商电话
	//		final String resellerPhone = sendSMSAroundConvert.getResellerPhone(buyerOrder.getReseller_id());
	//		//如果订单不是需要二次确认的订单
	//		if (saleOrderEntity.getNeed_confirm() == NeedConfirmEnum.NOT_NEED.getValue()) {
	//			String travelDate = null;
	//			//当前订单只有1个出游日期
	//			final MerchEntity baseMerch = buyerMerches.get(0);
	//			if (sendSMSRuleConvert.checkIsNeedPaytime(baseMerch.getProduct_id())) {
	//				travelDate = SMSContentTool.gettravelDate(baseMerch.getStart_time());
	//			}
	//			suplierMsg = SMSSupplierContentConvert.OrderNotifyMsg.getNotityMsgDefault(productName, travelDate, standards, orderURL, resellerPhone);
	//		} else {
	//			final double makeMoney = getSupplierMakeMoeny(buyerMerches, buyerOrder, saleOrderEntity.getSale_port());
	//			suplierMsg = SMSSupplierContentConvert.OrderNotifyMsg.getNotifiyMsgNeedConfirm(productName, makeMoney, buyerOrder.getOrder_id(), orderURL,
	//					resellerPhone);
	//		}
	//		//供应商电话
	//		final String supllierPhone = sendSMSAroundConvert.getSupplierPhone(buyerOrder.getSupplier_id());
	//		//供应商联系人电话
	//		final List<String> supplierContactee = sendSMSAroundConvert.getSupplierContacteePhone(buyerOrder.getSupplier_id());
	//		supplierContactee.add(supllierPhone);
	//		for (final String phoneNum : supplierContactee) {
	//			final SMSSendModel sendModel = new SMSSendModel();
	//			sendModel.setMsg(suplierMsg);
	//			sendModel.setPhoneNum(phoneNum);
	//			sendModel.setSaleOrderId(saleOrderEntity.getOrder_id());
	//			sendSMSAroundConvert.sendSMS(sendModel, SmsSendTypeEnum.orderSupplier);
	//		}
	//	}
}
