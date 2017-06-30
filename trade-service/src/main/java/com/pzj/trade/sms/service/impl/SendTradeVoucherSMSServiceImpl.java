package com.pzj.trade.sms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.sms.engine.handle.VoucherSMSHandle;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.sms.model.SMSVoucherReqModel;
import com.pzj.trade.sms.service.ISendTradeVoucherSMSService;

@Component("sendTradeVoucherSMSService")
public class SendTradeVoucherSMSServiceImpl implements ISendTradeVoucherSMSService {

	private final static Logger logger = LoggerFactory.getLogger(SendTradeVoucherSMSServiceImpl.class);

	@Autowired
	private VoucherSMSHandle voucherSMSHandle;

	@Override
	public Result<Boolean> sendSMS(SMSVoucherReqModel reqModel) {
		logger.info("call SendTradeVoucherSMSServiceImpl.sendSMS,reqParam:{}", JSONConverter.toJson(reqModel));
		try {
			voucherSMSHandle.setSMS(reqModel.getResellerOrderId());
			return new Result<Boolean>();
		} catch (Exception e) {
			logger.error("发送凭证短信服务异常,请求参数:" + JSONConverter.toJson(reqModel) + ",异常信息:", e);
			return new Result<Boolean>(10613, "发送短信服务异常");
		}
	}
}
