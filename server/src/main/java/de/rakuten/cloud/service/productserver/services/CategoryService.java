package de.rakuten.cloud.service.productserver.services;

import javax.validation.constraints.NotNull;

import de.rakuten.cloud.service.categoryapi.domainobjects.CategoryDO;
import de.rakuten.cloud.service.productserver.datatransferobjects.CategoryResponse;

public interface CategoryService {

	@NotNull
	public @NotNull CategoryResponse createCategory(@NotNull final CategoryDO categoryDO);

	public boolean exists(@NotNull final String categoryId);

}
