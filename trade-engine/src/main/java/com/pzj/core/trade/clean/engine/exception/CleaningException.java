package com.pzj.core.trade.clean.engine.exception;

import com.pzj.framework.exception.ServiceException;

/**
 * 清算异常基类.
 * @author YRJ
 *
 */
public class CleaningException extends ServiceException {

    private static final long serialVersionUID = 1L;

    public CleaningException(String message) {
        super(message);
    }

    public CleaningException(String message, Throwable cause) {
        super(message, cause);
    }
}
