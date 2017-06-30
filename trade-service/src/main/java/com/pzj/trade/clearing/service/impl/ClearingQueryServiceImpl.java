package com.pzj.trade.clearing.service.impl;

import java.util.ArrayList;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.clean.engine.QueryClearingEngine;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.clearing.model.UnClearingRequestModel;
import com.pzj.trade.clearing.response.UnClearingRelationResponse;
import com.pzj.trade.clearing.service.ClearingQueryService;

@Service(value = "clearingQueryService")
public class ClearingQueryServiceImpl implements ClearingQueryService {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ClearingQueryServiceImpl.class);

    @Autowired
    private QueryClearingEngine           queryClearingEngine;

    @Override
    public Result<ArrayList<UnClearingRelationResponse>> queryUnClearingRecordByPager(UnClearingRequestModel clearModel, ServiceContext serviceContext) {

        logger.info("clearModel: {}, serviceContext: {}.", clearModel, serviceContext);

        ArrayList<UnClearingRelationResponse> relations = null;
        try {
            relations = queryClearingEngine.queryClearingEngine(clearModel);
        } catch (Throwable e) {
            logger.error("clearModel: " + clearModel + ", serviceContext: " + serviceContext, e);
        }
        return new Result<ArrayList<UnClearingRelationResponse>>(relations);
    }

}
