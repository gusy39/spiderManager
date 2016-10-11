package com.ecmoho.common.exception;

public class MapperNullException extends AppException {

	private static final long serialVersionUID = 829404633660395710L;

	public MapperNullException() {
	}

	public MapperNullException(String message) {
		super(message);
	}

	public MapperNullException(Throwable cause) {
		super(cause);
	}

	public MapperNullException(String message, Throwable cause) {
		super(message, cause);
	}

	public MapperNullException(String message, Object... param) {
		super(message, param);
	}

	public MapperNullException(String message, Throwable cause, Object... param) {
		super(message, cause, param);
	}
}
