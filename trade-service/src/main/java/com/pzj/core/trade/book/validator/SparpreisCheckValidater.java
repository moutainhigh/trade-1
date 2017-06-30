package com.pzj.core.trade.book.validator;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.book.engine.exception.BookException;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.model.SparpreisCheckModel;

@Component("sparpreisCheckValidater")
public class SparpreisCheckValidater implements ObjectConverter<SparpreisCheckModel, Void, Boolean> {

	@Override
	public Boolean convert(SparpreisCheckModel x, Void y) {
		if (Check.NuNObject(x)) {
			throw new BookException("特价票、免票订单审核参数错误.");
		}

		if (Check.NuNObject(x.getBookId())) {
			throw new BookException("特价票、免票订单审核参数错误, 特价票、免票订单号[" + x.getBookId() + "].");
		}
		if (x.getOperatorId() <= 0) {
			throw new BookException("特价票、免票订单审核参数错误, 操作人[" + x.getOperatorId() + "].");
		}
		if (Check.NuNString(x.getAuditor())) {
			throw new BookException("特价票、免票订单审核参数错误, 审核人[" + x.getAuditor() + "].");
		}

		return true;
	}

}
