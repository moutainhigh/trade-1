package com.pzj.trade.confirm.service;

import java.util.ArrayList;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.confirm.model.BatchConfirmReqModel;
import com.pzj.trade.confirm.model.ConfirmFailValue;
import com.pzj.trade.confirm.model.ConfirmModel;
import com.pzj.voucher.entity.VoucherEntity;

/**
 * 订单核销接口
 *
 * @author YRJ
 * @author DongChunfu
 * @version $Id: ConfirmService.java, v 0.1 2017年3月22日 上午11:34:37 Administrator Exp $
 */
public interface ConfirmService {

	/**
	 * @api {dubbo} com.pzj.trade.confirm.service.ConfirmService#confirm(ConfirmModel,ServiceContext) 商品核销
	 * @apiGroup 核销
	 * @apiName 商品核销
	 * @apiDescription  <p>将商品从待消费状态变为已消费状态.</p>
	 *                  <p>如果是发货核销：记录发货信息，并且核销这个商品。</p>
	 * @apiVersion 1.1.0
	 *
	 * @apiParam (ConfirmModel) {long} voucherId 商品ID
	 * @apiParam (ConfirmModel) {long} ticketPoint 检票点. 当且仅当为演艺门票时, 传检票点信息(必填项), 通用产品不用传(选填项).
	 * @apiParam (ConfirmModel) {int} [type=1] type 核销方式. 只是用于记录商品采用何种方式进行的核销.[1. 手动核销, 平台点击触发; 2. 系统核销, 系统定时触发的]
	 * @apiParam (ConfirmModel) {DeliveryDetailModel} [deliveryDetail] deliveryDetail 发货配送信息
	 * @apiParam (ConfirmModel) {String} deliveryDetail.orderID 采购订单ID[在特产发货时是必填项]
	 * @apiParam (ConfirmModel) {Integer} deliveryDetail.deliveryWay 配送方式(1:上门自提, 2:快递配送)[在特产发货时是必填项]
	 * @apiParam (ConfirmModel) {String} deliveryDetail.expressCompany 快递公司[在特产发货时是必填项]
	 * @apiParam (ConfirmModel) {String} deliveryDetail.expressNO 快递单号[在特产发货时是必填项]
	 *
	 * @apiParamExample {json} Invoke-Example
	 * {
	 *     "voucherId": 123123,
	 *     "ticketPoint": 12312,
	 *     "type": 1,
	 *     "deliveryDetail": {
	 *         "orderID": "MF234234",
	 *         "deliveryWay": 2,
	 *         "expressCompany": "顺丰速递",
	 *         "expressNO": "SF1412312312123"
	 *     }
	 * }
	 *
	 * ServiceContext 略
	 *
	 * @apiUse ServiceContext
	 * @apiUse Result_Success
	 *
	 * @apiSuccess (返回-成功) {Long} data.voucherId                                                凭证ID
	 * @apiSuccess (返回-成功) {String} data.voucherContent                                         身份证号或二维码
	 * @apiSuccess (返回-成功) {List[String]} data.voucherContentList                               身份证号或二维码集合
	 * @apiSuccess (返回-成功) {Integer} data.voucherContentType                                    凭证介质类型：（ 1 身份证号   2 二维码或条码及其辅助码  3 手机号)  参照VoucherContentType
	 * @apiSuccess (返回-成功) {String} data.voucherContentImageUrl                                 凭证图片地址
	 * @apiSuccess (返回-成功) {String} data.contactMobile                                          凭证人手机号
	 * @apiSuccess (返回-成功) {String} data.contactName                                            凭证人姓名
	 * @apiSuccess (返回-成功) {String} data.checkStartTime                                         检票时间段：开始时间 格式是： 小时:分钟   例如：10:23
	 * @apiSuccess (返回-成功) {String} data.checkEndTime                                           检票时间结束 格式是：小时:分钟  例如：10：23
	 * @apiSuccess (返回-成功) {Date} data.startTime                                                凭证有效开始时间
	 * @apiSuccess (返回-成功) {Date} data.expireTime                                               凭证有效过期时间
	 * @apiSuccess (返回-成功) {Date} data.showStartTime                                            演艺开始时间
	 * @apiSuccess (返回-成功) {Date} data.showEndTime                                              演艺结束时间
	 * @apiSuccess (返回-成功) {Integer} data.voucherState                                          凭证状态(-1:不可用；0：可以使用；1核销完毕；2：退单) 参照 VoucherState枚举对象
	 * @apiSuccess (返回-成功) {Integer} data.voucherCategory                                       产品线(景区、演艺、住宿、特产、小交通、线路)  参照ProductCategory 枚举对象 （取值  GlobalDict.ProductCategory）
	 * @apiSuccess (返回-成功) {String} data.extendVoucherContent                                   拓展属性（身份证、指纹码信息）
	 * @apiSuccess (返回-成功) {Integer} data.extendVoucherContentType                              拓展类型 ()
	 * @apiSuccess (返回-成功) {String} data.extendVoucherImageUrl                                  拓展存取图片
	 * @apiSuccess (返回-成功) {Long} data.supplierId                                               供应商id
	 * @apiSuccess (返回-成功) {String} data.transactionId                                          交易号
	 * @apiSuccess (返回-成功) {Date} data.createTime                                               创建时间
	 * @apiSuccess (返回-成功) {Date} data.upateTime                                                更新时间
	 * @apiSuccess (返回-成功) {Long} data.productId                                                产品ID
	 * @apiSuccess (返回-成功) {String} data.productName                                            产品名称
	 * @apiSuccess (返回-成功) {Integer} data.productNum                                            产品数量
	 * @apiSuccess (返回-成功) {List[VoucherConfirm]} data.voucherConfirmList                       记录核销的产品和核销数量
	 * @apiSuccess (返回-成功) {Long}        data.voucherConfirmList.voucherId        凭证ID
	 * @apiSuccess (返回-成功) {List[Long]}  data.voucherConfirmList.voucherIdVoucher 凭证ID集合，查询使用
	 * @apiSuccess (返回-成功) {Long}        data.voucherConfirmList.childProductId   子产品ID
	 * @apiSuccess (返回-成功) {Long}        data.voucherConfirmList.productId        产品ID
	 * @apiSuccess (返回-成功) {Long}        data.voucherConfirmList.supplierId       供应商ID
	 * @apiSuccess (返回-成功) {Integer}     data.voucherConfirmList.usedTimes        使用次数
	 * @apiSuccess (返回-成功) {Integer}     data.voucherConfirmList.maxUseTimes      最多使用次数
	 * @apiSuccess (返回-成功) {String} data.accurateStartTime                                      精确日期（仅限供订查询有效期开始日期的数据使用）
	 * @apiSuccess (返回-成功) {String} data.accurateEndTime                                        精确日期（仅限供订查询有效期开始日期的数据使用）
	 * @apiSuccess (返回-成功) {List[Long]} data.voucherIdList                                      根据voucherId批量查询使用
	 * @apiSuccess (返回-成功) {List[ExtendVoucher]} data.extendVoucherList                         凭证拓展熟悉集合
	 * @apiSuccess (返回-成功) {Long} data.extendVoucherList.voucherId 核销凭证ID
	 * @apiSuccess (返回-成功) {Long} data.extendVoucherList.supplierId 供应商ID
	 * @apiSuccess (返回-成功) {String} data.extendVoucherList.voucherAttr 凭证属性名称
	 * @apiSuccess (返回-成功) {String} data.extendVoucherList.voucherAttrContent 凭证属性值
	 * @apiSuccess (返回-成功) {Date} data.createStartTime                                          创建开始时间
	 * @apiSuccess (返回-成功) {Date} data.createEndTime                                            创建结束时间
	 * @apiSuccess (返回-成功) {ScenicPruductVoucherVO} data.scenicPruductVoucherVO                 景区产品信息
	 * @apiSuccess (返回-成功) {Integer} data.scenicPruductVoucherVO.ticketVarie  散票 0 ，团票  1
	 * @apiSuccess (返回-成功) {String} data.scenicPruductVoucherVO.scenic 景区名称
	 * @apiSuccess (返回-成功) {String} data.scenicPruductVoucherVO.scenicId 景区ID
	 * @apiSuccess (返回-成功) {Date} data.scenicPruductVoucherVO.startTime 游玩开始日期
	 * @apiSuccess (返回-成功) {Date} data.scenicPruductVoucherVO.endTime 游玩结束日期
	 * @apiSuccess (返回-成功) {List[TheaterProductVoucherVO]} data.theaterProductVoucherVOList     针对非一证一票的voucher存储是一个voucher对应多个产品信息，所以要存储为多个
	 * @apiSuccess (返回-成功) {Integer} data.theaterProductVoucherVOList.ticketVarie=0] data.theaterProductVoucherVOList.ticketVarie 散票 0 ，团票  1
	 * @apiSuccess (返回-成功) {String} data.theaterProductVoucherVOList.scenic 景区名称
	 * @apiSuccess (返回-成功) {String} data.theaterProductVoucherVOList.scenicId 景区ID
	 * @apiSuccess (返回-成功) {Long} data.theaterProductVoucherVOList.productId 产品ID
	 * @apiSuccess (返回-成功) {String} data.theaterProductVoucherVOList.screenings 演出场次
	 * @apiSuccess (返回-成功) {String} data.theaterProductVoucherVOList.region 演出区域
	 * @apiSuccess (返回-成功) {String} data.theaterProductVoucherVOList.seatNumbers 座位号、中间用,隔开
	 * @apiSuccess (返回-成功) {Date} data.theaterProductVoucherVOList.showStartTime 演出开始时间
	 * @apiSuccess (返回-成功) {Date} data.theaterProductVoucherVOList.showEndTime 演出结束时间
	 * @apiSuccess (返回-成功) {SpecialityProductVoucherVo} data.specialityProductVoucherVo         特产产品信息
	 * @apiSuccess (返回-成功) {String} data.specialityProductVoucherVo.address 收货人信息
	 * @apiSuccess (返回-成功) {Integer} data.voucherType                                           核销类型 1: 自动 2: 手动; 3: 固定时间
	 * @apiSuccess (返回-成功) {Date} data.expVoucherTime                                           预计核销时间
	 * @apiSuccess (返回-成功) {Date} data.voucherTime                                              实际核销时间(首次核销时间)
	 * @apiSuccess (返回-成功) {Integer} data.isOverdue                                             是否逾期核销(暂时针对门票设置， 默认0：不是逾期，1是逾期 )
	 * @apiSuccess (返回-成功) {Long} data.tempId                                                   临时ID，用户批量插入在计算的时候判定唯一，插入数据结束后不在继续使用 仅供voucher模块内部使用
	 *
	 * @apiSuccessExample {json} Success-10000
	 * {
	 *      "errorCode": "10000",
	 *      "errorMsg": "ok",
	 *      "data": {
	 *          "voucherId":123123,
	 *          "voucherContent":"123123123123123123",
	 *          "voucherContentList":["13413413412","34134123412341234"],
	 *          "voucherContentType":1,
	 *          "voucherContentImageUrl":"",
	 *          "contactMobile":"18658585858",
	 *          "contactName":"James",
	 *          "checkStartTime":"08:00",
	 *          "checkEndTime":"17:00",
	 *          "...":""
	 *      }
	 * }
	 *
	 * @apiErrorExample {json} Error-45001
	 *     {
	 *          "errorCode": "45001",
	 *          "errorMsg": "核销失败, 参数错误.",
	 *          "data": null
	 *     }
	 *
	 * @apiErrorExample {json} Error-40001
	 *     {
	 *          "errorCode": "40001",
	 *          "errorMsg": "核销失败",
	 *          "data": null
	 *     }
	 *
	 * @apiErrorExample {json} Error-Exception
	 * ServiceException 服务异常
	 * MerchNotExistException 核销的商品不存在
	 */
	Result<VoucherEntity> confirm(ConfirmModel confirmModel, ServiceContext serviceContext);

	/**
	 * 批量核销接口
	 * {@link com.pzj.trade.confirm.service.ConfirmService#confirm}
	 *
	 * @author DongChunfu
	 * @param reqModel 批量核销请求参数
	 * @param serviceContext 可空
	 * @return 核销失败原因键值对
	 */
	Result<ArrayList<ConfirmFailValue>> batchConfirm(BatchConfirmReqModel reqModel, ServiceContext serviceContext);

}
