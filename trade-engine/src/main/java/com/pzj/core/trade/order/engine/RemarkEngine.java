package com.pzj.core.trade.order.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.trade.order.entity.RemarkEntity;
import com.pzj.trade.order.vo.OrderRemarkVO;
import com.pzj.trade.order.write.OrderWriteMapper;

/**
 * 订单备注信息执行引擎.
 * @author YRJ
 *
 */
@Component(value = "remarkEngine")
public class RemarkEngine {

    @Autowired
    private OrderWriteMapper orderWriteMapper;

    @Transactional(value = "trade.transactionManager", propagation = Propagation.SUPPORTS, timeout = 2)
    public void createRemark(OrderRemarkVO remark) {
        RemarkEntity remarkEntity = generateRemarkEntity(remark);
        orderWriteMapper.insertOrderRemark(remarkEntity);
    }

    private RemarkEntity generateRemarkEntity(OrderRemarkVO remark) {
        RemarkEntity remarkEntity = new RemarkEntity();
        remarkEntity.setOrder_id(remark.getOrder_id());
        remarkEntity.setRemark(remark.getRemark());
        remarkEntity.setOperator_id(remark.getOperator_id());
        remarkEntity.setOperator_name(remark.getOperator_name());
        remarkEntity.setOperator_type(remark.getOperator_type());
        return remarkEntity;
    }

}
