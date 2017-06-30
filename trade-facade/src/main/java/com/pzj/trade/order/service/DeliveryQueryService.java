package com.pzj.trade.order.service;

import java.util.List;

import com.pzj.framework.context.Result;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.order.model.DeliveryDetailModel;

/**
 * 订单配送信息查询服务
 * Created by fanggang on 2016/11/4.
 */
public interface DeliveryQueryService {

    /**
     * @api {dubbo} com.pzj.trade.order.service.DeliveryQueryService#queryOrderDeliveryDetail(List<String>) 查询订单的配送信息
     * @apiGroup 订单
     * @apiName 查询订单的配送信息
     * @apiDescription <p>根据一组采购订单ID列表查询订单的配送信息</p>
     * @apiVersion 1.1.0
     * @apiParam {List[String]} orderIDs 采购订单ID列表
     * @apiUse QueryResult_Success
     * @apiSuccess (返回-成功) {String} data.records.orderID 采购订单ID
     * @apiSuccess (返回-成功) {Integer} data.records.deliveryWay 配送方式(1:上门自提, 2:快递配送)
     * @apiSuccess (返回-成功) {String} data.records.expressCompany 快递公司
     * @apiSuccess (返回-成功) {String} data.records.expressNO 快递单号
     */
    Result<QueryResult<DeliveryDetailModel>> queryOrderDeliveryDetail(List<String> orderIDs);

}