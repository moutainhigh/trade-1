package com.pzj.core.trade.integration.engine;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.integration.engine.common.TransferredErrorCode;
import com.pzj.core.trade.integration.engine.converter.TransferredMerchStrategyConverter;
import com.pzj.core.trade.integration.engine.exception.TransferredParamException;
import com.pzj.core.trade.integration.engine.model.AfterRebateTransferredMsgModel;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.model.TransferredMerchStrategyModel;
import com.pzj.trade.order.write.OrderStrategyWriteMapper;

/**
 * 后返到账业务处理引擎
 * @author kangzl
 *
 */
@Component("afterRebateTransferredEngine")
public class AfterRebateTransferredEngine {

	@Autowired
	private TransferredMerchStrategyConverter transferredMerchStrategyConverter;

	@Autowired
	private OrderStrategyWriteMapper orderStrategyWriteMapper;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED)
	public void doEngine(final String mqContent) {

		List<AfterRebateTransferredMsgModel> models = JSONConverter.toList(mqContent, AfterRebateTransferredMsgModel.class);
		if (Check.NuNCollections(models)) {
			throw new TransferredParamException(TransferredErrorCode.TRANSFERRED_PARAM_ERROR, "输入的消息内容不符合协议规则");
		}
		List<TransferredMerchStrategyModel> totalModels = new ArrayList<TransferredMerchStrategyModel>();
		for (AfterRebateTransferredMsgModel model : models) {
			List<TransferredMerchStrategyModel> resultModels = transferredMerchStrategyConverter.convert(model);
			totalModels.addAll(resultModels);
		}
		orderStrategyWriteMapper.updateStrategyToAccountTransffered(totalModels);
	}
}
