package com.pzj.core.trade.log.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.log.dao.entity.OperLogEntity;
import com.pzj.core.trade.log.dao.write.OperLogWMapper;
import com.pzj.framework.idgen.IDGenerater;

@Component(value = "operLogEngine")
public class OperLogEngine {

	@Autowired
	private IDGenerater iDGenerater;

	@Autowired
	private OperLogWMapper operLogWMapper;

	public void generateLog(OperLogEntity entity) {
		entity.setLogId(iDGenerater.nextId());
		operLogWMapper.insert(entity);

	}

	public void generateLog(OperLogEntity entity, int prev, Object obj) {
		entity.setPrev(prev);
		entity.setLogId(iDGenerater.nextId());
		entity.setContext(entity.getContext());
		operLogWMapper.insert(entity);

	}

}
