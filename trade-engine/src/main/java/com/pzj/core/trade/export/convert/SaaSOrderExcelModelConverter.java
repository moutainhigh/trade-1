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
import com.pzj.core.trade.export.entity.SaaSOrderExportEntity;
import com.pzj.core.trade.export.model.SaaSMerchExcelModel;
import com.pzj.core.trade.export.model.SaaSOrderExcelModel;
import com.pzj.core.trade.order.engine.RuleReader;
import com.pzj.framework.context.Result;
import com.pzj.settlement.base.common.enums.ProductType;
import com.pzj.trade.order.common.MerchStateEnum;

/**
 * 
 * @author Administrator
 * @version $Id: E.java, v 0.1 2017年3月2日 上午10:47:47 Administrator Exp $
 */
@Component
public class SaaSOrderExcelModelConverter {

	@Autowired
	private IUserService userService;

	@Autowired
	private IProductRuleService productRuleService;

	public List<SaaSOrderExcelModel> convert(ArrayList<SaaSOrderExportEntity> exports) {
		HashMap<String, SaaSOrderExcelModel> maps = new HashMap<String, SaaSOrderExcelModel>();

		HashMap<Long, String> userNames = new HashMap<Long, String>();//用户id:name

		batchPrepareModel(exports, userNames);

		for (SaaSOrderExportEntity item : exports) {
			SaaSOrderExcelModel order = null;
			if (maps.containsKey(item.getTransaction_id())) {
				order = maps.get(item.getTransaction_id());
				SaaSMerchExcelModel normalMerch = assembleMerch(item, userNames);
				mergeByProductId(order, normalMerch);
			} else {//第一次
				order = new SaaSOrderExcelModel();
				maps.put(item.getTransaction_id(), order);
				assembleOrder(userNames, item, order);
				SaaSMerchExcelModel normalMerch = assembleMerch(item, userNames);
				mergeByProductId(order, normalMerch);
			}
		}
		Iterator<SaaSOrderExcelModel> iterator = maps.values().iterator();
		List<SaaSOrderExcelModel> orders = new ArrayList<SaaSOrderExcelModel>();
		while (iterator.hasNext()) {
			orders.add(iterator.next());
		}

		Comparator<SaaSOrderExcelModel> c = new Comparator<SaaSOrderExcelModel>() {
			@Override
			public int compare(SaaSOrderExcelModel o1, SaaSOrderExcelModel o2) {
				return -o1.getCreate_time().compareTo(o2.getCreate_time());
			}

		};
		Collections.sort(orders, c);
		return orders;
	}

	/**
	 * 
	 * @param order
	 * @param normalMerch
	 */
	private void mergeByProductId(SaaSOrderExcelModel order, SaaSMerchExcelModel merch) {
		//第一次
		if (order.getMerchs().isEmpty()) {
			order.getMerchs().add(merch);
			return;
		}
		//有则合并
		for (SaaSMerchExcelModel item : order.getMerchs()) {
			if (item.getProduct_id() == merch.getProduct_id()) {
				item.setTotal_num(item.getTotal_num() + merch.getTotal_num());
				item.setRefund_num(item.getRefund_num() + merch.getRefund_num());
				item.setCheck_num(item.getCheck_num() + merch.getCheck_num());
				item.setAuto_check_num(item.getAuto_check_num() + merch.getAuto_check_num());
				item.setOverdue_num(item.getOverdue_num() + merch.getOverdue_num());
				return;
			}
		}
		//无则添加
		order.getMerchs().add(merch);
		return;

	}

	/**
	 * 
	 * @param skuNames
	 * @param afterRebates
	 * @param item
	 * @return
	 */
	private SaaSMerchExcelModel assembleMerch(SaaSOrderExportEntity item, HashMap<Long, String> userNames) {
		SaaSMerchExcelModel merch = new SaaSMerchExcelModel();
		merch.setProduct_id(item.getProduct_id());
		merch.setMerch_name(item.getMerch_name());
		merch.setMerch_type(item.getMerch_type());
		merch.setSku_name(item.getSku_name());
		merch.setCheck_time(item.getCheck_time());
		merch.setTotal_num(item.getTotal_num());
		merch.setProduct_varie(item.getProduct_varie());
		merch.setRefund_num(item.getRefund_num());
		merch.setCheck_num(item.getCheck_num());
		merch.setAuto_check_num(item.getAuto_check_num());
		merch.setOverdue_num(item.getOverdueNum());
		merch.setOriginal_suppliler_id(item.getOriginal_supplier_id());
		merch.setOriginal_suppliler_name(userNames.containsKey(item.getOriginal_supplier_id()) ? userNames.get(item
				.getOriginal_supplier_id()) : "");

		merch.setSale_price(item.getSale_price());
		merch.setSale_after_rebate(item.getSale_after_rebate());
		merch.setPurch_price(item.getPurch_price());
		merch.setPurch_after_rebate(item.getPurch_after_rebate());

		if (item.getCheck_time() != null && item.getExpire_time() != null
				&& item.getCheck_time().getTime() > item.getExpire_time().getTime()) {
			merch.setOverdue_num(item.getTotal_num() - item.getRefund_num());
		}
		if (item.getCheck_time() == null && item.getExpire_time() != null
				&& new Date().getTime() > item.getExpire_time().getTime()
				&& item.getMerch_state() != MerchStateEnum.UNAVAILABLE.getState()) {
			merch.setOverdue_num(item.getTotal_num() - item.getRefund_num());
		}
		return merch;
	}

	private void assembleOrder(HashMap<Long, String> userNames, SaaSOrderExportEntity item, SaaSOrderExcelModel order) {
		{
			order.setOrder_id(item.getOrder_id());
			order.setTransaction_id(item.getTransaction_id());
			order.setOrder_status(item.getOrder_status());
			order.setCreate_time(item.getCreate_time());
			order.setStart_time(item.getStart_time());
			if (item.getMerch_type() == ProductType.nativeProduct.key) {
				if (!isNeedPlayTimeForNativeProduct(item.getProduct_id())) {
					order.setStart_time(null);
				}
			}
			order.setSale_port(item.getSale_port());
			order.setReseller_name(userNames.containsKey(item.getReseller_id()) ? userNames.get(item.getReseller_id()) : "");
			order.setSupplier_name(userNames.containsKey(item.getSupplier_id()) ? userNames.get(item.getSupplier_id()) : "");
			order.setContactee(item.getContactee());
			order.setContact_mobile(item.getContact_mobile());
			order.setIdcard_no(item.getIdcard_no());
			order.setContact_email(item.getContact_email());
			order.setContact_spelling(item.getContact_spelling());
			order.setDelivery_addr((item.getDelivery_addr_nation() + item.getDelivery_addr_province()
					+ item.getDelivery_addr_city() + item.getDelivery_addr_county() + item.getDelivery_addr_detail())
					.replaceAll("null", ""));
			order.setGet_on_addr((item.getGet_on_addr_nation() + item.getGet_on_addr_province() + item.getGet_on_addr_city()
					+ item.getGet_on_addr_county() + item.getGet_on_addr_detail()).replaceAll("null", ""));
			order.setGet_off_addr((item.getGet_off_addr_nation() + item.getGet_off_addr_province()
					+ item.getGet_off_addr_city() + item.getGet_off_addr_county() + item.getGet_off_addr_detail()).replaceAll(
					"null", ""));
			order.setExpect_use_car_time(item.getExpect_use_car_time());
			order.setFlight_no(item.getFlight_no());
			order.setTrain_no(item.getTrain_no());
			order.setExpect_to_shop_time(item.getExpect_to_shop_time());
			order.setRemark(item.getRemark());
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
			return null;
		}

		ProductRuleOutModel ruleModel = result.getData();

		return ruleModel;
	}

	/**
	 * 
	 * @param userNames
	 */
	private void batchPrepareModel(ArrayList<SaaSOrderExportEntity> exports, HashMap<Long, String> userNames) {
		HashSet<Long> users = new HashSet<Long>();

		for (SaaSOrderExportEntity export : exports) {
			users.add(export.getReseller_id());
			users.add(export.getSupplier_id());
			users.add(export.getOriginal_supplier_id());
		}
		//查用户名字
		List<SysUser> userEntitys = userService.findUserByIds(new ArrayList<Long>(users));
		for (SysUser sysUser : userEntitys) {
			userNames.put(sysUser.getId(), sysUser.getName());
		}
	}

}
