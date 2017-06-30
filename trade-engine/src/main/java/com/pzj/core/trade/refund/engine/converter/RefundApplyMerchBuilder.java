package com.pzj.core.trade.refund.engine.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.exception.OrderNotExistException;
import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.exception.OrderNotContainMerchException;
import com.pzj.core.trade.refund.engine.exception.RefundArgsException;
import com.pzj.core.trade.refund.engine.exception.RefundMerchNotFoundException;
import com.pzj.core.trade.refund.engine.exception.RepetitiveRefundMerchException;
import com.pzj.core.trade.refund.engine.model.RefundApplyMerchModel;
import com.pzj.core.trade.refund.entity.RefundMerchRequiredEntity;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.vo.RefundMerchandiseVO;
import com.pzj.trade.refund.model.RefundApplyReqModel;

/**
 * 申请退款商品模型构建器.
 * <p>用于将申请退款的商品构建为处理流程中的商品模型.</p>
 * @author kangzl
 *
 */
@Component(value = "refundApplyMerchBuilder")
public class RefundApplyMerchBuilder {

	private final static Logger logger = LoggerFactory.getLogger(RefundApplyMerchBuilder.class);

	@Autowired
	private MerchReadMapper merchReadMapper;

	public List<RefundApplyMerchModel> generateRefundApplyMerchModel(final RefundApplyReqModel reqModel, final OrderEntity order) {
		if (order == null) {
			throw new OrderNotExistException(RefundErrorCode.ORDER_NOT_FOUND_ERROR_CODE, "订单[" + reqModel.getOrderId() + "], 不存在.");
		}
		final List<RefundMerchRequiredEntity> merchs = merchReadMapper.getSellMerchesOfSellOrder(reqModel.getOrderId());
		if (Check.NuNCollections(merchs)) {
			throw new RefundMerchNotFoundException(10302, "订单[" + reqModel.getOrderId() + "], 未找到对应的商品信息.");
		}
		final List<RefundApplyMerchModel> merchModels = convert(reqModel, merchs);
		validateRefundable(merchModels, reqModel);
		return merchModels;
	}

	/**
	 * 根据申请退款的商品及该订单所对应商品做模型转换.
	 * @param refundMerches
	 * @param merchs
	 */
	private List<RefundApplyMerchModel> convert(final RefundApplyReqModel reqModel, final List<RefundMerchRequiredEntity> merchs) {
		final List<RefundApplyMerchModel> merchModels = new ArrayList<RefundApplyMerchModel>();
		final List<RefundMerchandiseVO> refundMerchs = reqModel.getRefundMerches();
		final Set<String> refundMerchIds = new HashSet<String>();
		for (final RefundMerchandiseVO refundMerch : refundMerchs) {
			boolean isFind = false;
			for (final RefundMerchRequiredEntity merch : merchs) {
				if (!(refundMerch.getMerchId().equalsIgnoreCase(merch.getMerchId()))) {
					continue;
				}
				if (refundMerchIds.contains(refundMerch.getMerchId())) {
					logger.warn("请求参数传递不合理, 对同一商品[{}]传递了多次.", refundMerch.getMerchId());
					throw new RepetitiveRefundMerchException(RefundErrorCode.REPETITIVE_REFUND_MERCH_ERROR_CODE, "重复的退款商品[" + refundMerch.getMerchId() + "]");
				}
				final int canRefundNum = reqModel.getRefundType() == RefundApplyTypeEnum.FORCE.getType().intValue() ? merch.getTotalNum()
						- merch.getRefundNum() : merch.getTotalNum() - merch.getCheckNum() - merch.getRefundNum();
				//判断申请退款的数量是否大于商品的可退数量
				if (refundMerch.getRefundNum() > canRefundNum) {
					throw new RefundArgsException(RefundErrorCode.REFUND_NUM_ERROR_CODE, "商品" + merch.getMerchName() + "的退款申请数量,大于该商品的可退数量");
				}
				final RefundApplyMerchModel merchModel = new RefundApplyMerchModel(merch.getOrderId(), merch.getMerchId());
				merchModel.setApplyNum(refundMerch.getRefundNum());
				merchModel.setProductId(merch.getProductId());
				merchModel.setMerchName(merch.getMerchName());
				merchModel.setMerchState(merch.getMerchState());
				merchModel.setPrice(merch.getPrice());
				merchModel.setTravelTime(merch.getStartTime());
				merchModel.setExpireTime(merch.getExpireTime());
				merchModel.setTotalNum(merch.getTotalNum());
				merchModel.setCheckedNum(merch.getCheckNum());
				merchModel.setRefundNum(merch.getRefundNum());
				merchModel.setRefundAmount(merch.getRefundAmount());
				merchModel.setIsRefunding(merch.getIsRefunding());
				merchModel.setRefundingNum(merch.getRefundingNum());
				merchModels.add(merchModel);
				refundMerchIds.add(merch.getMerchId());
				isFind = true;
			}
			//如果存在退款申请商品信息在，商品表中无法获取的情况
			if (!isFind) {
				logger.warn("请求参数传递不合理, 该商品:[{}]不属于这个销售单:[{}].", refundMerch.getMerchId(), reqModel.getOrderId());
				throw new OrderNotContainMerchException(RefundErrorCode.ORDER_NOT_CONTAIN_MERCH_ERROR_CODE, "订单[{" + reqModel.getOrderId() + "}]不含商品[{"
						+ refundMerch.getMerchId() + "}]");
			}
		}
		return merchModels;
	}

	/**
	 * 验证退款参数及订单购买数量判断是否可退.
	 * @param merchModels
	 */
	private void validateRefundable(final List<RefundApplyMerchModel> merchs, final RefundApplyReqModel reqModel) {
		boolean checkPrice = false;
		for (final RefundApplyMerchModel merch : merchs) {
			if (merch.getApplyNum() <= 0) {
				logger.warn("订单[" + merch.getOrderId() + "], 商品[" + merch.getMerchId() + "], 可退数量为0.");
				throw new RefundArgsException(RefundErrorCode.SELLORDER_MERCHES_IS_NONE_ERROR_CODE, "退款商品[" + merch.getMerchId() + "]可退款数量为0");
			}
			if (merch.getPrice() > 0) {
				checkPrice = true;
			}
		}
		//退款关联的商品的单价如果全部是0,则不可进行强制退款
		if (!checkPrice && reqModel.getRefundType() == RefundApplyTypeEnum.FORCE.getType().intValue()) {
			throw new RefundMerchNotFoundException(RefundErrorCode.CAN_NOT_REFUND_BEFORE_DATE_ERROR_CODE, "强制退款对应的商品不可以全部都是单价为0的商品");
		}
	}
}
