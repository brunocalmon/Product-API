package de.rakuten.cloud.service.productserver.exceptions;

public class InvalidCurrencyException extends Exception {
	private static final long serialVersionUID = 3689197784645132447L;

	public InvalidCurrencyException (final String message) {
		super(message);
	}

	public InvalidCurrencyException (final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
