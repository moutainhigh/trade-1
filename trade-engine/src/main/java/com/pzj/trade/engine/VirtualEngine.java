package com.pzj.trade.engine;

public interface VirtualEngine<M, N, T> {

    T execute(M m, N n);

    void onException(Throwable cause);
}
