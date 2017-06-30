package com.pzj.core.trade.voucher.read;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.voucher.entity.VoucherConfirm;
import com.pzj.voucher.entity.VoucherConfirmUsedTimes;

public interface VoucherConfirmReadMapper {

	VoucherConfirm selectByPrimaryKey(Integer id);

	VoucherConfirm queryVoucherConfim(@Param("voucherId") Long voucherId, @Param("productId") Long productId, @Param("childProductId") Long childProductId);

	List<VoucherConfirm> queryVoucherConfimByProductId(@Param("productId") Long productId, @Param("voucheId") long voucheId);

	/**
	 * 根据凭证ID查询检票信息.
	 * @param voucherId
	 * @return
	 */
	List<VoucherConfirm> queryVoucherConfimList(@Param("voucherId") Long voucherId);

	/**
	 * 根据条件查询查询检票信息
	 * 
	 * */
	List<VoucherConfirmUsedTimes> queryVoucherConfimByParameter(@Param("checkinPointIds") List<Long> checkinPointIds,
			@Param("idcardNos") List<String> idcardNos, @Param("playDate") Date playDate);
}
