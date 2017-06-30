package com.pzj.core.trade.refund.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.refund.engine.DoRefundEngine;
import com.pzj.core.trade.refund.engine.DockRefundConfirmEngine;
import com.pzj.core.trade.refund.engine.RefundApplyEngine;
import com.pzj.core.trade.refund.engine.TradeCleanEngine;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.common.RefundFlowAuditStateEnum;
import com.pzj.core.trade.refund.engine.converter.RefundMerchModelConverter;
import com.pzj.core.trade.refund.engine.exception.RefundException;
import com.pzj.core.trade.refund.engine.exception.RefundOrderException;
import com.pzj.core.trade.refund.engine.model.RefundApplyResultModel;
import com.pzj.core.trade.refund.engine.model.RefundMerchModel;
import com.pzj.core.trade.refund.entity.RefundMerchRequiredEntity;
import com.pzj.core.trade.saas.refund.engine.SassRefundApplyEngine;
import com.pzj.core.trade.saas.refund.engine.event.RefundApplyFinishEvent;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.refund.model.RefundApplyReqModel;
import com.pzj.trade.refund.service.RefundApplyService;

/**
 * 退款申请服务实现
 *
 * @author DongChunfu
 * @date 2016年12月14日
 */
@Service(value = "refundApplyService")
public class RefundApplyServiceImpl implements RefundApplyService {

	private static final Logger logger = LoggerFactory.getLogger(RefundApplyServiceImpl.class);

	@Resource(name = "refundApplyReqParamValidator")
	private ObjectConverter<RefundApplyReqModel, Void, Result<Boolean>> refundApplyReqParamValidator;

	@Resource(name = "refundApplyEngine")
	private RefundApplyEngine refundApplyEngine;
	@Resource(name = "sassRefundApplyEngine")
	private SassRefundApplyEngine sassRefundApplyEngine;
	@Resource(name = "tradeCleanEngine")
	private TradeCleanEngine tradeCleanEngine;

	@Resource(name = "dockRefundConfirmEngine")
	private DockRefundConfirmEngine dockRefundConfirmEngine;

	@Resource(name = "doRefundEngine")
	private DoRefundEngine doRefundEngine;

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private RefundMerchModelConverter refundMerchConverter;

	@Autowired
	private RefundApplyFinishEvent refundApplyFinishEvent;

	@Override
	public Result<Boolean> refundApply(final RefundApplyReqModel reqModel, final ServiceContext context) {
		try {

			logger.info("退款申请请求参数 ,reqModel:{}", reqModel);
			refundApplyReqParamValidator.convert(reqModel, null);
			final OrderEntity saleOrder = orderWriteMapper.getOrderEntityNotLock(reqModel.getOrderId());
			if (Check.NuNObject(saleOrder)) {
				throw new RefundOrderException(RefundErrorCode.ORDER_NOT_FOUND_ERROR_CODE, "退款申请对应的订单不存在");
			}
			if ("0".equals(saleOrder.getVersion())) {
				oldTradeRefundService(reqModel);
			} else {
				saasRefundService(reqModel);
			}
		} catch (final Throwable t) {

			logger.error("退款申请处理失败,reqModel: " + reqModel, t);

			if (t instanceof RefundException) {
				final RefundException exception = (RefundException) t;
				return new Result<Boolean>(exception.getErrCode(), exception.getMessage());
			}
			throw new RefundException(RefundErrorCode.REFUND_ERROR, t.getMessage(), t);
		}

		if (logger.isInfoEnabled()) {
			logger.info("退款申请,reqModel: {}, context: {}, result: {}.", reqModel, context, Boolean.TRUE);
		}
		return new Result<Boolean>(Boolean.TRUE);
	}

	/**
	 * 老的退款服务
	 * @param refundMerches
	 * @param reqModel
	 */
	private void oldTradeRefundService(final RefundApplyReqModel reqModel) {
		final List<RefundMerchRequiredEntity> sellMerches = refundApplyEngine.queryRefundRequiredEntitiesBySellOrderId(reqModel
				.getOrderId());
		final RefundMerchModel[] refundMerches = refundMerchConverter.convert(sellMerches, reqModel);
		// 返回结果包含退款ID及退款的审核状态,用于后续逻辑处理
		RefundApplyResultModel result = refundApplyEngine.doHandler(refundMerches, reqModel);

		int auditState = result.getAuditState();

		//		if (auditState == RefundFlowAuditStateEnum.PLATFORM_AUDIT.getState()) {
		//			tradeCleanEngine.clean(reqModel.getOrderId(), result.getRefundId(), 1);
		//		}

		if (auditState == RefundFlowAuditStateEnum.DOCK_AUDITING.getState()) {
			auditState = dockRefundConfirmEngine.refundConfirm(result.getRefundId());
		}
		//		if (RefundFlowAuditStateEnum.REFUSED.getState() == auditState) {
		//			tradeCleanEngine.clean(null, result.getRefundId(), 3);
		//		}
		if (auditState == RefundFlowAuditStateEnum.FINISHED.getState()) {
			doRefundEngine.refund(result.getRefundId());
			//			tradeCleanEngine.clean(reqModel.getOrderId(), result.getRefundId(), 2);
		}
		refundApplyFinishEvent.dofinish(result.getRefundId(), reqModel.getOrderId(), auditState);
	}

	/**
	 * saas1.1.0新的退款服务
	 * @param refundMerches
	 * @param reqModel
	 */
	private void saasRefundService(final RefundApplyReqModel reqModel) {
		final RefundApplyResultModel result = sassRefundApplyEngine.doRefundApply(reqModel);
		refundApplyFinishEvent.dofinish(result.getRefundId(), reqModel.getOrderId(), result.getAuditState());
	}
}
