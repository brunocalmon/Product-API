package de.rakuten.cloud.service.productserver.mappers;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.rakuten.cloud.service.productapi.domainobjects.ProductDO;
import de.rakuten.cloud.service.productapi.enums.ProductTypeEnum;
import de.rakuten.cloud.service.productapi.exceptions.InvalidProductTypeException;
import de.rakuten.cloud.service.productserver.categories.UnitTest;
import de.rakuten.cloud.service.productserver.datatransferobjects.ProductRequest;

@Category(UnitTest.class)
public class ProductMapperTest {

	@Test
	public void test_successfuly_dto_to_do_mapping() throws InvalidProductTypeException {
		ProductDO productDO = ProductMapper.productRequestToDomainMapper(createProduct(new BigDecimal(20), "E10", "BRL", "Action Toy", "PHYSICAL"));
		
		assertEquals(productDO.getAmount(), new BigDecimal(20));
		assertEquals(productDO.getCategoryId(), "E10");
		assertEquals(productDO.getCurrency(), "BRL");
		assertEquals(productDO.getName(), "Action Toy");
		assertEquals(productDO.getProductType(), ProductTypeEnum.PHYSICAL);
	}
	
	@Test(expected = InvalidProductTypeException.class)
	public void test_with_invalid_product_type_dto_to_do_mapping() throws InvalidProductTypeException {
		ProductMapper.productRequestToDomainMapper(createProduct(new BigDecimal(20), "E10", "BRL", "Action Toy", "AMORPH"));
	}

	private ProductRequest createProduct(BigDecimal amount, String categoryId, String currency, String name, String productType) {
		ProductRequest productRequest = new ProductRequest();
		productRequest.setAmount(amount);
		productRequest.setCategoryId(categoryId);
		productRequest.setCurrency(currency);
		productRequest.setName(name);
		productRequest.setProductType(productType);
		return productRequest;
	}
}
