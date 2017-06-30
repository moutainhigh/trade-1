package com.pzj.core.trade.sms.engine.content;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pzj.trade.sms.exception.SmsException;

public class SMSContent {
	private final static Logger logger = LoggerFactory.getLogger(SMSContent.class);
	/**
	 * 魔方联系电话
	 */
	//	public static final String mftourPhone = "400-880-8989";

	public static final String mftourCodeUrl;

	public static final String ticketSupplierId;

	static {
		Properties pro = new Properties();
		try {
			pro.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sysconfig.properties"));
		} catch (IOException e) {
			logger.error("SMSContent 初始化异常", e);
			throw new SmsException("SMSContent 初始化异常");
		}
		mftourCodeUrl = pro.getProperty("mftourcode.url");
		ticketSupplierId = pro.getProperty("mftour.ticket.supplierid");
	}

	/**
	 * 四舍五入获取2位小数的方法
	 * @return
	 */
	public static final double get2DecimalPlaces(double number) {
		BigDecimal b = new BigDecimal(number);
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
