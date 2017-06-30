package com.pzj.trade.payment.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.exception.ServiceException;

/**
 * @api {dubbo} com.pzj.trade.payment.service.PaymentTaskService 有关订单支付取消任务
 * @apiGroup 支付取消
 * @apiName 支付取消任务
 * @apiDescription<p>支付取消服务</p>
 * @author kangzl
 */
public interface PaymentTaskService {
	/**
	 * 接口含义:<b>支付主取消的定时任务(批量)</b></br>
	 * 
	 * <b>返回值描述</b>
	 * 
	 * <p>1.返回值!=null && isOk()==true 成功</p>
	 * <p>2.否则支付失败,通过getErrorMsg()获取异常信息</p>
	 * <p>3.抛出{@link ServiceException}异常,代表支付系统异常</p>
	 * 
	 * <b>数据变更</b>
	 * <p>相关需要进行支付取消的订单在t_order表中对应的字段order_status=20,pay_state=0</p>
	 * <b>错误码描述:</b>
	 * 参考{@linkplain com.pzj.core.trade.payment.engine.exception.PayErrorCode}
	 * @param context
	 * @return
	 */
	Result<Boolean> batchCancelPayment(ServiceContext context);

	/**
	 * 接口含义:<b>支付主取消的定时任务(单个)</b></br>
	 * 
	 * <b>返回值描述</b>
	 * 
	 * <p>1.返回值!=null && isOk()==true 成功</p>
	 * <p>2.否则支付失败,通过getErrorMsg()获取异常信息</p>
	 * <p>3.抛出{@link ServiceException}异常,代表支付系统异常</p>
	 * 
	 * <b>数据变更</b>
	 * <p>相关需要进行支付取消的订单在t_order表中对应的字段order_status=20,pay_state=0</p>
	 * <b>错误码描述:</b>
	 * 参考{@linkplain com.pzj.core.trade.payment.engine.exception.PayErrorCode}
	 * @param context
	 * @return
	 */
	Result<Boolean> cancelPaymentSinge(String saleOrderId, ServiceContext context);

}
