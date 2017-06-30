package com.pzj.core.trade.order.engine.event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.voucher.entity.VoucherBindEntity;
import com.pzj.core.trade.voucher.write.VoucherWriteMapper;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.order.entity.MftourCodeEntity;
import com.pzj.trade.order.entity.TradingOrderEntity;

/**
 * 凭证绑定事件.
 * @author YRJ
 *
 */
@Component(value = "voucherBindEvent")
public class VoucherBindEvent {

	@Autowired
	private VoucherWriteMapper voucherWriteMapper;

	private void bind(final List<TradingOrderEntity> purchs, final List<MerchEntity> merchs) {
		final List<VoucherBindEntity> bindModels = new ArrayList<VoucherBindEntity>();
		for (final MerchEntity merch : merchs) {
			final String code = getMfcode(merch.getOrder_id(), purchs);

			final VoucherBindEntity bindEntity = new VoucherBindEntity();
			bindEntity.setVoucherId(merch.getVoucher_id());
			bindEntity.setTransactionId(merch.getTransaction_id());
			bindEntity.setMfcode(code);
			bindModels.add(bindEntity);
		}

		//		voucherWriteMapper.bindTransaction(bindModels);
	}

	private String getMfcode(final String orderId, final List<TradingOrderEntity> purchs) {
		TradingOrderEntity order = null;
		for (final TradingOrderEntity purch : purchs) {
			if (purch.getOrder_id().equals(orderId)) {
				order = purch;
			}
		}

		String code = null;
		if (order != null) {
			final MftourCodeEntity mfCode = order.getMfCode();
			if (mfCode != null) {
				code = mfCode.getMf_code();
			}
		}
		return code;
	}

}
