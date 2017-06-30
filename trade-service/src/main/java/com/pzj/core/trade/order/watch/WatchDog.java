package com.pzj.core.trade.order.watch;

import com.pzj.core.trade.order.engine.model.MerchModel;

/**
 * 看门狗.
 * @author YRJ
 *
 */
public interface WatchDog {

	boolean watch(MerchModel merchModel);

	/**
	 * 异常忽略.
	 * @param e
	 */
	//void ignore(MerchModel merchModel, Throwable e);
}
