package com.pzj.core.trade.book.engine.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.pzj.core.trade.book.dao.entity.BookEntity;
import com.pzj.core.trade.book.engine.model.BookJsonEModel;
import com.pzj.core.trade.book.engine.model.ProductJsonEModel;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;

public enum BookJsonConverter {
	INTANCE;

	public BookJsonEModel getBookJson(BookEntity entity) {
		if (Check.NuNObject(entity) || Check.NuNString(entity.getBookDetail())) {
			return null;
		}
		return JSONConverter.toBean(entity.getBookDetail(), BookJsonEModel.class);
	}

	public Map<Long, ProductJsonEModel> getProductMap(BookJsonEModel json) {
		if (Check.NuNObject(json) || Check.NuNCollections(json.getProducts())) {
			return new HashMap<Long, ProductJsonEModel>();
		}
		return wrapProductJsonDto(json.getProducts());
	}

	public Map<Long, ProductJsonEModel> wrapProductJsonDto(List<ProductJsonEModel> originPriceDtos) {
		return Maps.uniqueIndex(originPriceDtos, indexProductJsonItemId());
	}

	private Function<ProductJsonEModel, Long> indexProductJsonItemId() {
		return new Function<ProductJsonEModel, Long>() {
			@Override
			public Long apply(ProductJsonEModel input) {
				return input.getProductId();
			}
		};
	}

}
