/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.create.engine.filter;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.read.TouristReadMapper;

/**
 * 身份证判重
 * @author Administrator
 * @version $Id: IdCardNoLimit.java, v 0.1 2017年3月10日 下午4:06:03 Administrator Exp $
 */
@Component
public class IdcardNoFilter {
	@Autowired
	private TouristReadMapper touristReadMapper;

	/**
	 * 对一个产品，同一个供应商在一个有效期内一个身份证号是否买过票
	 * 
	 * @return
	 */
	public List<String> checkUsedIdcardNo(long productId, long supplierId, List<String> idcardNos, Date playDate) {
		if (Check.NuNCollections(idcardNos)) {
			return null;
		}
		List<String> usedNos = touristReadMapper.getUsedIdcarNo(productId, supplierId, idcardNos, playDate);
		return usedNos;
	}
}
