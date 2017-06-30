package com.pzj.core.trade.refund.engine;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.handler.ForceApplyQueryHandler;
import com.pzj.core.trade.refund.entity.RefundApplyQueryPageEntity;
import com.pzj.core.trade.refund.read.RefundApplyReadMapper;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.refund.model.QueryRefundApplyReqModel;
import com.pzj.trade.refund.vo.ForceRefundApplyVO;

/**
 * 分页查询退款申请引擎
 *
 * @author DongChunfu
 * @date 2016年12月13日
 */
@Component(value = "pageQueryRefundApplyEngine")
public class PageQueryRefundApplyEngine {

	@Autowired
	private RefundApplyReadMapper refundApplyReadMapper;

	@Resource(name = "forceApplyQueryHandler")
	private ForceApplyQueryHandler forceApplyQueryHandler;

	public QueryResult<ForceRefundApplyVO> query(final QueryRefundApplyReqModel reqModel) {
		final int currentPage = reqModel.getCurrentPage();
		final int pageSize = reqModel.getPageSize();

		final RefundApplyQueryPageEntity queryParam = buildQueryParam(reqModel);
		final QueryResult<ForceRefundApplyVO> queryResult = new QueryResult<ForceRefundApplyVO>(currentPage, pageSize);

		final int count = refundApplyReadMapper.countRefundApply(queryParam);
		if (count == 0) {
			queryResult.setTotal(count);
			return queryResult;
		}

		queryResult.setTotal(count);

		final List<String> refundIds = refundApplyReadMapper.pageQueryRefundApply(queryParam);

		final List<ForceRefundApplyVO> forceRefunds = new ArrayList<>(refundIds.size());
		for (final String refundId : refundIds) {
			final ForceRefundApplyVO forceRefund = forceApplyQueryHandler.queryByRefundId(refundId);
			forceRefunds.add(forceRefund);
		}

		queryResult.setRecords(forceRefunds);

		return queryResult;
	}

	private RefundApplyQueryPageEntity buildQueryParam(final QueryRefundApplyReqModel reqModel) {
		final int currentPage = reqModel.getCurrentPage();
		final int pageSize = reqModel.getPageSize();

		final RefundApplyQueryPageEntity queryParam = RefundApplyQueryPageEntity.newInstance();
		queryParam.setOrderId(reqModel.getOrderId());
		queryParam.setAuditState(reqModel.getAuditState());
		queryParam.setThirdAuditState(reqModel.getThirdAuditState());

		queryParam.setStartApplyDate(reqModel.getStartApplyDate());
		queryParam.setEndApplyDate(reqModel.getEndApplyDate());
		queryParam.setStartAuditDate(reqModel.getStartAuditDate());
		queryParam.setEndAuditDate(reqModel.getEndAuditDate());

		queryParam.setIsPage(reqModel.getIsPage());
		queryParam.setStartIndex((currentPage - 1) * pageSize);
		queryParam.setEndIndex(currentPage * pageSize);

		return queryParam;
	}

}
