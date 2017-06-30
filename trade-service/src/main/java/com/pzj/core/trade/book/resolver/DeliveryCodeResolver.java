package com.pzj.core.trade.book.resolver;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.book.dao.entity.BookQueryEntity;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.trade.book.model.DeliveryCodeVModel;

@Component("deliveryCodeResolver")
public class DeliveryCodeResolver implements ObjectConverter<DeliveryCodeVModel, Void, BookQueryEntity> {

	@Override
	public BookQueryEntity convert(DeliveryCodeVModel x, Void y) {
		BookQueryEntity result = new BookQueryEntity();
		result.setBookId(x.getBookId());
		result.setDeliveryCode(x.getDeliveryCode());
		return result;

	}

}
