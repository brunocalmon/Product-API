package de.rakuten.cloud.service.productserver.services;

import javax.validation.constraints.NotNull;

import de.rakuten.cloud.service.productapi.domainobjects.ProductDO;
import de.rakuten.cloud.service.productserver.exceptions.ProductServiceException;

public interface ProductService {

	@NotNull
	public void createProduct(@NotNull final ProductDO productDO) throws ProductServiceException;

}
