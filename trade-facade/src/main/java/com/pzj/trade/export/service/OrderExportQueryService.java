/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.export.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.export.model.OrderExportQueryReqModel;
import com.pzj.trade.export.model.OrderExportRepModel;
import com.pzj.trade.export.model.OrderExportVerifyRepModel;
import com.pzj.trade.export.model.OrderExportVerifyReqModel;

/**
 * 导出订单查询服务
 *
 * @author DongChunfu
 * @date 2017年2月9日
 */
public interface OrderExportQueryService {
	/**
	 * @api {dubbo}
	 *      com.pzj.trade.export.service.OrderExportQueryService.queryExports
	 *      查询导出订单列表
	 * @apiGroup 订单
	 * @apiName 查询导出订单列表
	 * @apiDescription 查询导出订单列表
	 * @apiVersion 2.2.0-SNAPSHOT
	 *
	 * @apiParam {OrderExportQueryReqModel} reqModel 查询请求参数
	 *
	 * @apiParam (OrderExportQueryReqModel) {Date} startTime 导出开始时间
	 * @apiParam (OrderExportQueryReqModel) {Date} endTime 导出结束时间
	 * @apiParam (OrderExportQueryReqModel) {String} createBy 导出人(必填)
	 * @apiParam (OrderExportQueryReqModel) {boolean} pageAble 是否分页(true)
	 * @apiParam (OrderExportQueryReqModel) {int} current_page 当前页码(true)
	 * @apiParam (OrderExportQueryReqModel) {int} page_size 页数(20)
	 *
	 * @apiParamExample 请求参数示例
	 *
	 *                  "reqModel":{ "createBy":"dongchunfu","current_page":1,"page_size":20}
	 *
	 * @apiParam (响应数据) {int} total 数据总数
	 * @apiParam (响应数据) {int} current_page 当前页
	 * @apiParam (响应数据) {int} total_page 总页数
	 * @apiParam (响应数据) {int} page_size 每页最大记录数
	 *
	 * @apiSuccessExample 成功返回示例
	 *
	 *                    { "total": 2, "current_page":
	 *                    1,"total_page":1,"page_size":20 ,records:[{
	 *                    "fileName": "MF123456789", "exportState":
	 *                    "0", "createTime": "Dec 19, 2016 3:11:17 PM"},{
	 *                    "fileName": "MF123456789", "exportState":
	 *                    "0", "createTime": "Dec 19, 2016 3:11:17 PM"}]}
	 */
	Result<QueryResult<OrderExportRepModel>> queryExports(OrderExportQueryReqModel reqModel);

	Result<OrderExportVerifyRepModel> verifyExportLog(OrderExportVerifyReqModel reqModel);

}
