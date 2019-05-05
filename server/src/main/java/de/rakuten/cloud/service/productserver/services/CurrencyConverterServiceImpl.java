package de.rakuten.cloud.service.productserver.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.rakuten.cloud.service.productapi.domainobjects.ConvertedAmountDO;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCurrencyException;

@Service
public class CurrencyConverterServiceImpl implements CurrencyConverterService {

	private RestTemplate restTemplate;
	private String currconvEndpoint;

	@Autowired
	public CurrencyConverterServiceImpl(final RestTemplate restTemplate,
			@Value("${free.currconv}") final String currconvEndpoint) {
		this.restTemplate = restTemplate;
		this.currconvEndpoint = currconvEndpoint;
	}

	@Override
	public ConvertedAmountDO getConvertedCurrencyAmount(final String currency, final BigDecimal amount)
			throws InvalidCurrencyException {
		try {
			final String currencyPattern = buildConvertQuery(currency);
			final String currResponse = getConvertCurrencyApi(currencyPattern);
			final BigDecimal quotation = getQuotation(currencyPattern, currResponse);
			return ConvertedAmountDO.builder().originalCurrency(currency).originalAmount(amount).quotation(quotation)
					.build();
		} catch (InvalidCurrencyException ice) {
			throw new InvalidCurrencyException("Invalid currency: " + currency, ice);
		} catch (HttpClientErrorException hcee) {
			throw new HttpClientErrorException(hcee.getStatusCode(),
					"Erro while trying to access currency API: " + hcee.getMessage());
		} catch (HttpServerErrorException esee) {
			throw new HttpServerErrorException(esee.getStatusCode(),
					"Erro while trying to access currency API: " + esee.getMessage());
		} catch (Exception e) {
			throw new RuntimeException("Unkown error has occuried: " + e.getMessage());
		}
	}

	private BigDecimal getQuotation(final String currencyPattern, final String currResponse)
			throws InvalidCurrencyException {
		return new BigDecimal(extractValue(currencyPattern, currResponse));
	}

	private String extractValue(final String currencyPattern, final String currResponse)
			throws InvalidCurrencyException {
		try {
			final JsonNode json = new ObjectMapper().readTree(currResponse);

			if (null != json.get(currencyPattern)) {
				return json.get(currencyPattern).asText();
			}
			throw new InvalidCurrencyException("Invalid currency convertion: " + currencyPattern);
		} catch (IOException e) {
			throw new InvalidCurrencyException("Invalid currency convertion: " + currencyPattern, e);
		}
	}

	private String buildConvertQuery(final String currency) {
		return currency + "_EUR";
	}

	private String getConvertCurrencyApi(final String currencyPattern) {
		return restTemplate.getForObject(buildUri(currencyPattern), String.class);
	}

	private URI buildUri(final String currencyPattern) {
		final String url = String.format(currconvEndpoint, currencyPattern);
		return URI.create(url);
	}

}
