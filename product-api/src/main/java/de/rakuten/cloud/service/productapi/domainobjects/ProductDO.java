package de.rakuten.cloud.service.productapi.domainobjects;

import java.math.BigDecimal;

import de.rakuten.cloud.service.productapi.enums.ProductTypeEnum;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductDO {
	
	private String name;
	private BigDecimal amount;
	private String currency;
	private ProductTypeEnum productType;
	private String categoryId;
	
}
