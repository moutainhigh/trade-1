package com.pzj.core.trade.refund.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.payment.common.PayFlowStateEnum;
import com.pzj.trade.payment.entity.CashPostalEntity;
import com.pzj.trade.payment.service.ThirdPartyRefundCabackService;
import com.pzj.trade.payment.write.CashPostalWriteMapper;
import com.pzj.trade.refund.common.RefundExceptionCodeEnum;
import com.pzj.trade.refund.model.ThirdPayRefundRspModel;
import com.pzj.trade.refund.model.ThirdPayWithdrawRspModel;
import com.pzj.trade.withdraw.engine.RefundWithdrawEngine;

@Service("thirdPartyRefundCabackService")
public class ThirdPartyRefundCabackServiceImpl implements ThirdPartyRefundCabackService {

	private final static Logger logger = LoggerFactory.getLogger(ThirdPartyRefundCabackServiceImpl.class);

	@Resource(name = "paymentRefundCallbackReqParamValidator")
	private ObjectConverter<ThirdPayRefundRspModel, Void, Result<Boolean>> paymentRefundCallbackReqParamValidator;

	@Autowired
	private CashPostalWriteMapper cashPostalWriteMapper;
	@Autowired
	private RefundWithdrawEngine refundWithdrawEngine;

	@Override
	public Result<Boolean> thirdWithdrawCallback(final ThirdPayWithdrawRspModel withdrawModel) {
		logger.info("thirdPay call thirdPartyRefundService.thirdWithdrawCallback reqParma:{}",
				JSONConverter.toJson(withdrawModel));
		try {
			if (Check.NuNObject(withdrawModel)) {
				return new Result<Boolean>(RefundExceptionCodeEnum.vldParamException.getKey(), "回调对象为空");
			}
			if (withdrawModel.getRspResult() != true) {
				//提现被拒绝，目前不做处理
				return new Result<Boolean>();
			}
			if (Check.NuNObject(withdrawModel.getWithdrawId())) {
				return new Result<Boolean>(RefundExceptionCodeEnum.vldParamException.getKey(), "提现标识为空");
			}
			final CashPostalEntity cashPostal = cashPostalWriteMapper
					.getCashPostalEntityByIdForUpdate(withdrawModel.getWithdrawId());
			if (Check.NuNObject(cashPostal)) {
				return new Result<Boolean>(RefundExceptionCodeEnum.vldParamException.getKey(), "无法找到对应的提现记录");
			}
			if (cashPostal.getPostal_status() != PayFlowStateEnum.Paying.getKey()) {
				logger.info("thirdPay重复调用交易系统,withdrawId:{},PostalStatus:{}", withdrawModel.getWithdrawId(),
						cashPostal.getPostal_status());
				return new Result<Boolean>();
			}
			refundWithdrawEngine.withdrawFinishCallback(cashPostal);
			//提现被拒绝，目前不做处理
			return new Result<Boolean>();
		} catch (final Exception e) {
			logger.error("call trade withdraw is Error,postalId:{},content:{}", withdrawModel.getWithdrawId(), e);
			return new Result<Boolean>(RefundExceptionCodeEnum.refundServiceExcetion.getKey(), "退款冻结状态异常");
		}
	}
}
