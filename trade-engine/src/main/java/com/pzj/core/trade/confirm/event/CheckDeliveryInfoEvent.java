/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.confirm.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.confirm.common.ConfirmErrorCode;
import com.pzj.core.trade.confirm.exception.ConfirmReqParamException;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.model.DeliveryDetailModel;
import com.pzj.trade.order.model.DeliveryWay;

/**
 * 检查核销配送信息
 *
 * @author DongChunfu
 * @version $Id: CheckDeliveryInfoEvent.java, v 0.1 2017年5月10日 上午11:53:50 DongChunfu Exp $
 */
@Component(value = "checkDeliveryInfoEvent")
public class CheckDeliveryInfoEvent {

	private static final Logger logger = LoggerFactory.getLogger(CheckDeliveryInfoEvent.class);

	public void check(final DeliveryDetailModel deliveryDetail, final int merchType) {
		if (!isDeliveryMerch(merchType)) {
			return;
		}

		if (Check.NuNObject(deliveryDetail)) {
			logger.error("核销特产类商品请求参数错误,核销特产类订单配送信息不得为空");
			throw new ConfirmReqParamException(ConfirmErrorCode.REQ_PARAM_ERROR_CODE, "核销特产类订单配送信息不得为空");
		}
		final String orderID = deliveryDetail.getOrderID();
		if (Check.NuNObject(orderID)) {
			logger.error("核销特产类商品请求参数错误,采购订单ID:[{}]不得为空.", orderID);
			throw new ConfirmReqParamException(ConfirmErrorCode.REQ_PARAM_ERROR_CODE, "核销特产类订单,采购订单ID不得为空");
		}
		final Integer deliveryWay = deliveryDetail.getDeliveryWay();
		if (Check.NuNObject(deliveryWay)) {
			logger.error("核销特产类商品请求参数错误,核销特产类订单,配送方式不符合要求");
			throw new ConfirmReqParamException(ConfirmErrorCode.REQ_PARAM_ERROR_CODE, "核销特产类订单,配送方式不符合要求");
		}

		//如果发货方式为快递配送,则需要验证快递公司与快递单号
		if (DeliveryWay.EXPRESS_SERVICE.getKey().intValue() == deliveryWay) {
			final String expressCompany = deliveryDetail.getExpressCompany();
			if (Check.NuNObject(expressCompany)) {
				logger.error("核销特产类商品请求参数错误,快递配送方式快递公司不符合要求:[{}].", expressCompany);
				throw new ConfirmReqParamException(ConfirmErrorCode.REQ_PARAM_ERROR_CODE, "核销特产类订单,快递配送方式快递公司不符合要求");

			}
			final String expressNO = deliveryDetail.getExpressNO();
			if (Check.NuNObject(expressNO)) {
				logger.error("核销特产类商品请求参数错误,快递配送方式快递单号不符合要求:[{}].", expressNO);
				throw new ConfirmReqParamException(ConfirmErrorCode.REQ_PARAM_ERROR_CODE, "核销特产类订单,快递配送方式快递单号不符合要求");
			}
		}
	}

	public boolean isDeliveryMerch(final int merchType) {
		return merchType == 5000;
	}
}
