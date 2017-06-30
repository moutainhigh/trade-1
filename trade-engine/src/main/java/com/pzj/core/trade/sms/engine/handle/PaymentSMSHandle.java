package com.pzj.core.trade.sms.engine.handle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.product.common.model.response.SpuProductOutModel;
import com.pzj.core.sku.common.constants.SkuProductGlobal.VerificationTypeEnum;
import com.pzj.core.sku.common.constants.SkuRuleGLobal;
import com.pzj.core.trade.context.utils.MoneyUtils;
import com.pzj.core.trade.sms.engine.common.SmsSendTypeEnum;
import com.pzj.core.trade.sms.engine.convert.SMSContacteeContentConvert;
import com.pzj.core.trade.sms.engine.convert.SendSMSAroundConvert;
import com.pzj.core.trade.sms.engine.convert.SendSMSRuleConvert;
import com.pzj.core.trade.sms.engine.model.MerchStandardModel;
import com.pzj.core.trade.sms.engine.model.SMSSendModel;
import com.pzj.core.trade.sms.engine.model.SendSMSRuleModel;
import com.pzj.core.trade.sms.engine.tools.SMSContentTool;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.common.VourTypeEnum;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.common.NeedConfirmEnum;
import com.pzj.trade.order.entity.MftourCodeEntity;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.MftourCodeWriteMapper;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.voucher.engine.common.ProductType;

/**
 * 支付成功后发送短信的控制器
 * @author kangzl
 *
 */
@Component("paymentSMSHandle")
public class PaymentSMSHandle {
	private final static Logger logger = LoggerFactory.getLogger(PaymentSMSHandle.class);

	@Autowired
	private MerchWriteMapper merchWriteMapper;
	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private MftourCodeWriteMapper mftourCodeWriteMapper;

	@Autowired
	private SendSMSRuleConvert sendSMSRuleConvert;
	@Autowired
	private SendSMSAroundConvert sendSMSAroundConvert;

	@Resource(name = "sMSTemplateHandler")
	private SMSTemplateHandler sMSTemplateHandler;

	/**
	 * 发送订单支付成功后的消息
	 * @param saleOrderId
	 */
	public void sendPaymentSMS(final String saleOrderId) {
		try {
			final OrderEntity orderEntity = orderWriteMapper.getOrderEntityNotLock(saleOrderId);
			final List<MerchEntity> merches = merchWriteMapper.getMerchByOrderId(saleOrderId);
			//产品id
			final MerchEntity defaultMerch = merches.get(0);
			final Map<Long, SendSMSRuleModel> ruleCache = sendSMSRuleConvert.getRuleCache(merches);
			//产品类型
			final int merchType = merches.get(0).getMerch_type();
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
			e.printStackTrace();
			logger.error("call PaymentSMSHandle.sendPaymentSMS reqParam:" + saleOrderId + ",errorContent:", e);
		}
	}

	protected void sendContactSMS(final String productName, final OrderEntity saleOrder, final Map<Long, SendSMSRuleModel> ruleCache, final long rootSkuid) {
		String contacteeSms = null;
		final String contactPhone = saleOrder.getContact_mobile();
		final String resellerPhone = sendSMSAroundConvert.getResellerPhone(saleOrder.getReseller_id(), saleOrder);
		final String orderURL = SMSContentTool.getContacteeOrderURL(saleOrder.getOrder_id(), saleOrder.getCreate_time());
		final String supllierPhone = sendSMSAroundConvert.getSupplierPhone(sendSMSAroundConvert.getSaleOrderSupplierId(saleOrder));

		SendSMSRuleModel defualtModel = ruleCache.get(rootSkuid);
		//如果订单不是需要进行二次确认的订单
		if (saleOrder.getNeed_confirm() != NeedConfirmEnum.NEED.getValue()
				&& sendSMSRuleConvert.checkSMSSend(ruleCache, SkuRuleGLobal.MessageTypeEnum.contractVoucherMsg.getValue())) {
			//判断当前产品是否是景区产品
			if (ProductType.isScenicProduct(defualtModel.getMerchType())) {
				final SpuProductOutModel spuModel = sendSMSRuleConvert.getSpuModel(rootSkuid);
				if (!Check.NuNObject(spuModel) && !Check.NuNObject(spuModel.getSpuProductExtendOutModel())
						&& !Check.NuNString(spuModel.getSpuProductExtendOutModel().getMessageTemplate())) {
					try {
						sMSTemplateHandler.sendSMS(saleOrder.getTransaction_id(), saleOrder.getOrder_id(), spuModel.getSpuProductExtendOutModel()
								.getMessageTemplate());
					} catch (final Exception e) {
						logger.error("调用原有的voucher发送短信的接口异常,orderId:" + saleOrder.getOrder_id() + ",errorContent:", e);
					}
					return;
				}
			}
			//判断订单是否是二维码订单
			if (defualtModel.getVourType() != VourTypeEnum.MFCODE.getVourType()) {
				contacteeSms = SMSContacteeContentConvert.OrderVoucherContentSMS.getDefulatMsg(productName, resellerPhone, orderURL);
			} else if (defualtModel.getVourType() == VourTypeEnum.MFCODE.getVourType()) {

				if (VerificationTypeEnum.VERIFICATION_BY_ORDER.getValue() == defualtModel.getVerificationType()) {
					//获取订单对应的魔方码
					final List<MftourCodeEntity> mftourCodes = mftourCodeWriteMapper.getMftourCodeByTransactionId(saleOrder.getTransaction_id());
					//目前来说1个订单只有1个魔方码
					final String mfcode = mftourCodes.get(0).getMf_code();
					//				final String mfcodeUrl = SMSContentTool.getMfourCodeURL(saleOrder.getOrder_id(), mftourCodes.get(0).getCode_id(), saleOrder.getCreate_time());

					contacteeSms = SMSContacteeContentConvert.OrderVoucherContentSMS.getOrderMfcodeMsg(productName, supllierPhone, mfcode, orderURL,
							resellerPhone);
				} else {
					contacteeSms = SMSContacteeContentConvert.OrderVoucherContentSMS.getOrderOnePersonOneMfcodeMsg(productName, orderURL);
				}
			}
		} else if (saleOrder.getNeed_confirm() == NeedConfirmEnum.NEED.getValue()
				&& sendSMSRuleConvert.checkSMSSend(ruleCache, SkuRuleGLobal.MessageTypeEnum.contractConfirmMsg.getValue())) {
			//需要二次确认
			contacteeSms = SMSContacteeContentConvert.OrderPaymentContentSMS.getNeedConfirmMsg(productName, supllierPhone);
		}
		final SMSSendModel sendModel = new SMSSendModel();
		// 针对产品需求，将如下两个产品进行特殊处理
		//contacteeSms = specialProductHandle(rootSkuid, contacteeSms);

		sendModel.setMsg(contacteeSms);
		sendModel.setSaleOrderId(saleOrder.getOrder_id());
		sendModel.setPhoneNum(contactPhone);
		sendModel.setSupplierId(sendSMSAroundConvert.getSaleOrderSupplierId(saleOrder));
		sendSMSAroundConvert.sendSMS(sendModel, SmsSendTypeEnum.orderContact);
	}

	/**
	 * 对如下的商品进行特殊处理
	 * 美丽之冠大剧院(spu)
	 * 果喜红艺人表演(spu)
	 *
	 * @param skuId
	 * @param sms 默认短信内容
	 * @return 修改后的短信内容
	 */
	//	private String specialProductHandle(final Long skuId, final String sms) {
	//		final Long[] guoXiRedPerformerSkuId = { 828520898963943424L, 828520898963943425L, 828533479887421440L,
	//				828533479887421441L, 828810354157240320L };
	//		final Long[] beautyOfTheGrandTheathreSkuId = { 828521047316054016L, 828521047316054017L, 828810343499935744L };
	//		final Set<Long> beautyOfTheGrandTheathresSkuIds = new HashSet<>(Arrays.asList(beautyOfTheGrandTheathreSkuId));
	//		final Set<Long> guoXiRedPerformersSkuIds = new HashSet<>(Arrays.asList(guoXiRedPerformerSkuId));
	//		if (guoXiRedPerformersSkuIds.contains(skuId)) {
	//			sms.replaceAll("，详询", GUOXI_RED_PERFORMER + "，详询");
	//		}
	//		if (beautyOfTheGrandTheathresSkuIds.contains(skuId)) {
	//			sms.replaceAll("，详询", BEAUTY_OF_THE_GRAND_THEATHRE + "，详询");
	//		}
	//		return sms;
	//	}

	//	/**
	//	 * 向供应商发送短信
	//	 * @param orderEntity
	//	 * @param merches
	//	 * @param ruleCache
	//	 */
	//	protected void sendSupplierSMS(final String productName, final OrderEntity saleOrderEntity, final Map<Long, SendSMSRuleModel> ruleCache) {
	//		if (!sendSMSRuleConvert.checkSMSSend(ruleCache, SkuRuleGLobal.MessageTypeEnum.supplierMsg.getValue())) {
	//			return;
	//		}
	//		final OrderEntity buyerOrder = orderWriteMapper.getInitialSupplierOrderByTransactionId(saleOrderEntity.getTransaction_id());
	//		//for (final OrderEntity buyerOrder : buyerOrders) {
	//		String suplierMsg = null;
	//		//			if (buyerOrder.getOrder_id().equals(saleOrderEntity.getOrder_id())) {
	//		//				continue;
	//		//			}
	//		final List<MerchEntity> merches = merchWriteMapper.getMerchByOrderId(saleOrderEntity.getOrder_id());
	//		final List<MerchStandardModel> standards = getOrderStatndar(merches, ruleCache);
	//		final String orderURL = SMSContentTool.getSupplierOrderURL(buyerOrder.getOrder_id(), buyerOrder.getCreate_time());
	//		//分销商电话
	//		final String resellerPhone = sendSMSAroundConvert.getResellerPhone(buyerOrder.getReseller_id());
	//		//如果订单不是需要二次确认的订单
	//		if (saleOrderEntity.getNeed_confirm() == NeedConfirmEnum.NOT_NEED.getValue()) {
	//			String travelDate = null;
	//			//当前订单只有1个出游日期
	//			final MerchEntity baseMerch = merches.get(0);
	//			if (sendSMSRuleConvert.checkIsNeedPaytime(baseMerch.getProduct_id())) {
	//				travelDate = SMSContentTool.gettravelDate(baseMerch.getStart_time());
	//			}
	//			suplierMsg = SMSSupplierContentConvert.OrderNotifyMsg.getNotityMsgDefault(productName, travelDate, standards, orderURL, resellerPhone);
	//		} else {
	//			final double makeMoney = getSupplierMakeMoeny(merches, buyerOrder, saleOrderEntity.getSale_port());
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
	//		//}
	//	}

	//	/**
	//	 * 分销商发生短信
	//	 * @param productName
	//	 * @param saleOrderEntity
	//	 */
	//	protected void sendResellerSMS(final String productName, final OrderEntity saleOrderEntity) {
	//		if (SalePortEnum.isMicShop(saleOrderEntity.getSale_port()) && saleOrderEntity.getNeed_confirm() != NeedConfirmEnum.NEED.getValue()) {
	//			final String supplierPhone = sendSMSAroundConvert.getSupplierPhone(sendSMSAroundConvert.getSaleOrderSupplierId(saleOrderEntity));
	//			final String resellerMsg = SMSResellerContentConvert.OrderNoticeSMS.getNoticeMicroshopMsg(productName, saleOrderEntity.getOrder_id(),
	//					saleOrderEntity.getContactee(), saleOrderEntity.getContact_mobile(), supplierPhone);
	//			final String resellerPhone = sendSMSAroundConvert.getResellerPhone(saleOrderEntity.getReseller_id());
	//			final SMSSendModel sendModel = new SMSSendModel();
	//			sendModel.setMsg(resellerMsg);
	//			sendModel.setPhoneNum(resellerPhone);
	//			sendModel.setSaleOrderId(saleOrderEntity.getOrder_id());
	//			sendSMSAroundConvert.sendSMS(sendModel, SmsSendTypeEnum.orderReseller);
	//		}
	//	}

	/**
	 * 获取供应商赚取到的金额（单产品）
	 * @param merchs
	 * @return
	 */
	protected double getSupplierMakeMoeny(final List<MerchEntity> buyerMerchs, final OrderEntity buyerOrder, final int saleType) {
		double totalAmount = 0;
		final Map<Long, Double> settlementPriceCache = sendSMSRuleConvert.getSettlementPriceCache(buyerMerchs, buyerOrder, saleType);
		for (final MerchEntity merch : buyerMerchs) {
			//如果是销售订单的商品过滤
			if (merch.getMerch_id().equals(merch.getRoot_merch_id())) {
				continue;
			}
			final double settlementPrice = settlementPriceCache.get(merch.getProduct_id());
			totalAmount += (merch.getTotal_num() - merch.getRefund_num()) * settlementPrice;
		}
		//老的订单,供应商的利润金额包括退款结算金额,退款结算金额存储到了采购订单的退款金额上
		if (buyerOrder.getVersion().equals("0")) {
			totalAmount += buyerOrder.getRefund_amount();
		}
		return MoneyUtils.getMoenyNumber(totalAmount);
	}

	/**
	 * 获取订单对应的规格信息
	 * @param buyerMerches
	 * @param ruleCache
	 * @return
	 */
	protected List<MerchStandardModel> getOrderStatndar(final List<MerchEntity> buyerMerches, final Map<Long, SendSMSRuleModel> ruleCache) {
		final Map<Long, Integer> standarCache = new HashMap<Long, Integer>();
		for (final MerchEntity merch : buyerMerches) {
			final long skuid = merch.getProduct_id();
			int standarNum = standarCache.get(skuid) == null ? 0 : standarCache.get(skuid);
			standarNum += merch.getTotal_num();
			standarCache.put(skuid, standarNum);
		}
		final List<MerchStandardModel> standards = new ArrayList<MerchStandardModel>();
		for (final Entry<Long, Integer> entry : standarCache.entrySet()) {
			final long skuid = entry.getKey();
			final int standarNum = entry.getValue();
			final SendSMSRuleModel ruleModel = ruleCache.get(skuid);
			final String standarName = SMSContentTool.getFormatProductName(ruleModel.getSkuName());
			final MerchStandardModel model = new MerchStandardModel();
			model.setStandardName(standarName);
			model.setStandardNum(standarNum);
			standards.add(model);
		}
		return standards;
	}

}
