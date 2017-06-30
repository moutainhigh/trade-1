package com.pzj.core.trade.order.engine;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.base.entity.SysUser;
import com.pzj.base.service.sys.IUserService;
import com.pzj.core.trade.order.engine.converter.OrderDetailByResellerResponseConverter;
import com.pzj.core.trade.order.engine.event.DeliveryDetailQueryEvent;
import com.pzj.core.trade.order.engine.event.MerchCleanRelationEvent;
import com.pzj.core.trade.order.engine.event.OrderExtendAttrEvent;
import com.pzj.core.trade.order.engine.event.OrderTouristEvent;
import com.pzj.core.trade.order.engine.event.RefundInfoEvent;
import com.pzj.core.trade.order.engine.event.RemarkQueryEvent;
import com.pzj.core.trade.order.engine.event.VoucherQueryEvent;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.read.MerchCleanReadMapper;
import com.pzj.trade.order.entity.MerchCleanRelationResponse;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.entity.RefundFlowResponse;
import com.pzj.trade.order.entity.RemarkResponse;
import com.pzj.trade.order.model.DeliveryDetailModel;
import com.pzj.trade.order.model.FilledModel;
import com.pzj.trade.order.model.OrderTouristOutModel;
import com.pzj.trade.order.model.ResellerMerchDetailRespModel;
import com.pzj.trade.order.model.ResellerOrderDetailRespModel;
import com.pzj.trade.order.model.ResellerPriceDetailRespModel;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.OrderForResellerReadMapper;
import com.pzj.trade.order.read.OrderReadMapper;
import com.pzj.trade.order.read.OrderStrategyReadMapper;
import com.pzj.voucher.common.ExecuteResult;
import com.pzj.voucher.entity.VoucherEntity;

/**
 * 查询SaaS分销订单查询引擎.
 * @author GLG
 *
 */
@Component(value = "resellerOrderDetailEngine")
public class ResellerOrderDetailEngine {
	private final static Logger logger = LoggerFactory.getLogger(ResellerOrderDetailEngine.class);

	@Resource(name = "orderForResellerReadMapper")
	private OrderForResellerReadMapper orderForResellerReadMapper;

	@Resource(name = "merchReadMapper")
	private MerchReadMapper merchReadMapper;

	@Resource(name = "orderExtendAttrEvent")
	private OrderExtendAttrEvent orderExtendAttrEvent;
	@Resource(name = "remarkQueryEvent")
	private RemarkQueryEvent remarkQueryEvent;

	@Resource(name = "deliveryDetailQueryEvent")
	private DeliveryDetailQueryEvent deliveryDetailQueryEvent;
	@Resource(name = "deliveryEngine")
	private DeliveryEngine deliveryEngine;
	@Resource(name = "merchCleanRelationEvent")
	private MerchCleanRelationEvent merchCleanRelationEvent;
	@Resource(name = "merchCleanReadMapper")
	private MerchCleanReadMapper merchCleanReadMapper;

	@Resource(name = "voucherQueryEvent")
	private VoucherQueryEvent voucherQueryEvent;

	@Autowired
	private OrderReadMapper orderReadMapper;
	@Resource(name = "refundInfoEvent")
	private RefundInfoEvent refundInfoEvent;

	@Resource(name = "orderTouristEvent")
	private OrderTouristEvent orderTouristEvent;

	@Autowired
	private IUserService userService;
	@Autowired
	private OrderStrategyReadMapper orderStrategyReadMapper;

	@Resource(name = "orderDetailByResellerResponseConverter")
	private OrderDetailByResellerResponseConverter orderDetailByResellerResponseConverter;

	public ResellerOrderDetailRespModel queryOrderDetailByOrderId(String orderId, long reseller_id, int orderLevel, String transactionId) {

		OrderEntity orderEntity = orderForResellerReadMapper.queryOrderDetailByOrderId(orderId, reseller_id, orderLevel, transactionId);
		if (orderEntity == null) {
			return null;
		}
		// 查询商品和政策
		final List<MerchEntity> merchs = merchReadMapper.getMerchWithStrategyByTransactionId(orderEntity.getOrder_id(), orderEntity.getTransaction_id());
		//参数转换
		ResellerOrderDetailRespModel order = orderDetailByResellerResponseConverter.convert(orderEntity, merchs);
		// 查询填单项
		List<FilledModel> filledModelList = orderExtendAttrEvent.getOrderExtendAttr(orderEntity.getTransaction_id());
		order.setFilledModelList(filledModelList);
		// 查询快递信息
		DeliveryDetailModel deliveryDetailModel = new DeliveryDetailModel();
		if (merchs != null && merchs.size() > 0) {
			deliveryDetailModel = deliveryDetailQueryEvent.queryDeliveryDetailByTransactionId(orderEntity, merchs.get(0).getMerch_type());
		}
		order.setDelivery_info(deliveryDetailModel);
		//获取初始供应商的相关信息
		OrderEntity originOrder = orderForResellerReadMapper.getSupplierOrdersByResellerOrderId(orderEntity.getTransaction_id(), 1);
		if (originOrder != null) {
			SysUser originUser = userService.getById(originOrder.getSupplier_id());
			if (originUser != null) {
				order.setpSupplierId(originUser.getId()); //初始供应商id
				order.setpSupplierMobile(originUser.getCorporaterMobile());//初始供应商手机

			}
		}
		//获取主订单信息
		OrderEntity originResellerOrder = orderForResellerReadMapper.getResellerOrdersByTransactionId(orderEntity.getTransaction_id());
		order.setOriginPayTime(originResellerOrder.getPay_time());//主订单支付时间
		//获取我的供应商的相关信息
		SysUser supplierUser = userService.getById(order.getSupplier_id());
		if (supplierUser != null) {
			order.setSupplierMobile(supplierUser.getCorporaterMobile()); //我的供应商手机
		}
		order.setSupplierPayTime(order.getPay_time());

		order.setSupplierPayType(order.getPayWay());

		if (order.getTotal_amount() > 0) {
			order.setSupplierTotalAmount(order.getTotal_amount());
		}

		double supplierRebateAmount = 0;
		//获取我的分销商的相关信息
		OrderEntity resellerOrder = orderForResellerReadMapper.queryResellerOrder(orderEntity.getP_order_id(), orderEntity.getReseller_id());
		if (resellerOrder != null) {
			SysUser resellerUser = userService.getById(resellerOrder.getReseller_id());
			if (resellerUser != null) {
				order.setReseller_mobile(resellerUser.getCorporaterMobile());
			}
			order.setResellerPayTime(resellerOrder.getPay_time());
			if (resellerOrder.getPay_state() == 2) {
				order.setResellerPayType(orderDetailByResellerResponseConverter.getPayWay(resellerOrder));
			} else {
				order.setResellerPayType(0);
			}
			order.setResellerTotalAmount(resellerOrder.getTotal_amount());
			order.setChildResellerId(resellerOrder.getReseller_id());
		}
		double resellerRebateAmount = 0;
		//获取导游手机
		if (order.getGuide_id() > 0) {
			SysUser guideUser = userService.getById(order.getGuide_id());
			if (guideUser != null) {
				order.setGuide_mobile(guideUser.getCorporaterMobile());
			}
		}
		// 查询商品的退款信息
		for (ResellerMerchDetailRespModel merchResponse : order.getResellerMerchDetailRespModels()) {
			List<ResellerPriceDetailRespModel> resellerPriceDetailRespModels = new ArrayList<ResellerPriceDetailRespModel>();
			if (merchResponse.getRefundNum() > 0) {
				List<RefundFlowResponse> refundFlowResponses = refundInfoEvent.getRefundFlow(orderEntity.getOrder_id(), merchResponse.getMerchId(),
						Integer.valueOf(orderEntity.getVersion()), orderEntity.getTransaction_id(), merchResponse.getRootMerchId());
				merchResponse.setRefundInfos(refundFlowResponses);
			}
			List<MerchCleanRelationResponse> merchCleanRelationResponses = merchCleanRelationEvent.getMerchCleanRelation(merchResponse.getOrderId(),
					merchResponse.getMerchId());
			merchResponse.setMerchCleanRelations(merchCleanRelationResponses);
			//获取我的供应商的价格及返利信息

			List<OrderStrategyEntity> supplierOrderStrategy = orderStrategyReadMapper.getOrderStrategys(order.getOrderId(), merchResponse.getMerchId());
			if (supplierOrderStrategy != null && supplierOrderStrategy.size() != 0) {
				ResellerPriceDetailRespModel supplierPrice = assemblePriceDetail(merchResponse, supplierOrderStrategy.get(0), false, orderEntity);
				resellerPriceDetailRespModels.add(supplierPrice);
				if (supplierOrderStrategy.get(0).getAfter_rebate_amount() != null) {
					supplierRebateAmount = supplierRebateAmount + supplierOrderStrategy.get(0).getAfter_rebate_amount().doubleValue()
							* merchResponse.getTotalNum();
				}
				merchResponse.setSupplierStrategyId(supplierOrderStrategy.get(0).getStrategyId());
			}
			//获取我的分销商价格及返利信息
			if (resellerOrder != null) {
				List<OrderStrategyEntity> resellerOrderStrategy = orderStrategyReadMapper.getOrderStrategys(resellerOrder.getOrder_id(),
						merchResponse.getMerchId());
				if (resellerOrderStrategy != null && resellerOrderStrategy.size() != 0) {
					ResellerPriceDetailRespModel resellerPrice = assemblePriceDetail(merchResponse, resellerOrderStrategy.get(0), true, resellerOrder);
					resellerPriceDetailRespModels.add(resellerPrice);
					if (resellerOrderStrategy.get(0).getAfter_rebate_amount() != null) {
						resellerRebateAmount = resellerRebateAmount + resellerOrderStrategy.get(0).getAfter_rebate_amount().doubleValue()
								* merchResponse.getTotalNum();
					}
					merchResponse.setResellerStrategyId(resellerOrderStrategy.get(0).getStrategyId());
				}
			}

			merchResponse.setResellerPriceDetailRespModels(resellerPriceDetailRespModels);
			//查询游客信息
			List<OrderTouristOutModel> orderTouristOutModels = orderTouristEvent.getOrderToruists(order.getTransaction_id(), merchResponse.getMerchId());
			merchResponse.setOrderTouristOutModels(orderTouristOutModels);
		}
		order.setSupplierRebateAmount(supplierRebateAmount);//供应商返利信息
		order.setResellerRebateAmount(resellerRebateAmount);//分销商返利信息
		//获取
		// 查询商品的voucher信息
		ExecuteResult<List<VoucherEntity>> executeResult = voucherQueryEvent.getVoucherInfo(orderEntity.getTransaction_id());
		voucherQueryEvent.assembleVoucherInfoByReseller(order.getResellerMerchDetailRespModels(), executeResult, orderEntity.getTransaction_id());
		// 查询备注
		List<RemarkResponse> remarkResponses = remarkQueryEvent.queryRemarkByOrderId(orderEntity.getOrder_id());
		order.setRemarks(remarkResponses);
		//获取客源地信息
		String touristSource = orderExtendAttrEvent.getTouristSourceEvent(orderEntity.getTransaction_id());
		order.setTouristSource(touristSource);
		return order;
	}

	private ResellerPriceDetailRespModel assemblePriceDetail(ResellerMerchDetailRespModel merchResponse, OrderStrategyEntity orderStrategyEntity,
			boolean isReseller, OrderEntity order) {
		ResellerPriceDetailRespModel price = new ResellerPriceDetailRespModel();
		if (orderStrategyEntity.getAdvice_price() != null) {
			price.setPrice(orderStrategyEntity.getAdvice_price().doubleValue());
		}
		if (orderStrategyEntity.getSettlement_price() != null) {
			price.setDetailPrice(orderStrategyEntity.getSettlement_price().doubleValue());
		}
		if (orderStrategyEntity.getAfter_rebate_amount() != null) {
			price.setRebate_amount(orderStrategyEntity.getAfter_rebate_amount().doubleValue());
		}

		price.setRebate_object(orderStrategyEntity.getRebate_settlement());
		price.setReseller(isReseller);
		price.setReseller_id(order.getReseller_id());
		price.setSupplier_id(order.getSupplier_id());
		price.setTotal_num(merchResponse.getTotalNum());
		return price;

	}

}
