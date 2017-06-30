package com.pzj.core.trade.pay;


import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.trade.payment.service.ThirdPartyRefundCabackService;
import com.pzj.trade.refund.model.ThirdPayWithdrawRspModel;
import com.pzj.trade.withdraw.model.CashPostalModel;
import com.pzj.trade.withdraw.service.CashPostalService;

public class DrawingMoneyTest {
    static ApplicationContext context = null;

    @BeforeClass
    public static void setUpClass() {
        context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
        System.out.println(context);
    }

    private CashPostalService cashPostalService;
    private ThirdPartyRefundCabackService thirdPartyRefundCabackService;

    @Before
    public void setUp() {
        cashPostalService = context.getBean("cashPostalService", CashPostalService.class);
        thirdPartyRefundCabackService=context.getBean(ThirdPartyRefundCabackService.class);
    }

    @Test
    public void testDrawingMoney() {
//        ServiceContext serviceContext = new ServiceContext();
//        serviceContext.setTime(new Date());
//        OperationEnv env = new OperationEnv();
//        env.setClientId("192.168.96.14");
//        env.setClientId("unit-test");
//        serviceContext.setOperationEnv(env);

        CashPostalModel model = new CashPostalModel();
        model.setAccountId(3010985225289729L);
        model.setCashPostalMoney(1.0);
        model.setResellerType(0);
        model.setUserType(2);

        try {
            Result<Boolean> result = cashPostalService.cashPostal(model, null);
            Assert.assertEquals(result.getErrorCode(), 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testDrawingCallback(){
        ThirdPayWithdrawRspModel model=new ThirdPayWithdrawRspModel();
        model.setWithdrawId(574L);
        model.setRspResult(true);
        try {
            Result<Boolean> result=thirdPartyRefundCabackService.thirdWithdrawCallback(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
