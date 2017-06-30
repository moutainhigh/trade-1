/**
 * 
 */
package com.pzj.core.trade.finance.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pzj.framework.toolkit.Check;
import com.pzj.trade.financeCenter.response.SettleMerchRepModel;
import com.pzj.trade.order.vo.OrderPayVO;
import com.pzj.trade.order.vo.OrderTransferAccountsVO;

/**
 **@author dufangxing
 **@version 2017年5月31日上午9:49:50
 */
public class SettleDetailTool {

	
	public Map<String,List<OrderTransferAccountsVO>> getClassification(final List<OrderTransferAccountsVO> transferAccouts){
		Map<String,List<OrderTransferAccountsVO>> resMap=new HashMap<>();
		String transferId=null;
		List<OrderTransferAccountsVO> vos=null;
		for(final OrderTransferAccountsVO transferAccount : transferAccouts){
			transferId=transferAccount.getTransactionId();
			if(resMap.containsKey(transferId)){
				vos=resMap.get(transferId);
			}else{
				vos=new ArrayList<OrderTransferAccountsVO>();
			}
			vos.add(transferAccount);
			resMap.put(transferId, vos);
		}
		return resMap;
	}
	public SettleMerchRepModel getTempSettleMerchRepModel(final List<OrderTransferAccountsVO> transferAccouts,
			long userRole,final int payWay,final long userId){
		SettleMerchRepModel res=new SettleMerchRepModel();
		//取出供应商的ID
		long relativeUserId = 0L;
		for (final OrderTransferAccountsVO transferAccount : transferAccouts) {
			final Long payeeParty = transferAccount.getPayeeParty();
			if (SaasRoleEnum.RESELLER.getRole() == userRole) {
				if (transferAccount.getCurrentUserId().longValue() == userId && transferAccount.getOrderType()==1) {//取sass订单的收款
					relativeUserId = payeeParty;
				}
			}

			if (SaasRoleEnum.SUPPLIER.getRole() == userRole) {
				if (!Check.NuNObject(payeeParty) && payeeParty.longValue() == userId&&transferAccount.getOrderType()==1) {
					relativeUserId = transferAccount.getCurrentUserId();
				}
			}
		}

		double income = 0D;
		double expense = 0D;
		double profit = 0D;
		double refundAmount = 0D;
		List<OrderPayVO> orderPay = null;
		if (SaasRoleEnum.RESELLER.getRole() == userRole) {
			for (final OrderTransferAccountsVO transferAccount : transferAccouts) {
				if (transferAccount.getCurrentUserId().longValue() == relativeUserId&&transferAccount.getOrderType()==2) {
					refundAmount+=transferAccount.getOrderPayAmount();
				}
				if (transferAccount.getCurrentUserId().longValue() == userId) {
					if(transferAccount.getOrderType()==1){
						orderPay = transferAccount.getOrderPay();
					}
					income += transferAccount.getRebateTotalAmout();
				}

				if (SettleWayEnum.ONLINE.getWay() == payWay) {
					if(transferAccount.getOrderType()==1){
						if (transferAccount.getCurrentUserId().longValue() == userId) {
							expense += transferAccount.getOrderPayAmount();
						}
					}
					if(transferAccount.getOrderType()==2){
						if (transferAccount.getCurrentUserId().longValue() == relativeUserId) {
							expense += transferAccount.getOrderPayAmount();
						}
					}

				} else {
					if (transferAccount.getCurrentUserId().longValue() == relativeUserId) {
						expense += changeType(transferAccount.getMerchClearingAmount());
					}
				}
			}
		}

		if (SaasRoleEnum.SUPPLIER.getRole() == userRole) {
			for (final OrderTransferAccountsVO transferAccount : transferAccouts) {

				if (transferAccount.getCurrentUserId().longValue() == userId&&transferAccount.getOrderType()==2) {
					refundAmount+=transferAccount.getOrderPayAmount();
				}
				if(SettleWayEnum.ONLINE.getWay()==payWay){
					if (transferAccount.getCurrentUserId().longValue() == userId) {
						//
						// 如果是取线上分销商的数据
						// 增加利润列计算
						// 利润=商品利润分账表中本级SaaS用户的商品结算金额，包含退款单
						profit += changeType(transferAccount.getMerchClearingAmount());
					}
					if(transferAccount.getOrderType()==1
							&&transferAccount.getCurrentUserId().longValue() == relativeUserId){
						// 已收货款=SaaS用户作为供应商的订单实际支付金额，包含退款单
						income += transferAccount.getOrderPayAmount();
					}
					
					if(transferAccount.getOrderType()==2
							&&transferAccount.getCurrentUserId().longValue() == userId) {
						// 已收货款=SaaS用户作为供应商的订单实际支付金额，包含退款单
						income += transferAccount.getOrderPayAmount();
					}
					if (transferAccount.getCurrentUserId().longValue() == relativeUserId) {
						if(transferAccount.getOrderType()==1){
							orderPay = transferAccount.getOrderPay();
						}
						expense += transferAccount.getRebateTotalAmout();
					}
				}else{
					if (transferAccount.getCurrentUserId().longValue() == userId) {
						income += changeType(transferAccount.getMerchClearingAmount());
					}
					
					if (transferAccount.getCurrentUserId().longValue() == relativeUserId) {
						if(transferAccount.getOrderType()==1){
							orderPay = transferAccount.getOrderPay();
						}
						expense += transferAccount.getRebateTotalAmout();
					}
				}
			}
		}
		res.setRelativeUserId(relativeUserId);
		res.setIncome(income);
		res.setExpense(expense);
		res.setOrderPay(orderPay);
		res.setProfit(profit);
		res.setRefundAmount(refundAmount);
		/**
		 * @TODO 
		 * 退款金额
		 */

		return res;
	}
	private double changeType(Double source){
		return Check.NuNObject(source)?0d:source.doubleValue();
	}
}
