package com.pzj.voucher.common;

import com.pzj.voucher.exception.VoucherException;

/**
 * 核销状态
 * @author YRJ
 *
 */
public enum VoucherStateEnum {

    //凭证状态(-2:作废; -1:不可用；0：可以使用；1核销完毕；2：退单)
    OBSOLETE(-2, "取消作废", false),
    /** 不可用 */
    NOTAVAILABLE(-1, "不可用", false),
    /** 可以使用 */
    AVAILABLE(0, "可以使用", true),
    /** 核销完毕 */
    VERIFICATION(1, "核销完毕", false),
    /** 退单 */
    REFUND(2, "退单", false);

    /**
     * 凭证状态值.
     */
    private int     value;

    private String  name;
    /**
     * 是否可进行核销.
     */
    private boolean confirmable;

    VoucherStateEnum(int value, String name, boolean confirmable) {
        this.value = value;
        this.name = name;
        this.confirmable = confirmable;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public boolean isConfirmable() {
        return confirmable;
    }

    /**
     * 根据指定的状态值获取对应的凭证状态.
     * @param state
     * @return
     */
    public static VoucherStateEnum getVoucherStatus(int state) {
        for (VoucherStateEnum vs : VoucherStateEnum.values()) {
            if (vs.getValue() == state) {
                return vs;
            }
        }
        throw new VoucherException("凭证状态错误");
    }
}
