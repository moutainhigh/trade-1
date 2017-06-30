package com.pzj.trade.payment.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.exception.ServiceException;
import com.pzj.trade.refund.model.ThirdPayWithdrawRspModel;

/**
 * @api {dubbo} com.pzj.trade.payment.service.ThirdPartyRefundCabackService 三方支付回调服务
 * @apiGroup 三方支付回调
 * @apiName 三方支付回调服务
 * @apiDescription<p>三方支付回调服务</p>
 * @author kangzl
 */
public interface ThirdPartyRefundCabackService {

	/**
	 * 接口含义:<b>三方退款提现任务成功后的回调服务</b></br>
	 * 
	 * <b>返回值描述</b>
	 * 
	 * <p>1.返回值!=null && isOk()==true 成功</p>
	 * <p>2.否则支付失败,通过getErrorMsg()获取异常信息</p>
	 * <p>3.抛出{@link ServiceException}异常,代表支付系统异常</p>
	 * 
	 * <b>数据变更</b>
	 * <p>1.若withdrawModel.getRspResult()=true 提现对应的记录t_cash_postal的相关状态修改为提现成功</p>
	 * <p>2.若withdrawModel.getRspResult()=false 提现对应的记录t_cash_postal的相关状态修改为提现失败</p>
	 * <b>错误码描述:</b>
	 * 参考{@linkplain com.pzj.trade.refund.common.RefundExceptionCodeEnum}
	 * @param withdrawModel
	 * @return
	 */
	Result<Boolean> thirdWithdrawCallback(ThirdPayWithdrawRspModel withdrawModel);
}
