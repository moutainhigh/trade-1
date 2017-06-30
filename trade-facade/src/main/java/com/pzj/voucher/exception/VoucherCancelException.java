package com.pzj.voucher.exception;


/**
 * 凭证取消异常.
 * @author YRJ
 *
 */
public class VoucherCancelException extends VoucherException {

    private static final long serialVersionUID = 1L;

    public VoucherCancelException(String message) {
        super(message);
    }

    public VoucherCancelException(String message, Throwable cause) {
        super(message, cause);
    }
}
