/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.engine.event;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.trade.merch.entity.MerchCleanRelationEntity;
import com.pzj.trade.merch.read.MerchCleanReadMapper;
import com.pzj.trade.order.entity.MerchCleanRelationResponse;

/**
 * 
 * @author Administrator
 * @version $Id: MerchCleanRelationEnent.java, v 0.1 2017年3月23日 下午5:58:43 Administrator Exp $
 */
@Component(value = "merchCleanRelationEvent")
public class MerchCleanRelationEvent {

	@Resource(name = "merchCleanReadMapper")
	private MerchCleanReadMapper merchCleanReadMapper;

	public List<MerchCleanRelationResponse> getMerchCleanRelation(String order_id, String merch_id) {
		final List<MerchCleanRelationEntity> merchcleanEntitys = merchCleanReadMapper.queryCleanRelationsByOrderIdAndMerchId(order_id, merch_id);
		return assembleMerchClean(merchcleanEntitys);
	}

	/**
	 * 组装商品清算关系记录信息
	 *
	 * @param merch
	 * @param merchcleanEntitys
	 */
	private List<MerchCleanRelationResponse> assembleMerchClean(List<MerchCleanRelationEntity> merchcleanEntitys) {
		List<MerchCleanRelationResponse> merchCleanRelationResponses = new ArrayList<MerchCleanRelationResponse>();

		for (final MerchCleanRelationEntity merchCleanRelationEntity : merchcleanEntitys) {
			final MerchCleanRelationResponse merchCleanRelationResponse = new MerchCleanRelationResponse();
			merchCleanRelationResponse.setClean_state(merchCleanRelationEntity.getClean_state());
			merchCleanRelationResponse.setIs_minus_clean(merchCleanRelationEntity.getIs_minus_clean());
			merchCleanRelationResponse.setNormal_clean_amount(merchCleanRelationEntity.getNormal_clean_amount());
			merchCleanRelationResponse.setNormal_clean_num(merchCleanRelationEntity.getNormal_clean_num());
			merchCleanRelationResponse.setOverdue_clean_amount(merchCleanRelationEntity.getOverdue_clean_amount());
			merchCleanRelationResponse.setOverdue_clean_num(merchCleanRelationEntity.getOverdue_clean_num());
			merchCleanRelationResponse.setRefund_clean_amount(merchCleanRelationEntity.getRefund_clean_amount());
			merchCleanRelationResponse.setRefund_clean_num(merchCleanRelationEntity.getRefund_clean_num());
			merchCleanRelationResponse.setClean_type(merchCleanRelationEntity.getClean_type());
			merchCleanRelationResponses.add(merchCleanRelationResponse);
		}
		return merchCleanRelationResponses;
	}

}
