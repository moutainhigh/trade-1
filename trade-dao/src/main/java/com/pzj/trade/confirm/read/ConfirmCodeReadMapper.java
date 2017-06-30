package com.pzj.trade.confirm.read;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.confirm.entity.ConfirmCodeEntity;

public interface ConfirmCodeReadMapper {

    /**
     * 根据订单ID获取订单魔方码
     * @param order_id
     * @return
     */
    ConfirmCodeEntity getMftourCodeByOrderId(String order_id);

    /**
     * 根据交易ID获取订单魔方码
     * @param order_id
     * @return
     */
    ConfirmCodeEntity getMftourCodeByTransactionId(String transaction_id);

    /**
     * 根据魔方码ID获取订单魔方码.
     * @param codeId
     * @return
     */
    ConfirmCodeEntity getMftourCodeById(@Param(value = "code_id") long codeId);
}
