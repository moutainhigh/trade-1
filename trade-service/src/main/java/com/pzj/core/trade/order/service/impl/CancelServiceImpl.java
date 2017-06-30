package com.pzj.core.trade.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.cancel.engine.OrderCancelEngine;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.exception.ServiceException;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.model.OrderCancelModel;
import com.pzj.trade.order.service.CancelService;

@Service(value = "cancelService")
public class CancelServiceImpl implements CancelService {

	private final static Logger logger = LoggerFactory.getLogger(CancelServiceImpl.class);

	@Resource(name = "orderCancelEngine")
	private OrderCancelEngine orderCancelEngine;

	@Override
	public Result<Boolean> cancelOrder(final OrderCancelModel cancelModel, final ServiceContext context) {
		if (Check.NuNObject(cancelModel, context) || Check.NuNStrStrict(cancelModel.getOrderId())) {
		}
		logger.info("订单取消, reqModel: " + (cancelModel));
		try {
			final boolean cancel = orderCancelEngine.cancel(cancelModel.getOrderId(), cancelModel.getOperatorId());
			if (!cancel) {
				logger.info("订单取消, reqModel: " + (cancelModel) + ", 暂不可取消.");
				return new Result<Boolean>(10701, "当前订单不可取消");
			}
		} catch (final Throwable e) {
			logger.error("订单取消失败, reqModel:" + cancelModel, e);
			if (e instanceof ServiceException) {
				throw (ServiceException) e;
			}
			throw new ServiceException("订单[" + cancelModel.getOrderId() + "]取消失败.", e);
		}
		return new Result<Boolean>(true);
	}

	@Override
	public Result<Boolean> batchCancel(final ServiceContext context) {
		final List<String> orderIds = orderCancelEngine.queryUnPaiedOrderByLimit();
		if (Check.NuNCollections(orderIds)) {
			return new Result<Boolean>(true);
		}
		boolean isOk = false;
		for (final String orderId : orderIds) {
			final OrderCancelModel cancelModel = new OrderCancelModel();
			cancelModel.setOrderId(orderId);
			cancelModel.setOperatorId(10001L);

			try {
				cancelOrder(cancelModel, context);
				isOk = true;
			} catch (final Throwable e) {
				//ignore.
			}
		}
		return new Result<Boolean>(isOk);
	}
}
