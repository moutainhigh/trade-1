package com.pzj.core.trade.order.filter.strategy;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pzj.core.product.common.global.ProductRuleItem.OverdueVerificationEnum;
import com.pzj.core.product.common.model.response.ProductRuleOutModel;
import com.pzj.core.product.supplier.global.TimeUnitEnum;
import com.pzj.core.trade.order.engine.RuleReader;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.merch.common.VourTypeEnum;

public class ProductOverdueTimeUtil {

	private final static Logger logger = LoggerFactory.getLogger(ProductOverdueTimeUtil.class);
	//一小时的毫秒数.
	private static final long HOUR_STAMP = 60 * 60 * 1000;
	//一天的毫秒数.
	private static final long DAY_STAMP = 24 * HOUR_STAMP;

	public static long getMerchOverdueTime(int vourType, ProductRuleOutModel ruleModels, Date expireTime) {
		if (vourType == VourTypeEnum.CONTACT.getVourType() || vourType == VourTypeEnum.NUN.getVourType()) {
			return expireTime.getTime();
		}
		//		ProductRuleOutModel verificationRuleType = RuleReader.readProdRuleInstByName(
		//				VerificationRuleEnum.verificationRuleType.getName(), ruleModels);
		//		if (verificationRuleType == null) {
		//			return expireTime.getTime();
		//		}

		//		if (verificationRuleType.getValue() == VerificationTimeTypeEnum.VERIFICATION_TYPE_CHECKIN.getValue()) {//核销后立即清算
		//			return expireTime.getTime();
		//		}

		ProductRuleOutModel unit = RuleReader.readProdRuleInstByName(OverdueVerificationEnum.overdueVerificationType.getName(),
				ruleModels);
		logger.info("产品逾期时间类型模型：{}", JSONConverter.toJson(unit));
		if (unit == null) {
			return expireTime.getTime();
		}
		ProductRuleOutModel time = RuleReader.readProdRuleInstByName(OverdueVerificationEnum.overdueVerificationTime.getName(),
				ruleModels);
		logger.info("产品逾期时间value模型：{}", JSONConverter.toJson(time));
		int timeValue = 1;
		if (time != null) {
			timeValue *= time.getValue().intValue();
		}
		long interval = 0;
		if (unit.getValue() == TimeUnitEnum.DAY.getValue()) {
			interval = DAY_STAMP * timeValue;
		} else if (unit.getValue() == TimeUnitEnum.HOUR.getValue()) {
			interval = HOUR_STAMP * timeValue;
		}
		logger.info("产品逾期时间在产品有效期之后:{}秒", interval / 1000);
		return interval + expireTime.getTime();
	}

}
