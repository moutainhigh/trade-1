package com.pzj.trade.order.write;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.order.entity.MftourCodeEntity;

public interface MftourCodeWriteMapper {

    /**
     * 新增订单魔方码记录
     * @param order_id
     * @param agent_order_id
     */
    void insertMftourCode(@Param(value = "codeList") List<MftourCodeEntity> mftourCodeEntityList);

    /**
     * 更具订单Id,获取当前订单对应的魔方码信息
     * @param orderId
     * @return
     */
    List<MftourCodeEntity> getMftourCodeByOrderId(@Param(value = "orderId") String orderId);

    /**
     * 更具订单Id,获取当前订单对应的魔方码信息
     * @param orderId
     * @return
     */
    List<MftourCodeEntity> getMftourCodeByTransactionId(@Param(value = "transactionId") String transactionId);

}
