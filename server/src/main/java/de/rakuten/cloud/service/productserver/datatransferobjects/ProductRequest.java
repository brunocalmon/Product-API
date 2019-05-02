package de.rakuten.cloud.service.productserver.datatransferobjects;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRequest {

	@NotBlank
	private String name;
	@NotNull
	@Positive
	private BigDecimal amount;
	@NotBlank
	private String currency;
	@NotBlank
	private String productType;
	private String categoryId;

}