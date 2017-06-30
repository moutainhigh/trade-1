package com.pzj.core.trade.clean.engine.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.clean.engine.converter.CleaningStateEnum;
import com.pzj.core.trade.clean.engine.model.MerchCleanAssistModel;
import com.pzj.core.trade.clean.engine.model.MerchCleanCreatorModel;
import com.pzj.core.trade.context.utils.MoneyUtils;
import com.pzj.core.trade.refund.engine.common.RefundingEnum;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchCleanRelationEntity;
import com.pzj.trade.merch.write.MerchCleanRelationWriteMapper;
import com.pzj.trade.order.common.MerchStateEnum;

@Component("merchCleanValidator")
public class MerchCleanValidator {

	@Autowired
	private MerchCleanRelationWriteMapper merchCleanRelationWriteMapper;

	public boolean checkCreateCleanEntity(MerchCleanCreatorModel merch, MerchCleanAssistModel assistModel) {
		//如果执行生成清算信息的是强制退款操作
		if (assistModel.getIsForceRefund() == 1) {
			//判断商品是否存在已清算的清算记录,如果存在,则必然生成结算单
			MerchCleanRelationEntity relation = merchCleanRelationWriteMapper.queryCleanRelationByOrderIdAndMerchId(merch.getOrderId(), merch.getMerchId(),
					CleaningStateEnum.CLEANED.getState());
			if (!Check.NuNObject(relation)) {
				return true;
			}
		}
		//如果商品对应的是分销商向魔方购买订单中的商品，不生成对应的清算信息
		if (merch.getRootMerchId().equals(merch.getMerchId())) {
			return false;
		}
		//处于退款中的商品不生成清算信息
		if (merch.getIsRefunding() == RefundingEnum.REFUNDING.getValue()) {
			return false;
		}
		//商品的状态没有完全完结,不需生成结算信息
		if (merch.getCheckNum() + merch.getRefundNum() != merch.getTotalNum()) {
			return false;
		}
		//判断商品在当前状态下是否可以生成对应的清算信息
		if (!MerchStateEnum.getMerchState(merch.getMerchState()).isCanCleaning()) {
			return false;
		}
		//如果当前不与供应商结算,并且对应是销售结算的商品是全额给分销商退款，那么当前商品暂时不生成采购结算信息
		if (MoneyUtils.getMoenyNumber(merch.getRootPrice() * merch.getRootTotalNum()) == MoneyUtils.getMoenyNumber(merch.getRootRefundAmount())
				&& merch.getCheckNum() == 0) {
			return false;
		}
		return true;
	}
}
