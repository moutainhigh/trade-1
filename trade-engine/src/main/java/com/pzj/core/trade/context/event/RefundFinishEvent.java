package com.pzj.core.trade.context.event;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.clean.engine.event.RefundApplyCleanEvent;
import com.pzj.core.trade.clean.engine.handler.RefundToCleanHandler;
import com.pzj.core.trade.confirm.event.TransferAccountsEvent;
import com.pzj.core.trade.confirm.model.AccountHandleModel;
import com.pzj.core.trade.context.model.RefundModel;
import com.pzj.core.trade.order.engine.common.PayWayEnum;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.write.RefundApplyWriteMapper;
import com.pzj.core.trade.sms.engine.handle.RefundSMSHandle;
import com.pzj.core.trade.sms.engine.handle.saas.SaasRefundSMSHandle;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.mq.DockOtaMQMSgConverter;
import com.pzj.trade.mq.MQTagsEnum;
import com.pzj.trade.mq.MQUtil;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.model.OrderRefundModel;
import com.pzj.trade.order.write.OrderWriteMapper;

@Component
public class RefundFinishEvent {
	@Autowired
	private RefundApplyWriteMapper refundApplyWriteMapper;
	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private RefundToCleanHandler refundToCleanHandler;
	@Resource(name = "refundSMSHandle")
	private RefundSMSHandle refundSMSHandle;

	@Resource(name = "saasRefundSMSHandle")
	private SaasRefundSMSHandle saasRefundSMSHandle;

	@Autowired
	private RefundApplyCleanEvent refundApplyCleanEvent;
	@Autowired
	private TransferAccountsEvent transferAccountsEvent;
	@Resource(name = "mqUtil")
	private MQUtil mqUtil;

	public void doEvent(final RefundModel paramModel, final OrderEntity saleOrder) {
		final RefundApplyEntity refundApply = refundApplyWriteMapper.getRefundApplyEntityByRefundId(paramModel.getRefundId());
		sendRefundOtaSucess(saleOrder.getOrder_id());
		refundSuccessSendMQ(refundApply, saleOrder.getOrder_id());
		if ("0".equals(saleOrder.getVersion())) {
			refundToCleanHandler.convertClean(paramModel.getSaleOrderId(), paramModel.getRefundId());
			refundApplyCleanEvent.confirmRefund(paramModel.getRefundId());
			refundSMSHandle.sendRefundSMS(paramModel.getRefundId());
		} else {
			if (checkCallTransfer(saleOrder, paramModel.getRefundId())) {
				final AccountHandleModel reqModel = new AccountHandleModel();
				reqModel.setTransactionId(saleOrder.getTransaction_id());
				//reqModel.setCheckedTime(saleOrder.getConfirm_time());
				transferAccountsEvent.transferAccounts(reqModel.getTransactionId());
			}
			saasRefundSMSHandle.sendRefundSMS(paramModel.getRefundId());
		}
	}

	/**
	 * 效验退款完成后是否需要调用分账接口
	 * @param saleOrder
	 * @param refundId
	 * @return
	 */
	private boolean checkCallTransfer(final OrderEntity saleOrder, final String refundId) {
		//如果销售订单对应的支付方式为后付时,无需操作分账
		if (saleOrder.getPay_way() == PayWayEnum.AFTER.getPayWay()) {
			return false;
		}
		//如果订单的状态不是最终状态（已完结，已退款）,无需进行分账操作
		if (!((saleOrder.getOrder_status() == OrderStatusEnum.Finished.getValue())
				|| (saleOrder.getOrder_status() == OrderStatusEnum.Refunded.getValue()))) {
			return false;
		}
		final RefundApplyEntity refundApply = refundApplyWriteMapper.getRefundApplyEntityByRefundId(refundId);
		//如果退款申请时订单的状态不是已支付状态,无需调用分账操作
		if (refundApply.getApplySaleOrderStatus() != OrderStatusEnum.AlreadyPaid.getValue()) {
			return false;
		}
		final OrderRefundModel orderRefundModel = orderWriteMapper.getOrderRefundModel(saleOrder.getOrder_id(), refundId);
		//如果订单中任然存在退款中状态的商品，则无需进行分账操作
		if (orderRefundModel.getRefundingNum() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 向ota系统发送订单退款成功消息
	 *
	 * @param orderId
	 */
	private void sendRefundOtaSucess(final String orderId) {
		final String msg = DockOtaMQMSgConverter.getRefundOrderMsg(orderId, true);
		mqUtil.send(MQTagsEnum.otaRefundMerch.getValue(), msg);
	}

	private void refundSuccessSendMQ(final RefundApplyEntity rae, final String orderId) {
		final Map<String, Object> map = new HashMap<>();
		map.put("success", Boolean.TRUE);
		map.put("message", "退款成功");
		map.put("orderId", orderId);
		map.put("isForce", rae.getIsForce());
		map.put("isPart", rae.getIsParty());
		map.put("refuseParty", 0);
		final String msg = JSONConverter.toJson(map).toString();
		mqUtil.send(MQTagsEnum.refundSuccess.getValue(), msg);
	}
}
