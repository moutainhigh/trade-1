package com.pzj.trade.order.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.framework.cache.core.CacheService;

@Component
public class OrderIDGenerater {
	@Autowired
	private CacheService cacheService;

	public static String generate(String prefix) {
		int hashcode = UUID.randomUUID().hashCode();
		if (hashcode < 0) {
			hashcode = Math.abs(hashcode);
		}
		return prefix + hashcode;
	}

	public String generateOrderId(int salePort, int bookType, long supplierId) {
		StringBuffer id = new StringBuffer();
		if (salePort == SalePortEnum.OFFLINE_WINDOW.getValue()) {
			id.append("2");
		} else {
			id.append("1");
		}
		id.append(bookType);
		id.append(String.format("%03d", supplierId % 1000));

		String time = getTimeFlag();
		id.append(time);

		String sequence = getPrimaryKeyByRedis(supplierId);
		id.append(sequence);
		return id.toString();
	}

	/**
	 * 
	 * @param supplierId
	 * @return
	 */
	private String getPrimaryKeyByRedis(long supplierId) {
		String key = "trade:" + supplierId % 1000;
		Long sequence = cacheService.strIncrNumber(key);
		if (sequence == 1) {
			//第一次插入需要设置过期时间
			long unixTime = getUnixTime();
			cacheService.strIncrNumber(key, 100);
			sequence = cacheService.strIncrNumber(key);
			cacheService.keyExpireat(key, unixTime);
		}
		return String.format("%05d", sequence);
	}

	public static void main(String[] args) {
		System.out.println(String.format("%05d", 250000));
	}

	/**
	 * 
	 * @return
	 */
	private String getTimeFlag() {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String time = df.format(today);
		return time;
	}

	/**
	 * 
	 */
	private static long getUnixTime() {
		Date now = new Date();
		Calendar canlendar = Calendar.getInstance();
		canlendar.setTime(now);
		canlendar.add(Calendar.DAY_OF_MONTH, 1);
		canlendar.set(Calendar.HOUR_OF_DAY, 0);
		canlendar.set(Calendar.MINUTE, 0);
		canlendar.set(Calendar.SECOND, 0);
		return canlendar.getTime().getTime() / 1000;
	}

}
