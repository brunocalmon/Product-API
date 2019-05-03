package de.rakuten.cloud.service.productserver.services;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Splitter;

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
	public ConvertedAmountDO getConvertedCurrencyAmount(final String currency, final BigDecimal amount) {
		try {

			final String currencyPattern = buildConvertQuery(currency);
			final ResponseEntity<String> currResponse = getConvertCurrencyApi(currencyPattern);
			if (isSucceeded(currResponse)) {
				final BigDecimal quotation = getQuotation(currencyPattern, currResponse.getBody());
				return ConvertedAmountDO.builder().originalCurrency(currency).originalAmount(amount)
						.quotation(quotation).build();
			}
			throw new InvalidCurrencyException("Invalid currency: " + currency);
		} catch (Exception e) {
			throw new HttpServerErrorException(HttpStatus.BAD_GATEWAY, "Erro while trying to access currency API");
		}
	}

	private BigDecimal getQuotation(final String currencyPattern, String currResponse) {
		Map<String, String> properties = Splitter.on(",").withKeyValueSeparator(":").split(currResponse);
		return new BigDecimal(properties.get(currencyPattern));
	}

	private String buildConvertQuery(final String currency) {
		return currency + "_EUR";
	}

	private ResponseEntity<String> getConvertCurrencyApi(final String currencyPattern) {
		return restTemplate.getForEntity(buildUri(currencyPattern), String.class);
	}

	private boolean isSucceeded(final ResponseEntity<String> currResponse) {
		return null != currResponse && currResponse.getStatusCode().equals(HttpStatus.ACCEPTED)
				&& currResponse.hasBody();
	}

	private URI buildUri(final String currencyPattern) {
		final String url = String.format(currconvEndpoint, currencyPattern);
		return URI.create(url);
	}

}
