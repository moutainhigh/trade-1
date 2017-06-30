package com.pzj.core.trade.integration.engine.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.integration.engine.common.TransferredErrorCode;
import com.pzj.core.trade.integration.engine.exception.TransferredParamException;
import com.pzj.core.trade.integration.engine.model.AfterRebateTransferredMsgModel;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.model.TransferredMerchStrategyModel;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.OrderReadMapper;

@Component("transferredMerchStrategyConverter")
public class TransferredMerchStrategyConverter {

	@Autowired
	private MerchReadMapper merchReadMapper;
	@Autowired
	private OrderReadMapper orderReadMapper;

	public List<TransferredMerchStrategyModel> convert(final AfterRebateTransferredMsgModel msgModel) {
		paramVal(msgModel);
		final List<String> merchIds = merchReadMapper.queryTransfferedMerchIds(msgModel.getTransactionId(), msgModel.getSkuId());
		final String orderId = orderReadMapper.queryTransfferedOrder(msgModel.getTransactionId(), msgModel.getResellerId(), msgModel.getSupplierId());
		paramQueryResultVal(merchIds, orderId);
		List<TransferredMerchStrategyModel> result = new ArrayList<TransferredMerchStrategyModel>();
		for (String merchId : merchIds) {
			TransferredMerchStrategyModel model = new TransferredMerchStrategyModel();
			model.setOrderId(orderId);
			model.setMerchId(merchId);
			model.setTransfeeredTime(msgModel.getTransferredTime());
			result.add(model);
		}
		return result;
	}

	/**
	 * 请求参数效验
	 * @param msgModel
	 */
	private void paramVal(AfterRebateTransferredMsgModel msgModel) {
		if (Check.NuNObject(msgModel)) {
			throw new TransferredParamException(TransferredErrorCode.TRANSFERRED_PARAM_ERROR, "通知内容为空");
		}
		if (Check.NuNString(msgModel.getTransactionId()) || Check.NuNObject(msgModel.getResellerId()) || Check.NuNObject(msgModel.getSupplierId())
				|| Check.NuNObject(msgModel.getSkuId())) {
			throw new TransferredParamException(TransferredErrorCode.TRANSFERRED_PARAM_ERROR, "通知内容中必传的参数为空");
		}
		if (Check.NuNObject(msgModel.getTransferredTime()) || msgModel.getInAccountTime() <= 0) {
			throw new TransferredParamException(TransferredErrorCode.TRANSFERRED_PARAM_ERROR, "通知内容中有关的到账时间信息异常");
		}
	}

	/**
	 * 效验根据清算参数获取的数据内容
	 * @param merchIds
	 * @param orderId
	 */
	private void paramQueryResultVal(final List<String> merchIds, final String orderId) {
		if (Check.NuNCollections(merchIds)) {
			throw new TransferredParamException(TransferredErrorCode.TRANSFERRED_PARAM_QUERY_ERROR, "根据交易ID,与产品ID获取订单商品信息异常");
		}
		if (Check.NuNString(orderId)) {
			throw new TransferredParamException(TransferredErrorCode.TRANSFERRED_PARAM_QUERY_ERROR, "根据交易ID,分销商ID获取订单信息异常");
		}
	}
}
