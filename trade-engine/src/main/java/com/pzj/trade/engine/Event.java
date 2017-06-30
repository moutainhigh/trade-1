package com.pzj.trade.engine;

public interface Event<T> {

    T doEvent();

    boolean illegalException();
}
