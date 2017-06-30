/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.confirm.event;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.voucher.entity.VourEntity;
import com.pzj.core.trade.voucher.read.VoucherConfirmReadMapper;
import com.pzj.core.trade.voucher.write.VoucherConfirmWriteMapper;
import com.pzj.trade.confirm.model.ConfirmModel;
import com.pzj.trade.merch.entity.ConfirmMerchEntity;
import com.pzj.voucher.entity.VoucherConfirm;

/**
 * 普通核销事件(依据原核销事件copy)
 *
 * @author DongChunfu
 * @version $Id: ConfirmEvent.java, v 0.1 2017年3月29日 下午4:48:00 DongChunfu Exp $
 */
@Component(value = "confirmEvent")
@Deprecated
public class ConfirmEvent {

	/**特殊检票点，不做检票点校验限制*/
	private final static long SPECIAL_TICKET_POINT = -999;

	@Autowired
	private VoucherConfirmWriteMapper voucherConfirmWriteMapper;

	@Autowired
	private VoucherConfirmReadMapper voucherConfirmReadMapper;

	@Resource(name = "getUsableVoucherEvent")
	private GetUsableVoucherEvent getUsableVoucherEvent;

	@Resource(name = "ticketPointVerifyEvent")
	private TicketPointVerifyEvent ticketPointVerifyEvent;

	public VourEntity getVoucher(final ConfirmModel reqModel, final int tradeVersion,
			final List<ConfirmMerchEntity> confirmMerches) {
		final long voucherId = reqModel.getVoucherId();
		final long ticketPoint = reqModel.getTicketPoint();
		final int confirmType = reqModel.getType();

		//1:根据核销信息获取可用Voucher
		final VourEntity voucher = getUsableVoucherEvent.getUseableVoucher(confirmType, voucherId, tradeVersion,
				confirmMerches);
		final List<VoucherConfirm> voucherConfirms = voucherConfirmReadMapper.queryVoucherConfimList(voucherId);

		ticketPointVerifyEvent.verify(voucher, confirmType, ticketPoint, voucherConfirms);

		//5:是否需要唤醒商品的核销，触发过核销即不需再次核销商品
		//voucher.setNeedConfirmMerch(awakenConfirmMerch(voucherConfirms));

		final Long checkPoint = isSpecialTicketPoint(ticketPoint) ? null : ticketPoint;
		voucherConfirmWriteMapper.updateVoucherConfirmUsedTimes(voucherId, null, checkPoint, 1, null);
		return voucher;
	}

	private boolean isSpecialTicketPoint(final long ticketPoint) {
		return ticketPoint == SPECIAL_TICKET_POINT;
	}

	/**
	 * 是否需要唤醒商品核销动作.
	 * @param confirmEntities 检票点信息
	 * @return
	 */
	//	private boolean awakenConfirmMerch(final List<VoucherConfirm> voucherConfirms) {
	//		for (final VoucherConfirm confirmEntity : voucherConfirms) {
	//			//当且仅当, 一次都没有核销过才唤醒商品核销动作.
	//			if (confirmEntity.getUsedTimes() > 0) {
	//				return false;
	//			}
	//		}
	//		return true;
	//	}
}
