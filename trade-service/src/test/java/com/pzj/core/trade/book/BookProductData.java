package com.pzj.core.trade.book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pzj.core.product.common.model.response.SkuProductOutModel;
import com.pzj.core.product.common.model.response.SpuSkuProductOutModel;
import com.pzj.framework.toolkit.Check;

public class BookProductData {
	private static Map<Long, SpuSkuProductOutModel> map = new HashMap<Long, SpuSkuProductOutModel>();

	public static SpuSkuProductOutModel getProduct(Long id) {
		if (Check.NuNMap(map)) {
			init();
		}
		return map.get(id);
	}

	private static void init() {
		List<SpuSkuProductOutModel> list = ServiceTestData.getInstance().createTestDataList("/book/product.json",
				SpuSkuProductOutModel.class);
		if (Check.NuNCollections(list)) {
			return;
		}
		for (SpuSkuProductOutModel dto : list) {
			if (Check.NuNCollections(dto.getSkuProductResuts()))
				continue;
			for (SkuProductOutModel sku : dto.getSkuProductResuts()) {
				map.put(sku.getId(), dto);
			}
		}
	}

}
