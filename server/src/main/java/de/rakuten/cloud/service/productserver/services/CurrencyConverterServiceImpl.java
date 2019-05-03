package de.rakuten.cloud.service.productserver.services;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import de.rakuten.cloud.service.productapi.domainobjects.ConvertedAmountDO;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCurrencyException;

public class CurrencyConverterServiceImpl implements CurrencyConverterService {

	private RestTemplate restTemplate;
	private String currconvEndpoint;

	@Autowired
	public CurrencyConverterServiceImpl(final RestTemplate restTemplate,
			@Value("${free.currconv}") final String currconvEndpoint) {

	}

	@Override
	public ConvertedAmountDO getConvertedCurrencyAmount(final String currency, final BigDecimal amount) {
		try {

			final String currencyPattern = buildConvertQuery(currency);
			final Map<String, String> jsonDictionary = getConvertCurrencyApi(currencyPattern);
			if (isSucceeded(jsonDictionary)) {
				final BigDecimal quotation = getQuotation(currencyPattern, jsonDictionary);

				return ConvertedAmountDO.builder().originalCurrency(currency).originalAmount(amount)
						.quotation(quotation).build();
			}
			throw new InvalidCurrencyException("Invalid currency: " + currency);
		} catch (Exception e) {
			throw new HttpServerErrorException(HttpStatus.BAD_GATEWAY, "Erro while trying to access fixer API");
		}
	}

	private BigDecimal getQuotation(final String currencyPattern, final Map<String, String> jsonDictionary) {
		return new BigDecimal(jsonDictionary.get(currencyPattern));
	}

	private String buildConvertQuery(final String currency) {
		return currency + "_EUR";
	}

	private HashMap<String, String> getConvertCurrencyApi(final String currencyPattern) {
		return restTemplate.exchange(buildRequestEntity(currencyPattern), getResponseType()).getBody();
	}

	private RequestEntity<Void> buildRequestEntity(final String currencyPattern) {
		return RequestEntity.get(buildUri(currencyPattern)).accept(MediaType.APPLICATION_JSON).build();
	}

	private ParameterizedTypeReference<HashMap<String, String>> getResponseType() {
		return new ParameterizedTypeReference<HashMap<String, String>>() {
		};
	}

	private boolean isSucceeded(final Map<String, String> jsonDictionary) {
		return null != jsonDictionary && !jsonDictionary.isEmpty();
	}

	private URI buildUri(final String currencyPattern) {
		final String url = String.format(currconvEndpoint, currencyPattern);
		return URI.create(url);
	}

}
