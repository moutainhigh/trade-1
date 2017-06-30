package com.pzj.core.trade.voucher.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.voucher.entity.BasicQueryModel;
import com.pzj.core.trade.voucher.entity.VoucherBasicEntity;
import com.pzj.voucher.entity.VoucherEntity;

public interface VoucherReadMapper {

	/**
	 * 根据VoucherId查询Voucher信息.
	 * @param voucherId
	 * @return
	 */
	VoucherEntity selectByPrimaryKey(Long voucherId);

	/**
	 * 根据VoucherVo为参数查询Voucher
	 * @param BaseVoucher 基本查询参数
	 * @return voucher集合
	 */
	List<VoucherEntity> queryBaseVoucherByBaseVoucher(VoucherEntity BaseVoucher);

	/**
	 * 根据供应商及核销码查询可用的凭证信息.
	 * @param queryModel
	 * @return
	 */
	List<VoucherEntity> queryUsableVoucherBySupplierId(BasicQueryModel queryModel);

	/**
	 * 根据核销凭证信息检索凭证ID列表.
	 * @param vourContent
	 * @return
	 */
	List<Long> queryVoucherByContent(@Param("vourContent") String vourContent);

	/**
	 * 根据voucherId查询voucher基本属性
	 */
	VoucherBasicEntity queryVoucherBasicById(@Param("voucherId") Long voucherId);

	/**
	 * 根据交易ID查询对应的凭证.
	 * @param transactionId
	 * @return
	 */
	List<VoucherEntity> queryVoucherByTransactionId(@Param("transactionId") String transactionId);
}
