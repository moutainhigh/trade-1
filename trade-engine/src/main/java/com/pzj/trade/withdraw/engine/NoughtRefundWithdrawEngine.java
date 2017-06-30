package com.pzj.trade.withdraw.engine;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.settlement.balance.request.WithdrawVo;
import com.pzj.settlement.balance.service.SettlementCall;
import com.pzj.trade.constant.TradeConstant;
import com.pzj.trade.mq.MQUtil;
import com.pzj.trade.payment.common.PayFlowStateEnum;
import com.pzj.trade.payment.entity.CashPostalEntity;
import com.pzj.trade.payment.write.CashPostalWriteMapper;
import com.pzj.trade.withdraw.common.WithdrawTypeEnum;
import com.pzj.trade.withdraw.exception.WithdrawException;
import com.pzj.trade.withdraw.model.CashPostalModel;

/**
 * 无退款提现引擎.</br>
 * 当用户类型不是分销商时, 不走退款提现.
 * 当用户类型为分销商时, 需要判断该分销商是否有第三方支付的订单且支付金额大于提现金额.
 * @author YRJ
 *
 */
@Component(value = "noughtRefundWithdrawEngine")
public class NoughtRefundWithdrawEngine {

	private final static Logger logger = LoggerFactory.getLogger(NoughtRefundWithdrawEngine.class);

	@Resource(name = "mqUtil")
	private MQUtil mqutil;

	@Resource(name = "cashPostalWriteMapper")
	private CashPostalWriteMapper cashPostalWriteMapper;

	@Autowired
	private SettlementCall settlementCall;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	public void doWithdraw(final CashPostalModel cashPostalModel) {
		CashPostalEntity cashPostalEntity = generateCashPostalEntity(cashPostalModel);
		cashPostalWriteMapper.insertCashPostalEntity(cashPostalEntity);

		WithdrawVo vo = new WithdrawVo();
		vo.setAccountUnitId(TradeConstant.PLATFORM_ACCOUNT);
		vo.setWithdrawMoney(cashPostalModel.getCashPostalMoney());
		if (cashPostalModel.getUserType() == UserTypeEnum.Reseller.getValue() && cashPostalModel.isQR()) {
			vo.setIsQrCode(1);
		} else {
			vo.setIsQrCode(2);
		}
		vo.setAccountType(cashPostalModel.getUserType());
		vo.setUserId(cashPostalModel.getAccountId());
		vo.setWithdrawId(cashPostalEntity.getPostal_id());

		logger.info("call settlementCall.withdraw param:" + JSONConverter.toJson(vo));
		Result<Boolean> result = settlementCall.withdraw(vo);
		logger.info("call settlementCall.withdraw result:" + JSONConverter.toJson(result));
		if (!result.isOk()) {
			throw new WithdrawException(10501, result.getErrorMsg());
		}
	}

	/**
	 * 生成提现记录.
	 * @param cashPostal
	 * @return
	 */
	private CashPostalEntity generateCashPostalEntity(final CashPostalModel cashPostal) {
		CashPostalEntity postalEntity = new CashPostalEntity();
		postalEntity.setTarget_postal_money(cashPostal.getCashPostalMoney());
		postalEntity.setPostal_money(cashPostal.getCashPostalMoney());
		postalEntity.setAccount_id(cashPostal.getAccountId());
		postalEntity.setPostal_status(PayFlowStateEnum.PaySuccess.getKey());
		postalEntity.setWithdraw_type(WithdrawTypeEnum.notlineWithdraw.getKey());
		return postalEntity;
	}
}
