package de.rakuten.cloud.service.productserver.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.apache.http.client.HttpResponseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.github.tomakehurst.wiremock.client.WireMock;

import de.rakuten.cloud.service.productapi.domainobjects.ProductDO;
import de.rakuten.cloud.service.productapi.enums.ProductTypeEnum;
import de.rakuten.cloud.service.productapi.exceptions.InvalidProductTypeException;
import de.rakuten.cloud.service.productserver.categories.IntegrationTest;
import de.rakuten.cloud.service.productserver.datatransferobjects.ProductResponse;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCategoryException;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCurrencyException;
import de.rakuten.cloud.service.productserver.exceptions.ProductServiceException;

@Category(IntegrationTest.class)
public class ProductServiceImplTest extends SystemIntegrationTest {

	final BigDecimal amount = new BigDecimal(20);
	final String categoryId = "E10";
	final String currency = "BRL";
	final String name = "Action Toy";
	final String productType = "PHYSICAL";

	@Before
	public void setUp() {
		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(String.format(apiPath, "BRL_EUR")))
				.willReturn(WireMock.aResponse().withStatus(200).withBodyFile("BRL_Quotation.json")));

		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(String.format(apiPath, "Zeny_EUR")))
				.willReturn(WireMock.aResponse().withStatus(200).withBodyFile("Invalid_Quotation.json")));
	}

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
	}

	private ProductDO createProduct(final BigDecimal amount, final String categoryId, final String currency,
			final String name, final String productType) throws InvalidProductTypeException {
		return ProductDO.builder().amount(amount).categoryId(categoryId).currency(currency).name(name)
				.productType(ProductTypeEnum.getCorrespondentType(productType)).build();
	}

}
