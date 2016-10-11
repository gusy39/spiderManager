package com.ecmoho.common.exception;

/**
 * Created by Administrator on 2015/6/25.
 */
public class ServiceException extends AppException {

    public ServiceException() {
        super();
    }

    public ServiceException(String s) {
        super(s);
    }

    public ServiceException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ServiceException(Throwable throwable) {
        super(throwable);
    }

    protected ServiceException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }

    public ServiceException(String message, Throwable cause, Object... param) {
        super(message, cause, param);
    }

    public ServiceException(String message, Object... param) {
        super(message, param);
    }
}
