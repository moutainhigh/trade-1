/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.engine;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.common.utils.StockGlobalDict.ooccupyType;
import com.pzj.core.product.model.OccupyStockReqModel;
import com.pzj.core.product.model.OccupyStockReqsModel;
import com.pzj.core.product.model.OccupyStockResponse;
import com.pzj.core.product.service.SeatRecordService;
import com.pzj.core.trade.book.engine.model.BookJsonEModel;
import com.pzj.core.trade.book.engine.model.OrderBook;
import com.pzj.core.trade.book.engine.model.ProductJsonEModel;
import com.pzj.core.trade.order.engine.exception.OrderStockException;
import com.pzj.core.trade.order.engine.model.MerchBaseModel;
import com.pzj.core.trade.order.engine.model.SeatsModel;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.entity.TradingOrderEntity;

/**
 * 
 * @author Administrator
 * @version $Id: StockEngine.java, v 0.1 2017年3月20日 上午10:02:22 Administrator Exp $
 */
@Component
public class StockEngine {
	private final static Logger logger = LoggerFactory.getLogger(StockEngine.class);
	@Autowired
	private SeatRecordService seatRecordService;

	@Autowired
	private SeatsAssemble seatsAssemble;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 3)
	public void doHandler(OrderBook orderBook, TradingOrderEntity order, List<MerchBaseModel> merchs) {
		logger.info("操作库存参数 orderBook:" + JSONConverter.toJson(orderBook) + ",merchs:" + JSONConverter.toJson(merchs));
		operatorStock(orderBook, order.getTransaction_id(), order.getOperator_id(), merchs);
	}

	public static void main(String[] args) {
		List<Long> bookSeats = new ArrayList<Long>();

		bookSeats.addAll(null);
	}

	/**
	 * 
	 * @param orderBook
	 * @param transaction_id
	 * @param merchs
	 */
	private void operatorStock(OrderBook orderBook, String transaction_id, long operatorId,
			List<MerchBaseModel> merchs) {
		OccupyStockReqsModel occupyStockReqsModel = new OccupyStockReqsModel();
		occupyStockReqsModel.setOccupyStockReqs(new ArrayList<OccupyStockReqModel>());

		List<ProductJsonEModel> bookProducts = getBookProducts(orderBook);
		List<ProductJsonEModel> preOrderProducts = getPreOrderProducts(orderBook);

		for (MerchBaseModel merch : merchs) {
			if (merch.getStock_rule_id() == 0) {
				continue;
			}
			List<Long> newSeats = merch.getSeats();
			List<Long> bookSeats = new ArrayList<Long>();
			List<Long> preOrderSeats = new ArrayList<Long>();
			for (ProductJsonEModel item : bookProducts) {
				if (item.getProductId() == merch.getProdId()) {
					if (!Check.NuNCollections(item.getSeats())) {
						bookSeats.addAll(item.getSeats());
					}
				}
			}
			for (ProductJsonEModel item : preOrderProducts) {
				if (item.getProductId() == merch.getProdId()) {
					if (!Check.NuNCollections(item.getSeats())) {
						preOrderSeats.addAll(item.getSeats());
					}
				}
			}

			SeatsModel seats = seatsAssemble.computeSeatsOnCreateOrder(bookSeats, preOrderSeats, newSeats);
			OccupyStockReqModel occupyStockReqModel = generateOccupyStockReq(transaction_id, operatorId, merch, seats,
					ooccupyType.occupyingSeat);
			occupyStockReqModel.setOutQuantity(merch.getBuyNum());
			occupyStockReqsModel.getOccupyStockReqs().add(occupyStockReqModel);
		}
		if (Check.NuNCollections(occupyStockReqsModel.getOccupyStockReqs())) {
			return;
		}
		logger.info("调用库存接口,parameter:" + JSONConverter.toJson(occupyStockReqsModel));
		Result<OccupyStockResponse> result = seatRecordService.occupyStock(occupyStockReqsModel);
		if (!result.isOk()) {
			throw new OrderStockException(result.getData(), result.getErrorCode(), result.getErrorMsg());
		}
	}

	/**
	 * 
	 * @param transaction_id
	 * @param merch
	 * @param occupyStockReqModel
	 * @param seats
	 * @param type 
	 */
	public OccupyStockReqModel generateOccupyStockReq(String transaction_id, long operatorId, MerchBaseModel merch,
			SeatsModel seats, int type) {
		OccupyStockReqModel occupyStockReqModel = new OccupyStockReqModel();
		occupyStockReqModel.setOperator(operatorId);
		occupyStockReqModel.setOccupyType(type);
		if (!Check.NuNObject(merch.getRegion())) {
			occupyStockReqModel.setAreaId(Long.valueOf(merch.getRegion()));
		}
		if (!Check.NuNObject(merch.getRonda())) {
			occupyStockReqModel.setScreeningsId(Long.valueOf(merch.getRonda()));
		}

		occupyStockReqModel.setTransactionId(transaction_id);
		occupyStockReqModel.setOutQuantity(merch.getBuyNum());
		occupyStockReqModel.setProductId(merch.getProdId());
		occupyStockReqModel.setStockRuleId(merch.getStock_rule_id());
		occupyStockReqModel
				.setTravelDate(merch.getShow_start_time() != null ? merch.getShow_start_time() : merch.getPlayDate());

		occupyStockReqModel.setOccupySeatIds(seats.getNeedSeats());
		occupyStockReqModel.setReleaseSeatIds(seats.getReleaseSeats());
		occupyStockReqModel.setSpuId(merch.getSpuId());
		return occupyStockReqModel;
	}

	/**
	 * 
	 * @param orderBook
	 * @return
	 */
	private List<ProductJsonEModel> getPreOrderProducts(OrderBook orderBook) {
		List<ProductJsonEModel> preOrderProducts = new ArrayList<ProductJsonEModel>();
		if (!Check.NuNObject(orderBook)) {
			BookJsonEModel book = JSONConverter.toBean(orderBook.getPreOrder().getBookDetail(), BookJsonEModel.class);
			preOrderProducts = book.getProducts();
		}
		return preOrderProducts;
	}

	/**
	 * 
	 * @param orderBook
	 * @return
	 */
	private List<ProductJsonEModel> getBookProducts(OrderBook orderBook) {
		List<ProductJsonEModel> bookProducts = new ArrayList<ProductJsonEModel>();
		if (!Check.NuNObject(orderBook)) {
			BookJsonEModel book = JSONConverter.toBean(orderBook.getBook().getBookDetail(), BookJsonEModel.class);
			bookProducts = book.getProducts();
		}
		return bookProducts;
	}

}
