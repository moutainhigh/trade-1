package com.pzj.core.trade.order.engine.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.product.common.model.response.SkuProductOutModel;
import com.pzj.core.product.common.model.response.SpuSkuProductOutModel;
import com.pzj.core.product.common.service.ISpuProductService;
import com.pzj.core.product.model.QuerySeatRecordResponse;
import com.pzj.core.product.model.QueryValidSeatRecordResponse;
import com.pzj.core.product.model.area.AreaModel;
import com.pzj.core.product.model.screeings.ScreeingAreaReqModel;
import com.pzj.core.product.model.screeings.ScreeingAreaRespModel;
import com.pzj.core.product.model.screeings.ScreeingsModel;
import com.pzj.core.product.service.ScreeingsQueryService;
import com.pzj.core.product.service.SeatRecordQueryService;
import com.pzj.core.trade.voucher.entity.VoucherBasicEntity;
import com.pzj.core.trade.voucher.read.VoucherReadMapper;
import com.pzj.framework.context.Result;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.order.entity.MerchResponse;
import com.pzj.trade.order.entity.TouristEntity;
import com.pzj.trade.order.model.ResellerMerchDetailRespModel;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.TouristReadMapper;
import com.pzj.voucher.common.ExecuteResult;
import com.pzj.voucher.engine.VoucherBusiness;
import com.pzj.voucher.entity.VoucherEntity;
import com.pzj.voucher.entity.VoucherResponseModel;

/**
 * 凭证查询事件.
 * @author GLG
 *
 */
@Component(value = "voucherQueryEvent")
public class VoucherQueryEvent {
	@Autowired
	private VoucherBusiness voucherBusiness;

	@Resource(name = "voucherReadMapper")
	private VoucherReadMapper voucherReadMapper;

	@Resource(name = "merchReadMapper")
	private MerchReadMapper merchReadMapper;

	@Resource(name = "touristReadMapper")
	private TouristReadMapper touristReadMapper;

	@Autowired
	private SeatRecordQueryService seatRecordQueryService;
	@Autowired
	private ISpuProductService spuProductService;
	@Autowired
	private ScreeingsQueryService screeingsQueryService;

	private final static Logger logger = LoggerFactory.getLogger(VoucherQueryEvent.class);

	public List<Long> queryVoucherByTypeAndStatus(final String vourContent) {
		final List<Long> voucherIds = voucherReadMapper.queryVoucherByContent(vourContent);
		return voucherIds;
	}

	public ExecuteResult<List<VoucherEntity>> getVoucherInfo(final String transaction_id) {
		final VoucherEntity voucherEntity = new VoucherEntity();
		voucherEntity.setTransactionId(transaction_id);
		final List<VoucherEntity> vouchers = voucherBusiness.queryVoucherByParam(voucherEntity);
		return new ExecuteResult<List<VoucherEntity>>(ExecuteResult.SUCCESS, "OK", vouchers);
	}

	/**
	 * 查询凭证基本信息.
	 * @param voucherId
	 * @return
	 */
	public VoucherResponseModel queryBasicVoucherById(final long voucherId) {
		final VoucherBasicEntity entity = voucherReadMapper.queryVoucherBasicById(voucherId);
		if (Check.NuNObject(entity)) {
			return null;
		}

		final VoucherResponseModel resp = new VoucherResponseModel();
		resp.setVoucherId(entity.getVoucher_id());
		resp.setVoucherContent(entity.getVoucher_content());
		resp.setVoucherContentType(entity.getVoucher_content_type());
		resp.setVoucherCategory(entity.getVoucher_category());
		resp.setVoucherState(entity.getVoucher_state());

		final List<MerchEntity> merchs = merchReadMapper.getMerchByVoucherId(voucherId);
		if (merchs == null || merchs.isEmpty()) {
			return resp;
		}

		final MerchEntity merch = merchs.get(0);
		final List<TouristEntity> tourists = touristReadMapper.getByOrderMerchId(merch.getTransaction_id(), merch.getMerch_id());
		if (tourists == null || tourists.isEmpty()) {
			return resp;
		}
		final TouristEntity tourist = tourists.get(0);
		resp.setContactMobile(tourist.getMobile());
		resp.setContactName(tourist.getName());
		return resp;
	}

	/**
	 * 组装voucher信息的属性，放在商品下
	 *
	 * @param merchs
	 * @param executeResult
	 */
	public List<ResellerMerchDetailRespModel> assembleVoucherInfoByReseller(final List<ResellerMerchDetailRespModel> merchs,
			final ExecuteResult<List<VoucherEntity>> executeResult, final String transaction_id) {
		if (Check.NuNObject(executeResult) || Check.NuNObject(executeResult.getData())) {
			return merchs;
		}
		for (final ResellerMerchDetailRespModel merch : merchs) {
			for (final VoucherEntity voucherEntity : executeResult.getData()) {
				if (merch.getVoucherId() == voucherEntity.getVoucherId()) {
					//					merch.setContact_name(voucherEntity.getContactName());
					//					merch.setContact_mobile(voucherEntity.getContactMobile());
					if (voucherEntity.getVoucherContentType() == 3) {
						merch.setVoucher_content(voucherEntity.getVoucherContent());
					} else if (voucherEntity.getVoucherContentType() == 2) {
						merch.setQr_code(voucherEntity.getVoucherContent());
					} else if (voucherEntity.getVoucherContentType() == 1) {
						merch.setContact_mobile(voucherEntity.getVoucherContent());
					}
					merch.setVisit_time((merch.getExpire_time().getTime() - merch.getStart_time().getTime()) / 86400000.0);
					break;
				}
			}

			//获取订单的区域场次座位信息 因为目前库存是按订单进行存储 为了兼容之前的数据结构所以放到第一个merch中
			final Map<String, String> seatNum = querySeatByTransactionId(transaction_id);
			merchs.get(0).setSeatNumbers(seatNum.get("seatNumbers"));
			Map<String, String> screeningMsg = queryscreeningMsg(merch.getProductId());
			merch.setScreening(screeningMsg.get("screening"));
			merch.setRegion(screeningMsg.get("region"));

		}
		return merchs;
	}

	/**
	 * 组装voucher信息的属性，放在商品下
	 *
	 * @param merchs
	 * @param executeResult
	 */
	public List<MerchResponse> assembleVoucherInfos(final List<MerchResponse> merchs, final ExecuteResult<List<VoucherEntity>> executeResult,
			final String transaction_id) {
		if (Check.NuNObject(executeResult) || Check.NuNObject(executeResult.getData())) {
			return merchs;
		}
		if (merchs != null && merchs.size() > 0) {
			for (final MerchResponse merch : merchs) {
				for (final VoucherEntity voucherEntity : executeResult.getData()) {
					if (merch.getVoucherId() == voucherEntity.getVoucherId()) {
						if (voucherEntity.getVoucherContentType() == 3) {
							merch.setVoucher_content(voucherEntity.getVoucherContent());
						} else if (voucherEntity.getVoucherContentType() == 2) {
							merch.setQr_code(voucherEntity.getVoucherContent());
						}
						if (voucherEntity.getSceneId() > 0) {
							merch.setSceneId(voucherEntity.getSceneId());
						}
						merch.setVisit_time((merch.getExpire_time().getTime() - merch.getStart_time().getTime()) / 86400000.0);
						break;
					}
				}
				Map<String, String> screeningMsg = queryscreeningMsg(merch.getProductId());
				merch.setScreening(screeningMsg.get("screening"));
				merch.setRegion(screeningMsg.get("region"));
			}
			//获取订单的区域场次座位信息 因为目前库存是按订单进行存储 为了兼容之前的数据结构所以放到第一个merch中

			final Map<String, String> seatNum = querySeatByTransactionId(transaction_id);
			merchs.get(0).setSeatNumbers(seatNum.get("seatNumbers"));
		}
		return merchs;
	}

	public Map<String, String> querySeatByTransactionId(final String transaction_id) {
		final Map<String, String> seatNum = new HashMap<String, String>();
		final Result<QueryValidSeatRecordResponse> result = seatRecordQueryService.queryValidSeatRecordByTransactionId(transaction_id);
		final StringBuilder seatNumbers = new StringBuilder();
		if (result.getData() != null) {
			final List<QuerySeatRecordResponse> querySeatDescResModels = result.getData().getQuerySeatRecordResponses();
			if (querySeatDescResModels != null && querySeatDescResModels.size() > 0) {
				for (int i = 0; i < querySeatDescResModels.size(); i++) {
					if (!Check.NuNObject(querySeatDescResModels.get(i).getSeatName())) {
						seatNumbers.append(querySeatDescResModels.get(i).getSeatName());
						if (querySeatDescResModels.size() - 1 > i) {
							seatNumbers.append(",");
						}
					}
				}
			}
		}
		seatNum.put("seatNumbers", seatNumbers.toString());
		return seatNum;
	}

	private Map<String, String> queryscreeningMsg(long productId) {
		logger.info("进入到queryscreeningMsg查询接口：查询的productid为：" + productId);
		Result<SpuSkuProductOutModel> result = spuProductService.getSkuWithSpuBySkuId(productId);
		final Map<String, String> screeningMsg = new HashMap<String, String>();
		// 场次id集合
		List<Long> screeingIds = new ArrayList<Long>();
		// 区域id集合
		List<Long> areaIds = new ArrayList<Long>();
		if (result != null && result.isOk()) {
			logger.info("查询产品服务接口成功");
			SpuSkuProductOutModel outModel = result.getData();
			if (!Check.NuNObject(outModel)) {
				for (SkuProductOutModel spom : outModel.getSkuProductResuts()) {
					if (spom.getId().equals(productId)) {
						if (!Check.NuNObject(spom.getRegionId())) {
							logger.info("获取的spom.getRegionId())是：" + spom.getRegionId());
							areaIds.add(Long.valueOf(spom.getRegionId()));
						}
						if (!Check.NuNObject(spom.getRondaId())) {
							logger.info("获取的spom.getRondaId())是：" + spom.getRondaId());
							screeingIds.add(Long.valueOf(spom.getRondaId()));
						}
					}
				}
			}

		}
		ScreeingAreaReqModel reqModel = new ScreeingAreaReqModel();
		reqModel.setScreeingIds(screeingIds);
		reqModel.setAreaIds(areaIds);
		if (Check.NuNCollections(screeingIds) || Check.NuNCollections(areaIds)) {
			return screeningMsg;
		}
		Result<ScreeingAreaRespModel> screeingAreaRespModels = screeingsQueryService.queryScreeingAreaBaseInfo(reqModel, null);
		if (screeingAreaRespModels != null && screeingAreaRespModels.isOk()) {
			logger.info("查询库存服务接口成功");
			List<ScreeingsModel> screeings = screeingAreaRespModels.getData().getScreeings();
			List<AreaModel> areas = screeingAreaRespModels.getData().getAreas();
			if (screeings != null && screeings.size() > 0) {
				logger.info("screeings.get(0).getName()是：" + screeings.get(0).getName());
				screeningMsg.put("screening", screeings.get(0).getName());
			}
			if (areas != null && areas.size() > 0) {
				logger.info("areas.get(0).getName()是：" + areas.get(0).getName());
				screeningMsg.put("region", areas.get(0).getName());
			}
		}
		return screeningMsg;

	}
}
