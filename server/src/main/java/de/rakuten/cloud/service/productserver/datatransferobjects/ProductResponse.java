package de.rakuten.cloud.service.productserver.datatransferobjects;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class ProductResponse {

	private String id;
	private Date creationDate;
	private Date updateDate;
	private String name;
	private BigDecimal amount;
	private String currency;
	private String productType;
	private String categoryId;

}