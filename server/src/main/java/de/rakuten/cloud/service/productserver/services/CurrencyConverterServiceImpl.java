package de.rakuten.cloud.service.productserver.services;

import java.math.BigDecimal;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import de.rakuten.cloud.service.productapi.datatransferobjects.FixerResponse;
import de.rakuten.cloud.service.productapi.domainobjects.ConvertedAmountDO;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCurrencyException;

public class CurrencyConverterServiceImpl implements CurrencyConverterService {

	private RestTemplate restTemplate;
	private String fixerEndpoint; 
	private String fixerApiKey;
	
	@Autowired
	public CurrencyConverterServiceImpl(final RestTemplate restTemplate,
			@Value("${fixer.endpoint}") String fixerEndpoint, @Value("${fixer.apikey}") final String fixerApiKey) {

	}

	@Override
	public ConvertedAmountDO getConvertedCurrencyAmount(final BigDecimal amount, final String currency) {
		try {
			FixerResponse fixerResponse = restTemplate.getForObject(buildUri(amount, currency), FixerResponse.class);
			if (fixerResponse.isSuccess()) {
				return ConvertedAmountDO.builder().amount(fixerResponse.getResult()).currency("EUR").build();
			}
			throw new InvalidCurrencyException("Invalid currency: " + currency);
		} catch (Exception e) {
			throw new HttpServerErrorException(HttpStatus.BAD_GATEWAY, "Erro while trying to access fixer API");
		}
	}

	private URI buildUri(BigDecimal amount, String currency) {
		return URI.create(fixerEndpoint + "?access_key=" + fixerApiKey + "&from=" + currency.toUpperCase() + "&to=EUR"
				+ "&amount=" + amount);
	}
	
}
