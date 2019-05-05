package de.rakuten.cloud.service.productserver.services;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.rakuten.cloud.service.productapi.domainobjects.ConvertedAmountDO;
import de.rakuten.cloud.service.productapi.domainobjects.ProductDO;
import de.rakuten.cloud.service.productapi.mappers.ProductAPIMapper;
import de.rakuten.cloud.service.productapi.persistents.ProductPersistent;
import de.rakuten.cloud.service.productserver.datatransferobjects.ProductResponse;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCategoryException;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCurrencyException;
import de.rakuten.cloud.service.productserver.exceptions.ProductServiceException;
import de.rakuten.cloud.service.productserver.mappers.ProductServerMapper;
import de.rakuten.cloud.service.productserver.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CurrencyConverterService currencyConverterService;

	@Override
	public ProductResponse createProduct(@NotNull final ProductDO productDO)
			throws ProductServiceException, InvalidCurrencyException, InvalidCategoryException {
		checkCategory(productDO.getCategoryId());
		final ProductDO convertedProduct = getConvertedProductAmount(productDO);

		@NotNull
		final ProductPersistent productPersistent = productRepository
				.save(ProductAPIMapper.productDomainToCreatePersistentMapper(convertedProduct));
		return ProductServerMapper.productPersistentToResponse(productPersistent);
	}

	private ProductDO getConvertedProductAmount(final ProductDO productDO) throws InvalidCurrencyException {
		if (isEURCurrency(productDO.getCurrency())) {
			return productDO;
		} else {
			final ConvertedAmountDO convertedAmount = currencyConverterService
					.getConvertedCurrencyAmount(productDO.getCurrency(), productDO.getAmount());
			return productDO.createConvertedProductToEuroCurrency(convertedAmount);
		}
	}

	private boolean isEURCurrency(final String currency) {
		return "EUR".equalsIgnoreCase(currency.trim());
	}

	private void checkCategory(final String categoryId) throws InvalidCategoryException {
		if (hasValidCategory(categoryId)) {
			return;
		}
		throw new InvalidCategoryException("Invalid Category Found: " + categoryId);
	}

	private boolean hasValidCategory(final String category) {
		return true;
	}
}
