package com.pzj.trade.clear;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.clearing.model.CleaningModel;
import com.pzj.trade.clearing.service.ClearingService;

public class ClearingServiceTest {

    static ApplicationContext context;

    private ClearingService   clearingService;

    @BeforeClass
    public static void setUpClass() {
        context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
    }

    @Before
    public void setUp() {
        clearingService = context.getBean(ClearingService.class);
    }

    @Test
    public void testValidate() {
        CleaningModel cleaningModel = new CleaningModel();
        cleaningModel.setOrderId("MF1603354115");
        cleaningModel.setMerchId("P699159331");

        Result<Boolean> result = clearingService.clean(cleaningModel, ServiceContext.getServiceContext());
        Assert.assertNotNull(result);
        System.out.println(ToStringBuilder.reflectionToString(result, ToStringStyle.SHORT_PREFIX_STYLE));
    }

}
