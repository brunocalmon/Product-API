package de.rakuten.cloud.service.productapi.domainobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ConvertedAmountDO {

	private final Date currentDate = new Date();
	private String originalCurrency;
	private BigDecimal originalAmount;
	private final String currency = "EUR";
	private BigDecimal quotation;

	public BigDecimal getConvertedAmount() {
		return calculateAmountConvertion();
	}

	private BigDecimal calculateAmountConvertion() {
		return quotation.multiply(originalAmount).setScale(2, RoundingMode.HALF_EVEN);
	}
}
