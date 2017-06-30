package com.pzj.trade.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.settlement.balance.request.PrePaymentVo;
import com.pzj.settlement.balance.service.AccountBussinessService;

public class TestBalanceRooback {
    public static void main(String[] args) {
    	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
        AccountBussinessService accountBussinessService = context.getBean(AccountBussinessService.class);
        PrePaymentVo prePaymentVo = new PrePaymentVo();
        prePaymentVo.setOrderId("MF882776639");
        prePaymentVo.setSignId("11e0dd5027174c6f8419a90ef9412467");
        prePaymentVo.setUserId(2216619741564289L);
        try {
            Result<Boolean> result = accountBussinessService.cancelRefund(prePaymentVo);
            System.out.println(JSONConverter.toJson(result));
        } catch (Throwable e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
        	context.close();
        }

    }
}
