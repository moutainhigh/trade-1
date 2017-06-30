package com.pzj.trade.sms.vo;

import java.io.Serializable;

public class MfourCodeVo implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;

    /** 魔方码 */
    private String mfourCode;
    
    /** 魔方码连接 */
    private String mfourCodeUrl;

    public String getMfourCode() {
        return mfourCode;
    }

    public void setMfourCode(String mfourCode) {
        this.mfourCode = mfourCode;
    }

    public String getMfourCodeUrl() {
        return mfourCodeUrl;
    }

    public void setMfourCodeUrl(String mfourCodeUrl) {
        this.mfourCodeUrl = mfourCodeUrl;
    }
}
