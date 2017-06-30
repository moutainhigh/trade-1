/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.refund.engine.common;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.model.RefundRuleLimit;

/**
 * 
 * @author dongchf
 * @version $Id: DateUtil.java, v 0.1 2016年10月17日 下午5:31:38 dongchunfu Exp $
 */
@Component(value = "dateUtil")
public class DateUtil {

	/**
	 * 获取退款相关时间点的前一刻时间
	 * @param skuProduct
	 * @param targetDate
	 * @return
	 */
	public Date getAfterPointDate(RefundRuleLimit refundRuleLimit, Date targetDate) {
		//退款日期后天数
		int prorefundDays = refundRuleLimit.getProrefundDays();
		//退款日期后当天时刻-(时）
		int prorefundHour = refundRuleLimit.getProrefundHour();
		//退款日期后当天时刻-（分）
		int prorefundMinute = refundRuleLimit.getProrefundMinute();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(targetDate);
		calendar.add(Calendar.DAY_OF_YEAR, prorefundDays);
		calendar.set(Calendar.HOUR_OF_DAY, prorefundHour);
		calendar.set(Calendar.MINUTE, prorefundMinute);
		return calendar.getTime();
	}

	public Date getBeforPointDate(RefundRuleLimit refundRuleLimit, Date targetDate) {
		//退款日期前天数
		int prerefundDay = refundRuleLimit.getPrerefundDays();
		//退款日期前当天时刻/时
		int rerefundHour = refundRuleLimit.getPrerefundHour();
		//退款日期前当天时刻- 分
		int rerefundMinute = refundRuleLimit.getPrerefundMinute();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(targetDate);
		calendar.add(Calendar.DAY_OF_YEAR, -prerefundDay);
		calendar.set(Calendar.HOUR_OF_DAY, rerefundHour);
		calendar.set(Calendar.MINUTE, rerefundMinute);
		return calendar.getTime();
	}

}
