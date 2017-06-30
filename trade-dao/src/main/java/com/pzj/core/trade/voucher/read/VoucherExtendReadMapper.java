package com.pzj.core.trade.voucher.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.voucher.entity.ExtendVoucher;

public interface VoucherExtendReadMapper {

	List<ExtendVoucher> queryExtendVoucherListByVoucherId(Long voucherId);

	List<ExtendVoucher> queryExtendVoucherListByParam(ExtendVoucher extendVoucher);

	List<ExtendVoucher> queryListByParam(@Param("voucherIds") List<Long> voucherIds, @Param("voucherAttr") String voucherAttr);
}
