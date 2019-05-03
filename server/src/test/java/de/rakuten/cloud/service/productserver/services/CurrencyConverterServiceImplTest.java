package de.rakuten.cloud.service.productserver.services;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.rakuten.cloud.service.productapi.domainobjects.ConvertedAmountDO;
import de.rakuten.cloud.service.productserver.categories.IntegrationTest;

@Category(IntegrationTest.class)
public class CurrencyConverterServiceImplTest extends SystemIntegrationTest {

	@Test
	public void test_convertion_BRL_to_EUR_responding_a_datailed_response() {
		ConvertedAmountDO convertedCurrencyAmount = currencyConverterService.getConvertedCurrencyAmount("BRL",
				new BigDecimal(25));
		assertNotNull(convertedCurrencyAmount);
	}

}
