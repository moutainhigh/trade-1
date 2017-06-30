/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.create.engine.ContactBuyNumEngine;
import com.pzj.core.trade.create.engine.filter.IdcardNoFilter;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.model.CheckIdCardModel;
import com.pzj.trade.order.model.ContactlimitModel;
import com.pzj.trade.order.service.OrderValidateService;

/**
 * 
 * @author Administrator
 * @version $Id: OrderValidateServiceImpl.java, v 0.1 2017年3月29日 下午4:11:13 Administrator Exp $
 */
@Service(value = "orderValidateService")
public class OrderValidateServiceImpl implements OrderValidateService {
	private final static Logger logger = LoggerFactory.getLogger(OrderValidateServiceImpl.class);

	@Autowired
	private ContactBuyNumEngine contactBuyNumEngine;

	@Autowired
	private IdcardNoFilter idcardNoFilter;

	@Override
	public Result<Integer> getBuyCountByContactee(ContactlimitModel model, ServiceContext context) {
		logger.info("查询联系人购买产品组数量参数" + JSONConverter.toJson(model));
		if (Check.NuNObject(model) || Check.NuNObject(model.getSpuId(), model.getContactMobile())) {
			return new Result<Integer>(14001, "参数为空或不全,请填写产品ID和联系人手机号");
		}
		int hadBuyNum = contactBuyNumEngine.getBuyNumByContactee(model.getSpuId(), model.getStartTime(),
				model.getContactMobile());
		return new Result<Integer>(hadBuyNum);
	}

	@Override
	public Result<ArrayList<String>> checkIdCardBuyable(CheckIdCardModel model, ServiceContext context) {
		logger.info("判断身份证是否重复参数" + JSONConverter.toJson(model));
		Result<ArrayList<String>> result = new Result<ArrayList<String>>();
		if (Check.NuNObject(model) || Check.NuNObject(model.getProductId(), model.getSupplierId(), model.getIdCards())) {
			return new Result<ArrayList<String>>(14001, "参数为空或不全");
		}
		//先校验参数身份证号是不是重复
		ArrayList<String> distinctIdcards = new ArrayList<String>();
		for (String idcard : model.getIdCards()) {
			if (distinctIdcards.contains(idcard)) {
				return new Result<ArrayList<String>>(14002, "参数有重复的身份证号:" + idcard);
			} else {
				distinctIdcards.add(idcard);
			}
		}

		final List<String> idcards = idcardNoFilter.checkUsedIdcardNo(model.getProductId(), model.getSupplierId(),
				model.getIdCards(), model.getDate());
		logger.info("判断身份证是否重复参数结果:" + JSONConverter.toJson(idcards));
		if (!Check.NuNCollections(idcards)) {
			ArrayList<String> data = new ArrayList<String>();
			Set<String> set = new HashSet<String>();
			for (String idcard : idcards) {
				set.add(idcard);
			}
			data.addAll(set);
			result.setData(data);
			result.setErrorCode(14003);
			result.setErrorMsg("身份证存在重复:" + data.toString());
		}
		return result;
	}

}
