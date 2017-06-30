package com.pzj.trade.payment.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.exception.ServiceException;
import com.pzj.trade.payment.entity.PaymentResult;
import com.pzj.trade.payment.vo.ChildOrderPaymentModel;
import com.pzj.trade.payment.vo.PayCallbackVO;
import com.pzj.trade.payment.vo.PaymentVO;

/**
 * 订单支付
 * @author YRJ
 */
public interface PaymentService {

	/**
	 * 
	 * @api {dubbo} com.pzj.trade.payment.service.PaymentService.payOrder
	 * @apiGroup 支付
	 * @apiName 支付申请
	 * @apiDescription 接口含义:<b>对主订单进行付款</b></br>
	 * @apiVersion 2.2.0-SNAPSHOT
	 * @apiExample <b>返回值描述</b>
	 * 
	 * <p>1.返回值!=null && isOk()==true && getDate()!=null支付成功</p>
	 * <p>2.否则支付失败,通过getErrorMsg()获取异常信息</p>
	 * <p>3.抛出{@link ServiceException}异常,代表支付系统异常</p>
	 * 
	 * <b>数据变更</b>
	 * <p>1.若订单需要进行第三方支付,t_order表中对应的数据，字段pay_type=1</p>
	 * <p>2.若订单无需第三方支付,但相关的子订单需要供应商补差,t_order表中对应的数据，字段pay_type=2</p>
	 * <p>3.若订单无需他人进行其他操作,则t_order表中的数据,order_status=10</p>
	 * 
	 * <b>错误码描述:</b>
	 * 参考{@linkplain com.pzj.core.trade.payment.engine.exception.PayErrorCode}
	 * @param payment
	 * @param context
	 * @return
	 */
	Result<PaymentResult> payOrder(PaymentVO payment, ServiceContext context);

	/**
	 * 接口含义:<b>子订单补差支付</b></br>
	 * 
	 * <b>返回值描述</b>
	 * 
	 * <p>1.返回值!=null && isOk()==true && getDate()!=null支付成功</p>
	 * <p>2.否则支付失败,通过getErrorMsg()获取异常信息</p>
	 * <p>3.抛出{@link ServiceException}异常,代表支付系统异常</p>
	 * 
	 * <b>数据变更</b>
	 * <p>1.若子订单对应的交易中还存在其他需要补差的订单,t_order表中对应的数据，字段pay_type=2</p>
	 * <p>2.若交易中没有其他的补差订单,则t_order表中的数据,order_status=10</p>
	 * 
	 * <b>错误码描述:</b>
	 * 参考{@linkplain com.pzj.core.trade.payment.engine.exception.PayErrorCode}
	 * @param reqModel
	 * @param context
	 * @return
	 */
	Result<Boolean> payChildOrder(ChildOrderPaymentModel reqModel, ServiceContext context);

	/**
	 * 接口含义:<b>主订单付款成功后回调. 仅限第三方支付回调.</b></br>
	 * 
	 * <b>返回值描述</b>
	 * 
	 * <p>1.返回值!=null && isOk()==true 成功</p>
	 * <p>2.否则支付失败,通过getErrorMsg()获取异常信息</p>
	 * <p>3.抛出{@link ServiceException}异常,代表支付系统异常</p>
	 * 
	 * <b>数据变更</b>
	 * <p>1.若订单相关的子订单需要供应商补差,t_order表中对应的数据，字段pay_type=2</p>
	 * <p>2.若订单无需他人进行其他操作,则t_order表中的数据,order_status=10</p>
	 * 
	 * <b>错误码描述:</b>
	 * 参考{@linkplain com.pzj.core.trade.payment.engine.exception.PayErrorCode}
	 * @param payVO
	 * @param context
	 * @return
	 */
	Result<Boolean> payCallback(PayCallbackVO payVO, ServiceContext context);

}
