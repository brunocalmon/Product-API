package de.rakuten.cloud.service.productapi.exceptions;

public class ProductServiceException extends Exception {

	private static final long serialVersionUID = 3689197784645132447L;

	public ProductServiceException(final String message) {
		super(message);
	}

	public ProductServiceException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
