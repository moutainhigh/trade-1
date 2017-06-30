package com.pzj.core.trade.order.engine;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.order.entity.DeliveryDetailEntity;
import com.pzj.trade.order.model.DeliveryDetailModel;
import com.pzj.trade.order.read.DeliveryReadMapper;

@Component(value = "deliveryEngine")
public class DeliveryEngine {

    private final static Logger logger = LoggerFactory.getLogger(DeliveryEngine.class);

    @Resource(name = "deliveryReadMapper")
    private DeliveryReadMapper  deliveryReadMapper;

    /**
     * 根据采购订单ID列表查询配送信息.
     * @param orderIDs 采购订单ID列表
     * @return 配送信息.
     */
    public List<DeliveryDetailModel> queryOrderDelivery(List<String> orderIDs) {
        logger.info("查询快递配送信息:入参orderIDs:{}", JSONConverter.toJson(orderIDs));
        List<DeliveryDetailModel> deliveryDetailModels = new ArrayList<>();
        List<DeliveryDetailEntity> DeliveryDetailEntities = deliveryReadMapper.getDeliveryByOrderIds(orderIDs);
        for (DeliveryDetailEntity entity : DeliveryDetailEntities) {
            DeliveryDetailModel model = new DeliveryDetailModel();
            deliveryDetailModels.add(model);
            BeanUtils.copyProperties(entity, model);
        }
        return deliveryDetailModels;
    }

}
