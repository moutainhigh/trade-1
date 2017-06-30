package com.pzj.trade.confirm.write;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.confirm.entity.ConfirmCodeEntity;

/**
 * 核销码
 * @author YRJ
 *
 */
public interface ConfirmCodeWriteMapper {

	/**
	 * 根据供应商和核销码查询该码是否存在.
	 * @param mfcode
	 * @param supplierId
	 * @return
	 */
	List<ConfirmCodeEntity> queryConfirmCodeByMfCode(@Param(value = "mfcode") String mfcode,
			@Param(value = "supplierId") long supplierId);

	/**
	 * 更新核销码状态.
	 * @param codeId
	 * @param state
	 * @param source
	 */
	void updateConfirmCodeStateByOrderAndMerchId(@Param(value = "codes") List<ConfirmCodeEntity> codes);
}
