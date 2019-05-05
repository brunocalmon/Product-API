package de.rakuten.cloud.service.productserver.mappers;

import javax.validation.constraints.NotNull;

import de.rakuten.cloud.service.categoryapi.domainobjects.CategoryDO;
import de.rakuten.cloud.service.categoryapi.persistents.CategoryPersistent;
import de.rakuten.cloud.service.productserver.datatransferobjects.CategoryRequest;
import de.rakuten.cloud.service.productserver.datatransferobjects.CategoryResponse;

public class CategoryServerMapper {

	public static @NotNull CategoryResponse categoryPersistentToResponseMapper(
			@NotNull final CategoryPersistent persisted) {
		return CategoryResponse.builder().categoryId(persisted.getCategoryId()).name(persisted.getName())
				.createdAt(persisted.getCreatedAt()).lastUpdated(persisted.getLastUpdated()).build();
	}

	public static @NotNull CategoryDO categoryRequestToDomainMapper(@NotNull final CategoryRequest categoryRequest) {
		return CategoryDO.builder().categoryId(categoryRequest.getCategoryId()).name(categoryRequest.getName()).build();
	}

}
