package com.pzj.trade.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.order.entity.AgentOrderResponse;
import com.pzj.trade.order.entity.OrderDetailResponse;
import com.pzj.trade.order.entity.OrderListResponse;
import com.pzj.trade.order.entity.OrderResponse;
import com.pzj.trade.order.entity.OrderStrategyResponse;
import com.pzj.trade.order.entity.RemarkResponse;
import com.pzj.trade.order.entity.ReportOrderResponse;
import com.pzj.trade.order.entity.SalesOrderResponse;
import com.pzj.trade.order.entity.SupplierOrderDetailResponse;
import com.pzj.trade.order.model.AppOrdersReqModel;
import com.pzj.trade.order.model.AppOrdersRespModel;
import com.pzj.trade.order.model.AppRebateOrdersReqModel;
import com.pzj.trade.order.model.AppRebateOrdersRespModel;
import com.pzj.trade.order.model.OperatorOrdersReqModel;
import com.pzj.trade.order.model.OrderMerchConfirmsReqModel;
import com.pzj.trade.order.model.OrderMerchConfirmsRespModel;
import com.pzj.trade.order.model.OrderRemarksPageReqModel;
import com.pzj.trade.order.model.PrintOrderDetailReqModel;
import com.pzj.trade.order.model.PrintOrderDetailRespModel;
import com.pzj.trade.order.model.ResellerOrderDetailReqModel;
import com.pzj.trade.order.model.ResellerOrderDetailRespModel;
import com.pzj.trade.order.model.ResellerOrdersReqModel;
import com.pzj.trade.order.model.ResellerOrdersRespModel;
import com.pzj.trade.order.model.SettlementOrdersReqModel;
import com.pzj.trade.order.model.SettlementOrdersRespModel;
import com.pzj.trade.order.model.SupplierOrderDetailReqModel;
import com.pzj.trade.order.model.SupplierOrdersReqModel;
import com.pzj.trade.order.model.SupplierOrdersRespModel;
import com.pzj.trade.order.model.TicketSellerOrderDetailReqModel;
import com.pzj.trade.order.model.TicketSellerOrdersRespModel;
import com.pzj.trade.order.vo.OrderDetailVO;
import com.pzj.trade.order.vo.OrderListVO;
import com.pzj.trade.order.vo.PlatformOrderListVO;
import com.pzj.trade.order.vo.PlatformRefundOrderListVO;
import com.pzj.trade.order.vo.ReportOrderVO;
import com.pzj.trade.order.vo.SalesOrderVO;

/**
 * 订单查询服务.
 * @author YRJ
 *
 */
public interface OrderQueryService {

	/**
	 * @api {dubbo} com.pzj.trade.order.service.OrderQueryService#queryOrdersBySupplier 供应端列表查询
	 * @apiGroup 订单查询
	 * @apiName 供应端列表查询
	 * @apiDescription <p>供应端列表查询</p>
	 * @apiVersion 1.1.0
	 * @apiUse SupplierOrdersReqModel
	 * @apiParam {com.pzj.trade.order.model.SupplierOrdersReqModel} reqModel 查询参数
	 * @apiParamExample[{SupplierOrdersReqModel}] [SupplierOrdersReqModel]
	*  {
	      "order_id": "MF708212626"
	    }
	 *
	 * @apiParam {ServiceContext} ServiceContext 环境信息
	 * @apiSuccess (返回-成功) {List[OrderListResponse]} data.records 返回订单列表
	 * @apiUse QueryResult_Success
	 * @apiUse SupplierOrdersRespModel
	 * @apiUse SupplierOrdersOutModel
	 * @apiUse SupplierMerchOutModel
	 * @apiUse RefundFlowResponse
	 * @apiSuccessExample [{success}] [返回成功示例]
	*  {
	      "errorCode": 10000,
	      "errorMsg": "ok",
	      "data": {
	        "total": 1,
	        "current_page": 1,
	        "total_page": 1,
	        "page_size": 10,
	        "records": [
	          {
	            "orderId": "MF1804630892",
	            "porderId": "MF116754593",
	            "orderStatus": 10,
	            "totalNum": 1,
	            "checkedNum": 0,
	            "refundNum": 0,
	            "payer_id": 2216619736763823,
	            "operator_id": 2216619736763823,
	            "supplier_id": 2216619741563734,
	            "totalAmount": 10.0,
	            "refundAmount": 0.0,
	            "createTime": "Nov 11, 2016 4:18:02 PM",
	            "contactee": "2423",
	            "contact_mobile": "17761300302",
	            "category": 1000,
	            "salesPort": 3,
	            "resellerId": 123456789,
	            "travel": 0,
	            "travel_depart_id": 0,
	            "guide_id": 0,
	            "need_confirm": 1,
	            "settlement_price": 0.0,
	      
	            "agent_flag": 1,
	            "all_merch_num": 1,
	            "all_amount": 10.0000,
	            "order_type": 1,
	            "is_cleaned": 0,
	            "merchs": [
	              {
	                "merchId": "P702590952",
	                "merchName": "拥有最牛逼的填单项产品",
	                "merchType": 1000,
	                "orderId": "MF1804630892",
	                "merchState": 0,
	                "productId": 796276713940135936,
	                "parentProductId": 0,
	                "channelId": 1,
	                "strategyId": 0,
	                "voucherId": 2216619741570955,
	                "totalNum": 1,
	                "checkNum": 0,
	                "refundNum": 0,
	                "price": 10.0,
	                "refundAmount": 0.0,
	                "orderStrategyList": [],
	                "settlement_price": 10.0,
	                "check_time": "Nov 11, 2016 4:18:36 PM",
	                "is_manual": 0,
	                "start_time": "Nov 23, 2016 12:00:00 AM",
	                "expire_time": "Nov 23, 2016 11:59:59 PM",
	                "visit_time": 0.0,
	                "refundInfos": [],
	                "vour_type": 1,
	                "is_overdue": 0,
	                "is_refunding": 0,
	                "is_cleaned": 0
	              }
	            ]
	          }
	        ]
	      }
	    }
	 */
	Result<SupplierOrdersRespModel> queryOrdersBySupplier(SupplierOrdersReqModel reqModel, ServiceContext context);

	/**
	 * 订单备注信息 分页查询
	 * @param orderVO
	 * @param context
	 * @return
	 */
	Result<QueryResult<RemarkResponse>> queryRemarkByOrder(OrderRemarksPageReqModel orderVO, ServiceContext context);

	/**SaaS供应订单列表查询*/
	Result<QueryResult<OrderListResponse>> queryOrdersBySaaSSupplier(PlatformOrderListVO orderVO, ServiceContext context);

	/**
	 * 完成支付的SaaS订单及退款单的支付明细、分账明细.
	 * @param reqModel
	 * @param context
	 * @return
	 */
	Result<SettlementOrdersRespModel> queryOrdersDetailBySettlement(SettlementOrdersReqModel reqModel, ServiceContext context);

	/**
	 * @api {dubbo} com.pzj.trade.order.service.OrderQueryService#queryOrdersByReseller 分销端列表查询
	 * @apiGroup 订单查询
	 * @apiName 分销端列表查询
	 * @apiDescription <p>分销端列表查询</p>
	 * @apiVersion 1.1.0
	 * @apiUse OrderListVO
	 * @apiParam {com.pzj.trade.order.vo.OrderListVO} orderVO 查询参数
	 * @apiParamExample[{OrderListVO}] [OrderListVO]
	*  {
	      "order_id": "MF708212626"
	    }
	 *
	 * @apiParam {ServiceContext} ServiceContext 环境信息
	 * @apiSuccess (返回-成功) {List[OrderListResponse]} data.records 返回订单列表
	 * @apiUse QueryResult_Success
	 * @apiUse OrderListResponse
	 * @apiUse MerchResponse
	 * @apiUse OrderStrategyResponse
	 * @apiUse RefundFlowResponse
	 * @apiSuccessExample [{success}] [返回成功示例]
	*  {
	      "errorCode": 10000,
	      "errorMsg": "ok",
	      "data": {
	        "total": 1,
	        "current_page": 1,
	        "total_page": 1,
	        "page_size": 10,
	        "records": [
	          {
	            "orderId": "MF116754593",
	            "porderId": "MF116754593",
	            "orderStatus": 10,
	            "totalNum": 1,
	            "checkedNum": 0,
	            "refundNum": 0,
	            "payer_id": 2216619736763823,
	            "operator_id": 2216619736763823,
	            "supplier_id": 2216619741563734,
	            "totalAmount": 10.0,
	            "refundAmount": 0.0,
	            "createTime": "Nov 11, 2016 4:18:02 PM",
	            "payTime": "Nov 11, 2016 4:18:36 PM",
	            "contactee": "2423",
	            "contact_mobile": "17761300302",
	            "category": 1000,
	            "salesPort": 3,
	            "resellerId": 2216619736763823,
	            "travel": 0,
	            "travel_depart_id": 0,
	            "guide_id": 0,
	            "need_confirm": 1,
	            "settlement_price": 0.0,
	       
	            "agent_flag": 1,
	            "all_merch_num": 1,
	            "all_amount": 10.0000,
	            "order_type": 2,
	            "is_cleaned": 0,
	            "is_direct":1,
	            "online_pay":1,
	            "is_dock":1,
	            "merchs": [
	              {
	                "merchId": "P1703731968",
	                "merchName": "拥有最牛逼的填单项产品",
	                "merchType": 1000,
	                "orderId": "MF116754593",
	                "merchState": 0,
	                "productId": 796276713940135936,
	                "parentProductId": 0,
	                "channelId": 2216619741563714,
	                "strategyId": 0,
	                "voucherId": 2216619741570955,
	                "totalNum": 1,
	                "checkNum": 0,
	                "refundNum": 0,
	                "price": 10.0,
	                "refundAmount": 0.0,
	                "orderStrategyList": [],
	                "settlement_price": 10.0,
	                "check_time": "Nov 11, 2016 4:18:36 PM",
	                "is_manual": 0,
	                "start_time": "Nov 23, 2016 12:00:00 AM",
	                "expire_time": "Nov 23, 2016 11:59:59 PM",
	                "visit_time": 0.0,
	                "refundInfos": [],
	                "vour_type": 1,
	                "is_overdue": 0,
	                "is_refunding": 0,
	                "is_cleaned": 0,
	                "merchCleanRelations":[]
	              }
	            ]
	          }
	        ]
	      }
	    }
	 */
	Result<QueryResult<OrderListResponse>> queryOrdersByReseller(OrderListVO orderVO, ServiceContext context);

	/**SaaS分销订单列表查询*/
	Result<ResellerOrdersRespModel> queryOrdersBySaaSReseller(ResellerOrdersReqModel orderVO, ServiceContext context);

	/**
	 * @api {dubbo} com.pzj.trade.order.service.OrderQueryService#queryOrdersOnPlatform 支撑平台列表查询
	 * @apiGroup 订单查询
	 * @apiName 支撑平台列表查询
	 * @apiDescription <p>支撑平台列表查询</p>
	 * @apiVersion 1.1.0
	 * @apiUse PlatformOrderListVO
	 * @apiParam {com.pzj.trade.order.vo.PlatformOrderListVO} orderVO 查询参数
	 * @apiParamExample[{PlatformOrderListVO}] [PlatformOrderListVO]
	*  {
	      "order_id": "MF708212626"
	    }
	 *
	 * @apiParam {ServiceContext} ServiceContext 环境信息
	 * @apiSuccess (返回-成功) {List[OrderListResponse]} data.records 返回订单列表
	 * @apiUse QueryResult_Success
	 * @apiUse OrderListResponse
	 * @apiUse MerchResponse
	 * @apiUse OrderStrategyResponse
	 * @apiUse RefundFlowResponse
	 * @apiSuccessExample [{success}] [返回成功示例]
	*  {
	      "errorCode": 10000,
	      "errorMsg": "ok",
	      "data": {
	        "total": 1,
	        "current_page": 1,
	        "total_page": 1,
	        "page_size": 10,
	        "records": [
	          {
	            "orderId": "MF116754593",
	            "porderId": "MF116754593",
	            "orderStatus": 10,
	            "totalNum": 1,
	            "checkedNum": 0,
	            "refundNum": 0,
	            "payer_id": 2216619736763823,
	            "operator_id": 2216619736763823,
	            "supplier_id": 2216619741563734,
	            "totalAmount": 10.0,
	            "refundAmount": 0.0,
	            "createTime": "Nov 11, 2016 4:18:02 PM",
	            "payTime": "Nov 11, 2016 4:18:36 PM",
	            "contactee": "2423",
	            "contact_mobile": "17761300302",
	            "category": 1000,
	            "salesPort": 3,
	            "resellerId": 2216619736763823,
	            "travel": 0,
	            "travel_depart_id": 0,
	            "guide_id": 0,
	            "need_confirm": 1,
	            "settlement_price": 0.0,

	            "agent_flag": 1,
	            "all_merch_num": 1,
	            "all_amount": 10.0000,
	            "order_type": 2,
	            "is_cleaned": 0,
	            "is_direct":1,
	            "online_pay":1,
	            "is_dock":1,
	            "merchs": [
	              {
	                "merchId": "P1703731968",
	                "merchName": "拥有填单项产品",
	                "merchType": 1000,
	                "orderId": "MF116754593",
	                "merchState": 0,
	                "productId": 796276713940135936,
	                "parentProductId": 0,
	                "channelId": 2216619741563714,
	                "strategyId": 0,
	                "voucherId": 2216619741570955,
	                "totalNum": 1,
	                "checkNum": 0,
	                "refundNum": 0,
	                "price": 10.0,
	                "refundAmount": 0.0,
	                "orderStrategyList": [],
	                "settlement_price": 10.0,
	                "check_time": "Nov 11, 2016 4:18:36 PM",
	                "is_manual": 0,
	                "start_time": "Nov 23, 2016 12:00:00 AM",
	                "expire_time": "Nov 23, 2016 11:59:59 PM",
	                "visit_time": 0.0,
	                "refundInfos": [],
	                "vour_type": 1,
	                "is_overdue": 0,
	                "is_refunding": 0,
	                "is_cleaned": 0
	              }
	            ]
	          }
	        ]
	      }
	    }
	 */
	Result<QueryResult<OrderListResponse>> queryOrdersOnPlatform(PlatformOrderListVO orderVO, ServiceContext context);

	/**
	 * @api {dubbo} com.pzj.trade.order.service.OrderQueryService#queryRefundAuditOrdersOnPlatform 退款审核订单列表查询
	 * @apiGroup 订单查询
	 * @apiName 退款审核订单列表查询
	 * @apiDescription <p>退款审核订单列表查询</p>
	 * @apiVersion 1.1.0
	 * @apiUse PlatformRefundOrderListVO
	 * @apiParam {com.pzj.trade.order.vo.PlatformRefundOrderListVO} orderVO 查询参数
	 * @apiParamExample[{PlatformRefundOrderListVO}] [PlatformRefundOrderListVO]
	*  {
	      "order_id": "MF708212626"
	    }
	 *
	 * @apiParam {ServiceContext} ServiceContext 环境信息
	 * @apiSuccess (返回-成功) {List[OrderListResponse]} data.records 返回订单列表
	 * @apiUse QueryResult_Success
	 * @apiUse OrderListResponse
	 * @apiUse MerchResponse
	 * @apiUse OrderStrategyResponse
	 * @apiUse RefundFlowResponse
	 * @apiSuccessExample [{success}] [返回成功示例]
	*  {
	      "errorCode": 10000,
	      "errorMsg": "ok",
	      "data": {
	        "total": 1,
	        "current_page": 1,
	        "total_page": 1,
	        "page_size": 10,
	        "records": [
	          {
	            "orderId": "MF116754593",
	            "porderId": "MF116754593",
	            "orderStatus": 10,
	            "totalNum": 1,
	            "checkedNum": 0,
	            "refundNum": 0,
	            "payer_id": 2216619736763823,
	            "operator_id": 2216619736763823,
	            "supplier_id": 2216619741563734,
	            "totalAmount": 10.0,
	            "refundAmount": 0.0,
	            "createTime": "Nov 11, 2016 4:18:02 PM",
	            "payTime": "Nov 11, 2016 4:18:36 PM",
	            "contactee": "2423",
	            "contact_mobile": "17761300302",
	            "category": 1000,
	            "salesPort": 3,
	            "resellerId": 2216619736763823,
	            "travel": 0,
	            "travel_depart_id": 0,
	            "guide_id": 0,
	            "need_confirm": 1,
	            "settlement_price": 0.0,
	        
	            "agent_flag": 1,
	            "all_merch_num": 1,
	            "all_amount": 10.0000,
	            "order_type": 2,
	            "is_cleaned": 0,
	            "is_direct": 1,
	            "online_pay": 1,
	            "merchs": [
	              {
	                "merchId": "P1703731968",
	                "merchName": "拥有填单项产品",
	                "merchType": 1000,
	                "orderId": "MF116754593",
	                "merchState": 0,
	                "productId": 796276713940135936,
	                "parentProductId": 0,
	                "channelId": 2216619741563714,
	                "strategyId": 0,
	                "voucherId": 2216619741570955,
	                "totalNum": 1,
	                "checkNum": 0,
	                "refundNum": 0,
	                "price": 10.0,
	                "refundAmount": 0.0,
	                "orderStrategyList": [],
	                "settlement_price": 10.0,
	                "check_time": "Nov 11, 2016 4:18:36 PM",
	                "is_manual": 0,
	                "start_time": "Nov 23, 2016 12:00:00 AM",
	                "expire_time": "Nov 23, 2016 11:59:59 PM",
	                "visit_time": 0.0,
	                "refundInfos": [],
	                "vour_type": 1,
	                "is_overdue": 0,
	                "is_refunding": 0,
	                "is_cleaned": 0
	              }
	            ]
	          }
	        ]
	      }
	    }
	 */
	Result<QueryResult<OrderListResponse>> queryRefundAuditOrdersOnPlatform(PlatformRefundOrderListVO orderVO,
			ServiceContext context);

	/**SaaS供应退款订单列表查询*/
	Result<QueryResult<OrderListResponse>> queryRefundAuditOrdersBySupllier(PlatformRefundOrderListVO orderVO,
			ServiceContext context);

	/**
	 * @api {dubbo} com.pzj.trade.order.service.OrderQueryService#queryForceRefundOrdersOnPlatform 支撑平台已强制退款列表查询
	 * @apiGroup 订单查询
	 * @apiName 支撑平台已强制退款列表查询
	 * @apiDescription <p>支撑平台已强制退款列表查询</p>
	 * @apiVersion 1.1.0
	 * @apiUse PlatformRefundOrderListVO
	 * @apiParam {com.pzj.trade.order.vo.PlatformRefundOrderListVO} orderVO 查询参数
	 * @apiParamExample[{PlatformRefundOrderListVO}] [PlatformRefundOrderListVO]
	*  {
	      "order_id": "MF708212626"
	    }
	 *
	 * @apiParam {ServiceContext} ServiceContext 环境信息
	 * @apiSuccess (返回-成功) {List[OrderListResponse]} data.records 返回订单列表
	 * @apiUse QueryResult_Success
	 * @apiUse OrderListResponse
	 * @apiUse MerchResponse
	 * @apiUse OrderStrategyResponse
	 * @apiUse RefundFlowResponse
	 * @apiSuccessExample [{success}] [返回成功示例]
	*  {
	      "errorCode": 10000,
	      "errorMsg": "ok",
	      "data": {
	        "total": 1,
	        "current_page": 1,
	        "total_page": 1,
	        "page_size": 10,
	        "records": [
	          {
	            "orderId": "MF116754593",
	            "porderId": "MF116754593",
	            "orderStatus": 10,
	            "totalNum": 1,
	            "checkedNum": 0,
	            "refundNum": 0,
	            "payer_id": 2216619736763823,
	            "operator_id": 2216619736763823,
	            "supplier_id": 2216619741563734,
	            "totalAmount": 10.0,
	            "refundAmount": 0.0,
	            "createTime": "Nov 11, 2016 4:18:02 PM",
	            "payTime": "Nov 11, 2016 4:18:36 PM",
	            "contactee": "2423",
	            "contact_mobile": "17761300302",
	            "category": 1000,
	            "salesPort": 3,
	            "resellerId": 2216619736763823,
	            "travel": 0,
	            "travel_depart_id": 0,
	            "guide_id": 0,
	            "need_confirm": 1,
	            "settlement_price": 0.0,
	            "agent_flag": 1,
	            "all_merch_num": 1,
	            "all_amount": 10.0000,
	            "order_type": 2,
	            "is_cleaned": 0,
	            "is_direct": 1,
	            "online_pay": 1,
	            "refund_state": 1,
	            "merchs": [
	              {
	                "merchId": "P1703731968",
	                "merchName": "拥有填单项产品",
	                "merchType": 1000,
	                "orderId": "MF116754593",
	                "merchState": 0,
	                "productId": 796276713940135936,
	                "parentProductId": 0,
	                "channelId": 2216619741563714,
	                "strategyId": 0,
	                "voucherId": 2216619741570955,
	                "totalNum": 1,
	                "checkNum": 0,
	                "refundNum": 0,
	                "price": 10.0,
	                "refundAmount": 0.0,
	                "orderStrategyList": [],
	                "settlement_price": 10.0,
	                "check_time": "Nov 11, 2016 4:18:36 PM",
	                "is_manual": 0,
	                "start_time": "Nov 23, 2016 12:00:00 AM",
	                "expire_time": "Nov 23, 2016 11:59:59 PM",
	                "visit_time": 0.0,
	                "refundInfos": [],
	                "vour_type": 1,
	                "is_overdue": 0,
	                "is_refunding": 0,
	                "is_cleaned": 0
	              }
	            ]
	          }
	        ]
	      }
	    }
	 */
	Result<QueryResult<OrderListResponse>> queryForceRefundOrdersOnPlatform(PlatformRefundOrderListVO orderVO,
			ServiceContext context);

	@Deprecated
	Result<ArrayList<OrderDetailResponse>> queryOrderDetailOnPlatform(String order_id, ServiceContext context);

	/**
	*
	* <p><b>接口概述:</b></p>
	* 查询订单详情. 返回该订单详细信息及对应的全部商品列表.
	
	/**
	 * @api {dubbo} com.pzj.trade.order.service.OrderQueryService#queryOrderDetail 查询订单详情
	 * @apiGroup 订单查询
	 * @apiName 查询订单详情
	 * @apiDescription <p>查询订单详情. 返回该订单详细信息及对应的全部商品列表</p>
	 * @apiVersion 1.1.0
	 * @apiUse OrderDetailVO
	 * @apiParam {com.pzj.trade.order.vo.OrderDetailVO} orderDetailVO 查询参数
	 * @apiParamExample[{PlatformOrderListVO}] [PlatformOrderListVO]
	*  {
	      "order_id": "MF708212626",
	      "call_port":"1"
	    }
	 *
	 * @apiParam {ServiceContext} ServiceContext 环境信息
	 * @apiSuccess (返回-成功) {com.pzj.trade.order.entity.OrderDetailResponse} OrderDetailResponse 返回订单详情对象
	 * @apiUse QueryResult_Success
	 * @apiUse OrderDetailResponse
	 * @apiUse MerchResponse
	 * @apiUse OrderStrategyResponse
	 * @apiUse RefundFlowResponse
	 * @apiUse RemarkResponse
	 * @apiUse FilledModel
	 * @apiUse DeliveryDetailModel
	 * @apiSuccessExample [{success}] [返回成功示例]
	*  {
	      "errorCode": 10000,
	      "errorMsg": "ok",
	      "data": {
	        "order_id": "MF116754593",
	        "transaction_id": "MF116754593",
	        "p_order_id": "MF116754593",
	        "payer_id": 2216619736763823,
	        "operator_id": 2216619736763823,
	        "supplier_id": 123456789,
	        "origin_supplier_id": 2216619741563734,
	        "supplier_agent_id": 0,
	        "reseller_id": 2216619736763823,
	        "travel": 0,
	        "travel_depart_id": 0,
	        "guide_id": 0,
	        "reseller_agent_id": 0,
	        "order_status": 10,
	        "confirm": 1,
	        "total_amount": 10.0,
	        "wshop_balance_amount": 0.0,
	        "app_balance_amount": 0.0,
	        "refund_amount": 0.0,
	        "total_num": 1,
	        "checked_num": 0,
	        "refund_num": 0,
	        "order_type": 2,
	        "sale_port": 3,
	        "sale_port_content": "PC分销端",
	        "create_time": "Nov 11, 2016 4:18:02 PM",
	        "pay_time": "Nov 11, 2016 4:18:36 PM",
	        "third_code": "NULL",
	        "third_pay_type": 0,
	        "category": 1000,
	        "contactee": "2423",
	        "contact_mobile": "17761300302",
	        "usedAmount": 0.0,
	        "channel_id": 2216619741563714,
	        "agent_flag": 1,
	        "remarks": [],
	        "settlement_price": 0.0,
	        "code_state": 0,
	        "merchs": [
	          {
	            "merchId": "P1703731968",
	            "merchName": "拥有最牛逼的填单项产品",
	            "merchType": 1000,
	            "orderId": "MF116754593",
	            "merchState": 0,
	            "merchStateMsg": "待消费",
	            "productId": 796276713940135936,
	            "parentProductId": 0,
	            "channelId": 2216619741563714,
	            "strategyId": 796274387420479489,
	            "voucherId": 2216619741570955,
	            "totalNum": 1,
	            "checkNum": 0,
	            "refundNum": 0,
	            "price": 10.0,
	            "refundAmount": 0.0,
	            "orderStrategyList": [
	              {
	                "orderId": "MF116754593",
	                "channelId": 2216619741563714,
	                "strategyId": 796274387420479489,
	                "discountAmount": 0.0,
	                "discountType": 0
	              }
	            ],
	            "update_time": "Nov 11, 2016 4:18:36 PM",
	            "settlement_price": 10.0,
	            "check_time": "Nov 11, 2016 4:18:36 PM",
	            "is_manual": 0,
	            "contact_mobile": "17761300302",
	            "start_time": "Nov 23, 2016 12:00:00 AM",
	            "expire_time": "Nov 23, 2016 11:59:59 PM",
	            "visit_time": 0.999988425925926,
	            "refundInfos": [],
	            "vour_type": 1,
	            "is_overdue": 0,
	            "is_refunding": 0,
	            "is_cleaned": 0
	          }
	        ],
	        "filledModelList": [
	          {
	            "group": "orderFilled",
	            "attr_key": "3",
	            "attr_value": "2016-11-21 1:20"
	          },
	          {
	            "group": "gainType",
	            "attr_key": "0",
	            "attr_value": "17761300302"
	          },
	          {
	            "group": "gainType",
	            "attr_key": "1",
	            "attr_value": "2423"
	          },
	          {
	            "group": "orderFilled",
	            "attr_key": "5",
	            "attr_value": "24234"
	          },
	          {
	            "group": "orderFilled",
	            "attr_key": "6",
	            "attr_value": "2432"
	          },
	          {
	            "group": "orderFilled",
	            "attr_key": "7",
	            "attr_value": ""
	          },
	          {
	            "group": "orderFilled",
	            "attr_key": "1",
	            "attr_value": "北京北京市西城区safasf"
	          },
	          {
	            "group": "orderFilled",
	            "attr_key": "2",
	            "attr_value": "北京北京市西城区safasf"
	          },
	          {
	            "group": "orderFilled",
	            "attr_key": "0",
	            "attr_value": "河北省保定市清苑县424234"
	          }
	        ]
	      }
	    }
	 *@apiErrorExample [{error}] [订单不存在]
	*  {
	      "errorCode": 14001,
	      "errorMsg": "订单不存在"
	    }
	  *@apiErrorExample [{error}] [参数有误]
	*  {
	      "errorCode": 14001,
	      "errorMsg": "请求参数为空, 无法获取到订单详细信息"
	    }
	 */
	Result<OrderDetailResponse> queryOrderDetail(OrderDetailVO orderDetailVO, ServiceContext context);

	/**SaaS供应订单详情接口*/
	Result<SupplierOrderDetailResponse> queryOrderDetailBySaaSSupplier(SupplierOrderDetailReqModel supplierOrderDetailReqModel,
			ServiceContext context);

	/**
	* @api {dubbo} com.pzj.trade.order.service.OrderQueryService#querySalesOrderDetail 查询销售订单详情(清结算调用)
	* @apiGroup 订单查询
	* @apiName 查询销售订单详情(清结算调用)
	* @apiDescription <p>查询销售订单详情. 当商品ID(merchId)非空时, 只返回该商品信息; 当商品ID(merchId)为空时, 返回该笔订单下的所有商品信息</p>
	* @apiVersion 1.1.0
	* @apiUse OrderDetailVO
	* @apiParam {com.pzj.trade.order.vo.SalesOrderVO} salesOrderVO 查询参数
	* @apiParamExample[{SalesOrderVO}] [SalesOrderVO]
	*  {
	     "order_id": "MF708212626",
	     "call_port":"1"
	   }
	*
	* @apiParam {ServiceContext} ServiceContext 环境信息
	* @apiSuccess (返回-成功) {com.pzj.trade.order.entity.SalesOrderResponse} SalesOrderResponse 返回订单详情对象
	* @apiUse QueryResult_Success
	* @apiUse SalesOrderResponse
	* @apiUse MerchResponse
	* @apiUse OrderStrategyResponse
	* @apiUse RefundFlowResponse
	* @apiSuccessExample [{success}] [返回成功示例]
	*  {
	      "errorCode": 10000,
	      "errorMsg": "ok",
	      "data": {
	        "orderId": "MF116754593",
	        "porderId": "MF116754593",
	        "transactionId": "MF116754593",
	        "orderStatus": "已支付",
	        "payerId": 2216619736763823,
	        "operatorId": 2216619736763823,
	        "travel": 0,
	        "travelDepartId": 0,
	        "guideId": 0,
	        "resellerId": 2216619736763823,
	        "originResellerId": 2216619736763823,
	        "resellerAgentId": 0,
	        "supplierId": 123456789,
	        "originSupplierId": 2216619741563734,
	        "supplierAgentId": 0,
	        "totalNum": 1,
	        "checkedNum": 0,
	        "refundNum": 0,
	        "totalAmount": 10.0,
	        "refundAmount": 0.0,
	        "confirm": false,
	        "channelType": 3,
	        "currency": 1,
	        "salePort": 3,
	        "salePortContent": "PC分销端",
	        "orderType": 2,
	        "deduction": 0,
	        "thirdCode": "NULL",
	        "createTime": "Nov 11, 2016 4:18:02 PM",
	        "merchs": [
	          {
	            "merchId": "P1703731968",
	            "merchType": 0,
	            "orderId": "MF116754593",
	            "merchState": 0,
	            "merchStateMsg": "待消费",
	            "productId": 796276713940135936,
	            "parentProductId": 0,
	            "channelId": 2216619741563714,
	            "strategyId": 796274387420479489,
	            "voucherId": 2216619741570955,
	            "totalNum": 1,
	            "checkNum": 0,
	            "refundNum": 0,
	            "price": 10.0,
	            "refundAmount": 0.0,
	            "orderStrategyList": [],
	            "settlement_price": 0.0,
	            "is_manual": 0,
	            "visit_time": 0.0,
	            "refundInfos": [],
	            "vour_type": 1,
	            "is_overdue": 0,
	            "is_refunding": 0,
	            "is_cleaned": 0
	          }
	        ]
	      }
	    }
	*@apiErrorExample [{error}] [订单不存在]
	*  {
	      "errorCode": 14001,
	      "errorMsg": "订单不存在"
	    }
	*/
	Result<SalesOrderResponse> querySalesOrderDetail(SalesOrderVO salesOrderVO, ServiceContext context);

	/**
	 * @api {dubbo} com.pzj.trade.order.service.OrderQueryService#queryOrderDetailByReseller 查询分销商订单详情
	 * @apiGroup 订单查询
	 * @apiName查询分销商订单详情
	 * @apiDescription <p>查询分销商订单详情.</p>
	 * @apiVersion 1.1.0
	 * @apiParam {String} order_id  订单ID
	 * @apiParam {ServiceContext} ServiceContext 环境信息
	 * @apiSuccess (返回-成功) {com.pzj.trade.order.model.ResellerOrderDetailReqModel} SupplierOrderDetailResponse 返回订单详情对象
	 * @apiUse QueryResult_Success
	 * @apiUse ResellerOrderDetailRespModel
	 * @apiUse ResellerMerchDetailRespModel
	 * @apiUse ResellerPriceDetailRespModel
	 * @apiSuccessExample [{success}] [返回成功示例]
	*  {
	      "errorCode": 10000,
	      "errorMsg": "ok",
	      "data": {
	        "total": 0,
	        "current_page": 0,
	        "total_page": 0,
	        "page_size": 20,
	        "records": [
	          {
	    
	            "resellerMerchDetailRespModels": [
	              {
	              }
	            ]
	          }
	        ]
	      }
	    }

	 */
	Result<ResellerOrderDetailRespModel> queryOrderDetailByReseller(ResellerOrderDetailReqModel resellerOrderDetailReqModel,
			ServiceContext context);

	/**
	 * @api {dubbo} com.pzj.trade.order.service.OrderQueryService#querySupplierOrderDetailByPlatform 查询供应商订单详情
	 * @apiGroup 订单查询
	 * @apiName 查询供应商订单详情
	 * @apiDescription <p>查询供应商订单详情. 根据销售订单查询对应的供应商订单的详细信息及对应的全部商品列表.</p>
	 * @apiVersion 1.1.0
	 * @apiParam {String} order_id  订单ID
	 * @apiParam {ServiceContext} ServiceContext 环境信息
	 * @apiSuccess (返回-成功) {com.pzj.trade.order.entity.SupplierOrderDetailResponse} SupplierOrderDetailResponse 返回订单详情对象
	 * @apiUse QueryResult_Success
	 * @apiUse SupplierOrderDetailResponse
	 * @apiUse MerchResponse
	 * @apiUse OrderStrategyResponse
	 * @apiUse RefundFlowResponse
	 * @apiSuccessExample [{success}] [返回成功示例]
	*  {
	      "errorCode": 10000,
	      "errorMsg": "ok",
	      "data": {
	        "total": 0,
	        "current_page": 0,
	        "total_page": 0,
	        "page_size": 20,
	        "records": [
	          {
	            "supplier_order_id": "MF1804630892",
	            "reseller_order_id": "MF116754593",
	            "order_status": 10,
	            "create_time": "Nov 11, 2016 4:18:02 PM",
	            "supplier_id": 2216619741563734,
	            "payer_id": 2216619736763823,
	            "third_pay_type": 0,
	            "total_amount": 10.0,
	            "refund_amount": 0.0,
	            "checked_num": 0,
	            "refund_num": 0,
	            "usedAmount": 0.0,
	            "merchs": [
	              {
	                "merchId": "P702590952",
	                "merchName": "拥有最牛逼的填单项产品",
	                "merchType": 1000,
	                "merchState": 0,
	                "productId": 796276713940135936,
	                "parentProductId": 0,
	                "channelId": 0,
	                "strategyId": 0,
	                "voucherId": 0,
	                "totalNum": 1,
	                "checkNum": 0,
	                "refundNum": 0,
	                "price": 10.0,
	                "refundAmount": 0.0,
	                "orderStrategyList": [],
	                "settlement_price": 0.0,
	                "is_manual": 0,
	                "visit_time": 0.0,
	                "refundInfos": [],
	                "vour_type": 0,
	                "is_overdue": 0,
	                "is_refunding": 0,
	                "is_cleaned": 0
	              }
	            ]
	          }
	        ]
	      }
	    }
	*@apiErrorExample [{error}] [订单不存在]
	*  {
	      "errorCode": 14001,
	      "errorMsg": "订单不存在子订单"
	    }
	 */
	Result<QueryResult<SupplierOrderDetailResponse>> querySupplierOrderDetailByPlatform(String order_id, ServiceContext context);

	/**
	* @api {dubbo} com.pzj.trade.order.service.OrderQueryService#getOrderStrategy 查询订单政策列表
	* @apiGroup 订单查询
	* @apiName 查询订单政策列表
	* @apiDescription <p>查询订单和商品对应的政策列表. 当商品ID(merchId)为空时, 包含该笔订单下所有商品的政策.</p>
	* @apiVersion 1.1.0
	* @apiParam {String} orderId 订单ID
	*@apiParam {String} [merchId] 商品ID
	* @apiParam {ServiceContext} ServiceContext 环境信息
	* @apiSuccess (返回-成功) {com.pzj.trade.order.entity.OrderStrategyResponse} OrderStrategyResponse 返回政策对象
	* @apiUse QueryResult_Success
	* @apiUse OrderStrategyResponse
	* @apiSuccessExample [{success}] [返回成功示例]
	*  {
	      "errorCode": 10000,
	      "errorMsg": "ok",
	      "data": [
	        {
	          "orderId": "MF116754593",
	          "merchId": "P1703731968",
	          "channelId": 0,
	          "strategyId": 0,
	          "discountAmount": 0.0,
	          "discountType": 1
	        }
	      ]
	    }
	 *@apiErrorExample [{error}] [订单不存在]
	*  {
	      "errorCode": 14001,
	      "errorMsg": "参数orderId不能为空"
	    }
	*/
	Result<ArrayList<OrderStrategyResponse>> getOrderStrategy(String orderId, String merchId, ServiceContext context);

	/**
	 * 根据商品id查询商品核销数据列表
	 * */

	Result<ArrayList<OrderMerchConfirmsRespModel>> getOrderMerchConfirms(OrderMerchConfirmsReqModel orderMerchConfirmsReqModel,
			ServiceContext context);

	/**
	 * @api {dubbo} com.pzj.trade.order.service.OrderQueryService#queryReportOrders 查询订单详情(报表专用)
	 * @apiGroup 订单查询
	 * @apiName 查询订单详情(报表专用)
	 * @apiDescription <p>查询订单详情(报表专用)</p>
	 * @apiVersion 1.1.0
	 * @apiUse ReportOrderVO
	 * @apiParam {com.pzj.trade.order.vo.ReportOrderVO} ReportOrderVO 查询参数
	 * @apiParamExample[{ReportOrderVO}] [ReportOrderVO]
	*  {
	      "resellerId": 2216619741564110,
	      "is_root": 0
	    }
	 *
	 * @apiParam {ServiceContext} ServiceContext 环境信息
	 * @apiSuccess (返回-成功) {com.pzj.trade.order.entity.ReportOrderResponse} ReportOrderResponse 返回订单详情对象
	 * @apiUse ReportOrderResponse
	 * @apiSuccessExample [{success}] [返回成功示例]
	*  [
	    {
	    "orderId": "MF1045852338",
	    "order_status": 1,
	    "sale_port": 7,
	    "third_code": "NULL",
	    "reseller_id": "2216619741564110",
	    "create_time": "Nov 9, 2016 5:56:18 PM",
	    "total_num": 1,
	    "checked_num": 0,
	    "refund_num": 0,
	    "total_amount": 10.0,
	    "refund_amount": 0.0,
	    "receivable": 10.0,
	    "third_amount": 0.0,
	    "balance_amount": 0.0,
	    "unreceive": 10.0,
	    "merch_id": "P1679639329",
	    "root_merch_id": "P1679639329",
	    "merch_name": "拥有最牛逼的填单项产品",
	    "merch_type": 1000,
	    "channelId": 2216619736563726,
	    "merch_checked_num": 0,
	    "merch_price": 10.0,
	    "settlement_price": 0.0,
	    "merch_refund_amount": 0.0,
	    "update_time": "Nov 9, 2016 5:56:18 PM"
	    },
	    {
	    "orderId": "MF1075371135",
	    "order_status": 20,
	    "sale_port": 7,
	    "third_code": "NULL",
	    "reseller_id": "2216619741564110",
	    "create_time": "Sep 2, 2016 1:26:16 AM",
	    "total_num": 3,
	    "checked_num": 0,
	    "refund_num": 0,
	    "total_amount": 0.04,
	    "refund_amount": 0.0,
	    "receivable": 0.04,
	    "third_amount": 0.0,
	    "balance_amount": 0.0,
	    "unreceive": 0.04,
	    "merch_id": "P1880529721",
	    "root_merch_id": "P1880529721",
	    "merch_name": "洱海一日游2一日游",
	    "merch_type": 1000,
	    "channelId": 2216619736563726,
	    "merch_checked_num": 0,
	    "merch_price": 0.01,
	    "settlement_price": 0.0,
	    "merch_refund_amount": 0.0,
	    "update_time": "Sep 2, 2016 1:26:16 AM"
	    }]
	 */
	List<ReportOrderResponse> queryReportOrders(ReportOrderVO queryMerchStrategysByMerchId, ServiceContext context);

	/**
	 * @api {dubbo} com.pzj.trade.order.service.OrderQueryService#queryMerchStrategysByMerchId 查询商品政策
	 * @apiGroup 订单查询
	 * @apiName 查询商品政策
	 * @apiDescription <p>根据商品Id列表查询子商品的政策ID---只针对merch表的直签联票</p>
	 * @apiVersion 1.1.0
	 * @apiParam {List[String]} merchList 查询参数
	 * @apiParam {ServiceContext} ServiceContext 环境信息
	 *
	 * @apiSuccessExample [{success}] [返回成功示例]
	*  {
	      "P595505068": {
	        "2216619741564557": 2216619741565039
	      }
	    }
	 */
	@Deprecated
	Map<String, Map<Long, Long>> queryMerchStrategysByMerchId(List<String> merchList, ServiceContext context);

	/**
	 * @api {dubbo} com.pzj.trade.order.service.OrderQueryService#queryAgentOrderByOrderId 查询代下单信息
	 * @apiGroup 订单查询
	 * @apiName 查询代下单信息
	 * @apiDescription <p>根据订单ID查询订单对应的代下单信息</p>
	 * @apiVersion 1.1.0
	 * @apiParam {String} order_id 查询参数
	 * @apiParam {ServiceContext} ServiceContext 环境信息
	 *
	 * @apiSuccess (返回-成功) {com.pzj.trade.order.entity.AgentOrderResponse} AgentOrderResponse 返回订单详情对象
	 * @apiUse QueryResult_Success
	 * @apiUse AgentOrderResponse
	 * @apiSuccessExample [{success}] [返回成功示例]
	*  {
	      "errorCode": 10000,
	      "errorMsg": "ok",
	      "data": {
	        "order_id": "MF1001272280",
	        "agent_order_id": "222bb,333",
	        "operator_id": 0,
	        "create_time": "Aug 5, 2016 11:01:55 AM",
	        "update_time": "Aug 5, 2016 11:01:55 AM"
	      }
	    }
	 */
	Result<AgentOrderResponse> queryAgentOrderByOrderId(String order_id, ServiceContext context);

	@Deprecated
	List<OrderResponse> queryOrdersByOrderId(String order_id, ServiceContext context);

	/**
	*
	* <p><b>接口概述:</b></p>
	*售票员订单列表查询
	* @api {dubbo} com.pzj.trade.order.service.OrderQueryService#queryOrdersByTicketSeller 售票员订单列表查询
	* @apiGroup 订单查询
	* @apiName 售票员订单列表查询
	* @apiDescription <p>售票员订单列表查询</p>
	* @apiVersion 2.2.0
	* @apiUse OperatorOrdersReqModel
	* @apiParam {com.pzj.trade.order.model.OperatorOrdersReqModel} OperatorOrdersReqModel 查询参数
	* @apiParamExample[{OperatorOrdersReqModel}] [OperatorOrdersReqModel]
	*  {
	   
	   }
	*
	* @apiParam {ServiceContext} ServiceContext 环境信息
	* @apiSuccess (返回-成功) {com.pzj.trade.order.model.TicketSellerOrdersRespModel} TicketSellerOrdersRespModel 返回订单详情对象
	* @apiUse QueryResult_Success
	* @apiUse TicketSellerOrdersRespModel
	* @apiUse OperatorMerchOutModel
	* @apiUse RemarkResponse
	* @apiSuccessExample [{success}] [返回成功示例]
	*  {
	     "errorCode": 10000,
	     "errorMsg": "ok",
	     "data": {
	        "remarks": [],
	        "OperatorMerchOutModel": [
	         {
	 
	         }
	       ],
	     }
	   }
	*/
	Result<TicketSellerOrdersRespModel> queryOrdersByTicketSeller(OperatorOrdersReqModel reqModel, ServiceContext context);

	/**
	*
	* <p><b>接口概述:</b></p>
	*app返利金额订单列表查询
	* @api {dubbo} com.pzj.trade.order.service.OrderQueryService#queryOrdersByAppRebate app返利金额订单列表查询
	* @apiGroup 订单查询
	* @apiName app返利金额订单列表查询
	* @apiDescription <p>app返利金额订单列表查询</p>
	* @apiVersion 2.2.0
	* @apiUse AppRebateOrdersReqModel
	* @apiParam {com.pzj.trade.order.model.AppRebateOrdersReqModel} AppRebateOrdersReqModel 查询参数
	* @apiParamExample[{AppRebateOrdersReqModel}] [AppRebateOrdersReqModel]
	*  {
	   
	   }
	*
	* @apiParam {ServiceContext} ServiceContext 环境信息
	* @apiSuccess (返回-成功) {com.pzj.trade.order.entity.AppRebateOrdersRespModel} AppRebateOrdersRespModel 返回订单详情对象
	* @apiUse QueryResult_Success
	* @apiUse AppRebateOrdersRespModel
	* @apiUse AppRebateMerchRespModel
	* @apiSuccessExample [{success}] [返回成功示例]
	*  {
	     "errorCode": 10000,
	     "errorMsg": "ok",
	     "data": {
	        "remarks": [],
	        "AppRebateMerchRespModel": [
	         {
	 
	         }
	       ],
	     }
	   }
	*/
	Result<AppRebateOrdersRespModel> queryOrdersByAppRebate(AppRebateOrdersReqModel reqModel, ServiceContext context);

	/**
	 * 根据订单id批量查询订单，appapi使用
	 * */

	Result<QueryResult<AppOrdersRespModel>> queryOrdersByApp(AppOrdersReqModel reqModel, ServiceContext context);

	/**
	*
	* <p><b>接口概述:</b></p>
	*订单详情-售票员
	* @api {dubbo} com.pzj.trade.order.service.OrderQueryService#queryOrderDetailByTicketSeller 订单详情-售票员
	* @apiGroup 订单查询
	* @apiName 订单详情-售票员
	* @apiDescription <p>售票员订单详情查询</p>
	* @apiVersion 2.2.0
	* @apiUse TicketSellerOrderDetailReqModel
	* @apiParam {com.pzj.trade.order.model.TicketSellerOrderDetailReqModel} TicketSellerOrderDetailReqModel 查询参数
	* @apiParamExample[{TicketSellerOrderDetailReqModel}] [TicketSellerOrderDetailReqModel]
	*  {
	     "order_id": "MF708212626",
	     "operator_id":"1122112"
	   }
	*
	* @apiParam {ServiceContext} ServiceContext 环境信息
	* @apiSuccess (返回-成功) {com.pzj.trade.order.entity.TicketSellerOrderDetailRespModel} TicketSellerOrderDetailRespModel 返回订单详情对象
	* @apiUse QueryResult_Success
	* @apiUse TicketSellerOrderDetailRespModel
	* @apiUse OperatorMerchOutModel
	* @apiUse RemarkResponse
	* @apiSuccessExample [{success}] [返回成功示例]
	*  {
	     "errorCode": 10000,
	     "errorMsg": "ok",
	     "data": {
	       "order_id": "MF116754593", 
	       "total_amount": 10.0,
	       "orderStatus": 1,
	       "totalAmount": 1,
	       "createTime": ,
	       "contactee": 1,
	       "contact_mobile": 13621125645,
	       "reseller_id": 2216619736763823,
	       "pay_time": 2216619736763823,
	       "pay_type": 1,
	       "reseller_mobile": 13621125645,
	       "travel": 2216619736763823,
	       "travel_mobile": 13621125645,
	       "travel_depart_id": 2216619736763823,
	       "travel_depart_mobile": 2216619736763823,
	       "guide_id": 2216619736763823,
	       "guide_mobile": 13621125645,
	       "operator_id": 2216619736763823,
	       "operator_id": 2216619736763823,
	       "operator_id": 2216619736763823,
	        "remarks": [],
	        "operatorMerchOutModel": [
	         {
	           "merchId": "P1703731968",
	           "merchName": "拥有最牛逼的填单项产品",
	           "merchType": 1000,
	           "productId": 796276713940135936,
	           "voucherId": 2216619741570955,
	           "totalNum": 1,
	           "start_time": "Nov 23, 2016 12:00:00 AM",
	           "expire_time": "Nov 23, 2016 12:00:00 AM",
	           "createTime": "Nov 23, 2016 11:59:59 PM",
	           "visit_time": 0.999988425925926,
	           "screening":11111,
	           "region":A,
	           "seatNumbers":A_12,
	           "merchState":2,
	           "isRefunding":1,
	           "check_amount":11,
	           "refund_amount":11,
	         }
	       ],
	     }
	   }
	*/

	Result<SupplierOrderDetailResponse> queryOrderDetailByTicketSeller(TicketSellerOrderDetailReqModel reqModel,
			ServiceContext context);

	/**
	*
	* <p><b>接口概述:</b></p>
	* 票据打印查询接口, 提供订单创建完毕后打印纸质票信息.
	
	/**
	 * @api {dubbo} com.pzj.trade.order.service.OrderQueryService#queryOrderDetailByPrint 票据打印查询接口
	 * @apiGroup 订单查询
	 * @apiName 票据打印查询接口
	 * @apiDescription <p>票据打印查询接口, 提供订单创建完毕后打印纸质票信息.</p>
	 * @apiVersion 2.2.0
	 * @apiUse PrintOrderDetailReqModel
	 * @apiParam {com.pzj.trade.order.model.PrintOrderDetailReqModel} PrintOrderDetailReqModel 查询参数
	 * @apiParamExample[{PrintOrderDetailReqModel}] [PrintOrderDetailReqModel]
	*  {
	      "orderId": "MF708212626",
	      "operatorId":"1122112"
	    }
	 *
	 * @apiParam {ServiceContext} ServiceContext 环境信息
	 * @apiSuccess (返回-成功) {com.pzj.trade.order.entity.PrintOrderDetailRespModel} PrintOrderDetailRespModel 返回订单详情对象
	 * @apiUse QueryResult_Success
	 * @apiUse PrintOrderDetailRespModel
	 * @apiUse PrintMerchDetailRespModel
	 * @apiUse RemarkResponse
	 * @apiSuccessExample [{success}] [返回成功示例]
	*  {
	      "errorCode": 10000,
	      "errorMsg": "ok",
	      "data": {
	        "orderId": "MF116754593", 
	        "totalAmount": 10.0,
	        "printMerchDetailRespModel": [
	          {
	            "merchId": "P1703731968",
	            "merchName": "拥有最牛逼的填单项产品",
	            "merchType": 1000,
	            "productId": 796276713940135936,
	            "totalNum": 1,
	             "travelTime": "Nov 23, 2016 12:00:00 AM",
	            "screening":11111,
	            "region":A,
	            "seatNumbers":A_12,
	          }
	        ],
	        
	      }
	    }
	 */
	Result<PrintOrderDetailRespModel> queryOrderDetailByPrint(PrintOrderDetailReqModel reqModel, ServiceContext context);

	/**
	 * @apiDefine ResellerOrderDetailRespModel ResellerOrderDetailRespModel
	 * @apiParam (ResellerOrderDetailRespModel)  {String} [orderId]  订单id
	 * @apiParam (ResellerOrderDetailRespModel)  {String} [orderId]  订单id
	 * @apiParam (ResellerOrderDetailRespModel)  {BigDecimal} [totalAmount]  订单金额
	 * @apiParam (ResellerOrderDetailRespModel)  {Date} [create_time] 下单时间 
	 * @apiParam (ResellerOrderDetailRespModel)  {Date} [pay_time] 支付时间
	 * @apiParam (ResellerOrderDetailRespModel)  {int} [pay_type] 支付方式
	 * @apiParam (ResellerOrderDetailRespModel)  {long} [reseller_id] 分销商id
	 * @apiParam (ResellerOrderDetailRespModel)  {long} [reseller_mobile] 分销商id
	 * @apiParam (ResellerOrderDetailRespModel)  {long} [channel_id] 渠道id
	 * @apiParam (ResellerOrderDetailRespModel)  {long} [travel] 旅行社
	 * @apiParam (ResellerOrderDetailRespModel)  {String} [travel_mobile] 旅行社手机
	 * @apiParam (ResellerOrderDetailRespModel)  {long} [travel_depart_id] 旅行社部门
	 * @apiParam (ResellerOrderDetailRespModel)  {String} [travel_depart_mobile] 旅行社部门手机
	 * @apiParam (ResellerOrderDetailRespModel)  {long} [guide_id] 导游
	 * @apiParam (ResellerOrderDetailRespModel)  {String} [guide_mobile] 导游手机
	 * @apiParam (ResellerOrderDetailRespModel)  {double} [check_amount] 已消费金额
	 * @apiParam (ResellerOrderDetailRespModel)  {double} [refund_amount] 已退金额
	 * @apiParam (ResellerOrderDetailRespModel)  {double} [rebate_amount] 后返
	 * @apiParam (ResellerOrderDetailRespModel)  {double} [operator_id] 操作者ID
	 * @apiParam (ResellerOrderDetailRespModel)  {long} [supplier_id] 供应商ID
	 * @apiParam (ResellerOrderDetailRespModel)  {int} [order_status] 订单状态
	 * @apiParam (ResellerOrderDetailRespModel)  {double} [total_amount] 订单总金额
	 * @apiParam (ResellerOrderDetailRespModel)  {int} [total_num] 总数量
	 * @apiParam (ResellerOrderDetailRespModel)  {int} [order_type] 订单类型
	 * @apiParam (ResellerOrderDetailRespModel)  {int} [sale_port] 销售端口
	 * @apiParam (ResellerOrderDetailRespModel)  {long} [pSupplierId] 初始供应商
	 * @apiParam (ResellerOrderDetailRespModel)  {String} [pSupplierMobile] 初始供应商手机
	 * @apiParam (ResellerOrderDetailRespModel)  {long} [supplierMobile] 我的供应商手机
	 * @apiParam (ResellerOrderDetailRespModel)  {String} [resellerMobile] 我的分销商手机
	 * @apiParam (ResellerOrderDetailRespModel)  {Date} [resellerPayTime] 分销支付时间
	 * @apiParam (ResellerOrderDetailRespModel)  {int} [resellerPayType] 采购支付方式
	 * @apiParam (ResellerOrderDetailRespModel)  {Date} [supplierPayTime] 采购支付时间
	 * @apiParam (ResellerOrderDetailRespModel)  {double} [supplierTotalAmount] 采购订单金额
	 * @apiParam (ResellerOrderDetailRespModel)  {double} [resellerTotalAmount] 分销订单金额
	 * @apiParam (ResellerOrderDetailRespModel)  {List[ResellerMerchDetailRespModel]} [resellerMerchDetailRespModel] 
	 * @apiParam (ResellerOrderDetailRespModel)  {List[ResellerPriceDetailRespModel]} [resellerPriceDetailRespModel] 
	 * 
	 * 
	 * 
	 */

	/**
	 * @apiDefine ResellerMerchDetailRespModel ResellerMerchDetailRespModel
	*  @apiParam (ResellerMerchDetailRespModel) {String} [merchId] 商品ID
	*  @apiParam (ResellerMerchDetailRespModel) {String} [merchName] 商品名
	*  @apiParam (ResellerMerchDetailRespModel) {int} [merchState] 商品状态
	*  @apiParam (ResellerMerchDetailRespModel) {long} [productId] 产品ID
	*  @apiParam (ResellerMerchDetailRespModel) {int} [totalNum] 总数量
	*  @apiParam (ResellerMerchDetailRespModel) {String} [orderId] 订单ID
	*  @apiParam (ResellerMerchDetailRespModel) {Date} [createTime] 创建时间
	*  @apiParam (ResellerMerchDetailRespModel) {int} [merchType] 商品类型
	*  @apiParam (ResellerMerchDetailRespModel) {String} [contactName] 姓名
	*  @apiParam (ResellerMerchDetailRespModel) {String} [contactMobile] 游客电话
	*  @apiParam (ResellerMerchDetailRespModel) {String} [voucherContent] 游客身份证号
	*  @apiParam (ResellerMerchDetailRespModel) {String} [isRefunding] 商品是否存在退款中的 0否  1是
	*  @apiParam (ResellerMerchDetailRespModel) {Date} [start_time] 出游/入住时间
	*  @apiParam (ResellerMerchDetailRespModel) {long} [voucherId] voucherID
	*  @apiParam (ResellerMerchDetailRespModel) {Date} [expire_time] 出游/入住结束时间
	*  @apiParam (ResellerMerchDetailRespModel) {String} [screening] 场次 
	*  @apiParam (ResellerMerchDetailRespModel) {String} [region] 区域
	*  @apiParam (ResellerMerchDetailRespModel) {String} [seatNumbers] 座位
	*  @apiParam (ResellerMerchDetailRespModel) {String} [qr_code] 二维码
	*  @apiParam (ResellerMerchDetailRespModel) {String} [contact_name] 游客姓名
	*  @apiParam (ResellerMerchDetailRespModel) {String} [contact_name] 游客姓名
	*  @apiParam (ResellerMerchDetailRespModel) {String} [voucher_content] 游客身份证号
	*  @apiParam (ResellerMerchDetailRespModel) {Date} [check_time] 核销\检票时间
	*  @apiParam (ResellerMerchDetailRespModel) {Date} [refund_time] 退款时间
	*/

	/**
	 * @apiDefine ResellerPriceDetailRespModel ResellerPriceDetailRespModel
	*  @apiParam (ResellerPriceDetailRespModel) {long} [reseller_id] 分销商id
	*  @apiParam (ResellerPriceDetailRespModel) {long} [supplier_id] 供应商id
	*  @apiParam (ResellerPriceDetailRespModel) {double} [price] 建议零售价
	*  @apiParam (ResellerPriceDetailRespModel) {double} [detailPrice] 结算价
	*  @apiParam (ResellerPriceDetailRespModel) {double} [rebate_amount] 后返
	*  @apiParam (ResellerPriceDetailRespModel) {int} [total_num] 总数量
	*  @apiParam (ResellerPriceDetailRespModel) {int} [rebate_object] 现返、周期返
	*/

	/**
	 * @apiDefine ResellerOrderDetailReqModel ResellerOrderDetailReqModel
	*  @apiParam (ResellerPriceDetailRespModel) {String} [order_id] 订单Id
	*  @apiParam (ResellerPriceDetailRespModel) {long} [reseller_id] 供应商id
	*/

	/**
	 * @apiDefine SupplierOrdersReqModel SupplierOrdersReqModel
	 * 
	 * @apiParam (SupplierOrdersReqModel)  {String} [order_id]  订单id
	 * @apiParam (SupplierOrdersReqModel)  {String} [supplier_id]   供应商ID
	 * @apiParam (SupplierOrdersReqModel)  {int} [order_status] 订单状态 
	 * @apiParam (SupplierOrdersReqModel)  {int} [direct] 是否直签
	 * @apiParam (SupplierOrdersReqModel)  {Date} [start_date] 下单开始日期
	 * @apiParam (SupplierOrdersReqModel)  {Date} [end_date] 下单结束日期
	 * @apiParam (SupplierOrdersReqModel)  {String} [merch_name] 商品名称
	 * @apiParam (SupplierOrdersReqModel)  {Integer} [merch_state] 订单商品状态.0:可消费; 1: 已消费; 2: 待退款; 3: 已退款; 4:待确认；5:已完成
	 * @apiParam (SupplierOrdersReqModel)  {int} [category] 订单类型. 景区.线路、旅拍、包车、班车、特产、导游、住宿、特色餐饮
	 * @apiParam (SupplierOrdersReqModel)  {Date} [visit_time] 出游/入住时间 
	 * @apiParam (SupplierOrdersReqModel)  {String} [contactee] 联系人 
	 * @apiParam (SupplierOrdersReqModel)  {String} [contact_mobile]  联系电话
	 * @apiParam (SupplierOrdersReqModel)  {long} [channel_id] 渠道 id
	 * @apiParam (SupplierOrdersReqModel)  {long} [reseller_id] 分销商Id
	 * @apiParam (SupplierOrdersReqModel)  {String} [product_varie] 团散
	 * @apiParam (SupplierOrdersReqModel)  {Integer} [isDirectSale] 是否直营 1是，0否
	 * @apiParam (SupplierOrdersReqModel)  {int} [sale_port] 销售端口
	 * @apiParam (SupplierOrdersReqModel)  {int} [pay_type] 支付方式
	 * @apiParam (SupplierOrdersReqModel)  {long} [guide_id] 导游ID
	 * @apiParam (SupplierOrdersReqModel)  {long} [product_id] 产品id
	 * @apiParam (SupplierOrdersReqModel)  {String} [guide_mobile] 导游手机
	 * @apiParam (SupplierOrdersReqModel)  {String} [qr_code] 二维码
	 * @apiParam (SupplierOrdersReqModel)  {String} [idcard_no] 联系人身份证号
	 * @apiParam (SupplierOrdersReqModel)  {String} [salePiont] 售票点
	 * @apiParam (SupplierOrdersReqModel)  {String} [salePersonId] 售票员id
	 * @apiParam (SupplierOrdersReqModel)  {Date} [create_time]  订单创建时间 
	 * @apiParam (SupplierOrdersReqModel)  {int} [current_page]  当前页码
	 * @apiParam (SupplierOrdersReqModel)  {int} [page_size]  每页显示的最大数量, 默认为10
	 * @apiParam (SupplierOrdersReqModel)  {int} [query_type]  查询类型:	supplierQuery订单查询列表（供应）supplierQueryDownLine订单列表查询(供应线下) supplierQueryOnLine订单列表查询(供应线上),suppplierQueryRefund退款订单列表查询（供应）,suppplierQueryIsForceRefund强制退款申请订单列表,suppplierQueryConfirm手动确认订单列表查询(供应),suppplierQueryCheck批量核销_检票订单列表（供应）
	 * 
	 */

	/**
	 * @apiDefine SupplierOrdersRespModel SupplierOrdersRespModel
	 * @apiParam (SupplierOrdersRespModel)  {int } [totalNum]  统计值: 总记录数
	 * @apiParam (SupplierOrdersRespModel)  {int } [totalMerchNum] 统计值: 购买的总规格数
	 * @apiParam (SupplierOrdersRespModel)  {int } [currentPage] 当前页码
	 * @apiParam (SupplierOrdersRespModel)  {int } [maxPage] 最大页
	 * @apiParam (SupplierOrdersRespModel)  {List[SupplierOrdersOutModel] } [orders] 
	 * 
	 */

	/**
	 * @apiDefine SupplierOrdersOutModel SupplierOrdersOutModel
	 * @apiParam (SupplierOrdersOutModel)  {String} [order_id]  订单id
	 * @apiParam (SupplierOrdersOutModel)  {String} [supplier_id]   供应商ID
	 * @apiParam (SupplierOrdersOutModel)  {int} [order_status] 订单状态 
	 * @apiParam (SupplierOrdersOutModel)  {Date} [create_time]  订单创建时间 
	 * @apiParam (SupplierOrdersOutModel)  {String} [porderId] 父订单id
	 * @apiParam (SupplierOrdersOutModel)  {BigDecimal} [totalAmount] 订单总金额
	 * @apiParam (SupplierOrdersOutModel)  {String} [contactee] 联系人
	 * @apiParam (SupplierOrdersOutModel)  {String} [contact_mobile] 联系电话
	 * @apiParam (SupplierOrdersOutModel)  {String} [is_direct] 是否直签，1-直签，2-非直签（即魔方)
	 * @apiParam (SupplierOrdersOutModel)  {int} [needConfirm] 是否需要确认1. 不需要确认 2. 需要确认 3. 已确认. 
	 * @apiParam (SupplierOrdersOutModel)  {long} [channel_id] 渠道 id
	 * @apiParam (SupplierOrdersOutModel)  {long} [reseller_id] 分销商Id
	 * @apiParam (SupplierOrdersOutModel)  {String} [reseller_name] 分销商名称
	 * @apiParam (SupplierOrdersOutModel)  {String} [channel_name] 渠道名称
	 * @apiParam (SupplierOrdersOutModel)  {List[SupplierMerchOutModel]} [merchs] 渠道名称
	 */

	/**
	 * @apiDefine SupplierMerchOutModel SupplierMerchOutModel
	*  @apiParam (SupplierMerchOutModel) {String} [merchId] 商品ID
	*  @apiParam (SupplierMerchOutModel) {String} [merchName] 商品名
	*  @apiParam (SupplierMerchOutModel) {int} [merchState] 商品状态
	*  @apiParam (SupplierMerchOutModel) {long} [productId] 产品ID
	*  @apiParam (SupplierMerchOutModel) {int} [totalNum] 总数量
	*  @apiParam (SupplierMerchOutModel) {String} [orderId] 订单ID
	*  @apiParam (SupplierMerchOutModel) {Date} [createTime] 创建时间
	*  @apiParam (SupplierMerchOutModel) {int} [merchType] 商品类型
	*  @apiParam (SupplierMerchOutModel) {String} [contactName] 姓名
	*  @apiParam (SupplierMerchOutModel) {String} [contactMobile] 游客电话
	*  @apiParam (SupplierMerchOutModel) {String} [voucherContent] 游客身份证号
	*  @apiParam (SupplierMerchOutModel) {String} [isRefunding] 商品是否存在退款中的 0否  1是
	*  @apiParam (SupplierMerchOutModel) {Date} [start_time] 出游/入住时间
	*  @apiParam (SupplierMerchOutModel) {long} [voucherId] voucherID
	*/

	/**
	 * @apiDefine OperatorOrdersReqModel OperatorOrdersReqModel
	 * @apiParam (OperatorOrdersReqModel)  {String} [order_id]  订单id
	 * @apiParam (OperatorOrdersReqModel)  {int} [order_status] 订单状态 
	 * @apiParam (OperatorOrdersReqModel)  {String} [merch_name] 产品名称
	 * @apiParam (OperatorOrdersReqModel)  {Integer} [merch_state] 订单商品状态.0:可消费; 1: 已消费; 2: 待退款; 3: 已退款; 4:待确认；5:已完成
	 * @apiParam (OperatorOrdersReqModel)  {int} [category] 订单类型. 景区.线路、旅拍、包车、班车、特产、导游、住宿、特色餐饮
	 * @apiParam (OperatorOrdersReqModel)  {Date} [visit_time] 出游/入住时间 
	 * @apiParam (OperatorOrdersReqModel)  {String} [contactee] 联系人 
	 * @apiParam (OperatorOrdersReqModel)  {String} [contact_mobile]  联系电话 
	 * @apiParam (OperatorOrdersReqModel)  {long} [reseller_id] 分销商Id
	 * @apiParam (OperatorOrdersReqModel)  {String} [product_varie] 团散
	 * @apiParam (OperatorOrdersReqModel)  {int} [pay_type] 支付方式
	 * @apiParam (OperatorOrdersReqModel)  {long} [guide_id] 导游ID
	 * @apiParam (OperatorOrdersReqModel)  {long} [product_id] 产品id
	 * @apiParam (OperatorOrdersReqModel)  {String} [guide_mobile] 导游手机
	 * @apiParam (OperatorOrdersReqModel)  {String} [qr_code] 二维码
	 * @apiParam (OperatorOrdersReqModel)  {String} [idcard_no] 联系人身份证号
	 * @apiParam (OperatorOrdersReqModel)  {Date} [create_time]  订单创建时间 
	 * @apiParam (OperatorOrdersReqModel)  {int} [current_page]  当前页码
	 * @apiParam (OperatorOrdersReqModel)  {int} [page_size]  每页显示的最大数量, 默认为10
	 */

	/**
	 * @apiDefine OperatorOrdersRespModel OperatorOrdersRespModel
	 * @apiParam (OperatorOrdersRespModel)  {int} [totalNum]  统计值: 总记录数 
	 * @apiParam (OperatorOrdersRespModel)  {double} [totalAmount] 统计值: 购买的总金额
	 * @apiParam (OperatorOrdersRespModel)  {int} [totalMerchNum] 统计值: 购买的总规格数
	 * @apiParam (OperatorOrdersRespModel)  {int} [currentPage] 当前页码
	 * @apiParam (OperatorOrdersRespModel)  {int} [maxPage] 最大页
	 * @apiParam (OperatorOrdersRespModel)  {List[OperatorOrdersOutModel]} [orders] 
	 */

	/**
	 * @apiDefine OperatorOrdersOutModel OperatorOrdersOutModel
	 * @apiParam (OperatorOrdersOutModel)  {String} [order_id]  订单id
	 * @apiParam (OperatorOrdersOutModel)  {String} [pOrderId] 父订单id
	 *@apiParam (OperatorOrdersOutModel)  {int} [orderStatus] 订单状态
	 *@apiParam (OperatorOrdersOutModel)  {BigDecimal} [totalAmount] 订单金额
	 * @apiParam (OperatorOrdersOutModel)  {Date} [createTime] 创建时间 
	 * @apiParam (OperatorOrdersOutModel)  {String} [contactee] 联系人
	 * @apiParam (OperatorOrdersOutModel)  {String} [contact_mobile] 联系人电话
	 * @apiParam (OperatorOrdersOutModel)  {long} [reseller_id] 分销商Id
	 * @apiParam (OperatorOrdersOutModel)  {long} [reseller_name] 分销商名称
	 * @apiParam (OperatorOrdersOutModel)  {List[OperatorMerchOutModel]} [merchs] 
	 */

	/**
	 * @apiDefine OperatorOrderDetailReqModel OperatorOrderDetailReqModel
	 * @apiParam (OperatorOrderDetailReqModel)  {String} [order_id]  订单id
	 * @apiParam (OperatorOrderDetailReqModel)  {String} [operator_id] 操作id
	 */

	/**
	 * @apiDefine PrintOrderDetailReqModel PrintOrderDetailReqModel
	 * @apiParam (PrintOrderDetailReqModel)  {String} [order_id]  订单id
	 * @apiParam (PrintOrderDetailReqModel)  {String} [operator_id] 操作id
	 */

	/**
	 * @apiDefine OperatorOrderDetailRespModel OperatorOrderDetailRespModel
	 * @apiParam (OperatorOrderDetailRespModel)  {String} [order_id]  订单id
	 * @apiParam (OperatorOrderDetailRespModel)  {List[RemarkResponse]} [remarks] 备注模型
	 * @apiParam (OperatorOrderDetailRespModel)  {String} [pOrderId] 父订单id
	 * @apiParam (OperatorOrderDetailRespModel)  {int} [orderStatus] 订单状态
	 * @apiParam (OperatorOrderDetailRespModel)  {Timestamp} [createTime] 创建时间 
	 * @apiParam (OperatorOrderDetailRespModel)  {String} [contactee] 联系人
	 * @apiParam (OperatorOrderDetailRespModel)  {String} [contact_mobile] 联系人电话
	 * @apiParam (OperatorOrderDetailRespModel)  {BigDecimal} [totalAmount] 订单金额
	 * @apiParam (OperatorOrderDetailRespModel)  {long} [reseller_id] 分销商Id
	 * @apiParam (OperatorOrderDetailRespModel)  {Date} [pay_time] 支付时间
	 * @apiParam (OperatorOrderDetailRespModel)  {int} [pay_type] 支付方式 
	 * @apiParam (OperatorOrderDetailRespModel)  {String} [reseller_mobile] 分销商手机
	 * @apiParam (OperatorOrderDetailRespModel)  {long} [travel] 旅行社
	 * @apiParam (OperatorOrderDetailRespModel)  {String} [travel_mobile] 旅行社手机
	 * @apiParam (OperatorOrderDetailRespModel)  {long} [travel_depart_id] 旅行社部门
	 * @apiParam (OperatorOrderDetailRespModel)  {String} [travel_depart_mobile] 旅行社部门手机
	 * @apiParam (OperatorOrderDetailRespModel)  {long} [guide_id] 导游id 
	 * @apiParam (OperatorOrderDetailRespModel)  {String} [guide_mobile] 导游手机 
	 * @apiParam (OperatorOrderDetailRespModel)  {List[OperatorMerchOutModel]} [operatorMerchOutModels] 
	 */
	/**
	 * @apiDefine OperatorMerchOutModel OperatorMerchOutModel
	 * @apiParam (OperatorMerchOutModel)  {String} [merchId]  商品id
	 * @apiParam (OperatorMerchOutModel)  {int} [merchState]  商品状态
	 * @apiParam (OperatorMerchOutModel)  {String} [merchName]  商品名称
	 * @apiParam (OperatorMerchOutModel)  {long} [productId]   产品id
	 * @apiParam (OperatorMerchOutModel)  {int} [totalNum]  商品数量
	 * @apiParam (OperatorMerchOutModel)  {Date} [createTime] 创建时间
	 * @apiParam (OperatorMerchOutModel)  {int} [merchType]  商品类型
	 * @apiParam (OperatorMerchOutModel)  {double} [totalAmount]  订单总金额
	 * @apiParam (OperatorMerchOutModel)  {int} [isRefunding]  是否退款中. 0是1否
	 * @apiParam (OperatorMerchOutModel)  {Date} [start_time]  出游/入住开始时间
	 * @apiParam (OperatorMerchOutModel)  {Date} [expire_time]  出游/入住结束时间 
	 * @apiParam (OperatorMerchOutModel)  {BigDecimal} [check_amount]  已消费金额
	 * @apiParam (OperatorMerchOutModel)  {BigDecimal} [refund_amount]  已退金额
	 * @apiParam (OperatorMerchOutModel)  {Date} [expire_time]  出游/入住结束时间 
	 */

	/**
	 * @apiDefine PrintOrderDetailRespModel PrintOrderDetailRespModel
	 * @apiParam (printOrderDetailReqModel)  {String} [orderId]  订单id
	 * @apiParam (printOrderDetailReqModel)  {BigDecimal} [totalAmount] 订单金额
	 * @apiParam (printOrderDetailReqModel)  {List[PrintMerchDetailRespModel]} [printMerchDetailRespModel] 打印商品模型
	 */

	/**
	 * @apiDefine PrintMerchDetailRespModel PrintMerchDetailRespModel
	 * @apiParam (PrintMerchDetailRespModel)  {String} [merchId]  商品id
	 * @apiParam (PrintMerchDetailRespModel)  {String} [merchName]  商品名称
	 * @apiParam (PrintMerchDetailRespModel)  {long} [productId]   产品id
	 * @apiParam (PrintMerchDetailRespModel)  {int} [totalNum]  商品数量
	 * @apiParam (PrintMerchDetailRespModel)  {double} [totalAmount]  订单总金额
	 * @apiParam (PrintMerchDetailRespModel)  {Date} [travelTime]  出游日期
	 * @apiParam (PrintMerchDetailRespModel)  {String} [screening] 场次
	 * @apiParam (PrintMerchDetailRespModel)  {String} [region]  区域
	 * @apiParam (PrintMerchDetailRespModel)  {String} [seatNumbers] 座位
	 * @apiParam (PrintMerchDetailRespModel)  {String} [qr_code]  二维码  
	 */
	/**
	 * @apiDefine OrderListVO OrderListVO
	 *
	 * @apiParam (OrderListVO)  {int} [current_page] 当前页码,默认为1
	 * @apiParam (OrderListVO)  {int} [page_index] 页号
	 * @apiParam (OrderListVO)  {int} [page_size] 每页显示的最大数量, 默认为10
	 * @apiParam (OrderListVO)  {String} [order_id] 销售订单ID
	 * @apiParam (OrderListVO)  {int} [order_status] 订单状态
	 * @apiParam (OrderListVO)  {List[Integer]} [order_status_list] 订单状态列表
	 * @apiParam (OrderListVO)  {Date} [start_date] 下单开始日期
	 * @apiParam (OrderListVO)  {Date} [end_date] 下单结束日期
	 * @apiParam (OrderListVO)  {Date} [confirm_date_start] 检票开始日期
	 * @apiParam (OrderListVO)  {Date} [confirm_date_end] 检票结束日期
	 * @apiParam (OrderListVO)  {String} [category] 产品线
	 * @apiParam (OrderListVO)  {int} [categoryList] 产品线列表
	 * @apiParam (OrderListVO)  {List[String]} [contactee] 订单联系人
	 * @apiParam (OrderListVO)  {String} [contact_mobile] 订单联系电话
	 * @apiParam (OrderListVO)  {String} [idcard_no] 订单联系人身份证号
	 * @apiParam (OrderListVO)  {String} [merch_name] 产品名称
	 * @apiParam (OrderListVO)  {long} [operator_id] 操作人ID
	 * @apiParam (OrderListVO)  {long} [channel_id] 渠道
	 * @apiParam (OrderListVO)  {String} [product_varie] 团散
	 * @apiParam (OrderListVO)  {int} [sale_port] 销售端口
	 * @apiParam (OrderListVO)  {List[Integer]} [sale_port_list] 销售端口列表
	 * @apiParam (OrderListVO)  {Integer} [merch_state] 商品状态(0:可消费; 1: 已消费; 2: 待退款; 3: 已退款; -1: 不可用;4:待确认；5：已完成)
	 * @apiParam (OrderListVO)  {int} [need_confirm] 是否需要确认. 1: 不需要确认; 2: 待确认;3:已确认(用于查询待确认订单)
	 * @apiParam (OrderListVO)  {long} [reseller_id] 分销商Id
	 * @apiParam (OrderListVO)  {List[Long]} [reseller_ids] 分销商Id列表，用于接收多个分销商ID
	 * @apiParam (OrderListVO)  {long} [supplier_id] 供应商ID
	 * @apiParam (OrderListVO)  {List[Long]} [supplier_ids] 供应商Id列表，用于接收多个供应商ID
	 * @apiParam (OrderListVO)  {List[Long]} [voucher_ids] voucherIDs
	 * @apiParam (OrderListVO)  {Date} [visit_start_time] 出游/入住开始时间
	 * @apiParam (OrderListVO)  {Date} [visit_end_time] 出游/入住结束时间
	 * @apiParam (OrderListVO)  {String} [qr_code] 二维码或者身份证号
	 * @apiParam (OrderListVO)  {String} [salePiont] 售票点
	 * @apiParam (OrderListVO)  {String} [salePersonId] 售票员id
	 * @apiParam (OrderListVO)  {String} [query_type] 查询类型:supplierQueryRefundAudit退款审核订单查询（供应）,supplierQueryRefundResult退款审核结果（供应）
	 * @apiParam (OrderListVO)  {int} [is_direct] 是否直签，1-直签，2-非直签（即魔方)
	 * @apiParam (OrderListVO)  {Integer} [online_pay] 直签是否需要线上支付
	 * 
	 */

	/**
	 * @apiDefine PlatformOrderListVO PlatformOrderListVO
	 *
	 *@apiParam (PlatformOrderListVO)  {List[String]} [reseller_order_ids] 魔方订单ID列表
	 *@apiParam (PlatformOrderListVO)  {List[String]} [supplier_order_ids] 供应商订单ID列表
	 *@apiParam (PlatformOrderListVO)  {List[Long]} [product_ids] 产品ID列表
	 *@apiParam (PlatformOrderListVO)  {long} [guide_id] 导游ID
	 *@apiParam (PlatformOrderListVO)  {int} [agent_flag] 代下单标志：1:不需要代下单；2：需要代下单；3已经代下单 ---可用于查询需要代下单订单
	 *@apiParam (PlatformOrderListVO)  {boolean} [sortDesc] 列表结算是否按照创建时间降序排列  ---待确认、代下单、手动清算的订单列表查询是正序查询，所以请设置为false
	 *@apiParam (PlatformOrderListVO)  {Integer} [is_manual] 是否可手动清算.0: 不可;1: 可手动。只有清算规则为固定时间规则时, 此值有效
	 * @apiParam (PlatformOrderListVO)  {int} [current_page] 当前页码,默认为1
	 * @apiParam (PlatformOrderListVO)  {int} [page_index] 页号
	 * @apiParam (PlatformOrderListVO)  {int} [page_size] 每页显示的最大数量, 默认为10
	 * @apiParam (PlatformOrderListVO)  {String} [order_id] 销售订单ID
	 * @apiParam (PlatformOrderListVO)  {int} [order_status] 订单状态
	 * @apiParam (PlatformOrderListVO)  {List[Integer]} [order_status_list] 订单状态列表
	 * @apiParam (PlatformOrderListVO)  {Date} [start_date] 下单开始日期
	 * @apiParam (PlatformOrderListVO)  {Date} [end_date] 下单结束日期
	 * @apiParam (PlatformOrderListVO)  {Date} [confirm_date_start] 检票开始日期
	 * @apiParam (PlatformOrderListVO)  {Date} [confirm_date_end] 检票结束日期
	 * @apiParam (PlatformOrderListVO)  {String} [category] 产品线
	 * @apiParam (PlatformOrderListVO)  {int} [categoryList] 产品线列表
	 * @apiParam (PlatformOrderListVO)  {List[String]} [contactee] 订单联系人
	 * @apiParam (PlatformOrderListVO)  {String} [contact_mobile] 订单联系电话
	 * @apiParam (PlatformOrderListVO)  {String} [idcard_no] 订单联系人身份证号
	 * @apiParam (PlatformOrderListVO)  {String} [merch_name] 产品名称
	 * @apiParam (PlatformOrderListVO)  {long} [operator_id] 操作人ID
	 * @apiParam (PlatformOrderListVO)  {long} [channel_id] 渠道
	 * @apiParam (PlatformOrderListVO)  {String} [product_varie] 团散
	 * @apiParam (PlatformOrderListVO)  {int} [sale_port] 销售端口
	 * @apiParam (PlatformOrderListVO)  {List[Integer]} [sale_port_list] 销售端口列表
	 * @apiParam (PlatformOrderListVO)  {Integer} [merch_state] 商品状态(0:可消费; 1: 已消费; 2: 待退款; 3: 已退款; -1: 不可用;4:待确认；5：已完成)
	 * @apiParam (PlatformOrderListVO)  {int} [need_confirm] 是否需要确认. 1: 不需要确认; 2: 待确认;3:已确认(用于查询待确认订单)
	 * @apiParam (PlatformOrderListVO)  {long} [reseller_id] 分销商Id
	 * @apiParam (PlatformOrderListVO)  {List[Long]} [reseller_ids] 分销商Id列表，用于接收多个分销商ID
	 * @apiParam (PlatformOrderListVO)  {long} [supplier_id] 供应商ID
	 * @apiParam (PlatformOrderListVO)  {List[Long]} [supplier_ids] 供应商Id列表，用于接收多个供应商ID
	 * @apiParam (PlatformOrderListVO)  {List[Long]} [voucher_ids] voucherIDs
	 * @apiParam (PlatformOrderListVO)  {Date} [visit_start_time] 出游/入住开始时间
	 * @apiParam (PlatformOrderListVO)  {Date} [visit_end_time] 出游/入住结束时间
	 * @apiParam (PlatformOrderListVO)  {String} [qr_code] 二维码或者身份证号
	 */

	/**
	 * @apiDefine PlatformRefundOrderListVO PlatformRefundOrderListVO
	 *
	 *@apiParam (PlatformRefundOrderListVO)  {List[String]} [reseller_order_ids] 魔方订单ID列表
	 *@apiParam (PlatformRefundOrderListVO)  {List[String]} [supplier_order_ids] 供应商订单ID列表
	 *@apiParam (PlatformRefundOrderListVO)  {List[Long]} [product_ids] 产品ID列表
	 *@apiParam (PlatformRefundOrderListVO)  {long} [guide_id] 导游ID
	 *@apiParam (PlatformRefundOrderListVO)  {int} [agent_flag] 代下单标志：1:不需要代下单；2：需要代下单；3已经代下单 ---可用于查询需要代下单订单
	 *@apiParam (PlatformRefundOrderListVO)  {boolean} [sortDesc] 列表结算是否按照创建时间降序排列  ---待确认、代下单、手动清算的订单列表查询是正序查询，所以请设置为false
	 *@apiParam (PlatformRefundOrderListVO)  {Integer} [is_manual] 是否可手动清算.0: 不可;1: 可手动。只有清算规则为固定时间规则时, 此值有效
	 * @apiParam (PlatformRefundOrderListVO)  {int} [current_page] 当前页码,默认为1
	 * @apiParam (PlatformRefundOrderListVO)  {int} [page_index] 页号
	 * @apiParam (PlatformRefundOrderListVO)  {int} [page_size] 每页显示的最大数量, 默认为10
	 * @apiParam (PlatformRefundOrderListVO)  {String} [order_id] 销售订单ID
	 * @apiParam (PlatformRefundOrderListVO)  {int} [order_status] 订单状态
	 * @apiParam (PlatformRefundOrderListVO)  {List[Integer]} [order_status_list] 订单状态列表
	 * @apiParam (PlatformRefundOrderListVO)  {Date} [start_date] 下单开始日期
	 * @apiParam (PlatformRefundOrderListVO)  {Date} [end_date] 下单结束日期
	 * @apiParam (PlatformRefundOrderListVO)  {Date} [confirm_date_start] 检票开始日期
	 * @apiParam (PlatformRefundOrderListVO)  {Date} [confirm_date_end] 检票结束日期
	 * @apiParam (PlatformRefundOrderListVO)  {String} [category] 产品线
	 * @apiParam (PlatformRefundOrderListVO)  {int} [categoryList] 产品线列表
	 * @apiParam (PlatformRefundOrderListVO)  {List[String]} [contactee] 订单联系人
	 * @apiParam (PlatformRefundOrderListVO)  {String} [contact_mobile] 订单联系电话
	 * @apiParam (PlatformRefundOrderListVO)  {String} [idcard_no] 订单联系人身份证号
	 * @apiParam (PlatformRefundOrderListVO)  {String} [merch_name] 产品名称
	 * @apiParam (PlatformRefundOrderListVO)  {long} [operator_id] 操作人ID
	 * @apiParam (PlatformRefundOrderListVO)  {long} [channel_id] 渠道
	 * @apiParam (PlatformRefundOrderListVO)  {String} [product_varie] 团散
	 * @apiParam (PlatformRefundOrderListVO)  {int} [sale_port] 销售端口
	 * @apiParam (PlatformRefundOrderListVO)  {List[Integer]} [sale_port_list] 销售端口列表
	 * @apiParam (PlatformRefundOrderListVO)  {Integer} [merch_state] 商品状态(0:可消费; 1: 已消费; 2: 待退款; 3: 已退款; -1: 不可用;4:待确认；5：已完成)
	 * @apiParam (PlatformRefundOrderListVO)  {int} [need_confirm] 是否需要确认. 1: 不需要确认; 2: 待确认;3:已确认(用于查询待确认订单)
	 * @apiParam (PlatformRefundOrderListVO)  {long} [reseller_id] 分销商Id
	 * @apiParam (PlatformRefundOrderListVO)  {List[Long]} [reseller_ids] 分销商Id列表，用于接收多个分销商ID
	 * @apiParam (PlatformRefundOrderListVO)  {long} [supplier_id] 供应商ID
	 * @apiParam (PlatformRefundOrderListVO)  {List[Long]} [supplier_ids] 供应商Id列表，用于接收多个供应商ID
	 * @apiParam (PlatformRefundOrderListVO)  {List[Long]} [voucher_ids] voucherIDs
	 * @apiParam (PlatformRefundOrderListVO)  {Date} [visit_start_time] 出游/入住开始时间
	 * @apiParam (PlatformRefundOrderListVO)  {Date} [visit_end_time] 出游/入住结束时间
	 * @apiParam (PlatformRefundOrderListVO)  {String} [qr_code] 二维码或者身份证号
	 * @apiParam (PlatformRefundOrderListVO)  {Integer} [refund_platform_isAudit] 0：强制退款，1已强制退款
	 * @apiParam (PlatformRefundOrderListVO)  {Integer} [refund_platform_state] 强制退款状态：5:进行中，6：成功，7：拒绝,不传值为全部
	 * @apiParam (PlatformRefundOrderListVO)  {Integer} [isDirectSale] 是否直营1、是 2、否
	 */

	/**
	 * @apiDefine OrderListResponse OrderListResponse
	*  @apiParam (OrderListResponse) {String} orderId [采购订单ID]
	*  @apiParam (OrderListResponse) {String} porderId [销售订单ID]
	*  @apiParam (OrderListResponse) {int} orderStatus [订单状态]
	*  @apiParam (OrderListResponse) {int} checkedNum [已检票数量]
	*  @apiParam (OrderListResponse) {int} refundNum [退款数量]
	*  @apiParam (OrderListResponse) {long} payer_id [支付者ID]
	*  @apiParam (OrderListResponse) {long} operator_id [操作人ID]
	*  @apiParam (OrderListResponse) {long} supplier_id [供应商ID]
	*  @apiParam (OrderListResponse) {BigDecimal} totalAmount [订单总金额]
	*  @apiParam (OrderListResponse) {BigDecimal} refundAmount [退款总金额]
	*  @apiParam (OrderListResponse) {Timestamp} createTime [创建时间]
	*  @apiParam (OrderListResponse) {Timestamp} payTime [支付时间]
	*  @apiParam (OrderListResponse) {Timestamp} confirmTime [关闭时间]
	*  @apiParam (OrderListResponse) {String} contactee [联系人]
	*  @apiParam (OrderListResponse) {String} contact_mobile [联系人手机号]
	*  @apiParam (OrderListResponse) {String} category [产品线]
	*  @apiParam (OrderListResponse) {int} salesPort [销售端口]
	*  @apiParam (OrderListResponse) {long} resellerId [分销商ID]
	*  @apiParam (OrderListResponse) {String} idcard_no [联系人身份证号]
	*  @apiParam (OrderListResponse) {long} travel [旅行社ID]
	*  @apiParam (OrderListResponse) {long} travel_depart_id [旅行社部门]
	*  @apiParam (OrderListResponse) {long} guide_id [导游ID]
	*  @apiParam (OrderListResponse) {long} need_confirm [是否需要二次确认 1: 不需要; 2: 需要]
	*  @apiParam (OrderListResponse) {long} settlement_price [结算价,非政策结算价，是购买价-后返之和]
	*  @apiParam (OrderListResponse) {int} agent_flag [代下单标志：1:不需要代下单；2：需要代下单；3已经代下单]
	*  @apiParam (OrderListResponse) {int} all_merch_num [本次查询所有订单的总商品数量，为了减少程序改动，放到查询结果的第一个记录里]
	*  @apiParam (OrderListResponse) {BigDecimal} all_amount [本次查询所有订单的总金额，为了减少程序改动，放到查询结果的第一个记录里]
	*  @apiParam (OrderListResponse) {int} order_type [订单类型:1: 魔方向供应商采购订单; 2: 分销商向魔方购买订单; 3: 直签的单产品]
	*  @apiParam (OrderListResponse) {int} is_cleaned [是否已清算 0：未清算，1：已清算]
	*  @apiParam (OrderListResponse) {List[MerchResponse]} merchs [订单商品列表]
	*  @apiParam (OrderListResponse) {int} is_direct [是否直签，1-直签，2-非直签（即魔方)]
	*  @apiParam (OrderListResponse) {int} online_pay [直签是否需要线上支付，1是，0否]
	*  @apiParam (OrderListResponse) {int} refund_state [强制退款状态(1，退款中 2，成功 3，失败)]
	*  @apiParam (OrderListResponse) {int} is_dock 是否对接商品0: 否; 1: 是]
	*/
	/**
	 * @apiDefine MerchResponse MerchResponse
	*  @apiParam (MerchResponse) {String} merchId [商品ID]
	*  @apiParam (MerchResponse) {String} merchName [商品名]
	*  @apiParam (MerchResponse) {int} merchType [商品类型]
	*  @apiParam (MerchResponse) {String} rootMerchId [销售商品ID]
	*  @apiParam (MerchResponse) {String} orderId [订单ID]
	*  @apiParam (MerchResponse) {int} merchState [商品状态]
	*  @apiParam (MerchResponse) {String} merchStateMsg [商品状态描述]
	*  @apiParam (MerchResponse) {long} productId [产品ID]
	*  @apiParam (MerchResponse) {long} parentProductId [父产品ID. 若是组合产品,这里存组合产品的ID]
	*  @apiParam (MerchResponse) {long} channelId [渠道ID]
	*  @apiParam (MerchResponse) {long} strategyId [政策ID]
	*  @apiParam (MerchResponse) {long} voucherId [voucherID]
	*  @apiParam (MerchResponse) {int} totalNum [总数量]
	*  @apiParam (MerchResponse) {int} checkNum [已检票数量]
	*  @apiParam (MerchResponse) {int} refundNum [退款数量]
	*  @apiParam (MerchResponse) {double} price [单价]
	*  @apiParam (MerchResponse) {double} refundAmount [退款金额]
	*  @apiParam (MerchResponse) {String} product_varie [产品团散：1团0散]
	*  @apiParam (MerchResponse) {List[OrderStrategyResponse]} orderStrategyList []
	*  @apiParam (MerchResponse) {Date} update_time [更新时间]
	*  @apiParam (MerchResponse) {double} settlement_price [结算价,非政策结算价，是购买价-后返之和]
	*  @apiParam (MerchResponse) {Date} check_time [消费/检票时间]
	*  @apiParam (MerchResponse) {Date} clear_time [清算时间]
	*  @apiParam (MerchResponse) {String} qr_code [二维码]
	*  @apiParam (MerchResponse) {int} is_manual [是否可手动清算.0: 不可;1: 可手动]
	*  @apiParam (MerchResponse) {String} contact_name [姓名]
	*  @apiParam (MerchResponse) {String} contact_mobile [游客电话]
	*  @apiParam (MerchResponse) {String} voucher_content [游客身份证号]
	*  @apiParam (MerchResponse) {Date} start_time [出游/入住时间]
	*  @apiParam (MerchResponse) {Date} expire_time [出游/入住结束时间 ]
	*  @apiParam (MerchResponse) {double} visit_time [游玩时长]
	*  @apiParam (MerchResponse) {String} screening [场次]
	*  @apiParam (MerchResponse) {String} region [区域]
	*  @apiParam (MerchResponse) {String} seatNumbers [座位]
	*  @apiParam (MerchResponse) {List[RefundFlowResponse]} refundInfos [退款信息对象]
	*  @apiParam (MerchResponse) {String} vour_type [凭证类型. 0: 未凭证; 1: 联系人信息; 2: 魔方码]
	*  @apiParam (MerchResponse) {String} is_overdue [是否逾期核销(设置， 默认0：不是逾期，1是逾期]
	*  @apiParam (MerchResponse) {String} is_refunding [商品是否存在退款中的 0否  1是]
	*  @apiParam (MerchResponse) {String} is_cleaned [是否已清算 0：未清算，1：已清算]
	*  @apiParam (MerchResponse) {List[MerchCleanRelationResponse]} merchCleanRelations [订单商品清算关系信息]
	*  @apiParam (MerchResponse) {double} detailPrice [结算价]
	*  @apiParam (MerchResponse) {double} rebate_amount [后返金额]
	*  @apiParam (MerchResponse) {double} rebate_object [现返、周期返] 
	*  
	*/

	/**
	 * @apiDefine MerchCleanRelationResponse MerchCleanRelationResponse
	*  @apiParam (MerchCleanRelationResponse) {int} clean_state [清算状态. 0: 未清算; 1: 已清算; 2: 清算失败; 3：冻结(强制退款,退已经核销的商品,在退款申请事触发）]
	*  @apiParam (MerchCleanRelationResponse) {int} normal_clean_num [正常清算数量]
	*  @apiParam (MerchCleanRelationResponse) {int} overdue_clean_num [逾期清算数量]
	*  @apiParam (MerchCleanRelationResponse) {int} is_minus_clean [是否是为负数的结算单 0否（默认）1是，强制退款生成]
	*  @apiParam (MerchCleanRelationResponse) {int} refund_clean_num [退款清算数量]
	*  @apiParam (MerchCleanRelationResponse) {double} normal_clean_amount [正常结算金额]
	*  @apiParam (MerchCleanRelationResponse) {double} overdue_clean_amount [逾期结算金额]
	*  @apiParam (MerchCleanRelationResponse) {double} refund_clean_amount [退款结算金额]
	*  @apiParam (MerchCleanRelationResponse) {double} clean_type [清算类别. 1: 正常清算; 2: 逾期清算]
	*
	*/
	/**
	 * @apiDefine OrderStrategyResponse OrderStrategyResponse
	*  @apiParam (OrderStrategyResponse) {String} orderId [订单ID]
	*  @apiParam (OrderStrategyResponse) {String} merchId [商品ID]
	*  @apiParam (OrderStrategyResponse) {long} channelId [渠道ID]
	*  @apiParam (OrderStrategyResponse) {long} strategyId [政策ID]
	*  @apiParam (OrderStrategyResponse) {double} discountAmount [优惠金额]
	*  @apiParam (OrderStrategyResponse) {int} discountType [优惠类型]
	*  @apiParam (OrderStrategyResponse) {String} remark [优惠的说明]
	*
	*/
	/**
	 * @apiDefine RefundFlowResponse RefundFlowResponse
	*  @apiParam (RefundFlowResponse) {String} refund_id [退款单号]
	*  @apiParam (RefundFlowResponse) {String} merch_name [商品名]
	*  @apiParam (RefundFlowResponse) {String} order_id [订单ID]
	*  @apiParam (RefundFlowResponse) {String} merchandise_id [商品ID]
	*  @apiParam (RefundFlowResponse) {long} strategy_id [政策ID]
	*  @apiParam (RefundFlowResponse) {Integer} refund_num [退款数量 ]
	*  @apiParam (RefundFlowResponse) {Double} refund_price [退款价格]
	*  @apiParam (RefundFlowResponse) {Integer} refund_state [退款状态（1 退款中  2退款成功 3 退款失败 )]
	*  @apiParam (RefundFlowResponse) {Integer} refund_type [退款类型 1供应商 2 分销商]
	*  @apiParam (RefundFlowResponse) {Double} amount [退款总额]
	*  @apiParam (RefundFlowResponse) {Date} update_time [更新时间]
	*  @apiParam (RefundFlowResponse) {Date} create_time [创建时间]
	*/

	/**
	 * @apiDefine OrderDetailVO OrderDetailVO
	 * 订单详情查询对象
	*  @apiParam (OrderDetailVO) {String} [order_id] [订单ID]
	*  @apiParam (OrderDetailVO) {int} [call_port] [调用端口:供应商调用为1，分销商调用为2，支撑平台调用为3]
	*  @apiParam (OrderDetailVO) {String} [query_type] 查询类型:supplierDetail订单详情查询(供应),supplierDetailDownLine订单详情详情查询(供应线下),supplierDetailOnline订单详情查询(供应线上),supplierDetailRefund退款详情查询（供应）,supplierDetailConfirm手动确认订单详情查询（供应）,supplierDetailIsForceRefund强制退款申请订单详情查询（供应）,suppplierDetailCheck批量核销_检票订单详情查询（供应）,suppplierDetailRefundAudit退款审核订单详情查询（供应）,suppplierRefundResult退款审核结果详情查询（供应）,supplierQueryRefundAudit退款审核订单查询（供应）
	*  
	*  
	*/

	/**
	 * @apiDefine OrderDetailResponse OrderDetailResponse
	 * 订单详情返回对象
	*  @apiParam (OrderDetailResponse) {String} order_id [订单ID]
	*  @apiParam (OrderDetailResponse) {String} transaction_id [交易ID]
	*  @apiParam (OrderDetailResponse) {String} p_order_id [对应销售订单ID]
	*  @apiParam (OrderDetailResponse) {long} payer_id [订单支付者]
	*  @apiParam (OrderDetailResponse) {long} operator_id [操作者]
	*  @apiParam (OrderDetailResponse) {long} supplier_id [供应商ID]
	*  @apiParam (OrderDetailResponse) {long} origin_supplier_id [真实供应商ID]
	*  @apiParam (OrderDetailResponse) {long} supplier_agent_id [供应商代理商]
	*  @apiParam (OrderDetailResponse) {long} reseller_id [分销商ID]
	*  @apiParam (OrderDetailResponse) {long} travel [旅行社]
	*  @apiParam (OrderDetailResponse) {long} travel_depart_id [旅行社部门]
	*  @apiParam (OrderDetailResponse) {long} guide_id [导游ID]
	*  @apiParam (OrderDetailResponse) {long} reseller_agent_id [分销商代理商]
	*  @apiParam (OrderDetailResponse) {int} order_status [订单状态]
	*  @apiParam (OrderDetailResponse) {int} confirm [是否需要确认. 1: 不需要; 2: 需要;3:已确认]
	*  @apiParam (OrderDetailResponse) {double} total_amount [订单总金额]
	*  @apiParam (OrderDetailResponse) {double} refund_amount [已退款总金额]
	*  @apiParam (OrderDetailResponse) {int} total_num [订单包含的商品总数量]
	*  @apiParam (OrderDetailResponse) {int} checked_num [已确认的商品数量]
	*  @apiParam (OrderDetailResponse) {int} refund_num [已退款的商品数量]
	*  @apiParam (OrderDetailResponse) {int} order_type [订单类型1: 魔方向供应商采购订单; 2: 分销商向魔方购买订单; 3: 直签的单产品]
	*  @apiParam (OrderDetailResponse) {int} sale_port [销售端口]
	*  @apiParam (OrderDetailResponse) {String} sale_port_content [销售端口（APP, OTA, 微店）名称]
	*  @apiParam (OrderDetailResponse) {Date} create_time [创建时间]
	*  @apiParam (OrderDetailResponse) {Date} pay_time [支付时间]
	*  @apiParam (OrderDetailResponse) {String} third_code [第三方支付订单号]
	*  @apiParam (OrderDetailResponse) {int} third_pay_type [第三方支付类型:0-余额，1-支付宝，2-微信]
	*  @apiParam (OrderDetailResponse) {Date} confirm_time [单关闭时间]
	*  @apiParam (OrderDetailResponse) {int} category [订单类型]
	*  @apiParam (OrderDetailResponse) {String} contactee [联系人]
	*  @apiParam (OrderDetailResponse) {String} contact_mobile [联系电话]
	*  @apiParam (OrderDetailResponse) {String} idcard_no [联系人身份证]
	*  @apiParam (OrderDetailResponse) {double} usedAmount [已消费金额]
	*  @apiParam (OrderDetailResponse) {long} channel_id [渠道ID]
	*  @apiParam (OrderDetailResponse) {int} agent_flag [代下单标志：1:不需要代下单；2：需要代下单；3已经代下单]
	*  @apiParam (OrderDetailResponse) {List[RemarkResponse]} remarks [备注]
	*  @apiParam (OrderDetailResponse) {double} settlement_price [结算价,非政策结算价，是购买价-后返之和]
	*  @apiParam (OrderDetailResponse) {String} mftour_code [魔方码]
	*  @apiParam (OrderDetailResponse) {int} code_state [验证状态. 0:待验证; 1: 已验证; 2: 已过期 ]
	*  @apiParam (OrderDetailResponse) {List[MerchResponse]} merchs [商品对象列表]
	*  @apiParam (OrderDetailResponse) {List[FilledModel]} filledModelList [填单项列表]
	*  @apiParam (OrderDetailResponse) {DeliveryDetailModel} delivery_info [配送信息]
	*  @apiParam (OrderDetailResponse) {String} salePiont [售票点]
	*  @apiParam (OrderDetailResponse) {String} salePersonId [售票员]
	*/

	/**
	 * @apiDefine FilledModel
	*  @apiParam (FilledModel) {String} group [组]
	*  @apiParam (FilledModel) {String} attr_key [键]
	*  @apiParam (FilledModel) {String} attr_value [值]
	*/
	/**
	 * @apiDefine RemarkResponse
	 * 备注对象
	*  @apiParam (OrderDetailResponse) {long} remark_id [备注ID]
	*  @apiParam (OrderDetailResponse) {String} order_id [订单ID]
	*  @apiParam (OrderDetailResponse) {String} remark [备注内容]
	*  @apiParam (OrderDetailResponse) {int} operator_type [操作类型：1: 下单者; 2: 客服]
	*  @apiParam (OrderDetailResponse) {long} operator_id [操作人]
	*  @apiParam (OrderDetailResponse) {String} operator_name [操作人名]
	*  @apiParam (OrderDetailResponse) {Date} create_time [创建时间]
	*/

	/**
	 * @apiDefine DeliveryDetailModel
	*  @apiParam (DeliveryDetailModel) {String} merchId []
	*  @apiSuccess (DeliveryDetailModel) {String} orderID 采购订单ID
	 * @apiSuccess (DeliveryDetailModel) {Integer} deliveryWay 配送方式(1:上门自提, 2:快递配送)
	 * @apiSuccess (DeliveryDetailModel) {String} expressCompany 快递公司
	 * @apiSuccess (DeliveryDetailModel) {String} expressNO 快递单号
	*/
	/**
	 * @apiDefine SalesOrderVO
	*  @apiParam (SalesOrderVO) {String} order_id [订单ID]
	*  @apiSuccess (SalesOrderVO) {String} [merch_id] 商品ID
	 * @apiSuccess (SalesOrderVO) {int} order_type 订单类型：1采购 2销售
	*/

	/**
	 * @apiDefine SalesOrderResponse SalesOrderResponse
	 * 订单详情返回对象
	*  @apiParam (SalesOrderResponse) {String} orderId [订单ID]
	*  @apiParam (SalesOrderResponse) {String} porderId [对应销售订单ID]
	*  @apiParam (SalesOrderResponse) {String} transactionId [交易ID]
	*  @apiParam (SalesOrderResponse) {String} orderStatus [是否需要确认. 1: 不需要; 2: 需要;3:已确认]
	*  @apiParam (SalesOrderResponse) {long} payerId [订单支付者]
	*  @apiParam (SalesOrderResponse) {long} operatorId [操作者]
	*  @apiParam (OrderDetailResponse) {long} travel [旅行社]
	*  @apiParam (SalesOrderResponse) {long} guideId [导游ID]
	*  @apiParam (SalesOrderResponse) {long} resellerId [分销商ID]
	*  @apiParam (SalesOrderResponse) {String} reseller [分销商名称，空]
	*  @apiParam (SalesOrderResponse) {long} originResellerId [真实分销商ID]
	*  @apiParam (SalesOrderResponse) {long} originReseller [真实分销商名称，空]
	*  @apiParam (SalesOrderResponse) {long} resellerAgentId [分销商代理商ID]
	*  @apiParam (SalesOrderResponse) {long} resellerAgenter [分销商代理商]
	*  @apiParam (SalesOrderResponse) {long} supplierId [供应商ID]
	*  @apiParam (SalesOrderResponse) {long} supplier [供应商]
	*  @apiParam (SalesOrderResponse) {long} originSupplierId [真实供应商ID]
	*  @apiParam (SalesOrderResponse) {long} originSupplier [真实供应商]
	*  @apiParam (SalesOrderResponse) {long} supplierAgentId [供应商代理商ID]
	*  @apiParam (SalesOrderResponse) {long} supplierAgenter [供应商代理商]
	*  @apiParam (SalesOrderResponse) {int} totalNum [订单包含的商品总数量]
	*  @apiParam (SalesOrderResponse) {int} checkedNum [已确认的商品数量]
	*  @apiParam (SalesOrderResponse) {double} refundNum [已退款总金额]
	*  @apiParam (SalesOrderResponse) {double} totalAmount [订单总金额]
	*  @apiParam (SalesOrderResponse) {int} refundAmount [已退款的商品数量]
	*  @apiParam (SalesOrderResponse) {boolean} confirm [是否二次确认]
	*  @apiParam (SalesOrderResponse) {int} channelType [渠道类型. 1: 直签; 2: 直销; 3: 魔方分销]
	*  @apiParam (SalesOrderResponse) {int} currency [币种，默认为1]
	*  @apiParam (SalesOrderResponse) {int} salePort [销售端口]
	*  @apiParam (SalesOrderResponse) {String} salePortContent [销售端口（APP, OTA, 微店）名称]
	*  @apiParam (SalesOrderResponse) {int} orderType [订单类型1: 魔方向供应商采购订单; 2: 分销商向魔方购买订单; 3: 直签的单产品]
	*  @apiParam (SalesOrderResponse) {int} deduction 获取促销活动抵扣,空
	*  @apiParam (SalesOrderResponse) {String} thirdCode [第三方支付订单号]
	*  @apiParam (SalesOrderResponse) {Date} createTime [创建时间]
	*  @apiParam (SalesOrderResponse) {Date} confirmTime [单关闭时间]
	*  @apiParam (SalesOrderResponse) {List[MerchResponse]} merchs [商品对象列表]
	*/
	/**
	 * @apiDefine SupplierOrderDetailResponse
	*  @apiParam (SupplierOrderDetailResponse) {String} supplier_order_id [采购订单ID]
	*  @apiParam (SupplierOrderDetailResponse) {String} reseller_order_id [销售订单ID]
	*  @apiParam (SupplierOrderDetailResponse) {int} order_status [订单状态]
	*  @apiParam (SupplierOrderDetailResponse) {Date} create_time [创建时间]
	*  @apiParam (SupplierOrderDetailResponse) {long} supplier_id [供应商ID]
	*  @apiParam (SupplierOrderDetailResponse) {Date} pay_time [支付时间]
	*  @apiParam (SupplierOrderDetailResponse) {long} payer_id [订单支付者的资金帐号]
	*  @apiParam (SupplierOrderDetailResponse) {String} third_code [第三方支付订单号]
	*  @apiParam (SupplierOrderDetailResponse) {int} third_pay_type [第三方支付类型:0-余额，1-支付宝，2-微信]
	*  @apiParam (SupplierOrderDetailResponse) {double} refund_amount [已退款总金额]
	*  @apiParam (SupplierOrderDetailResponse) {int} checked_num [已确认的商品数量]
	*  @apiParam (SupplierOrderDetailResponse) {int} refund_num [已退款的商品数量]
	*  @apiParam (SupplierOrderDetailResponse) {double} usedAmount [已消费金额]
	*  @apiParam (SupplierOrderDetailResponse) {List[MerchResponse]} merchs [商品列表]
	*/

	/**
	 * @apiDefine ReportOrderVO ReportOrderVO
	*  @apiParam (ReportOrderVO) {Date} [start_date] [商品更新 开始时间]
	*  @apiParam (ReportOrderVO) {Date} [end_date] [商品更新 结束时间]
	*  @apiParam (ReportOrderVO) {long} resellerId [分销商Id]
	*  @apiParam (ReportOrderVO) {Integer} [merch_state] [商品状态：0：未检 1：已检]
	*  @apiParam (ReportOrderVO) {int} [is_root] [1:关联采购和销售merch 0:只返回销售merch]
	*/

	/**
	 * @apiDefine ReportOrderResponse ReportOrderResponse
	*  @apiParam (ReportOrderResponse) {String} orderId [订单ID]
	*  @apiParam (ReportOrderResponse) {int} order_status [订单检票状态]
	*  @apiParam (ReportOrderResponse) {int} sale_port [销售端口]
	*  @apiParam (ReportOrderResponse) {String} third_code [第三方支付编号]
	*  @apiParam (ReportOrderResponse) {String} reseller_id [分销商ID]
	*  @apiParam (ReportOrderResponse) {Date} create_time [创建时间]
	*  @apiParam (ReportOrderResponse) {Date} pay_time [支付时间]
	*  @apiParam (ReportOrderResponse) {int} total_num [商品总数量]
	*  @apiParam (ReportOrderResponse) {int} checked_num [已检票数量]
	*  @apiParam (ReportOrderResponse) {int} refund_num [退款数量]
	*  @apiParam (ReportOrderResponse) {double} total_amount [订单总金额]
	*  @apiParam (ReportOrderResponse) {double} refund_amount [退票金额]
	*  @apiParam (ReportOrderResponse) {double} receivable [应收 =订单总金额-退票金额]
	*  @apiParam (ReportOrderResponse) {double} third_amount [实收 根据订单id取pay_flow表中收款方法=!账扣（pay_type='4'),pay_amount的汇总]
	*  @apiParam (ReportOrderResponse) {double} balance_amount [帐扣 根据订单id取pay_flow表中收款方法=账扣（pay_type='4'),pay_amount的汇总]
	*  @apiParam (ReportOrderResponse) {double} unreceive 未收 =应收-实收-账扣
	*  @apiParam (ReportOrderResponse) {String} merch_id 票ID
	*  @apiParam (ReportOrderResponse) {String} root_merch_id 父商品ID
	*  @apiParam (ReportOrderResponse) {String} merch_name 产品名称
	*  @apiParam (ReportOrderResponse) {int} merch_type 产品类别
	*  @apiParam (ReportOrderResponse) {long} channelId 渠道ID
	*  @apiParam (ReportOrderResponse) {double} merch_checked_num 订单商品表检票数量
	*  @apiParam (ReportOrderResponse) {double} merch_price 订单商品表单价
	*  @apiParam (ReportOrderResponse) {double} settlement_price 采购价格
	*  @apiParam (ReportOrderResponse) {double} merch_refund_amount 订单商品表退票金额
	*  @apiParam (ReportOrderResponse) {Date} update_time 更新时间
	*  @apiParam (ReportOrderResponse) {Date} check_time 检票时间
	*/
	/**
	 * @apiDefine RefundFlowVO RefundFlowVO
	*  @apiParam (RefundFlowVO) {String} order_id 订单ID
	*  @apiParam (RefundFlowVO) {String} [merch_id] 商品ID
	*  @apiParam (RefundFlowVO) {String} refund_type 订单类型：1采购 2销售
	*
	*/

	/**
	 * @apiDefine RefundFlowResponse RefundFlowResponse
	 * 退款流水记录
	*  @apiParam (RefundFlowResponse) {String} refund_id 退款单号
	*  @apiParam (RefundFlowResponse) {String} merch_name 商品名
	*  @apiParam (RefundFlowResponse) {String} order_id 订单ID
	*  @apiParam (RefundFlowResponse) {String} merchandise_id 商品ID
	*  @apiParam (RefundFlowResponse) {long} strategy_id 政策ID
	*  @apiParam (RefundFlowResponse) {Integer} refund_num 退款数量
	*  @apiParam (RefundFlowResponse) {double} refund_price 退款价格
	*  @apiParam (RefundFlowResponse) {Integer} refund_state 退款状态（1 退款中  2退款成功 3 退款失败 ）
	*  @apiParam (RefundFlowResponse) {Integer} refund_type 退款类型 1供应商 2 分销商
	*  @apiParam (RefundFlowResponse) {double} amount 退款总额
	*  @apiParam (RefundFlowResponse) {Date} update_time 更新时间
	*  @apiParam (RefundFlowResponse) {Date} create_time 创建时间
	*/
	/**
	 * @apiDefine AgentOrderResponse AgentOrderResponse
	 * 代下单信息
	*  @apiParam (AgentOrderResponse) {String} order_id 销售订单ID
	*  @apiParam (AgentOrderResponse) {String} agent_order_id 代下单订单号
	*  @apiParam (AgentOrderResponse) {long} operator_id 操作人
	*  @apiParam (AgentOrderResponse) {String} operator_reason 操作原因
	*  @apiParam (AgentOrderResponse) {Date} create_time 创建时间
	*  @apiParam (AgentOrderResponse) {Date} update_time 更新时间
	*
	*/
	/**
	 * @apiDefine OrderResponse OrderResponse
	*  @apiParam (OrderResponse) {String} orderId 订单ID
	*  @apiParam (OrderResponse) {double} totalAmount 订单总金额
	*  @apiParam (OrderResponse) {long} reseller_id 分销商ID
	*  @apiParam (OrderResponse) {long} supplier_id 供应商ID
	*
	*/
	/**
	 * @apiDefine OrderLimitVO OrderLimitVO
	*  @apiParam (OrderLimitVO) {String} contact_mobile 领票人电话
	*  @apiParam (OrderLimitVO) {double} idcard_no 领票人身份证号
	*  @apiParam (OrderLimitVO) {long} product_id 产品ID
	*  @apiParam (OrderLimitVO) {Date} start_time 下单时间起
	*  @apiParam (OrderLimitVO) {Date} end_time 下单时间止
	*/

	/**
	 * @apiDefine AppRebateOrdersReqModel AppRebateOrdersReqModel
	*  @apiParam (AppRebateOrdersReqModel) {int} rebateFormtype 返利来源：1微店返利、0卖油翁返利
	*  @apiParam (AppRebateOrdersReqModel) {long} resellerId 分销商id
	*  @apiParam (AppRebateOrdersReqModel) {Date} createStartTime 订单创建开始时间*
	*  @apiParam (AppRebateOrdersReqModel) {Date} createEndTime 订单创建结束时间
	*  @apiParam (AppRebateOrdersReqModel) {int} rebateState 返利状态：0待返，1已返，2其他
	*/
	/***/

	/**
	 * @apiDefine AppRebateOrdersRespModel AppRebateOrdersRespModel
	*  @apiParam (AppRebateOrdersRespModel) {String} orderId 订单编号
	*  @apiParam (AppRebateOrdersRespModel) {double} rebatePrice 返利金额
	*  	*  @apiParam (AppRebateOrdersRespModel) {int} rebateState 返利状态：0待返，1已返，2其他
	*  @apiParam (AppRebateOrdersRespModel) {Date} rebateTime 预计到账时间
	*  @apiParam (AppRebateOrdersRespModel) {Date} realRebateTime 实际到账时间
	*  @apiParam (AppRebateOrdersRespModel) {Date} orderCreateTime 下单时间
	*  @apiParam (AppRebateOrdersRespModel) {List[AppRebateMerchRespModel]} appRebateMerchRespModels 
	*/

	/**
	 * @apiDefine AppRebateMerchRespModel AppRebateMerchRespModel
	*  @apiParam (AppRebateOrdersRespModel) {String} supProductName sup名称
	*  @apiParam (AppRebateOrdersRespModel) {int} totalNum 规格总份数

	*/

	/**
	 * @apiDefine TicketSellerOrdersRespModel TicketSellerOrdersRespModel
	*/
	/**
	 * @apiDefine TicketSellerOrderDetailReqModel TicketSellerOrderDetailReqModel
	*/
	/**
	 * @apiDefine TicketSellerOrderDetailRespModel TicketSellerOrderDetailRespModel
	*/

}
