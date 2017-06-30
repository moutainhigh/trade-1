package com.pzj.core.trade.refund.engine.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.product.common.model.response.SkuProductOutModel;
import com.pzj.core.product.common.service.ISpuProductService;
import com.pzj.core.trade.refund.engine.common.RefundFlowAuditStateEnum;
import com.pzj.core.trade.refund.engine.exception.RefundException;
import com.pzj.core.trade.refund.engine.model.RefundOperMQLogModel;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.write.RefundApplyWriteMapper;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.merch.write.MerchRefundFlowWriteMapper;
import com.pzj.trade.mq.MQTagsEnum;
import com.pzj.trade.mq.MQUtil;
import com.pzj.trade.order.read.MerchReadMapper;

@Component
public class RefundOperLogMQHandler {
	private static final Logger logger = LoggerFactory.getLogger(RefundOperLogMQHandler.class);

	@Autowired
	private MerchReadMapper merchReadMapper;
	@Autowired
	private RefundApplyWriteMapper refundApplyWriteMapper;
	@Autowired
	private MerchRefundFlowWriteMapper merchWriteMapperFlowWriteMapper;

	@Autowired
	private ISpuProductService iSpuProductService;
	@Resource(name = "mqUtil")
	private MQUtil mqUtil;

	public void sendOperLog(RefundOperMQLogModel model) {
		RefundApplyEntity refundApply = refundApplyWriteMapper.getRefundApplyEntityByRefundId(model.getRefundId());
		final Map<String, Object> mapContent = new HashMap<String, Object>();
		mapContent.put("orderId", model.getRootOrderId());
		mapContent.put("event", model.getEvent().getEvent());
		mapContent.put("isForce", refundApply.getIsForce());
		mapContent.put("operationId", model.getOperationId());
		List<RefundFlowEntity> refundFlows = merchWriteMapperFlowWriteMapper.getOrderMerchRefund(model.getRootOrderId(), model.getRefundId());
		final Map<String, Object> merchMap = new HashMap<String, Object>();
		for (final RefundFlowEntity refundFlow : refundFlows) {
			MerchEntity merch = merchReadMapper.getMerchByMerchId(refundFlow.getMerch_id());
			Result<SkuProductOutModel> skuResult = iSpuProductService.getSkuById(merch.getProduct_id());
			if (!skuResult.isOk() && Check.NuNObject(skuResult.getData())) {
				throw new RefundException(10324, "退款操作对应的产品规格信息异常");
			}
			SkuProductOutModel skuModel = skuResult.getData();
			merchMap.put(skuModel.getProductName() + " " + skuModel.getSkuName(), refundFlow.getRefund_num());
		}
		mapContent.put("refund_merch", merchMap);
		if (refundApply.getRefundAuditState() == RefundFlowAuditStateEnum.REFUSED.getState()) {
			mapContent.put("reason", model.getOperMsg());
		}

		String msg = JSONConverter.toJson(mapContent);
		logger.info("refund log send msg ,orderId:{},tag:{},content:{}.", model.getRootOrderId(), model.getEvent().getEvent(), msg);
		mqUtil.send(MQTagsEnum.refundLog.getValue(), msg);
	}
}
