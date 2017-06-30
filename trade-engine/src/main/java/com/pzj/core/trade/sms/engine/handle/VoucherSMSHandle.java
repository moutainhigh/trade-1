package com.pzj.core.trade.sms.engine.handle;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.product.common.model.response.SpuProductOutModel;
import com.pzj.core.sku.common.constants.SkuProductGlobal.VerificationTypeEnum;
import com.pzj.core.sku.common.constants.SkuRuleGLobal;
import com.pzj.core.trade.sms.engine.common.SmsSendTypeEnum;
import com.pzj.core.trade.sms.engine.convert.SMSContacteeContentConvert;
import com.pzj.core.trade.sms.engine.convert.SendSMSAroundConvert;
import com.pzj.core.trade.sms.engine.convert.SendSMSRuleConvert;
import com.pzj.core.trade.sms.engine.exception.SMSException;
import com.pzj.core.trade.sms.engine.model.SMSSendModel;
import com.pzj.core.trade.sms.engine.model.SendSMSRuleModel;
import com.pzj.core.trade.sms.engine.tools.SMSContentTool;
import com.pzj.core.trade.voucher.read.VoucherDockInfoEntityReadMapper;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.common.VourTypeEnum;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.entity.MftourCodeEntity;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.MftourCodeWriteMapper;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.voucher.engine.common.ProductType;
import com.pzj.voucher.entity.VoucherDockInfoEntity;

@Component("voucherSMSHandle")
public class VoucherSMSHandle {
	private final static Logger logger = LoggerFactory.getLogger(VoucherSMSHandle.class);
	@Autowired
	private OrderWriteMapper orderWriteMapper;
	@Autowired
	private MerchWriteMapper merchWriteMapper;
	@Autowired
	private SendSMSRuleConvert sendSMSRuleConvert;
	@Autowired
	private SendSMSAroundConvert sendSMSAroundConvert;

	@Autowired
	private VoucherDockInfoEntityReadMapper voucherDockInfoEntityReadMapper;

	@Autowired
	private MftourCodeWriteMapper mftourCodeWriteMapper;

	@Resource(name = "sMSTemplateHandler")
	private SMSTemplateHandler sMSTemplateHandler;

	public void setSMS(final String saleOrderId) {
		try {
			final OrderEntity orderEntity = orderWriteMapper.getOrderEntityNotLock(saleOrderId);
			final List<MerchEntity> merches = merchWriteMapper.getMerchByOrderId(saleOrderId);
			//产品id
			final MerchEntity defaultMerch = merches.get(0);
			final Map<Long, SendSMSRuleModel> ruleCache = sendSMSRuleConvert.getRuleCache(merches);

			//订单商品的核销凭证信息
			final int vourType = merches.get(0).getVour_type();
			//产品类型
			final int skuType = merches.get(0).getMerch_type();
			//产品ID
			final long rootSkuid = merches.get(0).getProduct_id();
			final String productName = SMSContentTool.getFormatProductName(ruleCache.get(defaultMerch.getProduct_id()).getProdcutName());
			//发送联系人短信
			sendContactSMS(productName, orderEntity, ruleCache, rootSkuid, skuType, vourType);
		} catch (final SMSException e) {
			logger.error("call VoucherSMSHandle.setSMS reqParam:" + saleOrderId + ",errorContent:", e);
			throw e;
		}
	}

	private void sendContactSMS(final String productName, final OrderEntity saleOrderEntity, final Map<Long, SendSMSRuleModel> ruleCache, final long rootSkuid,
			final int skuType, final int vourType) {

		//判断是否发送凭证短信,如果产品上订单的为不发送凭证短信，不触发后续流程
		if (!sendSMSRuleConvert.checkSMSSend(ruleCache, SkuRuleGLobal.MessageTypeEnum.contractVoucherMsg.getValue())) {
			return;
		}

		String contacteeSms = null;
		final String supllierPhone = sendSMSAroundConvert.getSupplierPhone(sendSMSAroundConvert.getSaleOrderSupplierId(saleOrderEntity));
		final String resellerPhone = sendSMSAroundConvert.getResellerPhone(saleOrderEntity.getReseller_id(), saleOrderEntity);
		final String contactOrderURL = SMSContentTool.getContacteeOrderURL(saleOrderEntity.getOrder_id(), saleOrderEntity.getCreate_time());
		//订单属于对接系统的订单
		if (saleOrderEntity.getIs_dock() == 1) {
			final VoucherDockInfoEntity voucherDockInfo = voucherDockInfoEntityReadMapper.queryVoucherDockInfoEntityByTransactionId(saleOrderEntity
					.getTransaction_id());
			//判断是否存在第三方凭证,如果存在发送第三方凭证短信,否则发送魔方通用短信
			if (!Check.NuNObject(voucherDockInfo) && !Check.NuNString(voucherDockInfo.getAuxiliaryCode())) {
				if (sendSMSRuleConvert.checkSMSSend(ruleCache, SkuRuleGLobal.MessageTypeEnum.contractVoucherMsg.getValue())) {
					sendDockVoucherSMS(saleOrderEntity, voucherDockInfo, supllierPhone);
				}
				return;
			}
		}

		//判断当前产品是否是景区产品
		if (ProductType.isScenicProduct(skuType)) {
			final SpuProductOutModel spuModel = sendSMSRuleConvert.getSpuModel(rootSkuid);
			if (!Check.NuNObject(spuModel) && !Check.NuNObject(spuModel.getSpuProductExtendOutModel())
					&& !Check.NuNString(spuModel.getSpuProductExtendOutModel().getMessageTemplate())) {
				try {
					sMSTemplateHandler.sendSMS(saleOrderEntity.getTransaction_id(), saleOrderEntity.getOrder_id(), spuModel.getSpuProductExtendOutModel()
							.getMessageTemplate());
				} catch (final Exception e) {
					logger.error("调用原有的voucher发送短信的接口异常,orderId:" + saleOrderEntity.getOrder_id() + ",errorContent:", e);
				}
				return;
			}
		}

		//判断订单是否是二维码订单
		if (vourType != VourTypeEnum.MFCODE.getVourType()) {
			contacteeSms = SMSContacteeContentConvert.OrderVoucherContentSMS.getDefulatMsg(productName, contactOrderURL, resellerPhone);
		} else if (vourType == VourTypeEnum.MFCODE.getVourType()) {

			SendSMSRuleModel defualtModel = ruleCache.get(rootSkuid);
			if (VerificationTypeEnum.VERIFICATION_BY_ORDER.getValue() == defualtModel.getVerificationType()) {
				//获取订单对应的魔方码
				final List<MftourCodeEntity> mftourCodes = mftourCodeWriteMapper.getMftourCodeByTransactionId(saleOrderEntity.getTransaction_id());
				//目前来说1个订单只有1个魔方码saleOrderEntity
				final String mfcode = mftourCodes.get(0).getMf_code();
				//				final String mfcodeUrl = SMSContentTool.getMfourCodeURL(saleOrder.getOrder_id(), mftourCodes.get(0).getCode_id(), saleOrder.getCreate_time());

				contacteeSms = SMSContacteeContentConvert.OrderVoucherContentSMS.getOrderMfcodeMsg(productName, supllierPhone, mfcode, contactOrderURL,
						resellerPhone);
			} else {
				contacteeSms = SMSContacteeContentConvert.OrderVoucherContentSMS.getOrderOnePersonOneMfcodeMsg(productName, contactOrderURL);
			}
		}
		final SMSSendModel sendModel = new SMSSendModel();
		sendModel.setMsg(contacteeSms);
		sendModel.setSaleOrderId(saleOrderEntity.getOrder_id());
		sendModel.setPhoneNum(saleOrderEntity.getContact_mobile());
		sendModel.setSupplierId(sendSMSAroundConvert.getSaleOrderSupplierId(saleOrderEntity));
		if (sendSMSRuleConvert.checkSMSSend(ruleCache, SkuRuleGLobal.MessageTypeEnum.contractVoucherMsg.getValue())) {
			sendSMSAroundConvert.sendSMS(sendModel, SmsSendTypeEnum.orderContact);
		}
	}

	private void sendDockVoucherSMS(final OrderEntity saleOrderEntity, final VoucherDockInfoEntity voucherDockInfo, final String supplierPhone) {
		final String contacteeMsg = SMSContacteeContentConvert.OrderVoucherContentSMS.getOrderDockMsg(voucherDockInfo.getAuxiliaryCode(), supplierPhone);
		final SMSSendModel sendModel = new SMSSendModel();
		sendModel.setMsg(contacteeMsg);
		sendModel.setSaleOrderId(saleOrderEntity.getOrder_id());
		sendModel.setPhoneNum(saleOrderEntity.getContact_mobile());
		sendModel.setSupplierId(sendSMSAroundConvert.getSaleOrderSupplierId(saleOrderEntity));
		sendSMSAroundConvert.sendSMS(sendModel, SmsSendTypeEnum.orderContact);
	}
}
