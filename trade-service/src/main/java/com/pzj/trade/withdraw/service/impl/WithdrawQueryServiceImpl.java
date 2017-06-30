package com.pzj.trade.withdraw.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzj.core.customer.profile.ProfileBasicInfo;
import com.pzj.core.customer.profile.ProfileService;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.entity.QueryResult;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.common.ErrorCode;
import com.pzj.trade.order.entity.OrderListEntity;
import com.pzj.trade.payment.entity.CashPostalEntity;
import com.pzj.trade.payment.read.WithdrawReadMapper;
import com.pzj.trade.withdraw.entity.WithdrawDetailResponse;
import com.pzj.trade.withdraw.entity.WithdrawResponse;
import com.pzj.trade.withdraw.exception.WithdrawException;
import com.pzj.trade.withdraw.model.WithdrawReqParameter;
import com.pzj.trade.withdraw.service.WithdrawQueryService;

@Service("withdrawQueryService")
public class WithdrawQueryServiceImpl implements WithdrawQueryService {
	private final static Logger logger = LoggerFactory.getLogger(WithdrawQueryServiceImpl.class);

	@Autowired
	private WithdrawReadMapper withdrawReadMapper;
	
	@Resource(name = "profileService")
	private ProfileService profileService;
	
	@Override
	public Result<QueryResult<WithdrawResponse>> queryAccountWithdraw(final WithdrawReqParameter param,
			final ServiceContext serviceContext) {
		logger.info("提现信息列表查询参数：parameter:{}", JSONConverter.toJson(param));
		if (Check.NuNObject(param)) {
			return new Result<QueryResult<WithdrawResponse>>(7485, "请求参数为空");
		}
		final QueryResult<WithdrawResponse> queryResult = getQueryPage(param);
		final Result<QueryResult<WithdrawResponse>> result = new Result<QueryResult<WithdrawResponse>>();
		result.setData(queryResult);
		return result;
	}

	/**
	 * 获取返回页面信息
	 * 
	 * @param param
	 * @return
	 */
	private QueryResult<WithdrawResponse> getQueryPage(final WithdrawReqParameter param) {
		final List<CashPostalEntity> withdrawlist = withdrawReadMapper.queryWithdrawByCondition(param,
				param.getPageNum() * param.getPageSize(), param.getPageSize());
		final int totalNum = withdrawReadMapper.queryWithdrawNumByCondition(param);
		final List<WithdrawResponse> resultlist = new ArrayList<WithdrawResponse>();
		for (final CashPostalEntity po : withdrawlist) {
			final WithdrawResponse rep = new WithdrawResponse();
			rep.setWithdrawId(po.getPostal_id());
			rep.setAccountId(po.getAccount_id());
			rep.setTargetWithdrawMoney(po.getTarget_postal_money());
			rep.setWithdrawMoney(po.getPostal_money());
			rep.setWithdrawStatus(po.getPostal_status());
			rep.setWithdrawType(po.getWithdraw_type());
			rep.setCreateTime(po.getCreate_time());
			rep.setUpdateTime(po.getUpdate_time());
			resultlist.add(rep);
		}
		final QueryResult<WithdrawResponse> page = new QueryResult<WithdrawResponse>(param.getPageNum(),
				param.getPageSize());
		page.setRecords(resultlist);
		page.setTotal(totalNum);
		return page;
	}

	@Override
	public Result<QueryResult<WithdrawDetailResponse>> queryWithdrawDetail(
			WithdrawReqParameter param) {

		logger.info("提现明细列表查询参数：parameter:{}", JSONConverter.toJson(param));
		
		/**
		 *@TODO 增加校验逻辑
		 */
		if (Check.NuNObject(param)) {
			return new Result<QueryResult<WithdrawDetailResponse>>(ErrorCode.PARAM_NOT_NULL.getCode(), ErrorCode.PARAM_NOT_NULL.getDesc());
		}
		if (Check.NuNObject(param.getAccountId())) {
			return new Result<QueryResult<WithdrawDetailResponse>>(ErrorCode.ACC_NOT_EMPTY.getCode(), ErrorCode.ACC_NOT_EMPTY.getDesc());
		}
		if (Check.NuNObject(param.getStartTime())||Check.NuNObject(param.getEndTime())) {
			return new Result<QueryResult<WithdrawDetailResponse>>(ErrorCode.TIME_NOT_EMPTY.getCode(), ErrorCode.TIME_NOT_EMPTY.getDesc());
		}
		
//		Result<ProfileBasicInfo> profileBasicInfo=profileService.queryActivateProfileBasicInfoById(param.getAccountId());
//		logger.info("用户信息：", JSONConverter.toJson(profileBasicInfo));
//		//用戶不存在
//		if(Check.NuNObject(profileBasicInfo)){
//			throw new WithdrawException(ErrorCode.USER_NOT_EXIST.getCode(), "提现失败, "+ErrorCode.USER_NOT_EXIST.getDesc()+".");
//		}
		
		QueryResult<WithdrawDetailResponse> withdrawDetail=buildWithdrawDetail(param);
		
		return new Result<QueryResult<WithdrawDetailResponse>>(withdrawDetail);

	}
	/**
	 * 瓶装对象
	 * @param cache
	 * @param param
	 * @return
	 */
	private QueryResult<WithdrawDetailResponse> buildWithdrawDetail(WithdrawReqParameter param) {
		List<OrderListEntity> cache=withdrawReadMapper.queryWithdrawDetail(param,param.getPageNum() * param.getPageSize(), param.getPageSize());
		
		final List<WithdrawDetailResponse> resultlist = new ArrayList<WithdrawDetailResponse>();
		final int totalNum = withdrawReadMapper.queryWithdrawDetailNum(param);
		for(OrderListEntity entity:cache){
			final WithdrawDetailResponse rep = new WithdrawDetailResponse();
			rep.setTransactionId(entity.getTransaction_id());
			rep.setOrderStatus(entity.getOrder_status());
			rep.setCreateTime(entity.getCreate_time());
			rep.setConfirmTime(entity.getConfirm_time());
			rep.setTotalAmount(entity.getTotal_amount());
			rep.setTotalNum(entity.getTotal_num());
			rep.setRefundAmount(entity.getRefund_amount());
			rep.setRefundNum(entity.getRefund_num());
			resultlist.add(rep);
		}
		QueryResult<WithdrawDetailResponse> result=new QueryResult<WithdrawDetailResponse>(param.getPageNum(), param.getPageSize());
		result.setTotal(totalNum);
		result.setRecords(resultlist);
		return result;
	}
}
