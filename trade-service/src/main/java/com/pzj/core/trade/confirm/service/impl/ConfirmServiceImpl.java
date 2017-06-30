package com.pzj.core.trade.confirm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.confirm.common.ConfirmErrorCode;
import com.pzj.core.trade.confirm.engine.CleanOrTransferAccount;
import com.pzj.core.trade.confirm.engine.ConfirmEngine;
import com.pzj.core.trade.confirm.exception.ConfirmException;
import com.pzj.core.trade.confirm.exception.ConfirmOrderNotExistException;
import com.pzj.core.trade.confirm.handler.ConfirmFinishedSendOpenApiMqHandler;
import com.pzj.core.trade.confirm.model.AccountHandleModel;
import com.pzj.core.trade.voucher.entity.VourEntity;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.confirm.model.BatchConfirmReqModel;
import com.pzj.trade.confirm.model.BatchConfirmWrapModel;
import com.pzj.trade.confirm.model.ConfirmFailValue;
import com.pzj.trade.confirm.model.ConfirmModel;
import com.pzj.trade.confirm.service.ConfirmService;
import com.pzj.trade.merch.entity.ConfirmBatchEntity;
import com.pzj.trade.mq.MQUtil;
import com.pzj.voucher.entity.VoucherEntity;

/**
 * 核销接口实现.
 *
 * @author YRJ
 * @author DongChunfu
 * @version $Id: ConfirmServiceImpl.java, v 0.1 2017年3月22日 下午1:53:47 DongChunfu Exp $
 */
@Service(value = "confirmService")
public class ConfirmServiceImpl implements ConfirmService {

	private final static Logger logger = LoggerFactory.getLogger(ConfirmServiceImpl.class);

	@Resource(name = "confirmEngine")
	private ConfirmEngine confirmEngine;

	@Resource(name = "cleanOrTransferAccount")
	private CleanOrTransferAccount cleanOrTransferAccount;

	@Resource(name = "confirmReqParamValidator")
	ObjectConverter<ConfirmModel, ServiceContext, Result<Boolean>> confirmReqParamValidator;

	@Resource(name = "batchConfirmReqParamValidator")
	private ObjectConverter<BatchConfirmReqModel, ServiceContext, Result<Boolean>> batchConfirmReqParamValidator;

	@Resource(name = "confirmFinishedSendOpenApiMqHandler")
	private ConfirmFinishedSendOpenApiMqHandler confirmFinishedSendOpenApiMqHandler;

	@Resource(name = "mqUtil")
	private MQUtil mqUtil;

	@Override
	public Result<VoucherEntity> confirm(final ConfirmModel reqModel, final ServiceContext context) {
		try {
			logger.info("confirm request, reqModel:{}.", reqModel);
			confirmReqParamValidator.convert(reqModel, context);

			final Result<VourEntity> confirmResult = confirmEngine.confirm(reqModel);
			logger.info("confrim result, reqModel:{}, result:{}.", reqModel, confirmResult.getErrorMsg());

			if (!confirmResult.isOk()) {
				logger.warn("confrim fail, reqModel:{}, result:{}.", reqModel,
						confirmResult.getErrorCode() + confirmResult.getErrorMsg());
				return new Result<VoucherEntity>(confirmResult.getErrorCode(), confirmResult.getErrorMsg());
			}
			// 核销完毕后做相应的清算或分账操作
			final VourEntity voucher = confirmResult.getData();
			logger.info("confrim voucher, voucher:{}.", voucher);
			if (voucher.needAccoutHandel()) {
				asyncAccountAndMqHandle(buildAccoutHandleModel(voucher));
				sendOpenApiMq(voucher.getTransaction_id());
			}
			return new Result<VoucherEntity>();
		} catch (final Throwable t) {
			logger.error("confirm error, reqModel:" + reqModel, t);

			if (t instanceof ConfirmException) {
				final ConfirmException ce = (ConfirmException) t;
				return new Result<VoucherEntity>(ce.getErrCode(), ce.getMessage());
			}
			return new Result<VoucherEntity>(ConfirmErrorCode.CONFIRM_ERROR, "核销失败");
		}
	}

	/**
	 * 构建账号操作参数
	 *
	 * @param voucher
	 * @return
	 */
	private AccountHandleModel buildAccoutHandleModel(final VourEntity voucher) {
		final AccountHandleModel handleModel = new AccountHandleModel();
		handleModel.setTradeVersion(voucher.getVersion());
		handleModel.setTransactionId(voucher.getTransaction_id());
		handleModel.setVoucherId(voucher.getVoucher_id());
		handleModel.setCheckedNum(voucher.getCheckedNum());
		handleModel.setCheckedTime(voucher.getCheckedTime());
		return handleModel;
	}

	/**
	 * 核销后的清算及分账操作不影响用户的核销操作
	 *
	 * @param reqModel
	 * @param confirmResult
	 */
	private void asyncAccountAndMqHandle(final AccountHandleModel reqModel) {
		new Thread() {
			@Override
			public void run() {
				try {
					cleanOrTransferAccount.handle(reqModel);
				} catch (final Throwable t) {
					logger.error("async clean or transfer account fail.", t);
				}
			}
		}.start();
	}

	private void sendOpenApiMq(final String transactionId) {
		new Thread() {
			@Override
			public void run() {
				// 核销完毕发送openApi Mq消息
				confirmFinishedSendOpenApiMqHandler.sentHandler(transactionId);
			};
		}.start();
	}

	/**
	 * @author DongChunfu
	 * @see com.pzj.trade.confirm.service.ConfirmService#batchConfirm(com.pzj.trade.confirm.model.BatchConfirmReqModel, com.pzj.framework.context.ServiceContext)
	 */
	@Override
	public Result<ArrayList<ConfirmFailValue>> batchConfirm(final BatchConfirmReqModel reqModel,
			final ServiceContext serviceContext) {

		List<BatchConfirmWrapModel> wrapModels = null;
		try {// 批量核销请求基础校验
			logger.info("batch confrim req param:{}.", reqModel);
			batchConfirmReqParamValidator.convert(reqModel, serviceContext);

			wrapModels = wrapConfirmReqParam(reqModel.getSellOrderIds());
		} catch (final Throwable t) {
			logger.error("batch confirm req error,reqParam:" + JSONConverter.toJson(reqModel), t);

			if (t instanceof ConfirmException) {
				final ConfirmException ce = (ConfirmException) t;
				return new Result<ArrayList<ConfirmFailValue>>(ce.getErrCode(), ce.getMessage());
			}
			throw new ConfirmException(ConfirmErrorCode.CONFIRM_ERROR, (String) null, t);
		}

		final ArrayList<ConfirmFailValue> confirmFailValues = confirmTask(wrapModels);

		final Result<ArrayList<ConfirmFailValue>> batchConfirmResult = new Result<ArrayList<ConfirmFailValue>>();
		if (!Check.NuNCollections(confirmFailValues)) {
			batchConfirmResult.setData(confirmFailValues);
			batchConfirmResult.setErrorCode(ConfirmErrorCode.CONFIRM_ERROR);
			batchConfirmResult.setErrorMsg("批量核销失败信息");
		}

		logger.info("batch confirm final result:{}.", JSONConverter.toJson(batchConfirmResult));
		return batchConfirmResult;
	}

	/************************************************PRIVATE METHOD************************************************/
	/**
	 * 多线程执行批量核销任务
	 *
	 * @author DongChunfu
	 * @param wrapModels 封装后的核销请求参数
	 * @return 核销事变信息
	 */
	private ArrayList<ConfirmFailValue> confirmTask(final List<BatchConfirmWrapModel> wrapModels) {
		// 内部核销服务，采用多线程处理
		final int threadNum = Runtime.getRuntime().availableProcessors() - 1;
		logger.debug("batch confirm start thread num :{}.", threadNum);
		final ExecutorService executorService = Executors.newFixedThreadPool(threadNum);

		final ArrayList<ConfirmFailValue> confirmFailValues = new ArrayList<ConfirmFailValue>();
		final List<Future<ConfirmFailValue>> batchConfirmResult = new ArrayList<Future<ConfirmFailValue>>();
		final List<Callable<ConfirmFailValue>> confirmTasks = new ArrayList<Callable<ConfirmFailValue>>();
		for (final BatchConfirmWrapModel wrapModel : wrapModels) {
			final ConfirmModel confirmReqModel = buildConfirmReqModel(wrapModel);
			final Callable<ConfirmFailValue> confirmTask = new Callable<ConfirmFailValue>() {
				@Override
				public ConfirmFailValue call() throws Exception {
					ConfirmFailValue confirmFailValue = null;
					try {
						final Result<VoucherEntity> innerConfirmResult = confirm(confirmReqModel, (ServiceContext) null);
						logger.info("single voucher confrim reqParam:{},repResult:{}.", confirmReqModel,
								JSONConverter.toJson(innerConfirmResult));

						if (!innerConfirmResult.isOk()) {
							logger.warn("single voucher confrim fail repResult:{}.", JSONConverter.toJson(innerConfirmResult));
							confirmFailValue = buildFailRepResult(wrapModel, innerConfirmResult);
						}
					} catch (final Throwable t) {
						//TODO confirm service cath throwable,here nothing to do!
						logger.error("核销错误", t);
					}
					return confirmFailValue;
				}
			};
			batchConfirmResult.add(executorService.submit(confirmTask));
		}
		logger.debug("batch confirm task num:{}.", confirmTasks.size());

		try {
			for (final Future<ConfirmFailValue> confirmFuture : batchConfirmResult) {
				final ConfirmFailValue confirmFailValue = confirmFuture.get();
				if (!Check.NuNObject(confirmFailValue)) {
					logger.warn("confrim fail repResult:{}.", JSONConverter.toJson(confirmFailValue));
					confirmFailValues.add(confirmFailValue);
				}
			}
		} catch (InterruptedException | ExecutionException e) {
			logger.error("thread interrupted exception", e);
		} finally {
			executorService.shutdown();
		}

		return confirmFailValues;
	}

	/**
	 * 封装核销请求参数
	 *
	 * @author DongChunfu
	 * @param orderIds 请求核销的订单ID集合
	 * @return 批量核销请求封装参数
	 */
	private List<BatchConfirmWrapModel> wrapConfirmReqParam(final List<String> orderIds) {
		final List<ConfirmBatchEntity> requireParam = confirmEngine.queryConfirmBatchRequireParam(orderIds);
		return convertBatchConfirmWrapParam(requireParam);
	}

	/**
	 * 批量核销数据转换
	 *
	 * @author DongChunfu
	 * @param requireParam 订单核销实体
	 * @return 批量核销请求封装参数
	 */
	private List<BatchConfirmWrapModel> convertBatchConfirmWrapParam(final List<ConfirmBatchEntity> requireParam) {
		if (Check.NuNCollections(requireParam)) {
			throw new ConfirmOrderNotExistException(ConfirmErrorCode.CONFIRM_ORDER_NOT_EXIST_ERROR_CODE, "核销的所有订单都不存在");
		}
		final List<BatchConfirmWrapModel> wrapParam = new ArrayList<>(requireParam.size());
		for (final ConfirmBatchEntity param : requireParam) {
			final BatchConfirmWrapModel model = new BatchConfirmWrapModel();
			model.setOrderId(param.getOrder_id());
			model.setVoucherId(param.getVoucher_id());
			model.setMerchId(param.getMerch_id());
			model.setMerchName(param.getMerch_name());
			wrapParam.add(model);
		}
		return wrapParam;
	}

	/**
	 * 构建单一凭证核销请求参数
	 *
	 * @author DongChunfu
	 * @param wrapModel 封装请求参数
	 * @return 单一凭证核销请求参数
	 */
	private ConfirmModel buildConfirmReqModel(final BatchConfirmWrapModel wrapModel) {
		final ConfirmModel confirmReqModel = new ConfirmModel();
		confirmReqModel.setVoucherId(wrapModel.getVoucherId());
		confirmReqModel.setTicketPoint(-999L);
		return confirmReqModel;
	}

	/**
	 * 根据核销失败响应构建响应参数
	 *
	 * @author DongChunfu
	 * @param confirmFailValues 核销失败信息集合
	 * @param wrapModel 封装请求参数
	 * @param confirmFailResult 核销失败结果
	 */
	private ConfirmFailValue buildFailRepResult(final BatchConfirmWrapModel wrapModel,
			final Result<VoucherEntity> confirmFailResult) {
		final ConfirmFailValue failValue = new ConfirmFailValue();
		failValue.setOrderId(wrapModel.getOrderId());
		failValue.setVoucherId(wrapModel.getVoucherId());
		failValue.setMerchId(wrapModel.getMerchId());
		failValue.setMerchName(wrapModel.getMerchName());
		failValue.setFailMsg(confirmFailResult.getErrorMsg());
		return failValue;
	}
}
