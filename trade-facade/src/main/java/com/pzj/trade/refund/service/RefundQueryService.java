package com.pzj.trade.refund.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.refund.model.QueryRefundApplyReqModel;
import com.pzj.trade.refund.vo.ForceRefundApplyVO;

/**
 * 退款查询服务
 *
 * @author DongChunfu
 * @date 2016年12月18日
 */
public interface RefundQueryService {

	/**
	 * @api {dubbo}
	 *      com.pzj.trade.refund.service.RefundQueryService.queryRefundApplyPage
	 *      财务分页查询强制退款申请
	 * @apiGroup 退款
	 * @apiName 财务分页查询强制退款申请
	 * @apiDescription 财务分页查询强制退款列表
	 * @apiVersion 2.2.0-SNAPSHOT
	 *
	 * @apiParam {QueryRefundApplyReqModel} reqModel 分页查询请求参数
	 * @apiParam {ServiceContext} context 服务上下文
	 *
	 * @apiParam (QueryRefundApplyReqModel) {String} orderId 订单ID
	 * @apiParam (QueryRefundApplyReqModel) {int} auditState
	 *           退款审核状态(1待审核态;2审核通过;3拒绝退款)
	 * @apiParam (QueryRefundApplyReqModel) {int} thirdAuditState
	 *           三方审核状态(0对接审核中1对接审核通过;2. 对接审核拒绝;3 不需对接审核)
	 * @apiParam (QueryRefundApplyReqModel) {Date} startApplyDate 开始申请时间
	 * @apiParam (QueryRefundApplyReqModel) {Date} endApplyDate 申请结束时间
	 * @apiParam (QueryRefundApplyReqModel) {Date} startAuditDate 开始审核时间
	 * @apiParam (QueryRefundApplyReqModel) {Date} endAuditDate 审核结束时间
	 *
	 * @apiParamExample 请求参数示例
	 *
	 *                  "reqModel":{ "orderId":MF12345678} "context":{ .........
	 *                  } }
	 *
	 * @apiParam (响应数据) {int} total 数据总数
	 * @apiParam (响应数据) {int} current_page 当前页
	 * @apiParam (响应数据) {int} total_page 总页数
	 * @apiParam (响应数据) {int} page_size 每页最大记录数
	 *
	 * @apiSuccessExample 成功返回示例
	 *
	 *                    { "total": 10, "current_page":
	 *                    1,"total_page":1,"page_size":10 ,records:[{{
	 *                    "orderId": "MF123456789", "refundId":
	 *                    "8fd0b9d8a2cc41918973e82353006915", "refundNum": 1,
	 *                    "applyDate": "Dec 19, 2016 3:11:17 PM", "auditor":
	 *                    "123456789", "dockAudit": 1 } }]}
	 */
	public Result<QueryResult<ForceRefundApplyVO>> queryRefundApplyPage(QueryRefundApplyReqModel reqModel,
			ServiceContext context);

}
