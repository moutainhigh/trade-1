package com.pzj.core.trade.refund.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.context.manger.TradePublisherFactory;
import com.pzj.core.trade.context.model.RefundModel;
import com.pzj.core.trade.refund.engine.common.RefundUserTypeEnum;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.write.MerchRefundFlowWriteMapper;

/**
 * 退款申请/成功/拒绝 预清算记录处理引擎
 * 
 * @author DongChunfu
 * @date 2016年12月27日
 */
@Component(value = "tradeCleanEngine")
public class TradeCleanEngine {

	@Autowired
	private TradePublisherFactory tradePublisherFactory;

	@Autowired
	private MerchRefundFlowWriteMapper merchRefundFlowWriteMapper;

//	public void clean(String sellOrderId, final String refundId, final int cleanType) {
//
//		if (Check.NuNString(sellOrderId)) {
//			sellOrderId = merchRefundFlowWriteMapper.queryOrderIdByRefundId(refundId,
//					RefundUserTypeEnum.resellerRefund.getKey());
//		}
//
//		final RefundModel refundModel = new RefundModel();
//		refundModel.setSaleOrderId(sellOrderId);
//		refundModel.setRefundId(refundId);
//		refundModel.setRefundSceneType(cleanType);
//		tradePublisherFactory.publish(refundModel);
//	}

}
