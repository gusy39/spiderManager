package com.ecmoho.common.exception;

public class DaoException extends AppException {

	private static final long serialVersionUID = 829404633660395710L;

	public DaoException() {
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(String message, Object... param) {
		super(message, param);
	}

	public DaoException(String message, Throwable cause, Object... param) {
		super(message, cause, param);
	}
}
