package com.pzj.core.trade.sms.engine.handle.saas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.write.RefundApplyWriteMapper;
import com.pzj.core.trade.sms.engine.convert.SendSMSAroundConvert;
import com.pzj.core.trade.sms.engine.convert.SendSMSRuleConvert;
import com.pzj.core.trade.sms.engine.handle.RefundSMSHandle;
import com.pzj.core.trade.sms.engine.model.MerchStandardModel;
import com.pzj.core.trade.sms.engine.model.SendSMSRuleModel;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.merch.write.MerchRefundFlowWriteMapper;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;

@Component("saasRefundSMSHandle")
public class SaasRefundSMSHandle extends RefundSMSHandle {
	private final static Logger logger = LoggerFactory.getLogger(RefundSMSHandle.class);
	@Autowired
	private MerchRefundFlowWriteMapper merchRefundFlowWriteMapper;
	@Autowired
	private RefundApplyWriteMapper refundApplyWriteMapper;
	@Autowired
	private SendSMSRuleConvert sendSMSRuleConvert;
	@Autowired
	private OrderWriteMapper orderWriteMapper;
	@Autowired
	private SendSMSAroundConvert sendSMSAroundConvert;

	@Override
	public void sendRefundSMS(String refundId) {
		try {
			RefundApplyEntity refundApply = refundApplyWriteMapper.getRefundApplyEntityByRefundId(refundId);
			List<RefundFlowEntity> refundFlows = merchRefundFlowWriteMapper.getOrderMerchRefund(refundApply.getTransactionId(), refundId);
			List<MerchEntity> merches = new ArrayList<MerchEntity>();
			Map<Long, SendSMSRuleModel> ruleCache = new HashMap<Long, SendSMSRuleModel>();
			List<MerchStandardModel> standards = getStatardModels(refundFlows, merches, ruleCache);
			MerchEntity baseMerch = merches.get(0);
			OrderEntity saleOrder = getSaleOrder(merches);
			String productName = getProductName(baseMerch.getProduct_id(), ruleCache);
			//订单联系人发送短信
			super.sendContacteeMsg(productName, saleOrder, ruleCache, standards, refundApply.getIsParty(), refundApply.getInitParty());
			//供应商发送短信
			//			this.sendSupplierMsg(productName, saleOrder, ruleCache, standards, refundApply.getInitParty());
			//分销商发送短信
			//			super.sendResellerMsg(productName, saleOrder, refundApply.getInitParty());
		} catch (Exception e) {
			logger.error("call RefundSMSHandle.sendRefundSMS sendParm:" + refundId + ",errorContent:", e);
		}
	}

	//	@Override
	//	protected void sendSupplierMsg(String productName, OrderEntity saleOrder, Map<Long, SendSMSRuleModel> ruleCache, List<MerchStandardModel> standarModels,
	//			int refundSource) {
	//		//验证是否发送短信
	//		if (!sendSMSRuleConvert.checkSMSSend(ruleCache, SkuRuleGLobal.MessageTypeEnum.supplierMsg.getValue())) {
	//			return;
	//		}
	//		String suplierMsg = null;
	//		//分销商发起的退款
	//		if (refundSource == RefundInitPartyEnum.GENERAL.getParty()) {
	//			OrderEntity supplierOrder = orderWriteMapper.getInitialSupplierOrderByTransactionId(saleOrder.getTransaction_id());
	//			String orderURL = SMSContentTool.getSupplierOrderURL(supplierOrder.getOrder_id(), supplierOrder.getCreate_time());
	//			suplierMsg = SMSSupplierContentConvert.OrderNotifyMsg.getRefundMsg(productName, standarModels, orderURL);
	//			String supllierPhone = sendSMSAroundConvert.getSupplierPhone(supplierOrder.getSupplier_id());
	//			//供应商联系人电话
	//			List<String> supplierContactee = sendSMSAroundConvert.getSupplierContacteePhone(supplierOrder.getSupplier_id());
	//			supplierContactee.add(supllierPhone);
	//			for (String phoneNum : supplierContactee) {
	//				SMSSendModel sendModel = new SMSSendModel();
	//				sendModel.setMsg(suplierMsg);
	//				sendModel.setPhoneNum(phoneNum);
	//				sendModel.setSaleOrderId(supplierOrder.getOrder_id());
	//				sendSMSAroundConvert.sendSMS(sendModel, SmsSendTypeEnum.orderSupplier);
	//			}
	//		}
	//	}
}
