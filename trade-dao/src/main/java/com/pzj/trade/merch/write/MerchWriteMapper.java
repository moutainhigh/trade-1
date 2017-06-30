package com.pzj.trade.merch.write;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.refund.entity.RefundMerchRequiredEntity;
import com.pzj.trade.merch.entity.ConfirmMerchEntity;
import com.pzj.trade.merch.entity.MerchEffecTimeEntity;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.MerchNumEntity;
import com.pzj.trade.merch.model.MerchRefundModel;
import com.pzj.trade.order.vo.RefundMerchandiseVO;

public interface MerchWriteMapper {

	/**
	 * 更新订单商品状态.
	 *
	 * @param transaction_id
	 *            交易ID
	 * @param merch_state
	 *            商品目标状态.
	 */
	void updateMerchStatusByTransactionId(@Param(value = "transaction_id") String transaction_id,
			@Param(value = "merch_state") int merch_state);

	/**
	 * 通过交易ID更新订单商品状态为待消费（已退款的除外）
	 *
	 * @param transaction_id
	 *            交易ID
	 * @param merch_state
	 *            商品目标状态.
	 */
	void updateMerchStatusConsumeByTransactionId(@Param(value = "transaction_id") String transaction_id,
			@Param(value = "merch_state") int merch_state);

	/**
	 * 更新订单商品状态.
	 *
	 * @param transaction_id
	 *            交易ID
	 * @param merch_state
	 *            商品目标状态.
	 */
	void updateMerchStatusByVoucherId(@Param(value = "voucher_id") long voucher_id,
			@Param(value = "merch_state") int merch_state);

	/**
	 * 更新订单商品状态和已检数量.
	 *
	 * @param transaction_id
	 *            交易ID
	 * @param merch_state
	 *            商品目标状态.
	 */
	void updateMerchStatusAndCheckNumByTransactionId(@Param(value = "transaction_id") String transaction_id,
			@Param(value = "merch_state") int merch_state);

	/**
	 * 更新订单商品状态.
	 *
	 * @param order_id
	 *            待更新的订单ID.
	 * @param merch_state
	 *            商品目标状态.
	 */
	void updateMerchStatusByOrderId(@Param(value = "order_id") String order_id, @Param(value = "merch_state") int merch_state);

	void updateMerchToFinishiRefund(@Param(value = "merchIds") List<String> merchIds);

	/**
	 * 以消费的商品退款操作,回滚商品信息
	 *
	 * @param transactionId
	 */
	void updateMerchNumForConsumeRollback(@Param(value = "transactionId") String transactionId);

	/**
	 * 保存订单商品.
	 *
	 * @param merchs
	 */
	void insertMerchEntity(@Param(value = "merchs") List<MerchEntity> merchs);

	/**
	 * 保存订单商品.
	 *
	 * @param merchs
	 */
	void insertMultiMerchEntity(@Param(value = "merchs") List<MerchEntity> merchs);

	/**
	 * 根据merch_ids更新商品状态.
	 *
	 * @param merch_ids
	 * @param merch_state
	 *            商品目标状态.
	 */
	void updateMerchStatusByMerchIds(@Param(value = "merch_ids") List<String> merch_ids,
			@Param(value = "merch_state") int merch_state);

	/**
	 * 当前退款所关联的商品设置为待退款
	 *
	 * @param merchandise_ids
	 */
	void updateMerchToBeRefund(@Param(value = "merch_ids") List<String> merch_ids, @Param("merch_state") int merch_state);

	/**
	 * 更新订单商品中与退款相关的属性信息(退已消费商品check_num 减)
	 *
	 * @param
	 */
	void updateMerchOfRefund(@Param("refundModel") MerchRefundModel refundModel);

	/**
	 * 更新订单商品中与退款相关的属性信息(退已消费商品check_num 减)
	 *
	 * @param
	 */
	void updateMerchesOfRefund(@Param("refundModels") List<MerchRefundModel> refundModel);

	void updateMercheStatusOfRefundFinish(String orderId);

	/**
	 * 根据商品ID获取订单商品信息
	 *
	 * @param merchId
	 * @return
	 */
	MerchEntity getMerchByMerchId(String merchId);

	/**
	 * 更新订单商品状态，商品从待消费变为已消费
	 *
	 * @param params
	 * @return
	 */
	Integer updateOrderMerchStatus(Map<String, Object> params);

	/**
	 * 更新商品状态及数量, 数量和状态由业务系统指定.
	 *
	 * @param merch
	 */
	void updateMerchStatusAndNumByMerchId(@Param(value = "merchs") List<MerchNumEntity> merchs);

	/**
	 * 根据交易唯一transactionId查询商品并按产品ID做分组聚合.
	 *
	 * @param transactionId
	 * @return
	 */
	List<MerchEffecTimeEntity> queryMerchByTransactionAlsoGroup(@Param(value = "transactionId") String transactionId);

	/**
	 * 获取核销的商品信息
	 *
	 * @param transactionId
	 * @param voucherId
	 * @return
	 */
	List<ConfirmMerchEntity> queryConfirmMerch(@Param("transactionId") String transactionId,
			@Param("voucherId") long voucherId);

	/**
	 * 获取订单商品信息
	 *
	 * @param order_id
	 * @return
	 */
	List<MerchEntity> getMerchByOrderId(String order_id);

	/**
	 * 根据顶层商品ID获取订单商品信息
	 *
	 * @param rootMerchId
	 * @return
	 */
	List<MerchEntity> getMerchsByRootMerchId(String rootMerchId);

	/**
	 * 通过voucher获取对应的商品信息
	 *
	 * @param voucherId
	 * @return
	 */
	List<MerchEntity> getMerchByVoucherId(@Param("voucherId") long voucherId);

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
	 * 获取退款中商品信息
	 *
	 * @param order_id
	 * @param refundMerch
	 * @return
	 */
	List<MerchEntity> getRefundingMerchOfTrade(@Param("transactionId") String transactionId);

	/**
	 * 获取供应商商品信息
	 *
	 * @param merchIds
	 * @return
	 */
	List<RefundMerchRequiredEntity> getMerchsByMerchIds(@Param("merchIds") List<String> merchIds);

	/**
	 * 更新商品清算状态为已清算(1)
	 *
	 * @param merchId
	 *            商品ID
	 */
	void updateMerchAsCleaned(String merchId);

	/**
	 * 根据交易ID更新商品为已清算
	 *
	 * @param transaction_id
	 */
	void updateCleanedByTransactionId(String transaction_id);

	/**
	 * 通过销售订单号查询所有的商品及数量信息.
	 *
	 * @param orderId
	 * @return
	 */
	List<RefundMerchRequiredEntity> queryMerchNumEntityByRootOrderId(String orderId);

	/**
	 * 根据销售商品ID获取采购商品信息
	 *
	 * @author DongChunfu
	 *
	 * @param sellMerchId
	 * @return 采购订单商品
	 */
	MerchEntity getPurchaseMerchBySellMerchId(@Param("sellMerchId") String sellMerchId);

	/**
	 * 批量获取商品
	 *
	 * @param merchIds
	 *            商品IDS
	 * @return
	 */
	List<MerchEntity> getMerchesByIds(@Param("merchIds") List<String> merchIds);
}
