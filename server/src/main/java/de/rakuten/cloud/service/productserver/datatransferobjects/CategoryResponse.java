package de.rakuten.cloud.service.productserver.datatransferobjects;

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
public class CategoryResponse {

	private String categoryId;
	private String name;
	private Date createdAt;
	private Date lastUpdated;

	public static CategoryResponseBuilder builder() {
		return new CategoryResponseBuilder();
	}

	public static class CategoryResponseBuilder {
		private Date createdAt;
		private Date lastUpdated;
		private String name;
		private String categoryId;

		public CategoryResponseBuilder categoryId(String categoryId) {
			this.categoryId = categoryId;
			return this;
		}

		public CategoryResponseBuilder createdAt(Date createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public CategoryResponseBuilder lastUpdated(Date lastUpdated) {
			this.lastUpdated = lastUpdated;
			return this;
		}

		public CategoryResponseBuilder name(String name) {
			this.name = name;
			return this;
		}

		public CategoryResponse build() {
			CategoryResponse categoryResponse = new CategoryResponse();
			categoryResponse.setCategoryId(categoryId);
			categoryResponse.setCreatedAt(createdAt);
			categoryResponse.setLastUpdated(lastUpdated);
			categoryResponse.setName(name);

			return categoryResponse;
		}
	}

}
