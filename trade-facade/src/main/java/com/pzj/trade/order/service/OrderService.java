package com.pzj.trade.order.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.order.entity.OrderResponse;
import com.pzj.trade.order.model.MultiOrderInModel;
import com.pzj.trade.order.model.OrderRemarkModel;
import com.pzj.trade.order.vo.AgentOrderVO;
import com.pzj.trade.order.vo.OrderRemarkVO;
import com.pzj.trade.order.vo.TradeOrderVO;

/**
 * 订单服务接口.
 * <ul>
 * <li>交易创建</li>
 * <li>交易取消</li>
 * <li>交易退款</li>
 * <li>交易完成</li>
 * </ul>
 * @author YRJ
 *
 */
public interface OrderService {

	@Deprecated
	Result<OrderResponse> createOrder(TradeOrderVO orderVO, ServiceContext context);

	/**
	 * @api {dubbo} com.pzj.trade.order.service.OrderService#createOrderRemark 添加订单备注
	 * @apiGroup 交易
	 * @apiName 添加订单备注
	 * @apiDescription 添加订单备注
	              修改的数据库表：trade库{t_order_remarks}
	               调用的接口:无
	 * @apiVersion 1.1.0
	 *
	 * @apiUse OrderRemarkVO
	 * @apiParam {com.pzj.trade.order.vo.OrderRemarkVO} OrderRemarkVO 备注对象
	 * @apiParamExample [{OrderRemarkVO}] [OrderRemarkVO]
	*  {
	      "order_id": "MF708212626",
	      "remark": "it\u0027s remark",
	      "operator_type": 1,
	      "operator_id": 123,
	      "operator_name": "123"
	    }
	 * @apiParam {com.pzj.trade.context.ServiceContext} context 环境信息
	 *
	 * @apiSuccess {com.pzj.framework.context.Result} Result 返回对象
	 * @apiUse Result_Success
	 *
	 * @apiSuccessExample [{success}] [返回成功示例]
	*  {
	      "errorCode": 10000,
	      "errorMsg": "ok"
	    }
	 *
	 */
	Result<Boolean> createOrderRemark(OrderRemarkVO orderRemarkVO, ServiceContext context);

	@Deprecated
	public Result<Boolean> updateAgentOrderId(AgentOrderVO agentOrderVO, ServiceContext context);

	public Result<Boolean> updateOrderRemark(OrderRemarkModel orderRemarkModel, ServiceContext context);

	/**
	 * @apiDefine TradeOrderVO TradeOrderVO
	 *
	 * @apiParam (TradeOrderVO)  {long} resellerId 分销商ID
	 * @apiParam (TradeOrderVO)  {long} payerId 支付者ID
	 * @apiParam (TradeOrderVO)  {long} operatorId 操作人ID
	 * @apiParam (TradeOrderVO)  {long} [travel] 旅行社ID
	 * @apiParam (TradeOrderVO)  {long} [travelDepartId] 旅行社部门
	 * @apiParam (TradeOrderVO)  {long} [guideId] 导游ID
	 * @apiParam (TradeOrderVO)  {long} [resellerAgentId] 分销端代理商
	 * @apiParam (TradeOrderVO)  {long} [supplierAgentId] 供应端代理商
	 * @apiParam (TradeOrderVO)  {int} salePort 销售端口（APP, OTA, 微店）必填
	 * @apiParam (TradeOrderVO)  {String} [contactee] 联系人
	 * @apiParam (TradeOrderVO)  {String} [contactMobile] 联系人电话
	 * @apiParam (TradeOrderVO)  {String} [idcard_no] 联系人身份证号
	 * @apiParam (TradeOrderVO)  {String} [remark] 订单备注信息
	 * @apiParam (TradeOrderVO)  {PurchaseProductVO} products 选购的商品必填
	 * @apiParam (TradeOrderVO)  {FilledModel} [filledModelList] 填单项列表
	 */
	/**
	 * @apiDefine PurchaseProductVO PurchaseProductVO
	 *
	 * @apiParam (PurchaseProductVO)  {long} productId 产品ID,必填
	 * @apiParam (PurchaseProductVO) {String} productName     商品名称,必填
	 * @apiParam (PurchaseProductVO) {boolean} isTicket     商品大类 是否为票，是为票，否为通用产品 必填,默认为false
	 * @apiParam (PurchaseProductVO) {String} product_varie  商品团散 团:1 散：0' 必填
	 * @apiParam (PurchaseProductVO)  {int} productNum  商品数量
	 * @apiParam (PurchaseProductVO)  {boolean} [combined]    是否为组合商品
	 * @apiParam (PurchaseProductVO)  {boolean} confirm 是否需要二次确认
	 * @apiParam (PurchaseProductVO)  {int} agent_flag  代下单标志：1:不需要代下单；2：需要代下单,默认为1
	 * @apiParam (PurchaseProductVO)  {double} [price]        结算价
	 * @apiParam (PurchaseProductVO)  {long} channelId  渠道ID，必填
	 * @apiParam (PurchaseProductVO)  {long} supplierId 供应商ID，必填
	 * @apiParam (PurchaseProductVO)  {int} product_type    产品类别必填
	 * @apiParam (PurchaseProductVO)  {Date} play_start_time    出游开始日期
	 * @apiParam (PurchaseProductVO)  {Date} play_end_time  出游结束日期
	 * @apiParam (PurchaseProductVO)  {long} voucherId  商品服务属性ID
	 * @apiParam (PurchaseProductVO)  {PurchaseProductVO} [products] 子商品列表
	 */
	/**
	 * @apiDefine FilledModel FilledModel
	 *
	 * @apiParam (FilledModel)  {String} group 组
	 * @apiParam (FilledModel) {String} attr_key     键
	 * @apiParam (FilledModel) {String} attr_value     值
	 */
	/**
	 * @apiDefine OrderResponse OrderResponse
	 *
	 * @apiParam (OrderResponse)  {String} orderId 订单ID
	 * @apiParam (OrderResponse) {double} totalAmount     订单总金额
	 * @apiParam (OrderResponse) {long} reseller_id     分销商ID
	 * @apiParam (OrderResponse) {long} supplier_id  供应商ID
	 */
	/**
	 * @apiDefine OrderRemarkVO OrderRemarkVO
	 *
	 * @apiParam (OrderRemarkVO)  {String} order_id 订单ID
	 * @apiParam (OrderRemarkVO) {String} remark     备注
	 * @apiParam (OrderRemarkVO) {int} operator_type     操作人类型：1: 下单者; 2: 客服
	 * @apiParam (OrderRemarkVO) {long} operator_id  操作者ID
	 * @apiParam (OrderRemarkVO) {String} operator_name  操作者名字
	 */

	/**
	 * @apiDefine AgentOrderVO AgentOrderVO
	 *
	 * @apiParam (Result)  {String} order_id 订单ID必填
	 * @apiParam (Result) {String} agent_order_id     第三方订单ID
	 * @apiParam (Result) {long} operator_id     操作者ID必填
	 * @apiParam (Result) {String} operator_reason     操作原因
	 * @apiParam (Result) {boolean} isAuto     是否自动,默认false
	 */

	/**
	 * @api {dubbo} com.pzj.trade.order.service.OrderService#createMultilevelOrder 创建多级订单
	 * @apiGroup 交易
	 * @apiName 创建多级订单2
	 * @apiDescription <p>创建多级订单</p>
	                  <p>修改的数据库表：trade库{t_order,t_order_merch,t_voucher_base,t_order_extend_attr,t_mftour_code,t_delivery_detail,t_order_remarks,t_order_strategy}<p>
	 * @apiVersion 1.1.0
	 *
	 * @apiUse MultiOrderInModel
	 * @apiUse MultiOrderProductModel
	 * @apiUse ContacteeModel
	 * @apiUse FilledModel
	 * @apiUse TouristModel
	 * @apiUse ServiceContext
	 * @apiParam {com.pzj.trade.order.model.MultiOrderInModel} MultiOrderInModel 下单对象
	 * @apiParamExample [{MultiOrderInModel}] [MultiOrderInModel]
	*  {
	    "resellerId": 2216619736565760,
	    "payerId": 2216619736565760,
	    "operatorId": 2216619736565760,
	    "travel": 0,
	    "travelDepartId": 0,
	    "guideId": 0,
	    "resellerAgentId": 0,
	    "supplierAgentId": 0,
	    "salePort": 4,
	    "contactMobile": "13717611243",
	    "remark": "下订单",
	    "products": [
	    {
	      "productId": 2216619736567543,
	      "isTicket": false,
	      "productNum": 3,
	      "combined": false,
	      "confirm": false,
	      "agent_flag": 0,
	      "price": 0.0,
	      "rId": 2216619736563727,
	      "supplierId": 2216619736565758
	    }
	    ]
	    }
	 *<b>返回值描述</b>
	 * @apiParam {com.pzj.trade.context.ServiceContext} context 环境信息
	 * @apiSuccess {com.pzj.trade.order.entity.OrderResponse} OrderResponse 下单返回对象
	 * @apiUse OrderResponse
	 *
	 * @apiSuccessExample [{success}] [返回成功示例]
	*  {"orderId":"MF123456789","totalAmount": 0.0,"reseller_id": 0,  "supplier_id": 0,"cancelTime":2017-05-22 17:16:47}
	*   <b>数据变更</b>
	*   <p>t_order_strategy表添加interval_day[解冻间隔天数:周期解冻默认-1；次日解冻1]字段</p>
	*   <p>t_order_merch表添加verification_type[核销方式 :1：按订单2：按规格3：按份数]字段</p>
	*   <p>添加了每个分数一个二维码和一个规格一个二维码，同一个身份证同一个检票点相同游玩日期内不能再次下单功能</p>
	*  <b>返回值描述</b>
	 * @apiError (错误码) {error} 14001 <p>创建订单参数为空 </p>
	 * <p>销售端口错误</p>
	 * <p> 创建订单参数错误</p>
	 * <p>参数中有重复身份证</p>
	 * <p>创建订单参数错误, 请选择购买的商品</p>
	 * <p>选购的商品数量错误, 请选择至少一个</p>
	 * <p>创建订单参数错误, 请指定联系人信息</p>
	 * <p>对不起，该联系人x天内,还可购买该产品x份,请重新选择购买数量</p>
	 * <p>身份证重复</p>
	 * <p>参数错误，订单消费凭证为身份证，但是参数没有传递身份证</p>
	 * <p>对不起，产品已下架，请购买其他产品</p>
	 * <p> 对不起，请选择你的出游日期</p>
	 * <p> 对不起，该产品已过期，请购买其他产品</p>
	 * <p>对不起，该日期商家未开放，请选择其他日期</p>
	 * <p>对不起，产品预定时间失效，请重新选择日期</p>
	 * <p>对不起，该产品要求最少购买x份，最多购买x份</p>
	 * <p>一证一票的购买数量和身份证数量不一致</p>
	 * <p>购买该产品的身份证存在还可以进行核销的相同检票点，不能进行下单</p>
	 * @apiError (错误码) {error} 10400 对不起，产品已下架，请购买其他产品
	 * @apiError (错误码) {error} 10500 产品ID获取产品规则模型为空
	 * <p>根据产品ID获取产品模型失败</p>
	 * @apiError (错误码) {error} 15001 查询场次信息报错
	 * <p>对不起,该产品已失效,暂不提供购买</p>
	 * <p>筛选商品的SKU模型失败</p>
	 * <p>该预约单状态不能出票</p>
	 * @apiError (错误码) {error} 10504 产品[是否需要出游日期]规则为空, 无法计算出游及游玩有效期
	 * 
	 */
	Result<OrderResponse> createMultilevelOrder(MultiOrderInModel orderVO, ServiceContext context);
}
