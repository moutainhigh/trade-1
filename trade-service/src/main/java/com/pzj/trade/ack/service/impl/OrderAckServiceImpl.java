package com.pzj.trade.ack.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.ack.engine.DockAckEngine;
import com.pzj.core.trade.ack.engine.OrderAckEngine;
import com.pzj.core.trade.ack.engine.exception.AckException;
import com.pzj.core.trade.context.common.OrderSceneTypeEnum;
import com.pzj.core.trade.context.exe.base.ExecuteBaseModel;
import com.pzj.core.trade.context.manger.TradePublisherFactory;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.ack.model.AckModel;
import com.pzj.trade.ack.service.OrderAckService;
import com.pzj.trade.order.common.OrderConfirmEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;

/**
 * 订单二次确认接口实现.
 * @author YRJ
 *
 */
@Service(value = "orderAckService")
public class OrderAckServiceImpl implements OrderAckService {

	private final static Logger logger = LoggerFactory.getLogger(OrderAckServiceImpl.class);

	@Resource(name = "orderAckEngine")
	private OrderAckEngine orderAckEngine;

	@Resource(name = "dockAckEngine")
	private DockAckEngine dockAckEngine;

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private TradePublisherFactory tradePublisherFactory;

	@Override
	public Result<Boolean> ack(AckModel ackModel, ServiceContext context) {
		logger.info("ackModel: {}, context: {}.", ackModel, context);
		if (ackModel == null || Check.NuNObject(ackModel.getOrderId())) {
			return new Result<Boolean>(41001, "二次确认参数有误");
		}
		OrderEntity order = orderWriteMapper.getOrderEntityNotLock(ackModel.getOrderId());
		if (Check.NuNObject(order)) {
			return new Result<Boolean>(41002, "无法获取订单信息");
		}
		if (order.getNeed_confirm() == OrderConfirmEnum.UNCONFIRM.getValue()) {
			logger.error("订单的“二次确认”属性异常：confirm:{},orderId:{}", order.getNeed_confirm(), order.getOrder_id());
			return new Result<Boolean>(41003, "该订单无需进行二次确认");
		}
		if (order.getNeed_confirm() == OrderConfirmEnum.CONFIRMED.getValue()) {
			logger.error("订单的“二次确认”属性异常：confirm:{},orderId:{}", order.getNeed_confirm(), order.getOrder_id());
			return new Result<Boolean>(41003, "该订单已经进行了二次确认");
		}
		try {
			Result<Boolean> result = null;
			if (Check.NuNStrStrict(ackModel.getThirdCode())) {
				result = orderAckEngine.ack(ackModel);
			} else {
				result = dockAckEngine.ack(ackModel);
			}
			if (result.isOk()) {
				ExecuteBaseModel paramModel = new ExecuteBaseModel();
				paramModel.setSaleOrderId(order.getP_order_id());
				int orderSceneType = 0;
				if (ackModel.getAcknowledge()) {
					orderSceneType = OrderSceneTypeEnum.AckSucess.getKey();
				} else {
					orderSceneType = OrderSceneTypeEnum.ackFailure.getKey();
				}
				paramModel.setOrderSceneType(orderSceneType);
				tradePublisherFactory.publish(paramModel);
			}
			return result;
		} catch (Throwable e) {
			logger.error("ackModel: " + JSONConverter.toJson(ackModel) + ", context:" + JSONConverter.toJson(ackModel)
					+ ",errorContent:", e);
			if (e instanceof AckException) {
				return new Result<Boolean>(10603, "二次确认服务异常");
			}
			return new Result<Boolean>(10604, "确认失败, 请稍后重试");
		}
	}
}
