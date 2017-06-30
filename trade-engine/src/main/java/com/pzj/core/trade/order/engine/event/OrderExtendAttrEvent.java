package com.pzj.core.trade.order.engine.event;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.order.entity.OrderExtendAttrEntity;
import com.pzj.trade.order.model.FilledModel;
import com.pzj.trade.order.read.OrderExtendAttrReadMapper;

/**
 * 订单查询天单项数据拼装.
 * @author GLG
 *
 */
@Component(value = "orderExtendAttrEvent")
public class OrderExtendAttrEvent {

	@Resource(name = "orderExtendAttrReadMapper")
	private OrderExtendAttrReadMapper orderExtendAttrReadMapper;
	private final static Logger logger = LoggerFactory.getLogger(OrderExtendAttrEvent.class);

	public List<FilledModel> getOrderExtendAttr(String transactionId) {
		OrderExtendAttrEntity extendAttr = new OrderExtendAttrEntity();
		extendAttr.setTransaction_id(transactionId);
		return queryOrderExtendAttrList(extendAttr);

	}

	/**
	 * 根据交易ID查询填单项.
	 * @param transaction_id
	 * @return 填单项.
	 */
	public List<FilledModel> queryOrderExtendAttrList(OrderExtendAttrEntity extendAttr) {
		logger.info("查询填单项:extendAttr:{}", JSONConverter.toJson(extendAttr));
		List<FilledModel> filledModelList = new ArrayList<FilledModel>();
		List<OrderExtendAttrEntity> attrList = orderExtendAttrReadMapper.queryOrderExtendAttrListByParam(extendAttr);
		for (OrderExtendAttrEntity item : attrList) {
			FilledModel filledModel = new FilledModel();
			filledModel.setGroup(item.getAttr_group());
			filledModel.setAttr_key(item.getAttr_key());
			filledModel.setAttr_value(item.getAttr_value());
			filledModelList.add(filledModel);
		}
		return filledModelList;
	}

	/**获取客源地信息*/
	public String getTouristSourceEvent(String transactionId) {
		OrderExtendAttrEntity extendAttr = new OrderExtendAttrEntity();
		extendAttr.setTransaction_id(transactionId);
		String province = "";
		String city = "";
		String country = "";
		List<OrderExtendAttrEntity> attrList = orderExtendAttrReadMapper.queryOrderExtendAttrListByParam(extendAttr);
		for (OrderExtendAttrEntity orderExtendAttrEntity : attrList) {
			if ("Order".equals(orderExtendAttrEntity.getAttr_group())) {
				if ("TouristSourceProvince".equals(orderExtendAttrEntity.getAttr_key())) {
					province = orderExtendAttrEntity.getAttr_value();
				} else if ("TouristSourceCity".equals(orderExtendAttrEntity.getAttr_key())) {
					city = orderExtendAttrEntity.getAttr_value();
				} else if ("TouristSourceCountry".equals(orderExtendAttrEntity.getAttr_key())) {
					country = orderExtendAttrEntity.getAttr_value();
				}
			}
		}
		return new StringBuilder().append(province).append(city).append(country).toString();
	}
}
