package com.pzj.core.trade.order.filter.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.strategy.common.global.StrategyGlobal.SaleTypeEnum;
import com.pzj.core.strategy.model.request.Strategy4TradeInModel;
import com.pzj.core.strategy.model.response.Strategy4TradeOutModel;
import com.pzj.core.strategy.service.Strategy4TradeService;
import com.pzj.core.trade.order.engine.exception.StrategyFilterException;
import com.pzj.core.trade.order.engine.model.MerchModel;
import com.pzj.core.trade.order.engine.model.StrategyModel;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.trade.order.vo.TradeOrderVO;

/**
 * 交易下单流程中获取购买产品政策.
 * @author YRJ
 *
 */
@Component(value = "strategyFilter")
public class StrategyFilter implements ObjectConverter<TradeOrderVO, List<MerchModel>, Void> {

	private final static Logger logger = LoggerFactory.getLogger(StrategyFilter.class);

	@Autowired
	private Strategy4TradeService strategy4TradeSerivce;

	@Resource(name = "strategyBuilder")
	private ObjectConverter<Long, ArrayList<Strategy4TradeOutModel>, StrategyModel[]> strategyBuilder;

	@Resource(name = "rebateCalculator")
	private ObjectConverter<Long, Long, Double> rebateCalculator;

	@Override
	public Void convert(TradeOrderVO reqModel, List<MerchModel> merchModels) {
		ArrayList<Strategy4TradeOutModel> outModels = getStrategyByResellerAndSaleport(reqModel.getResellerId(),
				reqModel.getSalePort(), merchModels);

		for (MerchModel merchModel : merchModels) {
			StrategyModel[] strategies = strategyBuilder.convert(merchModel.getProdId(), outModels);
			merchModel.setResellerStrategy(strategies[0]);
			merchModel.setSupplierStrategy(strategies[1]);
			strategies[0].setSale_port(reqModel.getSalePort());
			strategies[1].setSale_port(reqModel.getSalePort());

			merchModel.setPrice(merchModel.getPrice(strategies[0]).doubleValue());

			double rebate1 = rebateCalculator.convert(merchModel.getProdId(), strategies[0].getStrategyId());
			double rebate2 = rebateCalculator.convert(merchModel.getProdId(), strategies[1].getStrategyId());
			strategies[0].setRebate_amount(rebate1);
			strategies[1].setRebate_amount(rebate2);

			merchModel.setChannelId(strategies[0].getChannelId());
		}
		return null;
	}

	private ArrayList<Strategy4TradeOutModel> getStrategyByResellerAndSaleport(long resellerId, int salePort,
			List<MerchModel> merchModels) {
		Strategy4TradeInModel inModel = buildStrategyReqModel(resellerId, salePort, merchModels);
		Result<ArrayList<Strategy4TradeOutModel>> result;
		try {
			result = strategy4TradeSerivce.findStrategy(inModel);
		} catch (Throwable e) {
			throw new StrategyFilterException(10500, "根据分销商[" + resellerId + "], 销售端口[" + salePort + "], 产品ID["
					+ Arrays.toString(inModel.getSkudIds().toArray(new Long[0])) + "]获取政策信息错误.", e);
		}

		if (!result.isOk()) {
			throw new StrategyFilterException(10500, "根据分销商[" + resellerId + "], 销售端口[" + salePort + "], 产品ID["
					+ Arrays.toString(inModel.getSkudIds().toArray(new Long[0])) + "]获取政策信息错误. 返回错误码[" + result.getErrorCode()
					+ "], 错误描述[" + result.getErrorMsg() + "].");
		}

		if (logger.isInfoEnabled()) {
			logger.info("根据分销商[" + resellerId + "], 销售端口[" + salePort + "], 产品ID["
					+ Arrays.toString(inModel.getSkudIds().toArray(new Long[0])) + "]获取政策信息 --> "
					+ (JSONConverter.toJson(result)));
		}

		ArrayList<Strategy4TradeOutModel> outModels = result.getData();
		return outModels;
	}

	/**
	 * 构建政策请求参数.
	 * @param resellerId
	 * @param salePort
	 * @param merchModels
	 * @return
	 */
	private Strategy4TradeInModel buildStrategyReqModel(long resellerId, int salePort, List<MerchModel> merchModels) {
		Strategy4TradeInModel requestModel = new Strategy4TradeInModel();
		requestModel.setResellerId(resellerId);
		requestModel.setSaleType(SaleTypeEnum.get(String.valueOf(salePort)));

		List<Long> prodIds = collect(merchModels);
		requestModel.setSkudIds(prodIds);
		return requestModel;
	}

	/**
	 * 过滤产品ID, 剔除掉重复的ID.
	 * <p>当为一证一票时, 特别有用.</p>
	 * @param merchModels
	 * @return
	 */
	private List<Long> collect(List<MerchModel> merchModels) {
		Set<Long> prodIds = new HashSet<Long>();
		for (MerchModel merchModel : merchModels) {
			prodIds.add(merchModel.getProdId());
		}
		return new ArrayList<Long>(prodIds);
	}
}
