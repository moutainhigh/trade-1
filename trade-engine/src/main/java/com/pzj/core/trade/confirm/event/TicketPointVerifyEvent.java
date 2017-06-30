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
import com.pzj.core.trade.confirm.common.ConfirmTypeEnum;
import com.pzj.core.trade.confirm.exception.TicketPointException;
import com.pzj.core.trade.voucher.entity.VourEntity;
import com.pzj.framework.toolkit.Check;
import com.pzj.voucher.engine.common.ProductType;
import com.pzj.voucher.entity.VoucherConfirm;

/**
 * 检票点验证事件
 *
 * @author DongChunfu
 * @version $Id: TicketPointVerifyEvent.java, v 0.1 2017年5月10日 上午10:52:07 DongChunfu Exp $
 */
@Component(value = "ticketPointVerifyEvent")
public class TicketPointVerifyEvent {

	private static final Logger logger = LoggerFactory.getLogger(TicketPointVerifyEvent.class);

	/**特殊检票点，不做检票点校验限制*/
	private final static long SPECIAL_TICKET_POINT = -999;

	public void verify(final VourEntity voucher, final int confirmType, final long ticketPoint,
			final List<VoucherConfirm> voucherConfirms) {
		//1:产品未设置检票点信息
		if (Check.NuNCollections(voucherConfirms)) {
			return;
		}

		//2:是否需要验证检票点的合法性.
		if (!needValidateTicketPoint(voucher.getVoucher_category())) {
			return;
		}

		// 检票点校验
		if (!autoConfirm(confirmType)) {
			logger.debug("auto confirm not need to verify ticket point");
			illegalTicketPoint(ticketPoint, voucherConfirms);
		}
	}

	/**
	 * 是否需要验证检票点.
	 * @param voucher_category
	 * @return
	 */
	private boolean needValidateTicketPoint(final int voucherCategory) {
		return ProductType.isScenicProduct(voucherCategory);
	}

	private boolean autoConfirm(final int confirmType) {
		return ConfirmTypeEnum.AUTOMATIC.getType() == confirmType;
	}

	/**
	 * 验证检票点的合法性.
	 * @param point
	 * @param voucher_category
	 */
	private void illegalTicketPoint(final long ticketPoint, final List<VoucherConfirm> voucherConfirms) {
		if (isSpecialTicketPoint(ticketPoint)) {
			return;
		}

		if (ticketPoint <= 0) {
			logger.error("cofirm req param ,checkPoin:{},is illegal.", ticketPoint);
			throw new TicketPointException(ConfirmErrorCode.CHECK_TICKET_POINT_ERROR_CODE, "请指定正确的检票点信息");
		}

		for (final VoucherConfirm voucherConfirm : voucherConfirms) {
			if (voucherConfirm.getChildProductId() == ticketPoint) {
				final Integer maxUseTimes = voucherConfirm.getMaxUseTimes();
				if (maxUseTimes != -99 && voucherConfirm.getUsedTimes() >= maxUseTimes) {
					throw new TicketPointException(ConfirmErrorCode.CHECK_TICKET_POINT_ERROR_CODE, "可用次数不足");
				}
				return;
			}
		}
		throw new TicketPointException(ConfirmErrorCode.CHECK_TICKET_POINT_ERROR_CODE, "请指定正确的检票点信息");
	}

	private boolean isSpecialTicketPoint(final long ticketPoint) {
		return ticketPoint == SPECIAL_TICKET_POINT;
	}

}
