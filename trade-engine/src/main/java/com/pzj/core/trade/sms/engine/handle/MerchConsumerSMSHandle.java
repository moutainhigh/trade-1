package com.pzj.core.trade.sms.engine.handle;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.sku.common.constants.SkuProductTypeGlobal;
import com.pzj.core.sku.common.constants.SkuRuleGLobal;
import com.pzj.core.trade.sms.engine.common.SmsSendTypeEnum;
import com.pzj.core.trade.sms.engine.convert.SMSContacteeContentConvert;
import com.pzj.core.trade.sms.engine.convert.SendSMSAroundConvert;
import com.pzj.core.trade.sms.engine.convert.SendSMSRuleConvert;
import com.pzj.core.trade.sms.engine.model.SMSSendModel;
import com.pzj.core.trade.sms.engine.model.SendSMSRuleModel;
import com.pzj.core.trade.sms.engine.tools.SMSContentTool;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.DeliveryWriteMapper;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.voucher.engine.VoucherBusiness;

/**
 * 商品核销后发送短信的控制器
 * @author kangzl
 *
 */
@Component("merchConsumerSMSHandle")
public class MerchConsumerSMSHandle {
	private final static Logger logger = LoggerFactory.getLogger(MerchConsumerSMSHandle.class);

	@Autowired
	private OrderWriteMapper orderWriteMapper;
	@Autowired
	private MerchWriteMapper merchWriteMapper;
	@Autowired
	private VoucherBusiness voucherBusiness;
	@Autowired
	private SendSMSRuleConvert sendSMSRuleConvert;
	@Autowired
	private SendSMSAroundConvert sendSMSAroundConvert;
	@Autowired
	private DeliveryWriteMapper deliveryWriteMapper;

	/**
	 * 商品核销后发送短信
	 * @param saleOrderId
	 */
	public void sendConsumerSMS(long voucherId) {
		try {
			List<MerchEntity> merches = merchWriteMapper.getMerchByVoucherId(voucherId);
			Map<Long, SendSMSRuleModel> ruleCache = sendSMSRuleConvert.getRuleCache(merches);
			if (!sendSMSRuleConvert.checkSMSSend(ruleCache, SkuRuleGLobal.MessageTypeEnum.contractConfirmMsg.getValue())) {
				return;
			}
			int vourType = merches.get(0).getVour_type();
			int merchType = merches.get(0).getMerch_type();
			long skuId = merches.get(0).getProduct_id();
			String productName = SMSContentTool.getFormatProductName(ruleCache.get(skuId).getProdcutName());
			sendContacteeSMS(productName, merches, merchType, vourType);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("call MerchConsumerSMSHandle.sendConsumerSMS sendParam:" + voucherId + ",errorContent:", e);
		}
	}

	/**
	 * 向订单联系人发送短信(只发送特产发货的短信)
	 * @param productName
	 * @param merches
	 * @param merchType
	 * @param vourtype
	 */
	private void sendContacteeSMS(String productName, List<MerchEntity> merches, int merchType, int vourtype) {
		String contacteeMsg = null;
		OrderEntity saleOrder = getSaleOrderEntity(merches);
		//		final String resellerPhone = sendSMSAroundConvert.getResellerPhone(saleOrder.getReseller_id());
		//特产
		if (merchType == SkuProductTypeGlobal.nativeProduct) {
			final String orderURL = SMSContentTool.getContacteeOrderURL(saleOrder.getOrder_id(), saleOrder.getCreate_time());
			//			String buyerOrderId = getSupplierOrderId(merches);
			//			DeliveryDetailEntity deliveryDetail = deliveryWriteMapper.getDeliveryByOrderIdNolock(buyerOrderId);
			final String resellerPhone = sendSMSAroundConvert.getResellerPhone(saleOrder.getReseller_id(), saleOrder);
			//			if (!Check.NuNObject(deliveryDetail)) {
			//				if (DeliveryWay.EXPRESS_SERVICE.getKey().intValue() == deliveryDetail.getDeliveryWay()) {
			//					contacteeMsg = SMSContacteeContentConvert.OrderConsumerContentSMS.getDeliveryDefaultMsg(productName, deliveryDetail.getExpressCompany(),
			//							deliveryDetail.getExpressNO(), orderURL);
			//				}
			//			}
			//			if (Check.NuNString(contacteeMsg)) {
			//				contacteeMsg = SMSContacteeContentConvert.OrderConsumerContentSMS.getDeliveryOwnerPickMsg(productName, resellerPhone, orderURL);
			//			}
			contacteeMsg = SMSContacteeContentConvert.OrderConsumerContentSMS.getDeliveryOwnerPickMsg(productName, resellerPhone, orderURL);
		}
		//		if (vourtype == VourTypeEnum.MFCODE.getVourType()) {
		//			String consumerDate = SMSContentTool.getConsumerDate(new Date());
		//			contacteeMsg = SMSContacteeContentConvert.OrderConsumerContentSMS.getMfcodeConsumerMsg(productName, consumerDate, resellerPhone);
		//		}
		else {
			return;
		}
		SMSSendModel sendModel = new SMSSendModel();
		sendModel.setMsg(contacteeMsg);
		sendModel.setSaleOrderId(saleOrder.getOrder_id());
		sendModel.setPhoneNum(saleOrder.getContact_mobile());
		sendModel.setSupplierId(sendSMSAroundConvert.getSaleOrderSupplierId(saleOrder));
		sendSMSAroundConvert.sendSMS(sendModel, SmsSendTypeEnum.orderContact);
	}

	/**
	 * 获取商品集合中对应的采购单ID
	 * @param merch
	 * @return
	 */
	private String getSupplierOrderId(List<MerchEntity> merches) {
		String buyerOrderId = null;
		for (MerchEntity merch : merches) {
			if (merch.getMerch_id().equals(merch.getRoot_merch_id())) {
				continue;
			}
			buyerOrderId = merch.getOrder_id();
			break;
		}
		return buyerOrderId;
	}

	/**
	 * 获取销售订单信息
	 * @param buyerOrderId
	 * @return
	 */
	private OrderEntity getSaleOrderEntity(List<MerchEntity> merches) {
		String saleOrderId = null;
		for (MerchEntity merch : merches) {
			if (!merch.getMerch_id().equals(merch.getRoot_merch_id())) {
				continue;
			}
			saleOrderId = merch.getTransaction_id();
			break;
		}
		return orderWriteMapper.getOrderEntityNotLock(saleOrderId);
	}
}
