package com.pzj.trade.withdraw.nv;

/**
 * 名值对.
 * @author YRJ
 *
 * @param <X>
 * @param <Y>
 */
public class NameValue<X, Y> {

    private X name;
    private Y value;

    public NameValue(X name, Y value) {
        this.name = name;
        this.value = value;
    }

    public X getName() {
        return name;
    }

    public Y getValue() {
        return value;
    }
}
