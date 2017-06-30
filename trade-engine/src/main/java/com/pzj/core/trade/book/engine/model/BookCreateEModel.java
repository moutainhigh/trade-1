package com.pzj.core.trade.book.engine.model;

import com.pzj.core.trade.book.dao.entity.BookEntity;
import com.pzj.core.trade.log.dao.entity.OperLogEntity;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;

public class BookCreateEModel {
	/**预约单实体*/
	private BookEntity entity = null;

	/**预约单Json串*/
	private BookJsonEModel json = null;

	/**日志信息*/
	private OperLogEntity log = null;

	public BookEntity getEntity() {
		if (this.entity == null) {
			this.entity = new BookEntity();
		}
		return entity;
	}

	public BookJsonEModel getJson() {
		if (this.json == null) {
			this.json = new BookJsonEModel();
		}
		return json;
	}

	public OperLogEntity generateLog(String event) {
		if (Check.NuNObject(this.entity)) {
			return null;
		}
		if (this.log == null) {
			this.log = new OperLogEntity();
			log.setOrderId(this.entity.getBookId());
			log.setOperator(this.entity.getOperatorId());
			log.setNext(this.entity.getBookStatus());
			log.setEvent(event);
		}

		return log;

	}

	@Override
	public String toString() {
		return JSONConverter.toJson(this);
	}

}
