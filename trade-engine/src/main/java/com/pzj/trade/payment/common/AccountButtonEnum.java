package com.pzj.trade.payment.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 帐扣项
 * @author kangzl
 * @version $Id: Account_Button.java, v 0.1 2016年5月20日 上午11:10:09 Administrator Exp $
 */
public enum AccountButtonEnum {
    /**
     * 返利
     */
    rebate(20, 1, 1),

    /**
     * 预付
     */
    balance(19, 2, 4),

    /**
     * 授信
     */
    credit(31, 3, 3);

    private AccountButtonEnum(long value, int order, int button) {
        this.value = value;
        this.order = order;
        this.button = button;
    }

    private long value;

    /**
     * 付款顺序优先级.
     */
    private int  order;

    /**
     * 帐扣项
     */
    private int  button;

    public long getValue() {
        return value;
    }

    public int getOrder() {
        return order;
    }

    public int getButton() {
        return button;
    }

    public static List<Long> getDictIds() {
        List<Long> dictIds = new ArrayList<Long>();
        for (AccountButtonEnum e : AccountButtonEnum.values()) {
            dictIds.add(e.getValue());
        }
        return dictIds;
    }

    public static AccountButtonEnum getAccountButton(long value) {
        for (AccountButtonEnum ab : AccountButtonEnum.values()) {
            if (ab.getValue() == value) {
                return ab;
            }
        }
        return null;
    }

    public static boolean containPaymentAccount(int account_button) {
        for (AccountButtonEnum ab : AccountButtonEnum.values()) {
            if (ab.getButton() == account_button) {
                return true;
            }
        }
        return false;
    }
}
