package com.pzj.core.trade.saas.refund.engine.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.common.utils.StockGlobalDict;
import com.pzj.core.product.common.model.response.SkuProductOutModel;
import com.pzj.core.product.common.service.ISpuProductService;
import com.pzj.core.product.model.ReleaseStockReqModel;
import com.pzj.core.product.model.ReleaseStockReqsModel;
import com.pzj.core.product.service.SeatRecordService;
import com.pzj.core.sku.common.constants.SkuProductGlobal.InventoryTypeEnum;
import com.pzj.core.trade.order.engine.exception.OrderException;
import com.pzj.core.trade.refund.engine.common.RefundingEnum;
import com.pzj.core.trade.refund.engine.handler.PaymentRefundHandler;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.voucher.write.VoucherWriteMapper;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.merch.model.MerchRefundModel;
import com.pzj.trade.merch.write.MerchRefundFlowWriteMapper;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.common.MerchStateEnum;
import com.pzj.trade.order.model.OrderRefundModel;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.payment.common.PayFlowStateEnum;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.write.FreezeFlowWriteMapper;
import com.pzj.voucher.common.VoucherStateEnum;

@Component
public class SaasRefundFinishHandler {

	private static final Logger logger = LoggerFactory.getLogger(SaasRefundFinishHandler.class);

	@Autowired
	private PaymentRefundHandler paymentRefundHandler;

	@Autowired
	private SaasAccountRefundConfirmHandler saasAccountRefundConfirmHandler;

	@Autowired
	private FreezeFlowWriteMapper freezeFlowWriteMapper;
	@Autowired
	private MerchRefundFlowWriteMapper merchRefundFlowWriteMapper;

	@Autowired
	private MerchWriteMapper merchWriteMapper;

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private VoucherWriteMapper voucherWriteMapper;

	@Autowired
	private SeatRecordService seatRecordService;

	@Autowired
	private ISpuProductService iSpuProductService;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED)
	public void doFinishHandler(final RefundApplyEntity refundApply, final String saleOrderId) {

		//获取主订单商品的退款申请流水
		final List<RefundFlowEntity> saleRefundFlow = merchRefundFlowWriteMapper.getOrderMerchRefund(saleOrderId,
				refundApply.getRefundId());
		updateMerchAndOrderRefundStatus(saleRefundFlow, saleOrderId, refundApply.getRefundId());

		final FreezeFlowEntity freezeFlow = freezeFlowWriteMapper.getFreezingFlowBySignIdForRefund(refundApply.getRefundId());
		//判断是否存在退款冻结信息
		if (!Check.NuNObject(freezeFlow)) {
			freezeFlowWriteMapper.updateFreezeFlowStatus(saleOrderId, refundApply.getRefundId(),
					PayFlowStateEnum.PaySuccess.getKey());
			//判断是否存在第三方退款金额,如果存在则发起第三方退款申请
			saasAccountRefundConfirmHandler.confirmRefundApply(refundApply);
			if (freezeFlow.getThird_amount() > 0) {
				paymentRefundHandler.callPaymentRefundMoney(saleOrderId, refundApply, freezeFlow.getThird_amount());
			}
		}
	}

	/**
	 *
	 * @param saleRefundFlow
	 * @return
	 */
	private void updateMerchAndOrderRefundStatus(final List<RefundFlowEntity> saleRefundFlows, final String saleOrderId,
			final String refundId) {
		final List<MerchRefundModel> models = new ArrayList<MerchRefundModel>();
		final Set<Long> refundVouchers = new HashSet<Long>();
		final Map<Long, Integer> releaseStockCache = new HashMap<Long, Integer>();
		for (final RefundFlowEntity flow : saleRefundFlows) {
			final MerchRefundModel model = new MerchRefundModel();
			final MerchEntity merch = merchWriteMapper.getMerchByMerchId(flow.getMerch_id());
			model.setMerchId(merch.getMerch_id());
			model.setCheckNum(merch.getCheck_num());
			model.setRefundNum(merch.getRefund_num());
			model.setRefundAmount(merch.getRefund_amount());
			model.setRefundingNum(merch.getRefunding_num() - flow.getRefund_num());
			model.setMerchState(merch.getMerch_state());
			if (model.getRefundingNum() == 0) {
				model.setIsRefunding(RefundingEnum.NOTREFUNDING.getValue());
				if (merch.getTotal_num() == merch.getRefund_num()) {
					model.setMerchState(MerchStateEnum.REFUNDED.getState());
					refundVouchers.add(merch.getVoucher_id());
				}
			}
			int stcockNum = 0;
			if (releaseStockCache.containsKey(merch.getProduct_id())) {
				stcockNum = releaseStockCache.get(merch.getProduct_id()) + flow.getRefund_num();
			} else {
				stcockNum = flow.getRefund_num();
			}
			releaseStockCache.put(merch.getProduct_id(), stcockNum);
			models.add(model);
		}
		merchWriteMapper.updateMerchesOfRefund(models);
		final OrderRefundModel refundModel = orderWriteMapper.getOrderRefundModel(saleOrderId, refundId);
		if (refundModel.checkOrderFinish()) {
			merchWriteMapper.updateMercheStatusOfRefundFinish(saleOrderId);
			orderWriteMapper.updateOrderStatusOfRefundFinish(saleOrderId,
					refundModel.getTotalNum() == refundModel.getRefundNum());
		}
		if (!Check.NuNCollections(refundVouchers)) {
			for (final long voucher : refundVouchers) {
				final List<MerchEntity> merches = merchWriteMapper.getMerchByVoucherId(voucher);
				boolean isChangeVoucherState = true;
				for (final MerchEntity merch : merches) {
					if (merch.getIs_refunding() == RefundingEnum.REFUNDING.getValue()
							|| merch.getTotal_num() != merch.getRefund_num()) {
						isChangeVoucherState = false;
						break;
					}
				}
				if (isChangeVoucherState) {
					voucherWriteMapper.updateVouchConfirmStatusById(voucher, VoucherStateEnum.REFUND.getValue(), (Date) null);
				}
			}
		}
		//释放产品库存
		releaaseStock(releaseStockCache, saleOrderId);
	}

	private void releaaseStock(final Map<Long, Integer> releaseStockCache, final String saleOrderId) {
		final ReleaseStockReqsModel model = new ReleaseStockReqsModel();
		model.setTransactionId(saleOrderId);
		model.setReleaseStockReqs(new ArrayList<ReleaseStockReqModel>());
		model.setReleaseFlag(StockGlobalDict.ReleaseFlag.refunds);
		final Iterator<Entry<Long, Integer>> iter = releaseStockCache.entrySet().iterator();
		for (; iter.hasNext();) {
			final Entry<Long, Integer> entry = iter.next();
			final long prodId = entry.getKey();
			final SkuProductOutModel skuOutModel = getProductOutModel(prodId);
			if (skuOutModel == null || InventoryTypeEnum.IS_UNLIMITED_INVENTORY == skuOutModel.getIsUnlimitedStock()) {
				continue;//产品模型不存在或为无限库存情况下, 不需要走释放库存.
			}
			final ReleaseStockReqModel reqModel = new ReleaseStockReqModel();
			reqModel.setProductId(prodId);
			reqModel.setStockNum(entry.getValue());
			model.getReleaseStockReqs().add(reqModel);
		}

		try {
			if (!Check.NuNCollections(model.getReleaseStockReqs())) {
				logger.info("调用释放库存接口 seatRecordService.releaaseStock,transactioId:{},param:{}", saleOrderId,
						JSONConverter.toJson(model));
				final Result<Boolean> result = seatRecordService.releaaseStock(model, null);
				logger.info("调用释放库存接口 seatRecordService.releaaseStock,返回值", JSONConverter.toJson(result));
			}
		} catch (final Throwable e) {
			logger.error(
					"调用释放库存接口 seatRecordService.releaaseStock,transactioId:[" + saleOrderId + "],param:["
							+ JSONConverter.toJson(model) + "],errorContent:", e);
		}
	}

	/**
	 * 从产品服务获取产品模型.
	 * @param prodId
	 */
	private SkuProductOutModel getProductOutModel(final long prodId) {
		Result<SkuProductOutModel> result = null;
		try {
			result = iSpuProductService.getSkuById(prodId);
		} catch (final Throwable e) {
			throw new OrderException(10500, "根据产品ID[" + prodId + "]获取产品模型失败.", e);
		}

		if (!result.isOk()) {
			throw new OrderException(10500, "根据产品ID[" + prodId + "]获取产品模型失败, 返回错误码[" + result.getErrorCode() + "], 错误描述["
					+ result.getErrorMsg() + "].");
		}

		if (logger.isInfoEnabled()) {
			logger.info("根据产品ID[" + prodId + "]获取产品SKU模型信息 -->" + (JSONConverter.toJson(result)));
		}

		final SkuProductOutModel outModel = result.getData();
		if (Check.NuNObject(outModel)) {
			throw new OrderException(10500, "根据产品ID[" + prodId + "]获取产品模型失败, 返回错误码[" + result.getErrorCode() + "], 错误描述["
					+ result.getErrorMsg() + "], 产品模型为空.");
		}

		return outModel;
	}
}
