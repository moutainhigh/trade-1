package com.pzj.core.trade.order.build;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.product.common.model.response.ProductSiteOutModel;
import com.pzj.core.product.common.model.response.SpuSkuScenicProductOutModel;
import com.pzj.core.product.common.service.ISpuProductService;
import com.pzj.core.product.service.ScreeingsQueryService;
import com.pzj.core.trade.order.engine.exception.OrderException;
import com.pzj.core.trade.order.engine.model.CheckinPointModel;
import com.pzj.core.trade.order.engine.model.MerchBaseModel;
import com.pzj.framework.context.Result;
import com.pzj.framework.toolkit.Check;

/**
 * 检票点.
 * 
 * @author chj
 *
 */
@Component
public class CheckinPointAssembler {

	private final static Logger logger = LoggerFactory.getLogger(CheckinPointAssembler.class);

	@Autowired
	private ISpuProductService spuProductService;

	@Autowired
	private ScreeingsQueryService screeingsQueryService;

	/**
	 * 对于所有产品，查询检票点
	 * 
	 * @param checkinPointModels
	 * @param merch
	 */
	public void assemble(List<CheckinPointModel> checkinPointModels, final MerchBaseModel merch, List<Long> checkinPoints) {
		List<Long> checkinPointIds = new ArrayList<Long>();
		if (Check.NuNCollections(checkinPointModels)) {
			checkinPointModels = new ArrayList<CheckinPointModel>();

			List<ProductSiteOutModel> sites = getProductOutModel(merch.getSpuId());
			if (!Check.NuNCollections(sites)) {
				for (ProductSiteOutModel site : sites) {
					if (!Check.NuNCollections(checkinPoints)) {

						logger.info("产品组[" + merch.getSpuId() + "]获取的检票点id是：" + site.getId());
						if (!checkinPoints.contains(site.getId())) {
							continue;
						}
					}
					CheckinPointModel sceneBaseModel = new CheckinPointModel();
					sceneBaseModel.setCheckinPointId(site.getId());
					checkinPointIds.add(site.getId());
					sceneBaseModel.setMaxUseTimes(site.getCheckLimitCount());
					checkinPointModels.add(sceneBaseModel);

				}
			}
		}
		merch.setCheckinPointIds(checkinPointIds);
		merch.setCheckinPointModels(checkinPointModels);
	}

	/**
	 * 从产品服务获取检票点.
	 * 
	 * @param prodId
	 */
	private List<ProductSiteOutModel> getProductOutModel(final long spuId) {
		Result<SpuSkuScenicProductOutModel> result;
		try {
			result = spuProductService.getSpuSkuScenicBySpuId(spuId);
		} catch (final Throwable e) {
			throw new OrderException(10500, "根据产品组ID[" + spuId + "]获取产品模型失败.", e);
		}

		if (!result.isOk()) {
			throw new OrderException(10500, "根据产品组ID[" + spuId + "]获取产品模型失败, 返回错误码[" + result.getErrorCode() + "], 错误描述[" + result.getErrorMsg() + "].");
		}

		final SpuSkuScenicProductOutModel outModel = result.getData();
		if (Check.NuNObject(outModel)) {
			throw new OrderException(10500, "根据产品组ID[" + spuId + "]获取产品模型失败, 返回错误码[" + result.getErrorCode() + "], 错误描述[" + result.getErrorMsg() + "], 产品模型为空.");
		}
		List<ProductSiteOutModel> sites = outModel.getProductSiteOutModel();

		return sites;
	}

}
