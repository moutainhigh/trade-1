package com.pzj.core.trade.order.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.base.entity.SysUser;
import com.pzj.base.service.sys.IUserService;
import com.pzj.core.trade.query.entity.TicketSellerOrdersQueryModel;
import com.pzj.framework.entity.PageableRequestBean;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.common.MerchStateEnum;
import com.pzj.trade.order.entity.OrderCountEntity;
import com.pzj.trade.order.entity.TicketSellerOrdersEntity;
import com.pzj.trade.order.model.TicketSellerMerchOutModel;
import com.pzj.trade.order.model.TicketSellerOrdersOutModel;
import com.pzj.trade.order.model.TicketSellerOrdersRespModel;
import com.pzj.trade.order.read.TicketSellerOrdersReadMapper;

/**
 * 订单列表-售票员-查询引擎.
 * @author YRJ
 *
 */
@Component(value = "ticketSellerOrderQueryEngine")
public class TicketSellerOrderQueryEngine {

	private final static Logger logger = LoggerFactory.getLogger(TicketSellerOrderQueryEngine.class);

	@Autowired
	private IUserService userService;

	@Resource(name = "ticketSellerOrdersReadMapper")
	private TicketSellerOrdersReadMapper ticketSellerOrdersReadMapper;

	public TicketSellerOrdersRespModel queryOrders(TicketSellerOrdersQueryModel queryModel, PageableRequestBean pageBean) {
		TicketSellerOrdersRespModel respModel = new TicketSellerOrdersRespModel();
		respModel.setCurrentPage(pageBean.getCurrentPage());
		respModel.setMaxPage(pageBean.getPageSize());

		//1. 查询订单统计数据, 若统计数值为零, 则直接返回.
		{
			OrderCountEntity orderCount = ticketSellerOrdersReadMapper.queryOrderTotalNum(queryModel);
			if (orderCount == null) {
				return respModel;
			}
			respModel.setTotalNum(orderCount.getOrder_num());
			respModel.setTotalAmount(orderCount.getAmount().doubleValue());
			respModel.setTotalMerchNum(orderCount.getMerch_num());
		}

		//2. 根据页码查询具体的订单列表.
		List<TicketSellerOrdersEntity> orderEntities = ticketSellerOrdersReadMapper.queryOrdersByScrollData(queryModel,
				pageBean.getPageIndex(), pageBean.getPageSize());
		if (orderEntities == null) {//理论上讲, 此值永不可为空.
			return respModel;
		}

		Set<Long> resellerIds = new HashSet<Long>();
		List<TicketSellerOrdersOutModel> orders = new ArrayList<TicketSellerOrdersOutModel>();
		Map<String, TicketSellerOrdersOutModel> orderMaps = new HashMap<String, TicketSellerOrdersOutModel>();
		for (TicketSellerOrdersEntity orderEntity : orderEntities) {
			resellerIds.add(orderEntity.getReseller_id());//统计分销商ID.

			TicketSellerOrdersOutModel item = orderMaps.get(orderEntity.getOrder_id());
			if (Check.NuNObject(item)) {
				item = generateOrderOutModel(orderEntity);
				orderMaps.put(orderEntity.getOrder_id(), item);
			} else {
				item.setConsumeAmount(item.getConsumeAmount() + orderEntity.getPrice() * orderEntity.getCheck_num());
			}
			List<TicketSellerMerchOutModel> merchs = convertToMerchOutModel(orderEntity);
			item.getMerchs().addAll(merchs);
		}
		orders.addAll(orderMaps.values());
		{
			List<SysUser> resellers = onLoadResellerNames(resellerIds);
			fillResellerName(resellers, orders);
		}
		Comparator<TicketSellerOrdersOutModel> c = new Comparator<TicketSellerOrdersOutModel>() {
			@Override
			public int compare(TicketSellerOrdersOutModel o1, TicketSellerOrdersOutModel o2) {
				return -o1.getCreateTime().compareTo(o2.getCreateTime());
			}

		};
		Collections.sort(orders, c);
		respModel.setOrders(orders);
		return respModel;
	}

	/**
	 * 构建订单输出模型.
	 * @param orderEntity
	 * @return
	 */
	private TicketSellerOrdersOutModel generateOrderOutModel(TicketSellerOrdersEntity orderEntity) {
		TicketSellerOrdersOutModel orderModel = new TicketSellerOrdersOutModel();
		orderModel.setOrderId(orderEntity.getOrder_id());
		orderModel.setTransactionId(orderEntity.getTransaction_id());
		orderModel.setOrderStatus(orderEntity.getOrder_status());
		orderModel.setResellerId(orderEntity.getReseller_id());
		orderModel.setContactee(orderEntity.getContactee());
		orderModel.setContactMobile(orderEntity.getContact_mobile());
		orderModel.setCreateTime(orderEntity.getCreate_time());
		orderModel.setTotalAmount(orderEntity.getTotal_amount());
		orderModel.setRefundAmount(orderEntity.getRefund_amount());
		orderModel.setConsumeAmount(orderEntity.getPrice() * orderEntity.getCheck_num());
		return orderModel;
	}

	/**
	 * 根据状态拆分成多条商品模型.
	 * @param orderMerch
	 * @return
	 */
	private List<TicketSellerMerchOutModel> convertToMerchOutModel(TicketSellerOrdersEntity merch) {
		List<TicketSellerMerchOutModel> outModels = new ArrayList<TicketSellerMerchOutModel>();

		int refundNum = merch.getRefund_num();
		int totalNum = merch.getTotal_num();
		int refundingNum = merch.getRefunding_num();
		int merchState;
		if (refundingNum > 0) {
			merchState = MerchStateEnum.REFUNDING.getState();
			TicketSellerMerchOutModel merchModel = structure(merch, refundingNum, merchState);
			outModels.add(merchModel);
		}
		if (refundNum - refundingNum > 0) {
			merchState = MerchStateEnum.REFUNDED.getState();
			TicketSellerMerchOutModel merchModel = structure(merch, refundNum - refundingNum, merchState);
			outModels.add(merchModel);
		}
		if (totalNum - refundNum > 0) {
			TicketSellerMerchOutModel merchModel = structure(merch, totalNum - refundNum, merch.getMerch_state());
			outModels.add(merchModel);
		}
		return outModels;
	}

	private TicketSellerMerchOutModel structure(TicketSellerOrdersEntity merch, int totalNum, int merchState) {
		TicketSellerMerchOutModel merchModel = new TicketSellerMerchOutModel();
		merchModel.setProductId(merch.getProduct_id());
		merchModel.setMerchId(merch.getMerch_id());
		merchModel.setMerchName(merch.getMerch_name());
		merchModel.setMerchState(merchState);
		merchModel.setCategory(merch.getMerch_type());
		merchModel.setTotalNum(totalNum);
		merchModel.setTravelTime(merch.getStart_time());
		merchModel.setVoucherId(merch.getVoucher_id());
		merchModel.setVarie(merch.getProduct_varie());
		return merchModel;
	}

	/**
	 * 加载分销商信息.
	 * @param resellerIds
	 */
	private List<SysUser> onLoadResellerNames(Set<Long> resellerIds) {
		List<SysUser> resellers = null;
		try {
			resellers = userService.findUserByIds(new ArrayList<Long>(resellerIds));
		} catch (Throwable e) {
			logger.error("查询分销商信息失败, resellerIds: " + (Arrays.toString(resellerIds.toArray(new Long[0]))), e);
		}
		return resellers;
	}

	/**
	 * 填充分销商名称.
	 * @param resellers
	 * @param orders
	 */
	private void fillResellerName(List<SysUser> resellers, List<TicketSellerOrdersOutModel> orders) {
		if (resellers == null || resellers.isEmpty()) {
			return;
		}
		for (TicketSellerOrdersOutModel order : orders) {
			for (SysUser reseller : resellers) {
				if (order.getResellerId() == reseller.getId()) {
					order.setResellerName(reseller.getName());
				}
			}
		}
	}
}
