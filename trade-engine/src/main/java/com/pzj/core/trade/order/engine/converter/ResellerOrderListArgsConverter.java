package com.pzj.core.trade.order.engine.converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.common.PayWayForConvertEnum;
import com.pzj.core.trade.query.entity.ResellerOrdersQueryModel;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.common.MerchStateEnum;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.model.ResellerOrdersReqModel;

/**
 * 分销商订单列表查询条件转换.
 * @author YRJ
 *
 */
@Component(value = "resellerOrderListArgsConverter")
public class ResellerOrderListArgsConverter implements ObjectConverter<ResellerOrdersReqModel, Void, ResellerOrdersQueryModel> {

	@Override
	public ResellerOrdersQueryModel convert(final ResellerOrdersReqModel orderListVO, final Void y) {
		final ResellerOrdersQueryModel param = new ResellerOrdersQueryModel();
		param.setOrder_id(orderListVO.getOrderId());
		param.setOrder_status(orderListVO.getOrderStatus());
		param.setReseller_id(orderListVO.getResellerId());
		param.setStart_date(orderListVO.getStartDate());
		param.setEnd_date(orderListVO.getEndDate());
		param.setOperator_id(orderListVO.getOperatorId());
		param.setContactee(orderListVO.getContactee());
		param.setContact_mobile(orderListVO.getContactMobile());
		param.setMerch_name(orderListVO.getMerchName());
		param.setOrder_status_list(orderListVO.getOrderStatusList());
		param.setProduct_varie(orderListVO.getProductVarie());
		param.setSale_port(orderListVO.getSalePort());
		param.setMerch_state(orderListVO.getMerchState());
		param.setOrder_status_list(orderListVO.getOrderStatusList());
		param.setSale_port_list(orderListVO.getSalePortList());
		param.setSupplier_id(orderListVO.getSupplierId());
		param.setP_supplier_id(orderListVO.getpSupplierId());
		param.setMerch_type(orderListVO.getMerchType());
		param.setProduct_id(orderListVO.getProductId());
		param.setReseller_ids(orderListVO.getResellerIds());
		param.setSupplier_ids(orderListVO.getSupplierIds());
		param.setP_supplier_ids(orderListVO.getpSupplierIds());
		param.setQuery_type(orderListVO.getQueryType());
		param.setProduct_ids(orderListVO.getProductIds());
		param.setTransaction_id(orderListVO.getTransactionId());
		param.setSortDesc(orderListVO.getSortDesc());
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 添加查询出游开始、结束日期
		if (!Check.NuNObject(orderListVO.getVisitStartTime())) {
			param.setVisit_start_time(sdf.format(orderListVO.getVisitStartTime()) + " 00:00:00");
		}
		if (!Check.NuNObject(orderListVO.getVisitEndTime())) {
			param.setVisit_end_time(sdf.format(orderListVO.getVisitEndTime()) + " 23:59:59");
		}

		//判断查询类型 *resellerQueryPayReslut 需要付款订单（分销）*resellerQueryDifferencePayReslut 需要补差订单（分销）
		if ("resellerQueryPayReslut".equals(param.getQuery_type())) {
			final ArrayList<Integer> order_status_list = new ArrayList<Integer>();
			order_status_list.add(Integer.valueOf(OrderStatusEnum.Unpaid.getValue()));
			param.setOrder_status_list(order_status_list);
			param.setIsRootResellerOrder(1);
		}
		if ("resellerQueryDifferencePayReslut".equals(param.getQuery_type())) {
			param.setOrder_status(Integer.valueOf(OrderStatusEnum.Unpaid.getValue()));
			param.setHaveParentOrderFilter(true);
			//			param.setPay_state(0);
		}

		convert(param);
		convertPayWay(param, orderListVO);
		return param;
	}

	private void convert(final ResellerOrdersQueryModel param) {
		// 待退款特殊处理，因为目前商品状态没有待退款，需要查询refund flow来判断
		if (!Check.NuNObject(param.getMerch_state()) && param.getMerch_state() == MerchStateEnum.REFUNDING.getState()) {
			param.setMerch_state(null);
			param.setIs_refunding(1);
		}
		if (!Check.NuNObject(param.getMerch_state()) && param.getMerch_state() == MerchStateEnum.WAIT_CONFIRM.getState()) {
			param.setNeed_confirm(2);
		}
		// 是否有商品属性过滤，如果有，会关联商品表；如果没有，不关联，提高查询效率
		if (!Check.NuNString(param.getMerch_name()) || !Check.NuNString(param.getProduct_varie()) || !Check.NuNObject(param.getMerch_state())
				|| param.getClear_type() > 0 || !Check.NuNObject(param.getIs_refunding()) || !Check.NuNObject(param.getConfirm_date_start())
				|| !Check.NuNObject(param.getConfirm_date_end()) || 2 == param.getNeed_confirm() || !Check.NuNString(param.getVisit_start_time())
				|| !Check.NuNString(param.getVisit_end_time()) || param.getMerch_type() > 0 || !Check.NuNCollections(param.getMerchTypes())
				|| !Check.NuNCollections(param.getMerch_states()) || param.getP_supplier_id() > 0 || !Check.NuNCollections(param.getProduct_ids())
				|| !Check.NuNCollections(param.getP_supplier_ids())) {
			param.setHaveMerchFilter(true);
		}

	}

	/**
	 * 对前端传来的支付方式进行转换
	 * 1：第三方/余额对应：0: 纯余额; 1. 支付宝; 2. 微信; 4: 混合支付;
	 * 2：签单对应： 6: 后付
	 * 3现金对应：5: 现金;
	 * */
	private void convertPayWay(final ResellerOrdersQueryModel param, final ResellerOrdersReqModel orderListVO) {
		/**支付方式. 1：第三方/余额 2：签单 3：现金*/
		if (orderListVO.getPayWay() != null) {

			final String payWay = PayWayForConvertEnum.getPayWay(orderListVO.getPayWay()).getPayWay();
			final String[] payWays = payWay.split(",");
			if (payWays != null && payWays.length > 0) {
				if (payWays.length > 1) {
					param.setPay_ways(new ArrayList<Integer>());
					for (int i = 0; i < payWays.length; i++) {
						param.getPay_ways().add(Integer.valueOf(payWays[i]));
					}
				} else {
					param.setPay_way(Integer.valueOf(payWays[0]));
				}
			}

		}
	}
}
