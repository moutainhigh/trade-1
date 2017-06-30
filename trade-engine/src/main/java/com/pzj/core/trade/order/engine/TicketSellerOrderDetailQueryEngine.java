//package com.pzj.core.trade.order.engine;
//
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import com.pzj.base.entity.SysUser;
//import com.pzj.core.trade.order.engine.common.PayWayEnum;
//import com.pzj.core.trade.order.engine.event.CustomerQueryEvent;
//import com.pzj.core.trade.order.engine.event.DeliveryDetailQueryEvent;
//import com.pzj.core.trade.order.engine.event.RemarkQueryEvent;
//import com.pzj.core.trade.order.engine.exception.OrderException;
//import com.pzj.core.trade.payment.engine.exception.PaymentTypeException;
//import com.pzj.trade.merch.entity.MerchEntity;
//import com.pzj.trade.order.entity.OrderEntity;
//import com.pzj.trade.order.entity.OrderExtendAttrEntity;
//import com.pzj.trade.order.entity.RemarkResponse;
//import com.pzj.trade.order.model.ContacteeOutModel;
//import com.pzj.trade.order.model.DeliveryDetailModel;
//import com.pzj.trade.order.model.FilledModel;
//import com.pzj.trade.order.model.TicketSellerOrderDetailRespModel;
//import com.pzj.trade.order.read.MerchReadMapper;
//import com.pzj.trade.order.read.OrderExtendAttrReadMapper;
//import com.pzj.trade.order.read.OrderReadMapper;
//
///**
// * 订单详情-售票员-查询引擎.
// * @author YRJ
// *
// */
//@Component(value = "ticketSellerOrderDetailQueryEngine")
//public class TicketSellerOrderDetailQueryEngine {
//
//	private final static Logger logger = LoggerFactory.getLogger(TicketSellerOrderDetailQueryEngine.class);
//
//	@Resource(name = "orderReadMapper")
//	private OrderReadMapper orderReadMapper;
//
//	@Resource(name = "merchReadMapper")
//	private MerchReadMapper merchReadMapper;
//
//	@Resource(name = "orderExtendAttrReadMapper")
//	private OrderExtendAttrReadMapper orderExtendAttrReadMapper;
//
//	@Resource(name = "deliveryDetailQueryEvent")
//	private DeliveryDetailQueryEvent deliveryDetailQueryEvent;
//
//	@Resource(name = "remarkQueryEvent")
//	private RemarkQueryEvent remarkQueryEvent;
//
//	@Resource(name = "customerQueryEvent")
//	private CustomerQueryEvent customerQueryEvent;
//
//	/**
//	 * 根据订单号查询详情.
//	 * @param orderId
//	 * @param operatorId
//	 */
//	public TicketSellerOrderDetailRespModel queryOrderDetail(String orderId, long operatorId) {
//		OrderEntity orderEntity = orderReadMapper.getOrderById(orderId);
//		if (orderEntity == null) {
//			throw new OrderException(10500, "订单[" + orderId + "], 不存在.");
//		}
//
//		TicketSellerOrderDetailRespModel respModel = assembleTicketSellerOrderDetail(orderEntity);
//
//		List<MerchEntity> merchs = merchReadMapper.getMerchByOrderId(orderId);
//		assembleTicketSellerOrderDetail(respModel, merchs);
//
//		//联系人
//		List<OrderExtendAttrEntity> contacts = onloadContactExtendAttrs(orderEntity.getOrder_id(), "contact");
//		ContacteeOutModel contact = assembleContactsToOrderDetail(contacts);
//		respModel.setContact(contact);
//
//		//填单项.
//		List<OrderExtendAttrEntity> others = onloadContactExtendAttrs(orderEntity.getOrder_id(), "other");
//		List<FilledModel> filleds = assembleOtherFilledToOrderDetail(others);
//		respModel.setFilleds(filleds);
//
//		//快递信息.
//		DeliveryDetailModel deliveryModel = deliveryDetailQueryEvent.queryDeliveryDetailByTransactionId(orderEntity, respModel.getCategory());
//		respModel.setDelivery(deliveryModel);
//
//		// 查询备注.
//		List<RemarkResponse> remarks = remarkQueryEvent.queryRemarkByOrderId(orderId);
//		respModel.setRemark(remarks);
//		return respModel;
//	}
//
//	private TicketSellerOrderDetailRespModel assembleTicketSellerOrderDetail(OrderEntity orderEntity) {
//		TicketSellerOrderDetailRespModel respModel = new TicketSellerOrderDetailRespModel();
//		respModel.setOrderId(orderEntity.getOrder_id());
//		respModel.setOrderStatus(orderEntity.getOrder_status());
//		respModel.setCreateTime(orderEntity.getCreate_time());
//		respModel.setPayTime(orderEntity.getPay_time());
//
//		{
//			String payWay = setPayWay(orderEntity.getPay_way());
//			respModel.setPayType(payWay);
//		}
//		{
//			respModel.setResellerId(orderEntity.getReseller_id());
//
//			SysUser user = customerQueryEvent.queryCustomerById(orderEntity.getReseller_id());
//			if (user != null) {
//				respModel.setResellerMobile(user.getCorporaterMobile());
//			}
//
//			respModel.setDepartMobile("-");
//			respModel.setDepartName(orderEntity.getDepart());
//			respModel.setGuider(orderEntity.getGuider());
//			respModel.setGuiderMobile("-");
//
//			respModel.setTravel(orderEntity.getTravel());
//			user = customerQueryEvent.queryCustomerById(orderEntity.getReseller_id());
//			if (user != null) {
//				respModel.setTravelMobile(user.getCorporaterMobile());
//				respModel.setTravelName(user.getName());
//			}
//		}
//		{
//			respModel.setTotalAmount(BigDecimal.valueOf(orderEntity.getTotal_amount()));
//			respModel.setCheckAmount(BigDecimal.valueOf(0.0));
//			respModel.setRefundAmount(BigDecimal.valueOf(orderEntity.getRefund_amount()));
//		}
//
//		return respModel;
//	}
//
//	/**
//	 * 组装订单详情的商品属性.
//	 * @param respModel
//	 * @param merchs
//	 */
//	private void assembleTicketSellerOrderDetail(TicketSellerOrderDetailRespModel respModel, List<MerchEntity> merchs) {
//		if (merchs == null) {
//			return;
//		}
//
//		MerchEntity merch = merchs.get(0);
//		respModel.setCategory(merch.getMerch_type());
//		respModel.setVarie(merch.getProduct_varie() == 0 ? "散" : "团");
//		if (merch.getStart_time() != null) {
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//			respModel.setTravelDate(df.format(merch.getStart_time()));
//		}
//	}
//
//	/**
//	 * 计算支付类型.
//	 * @param pay_way
//	 * @return
//	 */
//	private String setPayWay(int pay_way) {
//		String message;
//		try {
//			PayWayEnum payWay = PayWayEnum.getPayWay(pay_way);
//			message = payWay.getMessage();
//		} catch (PaymentTypeException e) {
//			//ignore it.
//			message = "未知";
//		}
//		return message;
//	}
//
//	/**
//	 * 加载订单对应的填单项.
//	 * @param order_id
//	 */
//	private List<OrderExtendAttrEntity> onloadContactExtendAttrs(String orderId, String group) {
//		OrderExtendAttrEntity extendAttr = new OrderExtendAttrEntity();
//		extendAttr.setAttr_group("contact");
//		extendAttr.setOrder_id(orderId);
//		List<OrderExtendAttrEntity> contacts = null;
//		try {
//			contacts = orderExtendAttrReadMapper.queryOrderExtendAttrListByParam(extendAttr);
//		} catch (Throwable e) {
//			logger.error("订单详情-售票员, 联系人信息获取失败. orderId: [" + orderId + "]", e);
//		}
//		return contacts;
//	}
//
//	/**
//	 * 组装联系人信息.
//	 * @param respModel
//	 * @param contacts
//	 */
//	private ContacteeOutModel assembleContactsToOrderDetail(List<OrderExtendAttrEntity> contacts) {
//		if (contacts == null || contacts.isEmpty()) {
//			return null;
//		}
//
//		ContacteeOutModel outModel = new ContacteeOutModel();
//		for (OrderExtendAttrEntity contact : contacts) {
//			if (contact.getAttr_key().equals("contact_name")) {
//				outModel.setContactee(contact.getAttr_value());
//			} else if (contact.getAttr_key().equals("contact_mobile")) {
//				outModel.setContactMobile(contact.getAttr_value());
//			} else if (contact.getAttr_key().equals("contact_spelling")) {
//				outModel.setContacteeSpell(contact.getAttr_value());
//			} else if (contact.getAttr_key().equals("contact_email")) {
//				outModel.setEmail(contact.getAttr_value());
//			} else if (contact.getAttr_key().equals("contact_idcard")) {
//				outModel.setIdcardNo(contact.getAttr_value());
//			}
//		}
//		return outModel;
//	}
//
//	/**
//	 * 组装其他填单项.
//	 * @param others
//	 */
//	private List<FilledModel> assembleOtherFilledToOrderDetail(List<OrderExtendAttrEntity> others) {
//		if (others == null || others.isEmpty()) {
//			return null;
//		}
//
//		List<FilledModel> filleds = new ArrayList<FilledModel>();
//		for (OrderExtendAttrEntity item : others) {
//			FilledModel filledModel = new FilledModel();
//			filledModel.setGroup(item.getAttr_group());
//			filledModel.setAttr_key(item.getAttr_key());
//			filledModel.setAttr_value(item.getAttr_value());
//			filleds.add(filledModel);
//		}
//		return filleds;
//	}
//}
