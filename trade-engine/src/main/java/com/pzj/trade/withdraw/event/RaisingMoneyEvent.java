package com.pzj.trade.withdraw.event;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pzj.trade.payment.entity.TakenOrderEntity;
import com.pzj.trade.withdraw.nv.NameValue;

/**
 * 凑钱.
 * @author YRJ
 *
 */
@Component(value = "raisingMoneyEvent")
class RaisingMoneyEvent {

    /**
     * 打借条.
     * @param takens
     * @param total
     * @return
     */
    public RaisingResult collect(List<TakenOrderEntity> takens, double total) {
        RaisingResult raising = new RaisingResult(total);

        for (TakenOrderEntity takenEntity : takens) {
            double usable = computeUsableMoney(takenEntity);
            if (usable <= 0) {
                continue;
            }
            //可提现金额大于剩余金额, 则扣除剩余金额的钱.
            raising.writeIOU(new NameValue<String, Double>(takenEntity.getOrder_id(), usable));
            //凑齐了, 不用再借钱了.
            if (raising.isRaised()) {
                break;
            }
        }
        return raising;
    }

    /**
     * 计算可用金额.
     * @param can_postal_money
     * @param postal_money
     * @return
     */
    private double computeUsableMoney(TakenOrderEntity takenEntity) {
        double possible = takenEntity.getCan_postal_money();
        double consumed = takenEntity.getPostal_money();
        return (possible - consumed);
    }
}
