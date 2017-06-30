package com.pzj.core.trade.refund.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.refund.engine.PageQueryRefundApplyEngine;
import com.pzj.core.trade.refund.engine.exception.RefundException;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.entity.QueryResult;
import com.pzj.framework.exception.ServiceException;
import com.pzj.trade.refund.model.QueryRefundApplyReqModel;
import com.pzj.trade.refund.service.RefundQueryService;
import com.pzj.trade.refund.vo.ForceRefundApplyVO;

/**
 * 退款查询服务实现
 *
 * @author DongChunfu
 * @date 2016年12月14日
 */
@Service(value = "refundQueryService")
public class RefundQueryServiceImpl implements RefundQueryService {

	private static final Logger logger = LoggerFactory.getLogger(RefundQueryServiceImpl.class);

	@Resource(name = "queryRefundApplyPageReqParamValidator")
	private ObjectConverter<QueryRefundApplyReqModel, Void, Result<Boolean>> queryRefundApplyPageReqParamValidator;

	@Resource(name = "pageQueryRefundApplyEngine")
	private PageQueryRefundApplyEngine pageQueryRefundApplyEngine;

	@Override
	public Result<QueryResult<ForceRefundApplyVO>> queryRefundApplyPage(final QueryRefundApplyReqModel reqModel,
			final ServiceContext context) {

		QueryResult<ForceRefundApplyVO> queryResult = null;
		try {

			queryRefundApplyPageReqParamValidator.convert(reqModel, null);
			if (logger.isInfoEnabled()) {
				logger.info("强制退款分页查询,reqModel:{}.", JSONConverter.toJson(reqModel));
			}

			queryResult = pageQueryRefundApplyEngine.query(reqModel);

		} catch (final Throwable t) {
			logger.error("强制退款分页查询失败,reqModel:" + JSONConverter.toJson(reqModel), t);

			if (t instanceof RefundException) {
				final RefundException re = (RefundException) t;
				return new Result<QueryResult<ForceRefundApplyVO>>(0, re.getMessage());
			}
			throw new ServiceException(t.getMessage(), t);
		}

		if (logger.isInfoEnabled()) {
			logger.info("强制退款分页查询,reqModel:{},result:{}.", JSONConverter.toJson(reqModel), JSONConverter.toJson(queryResult));
		}

		return new Result<QueryResult<ForceRefundApplyVO>>(queryResult);
	}

}
