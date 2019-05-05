package de.rakuten.cloud.service.productapi.persistents;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import de.rakuten.cloud.service.productapi.enums.ProductTypeEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Document(collection = "products")
public class ProductPersistent {

	@Id
	private String id;
	private Date createdAt;
	private Date lastUpdated;
	private String name;
	private BigDecimal amount;
	private String currency;
	private ProductTypeEnum productType;
	private String categoryId;

}
