/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.order.validator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.exception.NullContacteeException;
import com.pzj.core.trade.order.engine.exception.OrderArgsException;
import com.pzj.core.trade.order.engine.exception.SalePortException;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.common.SalePortEnum;
import com.pzj.trade.order.model.ContacteeModel;
import com.pzj.trade.order.vo.PurchaseProductVO;
import com.pzj.trade.order.vo.TradeOrderVO;

/**
 *
 * @author chj
 * @订单验证
 */
@Component(value = "orderArgsValidater")
public class OrderArgsValidater implements ObjectConverter<TradeOrderVO, Void, Void> {

	@Override
	public Void convert(TradeOrderVO reqModel, Void arg1) {
		if (Check.NuNObject(reqModel)) {
			throw new OrderArgsException(10400, "创建订单参数错误.");
		}

		if (reqModel.getResellerId() <= 0 || reqModel.getOperatorId() <= 0 || reqModel.getPayerId() <= 0) {
			throw new OrderArgsException(10400, "创建订单参数错误, 分销商[" + reqModel.getResellerId() + "], 操作者["
					+ reqModel.getOperatorId() + "], 付款者[" + reqModel.getPayerId() + "].");
		}

		SalePortEnum port = SalePortEnum.getSalePort(reqModel.getSalePort());
		if (port == null) {
			throw new SalePortException(10400, "销售端口[" + reqModel.getSalePort() + "]错误.");
		}

		List<PurchaseProductVO> products = reqModel.getProducts();
		illegalPurchaseProductModel(products);

		ContacteeModel contact = new ContacteeModel();
		contact.setContactMobile(reqModel.getContactMobile());
		illegalContactee(contact);

		return null;
	}

	/**
	 * 选购商品信息验证.
	 * @param products
	 */
	private void illegalPurchaseProductModel(List<PurchaseProductVO> products) {
		//这里不建议使用Check.NuNCollection()判断. 因为, List数据结构中是允许为空, 当选择的多个商品中有一个为空, 为最大限度的达成交易, 对内部为空对象过滤.
		if (/*Check.NuNCollections(products)*/products == null || products.isEmpty()) {
			throw new OrderArgsException(10400, "创建订单参数错误, 请选择购买的商品.");
		}

		for (PurchaseProductVO product : products) {
			if (Check.NuNObject(product)) {
				continue;// Ignore.
			}
			int buyNum = product.getProductNum();
			if (buyNum <= 0) {
				throw new OrderArgsException(10400, "选购的商品数量[" + buyNum + "]错误, 请选择至少一个.");
			}
			long prodId = product.getProductId();
			if (prodId <= 0) {
				throw new OrderArgsException(10400, "选购的商品[" + prodId + "]不存在, 请选择一个合法的商品.");
			}
		}
	}

	/**
	 * 联系人信息验证.
	 * @param mobile
	 */
	private void illegalContactee(ContacteeModel contact) {
		if (Check.NuNObject(contact)) {
			throw new NullContacteeException(10400, "创建订单参数错误, 请指定联系人信息.");
		}

		String mobile = contact.getContactMobile();
		if (Check.NuNStrStrict(mobile) || mobile.trim().length() != 11) {
			throw new NullContacteeException(10400, "联系人信息[" + contact + "]错误, 请填写正确的手机号.");
		}
	}

}
