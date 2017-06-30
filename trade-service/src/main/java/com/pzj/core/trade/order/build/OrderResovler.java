/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.build;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.strategy.common.global.StrategyGlobal.RebateMethodEnum;
import com.pzj.core.trade.order.engine.common.PayWayEnum;
import com.pzj.core.trade.order.engine.exception.OrderException;
import com.pzj.core.trade.order.engine.model.MerchBaseModel;
import com.pzj.core.trade.order.engine.model.StrategyBaseModel;
import com.pzj.core.trade.voucher.entity.VoucherBriefEntity;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.idgen.IDGenerater;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.model.TouristModel;
import com.pzj.trade.merch.common.VerificationVourTypeEnum;
import com.pzj.trade.merch.common.VourTypeEnum;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.order.common.NeedConfirmEnum;
import com.pzj.trade.order.common.OrderIDGenerater;
import com.pzj.trade.order.common.SalePortEnum;
import com.pzj.trade.order.entity.MftourCodeEntity;
import com.pzj.trade.order.entity.OrderExtendAttrEntity;
import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.entity.RemarkEntity;
import com.pzj.trade.order.entity.TouristEntity;
import com.pzj.trade.order.entity.TradingOrderEntity;
import com.pzj.trade.order.model.FilledModel;
import com.pzj.trade.order.model.MultiOrderInModel;

/**
 * 
 * @author Administrator
 * @version $Id: OrderResovler.java, v 0.1 2017年3月9日 上午10:02:03 Administrator Exp $
 */
@Component
public class OrderResovler {
	private final static Logger logger = LoggerFactory.getLogger(OrderResovler.class);
	@Autowired
	private VoucherAssebler voucherAssebler;
	@Autowired
	private IDGenerater idGenerater;

	@Autowired
	private OrderIDGenerater orderIDGenerater;

	/**
	 * 拆单，生成voucher
	 * 订单、商品、政策、游客，voucher
	 * @param orderInModel
	 * @param merchs
	 * @return
	 */
	public List<TradingOrderEntity> resolve(MultiOrderInModel orderInModel, List<MerchBaseModel> merchModels, String transaction_id) {
		logger.info("组装订单参数:" + JSONConverter.toJson(orderInModel) + JSONConverter.toJson(merchModels) + transaction_id);
		int level = merchModels.get(0).getStrategys().size();
		List<TradingOrderEntity> orders = generateOrders(orderInModel, level, merchModels, transaction_id);
		//		boolean isOneVote = judgeOneVote(orderInModel);

		//		logger.info("该订单的核销方式与凭证类型是:" + verificationVourType);

		TradingOrderEntity order = orders.get(0);

		setMerchs(orderInModel, merchModels, level, orders, order);
		//		setMerchs(orderInModel, merchModels, level, orders, order);

		List<VoucherBriefEntity> vouchers = voucherAssebler.asseble(orderInModel, merchModels, order.getTransaction_id());
		order.setVouchers(vouchers);

		assebleMfCode(orderInModel, level, orders, vouchers);

		assemblyRemark(orderInModel, orders);

		List<OrderExtendAttrEntity> filleds = assemblyFilled(orderInModel.getFilleds(), order.getTransaction_id());
		extraFilled(orderInModel, filleds, order.getTransaction_id());
		order.setFilleds(filleds);

		return orders;
	}

	/**
	 * 
	 * @param orderInModel
	 * @param merchModels
	 * @param level
	 * @param orders
	 * @param isOneVote
	 * @param order
	 * 
	 */
	private void setMerchs(MultiOrderInModel orderInModel, List<MerchBaseModel> merchModels, int level, List<TradingOrderEntity> orders,
			TradingOrderEntity order) {

		int verificationVourType = merchModels.get(0).getVerificationVourType();
		for (MerchBaseModel merchModel : merchModels) {
			List<MerchEntity> merchs = new ArrayList<MerchEntity>();
			if (verificationVourType == VerificationVourTypeEnum.CARDID_BY_NUM.getVourType()
					|| verificationVourType == VerificationVourTypeEnum.MFCODE_BY_NUM.getVourType()) {//身份证
				for (int i = 0; i < merchModel.getBuyNum(); i++) {
					MerchEntity merch = assemblyMerch(merchModel, orders.get(0).getOrder_id(), true);//商品
					assembleStrategy(level, merchModel, merch, orders);//政策
					merchs.add(merch);
				}
				assebleTouristForOneVote(orderInModel, merchs, verificationVourType);//游客
				calculateOrderAmount(level, orders, merchModel, merchs);
			} else {
				MerchEntity merch = assemblyMerch(merchModel, orders.get(0).getOrder_id(), false);
				assebleTourists(orderInModel, merch);
				assembleStrategy(level, merchModel, merch, orders);
				merchs.add(merch);
				calculateOrderAmount(level, orders, merchModel, merchs);
			}
			order.getProducts().addAll(merchs);
		}
	}

	/**
	 * 
	 * @param orderInModel
	 * @param level
	 * @param orders
	 * @param vouchers
	 */
	//	private void assebleMfCode(MultiOrderInModel orderInModel, int level, List<TradingOrderEntity> orders, List<VoucherBriefEntity> vouchers) {
	//		if (orderInModel.getVourType() == VourTypeEnum.MFCODE.getVourType()) {
	//			MftourCodeEntity mftourCodeEntity = new MftourCodeEntity();
	//			TradingOrderEntity supplierOrder = orders.get(level - 1);
	//
	//			String mfcode = vouchers.get(0).getVoucherContent();
	//			mftourCodeEntity.setMf_code(mfcode);
	//			mftourCodeEntity.setTransaction_id(supplierOrder.getTransaction_id());
	//			mftourCodeEntity.setOrder_id(supplierOrder.getOrder_id());
	//			mftourCodeEntity.setSupplier_id(supplierOrder.getSupplier_id());
	//			mftourCodeEntity.setMerch_id("");
	//			supplierOrder.setMfCode(mftourCodeEntity);
	//		}
	//	}

	private void assebleMfCode(MultiOrderInModel orderInModel, int level, List<TradingOrderEntity> orders, List<VoucherBriefEntity> vouchers) {
		if (orderInModel.getVourType() == VourTypeEnum.MFCODE.getVourType() || orderInModel.getVourType() == 4) {
			List<MftourCodeEntity> mfCodes = new ArrayList<MftourCodeEntity>();
			TradingOrderEntity supplierOrder = orders.get(level - 1);
			for (VoucherBriefEntity voucherBriefEntity : vouchers) {
				MftourCodeEntity mftourCodeEntity = new MftourCodeEntity();
				mftourCodeEntity.setMf_code(voucherBriefEntity.getVoucherContent());
				mftourCodeEntity.setTransaction_id(supplierOrder.getTransaction_id());
				mftourCodeEntity.setOrder_id(supplierOrder.getOrder_id());
				mftourCodeEntity.setSupplier_id(supplierOrder.getSupplier_id());
				mftourCodeEntity.setMerch_id("");
				mfCodes.add(mftourCodeEntity);
			}
			supplierOrder.setMfCodes(mfCodes);
		}
	}

	/**
	 * 判断是否一整一票
	 * 
	 * @param orderInModel
	 * @param merchModels
	 */
	//		private boolean judgeOneVote(MultiOrderInModel orderInModel) {
	//			return orderInModel.getVourType() == VourTypeEnum.CARDID.getVourType();
	//		}

	/**
	 * 
	 * @param orderInModel
	 * @param filleds
	 */
	private void extraFilled(MultiOrderInModel orderInModel, List<OrderExtendAttrEntity> filleds, String transaction_id) {
		if (!Check.NuNObject(orderInModel.getContactee())) {
			if (orderInModel.getContactee().getEmail() != null) {
				OrderExtendAttrEntity orderExtendAttrEntity = new OrderExtendAttrEntity();
				orderExtendAttrEntity.setTransaction_id(transaction_id);
				orderExtendAttrEntity.setOrder_id(transaction_id);
				orderExtendAttrEntity.setAttr_group("contact");
				orderExtendAttrEntity.setAttr_key("contact_email");
				orderExtendAttrEntity.setAttr_value(orderInModel.getContactee().getEmail());
				filleds.add(orderExtendAttrEntity);
			}
			if (orderInModel.getContactee().getContacteeSpell() != null) {
				OrderExtendAttrEntity orderExtendAttrEntity = new OrderExtendAttrEntity();
				orderExtendAttrEntity.setTransaction_id(transaction_id);
				orderExtendAttrEntity.setOrder_id(transaction_id);
				orderExtendAttrEntity.setAttr_group("contact");
				orderExtendAttrEntity.setAttr_key("contact_spelling");
				orderExtendAttrEntity.setAttr_value(orderInModel.getContactee().getContacteeSpell());
				filleds.add(orderExtendAttrEntity);
			}
			if (orderInModel.getTouristSourceProvince() != null) {
				OrderExtendAttrEntity orderExtendAttrEntity = new OrderExtendAttrEntity();
				orderExtendAttrEntity.setTransaction_id(transaction_id);
				orderExtendAttrEntity.setOrder_id(transaction_id);
				orderExtendAttrEntity.setAttr_group("Order");
				orderExtendAttrEntity.setAttr_key("TouristSourceProvince");
				orderExtendAttrEntity.setAttr_value(orderInModel.getTouristSourceProvince());
				filleds.add(orderExtendAttrEntity);
			}
		}
		if (orderInModel.getTouristSourceCountry() != null) {
			OrderExtendAttrEntity orderExtendAttrEntity = new OrderExtendAttrEntity();
			orderExtendAttrEntity.setTransaction_id(transaction_id);
			orderExtendAttrEntity.setOrder_id(transaction_id);
			orderExtendAttrEntity.setAttr_group("Order");
			orderExtendAttrEntity.setAttr_key("TouristSourceCountry");
			orderExtendAttrEntity.setAttr_value(orderInModel.getTouristSourceCountry());
			filleds.add(orderExtendAttrEntity);
		}
		if (orderInModel.getTouristSourceCity() != null) {
			OrderExtendAttrEntity orderExtendAttrEntity = new OrderExtendAttrEntity();
			orderExtendAttrEntity.setTransaction_id(transaction_id);
			orderExtendAttrEntity.setOrder_id(transaction_id);
			orderExtendAttrEntity.setAttr_group("Order");
			orderExtendAttrEntity.setAttr_key("TouristSourceCity");
			orderExtendAttrEntity.setAttr_value(orderInModel.getTouristSourceCity());
			filleds.add(orderExtendAttrEntity);
		}
	}

	/**
	 * 
	 * @param orderInModel
	 * @param merch
	 */
	private void assebleTourists(MultiOrderInModel orderInModel, MerchEntity merch) {
		List<TouristEntity> tourists = new ArrayList<TouristEntity>();
		if (Check.NuNCollections(orderInModel.getTourists())) {
			return;
		}
		for (TouristModel tourist : orderInModel.getTourists()) {
			if (tourist.getProductId() != merch.getProduct_id()) {
				continue;
			}
			TouristEntity touristEntity = new TouristEntity();
			touristEntity.setOrder_id(merch.getOrder_id());
			touristEntity.setMerch_id(merch.getMerch_id());
			touristEntity.setName(tourist.getTourist());
			touristEntity.setMobile(tourist.getTouristMobile());
			touristEntity.setIdcard(tourist.getIdcardNo());
			touristEntity.setName_spell(tourist.getTouristSpell());
			touristEntity.setOther(tourist.getRemark());
			tourists.add(touristEntity);
		}
		merch.getTourists().addAll(tourists);
	}

	/**
	 * 
	 * @param orderInModel
	 * @param merchs
	 */
	private void assebleTouristForOneVote(MultiOrderInModel orderInModel, List<MerchEntity> merchs, int verificationVourType) {
		List<TouristEntity> tourists = new ArrayList<TouristEntity>();
		MerchEntity merch = merchs.get(0);
		if (verificationVourType == VerificationVourTypeEnum.MFCODE_BY_NUM.getVourType() && Check.NuNCollections(orderInModel.getTourists())) {
			return;
		}
		for (TouristModel tourist : orderInModel.getTourists()) {
			if (tourist.getProductId() != merch.getProduct_id()) {
				continue;
			}
			TouristEntity touristEntity = new TouristEntity();
			touristEntity.setOrder_id(merch.getOrder_id());
			touristEntity.setMerch_id(merch.getMerch_id());
			touristEntity.setName(tourist.getTourist());
			touristEntity.setMobile(tourist.getTouristMobile());
			touristEntity.setIdcard(tourist.getIdcardNo());
			touristEntity.setName_spell(tourist.getTouristSpell());
			touristEntity.setOther(tourist.getRemark());
			tourists.add(touristEntity);
		}
		if (merchs.size() != tourists.size()) {
			throw new OrderException(14001, "购买数量和游客数量不一致");
		}
		for (int i = 0; i < merchs.size(); i++) {
			merchs.get(i).getTourists().add(tourists.get(i));
			tourists.get(i).setMerch_id(merchs.get(i).getMerch_id());
		}
	}

	/**
	 * 
	 * @param orderInModel
	 * @param level
	 * @return
	 */
	private List<TradingOrderEntity> generateOrders(MultiOrderInModel orderInModel, int level, List<MerchBaseModel> merchModels, String transaction_id) {
		List<TradingOrderEntity> orders = new ArrayList<TradingOrderEntity>(level);
		OrderExtend orderExtend = new OrderExtend();
		for (MerchBaseModel merch : merchModels) {
			orderExtend.setDock(merch.getIsDock());
			orderExtend.setNeedConfirm(merch.getNeedConfirm());
		}

		//预先生成各级订单
		for (int i = 0; i < level; i++) {
			TradingOrderEntity parentOrder = (i == 0) ? null : orders.get(i - 1);
			StrategyBaseModel startegy = merchModels.get(0).getStrategys().get(i);
			TradingOrderEntity order = generateOrderEntity(orderInModel, parentOrder, orderExtend, startegy.getSupplierId(), transaction_id, level - i);
			orders.add(order);
		}
		computeOrdersPayWay(level, merchModels, orders);
		return orders;
	}

	/**
	 * 计算支付方式，从供应端判断
	 * @param level
	 * @param merchModels
	 * @param orders
	 */
	private void computeOrdersPayWay(int level, List<MerchBaseModel> merchModels, List<TradingOrderEntity> orders) {
		for (int i = level - 1; i >= 0; i--) {
			int payType = 0;
			TradingOrderEntity order = orders.get(i);
			StrategyBaseModel startegy = merchModels.get(0).getStrategys().get(i);
			if (startegy.getStrategyId() == 0) {//免票特价票
				payType = PayWayEnum.CASH.getPayWay();
			}

			if (order.getSale_port() == SalePortEnum.OFFLINE_WINDOW.getValue()) {//线下
				if (startegy.getPay_type() == 1) {
					payType = PayWayEnum.CASH.getPayWay();
				}
				if (startegy.getPay_type() == 2) {
					payType = PayWayEnum.AFTER.getPayWay();
				} else {
					payType = PayWayEnum.CASH.getPayWay();
				}

			} else if (SalePortEnum.isMicShop(order.getSale_port()) && i == 0) {
				payType = PayWayEnum.BALANCE.getPayWay();
			} else {//线上
				if (startegy.getPay_type() == 1) {
					payType = PayWayEnum.BALANCE.getPayWay();
				}
				if (startegy.getPay_type() == 2) {
					payType = PayWayEnum.AFTER.getPayWay();
				}

				if (i < level - 1) {
					TradingOrderEntity subOrder = orders.get(i + 1);
					if (subOrder.getPay_way() == PayWayEnum.BALANCE.getPayWay()) {
						payType = subOrder.getPay_way();
					}
				}
			}
			order.setPay_way(payType);
		}
	}

	private class OrderExtend {
		private int needConfirm = NeedConfirmEnum.NOT_NEED.getValue();
		private int dock = 0;

		/**
		 * Getter method for property <tt>needConfirm</tt>.
		 * 
		 * @return property value of needConfirm
		 */
		public int getNeedConfirm() {
			return needConfirm;
		}

		/**
		 * Setter method for property <tt>needConfirm</tt>.
		 * 
		 * @param needConfirm value to be assigned to property needConfirm
		 */
		public void setNeedConfirm(int needConfirm) {
			if (needConfirm == NeedConfirmEnum.NOT_NEED.getValue())
				return;
			this.needConfirm = needConfirm;
		}

		/**
		 * Getter method for property <tt>dock</tt>.
		 * 
		 * @return property value of dock
		 */
		public int getDock() {
			return dock;
		}

		/**
		 * Setter method for property <tt>dock</tt>.
		 * 
		 * @param dock value to be assigned to property dock
		 */
		public void setDock(int dock) {
			if (dock == 0)
				return;
			this.dock = dock;
		}
	}

	/**
	 * 
	 * @param level
	 * @param merchModel
	 * @param merch
	 */
	private void assembleStrategy(int level, MerchBaseModel merchModel, MerchEntity merch, List<TradingOrderEntity> orders) {
		for (int straIndex = 0; straIndex < level; straIndex++) {
			StrategyBaseModel strategyBaseModel = merchModel.getStrategys().get(straIndex);
			OrderStrategyEntity strategy = generateStrategy(strategyBaseModel, merch, straIndex == 0, orders.get(straIndex));
			merch.getStrategy().add(strategy);
		}
	}

	/**
	 * 
	 * @param level
	 * @param orders
	 * @param merchModel
	 * @param merchs
	 */
	private void calculateOrderAmount(int level, List<TradingOrderEntity> orders, MerchBaseModel merchModel, List<MerchEntity> merchs) {
		for (int straIndex = 0; straIndex < level; straIndex++) {
			OrderStrategyEntity strategy = merchs.get(0).getStrategy().get(straIndex);
			BigDecimal totalAmount = strategy.getPrice().multiply(new BigDecimal(merchModel.getBuyNum()));
			TradingOrderEntity order = orders.get(straIndex);
			order.setTotal_amount(order.getTotal_amount().add(totalAmount));
			order.setTotal_num(order.getTotal_num() + merchModel.getBuyNum());
			//分销商，供应商，支付人
			order.setReseller_id(strategy.getResellerId());
			order.setSupplier_id(strategy.getSupplierId());
			long payer_id = calculatePayerId(order.getSale_port(), order.getReseller_id(), straIndex == 0);
			order.setPayer_id(payer_id);
		}
	}

	/**
	 * 根据销售端口计算付款者.
	 * @param straIndex
	 * @param order
	 */
	private long calculatePayerId(int salePort, long resellerId, boolean isReseller) {
		if (isReseller) {
			boolean isMicShop = SalePortEnum.isMicShop(salePort);
			return isMicShop ? 967470 : resellerId;
		}
		return resellerId;
	}

	/**
	 * 
	 * @param strategyBaseModel
	 * @param merch
	 * @return
	 */
	private OrderStrategyEntity generateStrategy(StrategyBaseModel strategyBaseModel, MerchEntity merch, boolean isReseller, TradingOrderEntity order) {
		OrderStrategyEntity strategy = new OrderStrategyEntity();
		strategy.setStrategyId(strategyBaseModel.getStrategyId());
		strategy.setChannelId(strategyBaseModel.getChannelId());
		strategy.setOrderId(order.getOrder_id());
		strategy.setMerchId(merch.getMerch_id());
		strategy.setAdvice_price(BigDecimal.valueOf(strategyBaseModel.getAdvicePrice()));
		strategy.setSettlement_price(BigDecimal.valueOf(strategyBaseModel.getSettlementPrice()));
		strategy.setInterval_day(strategyBaseModel.getIntervalDays());
		if (SalePortEnum.isMicShop(order.getSale_port()) && isReseller) {//微店首层订单特殊处理，默认为后返
			strategy.setRebate_method(RebateMethodEnum.End.getId());
			strategy.setRebate_settlement(strategyBaseModel.getRebateCycle());
			strategy.setAfter_rebate_amount(strategy.getAdvice_price().subtract(strategy.getSettlement_price()));
		} else {
			strategy.setRebate_method(strategyBaseModel.getRebateType());
			strategy.setRebate_settlement(strategyBaseModel.getRebateCycle());
			strategy.setAfter_rebate_amount(BigDecimal.valueOf(strategyBaseModel.getRebate_amount()));
		}

		strategy.setPrice(BigDecimal.valueOf(strategyBaseModel.getPrice(order.getSale_port(), isReseller)));
		strategy.setSupplierId(strategyBaseModel.getSupplierId());
		strategy.setResellerId(strategyBaseModel.getResellerId());
		return strategy;
	}

	/**
	 * 
	 * @param orderInModel
	 * @return
	 */
	private TradingOrderEntity generateOrderEntity(MultiOrderInModel orderInModel, TradingOrderEntity parentOrder, OrderExtend orderExtend, long supplierId,
			String transaction_id, int level) {
		TradingOrderEntity order = new TradingOrderEntity();
		String orderId = "";
		if (Check.NuNObject(parentOrder) && !Check.NuNObject(transaction_id)) {
			orderId = transaction_id;
		} else {
			orderId = orderIDGenerater.generateOrderId(orderInModel.getSalePort(), orderInModel.getBookType(), supplierId);
		}
		{
			order.setOrder_id(orderId);
			order.setP_order_id(parentOrder == null ? orderId : parentOrder.getOrder_id());
			order.setTransaction_id(parentOrder == null ? orderId : parentOrder.getTransaction_id());
		}
		{
			order.setOperator_id(orderInModel.getOperatorId());
		}
		{
			order.setTravel(orderInModel.getTravel());
			order.setTravel_depart_id(orderInModel.getTravelDepartId());
			order.setGuide_id(orderInModel.getGuideId());
			order.setDepart(orderInModel.getTravelDepartName());
			order.setGuider(orderInModel.getGuideIdName());
		}
		{
			if (!Check.NuNObject(orderInModel.getContactee())) {
				order.setContactee(orderInModel.getContactee().getContactee());
				order.setContact_mobile(orderInModel.getContactee().getContactMobile());
				order.setIdcard_no(orderInModel.getContactee().getIdcardNo());
			}
		}
		order.setSale_port(orderInModel.getSalePort());
		{
			order.setOrder_source(orderInModel.getBookType());
			order.setTicket_office_id(orderInModel.getTicketOfficeId());
			order.setOrder_level(level);
			order.setVersion(1);
		}
		{
			order.setIs_dock(orderExtend.getDock());
			order.setConfirm(orderExtend.getNeedConfirm());
		}
		return order;
	}

	/**
	 * 组装商品.
	 * @param 
	 * @return
	 */
	private MerchEntity assemblyMerch(MerchBaseModel merchModel, String orderId, boolean isOneVote) {

		String merchId = String.valueOf(idGenerater.nextId());
		MerchEntity merch = new MerchEntity(merchId);
		merch.setRoot_merch_id(merch.getMerch_id());
		{
			merch.setProduct_id(merchModel.getProdId());
			merch.setMerch_name(merchModel.getProdName());
			merch.setSku_name(merchModel.getSkuName());
			merch.setMerch_type(merchModel.getProdType());
			merch.setTotal_num(isOneVote ? 1 : merchModel.getBuyNum());
			merch.setProduct_varie(merchModel.getProdVarie());
			merch.setSupplier_id(merchModel.getSupplierId());
		}
		{
			merch.setOrder_id(orderId);
			merch.setTransaction_id(orderId);
		}
		merch.setVour_type(merchModel.getVourType());
		merch.setVerification_type(merchModel.getVerificationType());
		{
			merch.setStart_time(merchModel.getPlayDate());
			merch.setExpire_time(merchModel.getExpireTime());
			merch.setShow_start_time(merchModel.getShow_start_time());
			merch.setShow_end_time(merchModel.getShow_end_time());
		}
		{
			merch.setAuto_confirm(merchModel.getAutoConfirm());
			merch.setVersion(1);
		}
		merch.setPrice(merchModel.getPrice());
		return merch;
	}

	/**
	 * 构建备注信息.
	 * @param orderVO
	 * @param orders
	 * @return
	 */
	private void assemblyRemark(MultiOrderInModel orderInModel, List<TradingOrderEntity> orders) {
		if (Check.NuNStrStrict(orderInModel.getRemark())) {
			return;
		}
		for (TradingOrderEntity order : orders) {
			RemarkEntity remarkEntity = new RemarkEntity();
			remarkEntity.setOrder_id(order.getOrder_id());
			remarkEntity.setOperator_name(String.valueOf(orderInModel.getOperatorId()));
			remarkEntity.setOperator_id(orderInModel.getOperatorId());
			remarkEntity.setOperator_type(1);
			remarkEntity.setRemark(orderInModel.getRemark());
			order.setRemark(remarkEntity);
		}
	}

	/**
	 * 组装填单项.
	 * @param filleds
	 * @param transactionId
	 */
	private List<OrderExtendAttrEntity> assemblyFilled(List<FilledModel> filleds, String transactionId) {
		List<OrderExtendAttrEntity> attributes = new ArrayList<OrderExtendAttrEntity>();
		if (filleds == null) {
			return attributes;
		}

		for (FilledModel item : filleds) {
			OrderExtendAttrEntity orderExtendAttrEntity = new OrderExtendAttrEntity();
			orderExtendAttrEntity.setTransaction_id(transactionId);
			orderExtendAttrEntity.setOrder_id(transactionId);
			orderExtendAttrEntity.setAttr_group(item.getGroup());
			orderExtendAttrEntity.setAttr_key(item.getAttr_key());
			orderExtendAttrEntity.setAttr_value(item.getAttr_value());
			attributes.add(orderExtendAttrEntity);
		}
		return attributes;
	}

}
