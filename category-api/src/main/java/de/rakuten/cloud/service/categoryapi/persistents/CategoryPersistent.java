package de.rakuten.cloud.service.categoryapi.persistents;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Document(collection = "categories")
public class CategoryPersistent {

	@Id
	private String categoryId;
	private String name;
	private Date createdAt;
	private Date lastUpdated;

}
