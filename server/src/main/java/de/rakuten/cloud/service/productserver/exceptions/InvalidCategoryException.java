package de.rakuten.cloud.service.productserver.exceptions;

public class InvalidCategoryException extends Exception {
	private static final long serialVersionUID = 3689197784645132447L;

	public InvalidCategoryException (final String message) {
		super(message);
	}

	public InvalidCategoryException (final String message, final Throwable throwable) {
		super(message, throwable);
	}

	public InvalidCategoryException() {
		super();
	}
}
