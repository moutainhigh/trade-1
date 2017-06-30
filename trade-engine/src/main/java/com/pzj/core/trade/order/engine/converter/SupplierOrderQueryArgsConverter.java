package com.pzj.core.trade.order.engine.converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.common.PayWayForConvertEnum;
import com.pzj.core.trade.order.engine.event.VoucherQueryEvent;
import com.pzj.core.trade.query.entity.SupplierOrdersQueryModel;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.common.MerchStateEnum;
import com.pzj.trade.order.common.SalePortEnum;
import com.pzj.trade.order.vo.OrderListVO;
import com.pzj.trade.order.vo.PlatformOrderListVO;
import com.pzj.trade.order.vo.PlatformRefundOrderListVO;

/**
 * 供应商订单列表查询条件转换.
 * @author YRJ
 *
 */
@Component(value = "supplierOrderQueryArgsConverter")
public class SupplierOrderQueryArgsConverter implements ObjectConverter<OrderListVO, Void, SupplierOrdersQueryModel> {

	@Resource(name = "voucherQueryEvent")
	private VoucherQueryEvent voucherQueryEvent;

	@Override
	public SupplierOrdersQueryModel convert(final OrderListVO orderListVO, final Void y) {
		final SupplierOrdersQueryModel param = new SupplierOrdersQueryModel();

		param.setOrder_id(orderListVO.getOrder_id()); // 订单id
		param.setOrder_status(orderListVO.getOrder_status()); //订单状态
		param.setMerch_state(orderListVO.getMerch_state()); //商品状态

		param.setMerch_name(orderListVO.getMerch_name()); //商品名称
		param.setReseller_id(orderListVO.getReseller_id()); //分销商id
		param.setReseller_ids(orderListVO.getReseller_ids());//分销商id集合
		param.setSupplier_id(orderListVO.getSupplier_id()); //供应商id
		param.setContactee(orderListVO.getContactee()); //联系人
		param.setContact_mobile(orderListVO.getContact_mobile()); //联系人电话
		param.setIdcard_no(orderListVO.getIdcard_no()); //联系人身份证号
		param.setStart_date(orderListVO.getStart_date()); //下单开始时间
		param.setEnd_date(orderListVO.getEnd_date()); //下单结束时间
		param.setOperator_id(orderListVO.getOperator_id()); //操作人id  
		param.setIs_direct_sale(orderListVO.getIsDirectSale()); //是否直营  直营就是这个订单的分销商id和供应商id相同这块查询的时候需要特殊处理
		param.setSale_port(orderListVO.getSale_port()); //销售端口
		param.setProduct_varie(orderListVO.getProduct_varie());// 团散
		param.setProduct_id(orderListVO.getProduct_id()); //产品编号
		param.setGuide_id(orderListVO.getGuide_id());//导游id
		param.setChannel_id(orderListVO.getChannel_id());//渠道id
		param.setChannel_ids(orderListVO.getChannel_ids());//渠道id集合
		param.setSale_piont(orderListVO.getSalePiont());//售票点 
		param.setSale_piont_ids(orderListVO.getSalePiontIds());//售票点id集合
		param.setSale_person_id(orderListVO.getSalePersonId());//售票员id
		param.setSale_person_ids(orderListVO.getSalePersonIds());//售票员id集合
		param.setOrder_source(orderListVO.getOrder_source());//订单来源 0. 普通订单 1. 预约单 2. 免票 3. 特价票
		param.setPageSize(orderListVO.getPage_size());
		param.setCurrentPage(orderListVO.getCurrent_page());
		param.setTransaction_id(orderListVO.getTransactionId());
		// 根据二维码先计算出相对应的voucher
		if (!Check.NuNObject(orderListVO.getQr_code())) {
			final List<Long> voucherIds = voucherQueryEvent.queryVoucherByTypeAndStatus(orderListVO.getQr_code());
			param.setVoucherIds(voucherIds);
			//			if (Check.NuNCollections(voucherIds)) {
			//				param.setVoucher_ids(new ArrayList<Long>());
			//				param.getVoucher_ids().add(-1L);
			//			} else {
			//				param.setVoucher_ids(voucherIds);
			//			}

		}
		param.setOrder_status_list(orderListVO.getOrder_status_list()); //订单状态集合
		param.setNeed_confirm(orderListVO.getNeed_confirm()); //是否需要确认
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 添加查询出游开始、结束日期
		if (!Check.NuNObject(orderListVO.getVisit_start_time())) {
			param.setAccurateStartTime(sdf.format(orderListVO.getVisit_start_time()) + " 00:00:00");
		}
		if (!Check.NuNObject(orderListVO.getVisit_end_time())) {
			param.setAccurateEndTime(sdf.format(orderListVO.getVisit_end_time()) + " 23:59:59");
		}

		if (!Check.NuNObject(param.getMerch_state()) && param.getMerch_state() == MerchStateEnum.REFUNDING.getState()) {
			param.setMerch_state(null);
			param.setIs_refunding(1);
		}

		if (!Check.NuNObject(param.getMerch_state()) && param.getMerch_state() == MerchStateEnum.WAIT_CONFIRM.getState()) {
			param.setNeed_confirm(2);
		}
		param.setSortDesc(orderListVO.getSortDesc());
		convertPayWay(param, orderListVO);
		// 是否有商品属性过滤，如果有，会关联商品表；如果没有，不关联，提高查询效率
		haveMerchFilter(param);
		return param;
	}

	public void supplierQueryTypeConvert(final PlatformOrderListVO orderListVO, final SupplierOrdersQueryModel param) {
		param.setMerch_states(orderListVO.getMerch_states());//商品状态集合
		param.setMerch_type(orderListVO.getMerch_type()); //订单类型
		param.setMerchTypes(orderListVO.getMerchTypes()); //订单类型集合
		param.setGuide_ids(orderListVO.getGuide_ids());
		param.setProduct_ids(orderListVO.getProduct_ids());
		//根据查询类型来判断组合查询参数
		/**
		 * 查询类型:
		 * 
		 * supplierQuery订单查询列表（供应）
		 * supplierQueryDownLine订单列表查询(供应线下)
		 * supplierQueryOnLine订单列表查询(供应线上)
		 * suppplierQueryRefund退款订单列表查询（供应）
		 * suppplierQueryIsForceRefund强制退款申请订单列表
		 * suppplierQueryConfirm手动确认订单列表查询(供应)
		 * suppplierQueryCheck批量核销_检票订单列表（供应）
		 * */

		final String query_type = orderListVO.getQuery_type();
		if (query_type == null || "supplierQuery".equals(query_type) || "".equals(query_type)) {
			param.setQuery_type(1);
		}
		//如果是supplierQueryDownLine订单列表查询(供应线下)的 展示的是直营并且是销售端口是线下窗口的订单
		if ("supplierQueryDownLine".equals(query_type)) {
			param.setIs_direct_sale(1);
			param.setSale_port(SalePortEnum.OFFLINE_WINDOW.getValue());
		}
		//如果是supplierQueryOnLine订单列表查询(供应线上)的 取的是销售端口不是线上的
		else if ("supplierQueryOnLine".equals(query_type)) {
			param.setIs_online(1);
			param.setQuery_type(1);
		}
		// suppplierQueryRefund退款订单列表查询（供应）取的都是待确认、待消费的订单
		else if ("suppplierQueryRefund".equals(query_type)) {
			/** '订单商品状态.0:可消费; 1: 已消费; 2: 待退款; 3: 已退款; -1: 不可用;4:待确认；5：已完成*/
			final List<Integer> merch_states = new ArrayList<Integer>();
			merch_states.add(MerchStateEnum.WAIT_CONFIRM.getState());
			merch_states.add(MerchStateEnum.CONSUMABLE.getState());
			param.setMerch_states(null);//现将前端穿过来的状态清除
			param.setMerch_states(merch_states);
			param.setIs_refunding(0);
		}
		// suppplierQueryIsForceRefund强制退款申请订单列表 取的是可消费、已消费、已完成的订单
		else if ("suppplierQueryIsForceRefund".equals(query_type)) {
			/** '订单商品状态.0:可消费; 1: 已消费; 2: 待退款; 3: 已退款; -1: 不可用;4:待确认；5：已完成*/
			final List<Integer> merch_states = new ArrayList<Integer>();
			merch_states.add(MerchStateEnum.CONSUMED.getState());
			merch_states.add(MerchStateEnum.CONSUMABLE.getState());
			merch_states.add(MerchStateEnum.FINISHED.getState());
			param.setMerch_states(null);//先将前端传过来的状态清除
			param.setMerch_states(merch_states);
			param.setIsforceOrder(true);
		}
		// suppplierQueryConfirm手动确认订单列表查询(供应) 获取的是待确认的订单
		else if ("suppplierQueryConfirm".equals(query_type)) {
			final List<Integer> merch_states = new ArrayList<Integer>();
			merch_states.add(MerchStateEnum.WAIT_CONFIRM.getState());
			param.setMerch_states(null);//现将前端传过来的状态清除
			param.setMerch_states(merch_states);
		}
		//suppplierQueryCheck批量核销_检票订单列表（供应）获取的是有可消费的商品，但是没有退款中的商品
		else if ("suppplierQueryCheck".equals(query_type)) {
			final List<Integer> merch_states = new ArrayList<Integer>();
			merch_states.add(MerchStateEnum.CONSUMABLE.getState());
			param.setMerch_states(null);//现将前端传过来的状态清除
			param.setMerch_states(merch_states);
			param.setIs_refunding(0);
			param.setIs_native_product(0);

		}
		haveMerchFilter(param);
	}

	public void supplierQueryRefundTypeConvert(final PlatformRefundOrderListVO orderListVO, final SupplierOrdersQueryModel param) {
		param.setMerch_states(orderListVO.getMerch_states());//商品状态集合
		param.setMerch_type(orderListVO.getMerch_type()); //订单类型
		param.setMerchTypes(orderListVO.getMerchTypes()); //订单类型集合
		param.setGuide_ids(orderListVO.getGuide_ids());
		param.setProduct_ids(orderListVO.getProduct_ids());
		//根据查询类型来判断组合查询参数
		/**
		 * 查询类型:
		 *supplierQueryRefundAudit退款审核订单查询（供应）
		 *supplierQueryRefundResult退款审核结果（供应）
		 * */
		final String query_type = orderListVO.getQuery_type();

		//如果是supplierQueryRefundAudit退款审核订单查询（供应） 展示的是需要审核的订单
		if ("supplierQueryRefundAudit".equals(query_type)) {
			param.setRefund_order_type(1);
		}
		//如果是supplierQueryOnLine订单列表查询(供应线上)的 取的是销售端口不是线上的
		else if ("supplierQueryRefundResult".equals(query_type)) {
			param.setRefund_order_type(2);
		}
		haveMerchFilter(param);
	}

	/**
	 * 对前端传来的支付方式进行转换
	 * 1：第三方/余额对应：0: 纯余额; 1. 支付宝; 2. 微信; 4: 混合支付;
	 * 2：签单对应： 6: 后付
	 * 3现金对应：5: 现金;
	 * */
	private void convertPayWay(final SupplierOrdersQueryModel param, final OrderListVO orderListVO) {
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

	private void haveMerchFilter(SupplierOrdersQueryModel param) {
		if (param.getMerch_state() != null || !Check.NuNCollections(param.getVoucher_ids()) || param.getProduct_id() > 0
				|| !Check.NuNString(param.getMerch_name()) || param.getRefund_order_type() == 1 || !Check.NuNString(param.getProduct_varie())
				|| param.getChannel_id() > 0 || !Check.NuNObject(param.getMerch_states()) || !Check.NuNObject(param.getIs_refunding())
				|| 2 == param.getNeed_confirm() || !Check.NuNString(param.getAccurateStartTime()) || !Check.NuNString(param.getAccurateEndTime())
				|| param.getMerch_type() > 0 || !Check.NuNCollections(param.getMerchTypes()) || !Check.NuNCollections(param.getMerch_states())
				|| !Check.NuNCollections(param.getChannel_ids()) || !Check.NuNCollections(param.getProduct_ids())) {
			param.setHaveMerchFilter(true);
		}
	}

}
