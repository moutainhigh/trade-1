package com.pzj.core.trade.order.engine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.trade.order.entity.RemarkEntity;
import com.pzj.trade.order.model.OrderRemarkModel;
import com.pzj.trade.order.read.RemarkReadMapper;
import com.pzj.trade.order.write.OrderWriteMapper;

/**
 * 订单备注修改执行引擎.
 * @author YRJ
 *
 */
@Component(value = "orderRemarkEngine")
public class OrderRemarkEngine {

	@Autowired
	private RemarkReadMapper remarkReadMapper;

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.SUPPORTS, timeout = 2)
	public void doHandler(OrderRemarkModel reqModel) {
		List<RemarkEntity> remarks = remarkReadMapper.getRemarkByOrderId(reqModel.getOrder_id());
		if (remarks != null) {
			for (RemarkEntity remark : remarks) {
				if (remark.getOperator_type() == 1) {
					remark.setRemark(reqModel.getRemark());
					orderWriteMapper.updateOrderRemark(remark);
					return;
				}
			}
		}

		RemarkEntity remark = generateOrderRemarkEntity(reqModel);
		orderWriteMapper.insertOrderRemark(remark);
	}

	/**
	 * 
	 * @param reqModel
	 * @return
	 */
	private RemarkEntity generateOrderRemarkEntity(OrderRemarkModel reqModel) {
		RemarkEntity remark = new RemarkEntity();
		remark.setOrder_id(reqModel.getOrder_id());
		remark.setRemark(reqModel.getRemark());
		remark.setOperator_type(1);
		remark.setOperator_id(reqModel.getOperator());
		remark.setOperator_name(String.valueOf(reqModel.getOperator()));
		return remark;
	}

}
