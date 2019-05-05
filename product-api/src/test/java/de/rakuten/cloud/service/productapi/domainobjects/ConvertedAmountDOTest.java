package de.rakuten.cloud.service.productapi.domainobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.rakuten.cloud.service.productapi.categories.UnitTest;

@Category(UnitTest.class)
public class ConvertedAmountDOTest {

	final BigDecimal originalAmount = new BigDecimal("47.753");
	final BigDecimal quotation = new BigDecimal("0.22662");
	final String originalCurrency = "BRL";

	@Test
	public void test_successful_convertion_between_brl_to_eur() {
		final ConvertedAmountDO convertion = ConvertedAmountDO.builder().originalAmount(originalAmount)
				.originalCurrency(originalCurrency).quotation(quotation).build();
		final BigDecimal convertedAmount = convertion.getConvertedAmount();

		assertEquals(originalAmount, convertion.getOriginalAmount());
		assertEquals(originalCurrency, convertion.getOriginalCurrency());
		assertEquals(quotation, convertion.getQuotation());
		assertNotNull(convertion.getCurrentDate());
		assertEquals(new BigDecimal("10.82"), convertedAmount);

	}

}
