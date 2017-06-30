package com.pzj.core.trade.order.engine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.base.entity.SysChannel;
import com.pzj.base.entity.SysUser;
import com.pzj.base.service.sys.IChannelService;
import com.pzj.base.service.sys.IUserService;
import com.pzj.core.trade.order.engine.common.TimeCalculator;
import com.pzj.core.trade.order.engine.converter.OrderListBySupplierResponseConverter;
import com.pzj.core.trade.order.engine.event.RefundInfoEvent;
import com.pzj.core.trade.query.entity.SupplierOrdersInModel;
import com.pzj.core.trade.query.entity.SupplierOrdersQueryModel;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.entity.QueryResult;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchListEntity;
import com.pzj.trade.merch.entity.SupplierOrderListEntity;
import com.pzj.trade.order.common.MerchStateEnum;
import com.pzj.trade.order.entity.MerchResponse;
import com.pzj.trade.order.entity.OrderCountEntity;
import com.pzj.trade.order.entity.OrderListResponse;
import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.entity.RefundFlowResponse;
import com.pzj.trade.order.model.SupplierMerchOutModel;
import com.pzj.trade.order.model.SupplierOrdersOutModel;
import com.pzj.trade.order.model.SupplierOrdersReqModel;
import com.pzj.trade.order.model.SupplierOrdersRespModel;
import com.pzj.trade.order.read.OrderForSupplierReadMapper;
import com.pzj.trade.order.read.OrderStrategyReadMapper;

/**
 * 供应商订单查询引擎.
 * @author YRJ
 *
 */
@Component(value = "supplierOrderQueryEngine")
public class SupplierOrderQueryEngine {

	private final static Logger logger = LoggerFactory.getLogger(SupplierOrderQueryEngine.class);

	@Autowired
	private OrderForSupplierReadMapper orderForSupplierReadMapper;

	@Autowired
	private TimeCalculator timeCalculator;

	@Autowired
	private IUserService userService;
	@Autowired
	private IChannelService iChannelService;
	@Resource(name = "refundInfoEvent")
	private RefundInfoEvent refundInfoEvent;

	@Resource(name = "orderListBySupplierResponseConverter")
	private OrderListBySupplierResponseConverter orderListBySupplierResponseConverter;

	@Resource(name = "orderStrategyReadMapper")
	private OrderStrategyReadMapper orderStrategyReadMapper;

	/**
	 * 供应商查询订单列表引擎.
	 * @param reqModel
	 */
	public SupplierOrdersRespModel queryOrders(SupplierOrdersReqModel reqModel) {
		SupplierOrdersInModel inModel = convert(reqModel);
		if (logger.isInfoEnabled()) {
			logger.info("供应商订单列表查询条件 --> " + (JSONConverter.toJson(inModel)));
		}

		SupplierOrdersRespModel respModel = new SupplierOrdersRespModel();
		OrderCountEntity count = orderForSupplierReadMapper.querySupplierOrderCount(inModel);
		if (count != null) {
			respModel.setTotalNum(count.getOrder_num());
			respModel.setTotalAmount(count.getAmount().doubleValue());
			respModel.setTotalMerchNum(count.getMerch_num());
		} else {
			return respModel;
		}

		QueryResult<SupplierOrderListEntity> qr = new QueryResult<SupplierOrderListEntity>(reqModel.getCurrentPage(), reqModel.getPageSize());
		qr.setTotal(respModel.getTotalNum());

		List<SupplierOrderListEntity> merchs = orderForSupplierReadMapper.queryOrderByCondition(inModel, inModel.getPageIndex(), inModel.getPageSize());
		List<SupplierOrdersOutModel> outModels = new ArrayList<SupplierOrdersOutModel>();
		if (merchs != null && merchs.size() > 0) {
			Set<Long> disUserIds = new HashSet<Long>();
			Set<Long> disChannelIds = new HashSet<Long>();
			for (SupplierOrderListEntity supplierOrder : merchs) {
				disUserIds.add(supplierOrder.getReseller_id());
				disChannelIds.add(supplierOrder.getChannel_id());
			}
			List<SysUser> sysusers = userService.findUserByIds(new ArrayList<Long>(disUserIds));

			SysChannel sysChannel = new SysChannel();
			sysChannel.setQueryIds(new ArrayList<Long>(disChannelIds));
			List<SysChannel> sysChannels = iChannelService.findListByParams(sysChannel);
			for (SupplierOrderListEntity merch : merchs) {
				SupplierOrdersOutModel outModel = getSupplierOrdersOutModelByOrderId(merch, outModels, sysusers, sysChannels);

				List<SupplierMerchOutModel> merchModels = convertToMerchOutModel(merch);
				outModel.getMerchs().addAll(merchModels);
			}
			qr.setRecords(merchs);
		}

		respModel.setCurrentPage(qr.getCurrentPage());
		respModel.setMaxPage((int) qr.getTotalPage());

		respModel.setOrders(outModels);
		return respModel;
	}

	private List<SupplierMerchOutModel> convertToMerchOutModel(SupplierOrderListEntity merch) {
		List<SupplierMerchOutModel> outModels = new ArrayList<SupplierMerchOutModel>();

		BigDecimal decimal = BigDecimal.valueOf(merch.getTotal_amount());
		BigDecimal avg = decimal.divide(BigDecimal.valueOf(merch.getTotal_num()), 2, BigDecimal.ROUND_HALF_EVEN);

		int refundNum = merch.getRefund_num();
		//int checkNum = merch.getCheck_num();
		int totalNum = merch.getTotal_num();
		int remain = totalNum; //剩余数量
		if (refundNum > 0) {
			remain -= refundNum;

			int merchState = MerchStateEnum.REFUNDED.getState();
			if (merch.getIs_refunding() == 1) {
				merchState = MerchStateEnum.REFUNDING.getState();
			}
			SupplierMerchOutModel merchModel = structure(merch, refundNum, merchState, avg);
			outModels.add(merchModel);
		}
		if (remain > 0) {
			SupplierMerchOutModel merchModel = structure(merch, remain, merch.getMerch_state(), avg);
			outModels.add(merchModel);
		}
		return outModels;
	}

	private SupplierMerchOutModel structure(SupplierOrderListEntity merch, int totalNum, int merchState, BigDecimal avg) {
		SupplierMerchOutModel merchModel = new SupplierMerchOutModel();
		merchModel.setOrderId(merch.getOrder_id());
		merchModel.setpOrderId(merch.getP_order_id());
		merchModel.setOrderStatus(merch.getOrder_status());
		merchModel.setNeedConfirm(merch.getNeed_confirm());
		merchModel.setOnlinePay(merch.getOnline_pay());
		merchModel.setDirect(merch.getIs_direct());
		merchModel.setContactee(merch.getContactee());
		merchModel.setContactMobile(merch.getContact_mobile());
		merchModel.setCreateTime(merch.getCreate_time());
		merchModel.setProductId(merch.getProduct_id());
		merchModel.setMerchId(merch.getMerch_id());
		merchModel.setMerchName(merch.getMerch_name());
		merchModel.setMerchState(/*merch.getMerch_state()*/merchState);
		merchModel.setMerchType(merch.getMerch_type());
		merchModel.setTotalNum(totalNum);
		merchModel.setIsRefunding(merch.getIs_refunding());
		merchModel.setStartTime(merch.getStart_time());
		merchModel.setVoucherId(merch.getVoucher_id());
		merchModel.setTotalAmount(avg.multiply(BigDecimal.valueOf(totalNum)).doubleValue());
		return merchModel;
	}

	/**
	 * 根据订单ID获取响应数据.
	 * @param order_id
	 * @return
	 */
	private SupplierOrdersOutModel getSupplierOrdersOutModelByOrderId(SupplierOrderListEntity merch, List<SupplierOrdersOutModel> outModels,
			List<SysUser> sysusers, List<SysChannel> sysChannels) {
		for (SupplierOrdersOutModel outModel : outModels) {
			if (outModel.getOrderId().equals(merch.getOrder_id())) {
				return outModel;
			}
		}
		SupplierOrdersOutModel outModel = new SupplierOrdersOutModel(merch.getOrder_id());
		outModel.setPorderId(merch.getP_order_id());
		outModel.setOrderStatus(merch.getOrder_status());
		outModel.setIs_direct(merch.getIs_direct());
		outModel.setNeedConfirm(merch.getNeed_confirm());
		outModel.setCreateTime(merch.getCreate_time());
		outModel.setContactee(merch.getContactee());
		outModel.setContact_mobile(merch.getContact_mobile());
		outModel.setTotalAmount(BigDecimal.valueOf(merch.getTotal_amount()));
		outModel.setReseller_id(merch.getReseller_id());
		outModel.setChannel_id(merch.getChannel_id());
		for (SysUser sysUser : sysusers) {
			if (sysUser.getId().equals(outModel.getReseller_id())) {
				outModel.setReseller_name(sysUser.getName());
			}
		}
		for (SysChannel sysChannel : sysChannels) {
			if (sysChannel.getId().equals(outModel.getChannel_id())) {
				outModel.setChannel_name(sysChannel.getName());
			}
		}
		outModels.add(outModel);
		return outModel;
	}

	public SupplierOrdersInModel convert(SupplierOrdersReqModel reqModel) {
		SupplierOrdersInModel inModel = new SupplierOrdersInModel();
		inModel.setCurrentPage(reqModel.getCurrentPage());
		inModel.setPageSize(reqModel.getPageSize());

		inModel.setOrder_id(reqModel.getOrder_id());
		inModel.setDirect(reqModel.getDirect());
		inModel.setOrder_status(reqModel.getOrder_status());
		inModel.setContactee(reqModel.getContactee());
		inModel.setContact_mobile(reqModel.getContact_mobile());
		inModel.setSupplier_id(reqModel.getSupplier_id());
		inModel.setReseller_id(reqModel.getReseller_id());
		inModel.setChannel_id(reqModel.getChannel_id());

		inModel.setMerch_state(reqModel.getMerch_state());

		if (!Check.NuNObject(reqModel.getMerch_state()) && reqModel.getMerch_state() == MerchStateEnum.REFUNDING.getState()) {
			inModel.setMerch_state(null);
			inModel.setIs_refunding(1);
		}

		inModel.setMerch_name(reqModel.getMerch_name());
		inModel.setCategory(reqModel.getCategory());

		Date start_date = reqModel.getStart_date();
		if (!Check.NuNObject(start_date)) {
			TimeCalculator.VisitTime startDate = timeCalculator.calculator(start_date);
			inModel.setStart_date(startDate.getStartTime());
			inModel.setEnd_date(startDate.getEndTime());
		}
		Date end_date = reqModel.getEnd_date();
		if (!Check.NuNObject(end_date)) {
			TimeCalculator.VisitTime endDate = timeCalculator.calculator(end_date);
			inModel.setEnd_date(endDate.getEndTime());
		}
		Date visit_time = reqModel.getVisit_time();
		if (!Check.NuNObject(visit_time)) {
			TimeCalculator.VisitTime visitTime = timeCalculator.calculator(visit_time);
			inModel.setStart_visit_time(visitTime.getStartTime());
			inModel.setEnd_visit_time(visitTime.getEndTime());
		}

		return inModel;
	}

	/**
	 * Sssa供应查询订单列表引擎.
	 * @param reqModel
	 */
	public QueryResult<OrderListResponse> querySaaSOrders(SupplierOrdersQueryModel reqModel) {

		final QueryResult<OrderListResponse> qr = new QueryResult<OrderListResponse>(reqModel.getCurrentPage(), reqModel.getPageSize());
		qr.setTotal(0);

		OrderCountEntity count = orderForSupplierReadMapper.querySaaSSupplierOrderCount(reqModel);

		if (count != null) {
			qr.setTotal(count.getOrder_num());
		} else {
			return qr;
		}
		List<MerchListEntity> merchs = orderForSupplierReadMapper.querySaaSOrderByCondition(reqModel, reqModel.getPageIndex(), reqModel.getPageSize());
		final Map<String, OrderListResponse> orders = new LinkedHashMap<String, OrderListResponse>();
		Set<String> orderIds = new HashSet<String>();
		Set<String> merchIds = new HashSet<String>();
		for (final MerchListEntity merch : merchs) {
			merchIds.add(merch.getMerch_id());
			orderIds.add(merch.getOrder_id());
		}
		//查询所有订单商品的价格，结算价，渠道，政策
		List<OrderStrategyEntity> orderStrategyEntitys = new ArrayList<OrderStrategyEntity>();
		if (orderIds.size() != 0 && merchIds.size() != 0) {
			orderStrategyEntitys = orderStrategyReadMapper.getOrderStrategyByOrders(new ArrayList<String>(orderIds), new ArrayList<String>(merchIds));
		}

		for (final MerchListEntity merch : merchs) {
			OrderListResponse order = orders.get(merch.getOrder_id());
			if (order == null) {
				order = orderListBySupplierResponseConverter.convert(merch, null);
				orders.put(merch.getOrder_id(), order);
			}

			final List<MerchResponse> merchEntities = order.getMerchs();
			final MerchResponse merchEntity = orderListBySupplierResponseConverter.convertToMerch(merch, orderStrategyEntitys);
			// 查询商品的退款信息
			if (merchEntity.getRefundNum() > 0) {
				List<RefundFlowResponse> refundFlowResponses = refundInfoEvent.getRefundFlow(order.getOrderId(), merchEntity.getMerchId(), merch.getVersion(),
						order.getTransactionId(), merch.getRoot_merch_id());
				merchEntity.setRefundInfos(refundFlowResponses);
			} else {
				merchEntity.setRefundInfos(new ArrayList<RefundFlowResponse>());
			}
			merchEntities.add(merchEntity);
		}

		final List<OrderListResponse> orderResponse = new ArrayList<OrderListResponse>();
		for (final Entry<String, OrderListResponse> entry : orders.entrySet()) {
			orderResponse.add(entry.getValue());
		}
		if (!Check.NuNCollections(orderResponse)) {
			orderResponse.get(0).setAll_merch_num(count.getMerch_num());
			orderResponse.get(0).setAll_amount(count.getAmount());
		}
		qr.setRecords(orderResponse);
		return qr;
	}
}
