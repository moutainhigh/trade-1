package com.pzj.core.trade.order.filter.strategy;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.product.common.model.response.StrategyRuleOutModel;
import com.pzj.core.strategy.common.global.StrategyGlobal.IsLimitAdvanceDueEnum;
import com.pzj.core.strategy.common.global.StrategyGlobal.StrategyRuleAdviceItemEnum;
import com.pzj.core.strategy.common.global.StrategyGlobal.StrategyRuleIsNeedPayment;
import com.pzj.core.strategy.common.global.StrategyGlobal.StrategyRulePerdueNumberItemEnum;
import com.pzj.core.strategy.common.global.StrategyGlobal.StrategyTypeEnum;
import com.pzj.core.strategy.model.response.Strategy4TradeItemOutModel;
import com.pzj.core.strategy.model.response.Strategy4TradeOutModel;
import com.pzj.core.trade.order.engine.RuleReader;
import com.pzj.core.trade.order.engine.exception.StrategyFilterException;
import com.pzj.core.trade.order.engine.model.BookModel;
import com.pzj.core.trade.order.engine.model.StrategyModel;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;

/**
 * 政策模型构建器. 根据政策服务返回信息, 包装为系统内部自定义政策模型.
 * @author YRJ
 *
 */
@Component(value = "strategyBuilder")
class StrategyBuilder implements ObjectConverter<Long, ArrayList<Strategy4TradeOutModel>, StrategyModel[]> {

	private final static Logger logger = LoggerFactory.getLogger(StrategyBuilder.class);

	@Override
	public StrategyModel[] convert(Long prodId, ArrayList<Strategy4TradeOutModel> strategyModels) {
		//1. 从多个产品政策中挑选出该产品的政策.
		List<Strategy4TradeItemOutModel> strategies = filterStrategyOutModel(prodId, strategyModels);

		//2. 计算最优的分销商政策\供应商政策.
		Strategy4TradeItemOutModel resellerStrategyModel = optimalStrategy(prodId, StrategyTypeEnum.MFFXS, strategies);//分销商政策
		Strategy4TradeItemOutModel supplierStrategyModel = optimalStrategy(prodId, StrategyTypeEnum.GYDMF, strategies);//供应商政策

		StrategyModel resellerStrategy = transform(resellerStrategyModel);
		StrategyModel supplierStrategy = transform(supplierStrategyModel);

		logger.info("商品[" + prodId + "], 分销商政策 --> " + (JSONConverter.toJson(resellerStrategy)) + ", 供应商政策 -->"
				+ (JSONConverter.toJson(supplierStrategy)));

		return new StrategyModel[] { resellerStrategy, supplierStrategy };
	}

	/**
	 * 将原始政策模型转换为系统内部政策模型.
	 * @param original
	 */
	private StrategyModel transform(Strategy4TradeItemOutModel original) {
		StrategyModel strategy = new StrategyModel();
		strategy.setStrategyId(original.getId());
		strategy.setStrategyType(original.getType().getType());
		strategy.setAdvicePrice(original.getAdvicePrice());
		strategy.setSettlementPrice(original.getSettlementPrice());
		strategy.setIsWeshopStrategy(original.getIsWeshopStrategy());
		strategy.setChannelId(original.getChannelId());

		strategy.setRebateType(original.getRebateMethod().getId());

		StrategyRuleOutModel ruleModel = original.getStrategyRuleOutModel();
		if (ruleModel == null) {
			//throw new StrategyFilterException(10502, "政策[" + original.getId() + "]规则定义不存在,  请检查产品库中该商品数据是否完整.");
			return strategy;
		}

		List<StrategyRuleOutModel> ruleModels = ruleModel.getNodes();
		{
			StrategyRuleOutModel payable = RuleReader.readStrategyRuleInstByName(
					StrategyRuleIsNeedPayment.STRATEGY_RULE_IS_NEEL_PAYMENT.getName(), ruleModels);
			if (payable != null) {
				strategy.setPayable(payable.getValue());
			}
		}
		{//最大最小购买限制.
			StrategyRuleOutModel leastNumberRule = RuleReader.readStrategyRuleInstByName(
					StrategyRulePerdueNumberItemEnum.LEAST_PERDUE_NUMBER.getName(), ruleModels);
			if (!Check.NuNObject(leastNumberRule)) {
				strategy.setLeastPerdueNumber(leastNumberRule.getValue());
			}
			StrategyRuleOutModel mostNumberRule = RuleReader.readStrategyRuleInstByName(
					StrategyRulePerdueNumberItemEnum.MOST_PERDUE_NUMBER.getName(), ruleModels);
			if (!Check.NuNObject(mostNumberRule)) {
				strategy.setMostPerdueNumber(mostNumberRule.getValue());
			}
		}
		{//提前预定时间限制.
			BookModel bookModel = new BookModel();
			StrategyRuleOutModel bookLimit = RuleReader.readStrategyRuleInstByName(
					StrategyRuleAdviceItemEnum.IS_LIMIT_ADVANCE_DUE.getName(), ruleModels);
			if (!Check.NuNObject(bookLimit)) {
				bookModel.setLimitable(bookLimit.getValue() == IsLimitAdvanceDueEnum.Absolute.getId());
			}
			StrategyRuleOutModel dueDay = RuleReader.readStrategyRuleInstByName(
					StrategyRuleAdviceItemEnum.ADVANCE_DUE_DAYS.getName(), bookLimit.getNodes());
			if (dueDay != null) {
				bookModel.setDueDay(dueDay.getValue().intValue());
			}
			StrategyRuleOutModel dueHour = RuleReader.readStrategyRuleInstByName(
					StrategyRuleAdviceItemEnum.ADVANCE_DUE_HOUR.getName(), bookLimit.getNodes());
			if (dueHour != null) {
				bookModel.setDueHour(dueHour.getValue().intValue());
			}
			StrategyRuleOutModel dueMin = RuleReader.readStrategyRuleInstByName(
					StrategyRuleAdviceItemEnum.ADVANCE_DUE_MINUTE.getName(), bookLimit.getNodes());
			if (dueMin != null) {
				bookModel.setDueMin(dueMin.getValue().intValue());
			}
			strategy.setBook(bookModel);
		}

		return strategy;
	}

	/**
	 * 过滤商品政策.
	 * @param prodId
	 * @param strategyModel
	 * @return
	 */
	private List<Strategy4TradeItemOutModel> filterStrategyOutModel(long prodId,
			ArrayList<Strategy4TradeOutModel> strategyModels) {
		for (Strategy4TradeOutModel strategyModel : strategyModels) {
			if (strategyModel.getSkuId() == prodId) {
				List<Strategy4TradeItemOutModel> strategies = strategyModel.getList();
				return strategies;
			}
		}
		throw new StrategyFilterException(10502, "商品[" + prodId + "]政策不存在, 请检查产品库中该商品数据是否完整.");
	}

	/**
	 * 根据政策类型计算最优的政策.
	 * <p>1. 存在直签政策, 则取直签政策<p>
	 * <p>2. 政策类型为魔方给分销, 则取分销政策</p>
	 * <p>3. 政策类型为供应商给魔方, 则取供应商政策</p>
	 * @param strategies
	 */
	private Strategy4TradeItemOutModel optimalStrategy(Long prodId, StrategyTypeEnum strategyType,
			List<Strategy4TradeItemOutModel> strategies) {
		int length = strategies.size();
		int index = 0;
		Strategy4TradeItemOutModel _strategy = null;

		do {
			Strategy4TradeItemOutModel strategy = strategies.get(index);
			if (StrategyTypeEnum.GYSZQ == strategy.getType()) {
				_strategy = strategy;
				break;
			}
			if (strategyType == strategy.getType()) {//此处处理方式并不明确, 且后期可能还会调整.
				_strategy = strategy;
			}
		} while (++index < length);

		if (_strategy == null) {
			logger.error("计算产品[" + prodId + "][" + strategyType.getName() + "], 政策列表 -->" + JSONConverter.toJson(strategies));
			throw new StrategyFilterException(10503, "产品[" + prodId + "][" + strategyType.getName() + "], 政策为空.");
		}
		return _strategy;
	}
}
