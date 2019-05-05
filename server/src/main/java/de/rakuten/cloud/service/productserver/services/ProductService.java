package de.rakuten.cloud.service.productserver.services;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import de.rakuten.cloud.service.productapi.domainobjects.ProductDO;
import de.rakuten.cloud.service.productserver.datatransferobjects.ProductResponse;
import de.rakuten.cloud.service.productserver.exceptions.CategoryNotFoundException;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCategoryException;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCurrencyException;
import de.rakuten.cloud.service.productserver.exceptions.ProductNotFoundException;
import de.rakuten.cloud.service.productserver.exceptions.ProductServiceException;

public interface ProductService {

	@NotNull
	public ProductResponse createProduct(@NotNull final ProductDO productDO)
			throws ProductServiceException, InvalidCurrencyException, InvalidCategoryException;

	@NotNull
	public ProductResponse findProductById(@NotNull final String productId) throws ProductNotFoundException;

	@NotNull
	public ProductResponse updateProduct(@Valid final ProductDO productRequest)
			throws InvalidCategoryException, InvalidCurrencyException, ProductNotFoundException;

	@NotNull
	public void deleteProductById(@NotNull final String productId) throws ProductNotFoundException;

	@NotNull
	public void deleteProductCategory(@NotNull final String productId) throws ProductNotFoundException, CategoryNotFoundException;

}
