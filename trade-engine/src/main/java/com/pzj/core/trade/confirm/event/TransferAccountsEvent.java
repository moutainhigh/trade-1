/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.confirm.event;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.confirm.common.ConfirmErrorCode;
import com.pzj.core.trade.confirm.exception.ConfirmException;
import com.pzj.core.trade.confirm.exception.TransferAccountException;
import com.pzj.core.trade.util.DoubleUtil;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.exception.ServiceException;
import com.pzj.framework.toolkit.Check;
import com.pzj.settlement.balance.request.RebateOperateDetail;
import com.pzj.settlement.balance.request.SaasVerificationVo;
import com.pzj.settlement.balance.request.SplitAccountDetail;
import com.pzj.settlement.balance.service.AccountBussinessService;
import com.pzj.trade.order.entity.RebateMerchEntity;
import com.pzj.trade.order.entity.TransferAccountsBaseDataEntity;
import com.pzj.trade.order.entity.TransferAccountsDetailParamEntity;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.OrderReadMapper;
import com.pzj.trade.order.read.OrderStrategyReadMapper;
import com.pzj.trade.order.vo.OrderTransferAccountsVO;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.read.FreezeFlowReadMapper;

/**
 * 统一分账处理事件
 *
 * @author DongChunfu
 * @version $Id: TransferAccountsEvent.java, v 0.1 2017年3月29日 上午11:41:06 DongChunfu Exp $
 */
@Component(value = "transferAccountsEvent")
public class TransferAccountsEvent {
	private static final Logger logger = LoggerFactory.getLogger(TransferAccountsEvent.class);

	/**魔方结算ID*/
	private static final long MF_CLEARING = 123456789L;

	@Autowired
	private OrderReadMapper orderReadMapper;

	@Autowired
	private MerchReadMapper merchReadMapper;

	@Autowired
	private OrderStrategyReadMapper orderStrategyReadMapper;

	@Resource(name = "queryTransferAccountsDetailEvent")
	private QueryTransferAccountsDetailEvent queryTransferAccountsDetailEvent;

	@Autowired
	private AccountBussinessService accountBussinessService;

	@Autowired
	private FreezeFlowReadMapper freezeFlowReadMapper;

	/**直客用户ID*/
	private static final long DIRECT_USER_ID = 967470L;

	/**
	 * 核销后分账
	 *
	 * @param transactionId
	 */
	public void transferAccounts(final String transactionId) {
		TransferAccountsEvent.logger.info("transfer account for transactionId:{}.", transactionId);

		// 检查交易是否进行了线上支付操作，并获取支付Sign
		final FreezeFlowEntity orderPayFlow = freezeFlowReadMapper.getFreezeFlow(transactionId, 1, 2);
		TransferAccountsEvent.logger.info("trade pay flow,transactionId:{},payFlow:{}", transactionId, orderPayFlow);
		if (Check.NuNObject(orderPayFlow)) {//若父订单没有支付流水则不进行分账操作
			TransferAccountsEvent.logger.info("trade pay flow is null,not need to transfer account,transactionId:{}.", transactionId);
			return;
		}

		final SaasVerificationVo splitAccountParam = buildTransferAccountParam(transactionId, orderPayFlow.getSign_id());

		transferAccount(splitAccountParam);
	}

	/**
	 * 调用账户服务进行分账操作
	 *
	 * @param splitAccountParam 分账请求参数
	 */
	private void transferAccount(final SaasVerificationVo splitAccountParam) {
		Result<Boolean> splitResult = null;
		try {
			TransferAccountsEvent.logger.info("call account transfer money,reqModel:{}.", JSONConverter.toJson(splitAccountParam));
			final List<RebateOperateDetail> rebateOperateDetail = splitAccountParam.getRebateOperateDetail();
			final List<SplitAccountDetail> splitAccountDetail = splitAccountParam.getSplitAccountDetail();
			if (Check.NuNCollections(rebateOperateDetail) && Check.NuNCollections(splitAccountDetail)) {
				TransferAccountsEvent.logger.info("not need to call account transfer money operate");
				return;
			}
			splitResult = accountBussinessService.saasVerification(splitAccountParam);
			TransferAccountsEvent.logger.info("call account transfer money result,reqModel:{},splitResult:{}.", JSONConverter.toJson(splitAccountParam),
					splitResult.getErrorMsg());
		} catch (final Throwable t) {
			TransferAccountsEvent.logger.error("call account transfer money error,reqModel:" + JSONConverter.toJson(splitAccountParam), t);
			if (t instanceof ServiceException) {
				throw new ConfirmException(ConfirmErrorCode.SPLIT_ACCOUNT_ERROR_CODE, splitResult.getErrorCode() + ":" + splitResult.getErrorMsg());
			}
			throw new ConfirmException(ConfirmErrorCode.SPLIT_ACCOUNT_ERROR_CODE, "调用账号分账失败");
		}
	}

	/**
	 * 根据交易ID查询分账数据，进行清结算分账操作
	 * 注：不能进行分页查询（queryParam.setPageable(Boolean.FALSE)）
	 *
	 * @param transactionId 交易ID
	 * @return 分账数据（包含SAAS订单及退款单数据）
	 */
	private List<OrderTransferAccountsVO> getTradeTransferAccoutData(final String transactionId) {
		final TransferAccountsDetailParamEntity queryParam = new TransferAccountsDetailParamEntity();
		queryParam.setTransactionId(transactionId);
		queryParam.setPageable(Boolean.FALSE);

		final List<TransferAccountsBaseDataEntity> baseDatas = orderReadMapper.queryTransferAccountsBaseData(queryParam);
		final List<OrderTransferAccountsVO> transferAccounts = queryTransferAccountsDetailEvent.transferAccounts(baseDatas);
		TransferAccountsEvent.logger.info("merch transfer accounts data:{}.", transferAccounts);
		return transferAccounts;
	}

	/**
	 * 构建分账参数
	 *
	 * @param transactionId 交易ID
	 * @param paySign 支付sign
	 * @return 分账请求参数
	 */
	private SaasVerificationVo buildTransferAccountParam(final String transactionId, final String paySign) {

		// 1:获取相关交易数据，按订单级别升序排列
		final List<OrderTransferAccountsVO> transferAccounts = getTradeTransferAccoutData(transactionId);

		final SaasVerificationVo splitAccountParam = new SaasVerificationVo();
		splitAccountParam.setTransactionId(transactionId);
		splitAccountParam.setSignId(paySign);
		splitAccountParam.setSplitAccountDetail(new ArrayList<SplitAccountDetail>(0));// 初始化，避免后续判空校验
		splitAccountParam.setRebateOperateDetail(new ArrayList<RebateOperateDetail>(0));// 初始化，避免后续判空校验

		calculateAmount(splitAccountParam, transferAccounts);

		return splitAccountParam;
	}

	/**
	 * 计算该交易每个用户应分金额
	 *
	 * @param splitAccountParam 分账请求参数
	 * @param details 交易的分账明细
	 */
	private void calculateAmount(final SaasVerificationVo splitAccountParam, final List<OrderTransferAccountsVO> details) {
		// 分账数据处理，区分saas订单 和 退款单
		final List<OrderTransferAccountsVO> saasOrders = new ArrayList<>();
		final List<OrderTransferAccountsVO> refundOrders = new ArrayList<>();
		for (final OrderTransferAccountsVO detail : details) {
			if (detail.getOrderType() == 1) {
				saasOrders.add(detail);
			} else {
				refundOrders.add(detail);
			}
		}

		for (final OrderTransferAccountsVO saasOrder : saasOrders) {
			if (saasOrder.getCurrentUserId() != TransferAccountsEvent.DIRECT_USER_ID) {// 直客不参与分账
				// 分商品的结算金额
				final double merchClearAmount = orderMerchAmount(saasOrder, refundOrders);
				if (merchClearAmount != 0) {
					final SplitAccountDetail splitAccount = new SplitAccountDetail();
					splitAccount.setUserId(saasOrder.getCurrentUserId());
					splitAccount.setAmount(DoubleUtil.precisionHandle(merchClearAmount, 2));
					splitAccountParam.getSplitAccountDetail().add(splitAccount);
				}

				// 分返利
				final List<RebateOperateDetail> earnRebate = earnRebate(saasOrder, refundOrders);
				if (!Check.NuNCollections(earnRebate)) {
					splitAccountParam.getRebateOperateDetail().addAll(earnRebate);
				}
			}
		}
	}

	/**
	 * 计算当前用户获得的商品结算金额
	 *
	 * @param saasOrder saas订单分账明细
	 * @param refundOrders 交易退款订单
	 * @return
	 */
	private double orderMerchAmount(final OrderTransferAccountsVO saasOrder, final List<OrderTransferAccountsVO> refundOrders) {
		if (!isMfClearing(saasOrder.getMerchClearingParty())) {
			return 0D;
		}
		return saasOrder.getMerchClearingAmount() + refundMerchAmount(refundOrders, saasOrder.getCurrentUserId());
	}

	/**
	 * 判断是否是魔方进行结算
	 *
	 * @param userId 用户ID
	 * @return <code>true</code>魔方结算;<code>false</code>用户结算.
	 */
	private boolean isMfClearing(final Long userId) {
		return Check.NuNObject(userId) ? false : userId == TransferAccountsEvent.MF_CLEARING;
	}

	/**
	 * 计算退款的退的商品金额
	 *
	 * @param refundOrders
	 * @return
	 */
	private double refundMerchAmount(final List<OrderTransferAccountsVO> refundOrders, final long currentUserId) {
		double totalAmount = 0D;
		if (Check.NuNCollections(refundOrders)) {
			return totalAmount;
		}

		for (final OrderTransferAccountsVO refundOrder : refundOrders) {
			if (refundOrder.getCurrentUserId() == currentUserId) {
				totalAmount += refundOrder.getMerchClearingAmount();
			}
		}
		return totalAmount;
	}

	/**
	 * 当前用户赚取的返利金额
	 *
	 * @param saasOrder 当前用户分账信息
	 * @param refundOrders 交易的退款信息
	 * @return 若集合为空，订单非线上支付或没有后返数据
	 */
	private List<RebateOperateDetail> earnRebate(final OrderTransferAccountsVO saasOrder, final List<OrderTransferAccountsVO> refundOrders) {

		// 计算当前SAAS订单用户赚取返利金额
		final double rebateAmount = calculateRebateAmount(saasOrder, refundOrders);
		if (rebateAmount == 0D) {// 当前用户在此次交易有由返利信息
			return null;
		}

		final Long resellerId = saasOrder.getCurrentUserId();
		final String transactionId = saasOrder.getTransactionId();

		// 获取订单商品检票大于零的商品信息
		final List<RebateMerchEntity> rebateMerches = merchReadMapper.getMerchRebateData(transactionId, resellerId);
		if (Check.NuNCollections(rebateMerches)) {
			return null;
		}

		final List<RebateOperateDetail> rebateOperateModels = new ArrayList<>(rebateMerches.size());

		for (final RebateMerchEntity rebateMerch : rebateMerches) {
			// 获取后返订单商品的政策ID
			final Long strategyId = orderStrategyReadMapper.getOrderMerchAfterRebateStrategyId(rebateMerch.getOrderId(), rebateMerch.getMerchId());
			if (Check.NuNObject(strategyId)) {
				TransferAccountsEvent.logger.info("商品：[{}]不是后返商品", rebateMerch.getMerchId());
				continue;
			}
			if (merchNumIslegal(rebateMerch)) {// 若为空说明该商品非后返商品
				final RebateOperateDetail rebateOperateModel = new RebateOperateDetail();
				rebateOperateModel.setResellerId(rebateMerch.getResellerId());
				rebateOperateModel.setSupplierId(rebateMerch.getSupplierId());
				rebateOperateModel.setSkuId(rebateMerch.getSkuId());
				rebateOperateModel.setSkuNumber(rebateMerch.getCheckedNum());
				rebateOperateModel.setStrategyId(strategyId);
				rebateOperateModels.add(rebateOperateModel);
			}
		}
		return rebateOperateModels;
	}

	private boolean merchNumIslegal(final RebateMerchEntity rebateMerch) {
		final int checkedNum = rebateMerch.getCheckedNum();
		if (checkedNum == 0) {
			return Boolean.FALSE;
		}
		final int refundNum = rebateMerch.getRefundNum();
		final int totalNum = rebateMerch.getTotalNum();
		if (totalNum == refundNum) {
			return Boolean.FALSE;
		}

		if (totalNum != (refundNum + checkedNum)) {
			throw new TransferAccountException(101010, "商品未全部核销，不进行分账操作");
		}
		return Boolean.TRUE;
	}

	private double calculateRebateAmount(final OrderTransferAccountsVO saasOrder, final List<OrderTransferAccountsVO> refundOrders) {

		if (!isMfClearing(saasOrder.getRebateClearingParty())) {
			return 0D; // 非魔方结算的不进行分账
		}
		final double rebateAmount = saasOrder.getRebateTotalAmout();

		double refundRebateAmount = 0D;
		if (!Check.NuNCollections(refundOrders)) {
			for (final OrderTransferAccountsVO refundOrder : refundOrders) {
				if (refundOrder.getCurrentUserId().longValue() == saasOrder.getCurrentUserId().longValue()) {
					refundRebateAmount += refundOrder.getRebateTotalAmout();
				}
			}
		}
		// 渠道的退款返利本身即为负数，不要进行减法运算
		return rebateAmount + refundRebateAmount;
	}

}
