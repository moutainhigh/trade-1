package com.pzj.core.trade.order.filter.merch;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.pzj.core.product.common.global.ProductRuleItem.PlaytimeEnum;
import com.pzj.core.product.common.model.response.ProductRuleOutModel;
import com.pzj.core.product.supplier.global.NotNeedPlayTimeEnum;
import com.pzj.core.product.supplier.global.TimeUnitEnum;
import com.pzj.core.trade.order.engine.RuleReader;
import com.pzj.core.trade.order.engine.exception.PlayTimeException;
import com.pzj.core.trade.order.engine.model.MerchModel;

/**
 * 游玩时间计算器.
 * @author YRJ
 *
 */
@Component(value = "playTimeCalculator")
class PlayTimeCalculator {

	public PlayTime calculate(MerchModel merchModel, ProductRuleOutModel outModel, Date playTime) {
		//1. 计算出游/入住日期.
		Date visitTime = getVisitTime(merchModel.getIsPlayTime(), playTime);

		//2. 计算游玩有效期.
		Date expireTime = getExpireTime(outModel, merchModel, visitTime);
		return new PlayTime(visitTime, expireTime);
	}

	/**
	 * 计算游玩/入住日期.
	 * @param value
	 * @return
	 */
	private Date getVisitTime(boolean needable, Date playTime) {
		Date _play = playTime;
		if (!needable || _play == null) {//不需要填写游玩日期或游玩日期为空.
			_play = new Date();
		}
		Date visitTime = getPlayTime(_play, 0, 0, 0, 0);
		return visitTime;
	}

	public static void main(String[] args) {
		PlayTimeCalculator p = new PlayTimeCalculator();
		Date d = p.getPlayTime(new Date(), 0, 23, 59, 59);
		d = new Date(1483027199054L);
		System.out.println(d);
	}

	/**
	 * 计算商品游玩有效期.
	 * @param outModels
	 * @param merchModel
	 * @param visitTime
	 * @return
	 */
	private Date getExpireTime(ProductRuleOutModel outModels, MerchModel merchModel, Date visitTime) {
		if (merchModel.getIsPlayTime()) {
			Date expireTime = $getExpireTime(outModels, visitTime);
			return expireTime;
		}

		ProductRuleOutModel noPlaytimeOrdertimeType = RuleReader.readProdRuleInstByName(
				PlaytimeEnum.noPlaytimeOrdertimeType.getName(), outModels);
		if (noPlaytimeOrdertimeType == null) {
			throw new PlayTimeException(10504, "产品[" + merchModel.getProdId()
					+ "], 不需要填写游玩/入住日期, 但商品有效期计算类型[noPlaytimeOrdertimeType]不存在, 无法计算游玩有效期时间.");
		}

		/*
		 * 根据下单时间计算有效期, 假定产品设置数据出现异常, 默认也是基于下单时间计算.
		 */
		if (NotNeedPlayTimeEnum.UseAvailableStartDate.getValue() != noPlaytimeOrdertimeType.getValue().intValue()) {
			Date expireTime = getPlayTime(visitTime, 0, 23, 59, 59);
			return expireTime;
		}

		//根据产品可用日期计算有效期.
		Date expireTime = $getExpireTime(outModels, merchModel.getUseEndDate());
		return expireTime;
	}

	private Date getPlayTime(Date playTime, int day, int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(playTime);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		return calendar.getTime();
	}

	private Date $getExpireTime(ProductRuleOutModel outModels, Date visitTime) {
		ProductRuleOutModel timeValueModel = RuleReader
				.readProdRuleInstByName(PlaytimeEnum.ordertimeValue.getName(), outModels);
		int timeValue = 0;
		if (timeValueModel != null) {
			timeValue = timeValueModel.getValue().intValue();
		}

		ProductRuleOutModel timeUnitModel = RuleReader.readProdRuleInstByName(PlaytimeEnum.ordertimeUnit.getName(), outModels);
		if (timeUnitModel != null) {
			int timeUnit = timeUnitModel.getValue().intValue();
			int day = 0, hour = 0, minute = 0, second = 0;
			second = -1;//产品要求在有效期是计算时间去一秒
			if (timeUnit == TimeUnitEnum.DAY.getValue()) {
				day = timeValue;
			} else if (timeUnit == TimeUnitEnum.HOUR.getValue()) {
				hour = timeValue;
			} else if (timeUnit == TimeUnitEnum.MINUTE.getValue()) {
				minute = timeValue;
			}
			Date expireTime = getPlayTime(visitTime, day, hour, minute, second);
			return expireTime;
		}
		return null;
	}

	class PlayTime {
		private Date playStartTime;
		private Date playEndTime;

		private PlayTime(Date playStartTime, Date playEndTime) {
			this.playStartTime = playStartTime;
			this.playEndTime = playEndTime;
		}

		public Date getPlayStartTime() {
			return playStartTime;
		}

		public Date getPlayEndTime() {
			return playEndTime;
		}
	}
}
