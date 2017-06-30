package com.pzj.voucher.lineVo;

import java.io.Serializable;

/**
 * 特产产品凭据信息.
 * @author YRJ
 *
 */
public class SpecialityProductVoucherVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 收货人信息 */
    private String            address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
