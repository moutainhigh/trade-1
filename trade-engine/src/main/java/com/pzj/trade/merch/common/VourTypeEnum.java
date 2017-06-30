package com.pzj.trade.merch.common;

/**
 * 商品凭证类型.
 * @author YRJ
 *
 */
public enum VourTypeEnum {

    /**
     * 无凭证.
     */
    NUN(0, true),
    /**
     * 联系人信息.
     */
    CONTACT(1, false),
    /**
     * 魔方码.
     */
    MFCODE(2, true),
    /**
     * 游客身份证.
     */
    CARDID(3, true);

    /**
     * 凭证类型.
     */
    private int vourType;

    public int getVourType() {
        return vourType;
    }

    /**
     * 逾期忽略. 当商品不在游玩有效期内时, 不同的商品凭证决定商品是否可进行二次确认.
     */
    private boolean lateIgnore;

    public boolean isLateIgnore() {
        return lateIgnore;
    }

    private VourTypeEnum(int vourType, boolean lateIgnore) {
        this.vourType = vourType;
        this.lateIgnore = lateIgnore;
    }

    /**
     * 获取商品凭证.
     * @param vourType
     * @return
     */
    public static VourTypeEnum getVourType(int vourType) {
        for (VourTypeEnum vte : VourTypeEnum.values()) {
            if (vte.getVourType() == vourType) {
                return vte;
            }
        }
        throw new IllegalStateException("商品凭证类型错误.");
    }
}
