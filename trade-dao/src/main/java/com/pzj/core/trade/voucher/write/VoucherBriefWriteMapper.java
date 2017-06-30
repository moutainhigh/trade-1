package com.pzj.core.trade.voucher.write;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.voucher.entity.VoucherBriefEntity;
import com.pzj.core.trade.voucher.entity.VoucherConfirmBriefEntity;
import com.pzj.core.trade.voucher.entity.VoucherExtendBriefEntity;

/**
 * 简化后的voucher mapper
 *
 * @author chj
 * @version $Id: VoucherBriefWriteMapper.java, v 0.1 2017年3月22日 下午4:32:03 Administrator Exp $
 */
public interface VoucherBriefWriteMapper {

	Integer insertVoucherBase(VoucherBriefEntity voucher);

	Integer insertVoucherConfirms(@Param(value = "voucherConfirms") List<VoucherConfirmBriefEntity> voucherConfirms);

	Integer insertVoucherExtends(@Param(value = "voucherExtends") List<VoucherExtendBriefEntity> voucherExtends);
}
