/**
 * 
 */
package com.pzj.core.trade.refund.engine.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.product.common.global.ProductRuleGroupEnum;
import com.pzj.core.product.common.model.response.ProductRuleOutModel;
import com.pzj.core.product.common.service.IProductRuleService;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.exception.QueryRefundRuleException;
import com.pzj.framework.context.Result;
import com.pzj.framework.exception.ServiceException;
import com.pzj.framework.toolkit.Check;

/**
 * 调用产品服务获取所有退款规则
 *
 * @author DongChunfu
 * @date 2016年12月3日
 */
@Component(value = "refundRuleLimitHandler")
public class RefundRuleLimitHandler {

	private static final Logger logger = LoggerFactory.getLogger(RefundRuleLimitHandler.class);

	//	@Resource(name = "skuRefundRuleConverter")
	//	private ObjectConverter<ProductRuleOutModel, Void, RefundRuleLimit> skuRefundRuleConverter;

	@Autowired
	private IProductRuleService productRuleService;

	//	private RefundRuleLimit doRuleLimit(final long prodId) {
	//		final ProductRuleOutModel productRule = queryRefundRuleLimit(prodId);
	//		return skuRefundRuleConverter.convert(productRule, null);
	//	}

	/**
	 * 根据产品ID获取退款规则模型列表.
	 * 
	 * @param prodId
	 * @return
	 */
	private ProductRuleOutModel queryRefundRuleLimit(final long prodId) {

		if (logger.isInfoEnabled()) {
			logger.info("调用产品服务,查询产品的退款规则, reqParm:{}.", prodId);
		}
		Result<ProductRuleOutModel> queryResult = null;

		try {
			final List<ProductRuleGroupEnum> groups = new ArrayList<>();
			groups.add(ProductRuleGroupEnum.PRODUCT_RULE_REFUND);// 退款规则
			groups.add(ProductRuleGroupEnum.PRODUCT_RULE_REFUND_REVIEW);// 运营审核规则
			queryResult = productRuleService.getBySkuId(prodId, groups);
		} catch (final Throwable t) {

			logger.error("查询退款规则失败, reqModel:" + prodId, t);

			if (t instanceof ServiceException) {
				throw (ServiceException) t;
			}
			throw new QueryRefundRuleException(RefundErrorCode.QUERY_REFUND_RULE_ERROR_CODE, t.getMessage());
		}

		if (!queryResult.isOk()) {
			logger.error("查询退款规则失败,errorCode:{},errorMsg:{}", queryResult.getErrorCode(), queryResult.getErrorMsg());
			throw new QueryRefundRuleException(RefundErrorCode.QUERY_REFUND_RULE_ERROR_CODE, queryResult.getErrorMsg());
		}

		final ProductRuleOutModel productRuleOutModel = queryResult.getData();

		if (Check.NuNObject(productRuleOutModel)) {
			logger.error("查询退款规则为空,prodId:{}.", prodId);
			throw new QueryRefundRuleException(RefundErrorCode.REFUND_RULE_NOT_FOUND_ERROR_CODE, "退款规则为空，不允许退款");
		}

		final List<ProductRuleOutModel> nodes = productRuleOutModel.getNodes();
		if (Check.NuNCollections(nodes)) {
			throw new QueryRefundRuleException(RefundErrorCode.REFUND_RULE_NOT_FOUND_ERROR_CODE, "产品退款规则子节点为空");
		}
		return productRuleOutModel;

		// return buildRefundRule();

	}

	/**
	 * 构建假退款规则
	 */
	// private ProductRuleOutModel buildRefundRule() {
	// final ProductRuleOutModel model = new ProductRuleOutModel();
	//
	// final List<ProductRuleOutModel> nodes =
	// Lists.newArrayListWithCapacity(6);
	//
	// final ProductRuleOutModel refundDateType = new ProductRuleOutModel();
	// refundDateType.setItemRule(RefundRuleEnum.refundDateType);
	// refundDateType.setValue(1);
	// nodes.add(refundDateType);
	//
	// final ProductRuleOutModel isNeedPrerefund = new ProductRuleOutModel();
	// isNeedPrerefund.setItemRule(RefundRuleEnum.isNeedPrerefund);
	// isNeedPrerefund.setValue(1);
	// nodes.add(isNeedPrerefund);
	//
	// final ProductRuleOutModel prerefundDays = new ProductRuleOutModel();
	// prerefundDays.setItemRule(RefundRuleEnum.prerefundDays);
	// prerefundDays.setValue(1);
	// nodes.add(prerefundDays);
	//
	// final ProductRuleOutModel prerefundHour = new ProductRuleOutModel();
	// prerefundHour.setItemRule(RefundRuleEnum.prerefundHour);
	// prerefundHour.setValue(1);
	// nodes.add(prerefundHour);
	//
	// final ProductRuleOutModel prerefundMinute = new ProductRuleOutModel();
	// prerefundMinute.setItemRule(RefundRuleEnum.prerefundMinute);
	// prerefundMinute.setValue(1);
	// nodes.add(prerefundMinute);
	//
	// final ProductRuleOutModel prerefundQuantityType = new
	// ProductRuleOutModel();
	// prerefundQuantityType.setItemRule(RefundRuleEnum.prerefundQuantityType);
	// prerefundQuantityType.setValue(1);
	// nodes.add(prerefundQuantityType);
	//
	// final ProductRuleOutModel prerefundSupplierFeetype = new
	// ProductRuleOutModel();
	// prerefundSupplierFeetype.setItemRule(RefundRuleEnum.prerefundSupplierFeetype);
	// prerefundSupplierFeetype.setValue(1);
	// nodes.add(prerefundSupplierFeetype);
	//
	// final ProductRuleOutModel prerefundSupplierFeevalue = new
	// ProductRuleOutModel();
	// prerefundSupplierFeevalue.setItemRule(RefundRuleEnum.prerefundSupplierFeevalue);
	// prerefundSupplierFeevalue.setValue(1);
	// nodes.add(prerefundSupplierFeevalue);
	//
	// final ProductRuleOutModel prerefundDistributorFeetype = new
	// ProductRuleOutModel();
	// prerefundDistributorFeetype.setItemRule(RefundRuleEnum.prerefundDistributorFeetype);
	// prerefundDistributorFeetype.setValue(1);
	// nodes.add(prerefundDistributorFeetype);
	//
	// final ProductRuleOutModel prerefundDistributorFeevalue = new
	// ProductRuleOutModel();
	// prerefundDistributorFeevalue.setItemRule(RefundRuleEnum.prerefundDistributorFeevalue);
	// prerefundDistributorFeevalue.setValue(1);
	// nodes.add(prerefundDistributorFeevalue);
	//
	// final ProductRuleOutModel isNeedProrefund = new ProductRuleOutModel();
	// isNeedProrefund.setItemRule(RefundRuleEnum.isNeedProrefund);
	// isNeedProrefund.setValue(1);
	// nodes.add(isNeedProrefund);
	//
	// final ProductRuleOutModel prorefundDays = new ProductRuleOutModel();
	// prorefundDays.setItemRule(RefundRuleEnum.prorefundDays);
	// prorefundDays.setValue(1);
	// nodes.add(prorefundDays);
	//
	// final ProductRuleOutModel prorefundHour = new ProductRuleOutModel();
	// prorefundHour.setItemRule(RefundRuleEnum.prorefundHour);
	// prorefundHour.setValue(1);
	// nodes.add(prorefundHour);
	//
	// final ProductRuleOutModel prorefundMinute = new ProductRuleOutModel();
	// prorefundMinute.setItemRule(RefundRuleEnum.prorefundMinute);
	// prorefundMinute.setValue(1);
	// nodes.add(prorefundMinute);
	//
	// final ProductRuleOutModel prorefundQuantityType = new
	// ProductRuleOutModel();
	// prorefundQuantityType.setItemRule(RefundRuleEnum.prorefundQuantityType);
	// prorefundQuantityType.setValue(1);
	// nodes.add(prorefundQuantityType);
	//
	// final ProductRuleOutModel prorefundSupplierFeetype = new
	// ProductRuleOutModel();
	// prorefundSupplierFeetype.setItemRule(RefundRuleEnum.prorefundSupplierFeetype);
	// prorefundSupplierFeetype.setValue(1);
	// nodes.add(prorefundSupplierFeetype);
	//
	// final ProductRuleOutModel prorefundSupplierFeevalue = new
	// ProductRuleOutModel();
	// prorefundSupplierFeevalue.setItemRule(RefundRuleEnum.prorefundSupplierFeevalue);
	// prorefundSupplierFeevalue.setValue(1);
	// nodes.add(prorefundSupplierFeevalue);
	//
	// final ProductRuleOutModel prorefundDistributorFeetype = new
	// ProductRuleOutModel();
	// prorefundDistributorFeetype.setItemRule(RefundRuleEnum.prorefundDistributorFeetype);
	// prorefundDistributorFeetype.setValue(1);
	// nodes.add(prorefundDistributorFeetype);
	//
	// final ProductRuleOutModel prorefundDistributorFeevalue = new
	// ProductRuleOutModel();
	// prorefundDistributorFeevalue.setItemRule(RefundRuleEnum.prorefundDistributorFeevalue);
	// prorefundDistributorFeevalue.setValue(1);
	// nodes.add(prorefundDistributorFeevalue);
	//
	// final ProductRuleOutModel refundReview = new ProductRuleOutModel();
	// refundReview.setItemRule(RefundReviewTypeEnum.refundReviewType);
	// refundReview.setValue(1);
	// nodes.add(refundReview);
	//
	// model.getNodes().addAll(nodes);
	//
	// return model;
	//
	// }

}
