/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.engine.converter;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import net.sf.cglib.beans.BeanCopier;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.event.VoucherQueryEvent;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.common.MerchStateEnum;
import com.pzj.trade.order.entity.OrderListParameter;
import com.pzj.trade.order.vo.PlatformOrderListVO;
import com.pzj.trade.order.vo.PlatformRefundOrderListVO;

/**
 * 
 * @author Administrator
 * @version $Id: PlatformOrdersQueryArgsConverter.java, v 0.1 2017年4月17日 上午10:55:22 Administrator Exp $
 */
@Component(value = "platformOrdersQueryArgsConverter")
public class PlatformOrdersQueryArgsConverter implements ObjectConverter<PlatformOrderListVO, Void, OrderListParameter> {
	@Resource(name = "voucherQueryEvent")
	private VoucherQueryEvent voucherQueryEvent;

	/** 
	 * @see com.pzj.framework.converter.ObjectConverter#convert(java.lang.Object, java.lang.Object)
	 */
	@Override
	public OrderListParameter convert(PlatformOrderListVO orderVO, Void y) {
		OrderListParameter param = new OrderListParameter();
		BeanCopier.create(PlatformOrderListVO.class, OrderListParameter.class, false).copy(orderVO, param, null);
		param.setSupplier_order_ids(orderVO.getSupplier_order_ids());
		param.setReseller_order_ids(orderVO.getReseller_order_ids());
		param.setProduct_ids(orderVO.getProduct_ids());
		param.setReseller_ids(orderVO.getReseller_ids());
		param.setSupplier_ids(orderVO.getSupplier_ids());
		param.setGuide_ids(orderVO.getGuide_ids());
		if (orderVO.getIsForceOrderList()) {
			param.setIsforceOrder(true);
			param.setHaveMerchFilter(true);
		}
		// 处理一些BeanCopier无法复制的List
		param.setOrder_status_list(orderVO.getOrder_status_list());
		param.setSale_port_list(orderVO.getSale_port_list());

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 添加查询出游开始、结束日期
		if (!Check.NuNObject(orderVO.getVisit_start_time())) {
			param.setAccurateStartTime(sdf.format(orderVO.getVisit_start_time()) + " 00:00:00");
		}
		if (!Check.NuNObject(orderVO.getVisit_end_time())) {
			param.setAccurateEndTime(sdf.format(orderVO.getVisit_end_time()) + " 23:59:59");
		}
		param.setMerch_type(orderVO.getCategory());
		param.setMerchTypes(orderVO.getCategoryList());
		param.setIsReseller(true);
		// 根据出游入住时间先计算出相对应的voucher
		if (!Check.NuNObject(orderVO.getQr_code())) {
			final List<Long> voucherIds = voucherQueryEvent.queryVoucherByTypeAndStatus(orderVO.getQr_code());
			param.setVoucherIds(voucherIds);
		}
		// 待退款特殊处理，因为目前商品状态没有待退款，需要查询refund flow来判断
		if (!Check.NuNObject(param.getMerch_state()) && param.getMerch_state() == MerchStateEnum.REFUNDING.getState()) {
			param.setMerch_state(null);
			param.setIs_refunding(1);
		}
		if (!Check.NuNObject(param.getMerch_state()) && param.getMerch_state() == MerchStateEnum.WAIT_CONFIRM.getState()) {
			param.setNeed_confirm(2);
		}
		// 是否有商品属性过滤，如果有，会关联商品表；如果没有，不关联，提高查询效率
		haveMerchFilter(param);
		return param;
	}

	public OrderListParameter convertRefundOrders(PlatformRefundOrderListVO orderVO, boolean isForce) {
		OrderListParameter param = new OrderListParameter();
		BeanCopier.create(PlatformRefundOrderListVO.class, OrderListParameter.class, false).copy(orderVO, param, null);
		param.setSupplier_order_ids(orderVO.getSupplier_order_ids());
		param.setReseller_order_ids(orderVO.getReseller_order_ids());
		param.setProduct_ids(orderVO.getProduct_ids());
		param.setReseller_ids(orderVO.getReseller_ids());
		param.setSupplier_ids(orderVO.getSupplier_ids());
		param.setGuide_ids(orderVO.getGuide_ids());
		// 处理一些BeanCopier无法复制的List
		param.setOrder_status_list(orderVO.getOrder_status_list());
		param.setSale_port_list(orderVO.getSale_port_list());

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 添加查询出游开始、结束日期
		if (!Check.NuNObject(orderVO.getVisit_start_time())) {
			param.setAccurateStartTime(sdf.format(orderVO.getVisit_start_time()) + " 00:00:00");
		}
		if (!Check.NuNObject(orderVO.getVisit_end_time())) {
			param.setAccurateEndTime(sdf.format(orderVO.getVisit_end_time()) + " 23:59:59");
		}
		param.setMerch_type(orderVO.getCategory());
		param.setMerchTypes(orderVO.getCategoryList());
		param.setIsReseller(true);
		// 根据出游入住时间先计算出相对应的voucher
		if (!Check.NuNObject(orderVO.getQr_code())) {
			final List<Long> voucherIds = voucherQueryEvent.queryVoucherByTypeAndStatus(orderVO.getQr_code());
			param.setVoucherIds(voucherIds);
		}
		// 待退款特殊处理，因为目前商品状态没有待退款，需要查询refund flow来判断
		if (!Check.NuNObject(param.getMerch_state()) && param.getMerch_state() == MerchStateEnum.REFUNDING.getState()) {
			param.setMerch_state(null);
			param.setIs_refunding(1);
		}
		if (!Check.NuNObject(param.getMerch_state()) && param.getMerch_state() == MerchStateEnum.WAIT_CONFIRM.getState()) {
			param.setNeed_confirm(2);
		}
		// 是否有商品属性过滤，如果有，会关联商品表；如果没有，不关联，提高查询效率
		haveMerchFilter(param);
		//如果是已强制退款则加个参数
		if (isForce) {
			param.setIsReseller(true);
			param.setIsforceOrder(true);
		}
		return param;
	}

	private void haveMerchFilter(OrderListParameter param) {
		if (!Check.NuNCollections(param.getMerch_ids()) || !Check.NuNCollections(param.getProduct_ids()) || !Check.NuNCollections(param.getVoucher_ids())
				|| !Check.NuNString(param.getMerch_name()) || !Check.NuNString(param.getProduct_varie()) || param.getChannel_id() > 0
				|| !Check.NuNObject(param.getMerch_state()) || param.getClear_type() > 0 || !Check.NuNObject(param.getIs_refunding())
				|| !Check.NuNObject(param.getConfirm_date_start()) || !Check.NuNObject(param.getConfirm_date_end()) || 2 == param.getAgent_flag()
				|| 2 == param.getNeed_confirm() || !Check.NuNString(param.getAccurateStartTime()) || !Check.NuNString(param.getAccurateEndTime())
				|| param.getMerch_type() > 0 || !Check.NuNCollections(param.getMerchTypes()) || !Check.NuNCollections(param.getMerch_states())) {
			param.setHaveMerchFilter(true);
		}
	}

}
