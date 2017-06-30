/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.voucher.engine.common;

import com.pzj.core.sku.common.constants.ProductTypeGlobal;

/**
 * 产品类型通用操作
 * @author Administrator
 * @version $Id: ProductType.java, v 0.1 2016年11月28日 上午11:01:12 Administrator Exp $
 */
public class ProductType {
	/**
	 *根据产品类型判断是否通用产品。
	 *产品主要分成两个大类，景区产品和通用产品。
	 *景区产品包含景区产品、演艺产品。
	 */
	public static boolean isCommonProduct(int productCategory) {
		if (productCategory == ProductTypeGlobal.NORMAL || productCategory == ProductTypeGlobal.SCENIC
				|| productCategory == ProductTypeGlobal.REBATE || productCategory == ProductTypeGlobal.PACK
				|| productCategory == ProductTypeGlobal.COMPOSE || productCategory == ProductTypeGlobal.SCORE_PACK
				|| productCategory == ProductTypeGlobal.PERFORMING_PACK) {
			return false;
		}
		return true;
	}

	/**
	 *根据产品类型判断是否景区产品产品。
	 *产品主要分成两个大类，景区产品和通用产品。
	 *景区产品包含景区产品、演艺产品。
	 */
	public static boolean isScenicProduct(int productCategory) {
		return !isCommonProduct(productCategory);
	}

}
