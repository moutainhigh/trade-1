package com.pzj.core.trade.sms.engine.convert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.base.entity.SysContacts;
import com.pzj.base.entity.SysUser;
import com.pzj.base.entity.query.SysContactsQueryParam;
import com.pzj.base.service.sys.IContactsService;
import com.pzj.base.service.sys.IUserService;
import com.pzj.commons.utils.StringUtils;
import com.pzj.core.customer.microshop.MicroshopInfo;
import com.pzj.core.customer.microshop.MicroshopService;
import com.pzj.core.smp.delivery.IShortMessageService;
import com.pzj.core.smp.delivery.MessageBean;
import com.pzj.core.smp.delivery.MessageDict;
import com.pzj.core.smp.delivery.MessageHead;
import com.pzj.core.trade.sms.engine.common.SmsSendTypeEnum;
import com.pzj.core.trade.sms.engine.exception.SMSException;
import com.pzj.core.trade.sms.engine.model.SMSSendModel;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.BasicTypeConverter;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;

/**
 * 发送短信的周边服务处理
 * @author kangzl
 *
 */
@Component("sendSMSAroundConvert")
public class SendSMSAroundConvert {
	private final static Logger logger = LoggerFactory.getLogger(SendSMSRuleConvert.class);
	@Autowired
	private IUserService userService;

	@Autowired
	private IContactsService icontactsService;

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private IShortMessageService shortMessageService;

	@Autowired
	private MicroshopService microshopService;

	final static long TIME_DURATION = 180000;//短信过期时间

	/**
	 * 分销商电话
	 * @param resellerId
	 * @return
	 */
	public String getResellerPhone(final long resellerId, final OrderEntity saleOrder) {
		String resellerPhone = null;
		Result<MicroshopInfo> result = microshopService.queryMicroshopByMasterId(resellerId);
		if (!result.isOk() || Check.NuNObject(result.getData()) || Check.NuNString(result.getData().getPhoneNum())) {
			logger.error("获取微店联系人电话信息异常,分销商ID:" + resellerId + ",订单ID:" + saleOrder.getOrder_id());
			//			throw new SMSException(10706, "获取微店分销商联系电话异常");
		} else {
			resellerPhone = result.getData().getPhoneNum();
		}
		if (Check.NuNString(resellerPhone)) {
			final SysUser resellerUser = userService.getById(resellerId);
			resellerPhone = resellerUser.getCorporaterMobile();
		}
		return resellerPhone;

	}

	/**
	 * 供应商电话
	 * @param supplierId
	 * @return
	 */
	public String getSupplierPhone(final long supplierId) {
		final SysUser supplierUser = userService.getById(supplierId);
		return supplierUser.getCorporaterMobile();
	}

	/**
	 * 获取销售订单对应的供应商ID(单产品)
	 * @param orderEntity
	 * @return
	 */
	public long getSaleOrderSupplierId(final OrderEntity orderEntity) {
		//判断订单是否是直签订单,直签1 
		long suplierId = 0;
		if (orderEntity.getIs_direct() == 1 || BasicTypeConverter.parseInt(orderEntity.getVersion(), 1) == 1) {
			suplierId = orderEntity.getSupplier_id();
		} else {
			final OrderEntity order = orderWriteMapper.getOrderListByPorderId(orderEntity.getOrder_id());
			//for (OrderEntity order : orders) {
			//				if (order.getOrder_id().equals(orderEntity.getOrder_id())) {
			//					continue;
			//				}
			suplierId = order.getSupplier_id();
			//break;
			//}
		}
		return suplierId;
	}

	/**
	 * 供应商联系人电话
	 * @param supplierId
	 * @return
	 */
	public List<String> getSupplierContacteePhone(final long supplierId) {
		//		ServiceContext.getServiceContext()
		final ServiceContext context = null;
		final Set<String> supplierContacteeNo = new HashSet<String>();
		final SysContactsQueryParam queryParam = new SysContactsQueryParam();
		queryParam.setSupplierId(supplierId);
		final Result<ArrayList<SysContacts>> suppliercontactee = icontactsService.queryByParam(queryParam, context);
		if (suppliercontactee.isOk()) {
			final List<SysContacts> resultlist = suppliercontactee.getData();
			for (final SysContacts constact : resultlist) {
				supplierContacteeNo.add(constact.getPhoneNumber());
			}
		}
		return new ArrayList<String>(supplierContacteeNo);
	}

	/**
	 * 特殊短信
	 * @param msg
	 * @param supplierId
	 * @param smsVo
	 * @return
	 */
	//	private String convertContacteeMsg(String msg, final Long supplierId) {
	//		if (Check.NuNObject(supplierId) || Check.NuNString(SMSContent.ticketSupplierId)
	//				|| SMSContent.ticketSupplierId.indexOf(String.valueOf(supplierId)) == -1) {
	//			return msg;
	//		}
	//		msg = msg.replace("详询" + SMSContent.mftourPhone, "详询咨询电话：0535-6333372，投诉电话：0535-6333382");
	//		return msg;
	//	}

	/**
	 * 发送短信的方法
	 * @param phoneNum
	 * @param SMSContent
	 * @param sendType
	 */
	public void sendSMS(final SMSSendModel sendModel, final SmsSendTypeEnum sendType) {
		if (Check.NuNObject(sendModel) || Check.NuNString(sendModel.getSaleOrderId())
				|| Check.NuNString(sendModel.getPhoneNum()) || Check.NuNString(sendModel.getMsg())) {
			return;
		}
		//		if (sendType.getKey() == SmsSendTypeEnum.orderContact.getKey()) {
		//			final String newSMS = convertContacteeMsg(sendModel.getMsg(), sendModel.getSupplierId());
		//			sendModel.setMsg(newSMS);
		//		}
		logger.info("SMSContent.sendSMS saleOrderId:{},msgContent:[" + sendModel.getMsg() + "],phone:{},sendType:{}",
				sendModel.getSaleOrderId(), sendModel.getPhoneNum(), sendType.getMsg());
		//		new Thread() {
		//			@Override
		//			public void run() {
		//				logger.info("SMSContent.sendSMS saleOrderId:{},msgContent:[" + sendModel.getMsg() + "],phone:{},sendType:{}", sendModel.getSaleOrderId(),
		//						sendModel.getPhoneNum(), sendType.getMsg());
		//				SMSSender.sendSMS(sendModel.getPhoneNum(), sendModel.getMsg());
		//			}
		//		}.start();
		final MessageBean msgBean = new MessageBean();
		try {
			logger.info("SMSContent.sendSMS saleOrderId:{},msgContent:[" + sendModel.getMsg() + "],phone:{},sendType:{}",
					sendModel.getSaleOrderId(), sendModel.getPhoneNum(), sendType.getMsg());
			//设置短信内容
			msgBean.setContent(sendModel.getMsg());
			//设置短信协议头
			msgBean.setHead(new MessageHead("trade:" + sendType.getKey(), MessageDict.Priority.A,
					SendSMSAroundConvert.TIME_DURATION));
			//设置短信发送的手机号
			msgBean.setPhoneNums(new ArrayList<String>());
			msgBean.getPhoneNums().add(sendModel.getPhoneNum());
			Result<Boolean> result = shortMessageService.sendMessage(msgBean);
			logger.info("调研那个发送短信服务成功后,获取的返回值内容：", result);
			if (!result.isOk()) {
				throw new SMSException(17001, result.getErrorMsg());
			}
		} catch (Exception e) {
			logger.error("调用发送短信服务异常,输入参数:" + StringUtils.replaceAllBlankStr(JSONConverter.toJson(msgBean)) + ",errorContent:",
					e);
		}
	}
}
