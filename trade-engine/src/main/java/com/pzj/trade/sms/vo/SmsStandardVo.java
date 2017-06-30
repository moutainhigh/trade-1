package com.pzj.trade.sms.vo;

import java.io.Serializable;

public class SmsStandardVo implements Serializable{

    /**  */
    private static final long serialVersionUID = -848246711612523454L;

    /** 规格名称 */
    private String standardName;
    /**  规格数量*/
    private int standardNum;
    
    public int getStandardNum() {
        return standardNum;
    }
    public void setStandardNum(int standardNum) {
        this.standardNum = standardNum;
    }
    public String getStandardName() {
        return standardName;
    }
    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }
}
