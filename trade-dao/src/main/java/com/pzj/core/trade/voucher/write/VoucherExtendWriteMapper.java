package com.pzj.core.trade.voucher.write;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.voucher.entity.ExtendVoucherEntity;
import com.pzj.voucher.entity.ExtendVoucher;

public interface VoucherExtendWriteMapper {

	Integer updateByPrimaryKey(ExtendVoucher record);

	void batchInsertExtendVoucher(List<ExtendVoucher> extendVoucherList);

	/**
	 * 获取凭证对应的所有扩展信息.
	 * @param voucher_id
	 * @return
	 */
	List<ExtendVoucherEntity> queryExtendsByVoucherId(long voucher_id);

	/**
	 * 获取凭证对应的制定属性Key的扩展信息.
	 * @param voucher_id
	 * @param key
	 * @return
	 */
	ExtendVoucherEntity queryExtendsByVoucherIdAndAttribuate(@Param(value = "voucher_id") long voucher_id, @Param(value = "voucher_attr") String voucher_attr);
}
