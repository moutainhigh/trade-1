package com.pzj.trade.order.read;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.export.entity.OrderExportExcelEntity;
import com.pzj.trade.merch.entity.MerchListEntity;
import com.pzj.trade.order.entity.AgentOrderEntity;
import com.pzj.trade.order.entity.ConfirmOrderEntity;
import com.pzj.trade.order.entity.OrderBasticEntity;
import com.pzj.trade.order.entity.OrderCountEntity;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.entity.OrderListParameter;
import com.pzj.trade.order.entity.OrderNumEntity;
import com.pzj.trade.order.entity.OrderTransferAccountsDetailEntity;
import com.pzj.trade.order.entity.ReportOrderEntity;
import com.pzj.trade.order.entity.SupplierOrderEntity;
import com.pzj.trade.order.entity.TransferAccountsBaseDataEntity;
import com.pzj.trade.order.entity.TransferAccountsDetailParamEntity;
import com.pzj.trade.order.model.SettleDetailQueryEntity;

public interface OrderReadMapper {

	/**
	 * 根据订单ID查询订单信息.
	 *
	 * @param order_id
	 * @return
	 */
	OrderEntity getOrderById(String order_id);

	/**
	 * 根据订单ID查询订单退款信息.
	 *
	 * @param order_id
	 * @return
	 */
	OrderEntity getOrderByIdForForceRefund(@Param(value = "orderId") String orderId);

	/**
	 * 根据订单ID查询同一个交易单号下的订单信息.
	 *
	 * @param order_id
	 * @return
	 */
	ArrayList<OrderEntity> getTransactionOrderById(String order_id);

	/**
	 * 根据分销商订单ID查询对应供应商订单的订单信息.
	 *
	 * @param order_id
	 * @return
	 */
	List<SupplierOrderEntity> getSupplierOrdersByResellerOrderId(String order_id);

	/**
	 * 根据分销商订单ID查询对应供应商订单的订单信息.
	 *
	 * @param order_id
	 * @return
	 */
	List<SupplierOrderEntity> getSupplierOrdersByTransactionId(String transaction_id, int order_level);

	List<OrderEntity> getOrderListByIds(@Param("orderIds") List<String> orderIds);

	/**
	 * 根据订单ID查询订单信息.用于二次确认
	 *
	 * @param order_id
	 * @return
	 */
	ConfirmOrderEntity getConfirmOrderById(String order_id);

	/**
	 * 通用的根据参数查询订单数量.
	 *
	 * @param param
	 * @return
	 */
	OrderCountEntity getOrderCountByCondition(@Param(value = "param") OrderListParameter param);

	List<MerchListEntity> getOrderByCondition(@Param(value = "param") OrderListParameter param,
			@Param(value = "page_index") int page_index, @Param(value = "page_size") int page_size);

	/**
	 * 查询需要取消的订单：30分钟未付款的订单
	 *
	 * @param transaction_id
	 * @return
	 */
	ArrayList<String> getUnPayOrderIds();

	List<ReportOrderEntity> getReportOrder(@Param(value = "start_time") Date start_time,
			@Param(value = "end_time") Date end_time, @Param(value = "reseller_id") long reseller_id,
			@Param(value = "merch_state") Integer merch_state, @Param(value = "is_root") int is_root);

	/**
	 * 根据订单号查询对应的代下单的订单号.
	 *
	 * @param order_id
	 * @return
	 */
	AgentOrderEntity queryAgentOrderByOrderId(String order_id);

	/**
	 * 查询导出订单.
	 *
	 * @param param
	 * @return
	 */
	ArrayList<OrderExportExcelEntity> getExportOrdersByCondition(@Param(value = "param") OrderListParameter param);

	/**
	 *查询联系人在限购时间内买了多少指定产品（退款的不算）
	 *
	 * @param param
	 * @return
	 */
	int getMerchCountByContactee(@Param(value = "skuIds") List<Long> skuIds, @Param(value = "createTime") Date createTime,
			@Param(value = "contacteeMobile") String contacteeMobile);

	/**
	 * 根据订单交易流水号查询所有的子订单信息.
	 * @param transaction_id
	 */
	List<OrderBasticEntity> queryAllChildOrderByTransactionId(String transaction_id);

	/**
	 * 在交易维度查询满足要求的交易条数
	 * @author DongChunfu
	 *
	 * @param queryParam 查询参数
	 * @return 满足要求的交易条数
	 */
	int queryTransferAccountsDetailByPageParamCount(@Param(value = "queryParam") TransferAccountsDetailParamEntity queryParam);

	/**
	 * 查询分账基础数据
	 * @author DongChunfu
	 *
	 * @param queryParam 查询参数
	 * @return 满足要求的交易ID集合
	 */
	List<TransferAccountsBaseDataEntity> queryTransferAccountsBaseData(
			@Param(value = "queryParam") TransferAccountsDetailParamEntity queryParam);

	/**
	 * 根据订单ID查询订单分账基础信息
	 * @author DongChunfu
	 *
	 * @param orderId 查询参数
	 * @return 分账明细数据模型
	 */
	OrderTransferAccountsDetailEntity queryTransferAccountsDetailByOid(@Param(value = "orderId") String orderId);

	/**
	 * 在交易维度查询满足分页要求的交易ID关联的素有订单分账基础信息
	 * @author DongChunfu
	 *
	 * @param queryParam 查询参数
	 * @return 分账明细数据模型
	 */
	List<OrderTransferAccountsDetailEntity> queryTransferAccountsDetailByBaseDate(
			@Param(value = "baseData") TransferAccountsBaseDataEntity baseData);

	/**
	 * 获取相对的订单
	 *
	 * @param transactionId 交易ID
	 * @param resellerId 分销商ID
	 * @param supplierId 供应商ID
	 * @return 相对订单
	 */
	OrderTransferAccountsDetailEntity getRelativeOrder(@Param(value = "transactionId") String transactionId,
			@Param(value = "childOrderId") String childOrderId, @Param(value = "parentOrderId") String parentOrderId);

	OrderNumEntity getOrderNumInfo(@Param(value = "orderId") String orderId);

	/**
	 * 获取到订单的ID
	 * @param orderId
	 * @param resellerId
	 * @param supplierId
	 * @return
	 */
	String queryTransfferedOrder(@Param("transactionId") String transactionId, @Param("resellerId") long resellerId,
			@Param("supplierId") long supplierId);

	/**
	 * 财务中心,结算明细金额汇总
	 *
	 * @param param
	 * @return 交易ID集合
	 */
	List<String> querySettleDetailAmountTradeByParam(@Param(value = "param") SettleDetailQueryEntity param);

	/**
	 * 财务中心，结算商品明细
	 *
	 * @param param
	 * @return 订单ID集合
	 */
	List<String> querySettleDetailTradeIdsByParam(@Param(value = "param") SettleDetailQueryEntity param);

	/**
	 * 根据财务中心查询参数，获取符合要求的交易ID集合数量
	 *
	 * @param param
	 * @return 订单ID数量
	 */
	int countSettleDetailTradeByParam(@Param(value = "param") SettleDetailQueryEntity param);

}
