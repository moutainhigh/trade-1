/*
 * TOperLogMapper.java
 
 * www.piaozhijia.coim
 */
package com.pzj.core.trade.log.dao.read;

import com.pzj.core.trade.log.dao.entity.OperLogEntity;

/**
 * Mapper接口.区域
 * @author 票之家
 */

public interface OperLogRMapper {

	/**
	 * 获取免票，特价票最新的审核记录
	 * 
	 * @param bookId
	 * @return
	 */
	OperLogEntity selectCurrentDrawLog(String bookId);

}
