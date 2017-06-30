/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.order.watch.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.product.common.service.IProductCloseTimeslotService;
import com.pzj.core.trade.order.engine.exception.CloseDoorDogException;
import com.pzj.core.trade.order.engine.model.MerchModel;
import com.pzj.core.trade.order.watch.WatchDog;
import com.pzj.framework.context.Result;

/**
 * 关门狗.
 * @author CHJ
 *
 */
@Component(value = "closeDoorDog")
public class CloseDoorDog implements WatchDog {

	@Autowired
	private IProductCloseTimeslotService productCloseTimeslotService;

	@Override
	public boolean watch(MerchModel merchModel) {

		List<Date> closeTimes = getCloseTime(merchModel.getProdId());
		if (closeTimes == null) {
			return true;
		}

		for (Date closeTime : closeTimes) {
			if (closeTime.compareTo(merchModel.getPlayDate()) == 0) {
				throw new CloseDoorDogException(10404, "对不起，该日期[" + merchModel.getPlayDate() + "]商家未开放，请选择其他日期");
			}
		}

		return true;
	}

	private List<Date> getCloseTime(long prodId) {
		Result<ArrayList<Date>> closeTimes = null;
		//		try {
		//			closeTimes = productCloseTimeslotService.getBySkuId(prodId);
		//		} catch (Throwable e) {
		//			throw new CloseDoorDogException(10404, "产品[" + prodId + "], 获取关闭时间错误.", e);
		//		}
		//
		//		if (!closeTimes.isOk()) {
		//			throw new CloseDoorDogException(10404, "产品[" + prodId + "], 获取关闭时间错误. 错误码[" + closeTimes.getErrorCode()
		//					+ "], 错误描述信息[" + closeTimes.getErrorMsg() + "]");
		//		}

		return closeTimes.getData();
	}

}
