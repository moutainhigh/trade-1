package com.pzj.core.trade.context.exe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.clean.engine.handler.ConsumerToCleanHandler;
import com.pzj.core.trade.context.common.VoucherSpeedTypeEnum;
import com.pzj.core.trade.context.exe.base.TradeExecutor;
import com.pzj.core.trade.context.model.ConsumerModel;
import com.pzj.core.trade.sms.engine.handle.MerchConsumerSMSHandle;
import com.pzj.framework.converter.JSONConverter;

@Component("voucherSpendedExecutor")
public class VoucherSpendedExecutor implements TradeExecutor<ConsumerModel> {

	private final static Logger logger = LoggerFactory.getLogger(VoucherSpendedExecutor.class);

	@Autowired
	private ConsumerToCleanHandler consumerToCleanHandler;

	@Autowired
	private MerchConsumerSMSHandle merchConsumerSMSHandle;

	@Override
	public void execute(ConsumerModel paramModel) {
		logger.info("call VoucherSpendedExecutor.execute sendParam:{}", JSONConverter.toJson(paramModel));
		try {
			if (paramModel.getConsumerSceneType() == VoucherSpeedTypeEnum.SpeedSuccess.getKey()) {
				spendSuccess(paramModel);
			}
		} catch (Exception e) {
			logger.info("call VoucherSpendedExecutor.execute is error, sendParam:" + JSONConverter.toJson(paramModel)
					+ ",errorContent:", e);
		}
	}

	private void spendSuccess(ConsumerModel paramModel) {
		consumerToCleanHandler.convertClean(paramModel.getSaleOrderId(), paramModel.getVoucherId());
		merchConsumerSMSHandle.sendConsumerSMS(paramModel.getVoucherId());
	}
}
