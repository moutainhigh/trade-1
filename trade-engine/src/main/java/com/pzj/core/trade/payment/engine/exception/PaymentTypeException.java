package com.pzj.core.trade.payment.engine.exception;


/**
 * 支付方式异常.
 * @author YRJ
 *
 */
public class PaymentTypeException extends PaymentException {

    private static final long serialVersionUID = 1L;

    public PaymentTypeException(String message) {
        super(message);
    }

    public PaymentTypeException(String message, Throwable cause) {
        super(message, cause);
    }

}
