package com.pzj.core.trade.sms.engine.handle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.sku.common.constants.SkuRuleGLobal;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.write.RefundApplyWriteMapper;
import com.pzj.core.trade.sms.engine.common.SmsSendTypeEnum;
import com.pzj.core.trade.sms.engine.convert.SMSContacteeContentConvert;
import com.pzj.core.trade.sms.engine.convert.SendSMSAroundConvert;
import com.pzj.core.trade.sms.engine.convert.SendSMSRuleConvert;
import com.pzj.core.trade.sms.engine.model.MerchStandardModel;
import com.pzj.core.trade.sms.engine.model.SMSSendModel;
import com.pzj.core.trade.sms.engine.model.SendSMSRuleModel;
import com.pzj.core.trade.sms.engine.tools.SMSContentTool;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.merch.write.MerchRefundFlowWriteMapper;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.common.SalePortEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;

/**
 * 退款成功后发送短信的控制器
 * @author kangzl
 *
 */
@Component("refundSMSHandle")
public class RefundSMSHandle {

	private final static Logger logger = LoggerFactory.getLogger(RefundSMSHandle.class);
	@Autowired
	private MerchRefundFlowWriteMapper merchRefundFlowWriteMapper;
	@Autowired
	private RefundApplyWriteMapper refundApplyWriteMapper;
	@Autowired
	private SendSMSRuleConvert sendSMSRuleConvert;
	@Autowired
	private SendSMSAroundConvert sendSMSAroundConvert;
	@Autowired
	private OrderWriteMapper orderWriteMapper;
	@Autowired
	private MerchWriteMapper merchWriteMapper;

	public void sendRefundSMS(final String refundId) {
		try {
			final List<RefundFlowEntity> refundFlows = merchRefundFlowWriteMapper.queryRefundFlowsByRefundId(refundId);
			final List<MerchEntity> merches = new ArrayList<MerchEntity>();
			final Map<Long, SendSMSRuleModel> ruleCache = new HashMap<Long, SendSMSRuleModel>();
			final List<MerchStandardModel> standards = getStatardModels(refundFlows, merches, ruleCache);
			final MerchEntity baseMerch = merches.get(0);
			final OrderEntity saleOrder = getSaleOrder(merches);
			final String productName = getProductName(baseMerch.getProduct_id(), ruleCache);
			final RefundApplyEntity refundApply = refundApplyWriteMapper.getRefundApplyEntityByRefundId(refundId);
			//订单联系人发送短信
			sendContacteeMsg(productName, saleOrder, ruleCache, standards, refundApply.getIsParty(), refundApply.getInitParty());
			//供应商发送短信
			//			sendSupplierMsg(productName, saleOrder, ruleCache, standards, refundApply.getInitParty());
			//分销商发送短信
			//			sendResellerMsg(productName, saleOrder, refundApply.getInitParty());
		} catch (final Exception e) {
			logger.error("call RefundSMSHandle.sendRefundSMS sendParm:" + refundId + ",errorContent:", e);
		}
	}

	/**
	 * 发送订单联系人短信
	 * @param saleOrder
	 * @param ruleCache
	 * @param isTotalRefund
	 * @param refundSource
	 */
	protected void sendContacteeMsg(final String productName, final OrderEntity saleOrder, final Map<Long, SendSMSRuleModel> ruleCache,
			final List<MerchStandardModel> standarModels, final int isTotalRefund, final int refundSource) {
		//验证是否发送短信
		if (!sendSMSRuleConvert.checkSMSSend(ruleCache, SkuRuleGLobal.MessageTypeEnum.contractConfirmMsg.getValue())) {
			return;
		}
		String contacteeMsg = null;
		final String resellerPhone = sendSMSAroundConvert.getResellerPhone(saleOrder.getReseller_id(), saleOrder);
		//判断是否是微店订单
		if (SalePortEnum.isMicShop(saleOrder.getSale_port())) {
			//分销商发起的退款
			if (refundSource == 1) {
				//整单退款
				if (isTotalRefund == 0) {
					contacteeMsg = SMSContacteeContentConvert.OrderRefundContentSMS.getResellerTotalRefundMicroshoptMsg(productName, resellerPhone);
				} else {
					contacteeMsg = SMSContacteeContentConvert.OrderRefundContentSMS.getResellerPartRefundMicroshoptMsg(productName, resellerPhone,
							standarModels);
				}
				//如果是二次确认拒绝操作发生的退款
			} else if (refundSource == 3) {
				contacteeMsg = SMSContacteeContentConvert.OrderRefundContentSMS.getRefuseConfirmMicroshopMsg(productName, resellerPhone);
			}
		} else {
			//分销商发起的退款
			if (refundSource == 1) {
				//整单退款
				if (isTotalRefund == 0) {
					contacteeMsg = SMSContacteeContentConvert.OrderRefundContentSMS.getResellerTotalRefundDefaultMsg(productName, resellerPhone);
				} else {
					contacteeMsg = SMSContacteeContentConvert.OrderRefundContentSMS.getResellerPartRefundDefaultMsg(productName, resellerPhone, standarModels);
				}
				//如果是二次确认拒绝操作发生的退款
			} else if (refundSource == 3) {
				contacteeMsg = SMSContacteeContentConvert.OrderRefundContentSMS.getRefuseConfirmDefaultMsg(productName, resellerPhone);
			}
		}
		final SMSSendModel sendModel = new SMSSendModel();
		sendModel.setMsg(contacteeMsg);
		sendModel.setPhoneNum(saleOrder.getContact_mobile());
		sendModel.setSaleOrderId(saleOrder.getOrder_id());
		sendModel.setSupplierId(sendSMSAroundConvert.getSaleOrderSupplierId(saleOrder));
		sendSMSAroundConvert.sendSMS(sendModel, SmsSendTypeEnum.orderContact);
	}

	//	/**
	//	 * 供应商发送短信
	//	 * @param productName
	//	 * @param saleOrder
	//	 * @param ruleCache
	//	 * @param standarModels
	//	 * @param isTotalRefund
	//	 * @param refundSource
	//	 */
	//	protected void sendSupplierMsg(final String productName, final OrderEntity saleOrder, final Map<Long, SendSMSRuleModel> ruleCache,
	//			final List<MerchStandardModel> standarModels, final int refundSource) {
	//		//验证是否发送短信
	//		if (!sendSMSRuleConvert.checkSMSSend(ruleCache, SkuRuleGLobal.MessageTypeEnum.supplierMsg.getValue())) {
	//			return;
	//		}
	//		String suplierMsg = null;
	//		//分销商发起的退款
	//		if (refundSource == 1) {
	//			//整单退款
	//			final OrderEntity supplierOrder = orderWriteMapper.getOrderListByPorderId(saleOrder.getOrder_id());
	//			//OrderEntity supplierOrder = null;
	//			//for (OrderEntity buyerOrder : buyerOrders) {
	//			//				if (buyerOrder.getOrder_id().equals(saleOrder.getOrder_id())) {
	//			//					continue;
	//			//				}
	//			//supplierOrder = buyerOrder;
	//			//break;
	//			//}
	//			final String orderURL = SMSContentTool.getSupplierOrderURL(supplierOrder.getOrder_id(), supplierOrder.getCreate_time());
	//			suplierMsg = SMSSupplierContentConvert.OrderNotifyMsg.getRefundMsg(productName, standarModels, orderURL);
	//			final String supllierPhone = sendSMSAroundConvert.getSupplierPhone(supplierOrder.getSupplier_id());
	//			//供应商联系人电话
	//			final List<String> supplierContactee = sendSMSAroundConvert.getSupplierContacteePhone(supplierOrder.getSupplier_id());
	//			supplierContactee.add(supllierPhone);
	//			for (final String phoneNum : supplierContactee) {
	//				final SMSSendModel sendModel = new SMSSendModel();
	//				sendModel.setMsg(suplierMsg);
	//				sendModel.setPhoneNum(phoneNum);
	//				sendModel.setSaleOrderId(supplierOrder.getOrder_id());
	//				sendSMSAroundConvert.sendSMS(sendModel, SmsSendTypeEnum.orderSupplier);
	//			}
	//		}
	//
	//	}

	//	/**
	//	 * 发送分销商短信
	//	 */
	//	protected void sendResellerMsg(final String productName, final OrderEntity saleOrder, final int refundSource) {
	//		//如果是二次确认拒绝,并且订单不是由微店端口进行的下单向分销商发送短信
	//		if (refundSource == 3 && !SalePortEnum.isMicShop(saleOrder.getSale_port())) {
	//			final String resellerPhone = sendSMSAroundConvert.getResellerPhone(saleOrder.getReseller_id());
	//			final String supplierPhone = sendSMSAroundConvert.getSupplierPhone(sendSMSAroundConvert.getSaleOrderSupplierId(saleOrder));
	//			final String resellerMsg = SMSResellerContentConvert.OrderRefundSMS.getRefuseConfirmOfPCAndAPP(productName, saleOrder.getOrder_id(),
	//					saleOrder.getContact_mobile(), supplierPhone);
	//			final SMSSendModel sendModel = new SMSSendModel();
	//			sendModel.setMsg(resellerMsg);
	//			sendModel.setPhoneNum(resellerPhone);
	//			sendModel.setSaleOrderId(saleOrder.getOrder_id());
	//			sendSMSAroundConvert.sendSMS(sendModel, SmsSendTypeEnum.orderSupplier);
	//		}
	//	}

	/**
	 * 获取销售订单
	 * @param merches
	 * @return
	 */
	protected OrderEntity getSaleOrder(final List<MerchEntity> merches) {
		String saleOrderId = null;
		for (final MerchEntity merch : merches) {
			if (!merch.getMerch_id().equals(merch.getRoot_merch_id())) {
				continue;
			}
			saleOrderId = merch.getOrder_id();
			break;
		}
		final OrderEntity saleOrder = orderWriteMapper.getOrderEntityNotLock(saleOrderId);
		return saleOrder;
	}

	/**
	 * 获取产品名称
	 * @param merches
	 * @return
	 */
	protected String getProductName(final long skuid, final Map<Long, SendSMSRuleModel> ruleCache) {
		final String productName = SMSContentTool.getFormatProductName(ruleCache.get(skuid).getProdcutName());
		return productName;
	}

	protected List<MerchStandardModel> getStatardModels(final List<RefundFlowEntity> flows, final List<MerchEntity> merches,
			final Map<Long, SendSMSRuleModel> ruleCache) {
		final List<MerchStandardModel> models = new ArrayList<MerchStandardModel>();
		for (final RefundFlowEntity flow : flows) {
			final MerchEntity merch = merchWriteMapper.getMerchByMerchId(flow.getMerch_id());
			merches.add(merch);
			final long skuid = merch.getProduct_id();
			final SendSMSRuleModel ruleModel = sendSMSRuleConvert.convert(skuid);
			if (Check.NuNObject(ruleCache.get(skuid))) {
				ruleCache.put(skuid, ruleModel);
			}
			//判断退款流水是否是对应的供应商
			if (!merch.getMerch_id().equals(merch.getRoot_merch_id())) {
				continue;
			}
			final MerchStandardModel model = new MerchStandardModel();
			model.setStandardName(ruleModel.getSkuName());
			model.setStandardNum(flow.getRefund_num());
			models.add(model);
		}
		return models;
	}
}
