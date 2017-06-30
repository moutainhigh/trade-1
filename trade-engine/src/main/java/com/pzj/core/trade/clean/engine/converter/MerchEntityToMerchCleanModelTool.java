package com.pzj.core.trade.clean.engine.converter;

import com.pzj.core.trade.clean.engine.model.MerchCleanCreatorModel;
import com.pzj.trade.merch.entity.MerchEntity;

public class MerchEntityToMerchCleanModelTool {
	/**
	 * 将订单商品信息转化为结算清算请求参数
	 * @param merch
	 * @param rootMerch
	 * @return
	 */
	public static final MerchCleanCreatorModel getCleanModel(final MerchEntity merch, final MerchEntity rootMerch) {
		MerchCleanCreatorModel cleanModel = new MerchCleanCreatorModel();
		cleanModel.setVourType(merch.getVour_type());
		cleanModel.setMerchId(merch.getMerch_id());
		cleanModel.setOrderId(merch.getOrder_id());
		cleanModel.setCheckNum(merch.getCheck_num());
		cleanModel.setTotalNum(merch.getTotal_num());
		cleanModel.setRefundNum(merch.getRefund_num());
		cleanModel.setPrice(merch.getPrice());
		cleanModel.setExpireDate(merch.getExpire_time());
		cleanModel.setIsRefunding(merch.getIs_refunding());
		cleanModel.setMerchState(merch.getMerch_state());
		cleanModel.setProductId(merch.getProduct_id());
		cleanModel.setRefundAmount(merch.getRefund_amount());
		cleanModel.setIsCleaned(merch.getIs_cleaned());

		cleanModel.setRootMerchId(rootMerch.getMerch_id());
		cleanModel.setRootPrice(rootMerch.getPrice());
		cleanModel.setRootRefundNum(rootMerch.getRefund_num());
		cleanModel.setRootRefundAmount(rootMerch.getRefund_amount());
		cleanModel.setRootTotalNum(rootMerch.getTotal_num());
		return cleanModel;
	}
}
