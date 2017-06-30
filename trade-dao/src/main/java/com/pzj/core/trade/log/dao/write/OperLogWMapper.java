/*
 * TOperLogMapper.java
 
 * www.piaozhijia.coim
 */
package com.pzj.core.trade.log.dao.write;

import java.util.List;

import com.pzj.core.trade.log.dao.entity.OperLogEntity;

/**
 * Mapper接口.区域
 * @author 票之家
 */

public interface OperLogWMapper {

	/**新增*/
	int insert(OperLogEntity entity);

	/**批量新增*/
	int insertBatch(List<OperLogEntity> entitys);

}
