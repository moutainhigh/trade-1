package com.pzj.trade.finance.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.financeCenter.request.SettleGatherReqModel;
import com.pzj.trade.order.entity.TransferAccountsBaseDataEntity;

public interface FinanceReadMapper {

	/**
	 * 
	 * @param queryParam
	 * @param isOnline 是否线上交易
	 * @return
	 */
	List<TransferAccountsBaseDataEntity> queryTransferAccountsBySaaSUser(
			@Param(value = "param") SettleGatherReqModel queryParam, @Param(value = "isOnline") boolean isOnline);

	/**
	 * 
	 * @param transactionId 交易ID
	 * @return
	 */
	List<TransferAccountsBaseDataEntity> queryRefundIdByTransactionId(@Param(value = "transactionId") String transactionId);

	/**
	 * 查询结算对象条数,用户分页
	 * @param 
	 * @return
	 */
	int querySettlePartyCount(@Param(value = "req") SettleGatherReqModel reqModel);

	/**
	 * 获取当前页需要展示的记录ID
	 * @param 
	 * @return
	 */
	List<Long> queryCurrentPageSettlePartyIds(@Param(value = "req") SettleGatherReqModel reqModel,
			@Param(value = "start") int start, @Param(value = "end") int end,@Param(value = "pageEnable") boolean pageEnable);

}
