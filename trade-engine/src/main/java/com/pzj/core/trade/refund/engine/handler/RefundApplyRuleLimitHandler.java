package com.pzj.core.trade.refund.engine.handler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.IsPartyRefundEnum;
import com.pzj.core.trade.refund.engine.common.RefundRuleTypeEnum;
import com.pzj.core.trade.refund.engine.exception.PartRefundException;
import com.pzj.core.trade.refund.engine.exception.RefundDateNotInRangeException;
import com.pzj.core.trade.refund.engine.exception.RefundException;
import com.pzj.core.trade.refund.engine.exception.RefundRuleNotFoundException;
import com.pzj.core.trade.refund.engine.model.MerchCalculateModel;
import com.pzj.core.trade.refund.engine.model.RefundApplyMerchModel;
import com.pzj.core.trade.refund.engine.model.RefundRuleLimit;

/**
 * 退款规则效验
 * @author kangzl
 *
 */
@Component(value = "refundApplyRuleLimitHandler")
public class RefundApplyRuleLimitHandler {

	private final static Logger logger = LoggerFactory.getLogger(RefundApplyRuleLimitHandler.class);

	/**
	 * 效验退款规则,获取退款金额计算器
	 */
	public MerchCalculateModel refundable(final RefundRuleLimit ruleLimit, final RefundApplyMerchModel merch,
			final int isParty, final Date refundApplyTime) {
		if (!(ruleLimit.refunable())) {//商品不可退.
			logger.warn("订单[" + merch.getOrderId() + "], 商品[" + merch.getMerchId() + "], refundable: ["
					+ ruleLimit.getRefundAllowed() + "], 不支持退款.");
			throw new RefundException(10310, "商品[" + merch.getMerchId() + "]不支持退款.");
		}

		//退款规则设置为: 随时可退, 并计算退款手续费.
		if (ruleLimit.refundAnyTime()) {
			isWhileRefund(merch, isParty, ruleLimit.getRefundAnyTimeQuantityType());
			final MerchCalculateModel skuRefundModel = new MerchCalculateModel(ruleLimit.getRefundAnyTimeFee(),
					RefundRuleTypeEnum.ANYTIME.getType());
			return skuRefundModel;
		}

		//退款规则设置为: 指定退票时间段, 需要根据当前申请时间判断属于前、后可退范围, 并计算退款手续费.
		final Date pointDate = getRefundPointDate(ruleLimit.getRefundDateType(), merch.getTravelTime(), merch.getExpireTime());
		if (pointDate == null) {
			throw new RefundRuleNotFoundException(10310, "订单[" + merch.getOrderId() + "], 商品[" + merch.getMerchId()
					+ "], 退款的日期类型错误. refundDateType: [" + ruleLimit.getRefundDateType() + "]");
		}

		//退款时间在前退款规则之前情况, 判断前退款规则是否可退.
		final Date preRefundTime = ruleLimit.beforeRefundMaxTime(pointDate);
		if (refundApplyTime.before(preRefundTime)) {
			if (!ruleLimit.preRefundable()) {
				throw new RefundDateNotInRangeException(10311, "订单[" + merch.getOrderId() + "], 商品[" + merch.getMerchName()
						+ "], 在前时间不可退款.");
			}
			isWhileRefund(merch, isParty, ruleLimit.getPrerefundQuantityType());
			//生成退款金额计算规则.
			final MerchCalculateModel skuRefundModel = new MerchCalculateModel(ruleLimit.getPrerefundDistributorFeetype(),
					ruleLimit.getPrerefundDistributorFeevalue(), RefundRuleTypeEnum.BEFORE.getType());
			return skuRefundModel;
		}

		//退款时间在后退款规则之前情况, 判断后退款规则是否可退.
		final Date proRefundTime = ruleLimit.afterRefundMaxTime(pointDate);
		if (refundApplyTime.before(proRefundTime)) {
			if (!ruleLimit.proRefundable()) {
				throw new RefundDateNotInRangeException(10311, "订单[" + merch.getOrderId() + "], 商品[" + merch.getMerchName()
						+ "], 在前时间不可退款.");
			}
			isWhileRefund(merch, isParty, ruleLimit.getProrefundQuantityType());
			final MerchCalculateModel skuRefundModel = new MerchCalculateModel(ruleLimit.getProrefundDistributorFeetype(),
					ruleLimit.getProrefundDistributorFeevalue(), RefundRuleTypeEnum.AFTER.getType());
			return skuRefundModel;
		}

		throw new RefundDateNotInRangeException(10311, "订单[" + merch.getOrderId() + "], 商品[" + merch.getMerchName()
				+ "], 在前时间不可退款.");
	}

	/**
	 * 根据退款规则
	 * @param refundDateType
	 * @param travelTime
	 * @param expireTime
	 * @return
	 */
	private Date getRefundPointDate(final Integer refundDateType, final Date travelTime, final Date expireTime) {
		final Date pointDate = (refundDateType == 1 ? travelTime : ((refundDateType == 2) ? expireTime : null));
		return pointDate;
	}

	/**
	 * 效验是否可以进行部分退款
	 * @param ruleLimit
	 */
	private void isWhileRefund(final RefundApplyMerchModel merch, final int isPartRefund, final int refundQuantityType) {
		//是否为整单、部分退的规则判断.
		if (isPartRefund == IsPartyRefundEnum.partRefund.getKey() && refundQuantityType != 2) {//设置的不可部分退
			logger.warn("订单[" + merch.getOrderId() + "], 商品[" + merch.getMerchId() + "], applyNum: [" + merch.getApplyNum()
					+ "],  不支持部分退款.");
			throw new PartRefundException(10318, "订单[" + merch.getOrderId() + "], 商品[" + merch.getMerchId() + "], 不支持部分退款");
		}
	}

}
