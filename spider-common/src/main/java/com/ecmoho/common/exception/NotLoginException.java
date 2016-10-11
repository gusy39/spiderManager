package com.ecmoho.common.exception;

/**
 * Created by Administrator on 2015/6/25.
 */
public class NotLoginException extends AppException {
    public NotLoginException() {
        super();
    }

    public NotLoginException(String s) {
        super(s);
    }

    public NotLoginException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NotLoginException(Throwable throwable) {
        super(throwable);
    }

    protected NotLoginException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }

    public NotLoginException(String message, Throwable cause, Object... param) {
        super(message, cause, param);
    }

    public NotLoginException(String message, Object... param) {
        super(message, param);
    }
}
