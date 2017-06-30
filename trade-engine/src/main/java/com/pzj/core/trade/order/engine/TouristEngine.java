package com.pzj.core.trade.order.engine;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.voucher.write.VoucherWriteMapper;
import com.pzj.trade.merch.common.VourTypeEnum;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.entity.TouristEditModel;
import com.pzj.trade.order.entity.TouristEntity;
import com.pzj.trade.order.model.OrderTouristOutModel;
import com.pzj.trade.order.model.TouristEditInModel;
import com.pzj.trade.order.read.TouristReadMapper;
import com.pzj.trade.order.write.TouristWriteMapper;
import com.pzj.voucher.engine.exception.IllegalTouristException;
import com.pzj.voucher.engine.exception.TouristNotExistException;

/**
 * 游客引擎.
 * @author YRJ
 *
 */
@Component(value = "touristEngine")
public class TouristEngine {

	@Resource(name = "touristReadMapper")
	private TouristReadMapper touristReadMapper;

	@Resource(name = "touristWriteMapper")
	private TouristWriteMapper touristWriteMapper;

	@Resource(name = "merchWriteMapper")
	private MerchWriteMapper merchWriteMapper;

	@Resource(name = "voucherWriteMapper")
	private VoucherWriteMapper voucherWriteMapper;

	public List<OrderTouristOutModel> queryTouristByMerchId(final String orderId, final String merchId) {
		final List<TouristEntity> tourists = touristReadMapper.getByOrderMerchId(orderId, merchId);
		if (tourists == null) {
			return null;
		}

		final List<OrderTouristOutModel> outModels = new ArrayList<OrderTouristOutModel>();
		for (final TouristEntity tourist : tourists) {
			final OrderTouristOutModel outModel = new OrderTouristOutModel();
			outModel.setTouristId(tourist.getTourist_id());
			outModel.setName(tourist.getName());
			outModel.setMobile(tourist.getMobile());
			outModel.setIdcard(tourist.getIdcard());
			outModel.setMerchId(merchId);
			outModel.setNameSpell(tourist.getName_spell());
			outModel.setOther(tourist.getOther());
			outModels.add(outModel);
		}
		return outModels;
	}

	@Transactional(value = "trade.transactionManager", timeout = 2)
	public void updateTourist(final TouristEditInModel inModel) {
		final TouristEditModel editModel = assembyUpdateModel(inModel);

		final TouristEntity tourist = touristWriteMapper.queryTouristById(editModel.getOrder_id(), editModel.getTourist_id());//不考虑并发.
		if (tourist == null) {
			throw new TouristNotExistException(10403, "游客信息不存在, orderId: [" + editModel.getOrder_id() + "], touristId: [" + editModel.getTourist_id() + "]");
		}

		final MerchEntity merch = merchWriteMapper.getMerchByMerchId(tourist.getMerch_id());
		if (merch == null) {
			throw new IllegalTouristException(10404, "订单ID: [" + editModel.getOrder_id() + "], 游客: [" + editModel.getTourist_id() + "], 商品ID: ["
					+ tourist.getMerch_id() + "], 商品不存在.");
		}

		touristWriteMapper.updateTourist(editModel);
		//当凭证类型为身份证时, 需要更新凭证信息.
		if (VourTypeEnum.CARDID.getVourType() == merch.getVour_type()) {
			voucherWriteMapper.updateContextByVoucherId(merch.getVoucher_id(), editModel.getIdcard());
		}
	}

	/**
	 * 组装更新模型.
	 * @param inModel
	 */
	private TouristEditModel assembyUpdateModel(final TouristEditInModel inModel) {
		final TouristEditModel editModel = new TouristEditModel();
		editModel.setTourist_id(inModel.getTouristId());
		editModel.setOrder_id(inModel.getOrderId());
		editModel.setName(inModel.getName());
		editModel.setMobile(inModel.getMobile());
		editModel.setIdcard(inModel.getCardId());
		return editModel;
	}

}
