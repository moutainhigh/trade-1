/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.order.write;

import com.pzj.trade.order.entity.DeliveryDetailEntity;

/**
 * 
 * @author fanggang
 * @version $Id: DeliveryWriteMapper.java, v 0.1 2016年11月1日 下午3:52:25 fanggang Exp $
 */
public interface DeliveryWriteMapper {

    /**
     * 插入配送明细
     * 
     * @param deliveryDetailEntity
     */
    void insertDeliveryDetail(DeliveryDetailEntity deliveryDetailEntity);
    
    /**
     * 获取订单的配送信息
     * @param orderId
     * @return
     */
    DeliveryDetailEntity getDeliveryByOrderIdNolock(String orderId);
}
