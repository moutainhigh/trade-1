/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.build;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.product.common.model.response.StrategyRuleSkuOutModel;
import com.pzj.core.product.common.service.IStrategyRuleSkuService;
import com.pzj.core.product.reseller.model.response.StrategySkuOutModel;
import com.pzj.core.strategy.common.global.StrategyGlobal.IsLimitAdvanceDueEnum;
import com.pzj.core.strategy.common.global.StrategyGlobal.RebateMethodEnum;
import com.pzj.core.strategy.common.global.StrategyGlobal.StrategyRuleAdviceItemEnum;
import com.pzj.core.strategy.common.global.StrategyGlobal.StrategyRuleIsNeedPayment;
import com.pzj.core.strategy.common.global.StrategyGlobal.StrategyRulePerdueNumberItemEnum;
import com.pzj.core.strategy.model.request.Strategy4TradeQueryInModel;
import com.pzj.core.strategy.model.request.Strategy4TradeSkuQueryInModel;
import com.pzj.core.strategy.model.response.Strategy4TradeResultOutModel;
import com.pzj.core.strategy.model.response.StrategyRebateOutModel;
import com.pzj.core.strategy.service.Strategy4TradeService;
import com.pzj.core.strategy.service.StrategyRebateService;
import com.pzj.core.trade.order.engine.RuleReader;
import com.pzj.core.trade.order.engine.exception.OrderException;
import com.pzj.core.trade.order.engine.model.BookModel;
import com.pzj.core.trade.order.engine.model.MerchBaseModel;
import com.pzj.core.trade.order.engine.model.StrategyBaseModel;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.common.BookTypeEnum;
import com.pzj.trade.order.model.MultiOrderInModel;

/**
 * 
 * @author Administrator
 * @version $Id: StrategyBuilder.java, v 0.1 2017年3月7日 下午3:45:30 Administrator Exp $
 */
@Component
public class StrategyAssembler {

	private final static Logger logger = LoggerFactory.getLogger(StrategyAssembler.class);

	@Autowired
	private Strategy4TradeService strategy4TradeSerivce;

	@Autowired
	private StrategyRebateService strategyRebateSerivce;

	@Autowired
	private IStrategyRuleSkuService strategyRuleSkuService;

	/**
	 * 
	 * @param merchs
	 * @param orderInModel
	 */
	public void assemble(List<MerchBaseModel> merchs, MultiOrderInModel orderInModel) {
		if (orderInModel.getBookType() == BookTypeEnum.FREE_TICKET.getType()
				|| orderInModel.getBookType() == BookTypeEnum.CHEAP_TICKET.getType()) {//免票特价票设置虚拟政策
			for (MerchBaseModel merch : merchs) {
				StrategyBaseModel strategy = new StrategyBaseModel();
				strategy.setStrategyId(0L);
				strategy.setSettlementPrice((int) (merch.getPrice()));
				strategy.setSupplierId(merch.getSupplierId());
				strategy.setResellerId(merch.getSupplierId());
				Result<StrategyRuleSkuOutModel> result = strategyRuleSkuService.getBySpuId(merch.getSpuId());
				if (result.isOk()) {
					doStrategyRule(result.getData(), strategy);
				}
				merch.getStrategys().add(strategy);
			}
		} else {//其他情况查询政策
			Result<ArrayList<Strategy4TradeResultOutModel>> result = getStrategys(merchs, orderInModel);
			for (MerchBaseModel merch : merchs) {
				Strategy4TradeResultOutModel skuStrategy = getStrategyByProductId(result, merch.getProdId());
				recursiveStrategy(merch, skuStrategy.getStrategyNode(), skuStrategy.getStrategyRuleSkuOutModel());
				merch.setPrice(merch.getStrategys().get(0).getPrice(orderInModel.getSalePort(), true));
			}
		}

	}

	/**
	 * 
	 * @param merchs
	 * @param orderInModel
	 * @return
	 */
	private Result<ArrayList<Strategy4TradeResultOutModel>> getStrategys(List<MerchBaseModel> merchs,
			MultiOrderInModel orderInModel) {
		Strategy4TradeQueryInModel inModel = new Strategy4TradeQueryInModel();

		inModel.setResellerId(orderInModel.getResellerId());
		inModel.setSaleType(String.valueOf(orderInModel.getSalePort()));
		for (MerchBaseModel merch : merchs) {
			Strategy4TradeSkuQueryInModel sku = new Strategy4TradeSkuQueryInModel();
			sku.setSkuId(merch.getProdId());
			sku.setStrategyRelationId(merch.getpId());
			sku.setParentUserId(merch.getParentUserId());
			sku.setSubUserId(merch.getSubUserId());
			inModel.getSkuList().add(sku);
		}

		Result<ArrayList<Strategy4TradeResultOutModel>> result = strategy4TradeSerivce.query(inModel);

		//Result<ArrayList<Strategy4TradeResultOutModel>> result = mockStrategy();
		logger.info("查询政策接口,参数:" + JSONConverter.toJson(inModel) + "政策结果:" + JSONConverter.toJson(result));
		if (Check.NuNObject(result) || !result.isOk()) {
			logger.error("查询政策报错,parameter:" + JSONConverter.toJson(inModel));
			throw new OrderException(15001, "对不起,该产品已失效,暂不提供购买");
		}
		if (Check.NuNCollections(result.getData())) {
			logger.error("查询政策报错,parameter:" + JSONConverter.toJson(inModel));
			throw new OrderException(15001, "对不起,该产品已失效,暂不提供购买");
		}
		return result;
	}

	/**
	 * 递归每层的政策，获取政策信息，政策规则，返利信息
	 * @param merch
	 * @param skuStrategy
	 */
	private void recursiveStrategy(MerchBaseModel merch, StrategySkuOutModel skuStrategy, StrategyRuleSkuOutModel strategyRule) {
		StrategyBaseModel strategy = new StrategyBaseModel();
		strategy.setStrategyId(skuStrategy.getStrategyId());
		strategy.setIsWeshopStrategy(skuStrategy.getIsWeshopStrategy());
		strategy.setSettlementPrice(skuStrategy.getSettlementPrice());
		strategy.setAdvicePrice(skuStrategy.getAdvicePrice());
		strategy.setChannelId(skuStrategy.getChannelId());
		strategy.setPay_type(skuStrategy.getPayType());
		strategy.setRebateType(skuStrategy.getRebateMethod().getId());
		strategy.setSupplierId(skuStrategy.getParentUserId());
		strategy.setResellerId(skuStrategy.getSubUserId());
		strategy.setSkuSupplierId(skuStrategy.getSkuSupplierId());

		doStrategyRule(strategyRule, strategy);

		if (skuStrategy.getRebateMethod().getId() == RebateMethodEnum.End.getId()) {//后返的时候才需要调用返利接口
			Result<ArrayList<StrategyRebateOutModel>> rebateResult = strategyRebateSerivce.findRebate(skuStrategy
					.getStrategyId());
			if (rebateResult.isOk() && !Check.NuNCollections(rebateResult.getData())) {
				for (StrategyRebateOutModel rebate : rebateResult.getData()) {
					strategy.setRebate_amount(strategy.getRebate_amount() + rebate.getRebateAmount());
					strategy.setRebateCycle(rebate.getRebateSettlement().getId());
					if (!Check.NuNObject(rebate.getIntervalDays()) && rebate.getIntervalDays() != 0) {
						strategy.setIntervalDays(rebate.getIntervalDays());
					}
				}

			}
		}
		merch.getStrategys().add(strategy);

		if (!Check.NuNObject(skuStrategy.getNextNode())) {
			recursiveStrategy(merch, skuStrategy.getNextNode(), null);
		}
	}

	/**
	 * 处理政策规则
	 * @param strategyRule
	 * @param strategy
	 */
	private void doStrategyRule(StrategyRuleSkuOutModel strategyRule, StrategyBaseModel strategy) {
		if (Check.NuNObject(strategyRule)) {
			return;
		}
		//政策规则
		List<StrategyRuleSkuOutModel> ruleModels = strategyRule.getNodes();
		{
			StrategyRuleSkuOutModel payable = RuleReader.readMultiStrategyRuleInstByName(
					StrategyRuleIsNeedPayment.STRATEGY_RULE_IS_NEEL_PAYMENT.getName(), ruleModels);
			if (payable != null) {
				strategy.setNeedPay(payable.getValue().intValue());
			}
		}
		{//最大最小购买限制.
			StrategyRuleSkuOutModel leastNumberRule = RuleReader.readMultiStrategyRuleInstByName(
					StrategyRulePerdueNumberItemEnum.LEAST_PERDUE_NUMBER.getName(), ruleModels);
			if (!Check.NuNObject(leastNumberRule)) {
				strategy.setLeastPerdueNumber(leastNumberRule.getValue());
			}
			StrategyRuleSkuOutModel mostNumberRule = RuleReader.readMultiStrategyRuleInstByName(
					StrategyRulePerdueNumberItemEnum.MOST_PERDUE_NUMBER.getName(), ruleModels);
			if (!Check.NuNObject(mostNumberRule)) {
				strategy.setMostPerdueNumber(mostNumberRule.getValue());
			}
		}
		{//提前预定时间限制:分绝对、相对两种
			BookModel bookModel = new BookModel();
			StrategyRuleSkuOutModel bookLimit = RuleReader.readMultiStrategyRuleInstByName(
					StrategyRuleAdviceItemEnum.IS_LIMIT_ADVANCE_DUE.getName(), ruleModels);
			if (!Check.NuNObject(bookLimit)) {
				bookModel.setLimiType(bookLimit.getValue());
				if (bookModel.getLimiType() == IsLimitAdvanceDueEnum.Absolute.getId()) {
					StrategyRuleSkuOutModel dueDay = RuleReader.readMultiStrategyRuleInstByName(
							StrategyRuleAdviceItemEnum.ADVANCE_DUE_DAYS.getName(), bookLimit.getNodes());
					if (dueDay != null) {
						bookModel.setDueDay(dueDay.getValue().intValue());
					}
					StrategyRuleSkuOutModel dueHour = RuleReader.readMultiStrategyRuleInstByName(
							StrategyRuleAdviceItemEnum.ADVANCE_DUE_HOUR.getName(), bookLimit.getNodes());
					if (dueHour != null) {
						bookModel.setDueHour(dueHour.getValue().intValue());
					}
				}
				StrategyRuleSkuOutModel dueMin = RuleReader.readMultiStrategyRuleInstByName(
						StrategyRuleAdviceItemEnum.ADVANCE_DUE_MINUTE.getName(), bookLimit.getNodes());
				if (dueMin != null) {
					bookModel.setDueMin(dueMin.getValue().intValue());
				}
			}
			strategy.setBookModel(bookModel);
		}
	}

	private Strategy4TradeResultOutModel getStrategyByProductId(Result<ArrayList<Strategy4TradeResultOutModel>> result,
			long prodId) {
		if (!Check.NuNObject(result.getData())) {
			List<Strategy4TradeResultOutModel> skuStrategys = result.getData();
			for (Strategy4TradeResultOutModel skuStrategy : skuStrategys) {
				if (skuStrategy.getSkuId() == prodId) {
					return skuStrategy;
				}
			}
		}
		throw new OrderException(15001, "对不起，该产品已失效，暂不提供购买");
	}
}
