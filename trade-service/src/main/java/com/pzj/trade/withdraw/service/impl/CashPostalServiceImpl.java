package com.pzj.trade.withdraw.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pzj.core.customer.profile.ProfileBasicInfo;
import com.pzj.core.customer.profile.ProfileService;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.exception.ServiceException;
import com.pzj.framework.toolkit.Check;
import com.pzj.settlement.base.entity.AccountBalance;
import com.pzj.trade.common.ErrorCode;
import com.pzj.trade.common.UserType;
import com.pzj.trade.withdraw.engine.NoughtRefundWithdrawEngine;
import com.pzj.trade.withdraw.engine.RefundWithdrawEngine;
import com.pzj.trade.withdraw.exception.WithdrawException;
import com.pzj.trade.withdraw.model.CashPostalModel;
import com.pzj.trade.withdraw.service.CashPostalService;

@Service("cashPostalService")
public class CashPostalServiceImpl implements CashPostalService {
	private final static Logger logger = LoggerFactory.getLogger(CashPostalServiceImpl.class);

	@Resource(name = "noughtRefundWithdrawEngine")
	private NoughtRefundWithdrawEngine noughtRefundWithdrawEngine;

	@Resource(name = "refundWithdrawEngine")
	private RefundWithdrawEngine refundWithdrawEngine;

	@Resource(name = "cashPostalConverter")
	private ObjectConverter<CashPostalModel, ServiceContext, Result<Boolean>> cashPostalConverter;

	@Resource(name = "accountBalanceConverter")
	private ObjectConverter<Long, Double, Result<AccountBalance>> accountBalanceConverter;
	
	@Resource(name = "profileService")
	private ProfileService profileService;

	@Override
	public Result<Boolean> cashPostal(final CashPostalModel cashPostal, ServiceContext serviceContext) {

		Result<Boolean> valid = cashPostalConverter.convert(cashPostal, null);
		if (!valid.isOk() && Check.NuNObject(valid.getData())) {
			logger.warn("withdraw fail, args: {}, result: {}.", cashPostal, JSONConverter.toJson(valid));
			return valid;
		}

		logger.info("withdraw, args: {}, isRefund: {},context:{}.", cashPostal, valid.getData(), serviceContext);

		Result<ProfileBasicInfo> profileBasicInfo=profileService.queryActivateProfileBasicInfoById(cashPostal.getAccountId());
		
		//用戶不存在
		if(Check.NuNObject(profileBasicInfo)){
			throw new WithdrawException(ErrorCode.USER_NOT_EXIST.getCode(), "提现失败, "+ErrorCode.USER_NOT_EXIST.getDesc()+".");
		}
		
		try {
			//saas用户类型或者拼单不无效时,使用线下提现流程通知清结算处理
			if(UserType.SASS.getType()==profileBasicInfo.getData().getUserType()||!valid.getData()){
				noughtRefundWithdrawEngine.doWithdraw(cashPostal);//TODO 需要加入返回值, 用于记录日志.
				
			}else{
				//线上退款提现流程
				Result<AccountBalance> result = accountBalanceConverter.convert(cashPostal.getAccountId(),
						cashPostal.getCashPostalMoney());
				if (!result.isOk()) {
					return new Result<Boolean>(result.getErrorCode(), result.getErrorMsg());
				}
				refundWithdrawEngine.doWithdraw(cashPostal);
			}
		} catch (Throwable e) {
			logger.error("withdraw fail, args: " + cashPostal, e);
			if (e instanceof ServiceException) {
				throw (ServiceException) e;
			}
			throw new WithdrawException(10532, "提现失败, 请稍后重试.");
		}

		Result<Boolean> result = new Result<Boolean>(Boolean.TRUE);
		logger.info("withdraw success, args: {}, result: {}.", cashPostal, result);
		return result;
	}
}
