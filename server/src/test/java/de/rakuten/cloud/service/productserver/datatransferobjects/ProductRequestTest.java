package de.rakuten.cloud.service.productserver.datatransferobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.rakuten.cloud.service.productserver.categories.UnitTest;

@Category(UnitTest.class)
public class ProductRequestTest {

	private static Validator validator;

	final BigDecimal amount = new BigDecimal(20);
	final String categoryId = "E10";
	final String currency = "BRL";
	final String name = "Action Toy";
	final String productType = "PHYSICAL";

	@BeforeClass
	public static void setupValidatorInstance() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	public void test_product_request_when_all_valid() {
		ProductRequest createValidProduct = createProduct(amount, categoryId, currency, name, productType);
		Set<ConstraintViolation<ProductRequest>> validated = validator.validate(createValidProduct);
		assertTrue(validated.isEmpty());
	}

	@Test
	public void test_product_request_when_amount_is_zero() {
		ProductRequest createValidProduct = createProduct(new BigDecimal(0), categoryId, currency, name, productType);
		Set<ConstraintViolation<ProductRequest>> validated = validator.validate(createValidProduct);
		assertEquals(validated.size(), 1);
	}

	@Test
	public void test_product_request_when_amount_is_null() {
		ProductRequest createValidProduct = createProduct(null, categoryId, currency, name, productType);
		Set<ConstraintViolation<ProductRequest>> validated = validator.validate(createValidProduct);
		assertEquals(validated.size(), 1);
	}

	@Test
	public void test_product_request_when_category_is_empty() {
		ProductRequest createValidProduct = createProduct(amount, "", currency, name, productType);
		Set<ConstraintViolation<ProductRequest>> validated = validator.validate(createValidProduct);
		assertEquals(validated.size(), 0);
	}

	@Test
	public void test_product_request_when_category_is_null() {
		ProductRequest createValidProduct = createProduct(amount, null, currency, name, productType);
		Set<ConstraintViolation<ProductRequest>> validated = validator.validate(createValidProduct);
		assertEquals(validated.size(), 0);
	}

	@Test
	public void test_product_request_when_currency_is_empty() {
		ProductRequest createValidProduct = createProduct(amount, categoryId, "", name, productType);
		Set<ConstraintViolation<ProductRequest>> validated = validator.validate(createValidProduct);
		assertEquals(validated.size(), 1);
	}

	@Test
	public void test_product_request_when_currency_is_null() {
		ProductRequest createValidProduct = createProduct(amount, categoryId, null, name, productType);
		Set<ConstraintViolation<ProductRequest>> validated = validator.validate(createValidProduct);
		assertEquals(validated.size(), 1);
	}

	@Test
	public void test_product_request_when_name_is_empty() {
		ProductRequest createValidProduct = createProduct(amount, categoryId, currency, "", productType);
		Set<ConstraintViolation<ProductRequest>> validated = validator.validate(createValidProduct);
		assertEquals(validated.size(), 1);
	}

	@Test
	public void test_product_request_when_name_is_null() {
		ProductRequest createValidProduct = createProduct(amount, categoryId, currency, null, productType);
		Set<ConstraintViolation<ProductRequest>> validated = validator.validate(createValidProduct);
		assertEquals(validated.size(), 1);
	}

	@Test
	public void test_product_request_when_product_type_is_empty() {
		ProductRequest createValidProduct = createProduct(amount, categoryId, currency, name, "");
		Set<ConstraintViolation<ProductRequest>> validated = validator.validate(createValidProduct);
		assertEquals(validated.size(), 1);
	}

	@Test
	public void test_product_request_when_product_type_is_null() {
		ProductRequest createValidProduct = createProduct(amount, categoryId, currency, name, null);
		Set<ConstraintViolation<ProductRequest>> validated = validator.validate(createValidProduct);
		assertEquals(validated.size(), 1);
	}

	private ProductRequest createProduct(final BigDecimal amount, final String categoryId, final String currency,
			final String name, final String productType) {
		ProductRequest productRequest = new ProductRequest();
		productRequest.setAmount(amount);
		productRequest.setCategoryId(categoryId);
		productRequest.setCurrency(currency);
		productRequest.setName(name);
		productRequest.setProductType(productType);

		return productRequest;
	}

}
