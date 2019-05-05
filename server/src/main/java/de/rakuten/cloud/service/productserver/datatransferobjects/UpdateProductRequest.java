package de.rakuten.cloud.service.productserver.datatransferobjects;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateProductRequest {

	private String name;
	private BigDecimal amount;
	private String currency;
	private String productType;
	private String categoryId;

}