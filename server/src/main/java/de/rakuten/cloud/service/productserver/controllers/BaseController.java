package de.rakuten.cloud.service.productserver.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import de.rakuten.cloud.service.productapi.exceptions.InvalidProductTypeException;
import de.rakuten.cloud.service.productserver.datatransferobjects.ErrorResponse;
import de.rakuten.cloud.service.productserver.exceptions.CategoryNotFoundException;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCategoryException;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCurrencyException;
import de.rakuten.cloud.service.productserver.exceptions.ProductNotFoundException;
import de.rakuten.cloud.service.productserver.exceptions.ProductServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class BaseController {

	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<ErrorResponse> categoryNotFoundException(CategoryNotFoundException exec,
			HttpServletRequest request, HttpServletResponse response) {
		ErrorResponse errorResponse = new ErrorResponse(new Date().toString(), HttpStatus.NOT_FOUND.name(),
				HttpStatus.NOT_FOUND.value(), exec.getMessage(), null, request.getRequestURI());

		log.warn(errorResponse.getMessage(), exec);

		return new ResponseEntity<>(errorResponse, new HttpHeaders(),
				HttpStatus.valueOf(errorResponse.getHttpStatusCode()));
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse> productNotFoundException(ProductNotFoundException exec,
			HttpServletRequest request, HttpServletResponse response) {
		ErrorResponse errorResponse = new ErrorResponse(new Date().toString(), HttpStatus.NOT_FOUND.name(),
				HttpStatus.NOT_FOUND.value(), exec.getMessage(), null, request.getRequestURI());

		log.warn(errorResponse.getMessage(), exec);

		return new ResponseEntity<>(errorResponse, new HttpHeaders(),
				HttpStatus.valueOf(errorResponse.getHttpStatusCode()));
	}

	@ExceptionHandler(InvalidProductTypeException.class)
	public ResponseEntity<ErrorResponse> invalidProductTypeException(InvalidProductTypeException exec,
			HttpServletRequest request, HttpServletResponse response) {
		ErrorResponse errorResponse = new ErrorResponse(new Date().toString(), HttpStatus.UNPROCESSABLE_ENTITY.name(),
				HttpStatus.UNPROCESSABLE_ENTITY.value(), exec.getMessage(), null, request.getRequestURI());

		log.warn(errorResponse.getMessage(), exec);

		return new ResponseEntity<>(errorResponse, new HttpHeaders(),
				HttpStatus.valueOf(errorResponse.getHttpStatusCode()));
	}

	@ExceptionHandler(InvalidCategoryException.class)
	public ResponseEntity<ErrorResponse> invalidCategoryException(InvalidCategoryException exec,
			HttpServletRequest request, HttpServletResponse response) {
		ErrorResponse errorResponse = new ErrorResponse(new Date().toString(), HttpStatus.UNPROCESSABLE_ENTITY.name(),
				HttpStatus.UNPROCESSABLE_ENTITY.value(), exec.getMessage(), null, request.getRequestURI());

		log.warn(errorResponse.getMessage(), exec);

		return new ResponseEntity<>(errorResponse, new HttpHeaders(),
				HttpStatus.valueOf(errorResponse.getHttpStatusCode()));
	}

	@ExceptionHandler(InvalidCurrencyException.class)
	public ResponseEntity<ErrorResponse> invalidCurrencyException(InvalidCurrencyException exec,
			HttpServletRequest request, HttpServletResponse response) {
		ErrorResponse errorResponse = new ErrorResponse(new Date().toString(), HttpStatus.UNPROCESSABLE_ENTITY.name(),
				HttpStatus.UNPROCESSABLE_ENTITY.value(), exec.getMessage(), null, request.getRequestURI());

		log.warn(errorResponse.getMessage(), exec);

		return new ResponseEntity<>(errorResponse, new HttpHeaders(),
				HttpStatus.valueOf(errorResponse.getHttpStatusCode()));
	}

	@ExceptionHandler(ProductServiceException.class)
	public ResponseEntity<ErrorResponse> productServiceException(ProductServiceException exec,
			HttpServletRequest request, HttpServletResponse response) {
		ErrorResponse errorResponse = new ErrorResponse(new Date().toString(), HttpStatus.UNPROCESSABLE_ENTITY.name(),
				HttpStatus.UNPROCESSABLE_ENTITY.value(), exec.getMessage(), null, request.getRequestURI());

		log.warn(errorResponse.getMessage(), exec);

		return new ResponseEntity<>(errorResponse, new HttpHeaders(),
				HttpStatus.valueOf(errorResponse.getHttpStatusCode()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> generic(Exception exec, HttpServletRequest request,
			HttpServletResponse response) {
		ErrorResponse errorResponse = new ErrorResponse(new Date().toString(), HttpStatus.BAD_REQUEST.name(),
				HttpStatus.BAD_REQUEST.value(), exec.getMessage(), null, request.getRequestURI());

		log.warn(errorResponse.getMessage(), exec);

		return new ResponseEntity<>(errorResponse, new HttpHeaders(),
				HttpStatus.valueOf(errorResponse.getHttpStatusCode()));
	}

}