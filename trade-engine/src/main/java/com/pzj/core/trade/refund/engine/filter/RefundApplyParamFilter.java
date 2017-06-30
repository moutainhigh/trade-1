package com.pzj.core.trade.refund.engine.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.exception.RefundArgsException;
import com.pzj.core.trade.refund.engine.exception.RefundMerchNotFoundException;
import com.pzj.core.trade.refund.entity.RefundMerchRequiredEntity;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.vo.RefundMerchandiseVO;
import com.pzj.trade.refund.model.RefundApplyReqModel;

@Component
public class RefundApplyParamFilter {
	@Autowired
	private MerchReadMapper merchReadMapper;

	public void dofilter(final RefundApplyReqModel reqModel, final OrderEntity order) {
		vldRefundOrderStatus(reqModel, order);
		initRefundParam(reqModel);
	}

	/**
	 * 效验退款操作对应的订单状态是否可退
	 */
	private void vldRefundOrderStatus(final RefundApplyReqModel reqModel, final OrderEntity order) {
		int orderStatus = order.getOrder_status();
		//如果是强制退款进行效验
		if (reqModel.getRefundType() == RefundApplyTypeEnum.FORCE.getType().intValue()) {
			//商品状态为 待付款，已取消，已退款状态的订单,无需发起强制退款
			if (orderStatus == OrderStatusEnum.Unpaid.getValue() || orderStatus == OrderStatusEnum.Refunded.getValue()
					|| orderStatus == OrderStatusEnum.Cancelled.getValue()) {
				throw new RefundArgsException(RefundErrorCode.ORDER_NOT_FOUND_ERROR_CODE, "订单[" + order.getOrder_id()
						+ "]的订单状态是:" + OrderStatusEnum.getOrderStatus(orderStatus).getMsg() + ",无需退款");
			}
		} else {
			if (orderStatus != OrderStatusEnum.AlreadyPaid.getValue()) {
				throw new RefundArgsException(RefundErrorCode.ORDER_NOT_FOUND_ERROR_CODE, "订单[" + order.getOrder_id()
						+ "]的订单状态是:" + OrderStatusEnum.getOrderStatus(orderStatus).getMsg() + "无法退款");
			}
		}
	}

	/**
	 * 如果前端没有输入任何退款商品信息
	 * @param reqModel
	 */
	private void initRefundParam(final RefundApplyReqModel reqModel) {
		if (!Check.NuNCollections(reqModel.getRefundMerches())) {
			return;
		}
		final List<RefundMerchRequiredEntity> merchs = merchReadMapper.getSellMerchesOfSellOrder(reqModel.getOrderId());
		if (Check.NuNCollections(merchs)) {
			throw new RefundMerchNotFoundException(10302, "订单[" + reqModel.getOrderId() + "], 未找到对应的商品信息.");
		}
		for (final RefundMerchRequiredEntity merch : merchs) {
			int canRefundNum = reqModel.getRefundType() == RefundApplyTypeEnum.FORCE.getType().intValue() ? merch.getTotalNum()
					- merch.getRefundNum() : merch.getTotalNum() - merch.getRefundNum() - merch.getCheckNum();
			if (canRefundNum > 0) {
				final RefundMerchandiseVO merchVo = new RefundMerchandiseVO();
				merchVo.setRefundNum(canRefundNum);
				merchVo.setMerchId(merch.getMerchId());
				reqModel.addRefundMerch(merchVo);
			}
		}
	}
}
