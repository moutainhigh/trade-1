package com.pzj.trade.withdraw.converter;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.trade.constant.TradeConstant;
import com.pzj.trade.payment.write.CashPostalWriteMapper;
import com.pzj.trade.withdraw.model.CashPostalModel;

/**
 * 提现参数验证器.
 * @author YRJ
 *
 */
@Component(value = "cashPostalConverter")
public class CashPostalConverter implements ObjectConverter<CashPostalModel, ServiceContext, Result<Boolean>> {

	@Autowired
	private CashPostalWriteMapper cashPostalWriteMapper;

	@Resource(name = "complyRefundCashPostalConverter")
	private ObjectConverter<CashPostalModel, ServiceContext, Result<Boolean>> complyRefundCashPostalConverter;

	@Override
	public Result<Boolean> convert(CashPostalModel model, ServiceContext y) {
		if (model.getCashPostalMoney() <= 0) {
			return new Result<Boolean>(43001, "提现金额必须大于零");
		}

		if (!checkCanWithdraw(model.getAccountId())) {
			return new Result<Boolean>(43001, "两次提现发起的间隔不可超过[" + TradeConstant.OPERATION_INTERVAL + "]秒");
		}

		Result<Boolean> ifRefund = complyRefundCashPostalConverter.convert(model, null);
		return ifRefund;
	}

	/**
	 * 判断提现间隔.
	 * @param account_id
	 * @return
	 */
	private boolean checkCanWithdraw(long account_id) {
		//获取最后一次提现任务，for update  order by create_time desc limit 1 for update
		//        CashPostalEntity entity = cashPostalWriteMapper.getCashPostalEntityForWithdraw(account_id);
		//        //TODO 时间计算问题！！！
		//        if (entity != null) {
		//            if (entity.getUpdate_time().getTime() - entity.getCreate_time().getTime() < 1000 * TradeConstant.OPERATION_INTERVAL) {
		//                return false;
		//            }
		//        }
		return true;
	}
}
