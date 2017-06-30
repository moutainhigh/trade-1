package com.pzj.core.trade.refund.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.refund.engine.DoRefundEngine;
import com.pzj.core.trade.refund.engine.DockRefundConfirmEngine;
import com.pzj.core.trade.refund.engine.RefundAuditEngine;
import com.pzj.core.trade.refund.engine.TradeCleanEngine;
import com.pzj.core.trade.refund.engine.common.RefundFlowAuditStateEnum;
import com.pzj.core.trade.refund.engine.exception.RefundException;
import com.pzj.core.trade.refund.engine.model.RefundAuditResultModel;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.write.RefundApplyWriteMapper;
import com.pzj.core.trade.saas.refund.engine.SaasRefundAuditEngine;
import com.pzj.core.trade.saas.refund.engine.event.RefundApplyFinishEvent;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.exception.ServiceException;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.refund.model.RefundAuditReqModel;
import com.pzj.trade.refund.service.RefundAuditService;

/**
 * 退款审核相关服务
 *
 * @author DongChunfu
 * @date 2016年12月14日
 */
@Service("refundAuditService")
public class RefundAuditServiceImpl implements RefundAuditService {

	private static final Logger logger = LoggerFactory.getLogger(RefundAuditServiceImpl.class);

	@Resource(name = "refundAuditReqParamValidator")
	private ObjectConverter<RefundAuditReqModel, Void, Result<Boolean>> refundAuditReqParamValidator;

	@Resource(name = "refundAuditEngine")
	private RefundAuditEngine refundAuditEngine;

	@Resource(name = "tradeCleanEngine")
	private TradeCleanEngine tradeCleanEngine;

	@Resource(name = "doRefundEngine")
	private DoRefundEngine doRefundEngine;

	@Autowired
	private SaasRefundAuditEngine saasRefundAuditEngine;

	@Resource(name = "dockRefundConfirmEngine")
	private DockRefundConfirmEngine dockRefundConfirmEngine;

	@Autowired
	private RefundApplyFinishEvent refundApplyFinishEvent;

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private RefundApplyWriteMapper refundApplyWriteMapper;

	@Override
	public Result<Boolean> refundAudit(final RefundAuditReqModel reqModel, final ServiceContext context) {
		try {
			logger.info("refund audit reqModel:{}, context:{}.", JSONConverter.toJson(reqModel), context);
			if (!Check.NuNString(reqModel.getRefundId())) {
				RefundApplyEntity refundApply = refundApplyWriteMapper.getRefundApplyEntityByRefundId(reqModel.getRefundId());
				if (!Check.NuNObject(refundApply)) {
					reqModel.setSaleOrderId(refundApply.getTransactionId());
				}
			}
			refundAuditReqParamValidator.convert(reqModel, null);
			String orderVersion = "0";
			if (!Check.NuNString(reqModel.getSaleOrderId())) {
				OrderEntity saleOrder = orderWriteMapper.getOrderEntityNotLock(reqModel.getSaleOrderId());
				orderVersion = saleOrder.getVersion();
			}
			if ("0".equals(orderVersion)) {
				oldTradeRefundAuditService(reqModel);
			} else {
				saasRefundAuditService(reqModel);
			}
		} catch (final Throwable t) {
			logger.error("refund audit exception ,reqModel:" + JSONConverter.toJson(reqModel), t);

			if (!(t instanceof RefundException)) {
				throw new ServiceException(t.getMessage(), t);
			}
			final RefundException refundException = (RefundException) t;
			return new Result<Boolean>(refundException.getErrCode(), refundException.getMessage());

		}

		logger.info("refund audit success,reqModel:{}.", JSONConverter.toJson(reqModel));
		return new Result<Boolean>(Boolean.TRUE);
	}

	private void oldTradeRefundAuditService(final RefundAuditReqModel reqModel) {
		int state = refundAuditEngine.doHandler(reqModel);
		if (RefundFlowAuditStateEnum.DOCK_AUDITING.getState() == state) {
			state = dockRefundConfirmEngine.refundConfirm(reqModel.getRefundId());
		}
		//		if (RefundFlowAuditStateEnum.REFUSED.getState() == state) {
		//			tradeCleanEngine.clean(null, reqModel.getRefundId(), 3);
		//		}
		if (RefundFlowAuditStateEnum.FINISHED.getState() == state) {
			doRefundEngine.refund(reqModel.getRefundId());
			//			tradeCleanEngine.clean(null, reqModel.getRefundId(), 2);
		}
		refundApplyFinishEvent.dofinish(reqModel.getRefundId(), reqModel.getSaleOrderId(), state);
	}

	private void saasRefundAuditService(final RefundAuditReqModel reqModel) {
		RefundAuditResultModel result = saasRefundAuditEngine.doAudit(reqModel, reqModel.getSaleOrderId());
		refundApplyFinishEvent.dofinish(result.getRefundId(), result.getSaleOrderId(), result.getAuditState());
	}

}
