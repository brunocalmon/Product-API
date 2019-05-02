package de.rakuten.cloud.service.productapi.services;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import de.rakuten.cloud.service.productapi.domainobjects.ConvertedAmountDO;

@Service
public interface CurrencyConverterService {
	public ConvertedAmountDO getConvertedCurrencyAmount(@NotNull final BigDecimal amount, @NotNull final String currency);
}
