package com.pzj.core.trade.book.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pzj.core.trade.book.engine.exception.BookException;
import com.pzj.core.trade.book.utils.BookUtil;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.common.BookTypeEnum;
import com.pzj.trade.book.model.BookInModel;
import com.pzj.trade.book.model.TouristModel;

public enum BaseBookValidater {
	INTANCE;

	public void valid(final BookInModel x) {
		if (Check.NuNObject(x)) {
			throw new BookException("预订单/特价票免票订单参数错误.");
		}

		if (x.getResellerId() <= 0 || x.getOperatorId() <= 0) {
			throw new BookException("预订单/特价票免票订单参数错误, 分销商[" + x.getResellerId() + "], 操作者[" + x.getOperatorId() + "].");
		}

		if (Check.NuNObject(x.getSupplierId()) || x.getSupplierId() <= 0) {
			throw new BookException("预订单/特价票免票订单参数错误, saas供应商[" + x.getSupplierId() + "].");
		}

		if (Check.NuNObject(x.getBookType())) {
			throw new BookException("预订单/特价票免票订单参数错误, 订单类型为空.");
		}

		if (!BookTypeEnum.isValidType(x.getBookType())) {
			throw new BookException("预订单/特价票免票订单参数错误, 订单类型错误[" + x.getBookType() + "].");
		}

		if (x.getSpuId() <= 0) {
			throw new BookException("预订单/特价票免票订单参数错误, 产品组[" + x.getSpuId() + "].");
		}

		//验证产品
		if (Check.NuNCollections(x.getProducts())) {
			throw new BookException("对不起，请至少选择一份产品.");
		}
		if (BookUtil.INTANCE.isSparpreisType(x.getBookType())) {
			if (Check.NuNObject(x.getDeliveryCode())) {
				throw new BookException("对不起，请输入出票密码.");
			}
			BookProductValidater.INTANCE.validateSparpreis(x.getProducts());

		} else {
			BookProductValidater.INTANCE.validateBooks(x.getProducts());
		}
		List<TouristModel> tourists = x.getTourists();
		if (!Check.NuNCollections(tourists)) {
			Set<String> set = new HashSet<String>();
			for (TouristModel item : tourists) {
				if (Check.NuNString(item.getIdcardNo())) {
					continue;
				}
				if (set.contains(item.getIdcardNo())) {
					throw new BookException("对不起，身份证号｛" + item.getIdcardNo() + "｝已存在。");
				}
				set.add(item.getIdcardNo());
			}
		}

	}

}
