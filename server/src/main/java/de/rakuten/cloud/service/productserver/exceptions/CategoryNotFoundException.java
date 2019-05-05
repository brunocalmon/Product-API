package de.rakuten.cloud.service.productserver.exceptions;

public class CategoryNotFoundException extends Exception {

	private static final long serialVersionUID = 3689197784645132447L;

	public CategoryNotFoundException(final String message) {
		super(message);
	}

	public CategoryNotFoundException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
