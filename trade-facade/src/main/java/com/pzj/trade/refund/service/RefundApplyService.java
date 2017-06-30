package com.pzj.trade.refund.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.refund.model.RefundApplyReqModel;

/**
 * 退款申请相关服务
 *
 * @author DongChunfu
 * @date 2016年12月18日
 */
public interface RefundApplyService {
	/**
	 * @api {dubbo} com.pzj.trade.refund.service.RefundApplyService.refundApply
	 *      退款申请
	 * @apiGroup 退款
	 * @apiName 退款申请
	 * @apiDescription 退款申请服务,支持:
	 *                 <li>部分退</li>
	 *                 <li>整单退</li>
	 *                 <li>强制退</li>
	 *                 <li>消费退</li>
	 *                 <li>直签退</li>
	 * @apiVersion 2.2.0-SNAPSHOT
	 *
	 *
	 * @apiParam {RefundApplyReqModel} reqModel 退款申请请求参数
	 * @apiParam {ServiceContext} context 服务上下文
	 *
	 * @apiParam (RefundApplyReqModel) {String} orderId 订单ID
	 * @apiParam (RefundApplyReqModel) {Long} initiatorId 发起人
	 * @apiParam (RefundApplyReqModel) {int} initParty 退款发起方(1用户发起;2平台发起;3 对接拒绝)
	 * @apiParam (RefundApplyReqModel) {int} refundType 退款类型(1普通退款;2强制退款)
	 * @apiParam (RefundApplyReqModel) {List} refundMerches 退款商品集
	 *
	 * @apiParam (RefundMerchandiseVO) {String} merchId 退款商品ID
	 * @apiParam (RefundMerchandiseVO) {int} refundNum 退款商品数量
	 *
	 * @apiParamExample 请求参数示例
	 *
	 *                  { "reqModel":{ "orderId":MF12345678,
	 *                  "initiatorId":123456789, "initParty":1
	 *                  ,"refundType":1,"refundMerches":[{"merchId":P123456789,
	 *                  "refundNum":1}]} context:{ ......... } }
	 *
	 *
	 * @apiParam (响应数据) {int} errorCode 返回结果码
	 * @apiParam (响应数据) {String} errorMsg 返回结果提示
	 * @apiParam (响应数据) {Boolean} data 返回结果,<code>true</code>申请成功,
	 *           <code>fasle</code>申请失败
	 *
	 * @apiParam (错误码) {RefundArgsException} 10301 参数错误
	 * @apiParam (错误码) {RefundMerchNotFoundException} 10302 未找到符合的销售商品信息
	 * @apiParam (错误码) {RepetitiveRefundMerchException} 10303 重复的退款商品
	 * @apiParam (错误码) {OrderNotContainMerchException} 10304 商品不属于这个销售单
	 * @apiParam (错误码) {OrderNotExitException} 10305 退款申请订单不存在
	 * @apiParam (错误码) {RefundOrderException} 10306 退款的订单必须是销售订单
	 * @apiParam (错误码) {OrderPayStateException} 10307 订单状态存在问题,不可退款
	 * @apiParam (错误码) {RefundOperationIntervalException} 10308 同一规格退款操作间隔须大于1分钟
	 * @apiParam (错误码) {TriggerPartyException} 10309 商品状态为不允许触发
	 * @apiParam (错误码) {MerchStateException} 10310 商品状态为不允许执行
	 * @apiParam (错误码) {MerchApplyNumException} 10311 商品当前申请量超过了可退量
	 * @apiParam (错误码) {QueryRefundRuleException} 10312 查询退款规则失败
	 * @apiParam (错误码) {QueryRefundRuleException} 10313 退款规则为空，不允许退款
	 * @apiParam (错误码) {VoucherNotFoundException} 10315 商品核销凭证异常
	 * @apiParam (错误码) {RefundDateNotInRangeException} 10316 当前产品不在可退时间范围内
	 * @apiParam (错误码) {CanNotRefundBeforeDateException} 10317 此产品前不可退
	 * @apiParam (错误码) {PartRefundException} 10318 只能对此订单进行整单退款
	 * @apiParam (错误码) {CanNotRefundAfterDateException} 10319 此产品后不可退
	 * @apiParam (错误码) {SettlementForceRefundValidatException} 10320 清结算验证强制退款异常
	 * @apiParam (错误码) {SettlementRefundFrozenException} 10321 清结算退款冻结异常
	 * @apiParam (错误码) {DockConfirmRefundException} 10314 对接确认退款异常
	 * @apiParam (错误码) {OrderNotExitException} 10305 退款申请订单为空
	 * @apiParam (错误码) {RefundFlowIsNoneException} 10323 没有对应的退款申请信息
	 * @apiParam (错误码) {RefundSignIsNoneException} 10324 退款申请唯一标识为空
	 * @apiParam (错误码) {CallPaymentRefundException} 10322 支付系统退款申请失败
	 * @apiParam (错误码) {PurchMerchNotFoundException} 10333 退款商品采购订单不存在
	 *
	 * @apiExample 影响数据库数据
	 *
	 *             表：t_order_merch
	 *             更新:refund_num,refund_amount,merch_state,is_refunding
	 *             表:t_order
	 *             更新:refund_num,checked_num,order_status,refund_amount
	 *             表:t_order_merchrefund_flow 插入:refund_id, order_id, merch_id,
	 *             refund_num, refund_price, refund_state,
	 *             refund_type,create_time, update_time, is_force, refund_state,
	 *             refund_audit_state, refund_rule_type 表:t_refund_apply_info
	 *             插入:apply_id, refund_id, applier_id, init_party, reason,
	 *             img_src
	 */
	public Result<Boolean> refundApply(RefundApplyReqModel reqModel, ServiceContext context);

}
