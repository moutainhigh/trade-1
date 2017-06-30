/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.engine;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.voucher.read.VoucherConfirmReadMapper;
import com.pzj.trade.order.model.OrderMerchConfirmsRespModel;
import com.pzj.voucher.entity.VoucherConfirm;

/**
 * 
 * @author Administrator
 * @version $Id: OrderMerchConfirmQueryEngine.java, v 0.1 2017年3月22日 下午5:59:58 Administrator Exp $
 */

@Component(value = "orderMerchConfirmQueryEngine")
public class OrderMerchConfirmQueryEngine {

	@Resource(name = "voucherConfirmReadMapper")
	private VoucherConfirmReadMapper voucherConfirmReadMapper;

	public ArrayList<OrderMerchConfirmsRespModel> queryMerchConfirms(long productId, long voucheId) {
		List<VoucherConfirm> voucherConfirms = voucherConfirmReadMapper.queryVoucherConfimByProductId(productId, voucheId);
		ArrayList<OrderMerchConfirmsRespModel> orderMerchConfirmsRespModels = new ArrayList<OrderMerchConfirmsRespModel>();
		for (VoucherConfirm voucherConfirm : voucherConfirms) {
			OrderMerchConfirmsRespModel orm = new OrderMerchConfirmsRespModel();
			if (voucherConfirm.getChildProductId() != null) {
				orm.setCheckPoint(voucherConfirm.getChildProductId().toString());
			}
			orm.setCheckedTime(voucherConfirm.getConfirmTime());
			if (voucherConfirm.getUsedTimes() > 0) {
				orm.setIsChecked(1);
			} else {
				orm.setIsChecked(2);
			}
			orderMerchConfirmsRespModels.add(orm);
		}

		return orderMerchConfirmsRespModels;
	}
}
