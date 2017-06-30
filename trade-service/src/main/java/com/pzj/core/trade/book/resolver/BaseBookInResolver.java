package com.pzj.core.trade.book.resolver;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pzj.core.trade.book.dao.entity.BookEntity;
import com.pzj.core.trade.book.engine.model.BookCreateEModel;
import com.pzj.core.trade.book.engine.model.BookJsonEModel;
import com.pzj.core.trade.book.utils.BookUtil;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.model.BookInModel;

public abstract class BaseBookInResolver<X, Y> implements ObjectConverter<X, Y, BookCreateEModel> {

	private Logger logger = LoggerFactory.getLogger(BaseBookInResolver.class);

	@Resource(name = "bookOrderValidater")
	private ObjectConverter<BookCreateEModel, Boolean, Boolean> bookOrderValidater;

	@Override
	public BookCreateEModel convert(X x, Y y) {
		BookCreateEModel result = new BookCreateEModel();
		//1.设置基本信息
		copyBook(x, result.getEntity());

		//2.设置产品信息
		copyJson(x, result.getEntity(), result.getJson());

		//3.验证产品政策数据，获取产品库存规则和产品价格
		bookOrderValidater.convert(result, BookUtil.INTANCE.isBookType(result.getEntity().getBookType()));

		//4.产品信息转Json
		result.getEntity().setBookDetail(JSONConverter.toJson(result.getJson()));

		return result;
	}

	protected void copyBaseBook(BookInModel source, final BookEntity target) {

		target.setSrcBookId(source.getSrcBookId());

		target.setBookType(source.getBookType());

		target.setOperatorId(source.getOperatorId());

		target.setResellerId(source.getResellerId());

		if (!Check.NuNObject(source.getTravelDate())) {
			target.setTravelDate(source.getTravelDate().getTime());
		}

		target.setSpuId(source.getSpuId());

		target.setDeliveryCode(source.getDeliveryCode());

		target.setSupplierId(source.getSupplierId());

	}

	protected void copyBaseJson(BookInModel source, final BookEntity target, final BookJsonEModel json) {

		//1.基础信息
		json.setContactee(source.getContactee());
		json.setFilledModelList(source.getFilledModelList());
		json.setTourists(source.getTourists());
		json.setRemark(source.getRemark());
		json.setTravel(source.getTravel());
		json.setTravelDepartment(source.getTravelDepartment());
		json.setGuide(source.getGuide());
		json.setTouristSourceCity(source.getTouristSourceCity());
		json.setTouristSourceCountry(source.getTouristSourceCountry());
		json.setTouristSourceProvince(source.getTouristSourceProvince());

		//2.产品信息
		BookProductResolver.INSTANCE.copyBookProductJson(source, target, json);

	}

	protected abstract void copyBook(final X source, final BookEntity target);

	protected abstract void copyJson(final X source, final BookEntity target, final BookJsonEModel json);

}
