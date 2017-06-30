package com.pzj.core.trade.order.resolver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.sku.common.constants.SkuProductGlobal;
import com.pzj.core.trade.order.engine.model.MerchModel;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.trade.constant.TradeConstant;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.order.common.OrderIDGenerater;
import com.pzj.trade.order.entity.MftourCodeEntity;
import com.pzj.trade.order.entity.TradingOrderEntity;

@Component(value = "purchOrderResolver")
public class PurchOrderResolver implements ObjectConverter<TradingOrderEntity, List<MerchModel>, List<TradingOrderEntity>> {

	private Logger logger = LoggerFactory.getLogger(PurchOrderResolver.class);

	@Override
	public List<TradingOrderEntity> convert(TradingOrderEntity saleOrder, List<MerchModel> merchModels) {
		Map<Long, TradingOrderEntity> purchs = new HashMap<Long, TradingOrderEntity>();

		for (MerchModel merchModel : merchModels) {
			TradingOrderEntity purchOrder = getPurchOrderEntity(purchs, saleOrder, merchModel.getSupplierId());

			MerchEntity merch = assemblyMerch(purchOrder, saleOrder.getProducts(), merchModel);
			BigDecimal totalAmount = calculator(merchModel);
			purchOrder.setTotal_amount(purchOrder.getTotal_amount().add(totalAmount));

			purchOrder.setTotal_num(purchOrder.getTotal_num() + merchModel.getBuyNum());

			purchOrder.getProducts().add(merch);
		}

		List<TradingOrderEntity> orders = new ArrayList<TradingOrderEntity>(purchs.values());
		generateMftourCode(orders);

		return orders;
	}

	/**
	 * 价格计算器.
	 * @param merchModel
	 * @return
	 */
	private BigDecimal calculator(MerchModel merchModel) {
		BigDecimal price = merchModel.getPrice(merchModel.getSupplierStrategy());
		BigDecimal total = price.multiply(new BigDecimal(merchModel.getBuyNum()));
		return total;
	}

	/**
	 * 获取采购订单. 采购订单基于供应商进行拆单操作, 相同供应商生成同一个采购订单.
	 * @param purchs         待组装的订单
	 * @param transactionId   销售订单ID
	 * @param supplierId    商品供应商ID
	 */
	private TradingOrderEntity getPurchOrderEntity(Map<Long, TradingOrderEntity> purchs, TradingOrderEntity saleOrder,
			long supplierId) {
		TradingOrderEntity order = null;
		if ((order = purchs.get(supplierId)) != null) {
			return order;
		}

		String orderId = OrderIDGenerater.generate("MF");
		order = new TradingOrderEntity();
		order.setOrder_id(orderId);
		order.setTransaction_id(saleOrder.getTransaction_id());
		extend(saleOrder, order);
		order.setSupplier_id(supplierId);

		purchs.put(supplierId, order);
		return order;
	}

	private void extend(TradingOrderEntity saleOrder, TradingOrderEntity order) {
		order.setTransaction_id(saleOrder.getTransaction_id());
		order.setOperator_id(saleOrder.getOperator_id());
		order.setPayer_id(saleOrder.getPayer_id());
		{
			order.setSale_port(saleOrder.getSale_port());
			order.setOrder_type(1);//OrderTypeEnum
			order.setIs_direct(saleOrder.getIs_direct());
			order.setOnline_pay(saleOrder.getOnline_pay());
			order.setIs_dock(saleOrder.getIs_dock());
			order.setAgent_flag(saleOrder.getAgent_flag());
			order.setConfirm(saleOrder.getConfirm());
		}
		{
			if (order.getIs_direct() == 1) {
				order.setReseller_id(saleOrder.getReseller_id());
			} else {
				order.setReseller_id(TradeConstant.PLATFORM_ACCOUNT);
			}
		}
		{
			order.setTravel(saleOrder.getTravel());
			order.setTravel_depart_id(saleOrder.getTravel_depart_id());
			order.setGuide_id(saleOrder.getGuide_id());
		}
		{
			order.setContactee(saleOrder.getContactee());
			order.setContact_mobile(saleOrder.getContact_mobile());
			order.setIdcard_no(saleOrder.getIdcard_no());
		}
	}

	private MerchEntity assemblyMerch(TradingOrderEntity purchOrder, List<MerchEntity> saleMerchs, MerchModel merchModel) {
		MerchEntity merch = new MerchEntity();
		merch.setOrder_id(purchOrder.getOrder_id());
		merch.setTransaction_id(purchOrder.getTransaction_id());

		String merchId = OrderIDGenerater.generate("P");
		merch.setMerch_id(merchId);
		merch.setProduct_id(merchModel.getProdId());

		for (MerchEntity m : saleMerchs) {
			if (m.getVoucher_id() == merchModel.getVoucherId() && m.getProduct_id() == merch.getProduct_id()) {
				merch.setRoot_merch_id(m.getMerch_id());
				break;
			}
			//			if (m.getProduct_id() == merch.getProduct_id()) {
			//				merch.setRoot_merch_id(m.getMerch_id());
			//				break;
			//			}
		}

		//merch.setRoot_merch_id(rootMerch.getMerch_id());
		merch.setMerch_name(merchModel.getProdName());
		merch.setMerch_type(merchModel.getProdType());

		merch.setProduct_varie(merchModel.getProdVarie());
		merch.setChannel_id(merchModel.getSupplierStrategy().getChannelId());
		merch.setStrategy_id(merchModel.getSupplierStrategy().getStrategyId());
		merch.setVoucher_id(merchModel.getVoucherId());
		merch.setTotal_num(merchModel.getBuyNum());
		merch.setPrice(merchModel.getPrice(merchModel.getSupplierStrategy()).doubleValue());
		merch.setSettlement_price(merch.getPrice() - merchModel.getSupplierStrategy().getRebate_amount());

		merch.setVour_type(merchModel.getVourType());
		merch.setClear_type(merchModel.getClearType());
		merch.setEffec_time(merchModel.getEffecTime());
		merch.setExpire_time(merchModel.getExpireTime());
		merch.setStart_time(merchModel.getPlayDate());
		return merch;
	}

	private void generateMftourCode(Collection<TradingOrderEntity> orders) {
		for (TradingOrderEntity order : orders) {
			List<MerchEntity> merchs = order.getProducts();
			boolean isMfcode = false;
			for (int j = 0; j < merchs.size(); j++) {
				if (merchs.get(j).getVour_type() == SkuProductGlobal.ConsumerTypeEnum.CONSUMER_CARDTYPE_MFOUTRCODE.getValue()) {
					isMfcode = true;
					break;
				}
			}
			if (isMfcode) {
				MftourCodeEntity mftourCodeEntity = new MftourCodeEntity();

				String mfcode = CodeGenerater.generate("");
				mftourCodeEntity.setMf_code(mfcode);
				mftourCodeEntity.setTransaction_id(order.getTransaction_id());
				mftourCodeEntity.setOrder_id(order.getOrder_id());
				mftourCodeEntity.setSupplier_id(order.getSupplier_id());
				mftourCodeEntity.setMerch_id(merchs.get(0).getMerch_id());
				order.setMfCode(mftourCodeEntity);
				logger.info("魔方码 --> " + (JSONConverter.toJson(mftourCodeEntity)));
			}
		}
	}

}
