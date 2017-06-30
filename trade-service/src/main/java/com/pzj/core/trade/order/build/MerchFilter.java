/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.build;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.commons.utils.DateUtils;
import com.pzj.core.product.common.model.response.CloseTimeOutModel;
import com.pzj.core.product.common.service.IProductCloseTimeslotService;
import com.pzj.core.sku.common.constants.SkuProductGlobal.PassengerFilledTypeValueEnum;
import com.pzj.core.strategy.common.global.StrategyGlobal.IsLimitAdvanceDueEnum;
import com.pzj.core.trade.create.engine.filter.CheckPointFilter;
import com.pzj.core.trade.create.engine.filter.ContactLimitFilter;
import com.pzj.core.trade.create.engine.filter.IdcardNoFilter;
import com.pzj.core.trade.order.engine.exception.OrderException;
import com.pzj.core.trade.order.engine.model.BookModel;
import com.pzj.core.trade.order.engine.model.CheckinPointModel;
import com.pzj.core.trade.order.engine.model.MerchBaseModel;
import com.pzj.core.trade.order.engine.model.StrategyBaseModel;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.model.TouristModel;
import com.pzj.trade.merch.common.VourTypeEnum;
import com.pzj.trade.order.model.MultiOrderInModel;
import com.pzj.trade.order.model.MultiOrderProductModel;

/**
 * 
 * @author Administrator
 * @version $Id: MerchBuilder.java, v 0.1 2017年3月7日 下午3:45:01 Administrator Exp $
 */
@Component
public class MerchFilter {
	private final static Logger logger = LoggerFactory.getLogger(MerchFilter.class);

	@Autowired
	private MerchAssembler merchBuilder;

	@Autowired
	private CheckinPointAssembler checkinPointAssembler;

	@Autowired
	private MerchRuleAssembler merchRuleBuilder;

	@Autowired
	private StrategyAssembler strategyBuilder;

	@Autowired
	private IProductCloseTimeslotService iProductCloseTimeslotService;

	@Autowired
	private IdcardNoFilter idcardNoFilter;

	@Autowired
	private CheckPointFilter checkPointFilter;

	@Autowired
	private ContactLimitFilter contactLimitFilter;

	public List<MerchBaseModel> assemble(final MultiOrderInModel orderInModel) {
		//获取产品和规则
		final List<MerchBaseModel> merchs = new ArrayList<MerchBaseModel>();

		final List<CheckinPointModel> checkinPointModels = null;
		for (final MultiOrderProductModel product : orderInModel.getProducts()) {
			MerchBaseModel merch = getMerch(product.getProductId(), merchs);
			if (merch == null) {
				//产品属性
				merch = copyProductBasic(orderInModel, product);
				merchBuilder.assemble(product.getProductId(), merch);

				checkinPointAssembler.assemble(checkinPointModels, merch, orderInModel.getCheckinPoints());
				//产品规则属性
				merchRuleBuilder.assemble(merch);
				merchs.add(merch);
			} else {
				merch.setBuyNum(merch.getBuyNum() + product.getProductNum());
				if (Check.NuNCollections(merch.getSeats())) {
					merch.setSeats(product.getSeats());
				} else if (!Check.NuNCollections(product.getSeats())) {
					merch.getSeats().addAll(product.getSeats());
				}
			}
		}

		//获取政策和返利
		strategyBuilder.assemble(merchs, orderInModel);
		logger.info("组装商品信息:" + JSONConverter.toJson(merchs));
		return merchs;
	}

	/**
	 * 
	 * @param orderInModel
	 * @param product
	 * @param merch
	 */
	private MerchBaseModel copyProductBasic(final MultiOrderInModel orderInModel, final MultiOrderProductModel product) {
		MerchBaseModel merch = new MerchBaseModel();
		merch.setPlayDate(new Date(orderInModel.getTravelDate()));
		if (product.getShow_date() > 0) {
			merch.setShow_start_time(new Date(product.getShow_date()));
			merch.setShow_end_time(new Date(product.getShow_date()));
		}
		merch.setSeats(product.getSeats());

		merch.setVourType(orderInModel.getVourType());
		merch.setSubUserId(product.getSubUserId());
		merch.setpId(product.getStrategyRelationId());
		merch.setParentUserId(product.getParentUserId());
		merch.setPrice(product.getPrice());
		merch.setBuyNum(product.getProductNum());
		merch.setScenicId(product.getScenicId());
		return merch;
	}

	//	/**
	//	 * 查询库存id
	//	 * @param merch
	//	 */
	//	private void getStockId(MerchBaseModel merch) {
	//		StockQueryRequestModel requestModel = new StockQueryRequestModel();
	//		requestModel.setRuleId(merch.getStock_rule_id());
	//		requestModel.setStockTime(DateUtils.getYearMonthDayString(merch.getPlayDate()));
	//		Result<StockModel> stockResult = stockQueryService.queryStockByRule(requestModel, null);
	//		if (!stockResult.isOk()) {
	//			throw new OrderException(15001, "查询库存失败:" + JSONConverter.toJson(requestModel));
	//		}
	//		if (!Check.NuNObject(stockResult.getData())) {
	//			merch.setStockId(stockResult.getData().getId());
	//		}
	//	}

	/**
	 * 
	 * @param merchs
	 * @return
	 */
	private MerchBaseModel getMerch(final long productId, final List<MerchBaseModel> merchs) {
		for (final MerchBaseModel merch : merchs) {
			if (productId == merch.getProdId()) {
				return merch;
			}
		}
		return null;
	}

	public boolean filter(final List<MerchBaseModel> merchs, final MultiOrderInModel orderInModel) {
		//联系人限购
		contactLimitFilter.filter(merchs, orderInModel);
		//身份证判重
		judgeUsedIdcard(merchs, orderInModel);

		// 消费凭证判断
		judgeVourType(merchs, orderInModel);
		//同一检票点判断
		judgeUsedCheckPoint(merchs, orderInModel);
		for (final MerchBaseModel merch : merchs) {
			//产品日期判断
			judgeMerchTime(merch);
			//政策判断
			final StrategyBaseModel strategy = merch.getStrategys().get(0);
			strategyBookTimeLimit(merch, merch.getPlayDate(), strategy);
		}
		strategyLimitCount(merchs);

		return true;
	}

	/**
	 * 
	 * @param merchs
	 */
	private void strategyLimitCount(final List<MerchBaseModel> merchs) {
		final StrategyBaseModel strategy = merchs.get(0).getStrategys().get(0);
		int buyNum = 0;
		for (MerchBaseModel merch : merchs) {
			buyNum += merch.getBuyNum();
		}
		//政策限购数量判断
		if ((!Check.NuNObject(strategy.getLeastPerdueNumber()) && buyNum < strategy.getLeastPerdueNumber())
				|| (!Check.NuNObject(strategy.getMostPerdueNumber()) && buyNum > strategy.getMostPerdueNumber())) {
			if (strategy.getLeastPerdueNumber() == -1 || strategy.getMostPerdueNumber() == -1) {
				return;
			}

			throw new OrderException(14001, "对不起，该产品要求最少购买" + strategy.getLeastPerdueNumber() + "份，最多购买" + strategy.getMostPerdueNumber() + "份");
		}
	}

	/**
	 * 
	 * @param merchs
	 * @param orderInModel
	 */
	private void judgeVourType(final List<MerchBaseModel> merchs, final MultiOrderInModel orderInModel) {
		if (orderInModel.getVourType() == VourTypeEnum.CARDID.getVourType()) {
			if (Check.NuNCollections(orderInModel.getTourists()) || Check.NuNObject(orderInModel.getTourists().get(0).getIdcardNo())) {
				throw new OrderException(14001, "参数错误，订单消费凭证为身份证，但是参数没有传递身份证");
			}
			for (MerchBaseModel merch : merchs) {
				if (merch.getPassengerType() != PassengerFilledTypeValueEnum.PASSENGER_FILLED_NEED_ALL.getValue()) {
					throw new OrderException(14001, merch.getProdId() + "参数错误，订单消费凭证为身份证，但是游客填单项配置不符:" + merch.getPassengerType());
				}
			}
		}
	}

	/**
	 * 
	 * @param merch
	 * @param playDate
	 * @param strategy
	 */
	private void strategyBookTimeLimit(final MerchBaseModel merch, final Date playDate, final StrategyBaseModel strategy) {
		if (!merch.getIsPlayTime()) {//如果不需要传游玩时间，就不判断提前预定时间
			return;
		}
		final BookModel bookModel = strategy.getBookModel();
		if (bookModel.getLimiType() == IsLimitAdvanceDueEnum.Absolute.getId()) {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			int playDay = Integer.valueOf(sdf.format(playDate));
			int createDay = Integer.valueOf(sdf.format(now)) + bookModel.getDueDay();

			if (playDay < createDay) {
				throw new OrderException(14001, "对不起，产品预订时间失效，请重新选择日期");
			}
			if (playDay == createDay) {
				if (now.getHours() > bookModel.getDueHour() || (now.getHours() == bookModel.getDueHour() && now.getMinutes() > bookModel.getDueMin())) {
					throw new OrderException(14001, "对不起，产品预订时间失效，请重新选择日期");
				}
			}

		} else if (bookModel.getLimiType() == IsLimitAdvanceDueEnum.Relative.getId()) {
			final Calendar bookTime = Calendar.getInstance();
			bookTime.setTime(new Date());
			bookTime.add(Calendar.MINUTE, bookModel.getDueMin());
			final Calendar showTime = Calendar.getInstance();
			showTime.setTime(merch.getShow_start_time());

			if (bookTime.after(showTime)) {
				throw new OrderException(14001, "对不起，产品预订时间失效，请重新选择日期");
			}
		}
	}

	/**
	 * 
	 * @param merch
	 * @return
	 */
	private void judgeMerchTime(final MerchBaseModel merch) {
		//销售日期
		if (compareDate(merch.getSaleStartDate(), new Date()) || compareDate(new Date(), merch.getSaleEndDate())) {
			throw new OrderException(14001, "对不起，产品已下架，请购买其他产品");
		}

		//是否已选择出游日期
		if (merch.getIsPlayTime() && merch.getPlayDate() == null) {
			throw new OrderException(14001, "对不起，请选择你的出游日期");
		}
		//产品可用日期
		final Date playDate = merch.getPlayDate();
		if (compareDate(merch.getUseStartDate(), playDate) || compareDate(playDate, merch.getUseEndDate())) {
			throw new OrderException(14001, "对不起，该产品已过期，请购买其他产品");
		}

		final Result<CloseTimeOutModel> result = iProductCloseTimeslotService.getBySkuId(merch.getProdId());
		if (result.isOk() && result.getData() != null && result.getData().getSkuMap().size() > 0) {
			final Map<Long, List<Date>> closeDates = result.getData().getSkuMap();
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			List<Date> skuCloseDates = closeDates.get(merch.getProdId());
			if (skuCloseDates != null) {
				for (final Date closeDate : skuCloseDates) {
					if (sdf.format(closeDate).equals(sdf.format(playDate))) {
						throw new OrderException(14001, "对不起，该日期商家未开放，请选择其他日期");
					}
				}
			}

		}
		return;
	}

	/**
	 * 比较两个日期的年月日	
	 * 如果前面的大，返回true
	 * @param d1
	 * @param d2
	 * @return
	 */
	private boolean compareDate(Date d1, Date d2) {
		if (Check.NuNObject(d1, d2)) {
			return true;
		}
		String day1 = DateUtils.formatDate("yyyyMMdd", d1);
		String day2 = DateUtils.formatDate("yyyyMMdd", d2);
		if (Integer.valueOf(day1) > Integer.valueOf(day2)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param merchs
	 * @param orderInModel
	 */
	private void judgeUsedIdcard(final List<MerchBaseModel> merchs, final MultiOrderInModel orderInModel) {
		final long supplierId = merchs.get(0).getSupplierId();
		if (merchs.get(0).getVourType() != VourTypeEnum.CARDID.getVourType()) {
			return;
		}
		if (!Check.NuNCollections(orderInModel.getTourists())) {

			for (final MerchBaseModel merch : merchs) {
				final ArrayList<String> idcardNos = new ArrayList<String>();
				for (final TouristModel tourist : orderInModel.getTourists()) {
					if (merch.getProdId() == tourist.getProductId()) {
						idcardNos.add(tourist.getIdcardNo());
					}
				}
				logger.info("身份证号验重,parameter:productId:" + merch.getProdId() + "supplierId:" + supplierId + "idcardNos:" + idcardNos + "playDate:"
						+ merch.getPlayDate().toLocaleString());
				final List<String> result = idcardNoFilter.checkUsedIdcardNo(merch.getProdId(), supplierId, idcardNos, merch.getPlayDate());
				if (!Check.NuNCollections(result)) {
					throw new OrderException(14001, "身份证重复:" + result.toString());
				}
			}

		}
	}

	private void judgeUsedCheckPoint(final List<MerchBaseModel> merchs, final MultiOrderInModel orderInModel) {
		final long supplierId = merchs.get(0).getSupplierId();
		if (merchs.get(0).getVourType() != VourTypeEnum.CARDID.getVourType()) {//验证是否身份证
			return;
		}
		//		if (merchs.get(0).getProdType() != ProductTypeGlobal.SCENIC && merchs.get(0).getProdType() != ProductTypeGlobal.NORMAL) {//验证是否  景区演艺产品
		//			return;
		//		}
		if (!Check.NuNCollections(orderInModel.getTourists())) {

			for (final MerchBaseModel merch : merchs) {
				final ArrayList<String> idcardNos = new ArrayList<String>();
				for (final TouristModel tourist : orderInModel.getTourists()) {
					if (merch.getProdId() == tourist.getProductId()) {
						idcardNos.add(tourist.getIdcardNo());
					}
				}
				logger.info("检票点验重,parameter:productId:" + merch.getProdId() + "supplierId:" + supplierId + "idcardNos:" + idcardNos + "playDate:"
						+ merch.getPlayDate().toLocaleString());
				//查询购买该产品的身份证是否存在还可以进行核销相同检票点。
				String idcardNo = checkPointFilter.queryUsedCheckPoint(merch.getCheckinPointIds(), idcardNos, merch.getPlayDate());
				if (idcardNo != null) {
					throw new OrderException(14001, "对不起，身份证号【" + idcardNo + "】已购【" + DateUtils.getYearMonthDayString(merch.getPlayDate())
							+ "】的同一检票点产品，未核销前暂不能重复购买");
				}
			}
		}
	}
}
