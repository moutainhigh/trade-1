package com.pzj.core.trade.order.filter.strategy;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.strategy.model.response.StrategyRebateOutModel;
import com.pzj.core.strategy.service.StrategyRebateService;
import com.pzj.core.trade.order.engine.exception.RebateFilterException;
import com.pzj.core.trade.order.engine.exception.StrategyFilterException;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;

/**
 * 政策返利计算器.
 * @author YRJ
 *
 */
@Component(value = "rebateCalculator")
class RebateCalculator implements ObjectConverter<Long, Long, Double> {

	private final static Logger logger = LoggerFactory.getLogger(RebateCalculator.class);

	@Autowired
	private StrategyRebateService strategyRebateSerivce;

	@Override
	public Double convert(Long prodId, Long strategyId) {
		Result<ArrayList<StrategyRebateOutModel>> result;
		try {
			result = strategyRebateSerivce.findRebate(strategyId);
		} catch (Throwable e) {
			logger.error("商品[" + prodId + "], 政策[" + strategyId + "], 获取返利信息失败.", e);
			throw new RebateFilterException(10501, "商品[" + prodId + "], 政策[" + strategyId + "], 获取返利信息失败.", e);
		}

		if (!result.isOk()) {
			throw new StrategyFilterException(10500, "商品[" + prodId + "], 政策[" + strategyId + "], 获取返利信息失败. 返回错误码["
					+ result.getErrorCode() + "], 错误描述[" + result.getErrorMsg() + "].");
		}

		ArrayList<StrategyRebateOutModel> rebates = result.getData();
		logger.info("商品[" + prodId + "], 政策[" + strategyId + "], 获取返利--> " + (JSONConverter.toJson(rebates)));

		double rebateAmount = calculate(rebates);
		return rebateAmount;
	}

	/**
	 * 计算返利金额.
	 * @param data
	 * @return
	 */
	private double calculate(ArrayList<StrategyRebateOutModel> rebates) {
		double rebateAmount = 0.0d;
		if (rebates == null) {
			return rebateAmount;
		}

		for (StrategyRebateOutModel rebate : rebates) {
			//rebateAmount += AmountConverter.unitDivideToYuan(rebate.getRebateAmount());
			rebateAmount += rebate.getRebateAmount() == null ? 0.0d : rebate.getRebateAmount();
		}

		return rebateAmount;
	}

}
