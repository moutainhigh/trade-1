package com.pzj.core.trade.order.engine;

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

@Component(value = "orderExtendAttrEngine")
public class OrderExtendAttrEngine {

	private final static Logger logger = LoggerFactory.getLogger(OrderExtendAttrEngine.class);

	@Resource(name = "orderExtendAttrReadMapper")
	private OrderExtendAttrReadMapper orderExtendAttrReadMapper;

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

}
