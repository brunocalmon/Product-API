package de.rakuten.cloud.service.productserver.mappers;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.rakuten.cloud.service.productapi.domainobjects.ProductDO;
import de.rakuten.cloud.service.productapi.enums.ProductTypeEnum;
import de.rakuten.cloud.service.productapi.exceptions.InvalidProductTypeException;
import de.rakuten.cloud.service.productapi.persistents.ProductPersistent;
import de.rakuten.cloud.service.productserver.categories.UnitTest;
import de.rakuten.cloud.service.productserver.datatransferobjects.ProductRequest;
import de.rakuten.cloud.service.productserver.datatransferobjects.ProductResponse;

@Category(UnitTest.class)
public class ProductMapperTest {

	final String id = "sa65d4asd4as5da6s5d";
	final Date creationDate = new Date();
	final Date updateDate = new Date();
	final BigDecimal amount = new BigDecimal(20);
	final String categoryId = "E10";
	final String currency = "BRL";
	final String name = "Action Toy";
	final String productType = "PHYSICAL";

	@Test
	public void test_successfuly_request_to_do_mapping() throws InvalidProductTypeException {

		final ProductDO productDO = ProductServerMapper
				.productRequestToDomainMapper(createProduct(amount, categoryId, currency, name, productType));

		assertEquals(amount, productDO.getAmount());
		assertEquals(categoryId, productDO.getCategoryId());
		assertEquals(currency, productDO.getCurrency());
		assertEquals(name, productDO.getName());
		assertEquals(ProductTypeEnum.PHYSICAL, productDO.getProductType());
	}

	@Test(expected = InvalidProductTypeException.class)
	public void test_with_invalid_product_type_dto_to_do_mapping() throws InvalidProductTypeException {
		ProductServerMapper.productRequestToDomainMapper(createProduct(amount, categoryId, currency, name, "AMORPH"));
	}

	@Test
	public void test_successfuly_persistent_to_response_mapping() throws InvalidProductTypeException {

		final ProductResponse productResonse = ProductServerMapper.productPersistentToResponse(createPersistedProduct(
				id, creationDate, updateDate, amount, name, currency, ProductTypeEnum.PHYSICAL, categoryId));

		assertEquals(id, productResonse.getId());
		assertEquals(creationDate, productResonse.getCreationDate());
		assertEquals(updateDate, productResonse.getUpdateDate());
		assertEquals(amount, productResonse.getAmount());
		assertEquals(categoryId, productResonse.getCategoryId());
		assertEquals(currency, productResonse.getCurrency());
		assertEquals(name, productResonse.getName());
		assertEquals(ProductTypeEnum.PHYSICAL.getType(), productResonse.getProductType());
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

	private ProductPersistent createPersistedProduct(final String id, final Date creationDate, final Date updateDate,
			final BigDecimal amount, String name, String currency, ProductTypeEnum productType, String categoryId) {
		return ProductPersistent.builder().amount(amount).categoryId(categoryId).currency(currency).name(name)
				.productType(productType).id(id).createdAt(creationDate).lastUpdated(updateDate).build();
	}
}
