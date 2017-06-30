package com.pzj.core.trade.voucher.write;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.voucher.entity.VoucherTimeEntity;
import com.pzj.core.trade.voucher.entity.VourEntity;
import com.pzj.voucher.entity.VoucherEntity;

public interface VoucherWriteMapper {

	Integer insertSelective(VoucherEntity record);

	/**
	 * 更新凭证状态及核销信息.
	 * @param voucher_id
	 */
	void updateVouchConfirmStatusById(@Param("voucher_id") long voucher_id, @Param("state") int state,
			@Param("checkTime") Date checkTime);

	/**
	 * 凭证绑定.
	 * @param bindModels
	 */
	//void bindTransaction(@Param(value = "mfcodes") List<VoucherBindEntity> bindModels);

	/**
	 * 根据凭证ID查询对应的凭证信息.
	 * @param voucherId
	 * @return
	 */
	VourEntity queryVoucherById(@Param(value = "voucher_id") long voucherId);

	/**
	 * 根据交易流水号查询对应的凭证信息.
	 * @param transactionId
	 * @return
	 */
	//List<VourEntity> queryVoucherByTransactionId(@Param(value = "transaction_id") String transactionId);

	/**
	 * 获取凭证的开始、有效期时间点.
	 * @param voucherId
	 */
	VoucherTimeEntity queryVoucherTimePointByVourId(long voucherId);

	/**
	 * 根据凭证ID获取凭证（锁）
	 * @author DongChunfu
	 * @param voucherId
	 * @return
	 */
	VourEntity queryVoucherForConfirmLockById(@Param(value = "voucher_id") long voucherId);

	/**
	 * 更新凭证值.
	 * @param voucher_id
	 * @param content
	 */
	void updateContextByVoucherId(@Param(value = "voucher_id") long voucher_id, @Param(value = "content") String content);
}
