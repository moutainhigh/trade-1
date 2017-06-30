package com.pzj.trade.withdraw.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.framework.toolkit.Check;
import com.pzj.trade.payment.entity.TakenOrderEntity;
import com.pzj.trade.payment.write.CashPostalWriteMapper;
import com.pzj.trade.withdraw.common.TakenStateEnum;
import com.pzj.trade.withdraw.nv.NameValue;

/**
 * 订单可提现事件.
 * @author YRJ
 *
 */
@Component(value = "possibleWithdrawEvent")
public class PossibleWithdrawEvent {

    @Resource(name = "cashPostalWriteMapper")
    private CashPostalWriteMapper cashPostalWriteMapper;

    @Resource(name = "raisingMoneyEvent")
    private RaisingMoneyEvent     raisingMoneyEvent;

    public Map<String, Double> getPossibleWithdrawRecord(long accountId, double cashPostalMoney) {

        double remain = cashPostalMoney;//剩余金额.
        int pageSize = 100;
        long takenId = 0;
        boolean hasNext = true;

        Map<String, Double> cachePostalMoney = new HashMap<String, Double>();
        do {
            //TODO pagesize 并没有使用，每次获取的都为一个实体，而非集合
            List<TakenOrderEntity> takens = cashPostalWriteMapper.getTakenOrderListForAccount(accountId, TakenStateEnum.canTaken.getKey(), takenId, pageSize);
            if (Check.NuNCollections(takens)) {
                break;
            }
            takenId = takens.get(takens.size() - 1).getTaken_id();

            RaisingResult raising = raisingMoneyEvent.collect(takens, remain);
            hasNext = raising.isRaised();
            remain = raising.getMargin();
            //将借的钱上缴.
            turned(cachePostalMoney, raising.getIous());
        } while (!hasNext);

        return cachePostalMoney;
    }

    /**
     * 将借的钱上缴.
     * @param cachePostalMoney
     * @param ious
     */
    private void turned(Map<String, Double> cachePostalMoney, List<NameValue<String, Double>> ious) {
        for (NameValue<String, Double> iou : ious) {
            cachePostalMoney.put(iou.getName(), iou.getValue());
        }
    }
}
