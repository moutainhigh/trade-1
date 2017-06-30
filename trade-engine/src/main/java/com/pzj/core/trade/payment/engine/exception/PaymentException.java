package com.pzj.core.trade.payment.engine.exception;

import com.pzj.framework.exception.ServiceException;

/**
 * 支付方式错误.
 * @author YRJ
 *
 */
public class PaymentException extends ServiceException {

    private static final long serialVersionUID = 1L;

    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }

}
