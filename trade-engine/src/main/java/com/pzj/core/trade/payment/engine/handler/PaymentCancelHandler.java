package com.pzj.core.trade.payment.engine.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.common.utils.StockGlobalDict;
import com.pzj.core.product.model.ReleaseStockReqModel;
import com.pzj.core.product.model.ReleaseStockReqsModel;
import com.pzj.core.product.service.SeatRecordService;
import com.pzj.core.trade.log.dao.entity.OperLogEntity;
import com.pzj.core.trade.log.engine.OperLogEngine;
import com.pzj.core.trade.voucher.read.VoucherReadMapper;
import com.pzj.core.trade.voucher.write.VoucherWriteMapper;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.voucher.common.VoucherStateEnum;
import com.pzj.voucher.entity.VoucherEntity;

@Component("paymentCancelHandler")
public class PaymentCancelHandler {
	private final static Logger logger = LoggerFactory.getLogger(PaymentCancelHandler.class);

	@Autowired
	private VoucherWriteMapper voucherWriteMapper;
	@Autowired
	private OrderWriteMapper orderWriteMapper;
	@Autowired
	private MerchWriteMapper merchWriteMapper;
	@Autowired
	private VoucherReadMapper voucherReadMapper;
	@Autowired
	private OperLogEngine operLogEngine;

	@Autowired
	private SeatRecordService seatRecordService;

	public void doHandle(final OrderEntity order, final long operatorId) {
		cancelVoucher(order.getTransaction_id());
		orderWriteMapper.updateOrderStatusByTransactionId(order.getTransaction_id(), OrderStatusEnum.Cancelled.getValue());
		generateOperationLogger(order, operatorId);
		releaaseStock(order.getTransaction_id());
	}

	/*
	 * 更新凭证状态.
	 */
	private void cancelVoucher(final String transaction_id) {
		final List<VoucherEntity> vouchers = voucherReadMapper.queryVoucherByTransactionId(transaction_id);
		for (final VoucherEntity voucher : vouchers) {
			voucherWriteMapper.updateVouchConfirmStatusById(voucher.getVoucherId(), VoucherStateEnum.OBSOLETE.getValue(),
					(Date) null);
		}
	}

	/*
	 * 记录订单操作日志.
	 */
	private void generateOperationLogger(final OrderEntity order, final long operatorId) {
		final OperLogEntity logEntity = new OperLogEntity();
		logEntity.setEvent("order_cancel");
		logEntity.setOrderId(order.getOrder_id());
		logEntity.setOperator(operatorId);
		logEntity.setPrev(order.getOrder_status());
		logEntity.setNext(OrderStatusEnum.Cancelled.getValue());
		operLogEngine.generateLog(logEntity);
	}

	private void releaaseStock(String transactionId) {
		List<MerchEntity> merches = merchWriteMapper.getMerchByOrderId(transactionId);
		final ReleaseStockReqsModel model = new ReleaseStockReqsModel();
		model.setTransactionId(transactionId);
		model.setReleaseStockReqs(new ArrayList<ReleaseStockReqModel>());
		model.setReleaseFlag(StockGlobalDict.ReleaseFlag.cancelOrder);
		Map<Long, ReleaseStockReqModel> cache = new HashMap<Long, ReleaseStockReqModel>();
		for (MerchEntity merch : merches) {
			ReleaseStockReqModel reqModel = cache.get(merch.getProduct_id());
			if (Check.NuNObject(reqModel)) {
				reqModel = new ReleaseStockReqModel();
				reqModel.setProductId(merch.getProduct_id());
				reqModel.setStockNum(0);
			}
			reqModel.setStockNum(reqModel.getStockNum() + merch.getTotal_num());
			cache.put(merch.getProduct_id(), reqModel);
		}
		model.setReleaseStockReqs(new ArrayList<ReleaseStockReqModel>(cache.values()));
		try {
			logger.info("调用释放库存接口 seatRecordService.releaaseStock,transactioId:{},param:{}", transactionId,
					JSONConverter.toJson(model));
			final Result<Boolean> result = seatRecordService.releaaseStock(model, null);
			logger.info("调用释放库存接口 seatRecordService.releaaseStock,返回值", JSONConverter.toJson(result));
		} catch (final Throwable e) {
			logger.error("调用释放库存接口 seatRecordService.releaaseStock,transactioId:[" + transactionId + "],param:["
					+ JSONConverter.toJson(model) + "],errorContent:", e);
		}
	}
}
