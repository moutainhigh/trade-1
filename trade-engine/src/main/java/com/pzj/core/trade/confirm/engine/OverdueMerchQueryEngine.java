package com.pzj.core.trade.confirm.engine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.order.read.MerchReadMapper;

/**
 * 逾期商品查询引擎.
 * @author YRJ
 *
 */
@Component(value = "overdueMerchQueryEngine")
public class OverdueMerchQueryEngine {
	@Autowired
	private MerchReadMapper merchReadMapper;

	public List<MerchEntity> doEngine() {
		return merchReadMapper.getAllOverdueMerches();
	}
}
