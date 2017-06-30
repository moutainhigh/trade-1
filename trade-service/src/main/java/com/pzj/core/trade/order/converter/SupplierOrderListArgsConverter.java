package com.pzj.core.trade.order.converter;

import org.springframework.stereotype.Component;

import com.pzj.framework.converter.ObjectConverter;
import com.pzj.trade.order.entity.OrderListParameter;
import com.pzj.trade.order.vo.SupplierOrderListVO;

/**
 * 供应商订单列表查询条件转换.
 * @author YRJ
 *
 */
@Component(value = "supplierOrderListArgsConverter")
public class SupplierOrderListArgsConverter implements ObjectConverter<SupplierOrderListVO, Void, OrderListParameter> {

	@Override
	public OrderListParameter convert(SupplierOrderListVO orderListVO, Void y) {
		OrderListParameter param = new OrderListParameter();

		param.setOrder_id(orderListVO.getOrder_id());
		param.setOrder_status(orderListVO.getOrder_status());
		param.setSupplier_id(orderListVO.getSupplier_id());
		param.setReseller_id(orderListVO.getReseller_id());

		param.setStart_date(orderListVO.getStart_date());
		param.setEnd_date(orderListVO.getEnd_date());
		param.setOperator_id(orderListVO.getOperator_id());

		param.setContactee(orderListVO.getContactee());
		param.setContact_mobile(orderListVO.getContact_mobile());
		param.setIdcard_no(orderListVO.getIdcard_no());
		param.setMerch_name(orderListVO.getMerch_name());
		param.setVoucher_ids(orderListVO.getVoucher_ids());

		param.setOrder_status_list(orderListVO.getOrder_status_list());

		param.setChannel_id(orderListVO.getChannel_id());
		param.setProduct_varie(orderListVO.getProduct_varie());
		param.setSale_port(orderListVO.getSale_port());
		param.setNeed_confirm(orderListVO.getNeed_confirm());
		return param;
	}
}
