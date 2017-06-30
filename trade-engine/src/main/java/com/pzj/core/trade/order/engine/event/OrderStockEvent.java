package com.pzj.core.trade.order.engine.event;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pzj.trade.merch.entity.MerchEntity;

/**
 * 下单占库存拼装.
 * @author CHJ
 *
 */
@Component(value = "orderStockEvent")
public class OrderStockEvent {

	//	@Autowired
	//	private StockOperateEngine stockOperateEngine;

	public void occupy(List<MerchEntity> merchs) {
		//		String transaction_id = merchs.get(0).getTransaction_id();
		//		Set<Long> voucherIds = new HashSet<Long>();
		//		for (MerchEntity merch : merchs) {
		//			voucherIds.add(merch.getVoucher_id());
		//		}
		//		if (merchs.get(0).getMerch_type() == ProductTypeGlobal.SCENIC) {//演绎，需要占座
		//			stockOperateEngine.operateStockAndSeat(new ArrayList<Long>(voucherIds), transaction_id);
		//		} else {
		//			stockOperateEngine.operateSotck(new ArrayList<Long>(voucherIds), transaction_id);
		//		}
	}

}
