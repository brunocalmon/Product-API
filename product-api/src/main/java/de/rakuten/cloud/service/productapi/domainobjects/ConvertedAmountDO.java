package de.rakuten.cloud.service.productapi.domainobjects;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ConvertedAmountDO {
	private BigDecimal amount;
	private String currency;
}
