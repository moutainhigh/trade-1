package com.pzj.trade;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.clearing.model.CleaningModel;
import com.pzj.trade.clearing.model.UnClearingRequestModel;
import com.pzj.trade.clearing.response.UnClearingRelationResponse;
import com.pzj.trade.clearing.service.ClearingQueryService;
import com.pzj.trade.clearing.service.ClearingService;

/**
 * 定时清算任务.
 *
 */
@Component(value = "clearingTask")
public class ClearingTask {

	private final static Logger logger = LoggerFactory.getLogger(ClearingTask.class);

	@Autowired
	private ClearingService clearingService;

	@Autowired
	private ClearingQueryService clearingQueryService;

	/**
	 * 1分钟执行一次.
	 */
	@Scheduled(cron = "0 0/1 * * * *")
	public void clearing() {
		final ServiceContext serviceContext = null;
		final UnClearingRequestModel clearModel = new UnClearingRequestModel();
		final Result<ArrayList<UnClearingRelationResponse>> result = clearingQueryService.queryUnClearingRecordByPager(clearModel, serviceContext);
		if (!result.isOk()) {
			logger.error("获取未核销记录失败, result: " + JSONConverter.toJson(result));
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("待清算记录: " + (JSONConverter.toJson(result)));
		}

		final ArrayList<UnClearingRelationResponse> relations = result.getData();
		if (relations == null) {
			return;
		}

		for (final UnClearingRelationResponse relation : relations) {
			final CleaningModel cleaningModel = new CleaningModel();
			cleaningModel.setOrderId(relation.getOrderId());
			cleaningModel.setMerchId(relation.getMerchId());

			try {
				final Result<Boolean> clearResult = clearingService.clean(cleaningModel, serviceContext);
				if (clearResult.isOk()) {
					logger.info("清算成功. cleaningModel: {}, clearResult: {}.", cleaningModel, clearResult);
					continue;
				}

				logger.error("清算失败. cleaningModel: {}, clearResult: {}.", cleaningModel, clearResult);
			} catch (final Throwable e) {
				logger.error("清算失败. cleaningModel: " + cleaningModel, e);
			}
		}
	}
}
