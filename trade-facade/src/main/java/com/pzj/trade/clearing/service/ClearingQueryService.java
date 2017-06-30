package com.pzj.trade.clearing.service;

import java.util.ArrayList;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.clearing.model.UnClearingRequestModel;
import com.pzj.trade.clearing.response.UnClearingRelationResponse;

/**
 * 清算查询接口.
 * @author YRJ
 *
 */
public interface ClearingQueryService {

    /**
     * @api {dubbo} com.pzj.trade.clearing.service.ClearingQueryService#queryUnClearingRecordByPager(UnClearingRequestModel,ServiceContext) 分页查询未清算记录
     * @apiGroup 清算
     * @apiDescription <p>分页查询未清算记录</p>
     * <p>影响数据库：无</p>
     * @apiVersion 1.1.0
     *
     * @apiParam (UnClearingRequestModel) {int} current_page 当前页码
     * @apiParam (UnClearingRequestModel) {int} [page_size=20] page_size 每页显示的最大数量
     *
     * @apiUse ServiceContext
     *
     * @apiUse Result_Success
     *
     * @apiSuccess (返回-成功) {ArrayList[UnClearingRelationResponse]} data.list 未清算记录
     * @apiSuccess (返回-成功) {long} data.list.cleanId 主键ID
     * @apiSuccess (返回-成功) {String} data.list.orderId 订单ID
     * @apiSuccess (返回-成功) {String} data.list.transactionId 事务ID
     * @apiSuccess (返回-成功) {String} data.list.merchId 商品ID
     * @apiSuccess (返回-成功) {long} data.list.effecTime 预期清算时间
     * @apiSuccess (返回-成功) {int} data.list.cleanType 清算类别. 1: 正常清算; 2: 逾期清算
     * @apiSuccess (返回-成功) {int} data.list.cleanState 清算状态. 0: 未清算; 1: 已清算; 2: 清算失败
     * @apiSuccess (返回-成功) {int} data.list.isManual 是否可手动清算.0: 不可;1: 可手动.只有清算规则为固定时间规则时, 此值有效.
     * @apiSuccess (返回-成功) {int} data.list.cleanCount 清算次数
     *
     * @apiSuccessExample {json} Success-10000
     *      {
     *              "errorCode": "10000",
     *              "errorMsg": "ok",
     *              "data":
     *              [{
     *                  "cleanId":123123123,
     *                  "orderId":"MF11001",
     *                  "transactionId":"MF11001",
     *                  "merchId":"P12354",
     *                  "effecTime":1123123123123,
     *                  "cleanType":1,
     *                  "cleanState":0,
     *                  "isManual":0,
     *                  "cleanCount":0
     *              },{
     *                  "cleanId":123123123,
     *                  "orderId":"MF11001",
     *                  "transactionId":"MF11001",
     *                  "merchId":"P12354",
     *                  "effecTime":1123123123123,
     *                  "cleanType":1,
     *                  "cleanState":0,
     *                  "isManual":0,
     *                  "cleanCount":0
     *              }]
     *      }
     *
     * @apiErrorExample {json} Error
     *      {
     *          "errorCode": "10000",
     *          "errorMsg": "ok",
     *          "data": null
     *      }
     */
    Result<ArrayList<UnClearingRelationResponse>> queryUnClearingRecordByPager(UnClearingRequestModel clearModel, ServiceContext serviceContext);
}
