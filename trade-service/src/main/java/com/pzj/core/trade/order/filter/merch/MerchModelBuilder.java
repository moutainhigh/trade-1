package com.pzj.core.trade.order.filter.merch;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.product.common.model.response.SkuProductOutModel;
import com.pzj.core.product.common.model.response.SpuProductOutModel;
import com.pzj.core.product.common.model.response.SpuSkuProductOutModel;
import com.pzj.core.product.common.service.ISpuProductService;
import com.pzj.core.trade.order.engine.exception.OrderException;
import com.pzj.core.trade.order.engine.exception.ProductConvertException;
import com.pzj.core.trade.order.engine.model.MerchModel;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;

/**
 * 商品模型转换策略.
 * 
 * @author YRJ
 *
 */
@Component(value = "merchModelBuilder")
class MerchModelBuilder implements ObjectConverter<Long, Void, MerchModel> {

	private final static Logger logger = LoggerFactory.getLogger(MerchModelBuilder.class);

	@Autowired
	private ISpuProductService spuProductService;

	@Override
	public MerchModel convert(final Long prodId, final Void y) {
		final SpuSkuProductOutModel outModel = getProductOutModel(prodId);

		final SpuProductOutModel spuModel = outModel.getSpuProductOutModel();
		final SkuProductOutModel skuModel = filterSkuModel(prodId, outModel.getSkuProductResuts());

		final MerchModel merchModel = generateMerchModel(spuModel, skuModel);
		return merchModel;
	}

	/**
	 * 从产品服务获取产品模型.
	 * 
	 * @param prodId
	 */
	private SpuSkuProductOutModel getProductOutModel(final long prodId) {
		Result<SpuSkuProductOutModel> result;
		try {
			result = spuProductService.getSkuWithSpuBySkuId(prodId);
		} catch (final Throwable e) {
			throw new OrderException(10500, "根据产品ID[" + prodId + "]获取产品模型失败.", e);
		}

		if (!result.isOk()) {
			throw new OrderException(10500, "根据产品ID[" + prodId + "]获取产品模型失败, 返回错误码[" + result.getErrorCode() + "], 错误描述[" + result.getErrorMsg() + "].");
		}

		if (logger.isDebugEnabled()) {
			logger.info("根据产品ID[" + prodId + "]获取产品SPU模型信息 -->" + (JSONConverter.toJson(result)));
		}

		final SpuSkuProductOutModel outModel = result.getData();
		if (Check.NuNObject(outModel)) {
			throw new OrderException(10500, "根据产品ID[" + prodId + "]获取产品模型失败, 返回错误码[" + result.getErrorCode() + "], 错误描述[" + result.getErrorMsg() + "], 产品模型为空.");
		}
		SpuProductOutModel spuProductOut = outModel.getSpuProductOutModel();
		if (!spuProductOut.getFlag().equals(1)) {
			throw new OrderException(10400, "对不起，产品已下架，请购买其他产品");
		}

		return outModel;
	}

	/**
	 * 根据购买的商品, 过滤SKU产品模型.
	 * 
	 * @param prodId
	 * @param skuProductResuts
	 * @return
	 */
	private SkuProductOutModel filterSkuModel(final long prodId, final ArrayList<SkuProductOutModel> skuModels) {
		SkuProductOutModel outModel = null;
		if (skuModels != null) {
			for (final SkuProductOutModel skuModel : skuModels) {
				if (skuModel.getId().longValue() == prodId) {
					outModel = skuModel;
					break;
				}
			}
		}

		if (outModel == null) {
			logger.warn("筛选商品[" + prodId + "]的SKU模型失败, SKU模型 --> " + (JSONConverter.toJson(skuModels)));
			throw new ProductConvertException(10501, "筛选商品[" + prodId + "]的SKU模型失败");
		}
		return outModel;
	}

	/**
	 * 根据产品模型转换为系统内部交易商品对象模型.
	 * 
	 * @param outModel
	 */
	private MerchModel generateMerchModel(final SpuProductOutModel spuModel, final SkuProductOutModel skuModel) {
		final MerchModel merchModel = new MerchModel(skuModel.getId());

		// 1. 转换SPU信息.
		merchModel.setSupplierId(spuModel.getSupplierId());
		merchModel.setProdVarie(spuModel.getTicketVarie());
		merchModel.setSaleStartDate(spuModel.getSaleStartDate());
		merchModel.setSaleEndDate(spuModel.getSaleEndDate());
		merchModel.setUseStartDate(spuModel.getUseStartDate());
		merchModel.setUseEndDate(spuModel.getUseEndDate());

		// 2. 转换SKU信息.
		merchModel.setProdId(skuModel.getId().longValue());
		merchModel.setProdName(skuModel.getProductName());
		merchModel.setProdType(skuModel.getProductType());
		merchModel.setIsDock(skuModel.getDockType().getValue());
		return merchModel;
	}
}
