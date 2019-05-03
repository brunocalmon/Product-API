package de.rakuten.cloud.service.productserver.services;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import de.rakuten.cloud.service.productapi.domainobjects.ConvertedAmountDO;

public interface CurrencyConverterService {
	public ConvertedAmountDO getConvertedCurrencyAmount(@NotNull final String currency, @NotNull final BigDecimal amount);
}
