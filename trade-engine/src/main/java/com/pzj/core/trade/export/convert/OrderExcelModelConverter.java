/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.export.convert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.base.entity.SysUser;
import com.pzj.base.service.sys.IUserService;
import com.pzj.core.product.common.global.ProductRuleGroupEnum;
import com.pzj.core.product.common.global.ProductRuleItem.PlaytimeEnum;
import com.pzj.core.product.common.model.response.ProductRuleOutModel;
import com.pzj.core.product.common.service.IProductRuleService;
import com.pzj.core.sku.common.constants.SkuProductGlobal.NeedPlayTimeTypeEnum;
import com.pzj.core.trade.export.entity.OrderExportExcelEntity;
import com.pzj.core.trade.export.model.MerchExcelModel;
import com.pzj.core.trade.export.model.OrderExcelModel;
import com.pzj.core.trade.export.model.SkuExcelModel;
import com.pzj.core.trade.order.engine.RuleReader;
import com.pzj.core.trade.order.engine.exception.OrderException;
import com.pzj.framework.context.Result;
import com.pzj.settlement.base.common.enums.ProductType;
import com.pzj.trade.order.common.MerchStateEnum;

/**
 * 
 * @author Administrator
 * @version $Id: E.java, v 0.1 2017年3月2日 上午10:47:47 Administrator Exp $
 */
@Component
public class OrderExcelModelConverter {

	@Autowired
	private IUserService userService;

	@Autowired
	private IProductRuleService productRuleService;

	public List<OrderExcelModel> convert(ArrayList<OrderExportExcelEntity> exports) {
		HashMap<String, OrderExcelModel> maps = new HashMap<String, OrderExcelModel>();

		HashMap<Long, String> userNames = new HashMap<Long, String>();//用户id:name

		batchPrepareModel(exports, userNames);

		for (OrderExportExcelEntity item : exports) {
			OrderExcelModel order = null;
			if (maps.containsKey(item.getOrder_id())) {
				order = maps.get(item.getOrder_id());
				MerchExcelModel normalMerch = assembleMerch(item);
				order.getMerchs().add(normalMerch);
			} else {//第一次
				order = new OrderExcelModel();
				maps.put(item.getOrder_id(), order);
				assembleOrder(userNames, item, order);
				MerchExcelModel normalMerch = assembleMerch(item);
				order.getMerchs().add(normalMerch);
			}
		}
		Iterator<OrderExcelModel> iterator = maps.values().iterator();
		List<OrderExcelModel> orders = new ArrayList<OrderExcelModel>();
		while (iterator.hasNext()) {
			orders.add(iterator.next());
		}

		Comparator<OrderExcelModel> c = new Comparator<OrderExcelModel>() {
			@Override
			public int compare(OrderExcelModel o1, OrderExcelModel o2) {
				return -o1.getCreate_time().compareTo(o2.getCreate_time());
			}

		};
		Collections.sort(orders, c);
		return orders;
	}

	/**
	 * 
	 * @param skuNames
	 * @param afterRebates
	 * @param item
	 * @return
	 */
	private MerchExcelModel assembleMerch(OrderExportExcelEntity item) {
		MerchExcelModel normalMerch = new MerchExcelModel();
		{
			normalMerch.setMerch_name(item.getMerch_name());
			normalMerch.setMerch_type(item.getMerch_type());
			normalMerch.setSku_name(item.getSku_name());
			normalMerch.setTotal_num(item.getTotal_num());
			normalMerch.setCheck_time(item.getCheck_time());

			normalMerch.setSale_price(item.getSale_price());
			normalMerch.setSale_after_rebate(item.getSale_after_price());
			normalMerch.setPurch_price(item.getPurch_price());
			normalMerch.setPurch_after_rebate(item.getPurch_after_price());
			if ((item.getCheck_time() != null && item.getCheck_time().getTime() > item.getExpire_time().getTime())
					|| (item.getCheck_time() == null && new Date().getTime() > item.getExpire_time().getTime() && item
							.getMerch_state() != MerchStateEnum.UNAVAILABLE.getState())) {
				normalMerch.setOverdue_num(item.getTotal_num() - item.getRefund_num());
			}

			SkuExcelModel sku = new SkuExcelModel();
			sku.setMerch_state(item.getMerch_state());
			sku.setNum(item.getTotal_num() - item.getRefund_num());
			normalMerch.getSkus().add(sku);
		}
		if (item.getRefund_num() > 0) {
			int refundingNum = 0;
			if (item.getIs_refunding() == 1) {//退款中
				refundingNum = item.getRefunding_num();
				if (refundingNum > 0) {
					SkuExcelModel sku = new SkuExcelModel();
					sku.setMerch_state(MerchStateEnum.REFUNDING.getState());
					sku.setNum(refundingNum);
					normalMerch.getSkus().add(sku);
				}
			}
			if (item.getRefund_num() - refundingNum > 0) {//已退款
				SkuExcelModel sku = new SkuExcelModel();
				sku.setMerch_state(MerchStateEnum.REFUNDED.getState());
				sku.setNum(item.getRefund_num() - refundingNum);
				normalMerch.getSkus().add(sku);
			}
		}
		return normalMerch;
	}

	/**
	 * 
	 * @param userNames
	 * @param item
	 * @param order
	 */
	private void assembleOrder(HashMap<Long, String> userNames, OrderExportExcelEntity item, OrderExcelModel order) {
		{
			order.setOrder_id(item.getOrder_id());
			order.setOrder_status(item.getOrder_status());
			order.setSale_port(item.getSale_port());
			order.setContactee(item.getContactee());
			order.setStart_time(item.getStart_time());
			if (item.getMerch_type() == ProductType.nativeProduct.key) {
				if (!isNeedPlayTimeForNativeProduct(item.getProduct_id())) {
					order.setStart_time(null);
				}
			}
			order.setCreate_time(item.getCreate_time());
			order.setTotal_amount(item.getTotal_amount());
			order.setProduct_varie(item.getProduct_varie());
			order.setIs_direct(item.getIs_direct());
			order.setReseller_name(userNames.containsKey(item.getReseller_id()) ? userNames.get(item.getReseller_id()) : "");
			order.setSupplier_name(userNames.containsKey(item.getSupplier_id()) ? userNames.get(item.getSupplier_id()) : "");
		}
	}

	private boolean isNeedPlayTimeForNativeProduct(long productId) {
		ProductRuleOutModel ruleModels = getProductRuleModel(productId);
		ProductRuleOutModel outModel = RuleReader.readProdRuleInstByName(PlaytimeEnum.isNeedPlaytime.getName(), ruleModels);
		if (outModel == null) {
			return false;
		}
		if (outModel.getValue().intValue() == NeedPlayTimeTypeEnum.NEED_PLAYTIME.getValue()) {
			return true;
		}
		return false;
	}

	private ProductRuleOutModel getProductRuleModel(long prodId) {
		Result<ProductRuleOutModel> result;
		try {
			List<ProductRuleGroupEnum> group = new ArrayList<ProductRuleGroupEnum>();
			group.add(ProductRuleGroupEnum.PRODUCT_RULE_PLAY_TIME);
			result = productRuleService.getBySkuId(prodId, group);
		} catch (Throwable e) {
			throw new OrderException(10500, "根据产品ID[" + prodId + "]获取产品规则模型失败.", e);
		}

		if (!result.isOk()) {
			throw new OrderException(10500, "根据产品ID[" + prodId + "]获取产品规则模型失败, 返回错误码[" + result.getErrorCode() + "], 错误描述["
					+ result.getErrorMsg() + "].");
		}

		ProductRuleOutModel ruleModel = result.getData();
		if (ruleModel == null) {
			throw new OrderException(10500, "根据产品ID[" + prodId + "]获取产品规则模型失败, 返回错误码[" + result.getErrorCode() + "], 错误描述["
					+ result.getErrorMsg() + "], 规则模型为空.");
		}

		return ruleModel;
	}

	/**
	 * 
	 * @param userNames
	 * @param skuNames
	 * @param afterRebates
	 */
	private void batchPrepareModel(ArrayList<OrderExportExcelEntity> exports, HashMap<Long, String> userNames) {
		HashSet<Long> users = new HashSet<Long>();

		for (OrderExportExcelEntity export : exports) {
			users.add(export.getReseller_id());
			users.add(export.getSupplier_id());
		}
		//查用户名字
		List<SysUser> userEntitys = userService.findUserByIds(new ArrayList<Long>(users));
		for (SysUser sysUser : userEntitys) {
			userNames.put(sysUser.getId(), sysUser.getName());
		}
	}

}
