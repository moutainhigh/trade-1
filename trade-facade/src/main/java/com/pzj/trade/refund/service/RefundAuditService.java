/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.refund.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.refund.model.RefundAuditReqModel;

/**
 * 退款审核相关服务
 *
 * @author DongChunfu
 * @date 2016年12月22日
 */
public interface RefundAuditService {
	/**
	 * @api {dubbo} com.pzj.trade.refund.service.RefundAuditService.refundAudit
	 *      退款审核
	 * @apiGroup 退款
	 * @apiName 退款审核
	 * @apiDescription 退款审核服务
	 * @apiVersion 2.2.0-SNAPSHOT
	 *
	 * @apiParam(请求参数) {RefundAuditReqModel} reqModel 退款审核请求参数
	 * @apiParam(请求参数) {ServiceContext} context 服务上下文
	 *
	 * @apiParam (RefundAuditReqModel) {String} refundId 退款ID(必填)
	 * @apiParam (RefundAuditReqModel) {Long} auditorId 审核人ID(必填)
	 * @apiParam (RefundAuditReqModel) {int} auditorParty 审核方(1平台;2财务;3对接)
	 * @apiParam (RefundAuditReqModel) {int} auditResult 审核结果(1
	 *           初始状态;2审核通过;3拒绝退款)
	 * @apiParam (RefundAuditReqModel) {String} refusedMsg 拒绝原因(拒绝时不得为空)
	 *
	 * @apiParamExample 请求参数示例
	 *
	 *                  { "reqModel":
	 *                  {"refundId":"fdsaeabcdkdadkjfoia923kdkaj93kda",
	 *                  "auditorId":8888888L, "auditorParty":1, "auditResult":1,
	 *                  } "context":{.......}}
	 *
	 * @apiParam (错误码) {RefundArgsException} 10301 参数错误
	 * @apiParam (错误码) {RefundFlowNotFoundException} 10323 退款流水不存在异常
	 * @apiParam (错误码) {RefundFlowStateException} 10326 退款流水状态异常
	 * @apiParam (错误码) {RefundRollbackException} 10325 退款回滚异常
	 * @apiParam (错误码) {RefundFlowIsNoneException} 10323 没有对应的退款申请信息
	 * @apiParam (错误码) {RefundFlowIsNoneException} 10323 没有对应的退款申请信息
	 * @apiParam (错误码) {RefundFlowIsNoneException} 10323 没有对应的退款申请信息
	 * @apiParam (错误码) {RefundFlowIsNoneException} 10323 没有对应的退款申请信息
	 *
	 * @apiParam (响应数据) {int} errorCode 返回结果码
	 * @apiParam (响应数据) {String} errorMsg 返回结果提示
	 * @apiParam (响应数据) {Boolean} data 返回结果,<code>true</code>申请成功,
	 *           <code>fasle</code>申请失败
	 *
	 * @apiSuccessExample 成功响应数据
	 *
	 *                    { "errorCode": 10000, "errorMsg": "ok","data":true }
	 *
	 * @apiExample 影响数据库数据
	 *
	 *             库:trade 表：t_refund_refuse_info 插入：refuse_id,refund_id,reason
	 *             表:t_refund_oper_log
	 *             插入：log_id,refund_id,operator_id,prev,later,action,create_time
	 *             表:t_order_merchrefund_flow 更新:refund_audit_state,refund_state
	 */
	public Result<Boolean> refundAudit(RefundAuditReqModel reqModel, ServiceContext context);

}
