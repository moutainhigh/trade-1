package com.pzj.trade.confirm.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.confirm.model.ConfirmCodeModel;
import com.pzj.trade.confirm.model.MfCodeModel;
import com.pzj.trade.confirm.response.MfcodeResult;

/**
 * 魔方码管理服务接口.
 * @author YRJ
 *
 */
public interface ConfirmCodeService {

    /**
     * @api {dubbo} com.pzj.trade.confirm.service.ConfirmCodeService#verify(ConfirmCodeModel,ServiceContext) 魔方码验证功能
     * @apiGroup 核销
     * @apiName 魔方码验证功能
     * @apiDescription <p>魔方码验证功能</p>
     * @apiVersion 1.1.0
     *
     * @apiParam (ConfirmCodeModel) {String} code 魔方码
     * @apiParam (ConfirmCodeModel) {long} supplierId 供应商ID
     *
     * @apiUse ServiceContext
     *
     * @apiSuccess (返回-成功) {int} errorCode 错误码
     * @apiSuccess (返回-成功) {String} errorMsg 错误描述信息
     * @apiSuccess (返回-成功) {String} orderId 成功返回orderID
     *
     * @apiSuccessExample {json} Success-10000
     *     {
     *          "errorCode": "10000",
     *          "errorMsg": "ok",
     *          "data": "MF10010101"
     *     }
     *
     * @apiErrorExample {json} Error-41001
     *     {
     *          "errorCode": "41001",
     *          "errorMsg": "验证失败, 请指定合法参数 | 请检查码号是否输入正确",
     *          "data": null
     *     }
     * @apiErrorExample {json} Error-51001
     *     {
     *          "errorCode": "51001",
     *          "errorMsg": "该魔方码已经使用",
     *          "data": null
     *     }
     * @apiErrorExample {json} Error-51002
     *     {
     *          "errorCode": "51002",
     *          "errorMsg": "该魔方码已经过期",
     *          "data": null
     *     }
     * @apiErrorExample {json} Error-51003
     *     {
     *          "errorCode": "51003",
     *          "errorMsg": "该魔方码不存在, 请检查魔方码是否正确",
     *          "data": null
     *     }
     * @apiErrorExample {json} Error-Exception
     * ServiceException 服务异常
     * ConfirmCodeException 魔方码验证失败, 请稍后重试.
     */
    Result<String> verify(ConfirmCodeModel codeModel, ServiceContext serviceContext);

    /**
     * @api {dubbo} com.pzj.trade.confirm.service.ConfirmCodeService#getMfcode(MfCodeModel,ServiceContext) 根据魔方码获取基本信息
     * @apiGroup 核销
     * @apiName 根据魔方码获取基本信息
     * @apiDescription <p>根据魔方码获取基本信息</p>
     * @apiVersion 1.1.0
     *
     * @apiParam (MfCodeModel) {String} codeId 魔方码
     *
     * @apiUse ServiceContext
     *
     * @apiUse Result_Success
     * @apiSuccess (返回-成功) {String} data.mfcode 核销码=魔方码
     * @apiSuccess (返回-成功) {String} data.orderId 订单ID
     * @apiSuccess (返回-成功) {long} data.supplierId 供应商ID
     * @apiSuccess (返回-成功) {int} data.codeState 码状态
     * @apiSuccess (返回-成功) {String} data.codeStateMsg 魔方码状态信息描述
     * @apiSuccess (返回-成功) {Date} data.startTime 魔方码有效期的开始时间
     * @apiSuccess (返回-成功) {Date} data.endTime 魔方码有效期的过期时间
     * @apiSuccess (返回-成功) {Date} data.createTime 魔方码创建时间
     *
     * @apiSuccess (返回-成功) {List[MerchSimpleModel]} data.merchs 对应商品信息
     * @apiSuccess (返回-成功) {long} data.merchs.product_id 产品ID
     * @apiSuccess (返回-成功) {int} data.merchs.total_num 数量=总数减去退款数量
     *
     * @apiSuccessExample {json} Success-10000
     *     {
     *          "errorCode": "10000",
     *          "errorMsg": "ok",
     *          "data": {
     *              "mfcode":"bdnhd",
     *              "orderId":"MF121212",
     *              "supplierId":123123123,
     *              "codeState":0,
     *              "codeStateMsg":"",
     *              "startTime":"2016-10-10 08:00:00",
     *              "endTime":"2016-10-20 23:59:59",
     *              "createTime":"2016-10-10 00:00:00",,
     *              "merchs":{
     *                  "product_id":1231231,
     *                  "total_num":2,
     *              }
     *
     *          }
     *     }
     *
     * @apiErrorExample {json} Error-41001
     *     {
     *          "errorCode": "41001",
     *          "errorMsg": "获取魔方码失败.",
     *          "data": null
     *     }
     * @apiErrorExample {json} Error-Exception
     * ServiceException 服务异常
     * ConfirmCodeException 魔方码验证失败, 请稍后重试.
     */
    Result<MfcodeResult> getMfcode(MfCodeModel codeModel, ServiceContext serviceContext);

}