package de.rakuten.cloud.service.productapi.enums;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.rakuten.cloud.service.productapi.categories.UnitTest;
import de.rakuten.cloud.service.productapi.exceptions.InvalidProductTypeException;

@Category(UnitTest.class)
public class ProductTypeEnumTest {

	@Test
	public void test_productTypeEnum_physical() throws InvalidProductTypeException {
		ProductTypeEnum correspondentType = ProductTypeEnum.getCorrespondentType("physical");

		assertEquals(ProductTypeEnum.PHYSICAL, correspondentType);
	}

	@Test
	public void test_productTypeEnum_virtual() throws InvalidProductTypeException {
		ProductTypeEnum correspondentType = ProductTypeEnum.getCorrespondentType("virtual");

		assertEquals(ProductTypeEnum.VIRTUAL, correspondentType);
	}

	@Test(expected = InvalidProductTypeException.class)
	public void test_productTypeEnum_invalid() throws InvalidProductTypeException {
		ProductTypeEnum.getCorrespondentType("unknown");
		fail();
	}

}
