package com.pzj.core.trade.clean.engine.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.clean.engine.converter.MerchCleanRelationConvert;
import com.pzj.core.trade.clean.engine.converter.MerchEntityToMerchCleanModelTool;
import com.pzj.core.trade.clean.engine.event.FinishCleanEvent;
import com.pzj.core.trade.clean.engine.model.MerchCleanAssistModel;
import com.pzj.core.trade.clean.engine.model.MerchCleanCreatorModel;
import com.pzj.core.trade.clean.engine.model.SkuConsumerRuleModel;
import com.pzj.core.trade.clean.engine.validator.MerchCleanValidator;
import com.pzj.core.trade.confirm.common.ConfirmVersionEnum;
import com.pzj.framework.context.Result;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchCleanRelationEntity;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.write.MerchCleanRelationWriteMapper;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;

/**
 * 正常核销生成清算信息操作
 * @author kangzl
 *
 */
@Component("consumerToCleanHandler")
public class ConsumerToCleanHandler {

	private final static Logger logger = LoggerFactory.getLogger(ConsumerToCleanHandler.class);

	@Autowired
	private MerchWriteMapper merchWriteMapper;
	@Autowired
	private OrderWriteMapper orderWriteMapper;
	@Autowired
	private MerchCleanValidator merchCleanValidator;
	@Autowired
	private MerchCleanConvert merchCleanConvert;
	@Autowired
	private MerchCleanRelationConvert merchCleanRelationConvert;
	@Autowired
	private MerchCleanRelationWriteMapper merchCleanRelationWriteMapper;

	@Autowired
	private FinishCleanEvent finishCleanEvent;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	public Result<Boolean> convertClean(String transactionId, long voucherId) {

		logger.info("call ConsumerToCleanHandler.convertClean params:{},{}", transactionId, voucherId);

		List<MerchCleanCreatorModel> merchModels = convertMerch(voucherId);
		if (Check.NuNCollections(merchModels)) {
			return new Result<Boolean>(10451, "商品信息为空");
		}
		OrderEntity saleOrder = orderWriteMapper.getOrderEntityNotLock(transactionId);
		//新的saas订单无需走后续流程
		if(saleOrder.getVersion()=="1"){
		    return new Result<Boolean>();
		}
		MerchCleanAssistModel assistModel = new MerchCleanAssistModel();
		assistModel.setIsOrderPaied(saleOrder.getOnline_pay());

		final Map<Long, SkuConsumerRuleModel> skuConsumerRuleCache = new HashMap<Long, SkuConsumerRuleModel>();
		List<MerchCleanRelationEntity> relations = new ArrayList<MerchCleanRelationEntity>();
		for (MerchCleanCreatorModel merch : merchModels) {
			//判断商品是否需要生成结算信息
			if (!merchCleanValidator.checkCreateCleanEntity(merch, assistModel)) {
				continue;
			}
			if (merch.getIsCleaned() == 1) {
				continue;
			}
			merchCleanConvert.addConsumerRuleToCach(merch.getProductId(), merch.getVourType(), skuConsumerRuleCache);
			MerchCleanRelationEntity relation = merchCleanRelationConvert.convert(transactionId, merch, skuConsumerRuleCache,
					assistModel, saleOrder);
			relations.add(relation);
		}
		List<MerchCleanRelationEntity> finishRelations = finishCleanEvent.disponseOrderFinish(transactionId, assistModel);
		if (!Check.NuNCollections(finishRelations)) {
			relations.addAll(finishRelations);
		}
		if (!Check.NuNCollections(relations)) {
			merchCleanRelationWriteMapper.insertMerchCleanRelation(relations);
		}
		return new Result<Boolean>();
	}

	/**
	 * 获取清算相关信息
	 * @param refundId
	 * @return
	 */
	private List<MerchCleanCreatorModel> convertMerch(long voucherId) {
		List<MerchCleanCreatorModel> models = new ArrayList<MerchCleanCreatorModel>();

		List<MerchEntity> merches = merchWriteMapper.getMerchByVoucherId(voucherId);
		for (MerchEntity merch : merches) {
			if (merch.getMerch_id().equals(merch.getRoot_merch_id())) {
				continue;
			}
			if (merch.getCheck_num() == 0) {
				continue;
			}
			MerchEntity rootMerch = merchWriteMapper.getMerchByMerchId(merch.getRoot_merch_id());
			MerchCleanCreatorModel model = MerchEntityToMerchCleanModelTool.getCleanModel(merch, rootMerch);
			models.add(model);
		}
		return models;
	}
}
