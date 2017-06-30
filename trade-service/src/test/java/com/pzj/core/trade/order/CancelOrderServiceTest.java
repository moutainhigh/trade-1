package com.pzj.core.trade.order;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.order.model.OrderCancelModel;
import com.pzj.trade.order.service.CancelService;

public class CancelOrderServiceTest {

    static ApplicationContext context = null;

    @BeforeClass
    public static void setUpClass() {
        context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
    }

    private CancelService cancelService;

    @Before
    public void setUp() {
        cancelService = context.getBean(CancelService.class);
    }

    @Test
    public void testCancelOrderStatusOnPending_Pay() {
        OrderCancelModel cancelModel = new OrderCancelModel();
        cancelModel.setOrderId("MF1931450075");

        ServiceContext serviceContext = ServiceContext.getServiceContext();

        Result<Boolean> result = cancelService.cancelOrder(cancelModel, serviceContext);
        Assert.assertNotNull(result);
    }
}
