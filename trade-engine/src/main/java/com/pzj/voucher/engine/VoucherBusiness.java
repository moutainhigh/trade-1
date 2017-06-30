package com.pzj.voucher.engine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pzj.core.trade.voucher.read.VoucherExtendReadMapper;
import com.pzj.core.trade.voucher.read.VoucherReadMapper;
import com.pzj.framework.toolkit.Check;
import com.pzj.voucher.common.VoucherAttrEnum;
import com.pzj.voucher.entity.ExtendVoucher;
import com.pzj.voucher.entity.VoucherEntity;

@Component(value = "voucherBusiness")
@Transactional(propagation = Propagation.REQUIRED)
public class VoucherBusiness {

	@Autowired
	private VoucherReadMapper voucherReadMapper;

	@Autowired
	private VoucherExtendReadMapper voucherExtendReadMapper;

	public List<VoucherEntity> queryVoucherByParam(final VoucherEntity baseVoucher) {
		final List<VoucherEntity> vouchers = this.voucherReadMapper.queryBaseVoucherByBaseVoucher(baseVoucher);
		if (vouchers != null) {
			for (final VoucherEntity voucher : vouchers) {//需要查询景区ID--from 李智勇
				final List<ExtendVoucher> extendVoucherList = this.voucherExtendReadMapper.queryExtendVoucherListByVoucherId(voucher.getVoucherId());
				if (!Check.NuNCollections(extendVoucherList)) {
					for (final ExtendVoucher extend : extendVoucherList) {
						if (extend.getVoucherAttr().equals(VoucherAttrEnum.ATTR_TICKET_SCENIC_ID_INFO.getValue())) {
							final JSONObject json = JSON.parseObject(extend.getVoucherAttrContent());
							final long scenicId = json.getLong("scenic");
							voucher.setSceneId(scenicId);
						}
					}
				}
			}
		}
		return vouchers;
	}

}
