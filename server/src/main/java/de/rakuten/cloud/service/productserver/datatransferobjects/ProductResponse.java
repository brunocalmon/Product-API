package de.rakuten.cloud.service.productserver.datatransferobjects;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

	public static ProductResponseBuilder builder() {
		return new ProductResponseBuilder();
	}

	public static class ProductResponseBuilder {
		private String id;
		private Date creationDate;
		private Date updateDate;
		private String name;
		private BigDecimal amount;
		private String currency;
		private String productType;
		private String categoryId;

		public ProductResponseBuilder id(String id) {
			this.id = id;
			return this;
		}

		public ProductResponseBuilder creationDate(Date createDate) {
			this.creationDate = createDate;
			return this;
		}

		public ProductResponseBuilder updateDate(Date updateDate) {
			this.updateDate = updateDate;
			return this;
		}

		public ProductResponseBuilder name(String name) {
			this.name = name;
			return this;
		}

		public ProductResponseBuilder amount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		public ProductResponseBuilder currency(String currency) {
			this.currency = currency;
			return this;
		}

		public ProductResponseBuilder productType(String productType) {
			this.productType = productType;
			return this;
		}

		public ProductResponseBuilder categoryId(String categoryId) {
			this.categoryId = categoryId;
			return this;
		}

		public ProductResponse build() {
			ProductResponse productResponse = new ProductResponse();
			productResponse.setAmount(amount);
			productResponse.setCategoryId(categoryId);
			productResponse.setCreationDate(creationDate);
			productResponse.setCurrency(currency);
			productResponse.setId(id);
			productResponse.setName(name);
			productResponse.setProductType(productType);
			productResponse.setUpdateDate(updateDate);
			return productResponse;
		}
	}
}