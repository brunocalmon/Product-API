package de.rakuten.cloud.service.productserver.services;

import javax.validation.constraints.NotNull;

import de.rakuten.cloud.service.productapi.domainobjects.ProductDO;
import de.rakuten.cloud.service.productserver.datatransferobjects.ProductResponse;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCategoryException;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCurrencyException;
import de.rakuten.cloud.service.productserver.exceptions.ProductServiceException;

public interface ProductService {

	@NotNull
	public ProductResponse createProduct(@NotNull final ProductDO productDO)
			throws ProductServiceException, InvalidCurrencyException, InvalidCategoryException;

}
