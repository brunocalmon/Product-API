package de.rakuten.cloud.service.productapi.exceptions;

public class InvalidProductTypeException extends Exception {

	private static final long serialVersionUID = 3689197784645132447L;

	public InvalidProductTypeException(final String message) {
		super(message);
	}

	public InvalidProductTypeException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
