package de.rakuten.cloud.service.productserver.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.apache.http.client.HttpResponseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.github.tomakehurst.wiremock.client.WireMock;

import de.rakuten.cloud.service.productapi.domainobjects.ConvertedAmountDO;
import de.rakuten.cloud.service.productserver.categories.IntegrationTest;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCurrencyException;

@Category(IntegrationTest.class)
public class CurrencyConverterServiceImplTest extends SystemIntegrationTest {

	@Before
	public void setUp() {
		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(String.format(apiPath, "BRL_EUR")))
				.willReturn(WireMock.aResponse().withStatus(200).withBodyFile("BRL_Quotation.json")));

		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(String.format(apiPath, "Zeny_EUR")))
				.willReturn(WireMock.aResponse().withStatus(200).withBodyFile("Invalid_Quotation.json")));
	}

	@Test
	public void test_convertion_BRL_to_EUR_responding_a_detailed_response()
			throws InvalidCurrencyException, HttpResponseException {
		final ConvertedAmountDO convertedCurrencyAmount = currencyConverterService.getConvertedCurrencyAmount("BRL",
				new BigDecimal("25"));

		assertNotNull(convertedCurrencyAmount);
		assertEquals(convertedCurrencyAmount.getQuotation(), new BigDecimal("0.225551"));
		assertEquals(convertedCurrencyAmount.getCurrency(), "EUR");
		assertEquals(convertedCurrencyAmount.getConvertedAmount(), new BigDecimal("5.64"));
		assertEquals(convertedCurrencyAmount.getOriginalAmount(), new BigDecimal("25"));
		assertEquals(convertedCurrencyAmount.getOriginalCurrency(), "BRL");
	}

	@Test(expected = InvalidCurrencyException.class)
	public void test_convertion_invalid_currency_response() throws InvalidCurrencyException, HttpResponseException {
		currencyConverterService.getConvertedCurrencyAmount("Zeny", new BigDecimal("25"));
		fail();
	}

}
