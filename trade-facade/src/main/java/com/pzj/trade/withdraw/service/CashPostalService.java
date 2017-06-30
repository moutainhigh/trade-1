package com.pzj.trade.withdraw.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.withdraw.model.CashPostalModel;

/**
 * 提现接口
 * @author kangzl
 *
 */
public interface CashPostalService {

	/**分销商用户类型*/
	public final static int reseller_type_id = 2;
	/**供应商的用户类型 */
	public final static int supplier_type_id = 1;

	/**  二维码分销商的分销商类型*/
	public final static int QR_code_reseller_type = 1;

	/**
	 * 资金提现
	 * 
	 * @param cashPostal   提现参数.
	 * @param serviceContext
	 * @return
	 * 
	 */
	Result<Boolean> cashPostal(final CashPostalModel cashPostal, ServiceContext serviceContext);
}
