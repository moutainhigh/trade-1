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
import com.pzj.trade.order.vo.OrderListVO;

/**
 * 分销商订单列表查询条件转换.
 * @author YRJ
 *
 */
@Component(value = "resellerOrdersArgsConverter")
public class ResellerOrdersArgsConverter implements ObjectConverter<OrderListVO, Void, OrderListParameter> {
	@Resource(name = "voucherQueryEvent")
	private VoucherQueryEvent voucherQueryEvent;

	@Override
	public OrderListParameter convert(final OrderListVO orderVO, final Void y) {
		OrderListParameter param = new OrderListParameter();
		BeanCopier.create(OrderListVO.class, OrderListParameter.class, false).copy(orderVO, param, null);
		param.setOrder_status_list(orderVO.getOrder_status_list());
		param.setSale_port_list(orderVO.getSale_port_list());
		param.setIsReseller(true);
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
		if (!Check.NuNCollections(param.getMerch_ids()) || !Check.NuNCollections(param.getProduct_ids()) || !Check.NuNCollections(param.getVoucher_ids())
				|| !Check.NuNString(param.getMerch_name()) || !Check.NuNString(param.getProduct_varie()) || param.getChannel_id() > 0
				|| !Check.NuNObject(param.getMerch_state()) || param.getClear_type() > 0 || !Check.NuNObject(param.getIs_refunding())
				|| !Check.NuNObject(param.getConfirm_date_start()) || !Check.NuNObject(param.getConfirm_date_end()) || 2 == param.getAgent_flag()
				|| 2 == param.getNeed_confirm() || !Check.NuNString(param.getAccurateStartTime()) || !Check.NuNString(param.getAccurateEndTime())
				|| param.getMerch_type() > 0 || !Check.NuNCollections(param.getMerchTypes()) || !Check.NuNCollections(param.getMerch_states())) {
			param.setHaveMerchFilter(true);
		}

		return param;
	}

}
