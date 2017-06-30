package com.pzj.trade.mq;

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
import com.pzj.core.trade.refund.engine.common.RefundUserTypeEnum;
import com.pzj.core.trade.refund.engine.exception.RefundException;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.order.read.MerchReadMapper;

/**
 * 退款消息转换器
 *
 * @author DongChunfu
 * @date 2016年12月26日
 */
@Component(value = "refundMQMSgConverter")
public class RefundMQMSgConverter {

	private static final Logger logger = LoggerFactory.getLogger(RefundMQMSgConverter.class);

	@Autowired
	private MerchReadMapper merchReadMapper;
	@Autowired
	private ISpuProductService iSpuProductService;
	@Resource(name = "mqUtil")
	private MQUtil mqUtil;

	/**
	 * 退款发送消息
	 *
	 * @param refundFlows 退款流水
	 * @param isPass 空为申请，否则是否成功
	 * @param unPassMsg 失败原因
	 */
	public void refundLogMsg(List<RefundFlowEntity> refundFlows, Long operationId, RefundApplyEntity refundApply, final Boolean isPass, final String event,
			final String unPassMsg) {

		refundFlows = RefundFlowEntity.filterFlows(refundFlows, RefundUserTypeEnum.resellerRefund.getKey());

		final String msg = buildRefundLogContent(refundFlows, operationId, refundApply, isPass, event, unPassMsg);

		logger.info("refund log send msg ,orderId:{},tag:{},content:{}.", refundFlows.get(0).getOrder_id(), event, msg);
		mqUtil.send(MQTagsEnum.refundLog.getValue(), msg);
	}

	private String buildRefundLogContent(final List<RefundFlowEntity> refundFlows, Long operationId, RefundApplyEntity refundApply, final Boolean isPass,
			final String event, final String unPassMsg) {

		final Map<String, Object> mapContent = new HashMap<String, Object>();
		mapContent.put("orderId", refundFlows.get(0).getOrder_id());
		mapContent.put("event", event);
		mapContent.put("isForce", refundApply == null ? 0 : refundApply.getIsForce());
		mapContent.put("operationId", operationId);
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

		if (null != isPass && !isPass) {
			mapContent.put("reason", unPassMsg);
		}

		return JSONConverter.toJson(mapContent);
	}

}
