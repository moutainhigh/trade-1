package com.pzj.trade.confirm.service;

import java.util.ArrayList;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.exception.ServiceException;
import com.pzj.trade.confirm.model.OverdueConfirmReqModel;
import com.pzj.trade.confirm.response.OverdueConfirmRespModel;

public interface ConfirmQueryService {

	/**
	 * @api {dubbo} com.pzj.trade.confirm.service.ConfirmQueryService#queryNotConfirmMerchOfAck() 获取订单商品状态是待确认且逾期的商品
	 * @apiGroup 核销
	 * @apiName 获取订单商品状态是待确认，并且已经逾期的商品
	 * @apiDescription <p>获取订单商品状态是待确认，并且已经逾期的商品</p>
	 * @apiVersion 1.1.0
	 *
	 * @apiSuccess (返回-成功) {int} OK 成功标识 [OK=10000]
	 * @apiSuccess (返回-成功) {int} errorCode 错误码
	 * @apiSuccess (返回-成功) {String} errorMsg 错误描述信息
	 * @apiSuccess (返回-成功) {ArrayList[Long]} data voucher_id列表
	 *
	 * @apiSuccessExample {json} Success-10000
	 *     {
	 *          "errorCode": "10000",
	 *          "errorMsg": "ok",
	 *          "data": [12313,123123,32523,4545]
	 *     }
	 *
	 */
	Result<ArrayList<Long>> queryNotConfirmMerchOfAck();

	/**
	 * <b>接口含义:</b>分页查询已超过游玩有效期且支持自动核销的订单, 用于系统自动核销商品.</br>
	 *
	 * <b>返回值描述:</b>
	 * <p>1. 返回值为true, 代表取消成功.</p>
	 * <p>2. 返回值为false, 由于订单状态或处于付款流程中等原因, 暂不可取消.</p>
	 * <p>3. 抛出 {@link ServiceException}异常, 代表订单取消失败.</p>
	 * <note>
	 * <b>数据变更:无.</b>
	 * </note>
	 * <b>错误码描述:</b>
	 * <ul>
	 *  <li>10701: 当前订单不可取消(订单状态、非主订单等)</li>
	 * </ul>
	 * @param reqModel	分页请求参数模型
	 * @param context		服务上下文.
	 * @return
	 */
	Result<OverdueConfirmRespModel> overdueMerches(OverdueConfirmReqModel reqModel, ServiceContext context);
}
