package com.pzj.core.trade.order.validator;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.common.OrderErrorCode;
import com.pzj.core.trade.order.engine.exception.OrderArgsException;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.model.TransferAccountsReqModel;

/**
 * 分账查询请求参数校验器
 *
 * @author DongChunfu
 * @version $Id: TransferAccountsQueryRepParamValidator.java, v 0.1 2017年3月25日 下午12:50:14 DongChunfu Exp $
 */
@Component(value = "transferAccountsQueryRepParamValidator")
public class TransferAccountsQueryRepParamValidator
		implements ObjectConverter<TransferAccountsReqModel, ServiceContext, Result<Boolean>> {

	private static final Logger logger = LoggerFactory.getLogger(TransferAccountsQueryRepParamValidator.class);

	/**
	 * @see com.pzj.framework.converter.ObjectConverter#convert(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Result<Boolean> convert(final TransferAccountsReqModel reqModel, final ServiceContext context) {

		if (Check.NuNObject(reqModel)) {
			logger.error("query transfer accounts detail reqMoel is null.");
			throw new OrderArgsException(OrderErrorCode.ORDER_ARGS_ERROR_CODE, "分账明细查询请求参数不得为空");
		}
		final Date payStartTime = reqModel.getPayStartTime();
		final Date payEndTime = reqModel.getPayEndTime();

		if (Check.NuNObject(payStartTime) || Check.NuNObject(payEndTime)) {
			if (!(Check.NuNObject(payStartTime) && Check.NuNObject(payEndTime))) {
				logger.error("query transfer accounts detail reqMoel is ilegal,payStartTime:{},payEndTime:{}.", payStartTime,
						payEndTime);
				throw new OrderArgsException(OrderErrorCode.ORDER_ARGS_ERROR_CODE, "分账明细查询请求支付时间参数需同时为空");
			}
		}

		final Date confirmStartTime = reqModel.getConfirmStartTime();
		final Date confirmEndTime = reqModel.getConfirmEndTime();

		if (Check.NuNObject(confirmStartTime) || Check.NuNObject(confirmEndTime)) {
			if (!(Check.NuNObject(confirmStartTime) && Check.NuNObject(confirmEndTime))) {
				logger.error("query transfer accounts detail reqMoel is ilegal,confirmStartTime:{},confirmEndTime:{}.",
						payStartTime, payEndTime);
				throw new OrderArgsException(OrderErrorCode.ORDER_ARGS_ERROR_CODE, "分账明细查询请求核销时间参数需同时为空");
			}
		}

		logger.debug("query transfer accounts detail reqMoel is ok.");
		return new Result<Boolean>();
	}

}
