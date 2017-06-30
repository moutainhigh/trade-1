package com.pzj.trade.sms.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pzj.framework.toolkit.Check;

public class SmsVo implements Serializable {
    /**  */
    private static final long   serialVersionUID = 3763331473214375528L;

    /** 产品名称 */
    private String              productName;
    /** 魔方联系电话 */
    private String              mfourPhone       = "400-880-8989";

    /**  魔方码集合*/
    private List<MfourCodeVo>   mftourcodelist   = new ArrayList<MfourCodeVo>();

    /** 快递公司名称 */
    private String              expressName;

    /**  产品类型（票，演艺，通用产品）*/
    private int                 category;

    /** 快递单号 */
    private String              expressCode;

    /** 出游日期 */
    private String              travelDate;

    /**  订单的商品规格信息*/
    private List<SmsStandardVo> standardlist     = new ArrayList<SmsStandardVo>();

    /**  订单ID*/
    private String              orderId;

    /**  订单详情连接*/
    private String              orderUrl;

    /**  订单金额*/
    private double              orderAmount;

    /**  订单联系人附属信息*/
    private SmsContacteeVo      smsContactee     = new SmsContacteeVo();

    /** 订单的分销商附属信息 */
    private SmsResellerVo       smsReseller      = new SmsResellerVo();

    /** 订单供应商附属信息 */
    private SmsSupplierVo       smsSupplier      = new SmsSupplierVo();
    
    /**
     * 商品的配送信息
     */
    private SmsDeliveryVo       smsDelivery     =new SmsDeliveryVo();

    /** 发送短信触发条件类型 */
    private int                 triggerType;

    /**
     * 订单填单项信息
     */
    private Set<String>         orderAttrMsg     = new HashSet<String>();

    public String getProductName() {
        if (!Check.NuNStrStrict(productName) && productName.length() > 25) {
            productName = productName.substring(0, 23) + "...";
        }
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMfourPhone() {
        return mfourPhone;
    }

    public void setMfourPhone(String mfourPhone) {
        this.mfourPhone = mfourPhone;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderUrl() {
        return orderUrl;
    }

    public void setOrderUrl(String orderUrl) {
        this.orderUrl = orderUrl;
    }

    public List<SmsStandardVo> getStandardlist() {
        return standardlist;
    }

    public void setStandardlist(List<SmsStandardVo> standardlist) {
        this.standardlist = standardlist;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public List<MfourCodeVo> getMftourcodelist() {
        return mftourcodelist;
    }

    public void setMftourcodelist(List<MfourCodeVo> mftourcodelist) {
        this.mftourcodelist = mftourcodelist;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(int triggerType) {
        this.triggerType = triggerType;
    }

    public SmsContacteeVo getSmsContactee() {
        return smsContactee;
    }

    public void setSmsContactee(SmsContacteeVo smsContactee) {
        this.smsContactee = smsContactee;
    }

    public SmsResellerVo getSmsReseller() {
        return smsReseller;
    }

    public void setSmsReseller(SmsResellerVo smsReseller) {
        this.smsReseller = smsReseller;
    }

    public SmsSupplierVo getSmsSupplier() {
        return smsSupplier;
    }

    public void setSmsSupplier(SmsSupplierVo smsSupplier) {
        this.smsSupplier = smsSupplier;
    }

    public Set<String> getOrderAttrMsg() {
        return orderAttrMsg;
    }

    public void setOrderAttrMsg(Set<String> orderAttrMsg) {
        this.orderAttrMsg = orderAttrMsg;
    }

    public SmsDeliveryVo getSmsDelivery() {
        return smsDelivery;
    }

    public void setSmsDelivery(SmsDeliveryVo smsDelivery) {
        this.smsDelivery = smsDelivery;
    }
}
