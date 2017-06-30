package com.pzj.core.trade.confirm.engine;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.confirm.common.MfCodeStateEnum;
import com.pzj.core.trade.confirm.exception.ConfirmCodeException;
import com.pzj.core.trade.voucher.read.VoucherReadMapper;
import com.pzj.trade.confirm.entity.ConfirmCodeEntity;
import com.pzj.trade.confirm.model.MerchSimpleModel;
import com.pzj.trade.confirm.read.ConfirmCodeReadMapper;
import com.pzj.trade.confirm.response.MfcodeResult;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.voucher.entity.VoucherEntity;

@Component(value = "getConfirmCodeEngine")
public class GetConfirmCodeEngine {

	@Autowired
	private ConfirmCodeReadMapper confirmCodeReadMapper;

	@Autowired
	private MerchReadMapper merchReadMapper;

	@Autowired
	private VoucherReadMapper voucherReadMapper;

	public MfcodeResult getMfcode(final long codeId) {
		final ConfirmCodeEntity codeEntity = confirmCodeReadMapper.getMftourCodeById(codeId);
		if (codeEntity == null) {
			throw new ConfirmCodeException(10601, "魔方码获取失败, code: " + codeId);
		}

		final List<MerchEntity> merchs = merchReadMapper.getMerchByOrderId(codeEntity.getTransaction_id());
		if (merchs == null || merchs.isEmpty()) {
			throw new ConfirmCodeException(10601, "魔方码没有找到可用订单, code: " + codeId);
		}

		final MfcodeResult mfcode = copy(codeEntity, merchs);
		return mfcode;
	}

	private MfcodeResult copy(final ConfirmCodeEntity codeEntity, final List<MerchEntity> merchs) {
		final List<MerchSimpleModel> merchList = new ArrayList<MerchSimpleModel>();
		for (final MerchEntity merchEntity : merchs) {
			final MerchSimpleModel merchSimpleModel = new MerchSimpleModel();
			merchSimpleModel.setProduct_id(merchEntity.getProduct_id());
			merchSimpleModel.setTotal_num(merchEntity.getTotal_num() - merchEntity.getRefund_num());
			merchList.add(merchSimpleModel);
		}

		final MerchEntity merch = merchs.get(0);
		final VoucherEntity voucher = voucherReadMapper.selectByPrimaryKey(merch.getVoucher_id());

		final MfcodeResult mfcode = new MfcodeResult();
		mfcode.setOrderId(codeEntity.getOrder_id());
		mfcode.setSupplierId(codeEntity.getSupplier_id());
		mfcode.setMerchs(merchList);
		mfcode.setCodeState(codeEntity.getCode_state());
		mfcode.setCodeStateMsg(MfCodeStateEnum.getMfCodeStatus(codeEntity.getCode_state()).getMsg());
		mfcode.setMfcode(codeEntity.getMf_code());
		mfcode.setStartTime(voucher.getStartTime());
		mfcode.setEndTime(voucher.getExpireTime());
		mfcode.setCreateTime(codeEntity.getCreate_time());
		return mfcode;
	}
}
