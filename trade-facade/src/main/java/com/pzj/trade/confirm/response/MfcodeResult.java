package com.pzj.trade.confirm.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.pzj.trade.confirm.model.MerchSimpleModel;

public class MfcodeResult implements Serializable {

    private static final long      serialVersionUID = 1L;

    /**
     * 核销码, 魔方码.
     */
    private String                 mfcode;
    /**
     * 订单ID.
     */
    private String                 orderId;
    /**
     * 供应商ID.
     */
    private long                   supplierId;
    /**
     * 码状态.
     */
    private int                    codeState;
    /**
     * 魔方码状态信息描述.
     */
    private String                 codeStateMsg;

    /**
     * 对应商品信息.
     */
    private List<MerchSimpleModel> merchs;
    /**
     * 魔方码有效期的开始时间.
     */
    private Date                   startTime;
    /**
     * 魔方码有效期的过期时间.
     */
    private Date                   endTime;
    /**
     * 魔方码创建时间.
     */
    private Date                   createTime;

    public String getMfcode() {
        return mfcode;
    }

    public void setMfcode(String mfcode) {
        this.mfcode = mfcode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public int getCodeState() {
        return codeState;
    }

    public void setCodeState(int codeState) {
        this.codeState = codeState;
    }

    public String getCodeStateMsg() {
        return codeStateMsg;
    }

    public void setCodeStateMsg(String codeStateMsg) {
        this.codeStateMsg = codeStateMsg;
    }

    public List<MerchSimpleModel> getMerchs() {
        return merchs;
    }

    public void setMerchs(List<MerchSimpleModel> merchs) {
        this.merchs = merchs;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
