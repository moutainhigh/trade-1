package com.pzj.core.trade.book.validator;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.book.engine.exception.BookException;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.model.DeliveryCodeVModel;

@Component("deliveryCodeValicater")
public class DeliveryCodeValicater implements ObjectConverter<DeliveryCodeVModel, Void, Boolean> {

	@Override
	public Boolean convert(DeliveryCodeVModel x, Void y) {
		if (Check.NuNObject(x)) {
			throw new BookException("验证特价票免票提货码参数错误.");
		}

		if (Check.NuNString(x.getBookId())) {
			throw new BookException("验证特价票免票提货码参数错误, 特价票、免票订单号[" + x.getBookId() + "].");
		}
		if (Check.NuNString(x.getDeliveryCode())) {
			throw new BookException("验证特价票免票提货码参数错误, 提货码[" + x.getDeliveryCode() + "].");
		}

		return true;
	}

}
