package com.pzj.core.trade.order.engine;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.order.engine.event.OrderStockEvent;
import com.pzj.core.trade.order.engine.event.VoucherBindEvent;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.entity.MftourCodeEntity;
import com.pzj.trade.order.entity.OrderExtendAttrEntity;
import com.pzj.trade.order.entity.OrderResponse;
import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.entity.TradingOrderEntity;
import com.pzj.trade.order.write.MftourCodeWriteMapper;
import com.pzj.trade.order.write.OrderExtendAttrWriteMapper;
import com.pzj.trade.order.write.OrderStrategyWriteMapper;
import com.pzj.trade.order.write.OrderWriteMapper;

/**
 * 交易下单执行引擎.
 * @author YRJ
 *
 */
@Component(value = "tradeOrderEngine")
public class TradeOrderEngine {

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private MerchWriteMapper merchWriteMapper;

	@Autowired
	private OrderStrategyWriteMapper orderStrategyWriteMapper;

	@Autowired
	private MftourCodeWriteMapper mftourCodeWriteMapper;

	@Resource(name = "orderExtendAttrWriteMapper")
	private OrderExtendAttrWriteMapper orderExtendAttrWriteMapper;

	@Autowired
	private VoucherBindEvent voucherBindEvent;

	@Autowired
	private OrderStockEvent orderStockEvent;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 3)
	public OrderResponse doHandler(final TradingOrderEntity order, final List<TradingOrderEntity> purchs) {
		purchs.add(order);//为了批量执行, 临时将销售订单、采购订单进行合并.

		final List<MerchEntity> merchs = new ArrayList<MerchEntity>();
		final List<OrderStrategyEntity> strategies = new ArrayList<OrderStrategyEntity>();
		final List<MftourCodeEntity> mfcodes = new ArrayList<MftourCodeEntity>();
		for (final TradingOrderEntity purch : purchs) {
			merchs.addAll(purch.getProducts());

			if (purch.getMfCode() != null) {
				mfcodes.add(purch.getMfCode());
			}

			if (purch.getStrategies().size() > 0) {
				strategies.addAll(order.getStrategies());
			}
		}
		OrderGenerateEngine.generateOrderCanceTime(purchs);
		orderWriteMapper.insert(purchs);
		merchWriteMapper.insertMerchEntity(merchs);
		if (mfcodes.size() > 0) {
			mftourCodeWriteMapper.insertMftourCode(mfcodes);
		}
		if (strategies.size() > 0) {
			orderStrategyWriteMapper.insertStrategy(strategies);
		}
		if (order.getRemark() != null) {
			orderWriteMapper.insertOrderRemark(order.getRemark());
		}
		final List<OrderExtendAttrEntity> filleds = order.getFilleds();
		if (filleds != null && filleds.size() > 0) {
			orderExtendAttrWriteMapper.insert(filleds);
		}

		//		voucherBindEvent.bind(purchs, merchs);
		orderStockEvent.occupy(merchs);//占座
		//boolean needSeat = (ProductTypeGlobal.SCENIC == voucherList.get(0).getVoucherCategory());
		//if (needSeat) {//演艺需要占座
		//stockOperateEngine.operateStockAndSeat(voucherList, type);
		//} else {
		//stockOperateEngine.operateSotck(voucherList, type);
		//}

		final OrderResponse resp = generateOrderResponse(order);

		return resp;
	}

	private OrderResponse generateOrderResponse(final TradingOrderEntity order) {
		final OrderResponse resp = new OrderResponse();
		resp.setOrderId(order.getOrder_id());
		resp.setReseller_id(order.getReseller_id());
		resp.setSupplier_id(order.getSupplier_id());
		resp.setIs_direct(order.getIs_direct());
		resp.setOnline_pay(order.getOnline_pay());
		resp.setNeedConfirm(order.getConfirm());
		resp.setSale_port(order.getSale_port());
		resp.setContact_mobile(order.getContact_mobile());
		resp.setContactee(order.getContactee());

		final DecimalFormat df = new DecimalFormat("#.00");
		final String total_amount = df.format(order.getTotal_amount());
		resp.setTotalAmount(Double.valueOf(total_amount));

		return resp;
	}
}
