package com.pzj.core.trade.clean.engine;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.clean.engine.converter.CleaningStateEnum;
import com.pzj.core.trade.clean.engine.exception.CleaningException;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.settlement.balance.service.SettlementCall;
import com.pzj.trade.clearing.model.CleaningModel;
import com.pzj.trade.merch.entity.MerchCleanRelationEntity;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.MerchStateEntity;
import com.pzj.trade.merch.write.MerchCleanRelationWriteMapper;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.mq.DockOtaMQMSgConverter;
import com.pzj.trade.mq.MQTagsEnum;
import com.pzj.trade.mq.MQUtil;
import com.pzj.trade.order.common.MerchStateEnum;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.withdraw.engine.TakenOrderEngine;

/**
 * 清算执行引擎.
 * @author YRJ
 *
 */
@Component(value = "cleaningEngine")
public class CleaningEngine {

	private final static Logger logger = LoggerFactory.getLogger(CleaningEngine.class);

	@Autowired
	private MerchCleanRelationWriteMapper merchCleanRelationWriteMapper;

	@Autowired
	private MerchReadMapper merchReadMapper;

	@Autowired
	private SettlementCall settlementCall;

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private MerchWriteMapper merchWriteMapper;

	@Autowired
	private TakenOrderEngine takenOrderEngine;

	@Autowired
	private MQUtil mqUtil;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	public void cleaning(final CleaningModel cleaningModel) {
		final MerchCleanRelationEntity cleanEntity = getMerchCleanRelationEntity(cleaningModel.getOrderId(),
				cleaningModel.getMerchId());
		long effectTime = cleanEntity.getEffec_time();
		final long newTime = System.currentTimeMillis();
		if (effectTime > newTime) {
			effectTime = newTime;
		}
		//如果清算记录对应的是为支付的订单,那么不通知清洁算系统
		if (cleanEntity.getClean_type() != 3) {
			logger.info("清算. 发送采购结算单: " + JSONConverter.toJson(cleanEntity));
			final Result<Boolean> result = settlementCall.confirmMerch(cleanEntity.getOrder_id(), cleanEntity.getMerch_id(),
					effectTime);
			logger.info("清算. 采购结算单: {}, 发送结果: {}.", JSONConverter.toJson(cleanEntity), JSONConverter.toJson(result));
			if (!result.isOk()) {
				logger.error("清算失败, 调用清结算发生异常. result: " + JSONConverter.toJson(result));
				throw new CleaningException("清算失败, 调用清结算发生异常");
			}
		}

		// 商品清算关系表标记为已清算
		merchCleanRelationWriteMapper.updateCleanRelationStateById(cleanEntity.getClean_id(),
				CleaningStateEnum.CLEANED.getState());

		final MerchEntity merchEntity = merchReadMapper.getMerchByMerchId(cleanEntity.getMerch_id());//只是为了获取transaction id
		final List<String> merch_ids = new ArrayList<String>();
		merch_ids.add(merchEntity.getMerch_id());
		final boolean ischangeRootMerch = judgeChangeRootMerch(merchEntity.getRoot_merch_id(), merchEntity.getMerch_id());
		if (ischangeRootMerch) {
			merch_ids.add(merchEntity.getRoot_merch_id());
		}
		//商品的状态是已消费，更新商品状态为已完结
		if (merchEntity.getMerch_state() == MerchStateEnum.CONSUMED.getState()) {
			merchWriteMapper.updateMerchStatusByMerchIds(merch_ids, MerchStateEnum.FINISHED.getState());
		}

		// 商品表中标记商品、父商品为已清算
		merchWriteMapper.updateMerchAsCleaned(cleanEntity.getMerch_id());
		if (ischangeRootMerch && !merchEntity.getRoot_merch_id().equals(cleanEntity.getMerch_id())) {
			merchWriteMapper.updateMerchAsCleaned(merchEntity.getRoot_merch_id());
		}
		//商品清算成功后发送ota对接的消息
		setConsumerOtaMsg(merchEntity);
		final boolean send = sendSaleSettlementFlow(merchEntity.getTransaction_id(), cleanEntity.getMerch_id());
		logger.info("清算. 采购结算单: {},销售结算单：{} 是否发送销售结算单: {} 结算类型{}.", JSONConverter.toJson(cleanEntity),
				merchEntity.getTransaction_id(), send, cleanEntity.getClean_type());
		//强制退款,是需要生成为负的采购结算单
		if (send || cleanEntity.getClean_type() == 2) {
			//如果清算记录对应的是为支付的订单,那么不通知清洁算系统
			if (cleanEntity.getClean_type() != 3) {
				final Result<Boolean> result = settlementCall.confirmOrder(merchEntity.getTransaction_id(), effectTime);
				logger.info("清算. 销售结算单: {}, 发送结果: {}.", merchEntity.getTransaction_id(), JSONConverter.toJson(result));
				if (!result.isOk()) {
					throw new CleaningException("生成销售结算单信息异常");
				}
				//订单完结后生成订单对应的提现信息
				takenOrderEngine.insertDrawing(merchEntity.getTransaction_id());
			}
			final OrderEntity order = orderWriteMapper.getOrderListByPorderId(merchEntity.getTransaction_id());
			//for (final OrderEntity order : orderlist) {
			//如果订单状态是已支付，更新订单状态为已完结
			if (order.getOrder_status() == OrderStatusEnum.AlreadyPaid.getValue()) {
				orderWriteMapper.updateOrderStatusByOrderId(order.getTransaction_id(), OrderStatusEnum.Finished.getValue());
			}
			//}
		}
	}

	/**
	 *判断是否修改父merch的状态
	 * @param root_merch_id
	 * @return
	 */
	private boolean judgeChangeRootMerch(final String root_merch_id, final String merch_id) {
		final List<MerchEntity> merchs = merchReadMapper.getMerchsByRootMerchId(root_merch_id);
		for (final MerchEntity merch : merchs) {
			if (root_merch_id.equals(merch.getMerch_id()) || merch_id.equals(merch.getMerch_id())) {
				continue;
			}
			if (merch.getMerch_state() != MerchStateEnum.FINISHED.getState()
					&& merch.getMerch_state() != MerchStateEnum.REFUNDED.getState()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否发送销售结算单.
	 * @param merchs
	 * @return
	 */
	private boolean sendSaleSettlementFlow(final String transactionId, final String merchId) {
		//判断订单状态，如果订单是已退款状态则不生成销售结算单
		final OrderEntity order = orderWriteMapper.getOrderEntityNotLock(transactionId);
		if (order.getOrder_status() == OrderStatusEnum.Refunded.getValue()
				&& order.getTotal_amount() == order.getRefund_amount()) {
			return false;
		}
		final List<MerchStateEntity> merchs = merchReadMapper.getMerchByTransactionId(transactionId);
		for (final MerchStateEntity merch : merchs) {
			if (merchId.equals(merch.getMerch_id()) || merch.getMerch_id().equals(merch.getRoot_merch_id())) {
				continue;
			}
			if (merch.getIs_cleaned() == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据订单及商品ID获取对应的商品清算记录信息.
	 * @param orderId
	 * @param merchId
	 * @return
	 */
	MerchCleanRelationEntity getMerchCleanRelationEntity(final String orderId, final String merchId) {
		final MerchCleanRelationEntity cleanEntity = merchCleanRelationWriteMapper
				.queryCleanRelationByOrderIdAndMerchId(orderId, merchId, CleaningStateEnum.CLEANABLE.getState());
		if (Check.NuNObject(cleanEntity)) {
			throw new CleaningException("暂时不支持该商品的清算.");
		}
		return cleanEntity;
	}

	/**
	 * 商品清算之后,向ota系统发送商品核销消息
	 * @param merchEntity
	 */
	private void setConsumerOtaMsg(final MerchEntity merchEntity) {
		final String msg = DockOtaMQMSgConverter.getConsumerMerchMsg(merchEntity.getTransaction_id(),
				merchEntity.getCheck_num());
		mqUtil.send(MQTagsEnum.otaConsumerMerch.getValue(), msg);
	}
}
