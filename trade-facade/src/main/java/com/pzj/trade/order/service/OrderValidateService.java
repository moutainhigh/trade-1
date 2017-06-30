/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.service;

import java.util.ArrayList;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.order.model.CheckIdCardModel;
import com.pzj.trade.order.model.ContactlimitModel;

/**
 * 订单下单前校验接口，在限购方面提供数据给调用方，配合校验
 * @author Administrator
 * @version $Id: OrderValidateService.java, v 0.1 2017年3月29日 下午3:47:52 Administrator Exp $
 */
public interface OrderValidateService {

	/**
	 * @api {dubbo} com.pzj.trade.order.service.OrderValidateService#getBuyCountByContactee 查询联系人购买产品数量
	 * @apiGroup 交易
	 * @apiName 联系人购买产品数量
	 * @apiDescription 一个联系人在一个时间之后购买的某个产品组的所有数量（只包括已支付、已完成订单）
	 * @apiVersion 1.1.0
	 *
	 * @apiUse ContactlimitModel
	 * @apiParam {com.pzj.trade.order.model.ContactlimitModel} model 
	 * @apiParamExample [{ContactlimitModel}] [ContactlimitModel]
	*  {
		"productId": 1234567890789,
		"startTime": "Mar 29, 2017 4:52:53 PM",
		"contactMobile": "18900000000"
	    }
	 * @apiParam {com.pzj.trade.context.ServiceContext} context 环境信息
	 *
	 * @apiSuccess {com.pzj.framework.context.Result} Result 返回对象
	 * @apiUse Result_Success
	 *
	 * @apiSuccessExample [{success}] [返回成功示例]
	*  {
	      "errorCode": 10000,
	      "errorMsg": "ok"
	    }
	 * @apiSuccessExample [{fail}] [返回失败示例]
	*  {
		"errorCode": 14001,
		"errorMsg": "参数为空或不全,请填写产品ID和联系人手机号"
		}
	 */
	public Result<Integer> getBuyCountByContactee(ContactlimitModel model, ServiceContext context);

	/**
	 * @api {dubbo} com.pzj.trade.order.service.OrderValidateService#checkIdCardBuyable 身份证判重
	 * @apiGroup 交易
	 * @apiName 身份证判重
	 * @apiDescription 根据产品ID、游玩时间判断身份证是否重复，返回重复的身份证号
	 * @apiVersion 1.1.0
	 *
	 * @apiUse CheckIdCardModel
	 * @apiParam {com.pzj.trade.order.model.CheckIdCardModel} model 
	 * @apiParamExample [{CheckIdCardModel}] [CheckIdCardModel]
	*  {
		"productId": 1234567890789,
		"startTime": "Mar 29, 2017 4:52:53 PM",
		"contactMobile": "18900000000"
	    }
	 * @apiParam {com.pzj.trade.context.ServiceContext} context 环境信息
	 *
	 * @apiSuccess {com.pzj.framework.context.Result} Result 返回对象
	 * @apiUse Result_Success
	 *
	 * @apiSuccessExample [{success}] [返回成功示例]
	*  {
		  "errorCode": 10000,
		  "errorMsg": "ok"
		}
	 * @apiSuccessExample [{fail}] [返回失败示例]
	*  {
		"errorCode": 14001,
		"errorMsg": "参数为空或不全"
		}
	*	@apiSuccessExample [{fail}] [返回失败示例]
	*  {
		"errorCode": 14002,
		"errorMsg": "参数有重复的身份证号:370881200012120000"
		}
		@apiSuccessExample [{fail}] [返回失败示例]
	*  {
		"errorCode": 14003,
			"errorMsg": "身份证存在重复",
			"data": [
					"370881200012120000"
					]
		}
	 */
	Result<ArrayList<String>> checkIdCardBuyable(CheckIdCardModel model, ServiceContext context);
}
