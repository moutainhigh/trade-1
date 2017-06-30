/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.confirm.event;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.confirm.common.ConfirmErrorCode;
import com.pzj.core.trade.confirm.exception.MechStateConfirmException;
import com.pzj.core.trade.confirm.exception.RefundedCanNotConfirmException;
import com.pzj.core.trade.confirm.exception.VoucherIsNotExitException;
import com.pzj.core.trade.refund.engine.common.RefundingEnum;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.ConfirmMerchEntity;
import com.pzj.trade.order.common.MerchStateEnum;

/**
 * 检查商品核销的合法性事件
 *
 * @author DongChunfu
 * @version $Id: MerchConfirmLegalCheckEvent.java, v 0.1 2017年3月23日 下午2:29:10 DongChunfu Exp $
 */
@Component(value = "merchConfirmLegalCheckEvent")
public class MerchConfirmLegalCheckEvent {
	private static final Logger logger = LoggerFactory.getLogger(MerchConfirmLegalCheckEvent.class);

	public void isLegal(final List<ConfirmMerchEntity> waitingConfirmMerches, final long voucherId) {

		if (Check.NuNCollections(waitingConfirmMerches)) {
			logger.info("凭证[" + voucherId + "], 待核销商品不存在");
			throw new VoucherIsNotExitException(ConfirmErrorCode.CONFIRM_VOUCHER_NOT_EXIST_ERROR_CODE,
					"核销凭证" + voucherId + "不存在");
		}

		boolean isAllRefunded = true;// 所有商品都已退款
		for (final ConfirmMerchEntity merch : waitingConfirmMerches) {
			if (merch.getIs_refunding() == RefundingEnum.REFUNDING.getValue()) {
				logger.error("凭证[" + voucherId + "]核销失败, 商品[" + merch.getMerch_id() + "]正在退款中...");
				throw new MechStateConfirmException(ConfirmErrorCode.REFUNDING_CAN_NOT_CONFIRM_ERROR_CODE, "退款中的商品,不可以进行核销");
			}

			if (merch.getMerch_state() == MerchStateEnum.WAIT_CONFIRM.getState()) {
				logger.error("凭证[" + voucherId + "]核销失败, 商品[" + merch.getMerch_id() + "]待确认状态中...");
				throw new MechStateConfirmException(ConfirmErrorCode.WAITING_CAN_NOT_CONFIRM_ERROR_CODE, "待确认的商品,不可以进行核销");
			}

			if (merch.getMerch_state() == MerchStateEnum.UNAVAILABLE.getState()) {
				logger.error("凭证[" + voucherId + "]核销失败, 商品[" + merch.getMerch_id() + "]不可用状态中...");
				throw new MechStateConfirmException(ConfirmErrorCode.WAITING_CAN_NOT_CONFIRM_ERROR_CODE, "不可用的商品:,不可以进行核销");
			}

			if (merch.getMerch_state() != MerchStateEnum.REFUNDED.getState()) {
				isAllRefunded = false;
			}
		}

		// 冗余判断，商品全部他退款，凭证应废弃
		if (isAllRefunded) {
			logger.error("凭证[" + voucherId + "]核销失败, 商品已全部退款.");
			throw new RefundedCanNotConfirmException(ConfirmErrorCode.REFUNDED_CAN_NOT_CONFIRM_ERROR_CODE, "已退款的商品,不可以进行核销");
		}
	}
}
