package de.rakuten.cloud.service.productserver.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.rakuten.cloud.service.productapi.exceptions.InvalidProductTypeException;
import de.rakuten.cloud.service.productserver.datatransferobjects.ProductRequest;
import de.rakuten.cloud.service.productserver.datatransferobjects.ProductResponse;
import de.rakuten.cloud.service.productserver.datatransferobjects.UpdateProductRequest;
import de.rakuten.cloud.service.productserver.exceptions.CategoryNotFoundException;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCategoryException;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCurrencyException;
import de.rakuten.cloud.service.productserver.exceptions.ProductNotFoundException;
import de.rakuten.cloud.service.productserver.exceptions.ProductServiceException;
import de.rakuten.cloud.service.productserver.mappers.ProductServerMapper;
import de.rakuten.cloud.service.productserver.services.ProductService;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductServiceController extends BaseController {

	@Autowired
	ProductService productService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ProductResponse createProduct(@Valid @RequestBody final ProductRequest productRequest)
			throws ProductServiceException, InvalidProductTypeException, InvalidCurrencyException,
			InvalidCategoryException {
		return productService.createProduct(ProductServerMapper.productRequestToDomainMapper(productRequest));
	}

	@RequestMapping(path = "/{productId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ProductResponse createProduct(@PathVariable(name = "productId", required = true) final String productId)
			throws ProductNotFoundException {
		return productService.findProductById(productId);
	}

	@RequestMapping(path = "/{productId}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public ProductResponse updateProduct(@PathVariable(name = "productId", required = true) final String productId,
			@Valid @RequestBody final UpdateProductRequest productRequest) throws ProductNotFoundException,
			InvalidProductTypeException, InvalidCategoryException, InvalidCurrencyException {
		return productService
				.updateProduct(ProductServerMapper.productUpdateRequestToDomainMapper(productId, productRequest));
	}

	@RequestMapping(path = "/{productId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteProduct(@PathVariable(name = "productId", required = true) final String productId)
			throws ProductNotFoundException {
		productService.findProductById(productId);
	}

	@RequestMapping(path = "/{productId}/category", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteProductCategory(@PathVariable(name = "productId", required = true) final String productId)
			throws ProductNotFoundException, CategoryNotFoundException {
		productService.deleteProductCategory(productId);
	}
}
