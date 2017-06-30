/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.create.engine.filter;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.product.supplier.global.TimeUnitEnum;
import com.pzj.core.trade.create.engine.ContactBuyNumEngine;
import com.pzj.core.trade.order.engine.exception.OrderException;
import com.pzj.core.trade.order.engine.model.GainLimitModel;
import com.pzj.core.trade.order.engine.model.MerchBaseModel;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.model.MultiOrderInModel;
import com.pzj.trade.order.read.OrderReadMapper;

/**
 * 联系人限购
 * @author Administrator
 * @version $Id: ContactLimitFilter.java, v 0.1 2017年3月13日 下午3:59:14 Administrator Exp $
 */
@Component
public class ContactLimitFilter {
	@Autowired
	private OrderReadMapper orderReadMapper;

	@Autowired
	private ContactBuyNumEngine contactBuyNumEngine;

	public void filter(List<MerchBaseModel> merchs, MultiOrderInModel orderInModel) {
		int buyNum = 0;
		for (MerchBaseModel merch : merchs) {
			buyNum += merch.getBuyNum();
		}
		MerchBaseModel merch = merchs.get(0);
		GainLimitModel gainLimit = merch.getGainLimitModel();
		if (gainLimit == null) {
			return;
		}
		if (buyNum > gainLimit.getNum()) {
			throw new OrderException(14001, "对不起，该联系人" + gainLimit.getValue() + ""
					+ TimeUnitEnum.get(gainLimit.getUnit()).getMsg() + "内,还可购买" + 0 + "份产品,请重新选择购买份数");
		}
		if (Check.NuNObject(orderInModel.getContactee()) || Check.NuNObject(orderInModel.getContactee().getContactMobile())) {
			return;
		}

		int hadBuyNum = getHadBuyNum(orderInModel, merch, gainLimit);

		int buyableNum = gainLimit.getNum() - hadBuyNum;
		if (buyableNum < 0) {
			buyableNum = 0;
		}
		if (hadBuyNum + buyNum > gainLimit.getNum()) {
			throw new OrderException(14001, "对不起，该联系人" + gainLimit.getValue() + ""
					+ TimeUnitEnum.get(gainLimit.getUnit()).getMsg() + "内,还可购买" + buyableNum + "份产品,请重新选择购买份数");
		}

	}

	/**
	 * 
	 * @param orderInModel
	 * @param merch
	 * @param gainLimit
	 * @return
	 */
	private int getHadBuyNum(MultiOrderInModel orderInModel, MerchBaseModel merch, GainLimitModel gainLimit) {
		if (Check.NuNObject(orderInModel.getContactee())) {
			return 0;
		}
		Date createTime = new Date();
		if (gainLimit.getUnit() == TimeUnitEnum.DAY.getValue()) {
			createTime = new Date(createTime.getTime() - gainLimit.getValue() * 86400000);
		} else if (gainLimit.getUnit() == TimeUnitEnum.HOUR.getValue()) {
			createTime = new Date(createTime.getTime() - gainLimit.getValue() * 3600000);
		}

		int hadBuyNum = contactBuyNumEngine.getBuyNumByContactee(merch.getSpuId(), createTime, orderInModel.getContactee()
				.getContactMobile());
		return hadBuyNum;
	}
}
