package com.pzj.core.trade.book.resolver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pzj.core.trade.book.dao.entity.BookEntity;
import com.pzj.core.trade.book.dao.entity.BookQueryEntity;
import com.pzj.core.trade.book.engine.model.BookJsonEModel;
import com.pzj.core.trade.book.engine.model.BookQueryEModel;
import com.pzj.core.trade.book.engine.model.BookQueryResultEModel;
import com.pzj.core.trade.log.dao.entity.OperLogEntity;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.entity.QueryResult;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.common.BookTypeEnum;
import com.pzj.trade.book.model.BookDetailModel;
import com.pzj.trade.book.model.BookQueryInModel;
import com.pzj.trade.book.model.BookQueryOutModel;
import com.pzj.trade.book.model.SparpreisQueryInModel;
import com.pzj.trade.book.model.SparpreisQueryOutModel;

public enum BookQueryResolver {
	INTANCE;

	public BookQueryEntity convertQueryModel(BookQueryInModel model) {
		BookQueryEntity result = new BookQueryEntity();
		result.setResellerIds(model.getResellerIds());
		result.setBookStatus(model.getBookStatus());
		result.setBookType(BookTypeEnum.BOOK.getType());
		result.setBookId(model.getBookId());
		result.setOperatorId(model.getOperatorId());
		result.setSupplierId(model.getSupplierId());
		if (!Check.NuNObject(model.getQueryStartDate())) {
			result.setQueryTravelSDate(model.getQueryStartDate().getTime());
		}
		if (!Check.NuNObject(model.getQueryEndDate())) {
			result.setQueryTravelEDate(model.getQueryEndDate().getTime());
		}

		if (!Check.NuNObject(model.getPage())) {
			result.setPage(model.getPage());
		}
		result.setOrderByClause("travel_date asc,book_id ASC");

		return result;

	}

	public BookQueryEntity convertQueryModel(SparpreisQueryInModel model) {
		BookQueryEntity result = new BookQueryEntity();
		result.setBookId(model.getBookId());
		result.setResellerIds(model.getResellerIds());
		result.setBookStatus(model.getBookStatus());
		result.setSupplierId(model.getSupplierId());
		if (Check.NuNObject(model.getBookType())) {
			List<Integer> bookTypes = new ArrayList<Integer>();
			bookTypes.add(BookTypeEnum.CHEAP_TICKET.getType());
			bookTypes.add(BookTypeEnum.FREE_TICKET.getType());
			result.setBookTypes(bookTypes);
		} else {
			result.setBookType(model.getBookType());
		}

		result.setOperatorId(model.getOperatorId());
		result.setDeliveryCode(model.getDeliveryCode());
		if (!Check.NuNObject(model.getQueryStartDate())) {
			result.setQueryBookSDate(model.getQueryStartDate().getTime());
		}
		if (!Check.NuNObject(model.getQueryEndDate())) {
			result.setQueryBookEDate(model.getQueryEndDate().getTime());
		}

		if (!Check.NuNObject(model.getPage())) {
			result.setPage(model.getPage());
		}
		String orderByClause = SparpreisQOrderRuleResolver.INTANCE.convert(model.getOrderRule());
		if (Check.NuNString(orderByClause)) {
			orderByClause = "travel_date asc, book_id asc";
		}

		result.setOrderByClause(orderByClause);

		return result;
	}

	public QueryResult<BookQueryOutModel> convertBookResult(BookQueryResultEModel model) {
		QueryResult<BookQueryOutModel> result = new QueryResult<BookQueryOutModel>(model.getCurrentPage(),
				model.getPageSize());
		result.setTotal(model.getTotal());
		List<BookQueryOutModel> resultData = new ArrayList<BookQueryOutModel>();
		if (!Check.NuNObject(model.getData())) {
			for (BookEntity entity : model.getData()) {
				resultData.add(convertBookResult(entity));
			}
		}

		result.setRecords(resultData);

		return result;

	}

	public QueryResult<SparpreisQueryOutModel> convertSparpreisResult(BookQueryResultEModel model) {
		QueryResult<SparpreisQueryOutModel> result = new QueryResult<SparpreisQueryOutModel>(model.getCurrentPage(),
				model.getPageSize());
		result.setTotal(model.getTotal());
		List<SparpreisQueryOutModel> resultData = new ArrayList<SparpreisQueryOutModel>();
		if (!Check.NuNObject(model.getData())) {
			for (BookEntity entity : model.getData()) {
				resultData.add(convertSparpreisQueryResult(entity));
			}
		}

		result.setRecords(resultData);

		return result;

	}

	private BookQueryOutModel convertBookResult(BookEntity data) {
		BookQueryOutModel result = new BookQueryOutModel();
		if (!Check.NuNObject(data.getBookDate())) {
			result.setBookDate(new Date(data.getBookDate()));
		}
		result.setBookId(data.getBookId());
		result.setBookState(data.getBookStatus());
		result.setBookType(data.getBookType());
		result.setOperateId(data.getOperatorId());
		result.setSpuId(data.getSpuId());
		result.setResellerId(data.getResellerId());
		result.setTotalNum(data.getTotalNum());
		result.setTotalAmount(data.getTotalAmount());
		if (!Check.NuNObject(data.getBookDate())) {
			result.setBookDate(new Date(data.getBookDate()));
		}
		if (!Check.NuNObject(data.getTravelDate()) && data.getTravelDate().longValue() > 0) {
			result.setTravelDate(new Date(data.getTravelDate()));
		}

		return result;

	}

	private SparpreisQueryOutModel convertSparpreisQueryResult(BookEntity data) {
		SparpreisQueryOutModel result = new SparpreisQueryOutModel();

		result.setBookId(data.getBookId());
		result.setBookState(data.getBookStatus());
		result.setOperateId(data.getOperatorId());
		result.setBookType(data.getBookType());
		result.setSpuId(data.getSpuId());
		result.setResellerId(data.getResellerId());
		result.setTotalAmount(data.getTotalAmount());

		if (!Check.NuNObject(data.getBookDate())) {
			result.setBookDate(new Date(data.getBookDate()));
		}
		if (!Check.NuNObject(data.getTravelDate()) && data.getTravelDate().longValue() > 0) {
			result.setTravelDate(new Date(data.getTravelDate()));
		}

		return result;
	}

	public BookDetailModel convertBookDetail(BookQueryEModel queryModel) {
		if (Check.NuNObject(queryModel) || Check.NuNObject(queryModel.getEntity())) {
			return null;
		}
		BookEntity data = queryModel.getEntity();
		BookDetailModel model = new BookDetailModel();
		model.setSrcBookId(data.getSrcBookId());
		model.setBookDate(new Date(data.getBookDate()));
		model.setBookId(data.getBookId());
		model.setBookType(data.getBookType());
		model.setBookStatus(data.getBookStatus());
		model.setTotalAmount(data.getTotalAmount());
		model.setTotalNum(data.getTotalNum());
		model.setDeliveryCode(data.getDeliveryCode());
		model.setOperatorId(data.getOperatorId());
		model.setResellerId(data.getResellerId());
		model.setSpuId(data.getSpuId());
		if (!Check.NuNObject(data.getTravelDate()) && data.getTravelDate().longValue() > 0) {
			model.setTravelDate(new Date(data.getTravelDate()));
		}

		if (!Check.NuNObject(data.getTravelDate()) && data.getTravelDate().longValue() > 0) {
			model.setTravelDate(new Date(data.getTravelDate()));
		}

		BookJsonEModel json = null;
		if (!Check.NuNString(data.getBookDetail())) {
			json = JSONConverter.toBean(data.getBookDetail(), BookJsonEModel.class);
			BookProductResolver.INSTANCE.copyBookProductList(json.getProducts(), model.getProducts());
			model.setAuditor(json.getAuditor());
			model.setContactee(json.getContactee());
			model.setFilledModelList(json.getFilledModelList());
			model.setTourists(json.getTourists());
			model.setRemark(json.getRemark());
			model.setTouristSourceCity(json.getTouristSourceCity());
			model.setTouristSourceCountry(json.getTouristSourceCountry());
			model.setTouristSourceProvince(json.getTouristSourceProvince());
			model.setCheckinPoints(json.getCheckinPoints());
		}
		OperLogEntity log = queryModel.getLog();
		if (!Check.NuNObject(log)) {
			model.setAuditReason(log.getContext());
		}

		return model;
	}

}
