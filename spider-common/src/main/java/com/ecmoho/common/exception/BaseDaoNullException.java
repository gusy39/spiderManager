package com.ecmoho.common.exception;

public class BaseDaoNullException extends AppException {

    private static final long serialVersionUID = 829404633660395710L;

    public BaseDaoNullException() {
    }

    public BaseDaoNullException(String message) {
        super(message);
    }

    public BaseDaoNullException(Throwable cause) {
        super(cause);
    }

    public BaseDaoNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseDaoNullException(String message, Object... param) {
        super(message, param);
    }

    public BaseDaoNullException(String message, Throwable cause, Object... param) {
        super(message, cause, param);
    }

}
