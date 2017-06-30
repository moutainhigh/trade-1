package com.pzj.core.trade.book.resolver;

import java.util.List;

import com.pzj.core.trade.book.dao.entity.BookEntity;
import com.pzj.core.trade.book.engine.model.BookJsonEModel;
import com.pzj.core.trade.book.engine.model.ProductJsonEModel;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.model.BookInModel;
import com.pzj.trade.book.model.BookProductModel;

public enum BookProductResolver {
	INSTANCE;

	public void copyBookProductJson(final BookInModel source, final BookEntity entity,
			final BookJsonEModel jsonTarget) {
		boolean jsonResult = !Check.NuNObject(jsonTarget);
		int totalNum = 0;
		for (BookProductModel product : source.getProducts()) {
			if (jsonResult) {
				ProductJsonEModel jsonModel = convertBookProductJson(product);
				jsonTarget.addProduct(jsonModel);
			}
			totalNum += product.getBuyNum();

		}

		if (!Check.NuNObject(entity)) {
			entity.setTotalNum(totalNum);
		}

	}

	public void copyBookProductList(final List<ProductJsonEModel> source, final List<BookProductModel> target) {
		if (Check.NuNCollections(source) || Check.NuNObject(target)) {
			return;
		}
		for (ProductJsonEModel json : source) {
			if (Check.NuNObject(json))
				continue;
			target.add(convertBookProduct(json));
		}

	}

	private void copyBookProduct(final ProductJsonEModel source, final BookProductModel target) {
		target.setBuyNum(source.getBuyNum());
		target.setProductId(source.getProductId());
		target.setSeats(source.getSeats());
		target.setPrice(source.getPrice());
		target.setStrategyRelationId(source.getStrategyRelationId());
		target.setParentUserId(source.getParentUserId());
		target.setSubUserId(source.getSubUserId());
		target.setAreaId(source.getAreaId());
		target.setScreeningsId(source.getScreeningsId());
		target.setProductName(source.getProductName());
		target.setScenicId(source.getScenicId());
		target.setStrategyId(source.getStrategyId());

	}

	private final BookProductModel convertBookProduct(final ProductJsonEModel source) {
		BookProductModel result = new BookProductModel();
		copyBookProduct(source, result);
		return result;

	}

	private void copyBookProductJson(final BookProductModel source, final ProductJsonEModel target) {
		target.setBuyNum(source.getBuyNum());

		target.setProductId(source.getProductId());
		target.setSeats(source.getSeats());
		target.setPrice(source.getPrice());
		target.setStrategyRelationId(source.getStrategyRelationId());
		target.setParentUserId(source.getParentUserId());
		target.setSubUserId(source.getSubUserId());
		target.setAreaId(target.getAreaId());
		target.setScreeningsId(target.getScreeningsId());
		target.setProductName(target.getProductName());
		target.setScenicId(source.getScenicId());
		target.setStrategyId(source.getStrategyId());

	}

	private final ProductJsonEModel convertBookProductJson(final BookProductModel source) {
		ProductJsonEModel result = new ProductJsonEModel();
		copyBookProductJson(source, result);
		return result;

	}

}
