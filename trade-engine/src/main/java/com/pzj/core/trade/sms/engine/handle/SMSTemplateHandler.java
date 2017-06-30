package com.pzj.core.trade.sms.engine.handle;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.commons.utils.SMSSender;
import com.pzj.core.product.model.QuerySeatRecordResponse;
import com.pzj.core.product.model.QueryValidSeatRecordResponse;
import com.pzj.core.product.service.SeatRecordQueryService;
import com.pzj.core.trade.voucher.engine.event.QrCodeEvent;
import com.pzj.core.trade.voucher.engine.event.QrCodeModel;
import com.pzj.core.trade.voucher.read.VoucherDockInfoEntityReadMapper;
import com.pzj.core.trade.voucher.read.VoucherReadMapper;
import com.pzj.framework.context.Result;
import com.pzj.framework.toolkit.Check;
import com.pzj.framework.toolkit.TimeHelper;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.OrderReadMapper;
import com.pzj.voucher.entity.VoucherDockInfoEntity;
import com.pzj.voucher.entity.VoucherEntity;
import com.pzj.voucher.exception.VoucherException;

/**
 * 短信模板处理器.
 * @author YRJ
 *
 */
@Component(value = "sMSTemplateHandler")
public class SMSTemplateHandler {

	private final static Logger logger = LoggerFactory.getLogger(SMSTemplateHandler.class);

	@Resource(name = "voucherReadMapper")
	private VoucherReadMapper voucherReadMapper;

	@Resource(name = "orderReadMapper")
	private OrderReadMapper orderReadMapper;

	@Resource(name = "merchReadMapper")
	private MerchReadMapper merchReadMapper;

	@Autowired
	private VoucherDockInfoEntityReadMapper voucherDockInfoEntityReadMapper;

	@Resource(name = "qrCodeEvent")
	private QrCodeEvent qrCodeEvent;

	@Autowired
	private SeatRecordQueryService seatRecordQueryService;

	/**
	 * 发送短信, 根据短信模板填充变量.
	 * @param transactionId	订单交易ID
	 * @param orderId	待发短信的订单号
	 */
	public void sendSMS(final String transactionId, final String orderId, final String smsTemplate) {
		final VoucherDockInfoEntity dockInfo = voucherDockInfoEntityReadMapper.queryVoucherDockInfoEntityByTransactionId(transactionId);
		final List<VoucherEntity> vouchers = voucherReadMapper.queryVoucherByTransactionId(transactionId);
		final String mobile = onloadContactMobile(transactionId, orderId);
		final String context = assembleSMS(vouchers, transactionId, orderId, dockInfo, smsTemplate);
		if (context != null) {
			SMSSender.sendSMS(mobile, context);
		}
	}

	private String assembleSMS(final List<VoucherEntity> vouchers, final String transactionId, final String orderId,
			final VoucherDockInfoEntity vourDockEntity, String smsTemplate) {
		if (Check.NuNStrStrict(smsTemplate)) {
			return null;
		}

		smsTemplate = smsTemplate.replaceAll("%orderId%", orderId);
		if (vourDockEntity != null && vourDockEntity.getAuxiliaryCode() != null) {
			smsTemplate = smsTemplate.replaceAll("%auxiliaryCode%", vourDockEntity.getAuxiliaryCode());
		}

		final StringBuffer productInfo = new StringBuffer();
		String seats = "";
		StringBuilder showTime = new StringBuilder();
		String playTime = null;
		QrCodeModel qrCode = null;

		final OrderEntity order = orderReadMapper.getOrderById(transactionId);

		final VoucherEntity voucher = vouchers.get(0);
		qrCode = qrCodeEvent.queryQrCodeByOrderId(voucher, order);
		if (!Check.NuNStrStrict(qrCode.getQrCode())) {
			smsTemplate = smsTemplate.replaceAll("%serviceCode%", qrCode.getQrCode());
			smsTemplate = smsTemplate.replaceAll("%qrCode%", qrCode.getQrCodeUrl());
		}

		final int difference = TimeHelper.getDateDifference(voucher.getStartTime(), voucher.getExpireTime());
		final String effectiveTime = "(有效期" + difference + "天)";

		final String startTime = TimeHelper.getTime(voucher.getStartTime(), "M月d日");
		playTime = startTime + effectiveTime;
		if (!Check.NuNStrStrict(playTime)) {
			smsTemplate = smsTemplate.replaceAll("%playTime%", playTime);
		}
		//在一证一票的情况下这个地方有问题
		//故做如下修改，by GLG 
		//		final List<MerchEntity> merchs = merchReadMapper.getMerchByVoucherId(voucher.getVoucherId());
		final List<MerchEntity> merchs = merchReadMapper.getMerchByOrderId(transactionId);
		productInfo.append(merchs.get(0).getMerch_name());

		//		for (final MerchEntity merch : merchs) {
		//			productInfo.append(merch.getSku_name());
		//			productInfo.append(merch.getTotal_num()).append("张");
		//			productInfo.append("、");
		//		}
		Map<Long, Integer> merchNums = new HashMap<Long, Integer>();
		Map<Long, String> merchNames = new HashMap<Long, String>();
		for (final MerchEntity merch : merchs) {
			if (merchNums.containsKey(merch.getProduct_id())) {
				merchNums.put(merch.getProduct_id(), merchNums.get(merch.getProduct_id()) + merch.getTotal_num());
			} else {
				merchNames.put(merch.getProduct_id(), merch.getSku_name());
				merchNums.put(merch.getProduct_id(), merch.getTotal_num());
			}
		}
		for (Long productId : merchNums.keySet()) {
			productInfo.append(merchNames.get(productId));
			productInfo.append(merchNums.get(productId)).append("张");
			productInfo.append("、");
		}
		//end
		productInfo.deleteCharAt(productInfo.length() - 1);
		if (productInfo.length() > 0) {
			smsTemplate = smsTemplate.replaceAll("%sceneName%", productInfo.toString());
			smsTemplate = smsTemplate.replaceAll("%productName%", productInfo.toString());
		}

		final Date showStartTime = merchs.get(0).getShow_start_time();
		final Date showEndTime = merchs.get(0).getShow_end_time();
		if (showStartTime != null && showEndTime != null) {
			String startDate = TimeHelper.getTime(showStartTime, "y年M月d日");
			String startDateTime = TimeHelper.getTime(showStartTime, "HH:mm");
			String endDateTime = TimeHelper.getTime(showEndTime, "HH:mm");
			showTime.append(startDate).append(startDateTime).append("至").append(endDateTime);
		}
		seats = onloadSeatsByTransactionId(transactionId);
		smsTemplate = smsTemplate.replaceAll("%seatInfo%", seats);
		if (!Check.NuNStrStrict(showTime.toString())) {
			smsTemplate = smsTemplate.replaceAll("%showTime%", showTime.toString().trim());
		}

		logger.info("orderId :" + orderId + " >> 短信内容 >>" + smsTemplate);
		return smsTemplate;
	}

	private String onloadSeatsByTransactionId(final String transactionId) {
		String seats = "";
		try {
			final Result<QueryValidSeatRecordResponse> result = seatRecordQueryService.queryValidSeatRecordByTransactionId(transactionId);
			//			seatQueryService.querySeatByTransactionId(transactionId, null);
			if (!result.isOk()) {
				logger.error("订单[" + transactionId + "], 查询座位失败, 错误码: [" + result.getErrorCode() + "], 错误描述: [" + result.getErrorMsg() + "].");
				return seats;
			}
			if (!Check.NuNObject(result.getData())) {
				seats = assemby(result.getData().getQuerySeatRecordResponses());
			}
		} catch (final Throwable e) {
			logger.error("订单[" + transactionId + "], 查询座位失败.", e);//ignore.
		}
		return seats;
	}

	private String assemby(final List<QuerySeatRecordResponse> seats) {
		final StringBuilder toStr = new StringBuilder();
		for (final QuerySeatRecordResponse seat : seats) {
			toStr.append(seat.getSeatName());
		}
		if (toStr.length() > 0) {
			toStr.deleteCharAt(toStr.length() - 1);
		}
		return toStr.toString();
	}

	/**
	 * 通过订单查询联系人手机号.
	 * @param transactionId
	 * @param orderId
	 * @return
	 */
	private String onloadContactMobile(final String transactionId, final String orderId) {
		final OrderEntity order = orderReadMapper.getOrderById(orderId);
		final String mobile = order.getContact_mobile();
		if (mobile == null) {
			throw new VoucherException("交易号：" + transactionId + "; 订单" + orderId + "取票人手机号为空,发送短信");
		}
		return mobile;
	}

}
