package com.pzj.trade.confirm.model;

import java.io.Serializable;

/**
 * 魔方码验证请求参数.
 * @author YRJ
 *
 */
public class ConfirmCodeModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 魔方码.
     */
    private String            code;

    /**
     * 供应商ID.
     */
    private long              supplierId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        StringBuilder tostr = new StringBuilder();
        tostr.append("[supplierId=").append(supplierId);
        tostr.append(", code=").append(code);
        tostr.append("]");
        return tostr.toString();
    }
}
