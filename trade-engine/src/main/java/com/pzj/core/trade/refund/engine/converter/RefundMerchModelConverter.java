package com.pzj.core.trade.refund.engine.converter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.MerchStateEnum;
import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.exception.MerchApplyNumException;
import com.pzj.core.trade.refund.engine.exception.OrderNotContainMerchException;
import com.pzj.core.trade.refund.engine.exception.RefundMerchNotFoundException;
import com.pzj.core.trade.refund.engine.exception.RepetitiveRefundMerchException;
import com.pzj.core.trade.refund.engine.model.RefundMerchModel;
import com.pzj.core.trade.refund.entity.RefundMerchRequiredEntity;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.vo.RefundMerchandiseVO;
import com.pzj.trade.refund.model.RefundApplyReqModel;

/**
 * 退款申请模型转换器.
 *
 * @author YRJ
 *
 */
@Component(value = "refundMerchConverter")
public class RefundMerchModelConverter implements
		ObjectConverter<List<RefundMerchRequiredEntity>, RefundApplyReqModel, RefundMerchModel[]> {

	private final static Logger logger = LoggerFactory.getLogger(RefundMerchModelConverter.class);

	@Override
	public RefundMerchModel[] convert(final List<RefundMerchRequiredEntity> sellMerches, final RefundApplyReqModel reqModel) {

		if (Check.NuNCollections(sellMerches)) {
			throw new RefundMerchNotFoundException(RefundErrorCode.SELLORDER_MERCHES_IS_NONE_ERROR_CODE, "当前退款申请中, 未找到销售商品信息.");
		}

		// 获取当前退款请求的退款商品集合
		final Set<RefundMerchModel> refundMerches = retrieveReplayMerchByMerchId(sellMerches, reqModel);

		double refundMoney = 0;
		for (RefundMerchModel model : refundMerches) {
			refundMoney += model.getPrice();
		}
		if (refundMoney == 0 && reqModel.getRefundType() == 1) {
			throw new RefundMerchNotFoundException(RefundErrorCode.CAN_NOT_REFUND_BEFORE_DATE_ERROR_CODE,
					"强制退款对应的商品不可以全部都是单价为0的商品");
		}
		return refundMerches.toArray(new RefundMerchModel[0]);

	}

	/**
	 * 从申请退款商品列表中检索对应的商品信息.
	 *
	 * @param sellMerches
	 *            销售商品集
	 * @param reqModel
	 *            申请参数
	 * @return
	 */
	private Set<RefundMerchModel> retrieveReplayMerchByMerchId(final List<RefundMerchRequiredEntity> sellMerches,
			final RefundApplyReqModel reqModel) {

		// 1. 若申请的商品列表为空, 则对可退的商品全部做退款.
		final List<RefundMerchandiseVO> refundMerches = reqModel.getRefundMerches();
		if (Check.NuNCollections(refundMerches)) {
			return generateAllRefundMerchModel(sellMerches, reqModel);
		}

		final Map<String, RefundMerchRequiredEntity> converMerch = merchConvertoMap(sellMerches);
		// 2. 对退款申请商品列表进行处理
		final Set<RefundMerchModel> merchModels = new HashSet<RefundMerchModel>();
		for (final RefundMerchandiseVO refundMerch : refundMerches) {

			Boolean isContain = false;

			if (converMerch.containsKey(refundMerch.getMerchId())) {
				final RefundMerchModel merchModel = generatePartRefundMerchModel(converMerch.get(refundMerch.getMerchId()),
						refundMerch.getRefundNum(), reqModel);

				isContain = true;

				final boolean maybe = merchModels.add(merchModel);
				if (!maybe) {
					logger.warn("请求参数传递不合理, 对同一商品[{}]传递了多次.", refundMerch.getMerchId());
					throw new RepetitiveRefundMerchException(RefundErrorCode.REPETITIVE_REFUND_MERCH_ERROR_CODE, "重复的退款商品["
							+ refundMerch.getMerchId() + "]");
				}
			}

			if (!isContain) {
				final String orderId = reqModel.getOrderId();
				logger.warn("请求参数传递不合理, 该商品:[{}]不属于这个销售单:[{}].", refundMerch.getMerchId(), orderId);
				throw new OrderNotContainMerchException(RefundErrorCode.ORDER_NOT_CONTAIN_MERCH_ERROR_CODE, "订单[{" + orderId
						+ "}]不含商品[{" + refundMerch.getMerchId() + "}]");
			}
		}
		return merchModels;

	}

	/**
	 * 销售商品转换
	 * @param sellMerches 销售商品集
	 * @return key,merchId;value,sellMerch.
	 */
	private Map<String, RefundMerchRequiredEntity> merchConvertoMap(final List<RefundMerchRequiredEntity> sellMerches) {
		final Map<String, RefundMerchRequiredEntity> conveterResult = new HashMap<String, RefundMerchRequiredEntity>();
		for (final RefundMerchRequiredEntity sellMerch : sellMerches) {
			conveterResult.put(sellMerch.getMerchId(), sellMerch);
		}
		return conveterResult;
	}

	/**
	 * 生成退款商品模型.
	 *
	 * @param sellMerch
	 *            销售商品
	 * @param refundNum
	 *            退款数量
	 * @param reqModel
	 *            申请参数
	 * @return 退款模型
	 */
	private RefundMerchModel generatePartRefundMerchModel(final RefundMerchRequiredEntity sellMerch, final int refundNum,
			final RefundApplyReqModel reqModel) {
		final RefundMerchModel merchModel = RefundMerchModel.newInstance();

		merchModel.setMerchId(sellMerch.getMerchId());
		merchModel.setRootMerchId(sellMerch.getRootMerchId());
		merchModel.setMerchName(sellMerch.getMerchName());
		merchModel.setProductId(sellMerch.getProductId());
		merchModel.setTotalNum(sellMerch.getTotalNum());
		merchModel.setCheckedNum(sellMerch.getCheckNum());
		merchModel.setRefundNum(sellMerch.getRefundNum());
		merchModel.setTotalAmount(sellMerch.getTotalNum() * sellMerch.getPrice());

		if (refundNum <= 0) {
			throw new MerchApplyNumException(RefundErrorCode.REFUND_NUM_ERROR_CODE, "退款申请数量错误[" + sellMerch.getMerchId() + "]");
		}

		merchModel.setApplyNum(refundNum);
		merchModel.setVoucherId(sellMerch.getVoucherId());
		merchModel.setPrice(sellMerch.getPrice());
		merchModel.setOrderId(reqModel.getOrderId());
		merchModel.setMerchState(sellMerch.getMerchState());
		merchModel.setIsRefunding(sellMerch.getIsRefunding());
		return merchModel;
	}

	/**
	 * 整单退转换
	 *
	 * @param sellMerches
	 * @param reqModel
	 * @return
	 */
	private Set<RefundMerchModel> generateAllRefundMerchModel(final List<RefundMerchRequiredEntity> sellMerches,
			final RefundApplyReqModel reqModel) {

		final Set<RefundMerchModel> refundMerches = new HashSet<RefundMerchModel>();
		for (final RefundMerchRequiredEntity sellMerch : sellMerches) {
			final int ableRefundTotalNum = ableRefundTotalNum(sellMerch, reqModel.getRefundType());
			if (ableRefundTotalNum == 0) {
				continue;
			}
			final RefundMerchModel merchModel = generatePartRefundMerchModel(sellMerch, ableRefundTotalNum, reqModel);
			refundMerches.add(merchModel);
		}
		return refundMerches;
	}

	/**
	 *
	 * @param sellMerch 销售商品
	 * @param isForce 退款类型
	 * @return 可退数量
	 */
	private int ableRefundTotalNum(final RefundMerchRequiredEntity sellMerch, final int isForce) {

		if (RefundApplyTypeEnum.FORCE.getType() == isForce) {
			return sellMerch.getTotalNum() - sellMerch.getRefundNum();
		}

		// 平台发起的已消费的普通退款
		if (sellMerch.getMerchState() == MerchStateEnum.CONSUMED.getState()) {
			return sellMerch.getCheckNum();
		}

		// 分销商发起的普通退款整单退款数量
		return sellMerch.getTotalNum() - sellMerch.getCheckNum() - sellMerch.getRefundNum();
	}
}