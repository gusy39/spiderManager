package com.ecmoho.common.exception;

public class PageException extends AppException {

	private static final long serialVersionUID = 829404633660395710L;

	public PageException() {
	}

	public PageException(String message) {
		super(message);
	}

	public PageException(Throwable cause) {
		super(cause);
	}

	public PageException(String message, Throwable cause) {
		super(message, cause);
	}

	public PageException(String message, Throwable cause, Object... param) {
		super(message, cause, param);
	}

	public PageException(String message, Object... param) {
		super(message, param);
	}
}
