package com.pzj.core.trade.order.build;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.product.common.model.response.SkuProductOutModel;
import com.pzj.core.product.common.model.response.SpuProductOutModel;
import com.pzj.core.product.common.model.response.SpuSkuProductOutModel;
import com.pzj.core.product.common.service.ISpuProductService;
import com.pzj.core.product.model.screeings.ScreeingsModel;
import com.pzj.core.product.service.ScreeingsQueryService;
import com.pzj.core.trade.order.engine.exception.OrderException;
import com.pzj.core.trade.order.engine.exception.ProductConvertException;
import com.pzj.core.trade.order.engine.model.MerchBaseModel;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;

/**
 * 商品模型转换策略.
 * 
 * @author chj
 *
 */
@Component
public class MerchAssembler {

	private final static Logger logger = LoggerFactory.getLogger(MerchAssembler.class);

	@Autowired
	private ISpuProductService spuProductService;

	@Autowired
	private ScreeingsQueryService screeingsQueryService;

	public MerchBaseModel assemble(final Long prodId, MerchBaseModel merch) {
		final SpuSkuProductOutModel outModel = getProductOutModel(prodId);

		final SpuProductOutModel spuModel = outModel.getSpuProductOutModel();
		final SkuProductOutModel skuModel = filterSkuModel(prodId, outModel.getSkuProductResuts());

		generateMerchModel(spuModel, skuModel, merch);

		getShowTime(skuModel, merch);
		return merch;
	}

	/**
	 * 获取演艺开始结束时间
	 * @param skuModel
	 * @param merch
	 */
	private void getShowTime(final SkuProductOutModel skuModel, final MerchBaseModel merch) {
		if (!Check.NuNObject(skuModel.getRondaId()) && !skuModel.getRondaId().equals(0)) {
			final Result<ScreeingsModel> screeResult = getscreening(skuModel);
			final Calendar calendar = Calendar.getInstance();
			final String startTime = screeResult.getData().getStartTime();
			final String endTime = screeResult.getData().getEndTime();
			if (Check.NuNObject(startTime, endTime)) {
				return;
			}

			Date showDate = merch.getPlayDate();
			if (merch.getShow_start_time() != null) {
				showDate = merch.getShow_start_time();
			}
			calendar.setTime(showDate);

			operateShowTime(calendar, startTime);
			merch.setShow_start_time(calendar.getTime());

			operateShowTime(calendar, endTime);
			merch.setShow_end_time(calendar.getTime());

		}
	}

	/**
	 * 
	 * @param calendar
	 * @param endTime
	 */
	private void operateShowTime(final Calendar calendar, final String endTime) {
		String[] ends = endTime.split(":");
		if (Check.NuNArray(ends) || ends.length != 2) {
			logger.error("产品演义时间配置有误" + endTime);
			throw new OrderException(15001, "产品演义时间配置有误");
		}
		calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(ends[0]));
		calendar.set(Calendar.MINUTE, Integer.valueOf(ends[1]));
	}

	/**
	 * 获取演艺场次
	 * @param skuModel
	 * @return
	 */
	private Result<ScreeingsModel> getscreening(final SkuProductOutModel skuModel) {
		final Result<ScreeingsModel> screeResult = screeingsQueryService.queryScreeingDetail(
				Long.parseLong(skuModel.getRondaId()), null);
		if (!screeResult.isOk()) {
			logger.error("查询场次信息报错，场次ID:" + skuModel.getRondaId());
			throw new OrderException(15001, "查询场次信息报错，场次ID:" + skuModel.getRondaId() + screeResult.getErrorMsg());
		}
		if (Check.NuNObject(screeResult.getData())) {
			logger.error("查询场次信息报错，场次ID:" + skuModel.getRondaId());
			throw new OrderException(15001, "查询场次信息报错，场次ID:" + skuModel.getRondaId() + screeResult.getErrorMsg());
		}
		return screeResult;
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
			throw new OrderException(10500, "根据产品ID[" + prodId + "]获取产品模型失败, 返回错误码[" + result.getErrorCode() + "], 错误描述["
					+ result.getErrorMsg() + "].");
		}

		if (logger.isDebugEnabled()) {
			logger.info("根据产品ID[" + prodId + "]获取产品SPU模型信息 -->" + (JSONConverter.toJson(result)));
		}

		final SpuSkuProductOutModel outModel = result.getData();
		if (Check.NuNObject(outModel)) {
			throw new OrderException(10500, "根据产品ID[" + prodId + "]获取产品模型失败, 返回错误码[" + result.getErrorCode() + "], 错误描述["
					+ result.getErrorMsg() + "], 产品模型为空.");
		}
		final SpuProductOutModel spuProductOut = outModel.getSpuProductOutModel();
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
	private MerchBaseModel generateMerchModel(final SpuProductOutModel spuModel, final SkuProductOutModel skuModel,
			final MerchBaseModel merchModel) {

		// 1. 转换SPU信息.
		merchModel.setSpuId(spuModel.getId());
		merchModel.setSupplierId(spuModel.getSupplierId());
		merchModel.setProdVarie(spuModel.getTicketVarie());
		merchModel.setSaleStartDate(skuModel.getSaleStartDate());
		merchModel.setSaleEndDate(skuModel.getSaleEndDate());
		merchModel.setUseStartDate(skuModel.getUseStartDate());
		merchModel.setUseEndDate(skuModel.getUseEndDate());
		merchModel.setProdName(spuModel.getName());

		// 2. 转换SKU信息.
		merchModel.setProdId(skuModel.getId().longValue());
		merchModel.setSkuName(skuModel.getSkuName());
		merchModel.setProdType(skuModel.getProductType());
		merchModel.setIsDock(skuModel.getDockType().getValue());
		merchModel.setSupplierId(Long.valueOf(skuModel.getSupplierId()));
		merchModel.setStock_rule_id(skuModel.getStockRuleId());
		//		merchModel.setRegion(skuModel.getRegion());
		//		merchModel.setRonda(skuModel.getRonda());
		merchModel.setRegion(skuModel.getRegionId());
		merchModel.setRonda(skuModel.getRondaId());
		merchModel.setSupplierId(Long.valueOf(skuModel.getSupplierId()));
		return merchModel;
	}
}
