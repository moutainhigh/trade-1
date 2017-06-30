package com.pzj.trade.ack.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.ack.model.AckModel;

/**
 * 订单二次确认接口.
 * @author YRJ
 *
 */
public interface OrderAckService {

	/**
	 * @apiDefine AckModel AckModel 二次确认查询对象
	 *
	 * @apiParam (AckModel) {String} orderId 订单号
	 * @apiParam (AckModel) {boolean} acknowledge 确认还是拒绝（true: 确认；false：拒绝）
	 * @apiParam (AckModel) {String} thirdCode 第三方订单号、辅助码. 仅用于对接使用, 平台不使用.
	 */

	/**
	 * @api {dubbo} com.pzj.trade.ack.service.OrderAckService#ack(AckModel,ServiceContext) 订单二次确认
	 * @apiGroup 订单
	 * @apiName 订单二次确认
	 * @apiDescription <p>订单的二次确认</p>
	 * <p>影响的数据库：trade.t_order(need_confirm,twice_confirm_time) </p>
	 * @apiVersion 1.1.0
	 * @apiUse AckModel
	 * @apiUse ServiceContext
	 * @apiUse Result_Success_Boolean
	 *
	 * @apiSuccessExample {json} Success-10000
	 *     {
	 *          "errorCode": "10000",
	 *          "errorMsg": "ok",
	 *          "data": true
	 *      }
	 *
	 * @apiErrorExample {json} Error-41001
	 *     {
	 *          "errorCode": "41001",
	 *          "errorMsg": "二次确认参数有误",
	 *          "data": false
	 *      }
	 * @apiErrorExample {json} Error-41002
	 *     {
	 *          "errorCode": "41002",
	 *          "errorMsg": "无法获取订单信息",
	 *          "data": false
	 *      }
	 * @apiErrorExample {json} Error-41003
	 *     {
	 *          "errorCode": "41003",
	 *          "errorMsg": "该订单无需进行二次确认",
	 *          "data": false
	 *      }
	 * @apiErrorExample {json} Error-41003
	 *     {
	 *          "errorCode": "41003",
	 *          "errorMsg": "该订单已经进行了二次确认",
	 *          "data": false
	 *      }
	 * @apiErrorExample {json} Error-41003
	 *     {
	 *          "errorCode": "1478",
	 *          "errorMsg": "确认失败, 请稍后重试",
	 *          "data": false
	 *     }
	 */
	Result<Boolean> ack(AckModel ackModel, ServiceContext context);
}
