package com.pzj.trade.confirm.model;

import java.io.Serializable;

/**
 * 根据魔方码的铸剑ID获取魔方码基本信息参数.
 * @author YRJ
 *
 */
public class MfCodeModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private long              codeId;

    public long getCodeId() {
        return codeId;
    }

    public void setCodeId(long codeId) {
        this.codeId = codeId;
    }

    @Override
    public String toString() {
        StringBuilder tostr = new StringBuilder();
        tostr.append("[codeId=").append(codeId);
        tostr.append("]");
        return tostr.toString();
    }
}
