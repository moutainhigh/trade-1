package com.pzj.core.trade.voucher.write;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.voucher.entity.VoucherConfirmNumEntity;
import com.pzj.voucher.entity.VoucherConfirm;

public interface VoucherConfirmWriteMapper {

	/**
	 * 根据凭证ID查询对应的检票点信息.
	 * @param voucherId
	 * @return
	 */
	List<VoucherConfirmNumEntity> queryVoucherConfirmByVoucherId(@Param(value = "voucher_id") long voucherId);

	/**
	 * @date 2017年3月29日16:31:16
	 * @author DongChunfu 添加每次核销时间
	 *
	 * @param voucherId
	 * @param productId
	 * @param childProductId
	 * @param num
	 * @return
	 */
	Integer updateVoucherConfirmUsedTimes(@Param("voucherId") Long voucherId, @Param("productId") Long productId,
			@Param("childProductId") Long childProductId, @Param("num") Integer num, @Param("checkTime") Date checkTime);

	Integer batchInsertVoucherConfirm(List<VoucherConfirm> vcList);
}
