package com.pzj.voucher.engine;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.voucher.read.VoucherExtendReadMapper;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.voucher.engine.model.ProductInfo;
import com.pzj.voucher.entity.ExtendVoucher;

/**
 * 凭证扩展信息执行引擎.
 * @author YRJ
 *
 */
@Component(value = "voucherExtendEngine")
public class VoucherExtendEngine {

	private final static Logger logger = LoggerFactory.getLogger(VoucherExtendEngine.class);

	@Resource(name = "merchReadMapper")
	private MerchReadMapper merchReadMapper;

	@Resource(name = "voucherExtendReadMapper")
	private VoucherExtendReadMapper voucherExtendReadMapper;

	/**
	 * 通过凭证ID查询特定的凭证扩展信息.
	 */
	public List<ExtendVoucher> queryExtendVoucherByVoucherId(final long voucherId) {
		final List<MerchEntity> merchs = merchReadMapper.getMerchByVoucherId(voucherId);
		logger.info("凭证[" + voucherId + "], 商品信息: " + (merchs == null ? 0 : merchs.size()));
		if (Check.NuNCollections(merchs)) {
			return null;
		}
		final List<ExtendVoucher> extVours = voucherExtendReadMapper.queryExtendVoucherListByVoucherId(voucherId);

		for (MerchEntity merchEntity : merchs) {//组装产品信息
			final ProductInfo prodInfo = new ProductInfo();
			prodInfo.setProdId(merchEntity.getProduct_id());
			prodInfo.setMerchName(merchEntity.getMerch_name());
			prodInfo.setMerchNum(merchEntity.getTotal_num() - merchEntity.getRefund_num());
			prodInfo.setVarie(merchEntity.getProduct_varie());

			final ExtendVoucher extVour = new ExtendVoucher();
			extVour.setSupplierId(merchEntity.getSupplier_id());
			extVour.setVoucherAttr("productInfo");
			extVour.setVoucherAttrContent(prodInfo.toString());
			extVour.setVoucherId(voucherId);
			extVours.add(extVour);
		}
		logger.info("凭证[" + voucherId + "], 扩展信息: " + (JSONConverter.toJson(extVours)));
		return extVours;
	}
}
