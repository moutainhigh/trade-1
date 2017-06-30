package com.pzj.trade.withdraw.converter;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.framework.context.Result;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.payment.write.CashPostalWriteMapper;
import com.pzj.trade.withdraw.common.TakenStateEnum;
import com.pzj.trade.withdraw.engine.UserTypeEnum;
import com.pzj.trade.withdraw.model.CashPostalModel;
import com.pzj.trade.withdraw.model.ResellerAccountType;

/**
 * 判断是否走退款流程提现.
 * @author YRJ
 *
 */
@Component(value = "complyRefundCashPostalConverter")
public class ComplyRefundCashPostalConverter implements ObjectConverter<CashPostalModel, Void, Result<Boolean>> {

    @Resource(name = "cashPostalWriteMapper")
    private CashPostalWriteMapper cashPostalWriteMapper;

    @Override
    public Result<Boolean> convert(CashPostalModel model, Void y) {
        //用户类型判断是否可以线上退款提现
        UserTypeEnum userType = UserTypeEnum.getUserType(model.getUserType());
        if (!userType.isWithdrawable()) {
            return new Result<Boolean>(43001, "用户不可提现");
        }

        //分销商提现, 可走线上退款进行提现.
        if (userType != UserTypeEnum.Reseller) {
            return new Result<Boolean>(Boolean.FALSE);
        }

        boolean isRefund = false;
        //二维码分销商（微店），不可走线上退款进行提现
        if (ResellerAccountType.QR_CODE.getType() != model.getResellerType()) {
            isRefund = checkDrawingOfReseller(model.getAccountId(), model.getCashPostalMoney());
        }
        return new Result<Boolean>(isRefund);
    }

    /**
     * 判断账户是否可执行退款提现.
     * @param account_id            帐号ID
     * @param cashPostalMoney       提现金额
     * @return  true 可以走退票流程,false 不可走退票流程
     */
    @SuppressWarnings("rawtypes")
    private boolean checkDrawingOfReseller(long account_id, double cashPostalMoney) {
        //获取当前账号可提现金额的总和 ,sum(can_postal_money-postal_money)
        Map takenMap = cashPostalWriteMapper.searchTakenMoneyForAccount(account_id, TakenStateEnum.canTaken.getKey());
        if (Check.NuNObject(takenMap) || Check.NuNObject(takenMap.get("postalmoney"))) {
            return false;
        }
        double takenMoney = Double.parseDouble(takenMap.get("postalmoney").toString());
        if (takenMoney == 0 || takenMoney < cashPostalMoney) {
            return false;
        }
        return true;
    }

}
