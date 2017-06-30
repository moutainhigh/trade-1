package com.pzj.core.trade.refund.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.refund.engine.common.RefundFlowStateEnum;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.write.RefundApplyWriteMapper;
import com.pzj.trade.refund.model.RefundApplyReqModel;

/**
 * 退款申请信息执行引擎.
 * 
 * @author DongChunfu
 */
@Component(value = "addRefundApplyEngine")
public class AddRefundApplyEngine {

	@Autowired
	private RefundApplyWriteMapper refundApplyWriteMapper;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED)
	public RefundApplyEntity add(final RefundApplyReqModel reqModel, final String refundId, final int isParty,
			final int refundAuditState) {

		final RefundApplyEntity refundApply = buildRefundApplyInfo(reqModel, refundId, isParty, refundAuditState);
		refundApplyWriteMapper.addRefundApply(refundApply);
		return refundApply;
	}

	/**
	 * 构建退款申请信息
	 * 
	 * @param reqModel
	 *            退款申请请求参数
	 * @param refundId
	 *            退款ID
	 * @param isParty
	 *            部分退款
	 * @param refundAuditStae
	 *            退款审核状态
	 * @param refundState
	 *            退款状态
	 * @return
	 */
	private RefundApplyEntity buildRefundApplyInfo(final RefundApplyReqModel reqModel, final String refundId,
			final int isParty, final int refundAuditState) {
		final RefundApplyEntity refundApply = RefundApplyEntity.newInstance();

		refundApply.setRefundId(refundId);
		refundApply.setApplierId(reqModel.getInitiatorId());
		refundApply.setInitParty(reqModel.getInitParty());

		refundApply.setIsForce(reqModel.getRefundType());
		refundApply.setIsParty(isParty);
		refundApply.setRefundState(RefundFlowStateEnum.REFUNDING.getState());
		refundApply.setRefundAuditState(refundAuditState);

		return refundApply;
	}

}
