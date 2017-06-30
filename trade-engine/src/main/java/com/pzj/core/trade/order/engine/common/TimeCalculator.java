package com.pzj.core.trade.order.engine.common;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * 出游日期计算器, 主要用于根据出游日期检索时, 将日期格式参数转换为时间点的时间.
 * @author YRJ
 *
 */
@Component(value = "timeCalculator")
public class TimeCalculator {

	public VisitTime calculator(Date visit_time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(visit_time);

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		long startTime = calendar.getTimeInMillis();

		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		long endTime = calendar.getTimeInMillis();

		return new VisitTime(startTime, endTime);
	}

	public class VisitTime {
		private final long startTime;
		private final long endTime;

		public VisitTime(long startTime, long endTime) {
			this.startTime = startTime;
			this.endTime = endTime;
		}

		public Timestamp getStartTime() {
			return new Timestamp(startTime);
		}

		public Timestamp getEndTime() {
			return new Timestamp(endTime);
		}
	}

}
