package com.pzj.core.trade.refund.engine.handler;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.core.product.service.SeatRecordService;
import com.pzj.core.trade.voucher.entity.ExtendVoucherEntity;
import com.pzj.core.trade.voucher.entity.VourEntity;
import com.pzj.core.trade.voucher.write.VoucherExtendWriteMapper;
import com.pzj.core.trade.voucher.write.VoucherWriteMapper;

@Component(value = "voucherRefundEvent")
public class VoucherRefundEvent {

	@Resource(name = "voucherWriteMapper")
	private VoucherWriteMapper voucherWriteMapper;

	@Resource(name = "voucherExtendWriteMapper")
	private VoucherExtendWriteMapper voucherExtendWriteMapper;

	@Resource(name = "seatRecordService")
	private SeatRecordService seatRecordService;

	public void refundVoucher(final long voucherId, final long productId, final int number) {
		final VourEntity voucher = voucherWriteMapper.queryVoucherById(voucherId);
		if (voucher == null) {
			return;//容错处理, 当凭证错误情况下, 不影响订单的退款.
		}

		final ExtendVoucherEntity extend = voucherExtendWriteMapper.queryExtendsByVoucherIdAndAttribuate(voucherId, "scenicId");

		//		final SeatRecordUpdateReqModel reqModel = new SeatRecordUpdateReqModel();
		//		reqModel.setTheaterId(Long.parseLong(extend.getVoucher_attr_content()));
		//		reqModel.setTransactionId(voucher.getTransaction_id());
		//		final Result<Boolean> released = seatRecordService.releaseSeat(reqModel, null);
	}
}
