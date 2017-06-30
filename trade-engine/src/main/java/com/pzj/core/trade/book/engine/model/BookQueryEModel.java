package com.pzj.core.trade.book.engine.model;

import com.pzj.core.trade.book.dao.entity.BookEntity;
import com.pzj.core.trade.log.dao.entity.OperLogEntity;

public class BookQueryEModel {
	private BookEntity entity;

	private OperLogEntity log;

	public BookEntity getEntity() {
		return entity;
	}

	public void setEntity(BookEntity entity) {
		this.entity = entity;
	}

	public OperLogEntity getLog() {
		return log;
	}

	public void setLog(OperLogEntity log) {
		this.log = log;
	}

}
