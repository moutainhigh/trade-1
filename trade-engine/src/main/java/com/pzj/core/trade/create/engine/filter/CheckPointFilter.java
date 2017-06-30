/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.create.engine.filter;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.voucher.read.VoucherConfirmReadMapper;
import com.pzj.framework.toolkit.Check;
import com.pzj.voucher.entity.VoucherConfirmUsedTimes;

/**
 * 身份证判重
 * @author Administrator
 * @version $Id: IdCardNoLimit.java, v 0.1 2017年3月10日 下午4:06:03 Administrator Exp $
 */
@Component
public class CheckPointFilter {
	@Autowired
	private VoucherConfirmReadMapper voucherConfirmReadMapper;

	/**
	 * 对一个产品，同一个供应商在一个有效期内一个身份证号是否买过票
	 * 
	 * @return
	 */
	public String queryUsedCheckPoint(List<Long> checkinPointIds, List<String> idcardNos, Date playDate) {
		if (Check.NuNCollections(idcardNos) || Check.NuNCollections(checkinPointIds)) {
			return null;
		}
		String idcardNo = null;

		final List<VoucherConfirmUsedTimes> voucherConfirmUsedTimes = voucherConfirmReadMapper.queryVoucherConfimByParameter(checkinPointIds, idcardNos,
				playDate);
		if (!Check.NuNCollections(voucherConfirmUsedTimes)) {
			for (VoucherConfirmUsedTimes vcut : voucherConfirmUsedTimes) {
				if (!Check.NuNObject(vcut.getMaxUseTimes(), vcut.getUsedTimes())) {
					if (vcut.getUsedTimes() != vcut.getMaxUseTimes()) {
						idcardNo = vcut.getVoucherContent();
						break;
					}
				}
			}
		}
		return idcardNo;
	}
}
