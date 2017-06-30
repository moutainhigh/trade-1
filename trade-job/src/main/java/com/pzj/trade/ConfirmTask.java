/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.confirm.model.ConfirmModel;
import com.pzj.trade.confirm.model.OverdueConfirmReqModel;
import com.pzj.trade.confirm.response.OverdueConfirmRespModel;
import com.pzj.trade.confirm.service.ConfirmQueryService;
import com.pzj.trade.confirm.service.ConfirmService;
import com.pzj.voucher.entity.VoucherEntity;

/**
 * 定时核销任务
 * <li>1:老版本核销过期的凭证</li>
 * <li>2:新版本核销过期且自动核销的凭证</li>
 *
 * @author DongChunfu
 * @version $Id: ConfirmTask.java, v 0.1 2017年4月24日 上午10:50:09 DongChunfu Exp $
 */
@Component
public class ConfirmTask {

	private final Logger logger = LoggerFactory.getLogger(ConfirmTask.class);

	@Autowired
	private ConfirmService confirmService;

	@Resource(name = "confirmQueryService")
	private ConfirmQueryService confirmQueryService;

	/**
	 * 每2分钟执行一次
	 */
	@Scheduled(cron = "0 0/2 * * * *")
	public void autoConfirm() {
		Result<OverdueConfirmRespModel> result = null;
		try {
			result = confirmQueryService.overdueMerches(new OverdueConfirmReqModel(), (ServiceContext) null);
		} catch (final Throwable t) {
			logger.error("query overdue merch error.", t);
			return;
		}

		if (!result.isOk()) {
			logger.error("query overdue merch fail,msg:{}", result.getErrorCode() + ":" + result.getErrorMsg());
			return;
		}

		final OverdueConfirmRespModel respModel = result.getData();
		if (Check.NuNCollections(respModel.getVoucherIds())) {
			logger.debug("overdue voucher is none.");
			return;
		}

		for (final Long voucherId : respModel.getVoucherIds()) {
			final ConfirmModel confirmModel = new ConfirmModel();
			confirmModel.setVoucherId(voucherId);
			confirmModel.setType(2);//核销方式为自动核销
			try {
				logger.info("auto confirm, reqModel:{}.", confirmModel);
				final Result<VoucherEntity> confirmResult = confirmService.confirm(confirmModel, (ServiceContext) null);
				logger.info("auto confirm result, reqModel:{}, result:{}.", confirmModel, confirmResult.getErrorMsg());
			} catch (final Throwable t) {
				logger.info("auto confirm error, reqModel:" + confirmModel, t);
			}
		}
	}
}
