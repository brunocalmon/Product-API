package de.rakuten.cloud.service.productapi.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.rakuten.cloud.service.productapi.categories.UnitTest;
import de.rakuten.cloud.service.productapi.domainobjects.ProductDO;
import de.rakuten.cloud.service.productapi.enums.ProductTypeEnum;
import de.rakuten.cloud.service.productapi.persistents.ProductPersistent;

@Category(UnitTest.class)
public class ProductMapperTest {

	final BigDecimal amount = new BigDecimal("10");
	final String categoryId = "E10";
	final String currency = "EUR";
	final String name = "product-test-1";
	final ProductTypeEnum physicalType = ProductTypeEnum.PHYSICAL;
	final ProductTypeEnum virtualType = ProductTypeEnum.VIRTUAL;

	@Test
	public void test_mapping_product_domain_to_create_persistent() {
		ProductDO productDO = createProductDO(amount, categoryId, currency, name, physicalType);
		ProductPersistent createdPersistent = ProductAPIMapper.productDomainToCreatePersistentMapper(productDO);

		assertEquals(new BigDecimal("10"), createdPersistent.getAmount());
		assertEquals("E10", createdPersistent.getCategoryId());
		assertEquals("EUR", createdPersistent.getCurrency());
		assertEquals("product-test-1", createdPersistent.getName());
		assertNotNull(createdPersistent.getCreatedAt());
		assertNull(createdPersistent.getLastUpdated());

	}

	@Test
	public void test_mapping_product_domain_to_update_persistent() {
		ProductDO productDO = createProductDO(amount, categoryId, currency, name, virtualType);
		ProductPersistent oldProduct = ProductAPIMapper.productDomainToCreatePersistentMapper(productDO);
		ProductPersistent createdPersistent = ProductAPIMapper.productDomainToUpdatePersistentMapper(oldProduct,
				productDO);

		assertEquals(new BigDecimal("10"), createdPersistent.getAmount());
		assertEquals("E10", createdPersistent.getCategoryId());
		assertEquals("EUR", createdPersistent.getCurrency());
		assertEquals("product-test-1", createdPersistent.getName());
		assertNotNull(createdPersistent.getLastUpdated());
		assertNull(createdPersistent.getCreatedAt());

	}

	private ProductDO createProductDO(BigDecimal amount, String categoryId, String currency, String name,
			ProductTypeEnum productType) {
		return ProductDO.builder().amount(amount).categoryId(categoryId).currency(currency).name(name)
				.productType(productType).build();
	}
}
