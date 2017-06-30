package com.pzj.trade.mq;

import java.util.HashMap;
import java.util.Map;

import com.pzj.framework.converter.JSONConverter;

public class DockOtaMQMSgConverter {
    /**
     * ota对接系统商品核销后，发送的短信内容
     * @param orderId
     * @param checkMerchNum
     * @return
     */
    public static String getConsumerMerchMsg(final String orderId,final int checkMerchNum){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("orderId", orderId);
        map.put("checkedNum", checkMerchNum);
        return JSONConverter.toJson(map).toString();
    }
    
    /**
     * ota对接系统，退款审核后向ota发送的消息
     * @param orderId
     * @param flag
     * @return
     */
    public static String getRefundOrderMsg(String orderId,boolean flag){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("orderId", orderId);
        map.put("result", flag);
        return JSONConverter.toJson(map).toString();
    }
}
