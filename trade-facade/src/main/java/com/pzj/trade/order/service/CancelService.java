package com.pzj.trade.order.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.exception.ServiceException;
import com.pzj.trade.order.model.OrderCancelModel;

/**
 * 订单取消服务接口.
 * @author YRJ
 *
 */
public interface CancelService {

	/**
	 * <b>接口含义:</b>取消订单服务接口.</br>
	 * 
	 * <b>返回值描述:</b>
	 * <p>1. 返回值为true, 代表取消成功.</p>
	 * <p>2. 返回值为false, 由于订单状态或处于付款流程中等原因, 暂不可取消.</p>
	 * <p>3. 抛出 {@link ServiceException}异常, 代表订单取消失败.</p>
	 * <note>
	 * <b>数据变更:</b>
	 * <p>1. t_order表, 订单状态值[order_status = 20].</p>
	 * <p>2. t_oper_log表, 新增一条订单取消事件[order_cancel]记录.</p>
	 * </note>
	 * <b>错误码描述:</b>
	 * <ul>
	 *  <li>10701: 当前订单不可取消(订单状态、非主订单等)</li>
	 * </ul>
	 * @param cancelModel
	 * @param context
	 * @return
	 */
	Result<Boolean> cancelOrder(OrderCancelModel cancelModel, ServiceContext context);

	/**
	 * <b>接口含义:</b>批量取消订单服务接口.(仅限系统内部使用)</br>
	 * 
	 * <b>返回值描述:</b>
	 * <p>1. 返回值为true, 代表取消成功.</p>
	 * <p>2. 返回值为false, 由于订单状态或处于付款流程中等原因, 暂不可取消.</p>
	 * <p>3. 抛出 {@link ServiceException}异常, 代表订单取消失败.</p>
	 * <note>
	 * <b>数据变更:</b>
	 * <p>1. t_order表, 订单状态值[order_status = 20].</p>
	 * <p>2. t_oper_log表, 新增一条订单取消事件[order_cancel]记录.</p>
	 * </note>
	 * <b>容错处理:</b>
	 * <p>当且仅当所有订单取消失败, 则接口失败. 反之, 成功</p>
	 * @param cancelModel
	 * @param context
	 * @return
	 */
	Result<Boolean> batchCancel(ServiceContext context);
}
