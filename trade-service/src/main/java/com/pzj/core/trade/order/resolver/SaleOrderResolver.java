package com.pzj.core.trade.order.resolver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.strategy.common.global.StrategyGlobal.IsNeedPaymentEnum;
import com.pzj.core.strategy.common.global.StrategyGlobal.StrategyTypeEnum;
import com.pzj.core.trade.order.engine.model.MerchModel;
import com.pzj.core.trade.order.engine.model.StrategyModel;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.constant.TradeConstant;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.order.common.NeedConfirmEnum;
import com.pzj.trade.order.common.OrderIDGenerater;
import com.pzj.trade.order.common.SalePortEnum;
import com.pzj.trade.order.entity.OrderExtendAttrEntity;
import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.entity.RemarkEntity;
import com.pzj.trade.order.entity.TradingOrderEntity;
import com.pzj.trade.order.model.FilledModel;
import com.pzj.trade.order.vo.TradeOrderVO;

/**
 * 销售订单生成器.
 * @author YRJ
 *
 */
@Component(value = "saleOrderResolver")
public class SaleOrderResolver implements ObjectConverter<TradeOrderVO, List<MerchModel>, TradingOrderEntity> {

	Logger logger = LoggerFactory.getLogger(SaleOrderResolver.class);

	@Override
	public TradingOrderEntity convert(TradeOrderVO reqModel, List<MerchModel> merchModels) {

		TradingOrderEntity order = generateOrderEntity(reqModel);
		logger.info("商品模型: " + (JSONConverter.toJson(merchModels.get(0))));

		for (MerchModel merchModel : merchModels) {
			MerchEntity merch = assemblyMerch(merchModel, order.getOrder_id());
			order.getProducts().add(merch);

			BigDecimal totalAmount = calculator(merchModel);
			order.setTotal_amount(order.getTotal_amount().add(totalAmount));
			order.setTotal_num(order.getTotal_num() + merchModel.getBuyNum());

			OrderStrategyEntity strategy = assemblyStrategy(order.getOrder_id(), merch);
			order.getStrategies().add(strategy);
		}

		handleExtend(order, merchModels);

		RemarkEntity remark = assemblyRemark(reqModel, order.getOrder_id());
		order.setRemark(remark);

		List<OrderExtendAttrEntity> filleds = assemblyFilled(reqModel.getFilledModelList(), order.getTransaction_id());
		order.setFilleds(filleds);
		return order;
	}

	private void handleExtend(TradingOrderEntity order, List<MerchModel> models) {
		OrderExtend extend = new OrderExtend();
		for (MerchModel model : models) {
			extend.setAgent(model.getAgent());
			extend.setNeed_confirm(model.getNeedConfirm());
			extend.setDock(model.getIsDock());
			if (model.getResellerStrategy().getStrategyType() == StrategyTypeEnum.GYSZQ.getType()) {
				extend.setPayable(model.getResellerStrategy().isPayable());
			} else {
				extend.setPayable(IsNeedPaymentEnum.Need.getId());
			}

		}
		order.setAgent_flag(extend.getAgent());
		order.setConfirm(extend.getNeed_confirm());
		order.setIs_dock(extend.getDock());

		{//设置是否需要支付.
			int onlinePay = IsNeedPaymentEnum.Need.getId();
			if (!SalePortEnum.isMicShop(order.getSale_port())) {
				onlinePay = extend.getPayable();
			}
			order.setOnline_pay(onlinePay);
		}

		int isDirect = 2;
		MerchModel model = models.get(0);
		StrategyModel strategy = model.getResellerStrategy();
		if (strategy.getStrategyType() == StrategyTypeEnum.GYSZQ.getType()) {
			isDirect = 1;
		}
		order.setIs_direct(isDirect);

		long supplierId = filterOrderSupplier(models.get(0));
		order.setSupplier_id(supplierId);
	}

	/**
	 * 根据分销政策计算订单供应商ID.
	 * @param merchModel
	 * @return
	 */
	private long filterOrderSupplier(MerchModel merchModel) {
		StrategyModel strategy = merchModel.getResellerStrategy();
		long supplierId = TradeConstant.PLATFORM_ACCOUNT;
		if (strategy.getStrategyType() == StrategyTypeEnum.GYSZQ.getType()) {
			supplierId = merchModel.getSupplierId();
		}
		return supplierId;
	}

	/**
	 * 价格计算器.
	 * @param merchModel
	 * @return
	 */
	private BigDecimal calculator(MerchModel merchModel) {
		BigDecimal price = merchModel.getPrice(merchModel.getResellerStrategy());
		BigDecimal total = price.multiply(new BigDecimal(merchModel.getBuyNum()));
		return total;
	}

	/**
	 *  构建商品政策.
	 * @param order_id
	 */
	private OrderStrategyEntity assemblyStrategy(String orderId, MerchEntity merch) {
		OrderStrategyEntity s = new OrderStrategyEntity(orderId, merch.getMerch_id());
		s.setChannelId(merch.getChannel_id());
		s.setStrategyId(merch.getStrategy_id());
		s.setDiscountAmount(merch.getPrice() - merch.getSettlement_price());
		s.setDiscountType(1);//1为前返，目前全是前返。以后如果扩展的话，应该根据政策的类型配置
		return s;
	}

	/**
	 * 构建备注信息.
	 * @param orderVO
	 * @param orderId
	 * @return
	 */
	private RemarkEntity assemblyRemark(TradeOrderVO orderVO, String orderId) {
		if (Check.NuNStrStrict(orderVO.getRemark())) {
			return null;
		}

		RemarkEntity remarkEntity = new RemarkEntity();
		remarkEntity.setOrder_id(orderId);
		remarkEntity.setOperator_name(String.valueOf(orderVO.getOperatorId()));
		remarkEntity.setOperator_id(orderVO.getOperatorId());
		remarkEntity.setOperator_type(1);
		remarkEntity.setRemark(orderVO.getRemark());
		return remarkEntity;
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

	/**
	 * 构建销售订单.
	 * @param reqModel
	 * @return
	 */
	private TradingOrderEntity generateOrderEntity(TradeOrderVO reqModel) {
		String orderId = OrderIDGenerater.generate("MF");
		TradingOrderEntity order = new TradingOrderEntity(orderId);

		{
			order.setOperator_id(reqModel.getOperatorId());
			order.setReseller_id(reqModel.getResellerId());
			long payerId = calculatePayId(reqModel.getPayerId(), reqModel.getSalePort());
			order.setPayer_id(payerId);
		}
		{
			order.setTravel(reqModel.getTravel());
			order.setTravel_depart_id(reqModel.getTravelDepartId());
			order.setGuide_id(reqModel.getGuideId());
		}
		{
			order.setContactee(reqModel.getContactee());
			order.setContact_mobile(reqModel.getContactMobile());
			order.setIdcard_no(reqModel.getIdcard_no());
		}
		order.setSale_port(reqModel.getSalePort());
		order.setOrder_type(2);//销售订单. 原为枚举类OrderTypeEnum
		return order;
	}

	/**
	 * 根据销售端口计算付款者.
	 * @param payerId
	 * @param salePort
	 * @return
	 */
	private long calculatePayId(long payerId, int salePort) {
		boolean isMicShop = SalePortEnum.isMicShop(salePort);
		return isMicShop ? 967470 : payerId;
	}

	/**
	 * 组装商品.
	 * @param 
	 * @return
	 */
	private MerchEntity assemblyMerch(MerchModel merchModel, String orderId) {

		String merchId = OrderIDGenerater.generate("P");
		MerchEntity merch = new MerchEntity(merchId);
		merch.setRoot_merch_id(merch.getMerch_id());
		{
			merch.setProduct_id(merchModel.getProdId());
			merch.setMerch_name(merchModel.getProdName());
			merch.setMerch_type(merchModel.getProdType());
			merch.setTotal_num(merchModel.getBuyNum());
			merch.setProduct_varie(merchModel.getProdVarie());
		}
		{
			merch.setOrder_id(orderId);
			merch.setTransaction_id(orderId);
		}
		{
			StrategyModel strategy = merchModel.getResellerStrategy();
			if (strategy != null) {
				merch.setStrategy_id(strategy.getStrategyId().longValue());
			}
			merch.setPrice(merchModel.getPrice());
			merch.setSettlement_price(merchModel.getPrice() - strategy.getRebate_amount());
		}
		{
			merch.setChannel_id(merchModel.getResellerStrategy().getChannelId());
			merch.setVoucher_id(merchModel.getVoucherId());
		}
		merch.setVour_type(merchModel.getVourType());
		{
			merch.setStart_time(merchModel.getPlayDate());
			merch.setExpire_time(merchModel.getExpireTime());
			merch.setEffec_time(merchModel.getEffecTime());
		}
		return merch;
	}

	private class OrderExtend {
		private int agent;
		private int dock;
		private int payable;
		private int need_confirm = NeedConfirmEnum.NOT_NEED.getValue();

		public void setAgent(int agent) {//产品上是1、0,而交易是2,1表示的
			if (this.agent != 2) {
				if (agent == 1) {
					this.agent = 2;
				} else {
					this.agent = 1;
				}
			}
		}

		public int getPayable() {
			return payable;
		}

		public void setPayable(int payable) {
			this.payable = payable;
		}

		public int getAgent() {
			return agent;
		}

		public int getDock() {
			return dock;
		}

		public void setDock(int dock) {
			this.dock = dock;
		}

		public void setNeed_confirm(int need_confirm) {
			if (need_confirm == 2) {
				this.need_confirm = NeedConfirmEnum.NEED.getValue();
			}
		}

		public int getNeed_confirm() {
			return need_confirm;
		}
	}
}
