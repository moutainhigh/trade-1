package com.pzj.core.trade.book.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.book.engine.exception.BookException;
import com.pzj.core.trade.book.engine.model.BookCreateEModel;
import com.pzj.core.trade.book.engine.model.ProductJsonEModel;
import com.pzj.core.trade.order.build.MerchFilter;
import com.pzj.core.trade.order.engine.model.MerchBaseModel;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.common.SalePortEnum;
import com.pzj.trade.order.model.MultiOrderInModel;
import com.pzj.trade.order.model.MultiOrderProductModel;

@Component(value = "bookOrderValidater")
public class BookOrderValidater implements ObjectConverter<BookCreateEModel, Boolean, Boolean> {
	@Autowired
	private MerchFilter merchFilter;

	/**
	 * y 标志位 是否需要从产品政策获取价格
	 * @see com.pzj.framework.converter.ObjectConverter#convert(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Boolean convert(BookCreateEModel x, Boolean y) {

		MultiOrderInModel model = convertTOrder(x);

		//1.获取产品，政策等信息
		List<MerchBaseModel> merchs = merchFilter.assemble(model);

		//2.验证
		merchFilter.filter(merchs, model);

		//3.生成产品库存实体和产品价格
		copyProductPrice(merchs, x, y);

		return true;
	}

	private void copyProductPrice(List<MerchBaseModel> source, BookCreateEModel x, Boolean y) {
		if (Check.NuNCollections(source))
			return;
		if (Check.NuNCollections(x.getJson().getProducts()))
			return;
		Map<Long, MerchBaseModel> productMap = new HashMap<Long, MerchBaseModel>();
		for (MerchBaseModel model : source) {
			if (productMap.containsKey(model.getProdId()))
				continue;
			productMap.put(model.getProdId(), model);
		}

		int totalAmount = 0;
		for (ProductJsonEModel json : x.getJson().getProducts()) {
			if (!productMap.containsKey(json.getProductId())) {
				throw new BookException("创建预订单/特价票免票参数错误,产品id[" + json.getProductId() + "]没有获取到有效的产品.");
			}
			MerchBaseModel model = productMap.get(json.getProductId());
			if (model.getSpuId() != x.getEntity().getSpuId().longValue()) {
				throw new BookException("创建预订单/特价票免票参数错误,产品id[" + json.getProductId() + "]与传入的父产品id["
						+ x.getEntity().getSpuId() + "]不对应.");
			}
			setProductStockInfo(model, json);

			//获取产品价格
			if (y) {
				json.setPrice((int) (model.getPrice() * 100));
			}

			totalAmount += json.getTotalAmount();
		}
		x.getEntity().setTotalAmount(totalAmount);
		//		x.getEntity().setTravelDate(source.get(0).getPlayDate().getTime());

	}

	private void setProductStockInfo(MerchBaseModel source, ProductJsonEModel target) {
		target.setStockRuleId(source.getStock_rule_id());
		if (!Check.NuNString(source.getRonda()))
			target.setScreeningsId(Long.valueOf(source.getRonda()));
		if (!Check.NuNString(source.getRegion()))
			target.setAreaId(Long.valueOf(source.getRegion()));

	}

	private MultiOrderInModel convertTOrder(BookCreateEModel x) {
		MultiOrderInModel model = new MultiOrderInModel();
		model.setBookId(x.getEntity().getBookId());
		model.setBookType(x.getEntity().getBookType());
		model.setContactee(x.getJson().getContactee());
		model.setFilleds(x.getJson().getFilledModelList());
		model.setGuideIdName(x.getJson().getGuide());
		model.setOperatorId(x.getEntity().getOperatorId());
		model.setResellerId(x.getEntity().getResellerId());
		model.setTourists(x.getJson().getTourists());
		model.setSalePort(SalePortEnum.OFFLINE_WINDOW.getValue());
		if (!Check.NuNObject(x.getEntity().getTravelDate())) {
			model.setTravelDate(x.getEntity().getTravelDate());
		}
		model.setTravelDepartName(x.getJson().getTravelDepartment());

		List<MultiOrderProductModel> products = new ArrayList<MultiOrderProductModel>();
		if (!Check.NuNCollections(x.getJson().getProducts())) {
			for (ProductJsonEModel json : x.getJson().getProducts()) {
				if (Check.NuNObject(json))
					continue;
				products.add(convertOrderProduct(json));
			}
		}
		model.setProducts(products);
		return model;

	}

	private MultiOrderProductModel convertOrderProduct(ProductJsonEModel x) {
		MultiOrderProductModel model = new MultiOrderProductModel();
		model.setPrice(x.getPrice());
		model.setProductId(x.getProductId());
		model.setProductNum(x.getBuyNum());
		model.setStrategyRelationId(x.getStrategyRelationId());
		model.setSeats(x.getSeats());
		model.setParentUserId(x.getParentUserId());
		model.setSubUserId(x.getSubUserId());
		model.setScenicId(x.getScenicId());
		return model;

	}

}
