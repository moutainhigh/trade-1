package com.pzj.trade.order.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.refund.entity.RefundMerchRequiredEntity;
import com.pzj.trade.financeCenter.response.SettleMerchRepModel;
import com.pzj.trade.merch.entity.ConfirmBatchEntity;
import com.pzj.trade.merch.entity.MerchBasicEntity;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.MerchNumEntity;
import com.pzj.trade.merch.entity.MerchStateEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.order.entity.RebateMerchEntity;
import com.pzj.trade.order.vo.RefundMerchandiseVO;

public interface MerchReadMapper {

	/**
	 * 获取订单商品信息
	 *
	 * @param order_id
	 * @return
	 */
	List<MerchEntity> getMerchByOrderId(String order_id);

	/**
	 * 获取订单商品信息以及相关政策信息
	 *
	 * @param order_id
	 * @return
	 */
	List<MerchEntity> getMerchWithStrategyByOrderId(String order_id);

	/**
	 * 获取TransactionId商品信息以及相关政策信息
	 *
	 * @param order_id
	 * @return
	 */
	List<MerchEntity> getMerchWithStrategyByTransactionId(@Param("order_id") String order_id,
			@Param("transaction_id") String transaction_id);

	/**
	 * 根据产品ID获取订单商品信息
	 *
	 * @param productId
	 * @return
	 */
	List<MerchEntity> getMerchsByProductId(String productId);

	/**
	 * 根据父产品ID获取订单商品信息
	 *
	 * @param parentProductId
	 * @return
	 */
	List<MerchEntity> getMerchsByParentProductId(String parentProductId);

	/**
	 * 根据voucherID获取订单商品信息
	 *
	 * @param voucherId
	 * @return
	 */
	List<MerchEntity> getMerchByVoucherId(long voucherId);

	/**
	 * 根据商品ID获取订单商品信息
	 *
	 * @param merchId
	 * @return
	 */
	MerchEntity getMerchByMerchId(String merchId);

	/**
	 * 根据顶层商品ID获取订单商品信息
	 *
	 * @param rootMerchId
	 * @return
	 */
	List<MerchEntity> getMerchsByRootMerchId(String rootMerchId);

	/**
	 * 根据主订单ID查询所有的子商品ID.
	 *
	 * @param transactionId
	 * @return
	 */
	List<MerchEntity> getChildMerchsByTransactionId(String transactionId);

	/**
	 * 获取退款商品信息
	 *
	 * @param order_id
	 * @param refundMerch
	 * @return
	 */
	List<MerchEntity> getMerchRefundOfOrder(@Param("transactionId") String transactionId,
			@Param("refundMerch") List<RefundMerchandiseVO> refundMerch);

	/**
	 * 获取订单的退款信息
	 *
	 * @param orderId
	 *            订单ID
	 * @param orderId
	 *            商品ID
	 * @param orderId
	 *            订单类型
	 * @return
	 */
	List<RefundFlowEntity> getRefundFlows(@Param("order_id") String orderId, @Param("merch_id") String merch_id,
			@Param("refund_type") int refund_type);

	/**
	 * 获取订单的退款信息
	 *
	 * @param orderId
	 *商品分账明细表查询
	 * @return
	 */
	List<RefundFlowEntity> getOrderRefundMerchByMerchId(@Param("merch_id") String merch_id);

	/**
	 * 通过商品，所关联的全部商品信息（包括子订单商品）
	 *
	 * @param merids
	 *            退款商品ID
	 * @param transactionid
	 *            交易ID
	 * @return
	 */
	List<MerchEntity> getAllMerchOfTransByMerIds(@Param("merids") List<String> merids,
			@Param("transactionid") String transactionid);

	/**
	 * 根据商品Id查询子商品的政策ID
	 *
	 * @param order_id
	 * @return
	 */
	List<MerchEntity> getMerchStrategysByMerchId(@Param("merchList") List<String> merchList);

	/**
	 * 根据订单ID查询所有的对应商品.
	 *
	 * @param order_id
	 * @return
	 */
	List<MerchBasicEntity> queryMerchByOrderId(String order_id);

	/**
	 * 根据事务ID获取对应的全部商品ID.
	 *
	 * @param transactionId
	 * @return
	 */
	List<MerchStateEntity> getMerchByTransactionId(String transactionId);

	/**
	 * 查询当前商品是待确认并且商品已经逾期
	 *
	 * @return
	 */
	List<Long> queryMerchVoucherForOverdueOfAck();

	/**
	 * 获取销售订单的销售商品信息
	 *
	 * @author DongChunfu
	 *
	 * @param orderId
	 *            销售订单ID
	 * @return 销售商品集
	 */
	List<RefundMerchRequiredEntity> getSellMerchesOfSellOrder(@Param("sellOrderId") String sellOrderId);

	/**
	 * 根据transaction_ids 获取订单商品信息
	 *
	 * @param order_id
	 * @return
	 */
	List<MerchEntity> getMerchByTransactionIds(@Param("transaction_ids") List<String> transaction_ids);

	/**
	 * 根据商品ID获取商品的名称
	 *
	 * @author DongChunfu
	 * @param merchId 商品ID
	 * @return 商品名称
	 */
	String getMerchName(@Param("merchId") String merchId);

	/**
	 * 批量核销需要的相关信息(最小维度为凭证,商品信息为附属信息)
	 *
	 * @author DongChunfu
	 * @param sellOrderIds 销售订单ID
	 * @return 批量核销需要的参数
	 */
	List<ConfirmBatchEntity> getConfirmBatchRequireParam(@Param(value = "sellOrderIds") List<String> sellOrderIds);

	/**
	 * 根据交易ID获取所有的商品
	 * @author DongChunfu
	 *
	 * @param orderId
	 * @return
	 */
	List<MerchNumEntity> getMerchNumEntityByTid(@Param(value = "transactionId") String transactionId);

	/**
	 * 获取所有逾期未核销的商品信息
	 * @author DongChunfu
	 *
	 * @return 逾期商品信息
	 */
	List<MerchEntity> getAllOverdueMerches();

	/**
	 * 通过skuId,transactionId获取订单商品ID集合
	 * @param transactionId
	 * @param skuId
	 * @return
	 */
	List<String> queryTransfferedMerchIds(@Param("transactionId") String transactionId, @Param("skuId") long skuId);

	/**
	 * 查询结算商品
	 * @param tradeIds
	 * @return
	 */
	List<SettleMerchRepModel> querySettleMerchesByOrderIds(@Param(value = "orderIds") final List<String> orderIds);

	/**
	 * @author DongChunfu
	 * @date 2017年5月24日09:51:30
	 * 分账使用
	 *
	 * @param transactionId 交易ID
	 * @param resellerId 分销商ID
	 * @return 后返商品的返利数据
	 */
	List<RebateMerchEntity> getMerchRebateData(@Param(value = "tradeId") String tradeId,
			@Param(value = "resellerId") Long resellerId);
}
