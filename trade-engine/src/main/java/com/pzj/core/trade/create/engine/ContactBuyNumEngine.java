/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.create.engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.product.common.model.response.SkuProductOutModel;
import com.pzj.core.product.common.model.response.SpuSkuProductOutModel;
import com.pzj.core.product.common.service.ISpuProductService;
import com.pzj.core.trade.order.engine.exception.OrderException;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.read.OrderReadMapper;

/**
 * 
 * @author Administrator
 * @version $Id: ContactBuyNumEngine.java, v 0.1 2017年4月25日 下午4:39:34 Administrator Exp $
 */
@Component
public class ContactBuyNumEngine {
	private final static Logger logger = LoggerFactory.getLogger(ContactBuyNumEngine.class);
	@Autowired
	private ISpuProductService spuProductService;

	@Autowired
	private OrderReadMapper orderReadMapper;

	public int getBuyNumByContactee(long spuId, Date createTime, String contacteeMobile) {
		ArrayList<SkuProductOutModel> skus = getProductOutModel(spuId);
		List<Long> skuIds = new ArrayList<Long>();
		for (SkuProductOutModel sku : skus) {
			skuIds.add(sku.getId());
		}
		if (skuIds.isEmpty()) {
			return 0;
		}
		int hadBuyNum = orderReadMapper.getMerchCountByContactee(skuIds, createTime, contacteeMobile);
		return hadBuyNum;
	}

	/**
	 * 从产品服务获取产品模型.
	 * 
	 * @param prodId
	 */
	private ArrayList<SkuProductOutModel> getProductOutModel(final long spuId) {
		Result<SpuSkuProductOutModel> result;
		try {
			result = spuProductService.getSpuWithSkusBySpuId(spuId);
		} catch (final Throwable e) {
			throw new OrderException(10500, "根据产品组ID[" + spuId + "]获取产品模型失败.", e);
		}

		if (!result.isOk()) {
			throw new OrderException(10500, "根据产品组ID[" + spuId + "]获取产品模型失败, 返回错误码[" + result.getErrorCode() + "], 错误描述["
					+ result.getErrorMsg() + "].");
		}

		if (logger.isDebugEnabled()) {
			logger.info("根据产品组ID[" + spuId + "]获取产品SPU模型信息 -->" + (JSONConverter.toJson(result)));
		}

		final SpuSkuProductOutModel outModel = result.getData();
		if (Check.NuNObject(outModel)) {
			throw new OrderException(10500, "根据产品组ID[" + spuId + "]获取产品模型失败, 返回错误码[" + result.getErrorCode() + "], 错误描述["
					+ result.getErrorMsg() + "], 产品模型为空.");
		}

		return outModel.getSkuProductResuts();
	}
}
