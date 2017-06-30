package com.pzj.core.trade.order.engine;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.book.dao.write.BookWMapper;
import com.pzj.core.trade.book.engine.model.OrderBook;
import com.pzj.core.trade.order.engine.model.MerchBaseModel;
import com.pzj.core.trade.voucher.entity.VoucherBriefEntity;
import com.pzj.core.trade.voucher.entity.VoucherConfirmBriefEntity;
import com.pzj.core.trade.voucher.entity.VoucherExtendBriefEntity;
import com.pzj.core.trade.voucher.write.VoucherBriefWriteMapper;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.constant.TradeConstant;
import com.pzj.trade.merch.common.VerificationVourTypeEnum;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.entity.MftourCodeEntity;
import com.pzj.trade.order.entity.OrderExtendAttrEntity;
import com.pzj.trade.order.entity.OrderResponse;
import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.entity.RemarkEntity;
import com.pzj.trade.order.entity.TouristEntity;
import com.pzj.trade.order.entity.TradingOrderEntity;
import com.pzj.trade.order.write.MftourCodeWriteMapper;
import com.pzj.trade.order.write.OrderExtendAttrWriteMapper;
import com.pzj.trade.order.write.OrderStrategyWriteMapper;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.order.write.TouristWriteMapper;

/**
 * 交易下单执行引擎.
 * @author YRJ
 *
 */
@Component
public class OrderGenerateEngine {
	private final static Logger logger = LoggerFactory.getLogger(OrderGenerateEngine.class);

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private MerchWriteMapper merchWriteMapper;

	@Autowired
	private VoucherBriefWriteMapper voucherBriefWriteMapper;

	@Autowired
	private OrderStrategyWriteMapper orderStrategyWriteMapper;

	@Autowired
	private MftourCodeWriteMapper mftourCodeWriteMapper;

	@Resource(name = "orderExtendAttrWriteMapper")
	private OrderExtendAttrWriteMapper orderExtendAttrWriteMapper;

	@Autowired
	private StockEngine stockEngine;

	@Autowired
	private BookWMapper bookWMapper;

	@Autowired
	private TouristWriteMapper touristWriteMapper;

	@Value("${order.cancel.supplier.cache}")
	private String supplierCacheValue;

	private static Map<Long, Integer> supplierCache = new HashMap<Long, Integer>();

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 3)
	public OrderResponse doHandler(List<TradingOrderEntity> orders, OrderBook orderBook, List<MerchBaseModel> merchs) {
		logger.info("订单入库参数 :" + JSONConverter.toJson(orders) + JSONConverter.toJson(orderBook) + JSONConverter.toJson(merchs));
		TradingOrderEntity order = orders.get(0);
		//voucher
		insertVoucher(order);
		{//订单
			generateOrderCanceTime(orders);
			orderWriteMapper.insertMultiOrder(orders);

			List<RemarkEntity> remarks = new ArrayList<RemarkEntity>();
			List<MftourCodeEntity> mfcodes = new ArrayList<MftourCodeEntity>();
			for (TradingOrderEntity orderEntity : orders) {
				if (!Check.NuNObject(orderEntity.getRemark())) {
					remarks.add(orderEntity.getRemark());
				}
				if (!Check.NuNObject(orderEntity.getMfCodes())) {
					mfcodes = orderEntity.getMfCodes();
				}
			}
			if (!Check.NuNCollections(remarks)) {
				orderWriteMapper.insertOrderRemarks(remarks);
			}
			if (!Check.NuNCollections(mfcodes)) {
				mftourCodeWriteMapper.insertMftourCode(mfcodes);
			}
			List<OrderExtendAttrEntity> filleds = order.getFilleds();
			if (filleds != null && filleds.size() > 0) {
				orderExtendAttrWriteMapper.insert(filleds);
			}
		}
		{//商品
			List<MerchEntity> merchEntitys = orders.get(0).getProducts();
			setVoucherIdForMerchs(order, merchEntitys, merchs);

			merchWriteMapper.insertMultiMerchEntity(merchEntitys);

			List<TouristEntity> tourists = new ArrayList<TouristEntity>();
			List<OrderStrategyEntity> strategys = new ArrayList<OrderStrategyEntity>();
			for (MerchEntity merch : merchEntitys) {
				tourists.addAll(merch.getTourists());
				strategys.addAll(merch.getStrategy());
			}
			if (!Check.NuNCollections(strategys)) {
				orderStrategyWriteMapper.insertMultiStrategy(strategys);

			}
			if (!Check.NuNCollections(tourists)) {
				touristWriteMapper.insertTourists(tourists);
			}
		}

		stockEngine.doHandler(orderBook, order, merchs);

		//回写预约单
		if (!Check.NuNObject(orderBook)) {
			bookWMapper.callBackByTransaction_id(order.getTransaction_id());
		}

		OrderResponse resp = generateOrderResponse(order);
		return resp;
	}

	/**
	 * 
	 * @param order
	 * @param merchEntitys
	 */
	private void setVoucherIdForMerchs(TradingOrderEntity order, List<MerchEntity> merchEntitys, List<MerchBaseModel> merchs) {
		List<VoucherBriefEntity> vouchers = order.getVouchers();
		int verificationVourType = merchs.get(0).getVerificationVourType();
		if (vouchers.size() == 1) {//voucher只有1条，代表是魔方码或者联系人的，或者是只有一条记录的一证一票
			for (MerchEntity merch : merchEntitys) {
				merch.setVoucher_id(vouchers.get(0).getVoucherId());
			}
		} else {
			if (verificationVourType == VerificationVourTypeEnum.CARDID_BY_NUM.getVourType()) {//身份证按份数
				for (MerchEntity merch : merchEntitys) {
					for (VoucherBriefEntity voucher : vouchers) {
						if (voucher.getVoucherContent() == merch.getTourists().get(0).getIdcard()) {
							merch.setVoucher_id(voucher.getVoucherId());
						}
					}
				}
			} else if (verificationVourType == VerificationVourTypeEnum.MFCODE_BY_NUM.getVourType()) {//二维码按份数
				for (int i = 0; i < merchEntitys.size(); i++) {
					merchEntitys.get(i).setVoucher_id(vouchers.get(i).getVoucherId());
				}
			} else if (verificationVourType == VerificationVourTypeEnum.MFCODE_BY_SKU.getVourType()) {//二维码按规格
				for (MerchEntity merch : merchEntitys) {
					for (VoucherBriefEntity voucher : vouchers) {
						if (voucher.getProductId() == merch.getProduct_id()) {
							merch.setVoucher_id(voucher.getVoucherId());
						}
					}
				}
			}

		}
	}

	/**
	 * 
	 * @param order
	 */
	private void insertVoucher(TradingOrderEntity order) {
		for (VoucherBriefEntity voucher : order.getVouchers()) {
			//之所以没使用批量插入，是需要返回voucherId，而批量的不支持返回主键，对这个mybatis版本
			voucherBriefWriteMapper.insertVoucherBase(voucher);
		}
		List<VoucherConfirmBriefEntity> confirms = new ArrayList<VoucherConfirmBriefEntity>();
		List<VoucherExtendBriefEntity> voucherExtends = new ArrayList<VoucherExtendBriefEntity>();
		for (VoucherBriefEntity voucher : order.getVouchers()) {
			for (VoucherConfirmBriefEntity confirm : voucher.getVoucherConfirms()) {
				confirm.setVoucherId(voucher.getVoucherId());
				confirm.setSupplierId(voucher.getSupplierId());
			}
			confirms.addAll(voucher.getVoucherConfirms());

			VoucherExtendBriefEntity extend = voucher.getVoucherExtend();
			if (!Check.NuNObject(extend)) {
				extend.setVoucher_id(voucher.getVoucherId());
				extend.setSupplier_id(voucher.getSupplierId());
				voucherExtends.add(extend);
			}
		}

		if (!Check.NuNCollections(confirms)) {
			voucherBriefWriteMapper.insertVoucherConfirms(confirms);
		}
		if (!Check.NuNCollections(voucherExtends)) {
			voucherBriefWriteMapper.insertVoucherExtends(voucherExtends);
		}
	}

	private OrderResponse generateOrderResponse(TradingOrderEntity order) {
		OrderResponse resp = new OrderResponse();
		resp.setOrderId(order.getOrder_id());
		resp.setTotalNum(order.getTotal_num());
		resp.setPay_state(order.getPay_state());
		resp.setPay_way(order.getPay_way());
		resp.setOrderStatus(order.getOrder_status());
		resp.setOrderSource(order.getOrder_source());
		resp.setOnline_pay(order.getOnline_pay());
		resp.setIs_direct(order.getIs_direct());
		resp.setNeedConfirm(order.getConfirm());
		resp.setContact_mobile(order.getContact_mobile());
		resp.setContactee(order.getContactee());
		resp.setReseller_id(order.getReseller_id());
		resp.setSupplier_id(order.getSupplier_id());
		DecimalFormat df = new DecimalFormat("#.00");
		String total_amount = df.format(order.getTotal_amount());
		resp.setTotalAmount(Double.valueOf(total_amount));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, order.getCancelMinute());
		resp.setCancelTime(calendar.getTime());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(format.format(calendar.getTime()));
		return resp;
	}

	protected static void generateOrderCanceTime(final List<TradingOrderEntity> purchs) {
		Integer tmpTime = null;
		for (final TradingOrderEntity purch : purchs) {
			if (supplierCache.containsKey(purch.getSupplier_id())) {
				if (tmpTime == null || tmpTime > supplierCache.get(purch.getSupplier_id())) {
					tmpTime = supplierCache.get(purch.getSupplier_id());
				}
			}
		}
		if (tmpTime == null) {
			tmpTime = TradeConstant.ORDER_CANCEL_MINUTES;
		}
		for (final TradingOrderEntity purch : purchs) {
			purch.setCancelMinute(tmpTime);
		}
	}

	@PostConstruct
	private void initSupplierCache() {
		if (Check.NuNString(this.supplierCacheValue)) {
			return;
		}
		supplierCache.clear();
		String[] supplierEntrys = this.supplierCacheValue.split(",");
		for (String entry : supplierEntrys) {
			int tmp = entry.indexOf(":");
			Long key = Long.parseLong(entry.substring(0, tmp));
			Integer value = Integer.parseInt(entry.substring(tmp + 1));
			supplierCache.put(key, value);
		}
	}
}
