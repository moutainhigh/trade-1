package com.pzj.trade.withdraw.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.withdraw.entity.WithdrawDetailResponse;
import com.pzj.trade.withdraw.entity.WithdrawResponse;
import com.pzj.trade.withdraw.model.WithdrawReqParameter;

public interface WithdrawQueryService {
    /**
     * 查询用户的提现信息接口
     * @return
     */
    Result<QueryResult<WithdrawResponse>> queryAccountWithdraw(WithdrawReqParameter param,ServiceContext serviceContext);
    
    /**
	 * 资金提现明细
	 * 
	 * @param param   提现详情参数.
	 * @return
	 * 
	 */
	Result<QueryResult<WithdrawDetailResponse>> queryWithdrawDetail(WithdrawReqParameter param);
}
