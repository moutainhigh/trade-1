package com.pzj.core.trade.sms.engine.handle;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.sku.common.constants.SkuRuleGLobal;
import com.pzj.core.trade.sms.engine.common.SmsSendTypeEnum;
import com.pzj.core.trade.sms.engine.convert.SMSContacteeContentConvert;
import com.pzj.core.trade.sms.engine.convert.SendSMSAroundConvert;
import com.pzj.core.trade.sms.engine.convert.SendSMSRuleConvert;
import com.pzj.core.trade.sms.engine.model.SMSSendModel;
import com.pzj.core.trade.sms.engine.model.SendSMSRuleModel;
import com.pzj.core.trade.sms.engine.tools.SMSContentTool;
import com.pzj.core.trade.voucher.read.VoucherDockInfoEntityReadMapper;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.voucher.engine.VoucherBusiness;
import com.pzj.voucher.entity.VoucherDockInfoEntity;

/**
 * 订单二次确认后发送短信的控制器
 * @author kangzl
 *
 */
@Component("orderConfirmSMSHandle")
public class OrderConfirmSMSHandle {
	private final static Logger logger = LoggerFactory.getLogger(OrderConfirmSMSHandle.class);
	@Autowired
	private MerchWriteMapper merchWriteMapper;
	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private SendSMSRuleConvert sendSMSRuleConvert;
	@Autowired
	private SendSMSAroundConvert sendSMSAroundConvert;

	@Autowired
	private VoucherBusiness voucherBusiness;

	@Autowired
	private VoucherDockInfoEntityReadMapper voucherDockInfoEntityReadMapper;

	@Autowired
	private PaymentSMSHandle paymentSMSHandle;

	/**
	 * 发送订单二次确认后的短信
	 * @param saleOrderId
	 */
	public void sendOrderConfirmMsg(final String saleOrderId) {
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
			//			sendResellerSMS(productName, orderEntity);
		} catch (final Exception e) {
			logger.error("OrderConfirmSMSHandle.sendOrderConfirmMsg is error!,param:" + saleOrderId + ",errorContent:", e);
		}
	}

	private void sendContactSMS(final String productName, final OrderEntity saleOrderEntity, final Map<Long, SendSMSRuleModel> ruleCache, final long rootSkuId) {
		if (saleOrderEntity.getIs_dock() == 1) {
			final VoucherDockInfoEntity voucherDockInfo = voucherDockInfoEntityReadMapper.queryVoucherDockInfoEntityByTransactionId(saleOrderEntity
					.getTransaction_id());
			if (!Check.NuNObject(voucherDockInfo) && !Check.NuNString(voucherDockInfo.getAuxiliaryCode())) {
				//				final String resellerPhone = sendSMSAroundConvert.getResellerPhone(saleOrderEntity.getReseller_id());
				final String supplierPhone = sendSMSAroundConvert.getSupplierPhone(saleOrderEntity.getSupplier_id());
				final String contacteeMsg = SMSContacteeContentConvert.OrderVoucherContentSMS
						.getOrderDockMsg(voucherDockInfo.getAuxiliaryCode(), supplierPhone);
				if (sendSMSRuleConvert.checkSMSSend(ruleCache, SkuRuleGLobal.MessageTypeEnum.contractVoucherMsg.getValue())) {
					final SMSSendModel sendModel = new SMSSendModel();
					sendModel.setMsg(contacteeMsg);
					sendModel.setSaleOrderId(saleOrderEntity.getOrder_id());
					sendModel.setPhoneNum(saleOrderEntity.getContact_mobile());
					sendModel.setSupplierId(sendSMSAroundConvert.getSaleOrderSupplierId(saleOrderEntity));
					sendSMSAroundConvert.sendSMS(sendModel, SmsSendTypeEnum.orderContact);
				}
				return;
			}
		}
		paymentSMSHandle.sendContactSMS(productName, saleOrderEntity, ruleCache, rootSkuId);
	}

	//	private void sendResellerSMS(final String productName, final OrderEntity saleOrderEntity) {
	//		paymentSMSHandle.sendResellerSMS(productName, saleOrderEntity);
	//	}
}
