package com.pzj.core.trade.confirm.engine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.sku.common.constants.ProductTypeGlobal;
import com.pzj.core.trade.refund.engine.common.MerchStateEnum;
import com.pzj.framework.context.Result;
import com.pzj.trade.confirm.entity.ConfirmCodeEntity;
import com.pzj.trade.confirm.model.ConfirmCodeModel;
import com.pzj.trade.confirm.write.ConfirmCodeWriteMapper;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.OrderReadMapper;

/**
 * 魔方码验证引擎.
 * @author YRJ
 *
 */
@Component("confirmCodeEngine")
public class ConfirmCodeEngine {

	@Autowired
	private ConfirmCodeWriteMapper confirmCodeWriteMapper;

	@Autowired
	private MerchReadMapper merchReadMapper;

	@Autowired
	private OrderReadMapper orderReadMapper;

	/**
	 * 验证魔方码.
	 * @param codeModel
	 */
	public Result<String> verifyMFCode(ConfirmCodeModel codeModel) {
		List<ConfirmCodeEntity> codes = confirmCodeWriteMapper.queryConfirmCodeByMfCode(codeModel.getCode(), 0);
		if (codes == null || codes.isEmpty()) {
			return new Result<String>(41001, "请检查码号是否输入正确");
		}

		long codeId = 0L;
		String order_id = "";
		boolean isExpire = false;
		boolean isUsed = false;
		boolean isMy = true;

		for (ConfirmCodeEntity code : codes) {
			if (code.getSupplier_id() != codeModel.getSupplierId()) {
				isMy = false;
				continue;
			}
			if (code.getCode_state() == 2) {//已过期.
				isExpire = true;
				continue;
			}
			if (code.getCode_state() == 1) {
				isUsed = true;
				continue;
			}

			codeId = code.getCode_id();
			order_id = code.getTransaction_id();
			break;
		}

		if (codeId > 0) {
			Result<String> result = judgeOrder(order_id);
			return result;
		}
		if (isUsed) {
			return new Result<String>(51001, "该魔方码已经使用");
		}
		if (isExpire || !isMy) {
			return new Result<String>(51002, "该魔方码不适用于本供应商或已过期");
		}
		return new Result<String>(51003, "该魔方码不存在, 请检查魔方码是否正确");
	}

	private Result<String> judgeOrder(String order_id) {
		List<MerchEntity> merchs = merchReadMapper.getMerchByOrderId(order_id);
		boolean isWaitConfirm = false;
		boolean isRefunding = false;
		boolean isAllRefunded = true;

		for (MerchEntity merch : merchs) {
			if (merch.getMerch_state() == MerchStateEnum.WAIT_CONFIRM.getState()) {
				isWaitConfirm = true;
				break;
			}
			if (merch.getIs_refunding() == 1) {
				isRefunding = true;
				isAllRefunded = false;
				continue;
			}
			if (merch.getMerch_state() != MerchStateEnum.REFUNDED.getState()) {
				isAllRefunded = false;
			}
			long currentTime = System.currentTimeMillis();
			if (currentTime > merch.getExpire_time().getTime()) {
				return new Result<String>(51002, "该魔方码不适用于本供应商或已过期");
			}
		}
		if (isWaitConfirm) {
			return new Result<String>(51004, "该订单为待确认状态,不可消费");
		}
		if (isRefunding) {
			return new Result<String>(51005, "该订单为待退款状态,不可消费");
		}
		if (isAllRefunded) {
			return new Result<String>(51006, "该订单为已经全部退款,不可消费");
		}

		//特产
		if (merchs.get(0).getMerch_type() == ProductTypeGlobal.NATIVE_PRODUCT) {
			return new Result<String>(51007, "特产类产品请去订单中心发货核销");
		}
		//对接产品
		OrderEntity order = orderReadMapper.getOrderById(order_id);
		if (order.getIs_dock() == 1) {
			return new Result<String>(51008, "请前往第三方系统验证该码");
		}
		if (order.getOrder_status() == OrderStatusEnum.Cancelled.getValue()) {
			return new Result<String>(51009, "该订单已取消,不可消费");
		}
		return new Result<String>(order_id);
	}
}
