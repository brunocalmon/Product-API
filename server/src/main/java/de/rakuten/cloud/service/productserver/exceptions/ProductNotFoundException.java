package de.rakuten.cloud.service.productserver.exceptions;

public class ProductNotFoundException extends Exception {

	private static final long serialVersionUID = 3689197784645132447L;

	public ProductNotFoundException(final String message) {
		super(message);
	}

	public ProductNotFoundException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
