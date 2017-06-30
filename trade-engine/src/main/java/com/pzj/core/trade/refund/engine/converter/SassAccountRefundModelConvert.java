package com.pzj.core.trade.refund.engine.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.context.utils.MoneyUtils;
import com.pzj.core.trade.order.engine.common.PayWayEnum;
import com.pzj.core.trade.refund.engine.model.RefundApplyMerchModel;
import com.pzj.framework.toolkit.Check;
import com.pzj.settlement.balance.request.LaunchSaasRefundVo;
import com.pzj.settlement.balance.request.SaasRefundDetial;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.common.SalePortEnum;
import com.pzj.trade.order.model.RefundMerchStrategyModel;

@Component
public class SassAccountRefundModelConvert {
	public LaunchSaasRefundVo convert(String refundId, String transactionId, int isForce, int saleOrderStatus,
			List<RefundMerchStrategyModel> merchStrategies, List<RefundApplyMerchModel> refundMerches) {
		LaunchSaasRefundVo refundModel = new LaunchSaasRefundVo();
		refundModel.setSignId(refundId);
		refundModel.setTransactionId(transactionId);
		refundModel.setRefundType(isForce);
		if (saleOrderStatus == OrderStatusEnum.Finished.getValue()) {
			refundModel.setSplitAccount(true);
		} else {
			refundModel.setSplitAccount(false);
		}
		refundModel.setRefundDetialList(new ArrayList<SaasRefundDetial>());
		final Map<String, RefundApplyMerchModel> refundApplyMerchCache = getRefundApplyCache(refundMerches);
		Map<String, List<RefundMerchStrategyModel>> refundOrderCache = getRefundOrderCache(merchStrategies);
		Iterator<Entry<String, List<RefundMerchStrategyModel>>> iterator = refundOrderCache.entrySet().iterator();
		for (; iterator.hasNext();) {
			Entry<String, List<RefundMerchStrategyModel>> entry = iterator.next();
			SaasRefundDetial detial = convertSaasRefundDetial(transactionId, entry.getValue(), isForce, refundApplyMerchCache);
			if (!Check.NuNObject(detial)) {
				refundModel.getRefundDetialList().add(detial);
			}
		}
		return refundModel;
	}

	protected Map<String, List<RefundMerchStrategyModel>> getRefundOrderCache(
			final List<RefundMerchStrategyModel> merchStrategies) {
		Map<String, List<RefundMerchStrategyModel>> refundOrderCache = new HashMap<String, List<RefundMerchStrategyModel>>();
		for (RefundMerchStrategyModel merch : merchStrategies) {
			if (!refundOrderCache.containsKey(merch.getOrderId())) {
				refundOrderCache.put(merch.getOrderId(), new ArrayList<RefundMerchStrategyModel>());
			}
			refundOrderCache.get(merch.getOrderId()).add(merch);
		}
		return refundOrderCache;
	}

	protected Map<String, RefundApplyMerchModel> getRefundApplyCache(final List<RefundApplyMerchModel> refundMerches) {
		Map<String, RefundApplyMerchModel> refundApplyCache = new HashMap<String, RefundApplyMerchModel>();
		for (RefundApplyMerchModel model : refundMerches) {
			refundApplyCache.put(model.getMerchId(), model);
		}
		return refundApplyCache;
	}

	/**
	 * 构建多级分销体系下的
	 * @param orderId
	 * @param merchStrategies
	 * @return
	 */
	private SaasRefundDetial convertSaasRefundDetial(final String transactionId,
			List<RefundMerchStrategyModel> merchStrategies, int isForce,
			final Map<String, RefundApplyMerchModel> refundApplyMerchCache) {
		SaasRefundDetial detial = new SaasRefundDetial();
		long resellerId = 0; // 分销商id（交易主体id）
		long supplierId = 0; // 供应商id（交易对手id）
		double refundAmount = 0; // 退款金额
		double rebateAmount = 0; // 返利冻结金额
		double profitAmount = 0;//退款关联利润金额
		boolean isMshop = false; // 是否是微店订单
		long realPayUserId = 0; // 实际支付者id
		for (RefundMerchStrategyModel merch : merchStrategies) {
			//如果商品对应的订单支付方式为后付款,则无需传入给账户系统
			if (merch.getPayWay() == PayWayEnum.AFTER.getPayWay()) {
				return null;
			}
			final RefundApplyMerchModel applyMerchModel = refundApplyMerchCache.get(merch.getMerchId());
			resellerId = merch.getResellerId();
			supplierId = merch.getSupplierId();
			realPayUserId = merch.getPayerId();
			isMshop = SalePortEnum.isMicShop(merch.getSalePort());
			double merchRefundRebate = merch.getAfterRebateAmount() * applyMerchModel.getApplyNum();
			rebateAmount += merchRefundRebate;
			refundAmount += applyMerchModel.getMerchCalculateModel().refundPrice(merch.getPrice())
					* applyMerchModel.getApplyNum();
			double profitPrice = 0;
			if (Check.NuNObject(merch.getParentMerchStrategyModel())) {
				profitPrice = merch.getSettlementPrice();
			} else {
				profitPrice = merch.getSettlementPrice() - merch.getParentMerchStrategyModel().getPrice();
			}
			profitAmount += profitPrice * applyMerchModel.getApplyNum();
		}
		detial.setResellerId(resellerId);
		detial.setSupplierId(supplierId);
		detial.setRealPayUserId(realPayUserId);
		detial.setMshop(isMshop);
		detial.setRebateAmount(MoneyUtils.getMoenyNumber(rebateAmount));
		detial.setRefundAmount(MoneyUtils.getMoenyNumber(refundAmount));
		detial.setProfitAmount(MoneyUtils.getMoenyNumber(profitAmount));
		return detial;
	}
}
