/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.confirm.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.confirm.common.OrderRebateMethodEnum;
import com.pzj.core.trade.confirm.common.RebateSettleMethodEnum;
import com.pzj.core.trade.confirm.common.TransferAccountOrderStateEnum;
import com.pzj.core.trade.confirm.common.TransferAccountOrderTypeEnum;
import com.pzj.core.trade.order.engine.common.PayWayEnum;
import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundFlowStateEnum;
import com.pzj.core.trade.refund.read.RefundFlowReadMapper;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchNumEntity;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.OrderRebateEntity;
import com.pzj.trade.order.entity.OrderRefundEntity;
import com.pzj.trade.order.entity.OrderTransferAccountsDetailEntity;
import com.pzj.trade.order.entity.TransferAccountsBaseDataEntity;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.OrderReadMapper;
import com.pzj.trade.order.vo.OrderPayVO;
import com.pzj.trade.order.vo.OrderRebateVO;
import com.pzj.trade.order.vo.OrderTransferAccountsVO;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.read.FreezeFlowReadMapper;

/**
 * 分账明细数据处理事件
 *
 * @author DongChunfu
 * @version $Id: QueryTransferAccountsDetailEvent.java, v 0.1 2017年3月29日 上午11:40:20 DongChunfu Exp $
 */
@Component(value = "queryTransferAccountsDetailEvent")
@Scope(value = "prototype")
public class QueryTransferAccountsDetailEvent {

	/**交易商品数量缓存，key=transactionId*/
	private final Map<String, List<MerchNumEntity>> MERCH_CACHE = new HashMap<String, List<MerchNumEntity>>(2, 1);

	/**魔方结算ID*/
	private static final long MF_CLEARING = 123456789L;
	/**直客用户ID*/
	private static final long DIRECT_USER_ID = 967470L;

	@Autowired
	private OrderReadMapper orderReadMapper;

	@Autowired
	private MerchReadMapper merchReadMapper;

	@Autowired
	private RefundFlowReadMapper refundFlowReadMapper;

	@Autowired
	private FreezeFlowReadMapper freezeFlowReadMapper;

	@Resource(name = "getRefundOrderPayWaySubEvent")
	private GetRefundOrderPayWaySubEvent getRefundOrderPayWaySubEvent;

	public List<OrderTransferAccountsVO> transferAccounts(final List<TransferAccountsBaseDataEntity> baseDatas) {
		final List<OrderTransferAccountsVO> transferAccountsResult = new ArrayList<OrderTransferAccountsVO>();
		for (final TransferAccountsBaseDataEntity baseDate : baseDatas) {
			final List<OrderTransferAccountsDetailEntity> baseDetailDatas = orderReadMapper
					.queryTransferAccountsDetailByBaseDate(baseDate);
			final String refundId = baseDate.getRefundId();
			List<OrderRefundEntity> refundInfo = null;
			if (!Check.NuNString(refundId)) {
				refundInfo = refundFlowReadMapper.getOrderRefundInfo(refundId);
			}
			for (final OrderTransferAccountsDetailEntity baseDetailDate : baseDetailDatas) {
				transferAccountsResult.addAll(wrapSingleDetail(baseDetailDate, refundInfo));
			}
		}
		return transferAccountsResult;
	}

	/**
	 * 对单个订单的拼装 SAAS订单和退款单的分账明细信息
	 *
	 * @param baseData 订单分账基础数据
	 * @return 单个订单的 SAAS订单 和 退款单 分析明细数据
	 */
	public List<OrderTransferAccountsVO> wrapSingleDetail(final OrderTransferAccountsDetailEntity baseData,
			final List<OrderRefundEntity> refundInfo) {
		final List<OrderTransferAccountsVO> detailVOs = new ArrayList<OrderTransferAccountsVO>(2);
		if (Check.NuNCollections(refundInfo)) {//依据订单退款信息判定是否为退款单
			//1:若是根订单需计算初始供应商的分账表
			if (baseData.isRootOrder()) {
				final OrderTransferAccountsVO rootSaasOrder = buildSaasOrderTransferAccountsDetail(baseData, true, false);
				detailVOs.add(rootSaasOrder);
			}
			//2:基于当前订单的分销进行分账
			final OrderTransferAccountsVO saasOrder = buildSaasOrderTransferAccountsDetail(baseData, false, false);
			detailVOs.add(saasOrder);

			//3:若是微店订单，且是父订单，构建直客分账表
			if (baseData.isMerchantOrder()) {
				final OrderTransferAccountsVO directUserSaasOrder = buildSaasOrderTransferAccountsDetail(baseData, false, true);
				detailVOs.add(directUserSaasOrder);
			}
		} else {//refundInfo不为空,则构建退款单
			detailVOs.addAll(buildRefundOrderBaseInfo(baseData, refundInfo));
		}
		return detailVOs;
	}

	/**
	 * 构建saas订单分账明细
	 *
	 * @param baseData 订单分账基础数据
	 * @param orderRebates 订单商品后返商品的返利集合
	 * @return
	 */
	private OrderTransferAccountsVO buildSaasOrderTransferAccountsDetail(final OrderTransferAccountsDetailEntity baseData,
			final boolean initalSupplier, final boolean isDirectUser) {

		//1:复制SAAS订单的基础信息
		final OrderTransferAccountsVO baseDetail = copySassOrderBaseInfo(baseData, initalSupplier, isDirectUser);
		//2:直客用户没有商品结算金额和返利金
		if (isDirectUser) {
			return baseDetail;
		}
		//3:根订单没有返利）
		if (!initalSupplier) {
			setSaasOrderAfterRebateInfo(baseDetail, baseData);
		}
		//4:构建商品结算信息
		setSaasMerchSettleInfo(baseDetail, baseData, initalSupplier);
		return baseDetail;
	}

	/**
	 * 设置SAAS订单的后返返利信息
	 * 末级订单若为微店订单，则返利方式为微店，否则为订单返利
	 * 若订单为线上支付，则订单返利结算方为魔方，否则为对应订单的供应商
	 *
	 * @param baseDetail 商品分账基础信息
	 * @param baseData 商品分账基础数据
	 */
	private void setSaasOrderAfterRebateInfo(final OrderTransferAccountsVO baseDetail,
			final OrderTransferAccountsDetailEntity baseData) {

		final List<OrderRebateEntity> orderRebates = baseData.getOrderRebates();
		if (Check.NuNCollections(orderRebates)) {
			return;
		}

		final List<OrderRebateEntity> directRebates = OrderRebateEntity.filterRebateWay(orderRebates,
				OrderRebateMethodEnum.AFTER.getMethod(), RebateSettleMethodEnum.IMMEDIATE.getMethod());
		final List<OrderRebateEntity> periodRebates = OrderRebateEntity.filterRebateWay(orderRebates,
				OrderRebateMethodEnum.AFTER.getMethod(), RebateSettleMethodEnum.PERIOD.getMethod());

		//1:立返返利信息
		final String transactionId = baseData.getTransactionId();
		final OrderRebateVO directRebate = buildSaasAfterRebateVO(directRebates, transactionId);
		if (!Check.NuNObject(directRebate)) {
			if (baseData.isMerchantOrder()) {
				baseDetail.getWeshopRebates().add(directRebate);
			} else {
				baseDetail.getOrderRebates().add(directRebate);
			}
		}
		//2:周返返利信息
		final OrderRebateVO periodRebate = buildSaasAfterRebateVO(periodRebates, transactionId);
		if (!Check.NuNObject(periodRebate)) {
			if (baseData.isMerchantOrder()) {
				baseDetail.getWeshopRebates().add(periodRebate);
			} else {
				baseDetail.getOrderRebates().add(periodRebate);
			}
		}

		if (!baseData.isAfterPay()) {
			baseDetail.setRebateClearingParty(MF_CLEARING);
		} else {
			baseDetail.setRebateClearingParty(baseData.getSupplierId());
		}

	}

	/**
	 * 设置SAAS订单商品结算信息
	 *
	 * @param baseDetail 商品分账基础信息
	 * @param baseData 商品分账基础数据
	 */
	private void setSaasMerchSettleInfo(final OrderTransferAccountsVO baseDetail,
			final OrderTransferAccountsDetailEntity baseData, final boolean initalSupplier) {

		OrderTransferAccountsDetailEntity sellOrder = null;
		Long merchClearingParty = null;
		if (initalSupplier) {
			merchClearingParty = baseData.isAfterPay() ? baseData.getResellerId() : MF_CLEARING;
		} else if (!baseData.isParentOrder()) {
			sellOrder = orderReadMapper.getRelativeOrder(baseData.getTransactionId(), baseData.getParentOrderId(),
					(String) null);
			merchClearingParty = sellOrder.isAfterPay() ? sellOrder.getResellerId() : MF_CLEARING;
		}
		baseDetail.setMerchClearingParty(merchClearingParty);

		final List<MerchNumEntity> merches = getTradeMerchNums(baseData.getTransactionId());
		if (initalSupplier) {
			if (baseData.isAfterPay()) {
				baseDetail.setMerchClearingAmount(baseData.getOrderAmount());
			} else {
				baseDetail.setMerchClearingAmount(merchSettlePriceAmount(merches, baseData.getOrderRebates()));
			}
			return;
		}

		if (baseData.isParentOrder()) {//若是父订单,没有商品结算信息
			return;
		}

		//商品结算金额=Σ【建议零售价（后返）/结算价（前返）*规格总数量】
		if (sellOrder.isAfterPay()) {
			baseDetail.setMerchClearingAmount(sellOrder.getOrderAmount());
			return;
		}

		// 当前用户结算价
		final double merchSettlePriceAmount = merchSettlePriceAmount(merches, sellOrder.getOrderRebates());
		if (baseData.isAfterPay()) {
			baseDetail.setMerchClearingAmount(merchSettlePriceAmount);
			return;
		}

		// 上级底价
		final OrderTransferAccountsDetailEntity upLevelOrder = orderReadMapper.getRelativeOrder(baseData.getTransactionId(),
				(String) null, baseData.getParentOrderId());
		baseDetail.setMerchClearingAmount(merchSettlePriceAmount - upLevelOrder.getOrderAmount());
	}

	/**
	 * 商品结算价金额
	 *
	 * @param merches 交易商品数量信息
	 * @param allRebates 订单的所有返利信息
	 * @return
	 */
	private double merchSettlePriceAmount(final List<MerchNumEntity> merches, final List<OrderRebateEntity> allRebates) {
		double totalAmount = 0D;
		final Map<String, OrderRebateEntity> rebateMap = OrderRebateEntity.convertRebateToMap(allRebates);
		for (final MerchNumEntity merch : merches) {
			final String merchId = merch.getMerchId();
			totalAmount += merch.getTotalNum() * rebateMap.get(merchId).getSettlementPrice();
		}
		return totalAmount;
	}

	/**
	 * 构建SAAS订单返利数据
	 *
	 * @param unifyRebates 统一返利数据
	 * @param transactionId 交易ID
	 * @return 返利详细信息
	 */
	private OrderRebateVO buildSaasAfterRebateVO(final List<OrderRebateEntity> unifyRebates, final String transactionId) {
		if (Check.NuNCollections(unifyRebates)) {
			return null;
		}

		final OrderRebateVO rebate = new OrderRebateVO();
		// 批量获取商品数量信息
		final List<MerchNumEntity> merches = getTradeMerchNums(transactionId);
		final Map<String, MerchNumEntity> conMerch = MerchNumEntity.convertToMerchNumMap(merches);

		// 计算统一返利方式的总金额
		double rebateAmount = 0D;
		for (final OrderRebateEntity rebateData : unifyRebates) {
			rebateAmount += conMerch.get(rebateData.getMerchId()).getTotalNum() * rebateData.getAfterRebateAmount();
		}

		rebate.setRebateWay(unifyRebates.get(0).getRebateSettlement());
		rebate.setRebateAmount(rebateAmount);
		return rebate;
	}

	private List<MerchNumEntity> getTradeMerchNums(final String transactionId) {
		List<MerchNumEntity> merchNums = MERCH_CACHE.get(transactionId);
		if (Check.NuNCollections(merchNums)) {
			merchNums = merchReadMapper.getMerchNumEntityByTid(transactionId);
			MERCH_CACHE.put(transactionId, merchNums);
		}
		return merchNums;
	}

	/**
	 * 复制SAAS订单分账明细的基础信息
	 *
	 * @param baseData
	 * @param refundInfo
	 * @param isRootOrder
	 * @param isDirectUser
	 * @param isParentOrder
	 * @return
	 */
	private OrderTransferAccountsVO copySassOrderBaseInfo(final OrderTransferAccountsDetailEntity baseData,
			final boolean initalSupplier, final boolean isDirectUser) {
		final OrderTransferAccountsVO transerAccounts = new OrderTransferAccountsVO();

		transerAccounts.setTransactionId(baseData.getTransactionId());
		transerAccounts.setOrderType(TransferAccountOrderTypeEnum.SAAS_ORDER.getType());

		final List<OrderPayVO> orderPays = new ArrayList<>(2);
		final Double orderAmount = baseData.getOrderAmount();
		afterPay: {
			if (!initalSupplier) {
				final int payWay = baseData.getPayWay();
				final String orderId = baseData.getOrderId();
				final FreezeFlowEntity payFlow = freezeFlowReadMapper.getFreezeFlow(orderId, 1, 2);
				if (Check.NuNObject(payFlow)) {
					final OrderPayVO afterPay = new OrderPayVO();
					afterPay.setOrderPayAmount(orderAmount);
					afterPay.setOrderPayWay(payWay);
					orderPays.add(afterPay);
					break afterPay;
				}

				if (baseData.isMerchantOrder() && !isDirectUser) {// 微店次末级用户订单支付方式修改
					final OrderPayVO accountOrderPay = new OrderPayVO();
					accountOrderPay.setOrderPayAmount(orderAmount);
					accountOrderPay.setOrderPayWay(PayWayEnum.ACCOUNT.getPayWay());
					orderPays.add(accountOrderPay);
					break afterPay;
				}

				final double balanceAmount = payFlow.getBalance_amount();
				if (!baseData.isParentOrder()) {
					if (balanceAmount > 0) {
						final OrderPayVO balancePay = new OrderPayVO();
						balancePay.setOrderPayAmount(balanceAmount);
						balancePay.setOrderPayWay(PayWayEnum.BALANCE.getPayWay());
						orderPays.add(balancePay);
					}
					if (balanceAmount < orderAmount) {
						final OrderPayVO accountOrderPay = new OrderPayVO();
						accountOrderPay.setOrderPayAmount(orderAmount - balanceAmount);
						accountOrderPay.setOrderPayWay(PayWayEnum.ACCOUNT.getPayWay());
						orderPays.add(accountOrderPay);
					}
				} else {// 夫订单，需要区分混合支付
					if (payWay == PayWayEnum.MIXED.getPayWay()) {
						final OrderPayVO balancePay = new OrderPayVO();
						balancePay.setOrderPayAmount(balanceAmount);
						balancePay.setOrderPayWay(PayWayEnum.BALANCE.getPayWay());
						orderPays.add(balancePay);

						final OrderPayVO thirdPay = new OrderPayVO();
						thirdPay.setOrderPayAmount(orderAmount - balanceAmount);
						thirdPay.setOrderPayWay(baseData.getThirdPayType());
						orderPays.add(thirdPay);

					} else {
						final OrderPayVO orderPay = new OrderPayVO();
						orderPay.setOrderPayAmount(orderAmount);
						orderPay.setOrderPayWay(payWay);
						orderPays.add(orderPay);
					}
				}
			}
		}
		transerAccounts.setOrderPay(orderPays);
		transerAccounts.setOrderAmount(orderAmount);
		transerAccounts.setOrderCreateTime(baseData.getCreateTime());
		if (initalSupplier) {
			transerAccounts.setCurrentUserId(baseData.getSupplierId());
		} else if (isDirectUser) {
			transerAccounts.setCurrentUserId(DIRECT_USER_ID);
		} else {
			transerAccounts.setCurrentUserId(baseData.getResellerId());
		}
		final int orderState = baseData.getOrderState();
		final int saasOrderState = orderState == OrderStatusEnum.Refunded.getValue() ? 4
				: orderState == OrderStatusEnum.AlreadyPaid.getValue() ? 1 : 2;

		final Date confirmTime = baseData.getConfirmTime();
		transerAccounts.setOrderState(saasOrderState);
		transerAccounts.setOrderFinishTime(Check.NuNObject(confirmTime) ? baseData.getPayTime() : confirmTime);
		transerAccounts.setOrderTotalCount(baseData.getTotalNum());

		transerAccounts.setOrderPayTime(baseData.getPayTime());
		if (isDirectUser) {
			transerAccounts.setPayeeParty(baseData.getResellerId());
		} else if (!initalSupplier) {
			transerAccounts.setPayeeParty(baseData.getSupplierId());
		}
		return transerAccounts;
	}

	/**
	 * 构建退款订单的退款单
	 *
	 * @param baseData 订单基础数据
	 * @return 订单的退款单分账信息
	 */
	private List<OrderTransferAccountsVO> buildRefundOrderBaseInfo(final OrderTransferAccountsDetailEntity baseData,
			final List<OrderRefundEntity> refundInfo) {

		final List<OrderTransferAccountsVO> refundOrders = new ArrayList<OrderTransferAccountsVO>();

		OrderTransferAccountsDetailEntity childOrderBaseData = null;
		if (!baseData.isParentOrder()) {// 若是父订单，则无需查找
			childOrderBaseData = orderReadMapper.getRelativeOrder(baseData.getTransactionId(), baseData.getParentOrderId(),
					(String) null);
		}

		//1:过滤当前订单的退款流水记录
		final List<OrderRefundEntity> orderRefundInfo = OrderRefundEntity.fillterByOrderId(refundInfo, baseData.getOrderId());

		if (baseData.isRootOrder()) {//2:若当前订单为初始供应商订单，则首先以供应商进行退款分账表
			final OrderTransferAccountsVO baseDetail = copyRefundOrderBaseInfo(baseData, childOrderBaseData, refundInfo, true,
					false, false);
			//初始供应商没有退款返利信息，直接进行商品结算金额分账
			setRefundMerchSettleInfo(baseDetail, baseData, childOrderBaseData, refundInfo, true);
			refundOrders.add(baseDetail);
		}

		if (baseData.isParentOrder()) {//若是夫订单，需要构建末级分销商退款分账明细
			final OrderTransferAccountsVO lastResellerBaseDetail = copyRefundOrderBaseInfo(baseData, childOrderBaseData,
					refundInfo, false, false, true);
			setRefundRebateVO(lastResellerBaseDetail, baseData, orderRefundInfo);
			refundOrders.add(lastResellerBaseDetail);
		} else {
			final OrderTransferAccountsVO baseDetail = copyRefundOrderBaseInfo(baseData, childOrderBaseData, refundInfo, false,
					false, false);
			setRefundRebateVO(baseDetail, baseData, orderRefundInfo);
			setRefundMerchSettleInfo(baseDetail, baseData, childOrderBaseData, refundInfo, false);
			refundOrders.add(baseDetail);
		}

		if (baseData.isMerchantOrder()) {// 若是夫订单且为微店订单，需要构建直客退款分账明细
			final OrderTransferAccountsVO directUserBaseDetail = copyRefundOrderBaseInfo(baseData, childOrderBaseData,
					refundInfo, false, true, false);
			refundOrders.add(directUserBaseDetail);
		}
		return refundOrders;
	}

	/**
	 * 复制退款单分账明细的基础信息
	 *
	 * @param baseData
	 * @param refundInfo
	 * @param isRootOrder
	 * @param isDirectUser
	 * @param isParentOrder
	 * @return
	 */
	private OrderTransferAccountsVO copyRefundOrderBaseInfo(final OrderTransferAccountsDetailEntity baseData,
			final OrderTransferAccountsDetailEntity childOrderBaseData, final List<OrderRefundEntity> refundInfo,
			final boolean isRootOrder, final boolean isDirectUser, final boolean isParentOrder) {
		final OrderTransferAccountsVO transerAccounts = new OrderTransferAccountsVO();

		transerAccounts.setTransactionId(baseData.getTransactionId());
		transerAccounts.setOrderType(TransferAccountOrderTypeEnum.REFUND_ORDER.getType());
		final List<OrderRefundEntity> baseDateRefundInfo = OrderRefundEntity.fillterByOrderId(refundInfo,
				baseData.getOrderId());

		// 获取退款单的支付方式
		final List<OrderPayVO> orderPays = getRefundOrderPayWaySubEvent.getPayWay(baseData, childOrderBaseData, refundInfo,
				isRootOrder, isDirectUser, isParentOrder, baseDateRefundInfo);

		final String refundId = refundInfo.get(0).getRefundId();

		transerAccounts.setOrderPay(orderPays);
		transerAccounts.setOrderAmount(baseData.getOrderAmount());
		transerAccounts.setRefundId(refundId);

		if (isParentOrder) {
			if (baseData.isMerchantOrder()) {
				transerAccounts.setCurrentUserId(baseData.getResellerId());
				transerAccounts.setPayeeParty(DIRECT_USER_ID);
			} else {
				transerAccounts.setCurrentUserId(baseData.getResellerId());
			}
		} else if (isDirectUser) {
			transerAccounts.setCurrentUserId(DIRECT_USER_ID);
		} else if (isRootOrder) {
			transerAccounts.setCurrentUserId(baseData.getSupplierId());
			transerAccounts.setPayeeParty(baseData.getResellerId());
		} else {
			transerAccounts.setCurrentUserId(baseData.getResellerId());
			final long payeeParty = Check.NuNObject(childOrderBaseData) ? null : childOrderBaseData.getResellerId();
			transerAccounts.setPayeeParty(payeeParty);
		}

		transerAccounts.setOrderTotalCount(OrderRefundEntity.calculateRefundCount(baseDateRefundInfo));
		final OrderRefundEntity orderRefund = refundInfo.get(0);
		final int state = orderRefund.getRefundState();
		transerAccounts.setOrderCreateTime(orderRefund.getCreateTime());
		final Date refundDate = orderRefund.getRefundDate();
		if (state == RefundFlowStateEnum.REFUNDING.getState()) {
			transerAccounts.setOrderPayTime((Date) null);
			transerAccounts.setOrderFinishTime((Date) null);
			transerAccounts.setOrderState(TransferAccountOrderStateEnum.REFUNDING.getState());
		}
		if (state == RefundFlowStateEnum.SUCCESS.getState()) {// 退款成功
			transerAccounts.setOrderPayTime(refundDate);
			transerAccounts.setOrderFinishTime(refundDate);
			transerAccounts.setOrderState(TransferAccountOrderStateEnum.REFUNDED.getState());
		}
		return transerAccounts;
	}

	/**
	 * 构建退款返利信息
	 *
	 * @param baseDetail
	 * @param baseData
	 * @param unifyRefunds
	 */
	private void setRefundRebateVO(final OrderTransferAccountsVO baseDetail, final OrderTransferAccountsDetailEntity baseData,
			final List<OrderRefundEntity> unifyRefunds) {

		setRefundRebateInfo(baseDetail, baseData, unifyRefunds);
		if (baseData.isAfterPay()) {
			baseDetail.setRebateClearingParty(baseData.getSupplierId());
		} else {
			baseDetail.setRebateClearingParty(MF_CLEARING);
		}
	}

	private void setRefundMerchSettleInfo(final OrderTransferAccountsVO baseDetail,
			final OrderTransferAccountsDetailEntity baseData, final OrderTransferAccountsDetailEntity childOrderBaseData,
			final List<OrderRefundEntity> refundInfo, final boolean initialSupplier) {

		Long merchClearingParty = null;
		if (initialSupplier) {
			merchClearingParty = baseData.isAfterPay() ? baseData.getResellerId() : MF_CLEARING;
		} else {
			merchClearingParty = childOrderBaseData.isAfterPay() ? childOrderBaseData.getResellerId() : MF_CLEARING;
		}
		baseDetail.setMerchClearingParty(merchClearingParty);

		final int refundType = refundInfo.get(0).getIsForce();
		if (refundType == RefundApplyTypeEnum.FORCE.getType()) {
			forceRefundMerchSettleInfo(baseDetail, baseData, childOrderBaseData, childOrderBaseData, refundInfo,
					initialSupplier);
		} else {
			normalRefundMerchSettleInfo(baseDetail, baseData, childOrderBaseData, childOrderBaseData, refundInfo,
					initialSupplier);
		}
	}

	/**
	 * 强制退款商品结算信息
	 *
	 * @param baseDetail 分账明细基本信息
	 * @param baseData 分账明细基础数据
	 * @param childOrder 下级订单
	 * @param orderRefunds 订单退款商品流水
	 */
	private void forceRefundMerchSettleInfo(final OrderTransferAccountsVO baseDetail,
			final OrderTransferAccountsDetailEntity baseData, final OrderTransferAccountsDetailEntity childOrderBaseData,
			final OrderTransferAccountsDetailEntity sellOrder, final List<OrderRefundEntity> refundInfo,
			final boolean initialSupplier) {

		// 底价
		final List<OrderRefundEntity> purchaseOrderRefunds = OrderRefundEntity.fillterByOrderId(refundInfo,
				baseData.getOrderId());
		final List<OrderRebateEntity> purchaseOrderRebates = baseData.getOrderRebates();
		final double floorPrice = calculateFloorPrice(purchaseOrderRefunds, purchaseOrderRebates);
		// 结算价,（特殊情况为一级订单）
		List<OrderRebateEntity> sellOrderRebates = baseData.getOrderRebates();
		List<OrderRefundEntity> sellOrderRefunds = OrderRefundEntity.fillterByOrderId(refundInfo, baseData.getOrderId());
		if (!Check.NuNObject(sellOrder)) {
			sellOrderRebates = sellOrder.getOrderRebates();
			sellOrderRefunds = OrderRefundEntity.fillterByOrderId(refundInfo, sellOrder.getOrderId());
		}
		final double settlePrice = refundUseSettlePrice(sellOrderRefunds, sellOrderRebates);
		final double refundAmount = calculateRefundMerchAmount(sellOrderRefunds);
		if (initialSupplier) {
			if (baseData.isAfterPay()) {
				baseDetail.setMerchClearingAmount(refundAmountHandle(calculateRefundMerchAmount(purchaseOrderRefunds)));
			} else {
				baseDetail.setMerchClearingAmount(
						refundAmountHandle(refundUseSettlePrice(purchaseOrderRefunds, purchaseOrderRebates)));
			}
			return;
		}

		if (sellOrder.isAfterPay()) {// 子订单为后付
			baseDetail.setMerchClearingAmount(refundAmountHandle(refundAmount));
			return;
		}

		if (baseData.isAfterPay()) {
			baseDetail.setMerchClearingAmount(refundAmountHandle(settlePrice));
			return;
		}

		baseDetail.setMerchClearingAmount(refundAmountHandle(settlePrice - floorPrice));
	}

	/**
	 * 普通退款商品结算信息
	 *
	 * @param baseDetail 分账明细基本信息
	 * @param baseData 分账明细基础数据
	 * @param childOrder 下级订单
	 * @param orderRefunds 订单退款商品流水
	 */
	private void normalRefundMerchSettleInfo(final OrderTransferAccountsVO baseDetail,
			final OrderTransferAccountsDetailEntity baseData, final OrderTransferAccountsDetailEntity childOrderBaseData,
			final OrderTransferAccountsDetailEntity sellOrder, final List<OrderRefundEntity> refundInfo,
			final boolean initialSupplier) {

		// 底价
		final List<OrderRefundEntity> purchaseOrderRefunds = OrderRefundEntity.fillterByOrderId(refundInfo,
				baseData.getOrderId());
		final List<OrderRebateEntity> purchaseOrderRebates = baseData.getOrderRebates();

		// 结算价（特殊情况为一级订单）
		List<OrderRebateEntity> sellOrderRebates = baseData.getOrderRebates();
		List<OrderRefundEntity> sellOrderRefunds = OrderRefundEntity.fillterByOrderId(refundInfo, baseData.getOrderId());
		if (!Check.NuNObject(sellOrder)) {
			sellOrderRebates = sellOrder.getOrderRebates();
			sellOrderRefunds = OrderRefundEntity.fillterByOrderId(refundInfo, sellOrder.getOrderId());
		}

		// 销售订单返利金
		final double sellRefundRebate = getSellOrderRefundRebate(sellOrderRebates, sellOrderRefunds);
		if (initialSupplier) {
			double merchClearingAmount = 0D;
			if (baseData.isAfterPay()) {
				merchClearingAmount = refundAmountHandle(calculateRefundMerchAmount(purchaseOrderRefunds));
			} else {
				final double currentRefundRebate = getSellOrderRefundRebate(purchaseOrderRebates, purchaseOrderRefunds);
				merchClearingAmount = refundAmountHandle(
						calculateRefundMerchAmount(purchaseOrderRefunds) - currentRefundRebate);
			}
			baseDetail.setMerchClearingAmount(merchClearingAmount);
			return;
		}

		final double refundAmount = calculateRefundMerchAmount(sellOrderRefunds);
		if (sellOrder.isAfterPay()) {
			baseDetail.setMerchClearingAmount(refundAmountHandle(refundAmount));
			return;
		}
		//建议零售价（后返）/结算价（前返）*规格总数量*退款比例-下级退还订单返利-下级退还微店返利
		if (baseData.isAfterPay()) {//签单+线上支付
			baseDetail.setMerchClearingAmount(refundAmountHandle(refundAmount - sellRefundRebate));
			return;
		}

		baseDetail.setMerchClearingAmount(refundAmountHandle(
				calculateSubFloorPrice(sellOrderRefunds, purchaseOrderRebates, sellOrderRebates) - sellRefundRebate));
	}

	/**
	 * (建议零售价（后返）/结算价（前返）-底价)*规格总数量*退款比例
	 */
	private double calculateSubFloorPrice(final List<OrderRefundEntity> sellOrderRefunds,
			final List<OrderRebateEntity> purchaseOrderRebates, final List<OrderRebateEntity> sellOrderRebates) {

		final Map<String, OrderRebateEntity> purchaseRebate = OrderRebateEntity.convertRebateToMap(purchaseOrderRebates);
		final Map<String, OrderRebateEntity> sellRebate = OrderRebateEntity.convertRebateToMap(sellOrderRebates);

		double totalAmount = 0D;
		for (final OrderRefundEntity refundMerch : sellOrderRefunds) {
			final String merchId = refundMerch.getMerchId();
			final int refundNum = refundMerch.getRefundNum();
			// 退款价格
			final double settlePrice = refundNum * refundMerch.getRefundPrice();
			// 退款比例
			double refundProportion = 1;
			if (refundMerch.getRefundPrice() < sellRebate.get(merchId).getPrice()) {
				refundProportion = refundMerch.getRefundPrice() / sellRebate.get(merchId).getPrice();
			}
			// 退款底价
			final double refundFloorPrice = refundNum * purchaseRebate.get(merchId).getPrice() * refundProportion;

			totalAmount += settlePrice - refundFloorPrice;
		}
		return totalAmount;
	}

	/**
	 * 计算退款底价
	 *
	 * @param orderRefunds 商品退款记录
	 * @param parentOrderRebates 上级订单政策价格信息
	 * @return
	 */
	private double calculateFloorPrice(final List<OrderRefundEntity> purchaseOrderRefunds,
			final List<OrderRebateEntity> purchaseOrderRebates) {

		final Map<String, OrderRebateEntity> conRebateMap = OrderRebateEntity.convertRebateToMap(purchaseOrderRebates);
		double totalAmount = 0D;
		for (final OrderRefundEntity refund : purchaseOrderRefunds) {
			totalAmount += conRebateMap.get(refund.getMerchId()).getPrice() * refund.getRefundNum();
		}
		return totalAmount;
	}

	/**
	 * 强制退款商品结算价格计算（ 结算价*规格总数量）
	 *
	 * @param orderRefunds
	 * @param merches
	 * @param rebates
	 * @return
	 */
	private double refundUseSettlePrice(final List<OrderRefundEntity> sellOrderRefunds,
			final List<OrderRebateEntity> sellOrderRebates) {
		if (Check.NuNCollections(sellOrderRefunds)) {
			return 0D;
		}
		final Map<String, OrderRebateEntity> comRebates = OrderRebateEntity.convertRebateToMap(sellOrderRebates);
		double totalAmount = 0D;
		for (final OrderRefundEntity refund : sellOrderRefunds) {
			totalAmount += comRebates.get(refund.getMerchId()).getSettlementPrice() * refund.getRefundNum();
		}
		return totalAmount;
	}

	/**
	 * 下级订单退款返利金额
	 *
	 * @param sellOrderRebates
	 * @param sellOrderRefunds
	 * @return
	 */
	private double getSellOrderRefundRebate(final List<OrderRebateEntity> sellOrderRebates,
			final List<OrderRefundEntity> sellOrderRefunds) {
		double refundRebate = 0D;
		final List<OrderRebateEntity> afterRebateWay = OrderRebateEntity.filterRebateWay(sellOrderRebates,
				OrderRebateMethodEnum.AFTER.getMethod(), 0);
		final Map<String, OrderRebateEntity> afterRebateMap = OrderRebateEntity.convertRebateToMap(afterRebateWay);
		for (final OrderRefundEntity orderRefund : sellOrderRefunds) {
			final String merchId = orderRefund.getMerchId();
			if (afterRebateMap.containsKey(merchId)) {
				final int refundNum = orderRefund.getRefundNum();
				final OrderRebateEntity orderRebate = afterRebateMap.get(merchId);
				refundRebate += refundNum * orderRebate.getAfterRebateAmount();
			}
		}
		return refundRebate;
	}

	/**
	 * 计算退款商品的商品结算价
	 *
	 * @param orderRefunds 订单退款流水
	 * @param useSettleAmount 使用结算价计算
	 * @param merches 上级订单商品集合
	 * @return
	 */
	private double calculateRefundMerchAmount(final List<OrderRefundEntity> sellOrderRefunds) {
		double totalAmount = 0D;
		if (Check.NuNCollections(sellOrderRefunds)) {
			return totalAmount;
		}
		for (final OrderRefundEntity refund : sellOrderRefunds) {
			totalAmount += refund.getRefundNum() * refund.getRefundPrice();
		}
		return totalAmount;
	}

	/**
	 * 构建退款订单的返利信息
	 * @author DongChunfu
	 *
	 * @param baseDetail 订单分账明细数据模型
	 * @param rebates 订单返利信息
	 * @param baseData 订单分账基础数据
	 */
	private void setRefundRebateInfo(final OrderTransferAccountsVO baseDetail, final OrderTransferAccountsDetailEntity baseData,
			final List<OrderRefundEntity> orderRefunds) {

		final List<OrderRebateEntity> orderRebates = baseData.getOrderRebates();

		final List<OrderRebateEntity> directRebates = OrderRebateEntity.filterRebateWay(orderRebates,
				OrderRebateMethodEnum.AFTER.getMethod(), RebateSettleMethodEnum.IMMEDIATE.getMethod());
		final List<OrderRebateEntity> periodRebates = OrderRebateEntity.filterRebateWay(orderRebates,
				OrderRebateMethodEnum.AFTER.getMethod(), RebateSettleMethodEnum.PERIOD.getMethod());

		//1:立返返利信息
		final OrderRebateVO directRebate = buildRefundRebateVO(directRebates, orderRefunds);
		if (!Check.NuNObject(directRebate)) {
			if (baseData.isMerchantOrder()) {
				baseDetail.getWeshopRebates().add(directRebate);
			} else {
				baseDetail.getOrderRebates().add(directRebate);
			}
		}
		//2:周返返利信息
		final OrderRebateVO periodRebate = buildRefundRebateVO(periodRebates, orderRefunds);
		if (!Check.NuNObject(periodRebate)) {
			if (baseData.isMerchantOrder()) {
				baseDetail.getWeshopRebates().add(periodRebate);
			} else {
				baseDetail.getOrderRebates().add(periodRebate);
			}
		}
	}

	/**
	 * 构建当前退款申请退款返利信息
	 * @author DongChunfu
	 *
	 * @param rebates 统一类型的返利信息
	 * @return 订单返利信息
	 */
	private OrderRebateVO buildRefundRebateVO(final List<OrderRebateEntity> unifyRebates,
			final List<OrderRefundEntity> orderRefunds) {
		if (Check.NuNCollections(unifyRebates)) {
			return null;
		}
		final OrderRebateVO rebate = new OrderRebateVO();
		double rebateAmount = 0D;
		final Map<String, OrderRefundEntity> convertToRefundMap = OrderRefundEntity.convertToMapByMerchId(orderRefunds);

		for (final OrderRebateEntity orderRebate : unifyRebates) {
			final String merchId = orderRebate.getMerchId();
			if (convertToRefundMap.containsKey(merchId)) {
				final OrderRefundEntity orderRefund = convertToRefundMap.get(merchId);
				rebateAmount += orderRefund.getRefundNum() * orderRebate.getAfterRebateAmount();
			}
		}
		rebate.setRebateWay(unifyRebates.get(0).getRebateSettlement());
		// 退款单返利金转换为负数
		rebate.setRebateAmount(refundAmountHandle(rebateAmount));
		return rebate;
	}

	/**
	 * 退款单显示金额为负值，
	 *
	 * @param amount
	 * @return
	 */
	private double refundAmountHandle(final double amount) {
		return 0 - amount;
	}
}
