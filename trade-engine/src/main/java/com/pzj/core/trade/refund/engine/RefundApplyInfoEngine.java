/**
 * 
 */
package com.pzj.core.trade.refund.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.refund.entity.RefundApplyInfoEntity;
import com.pzj.core.trade.refund.write.RefundApplyInfoWriteMapper;
import com.pzj.trade.refund.model.RefundAuditReqModel;

/**
 * 退款申请信息操作引擎
 *
 * @author DongChunfu
 * @date 2016年12月9日
 */
@Component(value = "refundApplyInfoEngine")
public class RefundApplyInfoEngine {

	@Autowired
	private RefundApplyInfoWriteMapper refundRefuseInfoWriteMapper;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED)
	public long addRefundRefuseInfo(final RefundAuditReqModel reqModel) {
		final RefundApplyInfoEntity refuseInfo = buildRefundRefuseInfo(reqModel);
		refundRefuseInfoWriteMapper.addRefundRefuseInfo(refuseInfo);
		return refuseInfo.getInfoId();
	}

	private RefundApplyInfoEntity buildRefundRefuseInfo(final RefundAuditReqModel reqModel) {
		final RefundApplyInfoEntity refuseInfo = new RefundApplyInfoEntity();
		refuseInfo.setRefundId(reqModel.getRefundId());
		refuseInfo.setReason(reqModel.getRefusedMsg());
		return refuseInfo;
	}
}
