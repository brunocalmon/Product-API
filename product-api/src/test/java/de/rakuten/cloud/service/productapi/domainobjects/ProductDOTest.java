package de.rakuten.cloud.service.productapi.domainobjects;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.rakuten.cloud.service.productapi.categories.UnitTest;
import de.rakuten.cloud.service.productapi.enums.ProductTypeEnum;

@Category(UnitTest.class)
public class ProductDOTest {

	final BigDecimal amount = new BigDecimal("10");
	final String categoryId = "E10";
	final String currency = "EUR";
	final String name = "product-test-1";
	final ProductTypeEnum physicalType = ProductTypeEnum.PHYSICAL;

	@Test
	public void test_product_domain_creation() {
		ProductDO product = ProductDO.builder().name(name).categoryId(categoryId).currency(currency).amount(amount)
				.productType(physicalType).build();

		assertEquals(amount, product.getAmount());
		assertEquals(categoryId, product.getCategoryId());
		assertEquals(currency, product.getCurrency());
		assertEquals(name, product.getName());
		assertEquals(physicalType, product.getProductType());
	}

	@Test
	public void test_product_domain_converted_creation() {
		ProductDO product = ProductDO.builder().name(name).categoryId(categoryId).currency(currency).amount(amount)
				.productType(physicalType).build();

		assertEquals(amount, product.getAmount());
		assertEquals(categoryId, product.getCategoryId());
		assertEquals(currency, product.getCurrency());
		assertEquals(name, product.getName());
		assertEquals(physicalType, product.getProductType());

		ConvertedAmountDO convertedAmount = ConvertedAmountDO.builder().originalAmount(amount)
				.originalCurrency(currency).quotation(new BigDecimal("0.22662")).build();

		ProductDO convertedProduct = product.createConvertedProductToEuroCurrency(convertedAmount);

		assertEquals(new BigDecimal("2.27"), convertedProduct.getAmount());
		assertEquals(categoryId, convertedProduct.getCategoryId());
		assertEquals("EUR", convertedProduct.getCurrency());
		assertEquals(name, convertedProduct.getName());
		assertEquals(physicalType, convertedProduct.getProductType());
	}

}
