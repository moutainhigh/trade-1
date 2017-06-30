package com.pzj.core.trade.book.utils;

import com.pzj.trade.book.common.BookTypeEnum;

public enum BookUtil {
	INTANCE;
	public Boolean isBookType(int type) {
		if (type == BookTypeEnum.BOOK.getType() || type == BookTypeEnum.BOOK_PRE_ORDER.getType()) {
			return true;
		}
		return false;
	}

	public Boolean isSparpreisType(int type) {
		if (type == BookTypeEnum.CHEAP_TICKET.getType() || type == BookTypeEnum.FREE_TICKET.getType()) {
			return true;
		}
		return false;
	}

}
