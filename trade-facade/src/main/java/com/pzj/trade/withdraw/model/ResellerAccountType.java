package com.pzj.trade.withdraw.model;

/**
 * 分销商账户类型.
 * @author YRJ
 *
 */
public class ResellerAccountType {

    /** 账户类型 */
    private int                             type;

    /** 账户类型名称. */
    private String                          name;

    /**
     * 二维码账户类型.
     */
    public final static ResellerAccountType QR_CODE = new ResellerAccountType(1, "二维码");

    public ResellerAccountType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder tostr = new StringBuilder();
        tostr.append("[type: ").append(type);
        tostr.append(", name: ").append(name);
        return tostr.toString();
    }
}
