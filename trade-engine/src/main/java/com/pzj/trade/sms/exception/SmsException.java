package com.pzj.trade.sms.exception;

import com.pzj.framework.exception.ServiceException;

public class SmsException extends ServiceException {
    /**  */
    private static final long serialVersionUID = -9047497939681993673L;


    public SmsException(String error) {
        super(error);
    }

    public SmsException(String error, Throwable cause) {
        super(error, cause);
    }
}
