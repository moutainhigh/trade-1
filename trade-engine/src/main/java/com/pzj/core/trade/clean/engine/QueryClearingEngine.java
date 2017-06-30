package com.pzj.core.trade.clean.engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.trade.clearing.model.UnClearingRequestModel;
import com.pzj.trade.clearing.response.UnClearingRelationResponse;
import com.pzj.trade.merch.entity.MerchCleanRelationEntity;
import com.pzj.trade.merch.write.MerchCleanRelationWriteMapper;

@Component(value = "queryClearingEngine")
public class QueryClearingEngine {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(QueryClearingEngine.class);

    @Autowired
    private MerchCleanRelationWriteMapper merchCleanRelationWriteMapper;

    public ArrayList<UnClearingRelationResponse> queryClearingEngine(UnClearingRequestModel clearModel) {
        List<MerchCleanRelationEntity> relations = merchCleanRelationWriteMapper.queryUnClearRecordByPager(new Date().getTime(), clearModel.getPageIndex(),
            clearModel.getPageSize());
        logger.info("查询待清算{}条", relations.size());
        ArrayList<UnClearingRelationResponse> response = convert(relations);

        return response;
    }

    private ArrayList<UnClearingRelationResponse> convert(List<MerchCleanRelationEntity> relations) {
        ArrayList<UnClearingRelationResponse> result = new ArrayList<UnClearingRelationResponse>();

        if (relations == null) {
            return result;
        }

        for (MerchCleanRelationEntity relation : relations) {
            UnClearingRelationResponse resp = new UnClearingRelationResponse();
            resp.setCleanId(relation.getClean_id());
            resp.setCleanState(relation.getClean_state());
            resp.setCleanType(relation.getClean_type());
            resp.setEffecTime(relation.getEffec_time());
            resp.setIsManual(relation.getIs_manual());
            resp.setMerchId(relation.getMerch_id());
            resp.setOrderId(relation.getOrder_id());
            result.add(resp);
        }
        return result;
    }
}
