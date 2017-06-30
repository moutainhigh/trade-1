/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.order.watch.impl;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.exception.BuyNumLimitException;
import com.pzj.core.trade.order.engine.model.MerchModel;
import com.pzj.core.trade.order.engine.model.StrategyModel;
import com.pzj.core.trade.order.watch.WatchDog;

/**
 *
 * @author Administrator
 * @version $Id: PlayTimeFilter.java, v 0.1 2016年11月29日 下午4:00:40 Administrator Exp $
 */
@Component(value = "buyNumLimitDog")
public class BuyNumLimitDog implements WatchDog {

	//	public Result<Boolean> doFilter(List<PurchaseProductModel> products, HashMap<Long, ProductModel> productInfos) {
	//		//计算产品数量
	//		HashMap<Long, Integer> productNums = new HashMap<Long, Integer>();
	//		for (PurchaseProductModel product : products) {
	//			if (productNums.containsKey(product.getProductId())) {
	//				productNums.put(product.getProductId(),
	//						product.getProductNum() + productNums.get(product.getProductId()));
	//			}
	//			productNums.put(product.getProductId(), product.getProductNum());
	//		}
	//		//验证产品数量
	//		Iterator<Map.Entry<Long, Integer>> iterator = productNums.entrySet().iterator();
	//		while (iterator.hasNext()) {
	//			Entry<Long, Integer> entry = iterator.next();
	//			StrategyModel strategyModel = productInfos.get(entry.getKey()).getResellerStrategyModel();
	//			if (strategyModel.getLeastPerdueNumber() > entry.getValue()
	//					|| strategyModel.getMostPerdueNumber() < entry.getValue()) {
	//				throw new OrderException(10401, "每单最少购买" + strategyModel.getLeastPerdueNumber() + "份,最多购买"
	//						+ strategyModel.getMostPerdueNumber() + "份");
	//			}
	//		}
	//		return new Result<Boolean>();
	//	}

	@Override
	public boolean watch(MerchModel merchModel) {
		StrategyModel strategyModel = merchModel.getResellerStrategy();
		if (strategyModel.getLeastPerdueNumber() > merchModel.getBuyNum()) {
			throw new BuyNumLimitException(10410, "购买商品[" + merchModel.getProdId() + "]数量[" + merchModel.getBuyNum()
					+ "]少于该商品最低购买数量[" + strategyModel.getLeastPerdueNumber() + "]限制.");
		}
		if (strategyModel.getMostPerdueNumber() < merchModel.getBuyNum()) {
			throw new BuyNumLimitException(10410, "购买商品[" + merchModel.getProdId() + "]数量[" + merchModel.getBuyNum()
					+ "]大于该商品最高购买数量[" + strategyModel.getMostPerdueNumber() + "]限制.");
		}
		return true;
	}

}
