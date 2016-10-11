package com.ecmoho.common.exception;

import java.text.MessageFormat;

public class AppException extends RuntimeException {

    private static final long serialVersionUID = 829404633660395710L;

    public AppException() {
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }


    public AppException(String message, Object... param) {
        super(MessageFormat.format(message, param));
    }

    public AppException(String message, Throwable cause, Object... param) {
        super(MessageFormat.format(message, param, cause));
    }

}
