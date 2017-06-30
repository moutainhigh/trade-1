/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.build;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.pzj.core.trade.order.engine.model.CheckinPointModel;
import com.pzj.core.trade.order.engine.model.MerchBaseModel;
import com.pzj.core.trade.order.model.VoucherInModel;
import com.pzj.core.trade.order.resolver.CodeGenerater;
import com.pzj.core.trade.voucher.entity.VoucherBriefEntity;
import com.pzj.core.trade.voucher.entity.VoucherConfirmBriefEntity;
import com.pzj.core.trade.voucher.entity.VoucherExtendBriefEntity;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.model.TouristModel;
import com.pzj.trade.merch.common.VerificationVourTypeEnum;
import com.pzj.trade.merch.common.VourTypeEnum;
import com.pzj.trade.order.model.MultiOrderInModel;
import com.pzj.voucher.common.VoucherAttrEnum;

/**
 * 
 * @author Administrator
 * @version $Id: VoucherAssebler.java, v 0.1 2017年3月16日 上午11:42:33 Administrator Exp $
 */
@Component
public class VoucherAssebler {
	private final static Logger logger = LoggerFactory.getLogger(VoucherAssebler.class);

	/*
	public List<VoucherBriefEntity> asseble(MultiOrderInModel orderInModel, List<MerchBaseModel> merchModels,
			boolean isOneVote, String transaction_id) {
		logger.info("创建voucher参数:" + JSONConverter.toJson(orderInModel) + JSONConverter.toJson(merchModels) + isOneVote);
		List<VoucherBriefEntity> vouchers = new ArrayList<VoucherBriefEntity>();
		if (isOneVote) {
			List<TouristModel> tourists = orderInModel.getTourists();
			for (TouristModel tourist : tourists) {
				VoucherInModel voucherInModel = new VoucherInModel();
				voucherInModel.setVoucher_content_type(VourTypeEnum.CARDID.getVourType());
				voucherInModel.setVoucher_content(tourist.getIdcardNo());
				voucherInModel.setTransaction_id(transaction_id);
				for (MerchBaseModel merch : merchModels) {
					if (merch.getProdId() == tourist.getProductId()) {
						setMerchInfo(voucherInModel, merch);
					}
				}
				VoucherBriefEntity voucher = generateVoucher(voucherInModel);
				vouchers.add(voucher);
			}
		} else {
			VoucherInModel voucherInModel = new VoucherInModel();
			voucherInModel.setVoucher_content_type(orderInModel.getVourType());
			voucherInModel.setVoucher_content(getVoucherContent(orderInModel));
			voucherInModel.setTransaction_id(transaction_id);
			for (MerchBaseModel merch : merchModels) {
				setMerchInfo(voucherInModel, merch);
			}
			VoucherBriefEntity voucher = generateVoucher(voucherInModel);
			vouchers.add(voucher);
		}
		return vouchers;
	}
	*/
	public List<VoucherBriefEntity> asseble(MultiOrderInModel orderInModel, List<MerchBaseModel> merchModels, String transaction_id) {
		int verificationVourType = merchModels.get(0).getVerificationVourType();
		logger.info("创建voucher参数:" + JSONConverter.toJson(orderInModel) + JSONConverter.toJson(merchModels) + verificationVourType);
		List<VoucherBriefEntity> vouchers = new ArrayList<VoucherBriefEntity>();
		if (verificationVourType == VerificationVourTypeEnum.CARDID_BY_NUM.getVourType()) {//身份证按分数核销
			assebleVoucherContentByNumForIdcard(orderInModel, merchModels, transaction_id, vouchers);
		} else if (verificationVourType == VerificationVourTypeEnum.MFCODE_BY_NUM.getVourType()) {//二维码按分数核销
			assebleVoucherContentByNumForMfcode(orderInModel, merchModels, transaction_id, vouchers);
		} else if (verificationVourType == VerificationVourTypeEnum.MFCODE_BY_SKU.getVourType()) {//二维码按规格核销
			for (MerchBaseModel merch : merchModels) {
				VoucherInModel voucherInModel = new VoucherInModel();
				voucherInModel.setVoucher_content_type(VourTypeEnum.MFCODE.getVourType());
				voucherInModel.setVoucher_content(CodeGenerater.generate(""));
				voucherInModel.setTransaction_id(transaction_id);
				setMerchInfo(voucherInModel, merch);
				VoucherBriefEntity voucher = generateVoucher(voucherInModel);
				vouchers.add(voucher);
			}
		} else {//二维码、联系人按订单核销
			VoucherInModel voucherInModel = new VoucherInModel();
			if (orderInModel.getVourType() == 4) {
				voucherInModel.setVoucher_content_type(VourTypeEnum.MFCODE.getVourType());
			} else {
				voucherInModel.setVoucher_content_type(orderInModel.getVourType());
			}

			//			voucherInModel.setVoucher_content_type(merchModels.get(0).getVourType());
			voucherInModel.setVoucher_content(getVoucherContent(orderInModel, merchModels.get(0)));
			voucherInModel.setTransaction_id(transaction_id);
			for (MerchBaseModel merch : merchModels) {
				setMerchInfo(voucherInModel, merch);
			}
			VoucherBriefEntity voucher = generateVoucher(voucherInModel);
			vouchers.add(voucher);
		}
		return vouchers;
	}

	/**
	 * 
	 * @param voucherInModel
	 * @param merch
	 */
	private void setMerchInfo(VoucherInModel voucherInModel, MerchBaseModel merch) {
		voucherInModel.setStrat_time(merch.getPlayDate());
		voucherInModel.setExpire_time(merch.getExpireTime());
		voucherInModel.setShow_start_time(merch.getShow_start_time());
		voucherInModel.setShow_end_time(merch.getShow_end_time());
		//			voucherInModel.setTransaction_id(merch.gett);
		voucherInModel.setVoucher_category(merch.getProdType());
		voucherInModel.setSupplier_id(merch.getSupplierId());
		voucherInModel.setSceneId(merch.getScenicId());
		voucherInModel.setCheckinPointModels(merch.getCheckinPointModels());
		voucherInModel.getProductIds().add(merch.getProdId());
	}

	/**
	 * 
	 * @param orderInModel
	 * @return
	 */
	//	private String getVoucherContent(MultiOrderInModel orderInModel) {
	private String getVoucherContent(MultiOrderInModel orderInModel, MerchBaseModel merchModel) {
		String conetent = "";
		if (orderInModel.getVourType() == VourTypeEnum.MFCODE.getVourType() || orderInModel.getVourType() == 4) {
			//		if (merchModel.getVourType() == VourTypeEnum.MFCODE.getVourType()) {
			conetent = CodeGenerater.generate("");
		} else {
			if (!Check.NuNObject(orderInModel.getContactee())) {
				conetent = orderInModel.getContactee().getContactMobile();
			}
		}
		return conetent;
	}

	/**
	 * 
	 * @param orderInModel
	 * @return
	 */
	private void assebleVoucherContentByNumForIdcard(MultiOrderInModel orderInModel, List<MerchBaseModel> merchModels, String transaction_id,
			List<VoucherBriefEntity> vouchers) {
		List<TouristModel> tourists = orderInModel.getTourists();
		for (TouristModel tourist : tourists) {
			VoucherInModel voucherInModel = new VoucherInModel();
			voucherInModel.setVoucher_content_type(VourTypeEnum.CARDID.getVourType());
			voucherInModel.setVoucher_content(tourist.getIdcardNo());
			voucherInModel.setTransaction_id(transaction_id);
			for (MerchBaseModel merch : merchModels) {
				if (merch.getProdId() == tourist.getProductId()) {
					setMerchInfo(voucherInModel, merch);
				}
			}
			VoucherBriefEntity voucher = generateVoucher(voucherInModel);
			vouchers.add(voucher);
		}

	}

	private void assebleVoucherContentByNumForMfcode(MultiOrderInModel orderInModel, List<MerchBaseModel> merchModels, String transaction_id,
			List<VoucherBriefEntity> vouchers) {
		for (MerchBaseModel merch : merchModels) {
			for (int i = 0; i < merch.getBuyNum(); i++) {
				VoucherInModel voucherInModel = new VoucherInModel();
				voucherInModel.setVoucher_content_type(VourTypeEnum.MFCODE.getVourType());
				voucherInModel.setVoucher_content(CodeGenerater.generate(""));
				voucherInModel.setTransaction_id(transaction_id);
				setMerchInfo(voucherInModel, merch);
				VoucherBriefEntity voucher = generateVoucher(voucherInModel);
				vouchers.add(voucher);
			}
		}
	}

	private VoucherBriefEntity generateVoucher(VoucherInModel voucherInModel) {
		VoucherBriefEntity voucher = new VoucherBriefEntity();
		voucher.setVoucherContentType(voucherInModel.getVoucher_content_type());
		voucher.setVoucherContent(voucherInModel.getVoucher_content());
		voucher.setStartTime(voucherInModel.getStrat_time());
		voucher.setExpireTime(voucherInModel.getExpire_time());
		voucher.setShowStartTime(voucherInModel.getShow_start_time());
		voucher.setShowEndTime(voucherInModel.getShow_end_time());
		voucher.setVoucherCategory(voucherInModel.getVoucher_category());
		voucher.setSupplierId(voucherInModel.getSupplier_id());
		voucher.setTransactionId(voucherInModel.getTransaction_id());
		voucher.setProductId(voucherInModel.getProductIds().get(0));
		if (!Check.NuNCollections(voucherInModel.getCheckinPointModels())) {
			for (CheckinPointModel checkinPoint : voucherInModel.getCheckinPointModels()) {
				for (Long productId : voucherInModel.getProductIds()) {
					VoucherConfirmBriefEntity voucherConfirm = new VoucherConfirmBriefEntity();
					voucherConfirm.setChildProductId(checkinPoint.getCheckinPointId());
					if (checkinPoint.getMaxUseTimes() == 0) {
						voucherConfirm.setMaxUseTimes(-99);
					} else {
						voucherConfirm.setMaxUseTimes(checkinPoint.getMaxUseTimes());
					}
					voucherConfirm.setProductId(productId);//其实不用维护
					voucherConfirm.setSupplierId(voucherInModel.getSupplier_id());
					voucher.getVoucherConfirms().add(voucherConfirm);
				}

			}
		}
		if (voucherInModel.getSceneId() > 0) {
			VoucherExtendBriefEntity voucherExtend = new VoucherExtendBriefEntity();
			voucherExtend.setSupplier_id(voucherInModel.getSupplier_id());
			voucherExtend.setVoucher_attr(VoucherAttrEnum.ATTR_TICKET_SCENIC_ID_INFO.getValue());
			JSONObject scene = new JSONObject();
			scene.put(VoucherAttrEnum.ATTR_TICKET_SCENIC_INFO.getValue(), voucherInModel.getSceneId());
			voucherExtend.setVoucher_attr_content(scene.toString());
			voucher.setVoucherExtend(voucherExtend);
		}
		return voucher;
	}
}
