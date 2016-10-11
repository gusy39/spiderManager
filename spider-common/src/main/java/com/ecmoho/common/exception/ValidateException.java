package com.ecmoho.common.exception;

/**
 * Created by Administrator on 2015/6/25.
 */
public class ValidateException extends AppException {
    public ValidateException() {
        super();
    }

    public ValidateException(String s) {
        super(s);
    }

    public ValidateException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ValidateException(Throwable throwable) {
        super(throwable);
    }

    protected ValidateException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }

    public ValidateException(String message, Throwable cause, Object... param) {
        super(message, cause, param);
    }

    public ValidateException(String message, Object... param) {
        super(message, param);
    }
}
