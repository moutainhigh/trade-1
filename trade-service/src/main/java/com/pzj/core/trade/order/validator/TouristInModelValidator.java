package com.pzj.core.trade.order.validator;

import org.springframework.stereotype.Component;

import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.model.TouristEditInModel;
import com.pzj.voucher.engine.exception.TouristInModelException;

/**
 * 游客信息修改入參模型验证器.
 * @author YRJ
 *
 */
@Component(value = "touristInModelValidator")
public class TouristInModelValidator {

	public void validate(final TouristEditInModel inModel) {
		if (inModel == null) {
			throw new TouristInModelException(10401, "游客信息不可修改, 请检查参数是否合法.");
		}

		if (Check.NuNStrStrict(inModel.getOrderId())) {
			throw new TouristInModelException(10401, "修改游客信息, 请指定订单号.");
		}

		if (inModel.getTouristId() < 1) {
			throw new TouristInModelException(10402, "修改游客信息, 请指定游客标识.");
		}
		if (Check.NuNObject(inModel.getCardId()) && Check.NuNObject(inModel.getMobile()) && Check.NuNObject(inModel.getName())) {
			throw new TouristInModelException(10402, "修改游客信息,请指定游客修改内容.");
		}
	}
}
