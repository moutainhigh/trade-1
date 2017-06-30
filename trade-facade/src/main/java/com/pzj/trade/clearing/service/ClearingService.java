package com.pzj.trade.clearing.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.clearing.model.CleaningModel;

/**
 * 清算服务接口.
 * @author YRJ
 *
 */
public interface ClearingService {

	/**
	 * @api {dubbo} com.pzj.trade.clearing.service.ClearingService#clean(CleaningModel,ServiceContext) 商品清算
	 * @apiGroup 清算
	 * @apiName 商品清算
	 * @apiDescription <p>商品清算</p>
	 * @apiVersion 1.1.0
	 *
	 * @apiParam (CleaningModel) {String} orderId 订单ID
	 * @apiParam (CleaningModel) {String} merchId 商品ID
	 *
	 */
	@Deprecated
	Result<Boolean> clean(CleaningModel cleaningModel, ServiceContext serviceContext);
}
