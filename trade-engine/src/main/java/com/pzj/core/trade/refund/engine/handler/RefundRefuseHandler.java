package com.pzj.core.trade.refund.engine.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.refund.engine.common.MerchStateEnum;
import com.pzj.core.trade.refund.engine.common.RefundFlowAuditStateEnum;
import com.pzj.core.trade.refund.engine.common.RefundFlowStateEnum;
import com.pzj.core.trade.refund.write.RefundApplyWriteMapper;
import com.pzj.framework.context.Result;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.merch.model.MerchRefundModel;
import com.pzj.trade.merch.write.MerchRefundFlowWriteMapper;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.mq.DockOtaMQMSgConverter;
import com.pzj.trade.mq.MQTagsEnum;
import com.pzj.trade.mq.MQUtil;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.entity.OrderRefundEditModel;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.voucher.service.VoucherService;

@Component(value = "refundRefuseHandler")
public class RefundRefuseHandler {

	private static final Logger logger = LoggerFactory.getLogger(RefundRefuseHandler.class);

	@Autowired
	private MerchRefundFlowWriteMapper merchRefundFlowWriteMapper;

	@Autowired
	private RefundApplyWriteMapper refundApplyWriteMapper;

	@Autowired
	private MerchWriteMapper merchWriteMapper;

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Resource(name = "mqUtil")
	private MQUtil mqUtil;

	@Autowired
	private VoucherService voucherService;

	@Resource
	private SettlRefundCancleHandler settlRefundCancleHandler;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 4)
	public Result<Boolean> refuse(final String refundId, final List<RefundFlowEntity> refundFlows, final OrderEntity sellOrder, final int refundType) {

		refundApplyWriteMapper.updateRefundApplyRelationStatue(refundId, RefundFlowStateEnum.FAIL.getState(), RefundFlowAuditStateEnum.REFUSED.getState());

		final String orderId = sellOrder.getOrder_id();

		// 清结算冻结记录回滚
		settlRefundCancleHandler.handler(sellOrder, refundType, refundId);

		final Map<String, Integer> refundNumCache = new HashMap<String, Integer>();
		final Map<String, Double> refundPriceCache = new HashMap<String, Double>();
		int realRefundType = 0;
		for (final RefundFlowEntity refundFlow : refundFlows) {
			realRefundType = rollbackRefundMerch(refundFlow, refundNumCache, refundPriceCache);
		}

		updateOrderInfo(refundNumCache, refundPriceCache, realRefundType);

		callVoucharActivation(orderId, refundId, refundFlows);

		sendOatRefundFailure(orderId);

		return new Result<Boolean>();

	}

	/**
	 * 审核回执订单商品信息
	 *
	 * @param applyEntity
	 * @param orderNumChangeCache
	 * @param orderPriceChangeCache
	 */
	private int rollbackRefundMerch(final RefundFlowEntity refundFlow, final Map<String, Integer> orderNumChangeCache,
			final Map<String, Double> orderPriceChangeCache) {

		final String merchid = refundFlow.getMerch_id();
		final List<MerchEntity> childMerch = merchWriteMapper.getMerchsByRootMerchId(merchid);
		int refundType = 0;

		for (final MerchEntity merch : childMerch) {

			final RefundFlowEntity merchRefund = merchRefundFlowWriteMapper.getMerchRefundEntityOfMerch(merch.getMerch_id(), refundFlow.getRefund_id());

			final MerchRefundModel refundModel = new MerchRefundModel();
			refundModel.setMerchId(merch.getMerch_id());
			refundModel.setRefundNum(merch.getRefund_num() - merchRefund.getRefund_num());

			refundModel.setRefundAmount(merch.getRefund_amount() - merchRefund.getRefund_price() * merchRefund.getRefund_num());

			refundModel.setMerchState(merch.getMerch_state());

			if (merch.getMerch_state() == MerchStateEnum.CONSUMED.getState() || merch.getMerch_state() == MerchStateEnum.FINISHED.getState()) {

				refundModel.setCheckNum(merch.getCheck_num() + merchRefund.getRefund_num());

				refundType = 3;

			} else {
				refundModel.setCheckNum(merch.getCheck_num());
			}

			final int count = merchRefundFlowWriteMapper.getRefundAuditingOfMerch(merch.getOrder_id(), merch.getMerch_id(),
			/*MerchAuditStateEnum.AUDITING.getState()*/0);
			if (count == 1) {
				refundModel.setIsRefunding(2/*RefundingEnum.notRefunding.getKey()*/);
			}

			merchWriteMapper.updateMerchOfRefund(refundModel);

			if (orderNumChangeCache.get(merch.getOrder_id()) == null) {
				orderNumChangeCache.put(merch.getOrder_id(), 0);
				orderPriceChangeCache.put(merch.getOrder_id(), 0.0);
			}

			orderNumChangeCache.put(merch.getOrder_id(), orderNumChangeCache.get(merch.getOrder_id()) + merchRefund.getRefund_num());

			orderPriceChangeCache.put(merch.getOrder_id(),
					orderPriceChangeCache.get(merch.getOrder_id()) + merchRefund.getRefund_num() * merchRefund.getRefund_price());
		}

		return refundType;
	}

	/**
	 * 调用vouchar激活服务接口
	 */
	private void callVoucharActivation(final String orderId, final String refundId, final List<RefundFlowEntity> refundMerchApplyList) {

		//final List<VoucherNumberVO> voucharlist = new ArrayList<VoucherNumberVO>();

		//for (final RefundFlowEntity refundvo : refundMerchApplyList) {
		//final MerchEntity merch = merchWriteMapper.getMerchByMerchId(refundvo.getMerch_id());
		//final VoucherNumberVO vo = new VoucherNumberVO();
		//			vo.setNumber(refundvo.getRefund_num());
		//			vo.setVoucherId(merch.getVoucher_id());
		//			vo.setProductId(merch.getProduct_id());
		//			voucharlist.add(vo);
		//}
		//		logger.info("call replyToUseVoucherBatch content:" + JSONConverter.toJson(voucharlist));
		//		final ExecuteResult<Boolean> result = voucherService.replyToUseVoucherBatch(voucharlist);
		//		logger.info("call replyToUseVoucherBatch resultContent:" + JSONConverter.toJson(result));
		//		if (Check.NuNObject(result) || Check.NuNObject(result.getStateCode()) || result.getStateCode().intValue() != ExecuteResult.SUCCESS.intValue()) {
		//			logger.error("refund errorContent:" + JSONConverter.toJson(result));
		//			logger.error("voucher注销激活,req param:" + JSONConverter.toJson(voucharlist) + ",orderId:" + orderId + ",refundId:" + refundId);
		//		}
	}

	// 新增退款，核销后发送消息
	private void sendOatRefundFailure(final String orderId) {
		final String msg = DockOtaMQMSgConverter.getRefundOrderMsg(orderId, false);
		mqUtil.send(MQTagsEnum.otaRefundMerch.getValue(), msg);
	}

	private void updateOrderInfo(final Map<String, Integer> refundNumCache, final Map<String, Double> refundPriceCache, final int realRefundType) {
		final List<OrderEntity> refundorders = new ArrayList<OrderEntity>();

		for (final Entry<String, Integer> entry : refundNumCache.entrySet()) {
			final String refundorderid = entry.getKey();
			final int refundnum = entry.getValue();
			final double refundprice = refundPriceCache.get(refundorderid);

			final OrderEntity order = orderWriteMapper.getOrderEntityNotLock(refundorderid);

			order.setRefund_num(order.getRefund_num() - refundnum);
			order.setRefund_amount(order.getRefund_amount() - refundprice);

			refundorders.add(order);

			final OrderRefundEditModel editModel = new OrderRefundEditModel();
			editModel.setOrderId(order.getOrder_id());
			editModel.setRefundNum(order.getRefund_num() - refundnum);
			editModel.setRefundAmount(order.getRefund_amount() - refundprice);

			editModel.setCheckNum(order.getChecked_num());
			if (3 == realRefundType) {
				order.setChecked_num(order.getChecked_num() + refundnum);
			}
			orderWriteMapper.updateOrderRefundAmountAndNum(editModel);
		}

		//		orderWriteMapper.updateOrderOfRefund(refundorders);

	}
}
