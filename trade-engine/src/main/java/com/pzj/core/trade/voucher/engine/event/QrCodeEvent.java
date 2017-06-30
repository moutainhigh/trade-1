package com.pzj.core.trade.voucher.engine.event;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.sms.engine.tools.SMSContentTool;
import com.pzj.framework.shorturl.ShortenUrlUtils;
import com.pzj.trade.merch.common.VourTypeEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.MftourCodeWriteMapper;
import com.pzj.voucher.entity.VoucherEntity;

/**
 * 根据凭证类型计算二维码信息.
 * @author YRJ
 *
 */
@Component(value = "qrCodeEvent")
public class QrCodeEvent {

	@Value(value = "${h5.info.url}")
	private String h5InfoUrl;

	@Resource(name = "mftourCodeWriteMapper")
	private MftourCodeWriteMapper mftourCodeWriteMapper;

	public QrCodeModel doEvent(String orderId, int vourType, String context) {
		if (vourType != 2) {
			return null;
		}

		String qrCode = context;
		String qrCodeUrl = computeQrCodeUrl(orderId);
		return new QrCodeModel(qrCode, qrCodeUrl);
	}

	private String computeQrCodeUrl(String orderId) {
		StringBuilder urlBuffer = new StringBuilder(h5InfoUrl);
		urlBuffer.append(orderId);
		String qrCodeUrl = ShortenUrlUtils.getShortenUrl(urlBuffer.toString());
		return qrCodeUrl;
	}

	public QrCodeModel queryQrCodeByOrderId(VoucherEntity voucher, OrderEntity order) {
		if (voucher.getVoucherContentType() != VourTypeEnum.MFCODE.getVourType()) {
			return new QrCodeModel(null, null);
		}

		//		final List<MftourCodeEntity> mftourCodes = mftourCodeWriteMapper.getMftourCodeByTransactionId(voucher.getTransactionId());

		final String mfcodeUrl = SMSContentTool.getContacteeOrderURL(order.getOrder_id(), order.getCreate_time());
		String qrCodeUrl = ShortenUrlUtils.getShortenUrl(mfcodeUrl);
		String qrCode = voucher.getVoucherContent();
		return new QrCodeModel(qrCode, qrCodeUrl);
	}
}
