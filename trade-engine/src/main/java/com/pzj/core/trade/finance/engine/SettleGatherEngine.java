/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.finance.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.confirm.event.QueryTransferAccountsDetailEvent;
import com.pzj.framework.context.Result;
import com.pzj.framework.entity.QueryResult;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.finance.read.FinanceReadMapper;
import com.pzj.trade.financeCenter.request.SettleGatherReqModel;
import com.pzj.trade.financeCenter.response.SettleGatherAmountRepModel;
import com.pzj.trade.financeCenter.response.SettleGatherRepModel;
import com.pzj.trade.financeCenter.response.SettlePartyRepModel;
import com.pzj.trade.order.entity.TransferAccountsBaseDataEntity;
import com.pzj.trade.order.vo.OrderTransferAccountsVO;

/**
 * 结算汇总引擎
 *
 * @author DongChunfu
 * @version $Id: SettleGatherEngine.java, v 0.1 2017年5月15日 上午11:57:48 DongChunfu Exp $
 */
@Component(value = "settleGatherEngine")
public class SettleGatherEngine {
	@Autowired
	private FinanceReadMapper financeReadMapper;

	@Resource(name = "queryTransferAccountsDetailEvent")
	private QueryTransferAccountsDetailEvent queryTransferAccountsDetailEvent;

	public Result<SettleGatherRepModel> query(final SettleGatherReqModel reqModel) {

		final SettleGatherRepModel model = new SettleGatherRepModel();
		final int userRole = reqModel.getUserRole();
		final List<Long> relativeIds = reqModel.getRelativeUserIds();

		//统计结算汇总金额
		reqModel.setRelativeUserIds(null);
		final SettleGatherAmountRepModel amountRepModel = getSettleGatherAmountRepModel(reqModel);
		model.setGatherModel(amountRepModel);

		//统计结算方详细信息
		reqModel.setUserRole(userRole);
		reqModel.setRelativeUserIds(relativeIds);
		final QueryResult<SettlePartyRepModel> settleParties = queryGatherDetail(reqModel);
		model.setSettleParties(settleParties);

		return new Result<SettleGatherRepModel>(model);
	}

	/**
	 * @author chj
	 * @param reqModel
	 * @param model
	 */
	private QueryResult<SettlePartyRepModel> queryGatherDetail(final SettleGatherReqModel reqModel) {
		final QueryResult<SettlePartyRepModel> result = new QueryResult<SettlePartyRepModel>(reqModel.getCurrentPage(),
				reqModel.getPageSize());

		final int count = financeReadMapper.querySettlePartyCount(reqModel);
		result.setTotal(count);
		if (count == 0) {
			return result;
		}

		final Map<Long, SettlePartyRepModel> settles = new HashMap<Long, SettlePartyRepModel>();

		List<Long> ids = reqModel.getRelativeUserIds();
		if (Check.NuNCollections(reqModel.getRelativeUserIds())
				|| reqModel.getRelativeUserIds().size() > reqModel.getPageSize()) {//取当页要显示的记录
			ids = financeReadMapper.queryCurrentPageSettlePartyIds(reqModel, reqModel.getPageIndex(), reqModel.getPageSize(),
					true);
		}

		for (final Long id : ids) {
			final SettlePartyRepModel model = new SettlePartyRepModel();
			model.setRelativeUserId(id);
			settles.put(id, model);
		}
		final long currentUserId = reqModel.getUserId();
		for (final Long id : ids) {
			reqModel.setRelativeUserIds(Arrays.asList(id));

			final List<OrderTransferAccountsVO> onlineSettles = getTransferAccounts(reqModel, true);
			final List<OrderTransferAccountsVO> offlineSettles = getTransferAccounts(reqModel, false);

			if (Check.NuNCollections(onlineSettles) && Check.NuNCollections(offlineSettles)) {
				settles.remove(id);
				continue;
			}
			final SettlePartyRepModel settle = settles.get(id);

			if (reqModel.getUserRole() == 2) {//我的供应商
				final double[] onlineAmount = buildSupplierTotalOnline(onlineSettles, currentUserId);
				settle.setOnlineReceipt(onlineAmount[0]);
				settle.setOnlinePay(onlineAmount[1]);

				final double[] offlineAmount = buildSupplierTotalOffline(offlineSettles, currentUserId);
				settle.setOfflineReceipt(offlineAmount[0]);
				settle.setOfflinePay(offlineAmount[1]);
			} else if (reqModel.getUserRole() == 1) {//我的分销商
				final double[] onlineAmount = buildResellerTotalOnline(onlineSettles, currentUserId);
				settle.setOnlineReceipt(onlineAmount[0]);
				settle.setOnlinePay(onlineAmount[1]);

				final double[] offlineAmount = buildResellerTotalOffline(offlineSettles, currentUserId);
				settle.setOfflineReceipt(offlineAmount[0]);
				settle.setOfflinePay(offlineAmount[1]);
			}
		}

		final List<SettlePartyRepModel> models = new ArrayList<SettlePartyRepModel>();
		models.addAll(settles.values());
		result.setRecords(models);
		return result;

	}

	public SettleGatherAmountRepModel getSettleGatherAmountRepModel(final SettleGatherReqModel queryParam) {

		double supplierIncome = 0.0;//供应商收入
		double supplierExpend = 0.0;//供应商支出

		double resellerIncome = 0.0;//分销商收入
		double resellerExpend = 0.0;//分销商支出

		//我与供应商线下交易：我作为分销商

		final double[] d = getSupplierOffline(queryParam);
		supplierIncome += d[0];
		supplierExpend += d[1];

		//我与供应商线上交易
		final double[] d1 = getSupplierOnline(queryParam);
		supplierIncome += d1[0];
		supplierExpend += d1[1];

		//我与分销商线上交易：我作为供应商
		final double[] d2 = getResellerOnline(queryParam);
		resellerIncome += d2[0];
		resellerExpend += d2[1];
		//我与分销商线下交易：我作为供应商
		final double[] d3 = getResellerOffline(queryParam);
		resellerIncome += d3[0];
		resellerExpend += d3[1];

		final SettleGatherAmountRepModel result = new SettleGatherAmountRepModel();
		result.setResellerRolePayAmount(resellerExpend);
		result.setResellerRoleReceiptAmount(resellerIncome);

		result.setSupplierRolePayAmount(supplierExpend);
		result.setSupplierRoleReceiptAmount(supplierIncome);

		return result;
	}

	/**
	 *
	 * @param param
	 * @return
	 */
	private List<OrderTransferAccountsVO> getTransferAccounts(final SettleGatherReqModel param, final boolean isOnline) {
		final List<TransferAccountsBaseDataEntity> baseDatas = financeReadMapper.queryTransferAccountsBySaaSUser(param,
				isOnline);
		return queryTransferAccountsDetailEvent.transferAccounts(baseDatas);
	}

	/**
	 *我与供应商线下交易：即我作为分销商
	 * @param param
	 * @return
	 */
	private double[] getSupplierOffline(final SettleGatherReqModel param) {
		final SettleGatherReqModel temp = param;
		temp.setUserRole(2);//
		return buildSupplierTotalOffline(getTransferAccounts(param, false), param.getUserId());
	}

	/**
	 * 与供应商线下统计交易的收入，支出
	 * 支出=本级订单支付金额
	 * 收入=本级的返利金额
	 * @param orderTransferAccountsVOs 商品利润分账明细
	 * @param userId 当前用户ID
	 * @return
	 */
	private double[] buildSupplierTotalOffline(final List<OrderTransferAccountsVO> orderTransferAccountsVOs,
			final Long userId) {
		double expend = 0;
		double income = 0;

		if (!Check.NuNCollections(orderTransferAccountsVOs)) {
			final Map<String, List<OrderTransferAccountsVO>> tempMap = new HashMap<>();
			List<OrderTransferAccountsVO> tempList = new ArrayList<>();
			for (final OrderTransferAccountsVO entity : orderTransferAccountsVOs) {
				if (tempMap.containsKey(entity.getTransactionId())) {
					tempMap.get(entity.getTransactionId()).add(entity);
				} else {
					tempList = new ArrayList<>();
					tempList.add(entity);
					tempMap.put(entity.getTransactionId(), tempList);
				}
			}
			for (final String key : tempMap.keySet()) {
				final List<OrderTransferAccountsVO> tempOrderTransferAccountsVOs = tempMap.get(key);
				final double[] d = buildSupplierOneTotalOffine(tempOrderTransferAccountsVOs, userId);
				income += d[0];
				expend += d[1];
			}
		}
		return new double[] { income, expend };
	}

	private double[] buildSupplierOneTotalOffine(final List<OrderTransferAccountsVO> orderTransferAccountsVOs,
			final Long userId) {
		double expend = 0;
		double income = 0;

		if (!Check.NuNCollections(orderTransferAccountsVOs)) {
			long relativeUserId = 0L;
			for (final OrderTransferAccountsVO entiy : orderTransferAccountsVOs) {
				if (entiy.getCurrentUserId().equals(userId) && entiy.getOrderType() == 1 && null != entiy.getPayeeParty()) {
					relativeUserId = entiy.getPayeeParty();
				}
			}

			for (final OrderTransferAccountsVO entity : orderTransferAccountsVOs) {
				//本级用户返利
				if (userId.equals(entity.getCurrentUserId())) {
					income += changType(entity.getRebateTotalAmout());
				}
				//上级用户的结算金额
				if (relativeUserId == entity.getCurrentUserId()) {
					expend += changType(entity.getMerchClearingAmount());
				}
			}
		}
		return new double[] { income, expend };
	}

	/**
	 *我与供应商线上交易：即我作为分销商
	 * @param param
	 * @return
	 */
	private double[] getSupplierOnline(final SettleGatherReqModel param) {
		final SettleGatherReqModel temp = param;
		temp.setUserRole(2);//
		return buildSupplierTotalOnline(getTransferAccounts(param, true), param.getUserId());
	}

	/**
	 * 与供应商线上统计交易的收入，支出
	 * 支出=本级别用户支付金额
	 * 收入=本级的返利金额
	 * @param orderTransferAccountsVOs 商品利润分账明细
	 * @param userId 用户ID
	 * @return
	 */
	private double[] buildSupplierTotalOnline(final List<OrderTransferAccountsVO> orderTransferAccountsVOs, final Long userId) {
		double expend = 0;
		double income = 0;
		if (!Check.NuNCollections(orderTransferAccountsVOs)) {
			for (final OrderTransferAccountsVO account : orderTransferAccountsVOs) {
				final long payeeParty = account.getPayeeParty() == null ? 0l : account.getPayeeParty().longValue();
				if (payeeParty == userId && account.getOrderType() == 2) {
					//本级订单支付金额
					expend += account.getOrderPayAmount();
				}
				if (userId.equals(account.getCurrentUserId())) {
					if (account.getOrderType() == 1) {
						//本级订单支付金额
						expend += account.getOrderPayAmount();
					}
					//本级返利
					income += changType(account.getRebateTotalAmout());
				}

			}

		}
		return new double[] { income, expend };
	}

	/**
	 *我与分销商线上交易：即我作为供应商
	 * @param param
	 * @return
	 */
	private double[] getResellerOnline(final SettleGatherReqModel param) {
		final SettleGatherReqModel temp = param;
		temp.setUserRole(1);//
		return buildResellerTotalOnline(getTransferAccounts(param, true), param.getUserId());
	}

	/**
	 * 与分销商线上统计交易的收入，支出
	 * 支出=下级的返利金额
	 * 收入=本用户的结算金额
	 * @param orderTransferAccountsVOs 商品利润分账明细
	 * @param userId 用户ID
	 * @return
	 */
	private double[] buildResellerTotalOnline(final List<OrderTransferAccountsVO> orderTransferAccountsVOs, final Long userId) {
		return buildResellerTotal(orderTransferAccountsVOs, userId, true);
	}

	private double[] buildResellerTotal(final List<OrderTransferAccountsVO> orderTransferAccountsVOs, final Long userId,
			final boolean isOnline) {
		double expend = 0;
		double income = 0;
		if (!Check.NuNCollections(orderTransferAccountsVOs)) {
			final Map<String, List<OrderTransferAccountsVO>> tempMap = new HashMap<>();
			List<OrderTransferAccountsVO> tempList = new ArrayList<>();
			for (final OrderTransferAccountsVO entity : orderTransferAccountsVOs) {
				if (tempMap.containsKey(entity.getTransactionId())) {
					tempMap.get(entity.getTransactionId()).add(entity);
				} else {
					tempList = new ArrayList<>();
					tempList.add(entity);
					tempMap.put(entity.getTransactionId(), tempList);
				}
			}
			for (final String key : tempMap.keySet()) {
				final List<OrderTransferAccountsVO> tempOrderTransferAccountsVOs = tempMap.get(key);
				final double[] d = buildResellerOneTotal(tempOrderTransferAccountsVOs, userId, isOnline);
				income += d[0];
				expend += d[1];
			}
		}
		return new double[] { income, expend };
	}

	private double[] buildResellerOneTotal(final List<OrderTransferAccountsVO> orderTransferAccountsVOs, final Long userId,
			final boolean isOnline) {
		double expend = 0;
		double income = 0;
		if (!Check.NuNCollections(orderTransferAccountsVOs)) {
			long relativeUserId = 0L;
			for (final OrderTransferAccountsVO entity : orderTransferAccountsVOs) {
				final Long payeeParty = entity.getPayeeParty();
				if (null != payeeParty && entity.getPayeeParty().longValue() == userId && entity.getOrderType() == 1) {
					relativeUserId = entity.getCurrentUserId();
				}
			}

			for (final OrderTransferAccountsVO account : orderTransferAccountsVOs) {
				final long currentUserId = account.getCurrentUserId();
				if (isOnline) {
					if (account.getOrderType() == 1 && relativeUserId == currentUserId) {
						// 已收货款=SaaS用户作为供应商的订单实际支付金额，包含退款单
						income += account.getOrderPayAmount();
					}
					if (account.getOrderType() == 2 && currentUserId == userId) {
						// 已收货款=SaaS用户作为供应商的订单实际支付金额，包含退款单
						income += account.getOrderPayAmount();
					}
					if (relativeUserId == currentUserId) {
						expend += account.getRebateTotalAmout();
					}
				} else {
					if (userId.equals(account.getCurrentUserId())) {
						//本级用户结算金额
						income += changType(account.getMerchClearingAmount());
					}
					//下级用户的返利金额
					if (relativeUserId == currentUserId) {
						expend += changType(account.getRebateTotalAmout());
					}
				}
			}
		}
		return new double[] { income, expend };
	}

	/**
	 *我与分销商线下交易：即我作为供应商
	 * @param param
	 * @return
	 */
	private double[] getResellerOffline(final SettleGatherReqModel param) {
		final SettleGatherReqModel temp = param;
		temp.setUserRole(1);//
		return buildResellerTotalOffline(getTransferAccounts(param, false), param.getUserId());
	}

	/**
	 * 我与分销商线下交易的收入，支出
	 * 支出=下级的返利金额
	 * 收入=本级用户的结算金额
	 * @param orderTransferAccountsVOs 商品利润分账明细
	 * @param userId 用户ID
	 * @return
	 */
	private double[] buildResellerTotalOffline(final List<OrderTransferAccountsVO> orderTransferAccountsVOs,
			final Long userId) {
		return buildResellerTotal(orderTransferAccountsVOs, userId, false);
	}

	private double changType(final Double d) {
		return Check.NuNObject(d) ? 0 : d.doubleValue();
	}
}
