/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.confirm.event;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.confirm.common.AutoConfirmEnum;
import com.pzj.core.trade.confirm.common.ConfirmErrorCode;
import com.pzj.core.trade.confirm.common.ConfirmTypeEnum;
import com.pzj.core.trade.confirm.common.ConfirmVersionEnum;
import com.pzj.core.trade.confirm.exception.VoucherConfirmException;
import com.pzj.core.trade.confirm.exception.VoucherIsNotExitException;
import com.pzj.core.trade.voucher.entity.VourEntity;
import com.pzj.core.trade.voucher.write.VoucherWriteMapper;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.ConfirmMerchEntity;

/**
 *
 * @author DongChunfu
 * @version $Id: GetUsableVoucherEvent.java, v 0.1 2017年5月10日 上午10:46:10 DongChunfu Exp $
 */
@Component(value = "getUsableVoucherEvent")
public class GetUsableVoucherEvent {

	private static final Logger logger = LoggerFactory.getLogger(GetUsableVoucherEvent.class);

	@Autowired
	private VoucherWriteMapper voucherWriteMapper;

	public VourEntity getUseableVoucher(final int confirmType, final long voucherId, final int version,
			final List<ConfirmMerchEntity> confirmMerches) {
		final VourEntity voucher = voucherWriteMapper.queryVoucherForConfirmLockById(voucherId);
		if (Check.NuNObject(voucher)) {
			logger.error("confirm voucher is not exist,voucherId:{}.", voucherId);
			throw new VoucherIsNotExitException(ConfirmErrorCode.CONFIRM_ORDER_NOT_EXIST_ERROR_CODE, "凭证不存在");
		}

		if (confirmType == ConfirmTypeEnum.MANUAL.getType()) {
			if (version == ConfirmVersionEnum.OLD.getVersion()) {// 老版本逾期不能正常核销
				final long currentTime = System.currentTimeMillis();
				final long expireTime = voucher.getExpire_time().getTime();
				if (currentTime > expireTime) {
					logger.error("confirm vourcher is already expired,voucherId:{},expireTime:{}.", voucherId,
							new Date(expireTime));
					throw new VoucherConfirmException(ConfirmErrorCode.NORMAL_CONFIRM_ERROR_CODE, "逾期的凭证不能进行核销操作");
				}
			}
		}
		if (confirmType == ConfirmTypeEnum.AUTOMATIC.getType()) {
			// 1:是否逾期
			whetherOverdue(voucher);
			if (version == ConfirmVersionEnum.NEW.getVersion()) {
				// 2:是否可以自动核销
				canAutoConfirm(confirmMerches);
			}
		}
		return voucher;
	}

	/**
	 * 检查凭证是否已经过期
	 *
	 * @param voucher 已过期的凭证
	 */
	private void whetherOverdue(final VourEntity voucher) {
		final long currentTime = System.currentTimeMillis();
		final long expireTime = voucher.getExpire_time().getTime();
		if (currentTime < expireTime) {
			logger.error("confirm vourcher is not expired,voucherId:{},expireTime:{}.", voucher.getVoucher_id(),
					new Date(expireTime));
			throw new VoucherConfirmException(ConfirmErrorCode.AUTO_CONFIRM_ERROR_CODE, "未逾期的凭证不能进行自动核销操作");
		}
	}

	private Boolean canAutoConfirm(final List<ConfirmMerchEntity> confirmMerches) {

		for (final ConfirmMerchEntity confirmMerch : confirmMerches) {
			if (confirmMerch.getVersion() == ConfirmVersionEnum.NEW.getVersion()) {
				if (confirmMerch.getAuto_confirm() == AutoConfirmEnum.MANUAL.getType()) {
					final String merchId = confirmMerch.getMerch_id();
					logger.error("merch not support auto confirm,merchId:{}.", merchId);
					throw new VoucherConfirmException(ConfirmErrorCode.AUTO_CONFIRM_ERROR_CODE, "商品不支持自动核销操作" + merchId);
				}
			}
		}
		return Boolean.TRUE;
	}

}
