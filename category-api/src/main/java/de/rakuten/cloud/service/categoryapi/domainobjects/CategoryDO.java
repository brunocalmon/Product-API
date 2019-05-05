package de.rakuten.cloud.service.categoryapi.domainobjects;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryDO {

	private String categoryId;
	private String name;

}
