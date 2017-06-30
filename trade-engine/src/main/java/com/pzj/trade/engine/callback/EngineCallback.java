package com.pzj.trade.engine.callback;

/**
 * 执行引擎回调.
 * @author YRJ
 *
 * @param <X>
 * @param <T>
 */
public interface EngineCallback<X, T> {

    /**
     * 回调.
     * @param x
     * @return
     */
    T callback(X x);
}
