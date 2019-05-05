package de.rakuten.cloud.service.productserver.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.apache.http.client.HttpResponseException;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.web.client.HttpClientErrorException;

import de.rakuten.cloud.service.productapi.exceptions.InvalidProductTypeException;
import de.rakuten.cloud.service.productserver.categories.IntegrationTest;
import de.rakuten.cloud.service.productserver.datatransferobjects.ProductResponse;
import de.rakuten.cloud.service.productserver.exceptions.CategoryNotFoundException;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCategoryException;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCurrencyException;
import de.rakuten.cloud.service.productserver.exceptions.ProductNotFoundException;
import de.rakuten.cloud.service.productserver.exceptions.ProductServiceException;

@Category(IntegrationTest.class)
public class ProductServiceImplTest extends SystemIntegrationTest {

	final BigDecimal amount = new BigDecimal(20);
	final String categoryId = "E10";
	final String currency = "BRL";
	final String name = "Action Toy";
	final String productType = "PHYSICAL";

	@Test
	public void test_create_product() throws InvalidCurrencyException, HttpResponseException, ProductServiceException,
			InvalidCategoryException, InvalidProductTypeException {
		final ProductResponse productResponse = productService
				.createProduct(createProduct(amount, categoryId, currency, name, productType));

		assertNotNull(productResponse.getId());
		assertNotNull(productResponse.getCreationDate());
		assertNull(productResponse.getUpdateDate());
		assertEquals(new BigDecimal("4.51"), productResponse.getAmount());
		assertEquals(categoryId, productResponse.getCategoryId());
		assertEquals("EUR", productResponse.getCurrency());

		deleteProduct(productResponse.getId());
	}

	@Test
	public void test_create_product_without_category() throws InvalidCurrencyException, HttpResponseException,
			ProductServiceException, InvalidCategoryException, InvalidProductTypeException {
		final ProductResponse productResponse = productService
				.createProduct(createProduct(amount, null, currency, name, productType));

		assertNotNull(productResponse.getId());
		assertNotNull(productResponse.getCreationDate());
		assertNull(productResponse.getUpdateDate());
		assertEquals(new BigDecimal("4.51"), productResponse.getAmount());
		assertEquals(null, productResponse.getCategoryId());
		assertEquals("EUR", productResponse.getCurrency());

		deleteProduct(productResponse.getId());
	}

	@Test(expected = InvalidCategoryException.class)
	public void test_create_product_with_invalid_category() throws InvalidCurrencyException, HttpResponseException,
			ProductServiceException, InvalidCategoryException, InvalidProductTypeException {
		productService.createProduct(createProduct(amount, "E00", currency, name, productType));
		fail();
	}

	@Test
	public void test_create_product_using_euro() throws InvalidCurrencyException, HttpResponseException,
			ProductServiceException, InvalidCategoryException, InvalidProductTypeException {
		final ProductResponse productResponse = productService
				.createProduct(createProduct(amount, null, "EUR", name, productType));

		assertNotNull(productResponse.getId());
		assertNotNull(productResponse.getCreationDate());
		assertNull(productResponse.getUpdateDate());
		assertEquals(new BigDecimal("20"), productResponse.getAmount());
		assertEquals(null, productResponse.getCategoryId());
		assertEquals("EUR", productResponse.getCurrency());

		deleteProduct(productResponse.getId());
	}

	@Test
	public void test_find_product_by_id() throws InvalidCurrencyException, HttpResponseException,
			ProductServiceException, InvalidCategoryException, InvalidProductTypeException, ProductNotFoundException {
		ProductResponse createdProduct = productService
				.createProduct(createProduct(amount, "H10", "EUR", name, productType));
		assertNotNull(createdProduct);

		ProductResponse product = productService.findProductById(createdProduct.getId());
		assertEquals(createdProduct.getId(), product.getId());

		deleteProduct(product.getId());
	}

	@Test(expected = ProductNotFoundException.class)
	public void test_find_inexistent_product_by_id() throws InvalidCurrencyException, HttpResponseException,
			ProductServiceException, InvalidCategoryException, InvalidProductTypeException, ProductNotFoundException {
		productService.findProductById("inexistent_product_id");
	}

	@Test
	public void test_update_product_name() throws ProductServiceException, InvalidCurrencyException,
			InvalidCategoryException, InvalidProductTypeException, ProductNotFoundException {
		final ProductResponse productResponse = productService
				.createProduct(createProduct(amount, categoryId, currency, name, productType));

		final ProductResponse updateProductResponse = productService
				.updateProduct(updateProduct(productResponse.getId(), null, null, null, "new-product", null));

		assertEquals(productResponse.getId(), updateProductResponse.getId());
		assertEquals(productResponse.getCreationDate(), updateProductResponse.getCreationDate());
		assertNotEquals(productResponse.getUpdateDate(), updateProductResponse.getUpdateDate());
		assertEquals(productResponse.getAmount(), updateProductResponse.getAmount());
		assertEquals(productResponse.getCategoryId(), updateProductResponse.getCategoryId());
		assertEquals(productResponse.getCurrency(), updateProductResponse.getCurrency());
		assertEquals(productResponse.getProductType(), updateProductResponse.getProductType());
		assertEquals(name, productResponse.getName());
		assertEquals("new-product", updateProductResponse.getName());

		deleteProduct(productResponse.getId());
	}

	@Test
	public void test_update_product_type() throws ProductServiceException, InvalidCurrencyException,
			InvalidCategoryException, InvalidProductTypeException, ProductNotFoundException {
		final ProductResponse productResponse = productService
				.createProduct(createProduct(amount, categoryId, currency, name, productType));

		final ProductResponse updateProductResponse = productService
				.updateProduct(updateProduct(productResponse.getId(), null, null, null, null, "virtual"));

		assertEquals(productResponse.getId(), updateProductResponse.getId());
		assertEquals(productResponse.getCreationDate(), updateProductResponse.getCreationDate());
		assertNotEquals(productResponse.getUpdateDate(), updateProductResponse.getUpdateDate());
		assertEquals(productResponse.getAmount(), updateProductResponse.getAmount());
		assertEquals(productResponse.getCategoryId(), updateProductResponse.getCategoryId());
		assertEquals(productResponse.getCurrency(), updateProductResponse.getCurrency());
		assertEquals("physical", productResponse.getProductType());
		assertEquals("virtual", updateProductResponse.getProductType());
		assertEquals(productResponse.getName(), updateProductResponse.getName());

		deleteProduct(productResponse.getId());
	}

	@Test
	public void test_update_product_category() throws ProductServiceException, InvalidCurrencyException,
			InvalidCategoryException, InvalidProductTypeException, ProductNotFoundException {
		final ProductResponse productResponse = productService
				.createProduct(createProduct(amount, categoryId, currency, name, productType));

		final ProductResponse updateProductResponse = productService
				.updateProduct(updateProduct(productResponse.getId(), null, "E05", null, null, null));

		assertEquals(productResponse.getId(), updateProductResponse.getId());
		assertEquals(productResponse.getCreationDate(), updateProductResponse.getCreationDate());
		assertNotEquals(productResponse.getUpdateDate(), updateProductResponse.getUpdateDate());
		assertEquals(productResponse.getAmount(), updateProductResponse.getAmount());
		assertEquals("E10", productResponse.getCategoryId());
		assertEquals("E05", updateProductResponse.getCategoryId());
		assertEquals(productResponse.getCurrency(), updateProductResponse.getCurrency());
		assertEquals(productResponse.getProductType(), updateProductResponse.getProductType());
		assertEquals(productResponse.getName(), updateProductResponse.getName());

		deleteProduct(productResponse.getId());
	}

	@Test(expected = InvalidCategoryException.class)
	public void test_update_product_category_invalid() throws ProductServiceException, InvalidCurrencyException,
			InvalidCategoryException, InvalidProductTypeException, ProductNotFoundException {
		final ProductResponse productResponse = productService
				.createProduct(createProduct(amount, categoryId, currency, name, productType));
		try {
			productService.updateProduct(updateProduct(productResponse.getId(), null, "S05", null, null, null));
		} finally {
			deleteProduct(productResponse.getId());
		}
	}

	@Test(expected = ProductNotFoundException.class)
	public void test_update_invalid_product() throws ProductServiceException, InvalidCurrencyException,
			InvalidCategoryException, InvalidProductTypeException, ProductNotFoundException {
		productService.updateProduct(updateProduct("invalid_product_update", null, null, null, null, null));
	}

	@Test(expected = HttpClientErrorException.class)
	public void test_try_to_update_product_amount_without_currency() throws ProductServiceException,
			InvalidCurrencyException, InvalidCategoryException, InvalidProductTypeException, ProductNotFoundException {
		final ProductResponse productResponse = productService
				.createProduct(createProduct(amount, categoryId, currency, name, productType));
		try {
			productService.updateProduct(
					updateProduct(productResponse.getId(), new BigDecimal("500"), null, null, null, null));
		} finally {
			deleteProduct(productResponse.getId());
		}
	}

	@Test(expected = ProductNotFoundException.class)
	public void test_delete_product() throws ProductServiceException, InvalidCurrencyException,
			InvalidCategoryException, InvalidProductTypeException, ProductNotFoundException {
		final ProductResponse productResponse = productService
				.createProduct(createProduct(amount, categoryId, currency, name, productType));

		productService.deleteProductById(productResponse.getId());
		productService.deleteProductById(productResponse.getId());
	}

	@Test(expected = ProductNotFoundException.class)
	public void test_delete_product_category() throws ProductServiceException, InvalidCurrencyException,
			InvalidCategoryException, InvalidProductTypeException, ProductNotFoundException, CategoryNotFoundException {
		final ProductResponse productResponse = productService
				.createProduct(createProduct(amount, categoryId, currency, name, productType));

		assertEquals("E10", productResponse.getCategoryId());

		productService.deleteProductCategory(productResponse.getCategoryId());
		ProductResponse productWithoutCategory = productService.findProductById(productResponse.getCategoryId());

		assertNull(productWithoutCategory.getCategoryId());
	}
}
