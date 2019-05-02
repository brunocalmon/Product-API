package de.rakuten.cloud.service.productapi.datatransferobjects;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixerResponse {
	private boolean success;
	private BigDecimal result;
}
