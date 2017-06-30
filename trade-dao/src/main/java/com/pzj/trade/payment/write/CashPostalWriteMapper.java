package com.pzj.trade.payment.write;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.payment.entity.CashPostalEntity;
import com.pzj.trade.payment.entity.TakenOrderEntity;
import com.pzj.trade.payment.entity.ThirdTradingRecordEntiry;
import com.pzj.trade.payment.entity.WithdrawOrderFlow;

public interface CashPostalWriteMapper {

	/**
	 * 获取人员的提现信息
	 * @param account_id
	 * @return
	 */
	CashPostalEntity getCashPostalEntityForWithdraw(long account_id);

	CashPostalEntity getCashPostalEntityByIdForUpdate(long postal_id);

	/**
	 * 获取帐号的提现信息
	 * @param account_id
	 * @param postal_status
	 * @return
	 */
	List<CashPostalEntity> getCashPostallistByAccountId(@Param("account_id") long account_id, @Param("postal_status") int postal_status);

	/**
	 * 获取订单的第三方支付信息
	 * @param order_id      订单ID    
	 * @param account_id    帐号ID
	 * @return
	 */
	ThirdTradingRecordEntiry getThirdRecordOfDrawing(@Param("order_id") String order_id, @Param("account_id") long account_id);

	/**
	 * 获取订单提现信息（锁定）
	 * @param order_id
	 * @param account_id
	 * @return
	 */
	TakenOrderEntity getTakenOrderEntityForUpdate(@Param("order_id") String order_id, @Param("account_id") long account_id);

	/**
	 * 获取订单提现信息（不锁定）
	 * @param order_id
	 * @param account_id
	 * @return
	 */
	TakenOrderEntity getTakenOrderEntityNotlock(@Param("order_id") String order_id, @Param("account_id") long account_id);

	/**
	 * @param postalId
	 * @param flowStatus
	 * @return
	 */
	List<WithdrawOrderFlow> getWithdrawOrderFlowList(@Param("withdrawId") long withdrawId, @Param("flowStatus") int flowStatus);

	/**
	 * 获取帐号下的可提现总金额
	 * @param      account_id           帐号ID
	 * @param      postal_status        提现状态
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Map searchTakenMoneyForAccount(@Param("account_id") long account_id, @Param("taken_status") int taken_status);

	/**
	 * 获取订单的提现情况
	 * @param account_id
	 * @param taken_status
	 * @param last_taken_id
	 * @param list_size
	 * @return
	 */
	List<TakenOrderEntity> getTakenOrderListForAccount(@Param("account_id") long account_id, @Param("taken_status") int taken_status,
			@Param("last_taken_id") long last_taken_id, @Param("list_size") int list_size);

	/**
	 * 通过订单ID获取订单集合
	 * @param orderidlist
	 * @return
	 */
	List<OrderEntity> getOrderListByOrderIds(List<String> orderidlist);

	/**
	 * 新增订单的提现信息
	 * @param entity
	 */
	void insertTakenEntity(TakenOrderEntity entity);

	/**
	 * 新增提现记录
	 * @param entity
	 */
	void insertCashPostalEntity(CashPostalEntity entity);

	/**
	 * 查处提现对应订单的相关信息
	 * @param flowlist
	 */
	void insertWithdrawOrderFlow(@Param("flowlist") List<WithdrawOrderFlow> flowlist);

	/**
	 * 修改提现订单信息状态
	 * @param flow
	 */
	void updateWithDrawOrderFlow(WithdrawOrderFlow flow);

	/**
	 * 更新实际提现的信息
	 * @param postal_id
	 * @param money
	 * @param postal_status
	 */
	void updateCashPostalMoney(@Param("postal_id") long postal_id, @Param("change_money") double change_money, @Param("postal_status") int postal_status);

	void updateTakenStatusByOrderId(@Param("order_id") String order_id, @Param("taken_status") int taken_status);

	/**
	 * 更新提现状态
	 * @param order_id
	 * @param taken_status
	 */
	void updateTakenStatus(@Param("order_id") String order_id, @Param("taken_money") double taken_moeny, @Param("taken_status") int taken_status);

}
