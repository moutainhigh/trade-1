package com.pzj.core.trade.order.engine.converter;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.query.entity.OrderMerchDetailQueryModel;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.trade.order.model.SettlementOrdersReqModel;

/**
 * 订单商品明细查询模型转换器.
 * @author YRJ
 *
 */
@Component(value = "orderMerchDetailQueryModelConverter")
public class OrderMerchDetailQueryModelConverter implements ObjectConverter<SettlementOrdersReqModel, Void, OrderMerchDetailQueryModel> {

	@Override
	public OrderMerchDetailQueryModel convert(final SettlementOrdersReqModel reqModel, final Void arg1) {
		final OrderMerchDetailQueryModel queryModel = new OrderMerchDetailQueryModel();
		queryModel.setOrderId(reqModel.getOrderId());
		queryModel.setTransaction_id(reqModel.getTransactionId());

		final Timestamp startTime = calculator(reqModel.getPayStartTime(), true);
		queryModel.setPayStartTime(startTime);

		final Timestamp payEndTime = calculator(reqModel.getPayEndTime(), false);
		queryModel.setPayEndTime(payEndTime);

		final Timestamp checkStartTime = calculator(reqModel.getCheckStartTime(), true);
		queryModel.setCheckStartTime(checkStartTime);

		final Timestamp checkEndTime = calculator(reqModel.getCheckEndTime(), false);
		queryModel.setCheckEndTime(checkEndTime);
		return queryModel;
	}

	public Timestamp calculator(final Date time, final boolean up) {
		if (time == null) {
			return null;
		}
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(Calendar.HOUR_OF_DAY, up ? 0 : 23);
		calendar.set(Calendar.MINUTE, up ? 0 : 59);
		calendar.set(Calendar.SECOND, up ? 0 : 59);
		final long target = calendar.getTimeInMillis();

		return new Timestamp(target);
	}
}
