package com.pzj.trade;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pzj.framework.context.Result;
import com.pzj.trade.book.service.BookTaskService;

/**
 * 定时清算任务.
 *
 */
@Component(value = "bookCancelTask")
public class BookCancelTask {

	private final static Logger logger = LoggerFactory.getLogger(BookCancelTask.class);

	@Autowired
	private BookTaskService bookTaskService;

	@Value("${pre_order_cancel_time}")
	private Integer minute;

	@Value("${sparpreis_cancel_time}")
	private Integer hour;

	/**后台取消操作人，需指定管理员id*/
	private Long operator = 1234567890l;

	/**
	 * 5分钟执行一次.
	 */
	@Scheduled(cron = "0 0/5 * * * *")
	public void cancelPreOrder() {
		if (minute == null || minute <= 0) {
			return;
		}

		Result<Boolean> result = bookTaskService.cancelPreBook(minute, operator);
		if (result.isOk()) {
			logger.error("{}定时取消前置订单成功", new Date());
		}
	}

	/**
	 * 5分钟执行一次.
	 */
	//	@Scheduled(cron = "0 0/5 * * * *")
	//	public void cancelSparpreis() {
	//		if (hour == null || hour <= 0) {
	//			return;
	//		}
	//
	//		Result<Boolean> result = bookTaskService.cancelSparpreisNotAudit(hour, operator);
	//		if (result.isOk()) {
	//			logger.error("{}定时取消免票特价票成功", new Date());
	//		}
	//	}
}
