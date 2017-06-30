package com.pzj.voucher.exception;

import com.pzj.framework.exception.ServiceException;

/**
 * 凭证异常基类.
 * @author YRJ
 *
 */
public class VoucherException extends ServiceException {

    private static final long serialVersionUID = 1L;

    public VoucherException(String message) {
        super(message);
    }

    public VoucherException(String message, Throwable cause) {
        super(message, cause);
    }

}
