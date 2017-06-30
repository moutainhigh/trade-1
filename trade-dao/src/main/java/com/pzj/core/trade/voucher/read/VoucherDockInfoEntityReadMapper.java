package com.pzj.core.trade.voucher.read;

import com.pzj.voucher.entity.VoucherDockInfoEntity;

public interface VoucherDockInfoEntityReadMapper {

	/**
	 * 
	 * @param transactionId
	 * @return
	 */
	VoucherDockInfoEntity queryVoucherDockInfoEntityByTransactionId(String transactionId);

}