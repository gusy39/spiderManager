package com.ecmoho.common.exception;

/**
 * Created by Administrator on 2015/6/25.
 */
public class BeanNullException extends AppException {
    public BeanNullException() {
        super();
    }

    public BeanNullException(String s) {
        super(s);
    }

    public BeanNullException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BeanNullException(Throwable throwable) {
        super(throwable);
    }

    protected BeanNullException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }

    public BeanNullException(String message, Throwable cause, Object... param) {
        super(message, cause, param);
    }

    public BeanNullException(String message, Object... param) {
        super(message, param);
    }
}
