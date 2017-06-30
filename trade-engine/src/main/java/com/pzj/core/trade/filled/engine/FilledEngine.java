/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.filled.engine;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.trade.order.entity.OrderExtendAttrEntity;
import com.pzj.trade.order.model.FilledModel;
import com.pzj.trade.order.model.OrderFillModel;
import com.pzj.trade.order.write.OrderExtendAttrWriteMapper;

/**
 * 
 * @author Administrator
 * @version $Id: FilledEngine.java, v 0.1 2017年2月20日 上午10:58:01 Administrator Exp $
 */
@Component
public class FilledEngine {
	@Autowired
	private OrderExtendAttrWriteMapper orderExtendAttrWriteMapper;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 3)
	public void updateFilled(OrderFillModel order) {
		List<OrderExtendAttrEntity> attrs = convertFilledToExtendAttr(order);
		orderExtendAttrWriteMapper.updateByTransactionIdAndKey(attrs);

	}

	/**
	 * 
	 * @param order
	 * @return
	 */
	private List<OrderExtendAttrEntity> convertFilledToExtendAttr(OrderFillModel order) {
		List<OrderExtendAttrEntity> attrs = new ArrayList<OrderExtendAttrEntity>();
		List<FilledModel> filleds = order.getFilleds();
		for (FilledModel filled : filleds) {
			OrderExtendAttrEntity attr = new OrderExtendAttrEntity();
			attr.setTransaction_id(order.getOrder_id());
			attr.setAttr_group(filled.getGroup());
			attr.setAttr_key(filled.getAttr_key());
			attr.setAttr_value(filled.getAttr_value());
			attrs.add(attr);
		}
		return attrs;
	}
}
