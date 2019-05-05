package de.rakuten.cloud.service.productserver.services;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import de.rakuten.cloud.service.productapi.domainobjects.ConvertedAmountDO;
import de.rakuten.cloud.service.productapi.domainobjects.ProductDO;
import de.rakuten.cloud.service.productapi.mappers.ProductAPIMapper;
import de.rakuten.cloud.service.productapi.persistents.ProductPersistent;
import de.rakuten.cloud.service.productserver.datatransferobjects.ProductResponse;
import de.rakuten.cloud.service.productserver.exceptions.CategoryNotFoundException;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCategoryException;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCurrencyException;
import de.rakuten.cloud.service.productserver.exceptions.ProductNotFoundException;
import de.rakuten.cloud.service.productserver.exceptions.ProductServiceException;
import de.rakuten.cloud.service.productserver.mappers.ProductServerMapper;
import de.rakuten.cloud.service.productserver.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CurrencyConverterService currencyConverterService;

	@Override
	public @NotNull ProductResponse createProduct(@NotNull final ProductDO productDO)
			throws ProductServiceException, InvalidCurrencyException, InvalidCategoryException {
		checkCategory(productDO.getCategoryId());
		final ProductDO convertedProduct = getConvertedProductAmount(productDO);

		final ProductPersistent productPersistent = productRepository
				.save(ProductAPIMapper.productDomainToCreatePersistentMapper(convertedProduct));
		return ProductServerMapper.productPersistentToResponse(productPersistent);
	}

	@Override
	public @NotNull ProductResponse findProductById(@NotNull String productId) throws ProductNotFoundException {
		ProductPersistent persisted = findById(productId);
		return ProductServerMapper.productPersistentToResponse(persisted);
	}

	@Override
	public @NotNull ProductResponse updateProduct(@NotNull final ProductDO updateProductDO)
			throws InvalidCategoryException, InvalidCurrencyException, ProductNotFoundException {
		checkCategory(updateProductDO.getCategoryId());
		final ProductPersistent oldProduct = findById(updateProductDO.getId());
		final ProductDO convertedProduct = getConvertedProductAmount(updateProductDO);

		final ProductPersistent productPersistent = productRepository
				.save(ProductAPIMapper.productDomainToUpdatePersistentMapper(oldProduct, convertedProduct));

		return ProductServerMapper.productPersistentToResponse(productPersistent);
	}

	@Override
	public @NotNull void deleteProductById(@NotNull String productId) throws ProductNotFoundException {
		deleteById(productId);
	}

	@Override
	public @NotNull void deleteProductCategory(@NotNull String productId)
			throws ProductNotFoundException, CategoryNotFoundException {
		ProductPersistent persistent = findById(productId);
		if (hasCategory(persistent)) {
			productRepository.save(persistWithoutCategoty(persistent));
		}
		throw new CategoryNotFoundException("No Category founded");

	}

	private ProductPersistent persistWithoutCategoty(ProductPersistent persistent) {
		return ProductPersistent.builder().amount(persistent.getAmount()).createdAt(persistent.getCreatedAt())
				.currency(persistent.getCurrency()).id(persistent.getId()).lastUpdated(new Date())
				.name(persistent.getName()).productType(persistent.getProductType()).build();
	}

	private boolean hasCategory(ProductPersistent persistent) {
		return !StringUtils.isEmpty(persistent.getCategoryId());
	}

	private void deleteById(String productId) throws ProductNotFoundException {
		findById(productId);
		productRepository.deleteById(productId);
	}

	private ProductPersistent findById(String productId) throws ProductNotFoundException {
		return productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException("Product <" + productId + "> not found"));
	}

	private ProductDO getConvertedProductAmount(final ProductDO productDO) throws InvalidCurrencyException {
		if (isUpdateble(productDO) || isEURCurrency(productDO.getCurrency())) {
			return productDO;
		} else {
			final ConvertedAmountDO convertedAmount = currencyConverterService
					.getConvertedCurrencyAmount(productDO.getCurrency(), productDO.getAmount());
			return productDO.createConvertedProductToEuroCurrency(convertedAmount);
		}
	}

	private boolean isUpdateble(ProductDO productDO) {
		return !StringUtils.isEmpty(productDO.getId()) && hasCorrectPriceFields(productDO);
	}

	private boolean hasCorrectPriceFields(ProductDO productDO) {
		if (isMoneyUpdate(productDO)) {
			return true;
		}
		throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "You must fill both amount and currency to update");

	}

	private boolean isMoneyUpdate(ProductDO productDO) {
		return !StringUtils.isEmpty(productDO.getCurrency()) && !StringUtils.isEmpty(productDO.getAmount())
				|| StringUtils.isEmpty(productDO.getCurrency()) && StringUtils.isEmpty(productDO.getAmount());
	}

	private boolean isEURCurrency(final String currency) {
		return "EUR".equalsIgnoreCase(currency.trim());
	}

	private void checkCategory(final String categoryId) throws InvalidCategoryException {
		if (StringUtils.isEmpty(categoryId) || hasValidCategory(categoryId)) {
			return;
		}
		throw new InvalidCategoryException("Invalid Category Found: " + categoryId);
	}

	private boolean hasValidCategory(final String categoryId) {
		return categoryService.exists(categoryId);
	}
}
