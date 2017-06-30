package com.pzj.trade.payment.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.order.entity.OrderListEntity;
import com.pzj.trade.payment.entity.CashPostalEntity;
import com.pzj.trade.withdraw.model.WithdrawReqParameter;

public interface WithdrawReadMapper {

    /**
     * 通过查询条件获取提现信息
     * @param param
     * @return
     */
    List<CashPostalEntity> queryWithdrawByCondition(@Param("param") WithdrawReqParameter param,@Param("pageIndex")int pageIndex,@Param("pageSize")int pageSize);
    /**
     * 通过查询条件获取提现信息数量
     * @param param
     * @return
     */
    int queryWithdrawNumByCondition(@Param("param") WithdrawReqParameter param);
	
    /**
     * 查询提现明细
     * @param param
     * @return
     */
    List<OrderListEntity> queryWithdrawDetail(@Param("param") WithdrawReqParameter param,@Param("pageIndex")int pageIndex,@Param("pageSize")int pageSize);
    /**
     * 通过查询条件获取提现信息数量
     * @param param
     * @return
     */
    int queryWithdrawDetailNum(@Param("param") WithdrawReqParameter param);
}
