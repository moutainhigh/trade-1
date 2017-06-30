package com.pzj.trade.sms.service;

import com.pzj.framework.context.Result;
import com.pzj.trade.sms.model.SMSVoucherReqModel;

/**
 * 发送凭证短信服务
 * @author kanglz
 *
 */
public interface ISendTradeVoucherSMSService {

	/**
	 * @api {dubbo} com.pzj.trade.sms.service.ISendTradeVoucherSMSService.sendSMS  发送凭证短信服务
	 * @apiGroup 交易系统
	 * @apiName 发送凭证短信
	 * @apiDescription 发送凭证短信务
	 * @apiVersion 1.1.1
	 *
	 * @apiParam(请求参数) {com.pzj.trade.sms.model.SMSVoucherReqModel} reqModel 短信发送的请求参数模型
	 * 
	 * @apiParam (reqModel) {String } trasactionId 交易Id <strong>(交易系统的交易ID)</strong>
	 * @apiParam (reqModel) {String} orderId 销售订单ID <strong>(交易对应的销售订单ID(可以为空))</strong>
	 * 
	 * 
	 * @apiParamExample {json} [reqModel]
	 *  {
	 *     "trasactionId": "MF708212626",
	 *     "orderId":"MF708212626"
	 *   }
	 *   
	 *   
	 * @apiParam (响应数据) {int} errorCode 返回结果码
	 * @apiParam (响应数据) {String} errorMsg 返回结果提示
	 * @apiParam (响应数据) {Boolean} data 
	 *   
	 * @apiSuccess {com.pzj.framework.context.Result} Result 返回对象
	 * @apiSuccessExample [{success}] [返回成功示例]
	 *  {
	 *     "errorCode": 10000,
	 *     "errorMsg": "ok",
	 *   }
	 *   
	 *  @apiExample 影响数据库数据
	 *  数据库：trade
	 *  表：
	 *  插入：无
	 */
	Result<Boolean> sendSMS(SMSVoucherReqModel reqModel);
}
