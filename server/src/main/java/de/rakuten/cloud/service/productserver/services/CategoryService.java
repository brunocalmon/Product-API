package de.rakuten.cloud.service.productserver.services;

import javax.validation.constraints.NotNull;

import de.rakuten.cloud.service.categoryapi.domainobjects.CategoryDO;
import de.rakuten.cloud.service.productserver.datatransferobjects.CategoryResponse;
import de.rakuten.cloud.service.productserver.exceptions.CategoryNotFoundException;

public interface CategoryService {

	@NotNull
	public CategoryResponse createCategory(@NotNull final CategoryDO categoryDO);

	@NotNull
	public CategoryResponse findCategoryById(@NotNull final String categoryId) throws CategoryNotFoundException;

	public void deleteCategoryById(@NotNull final String categoryId) throws CategoryNotFoundException;

	public boolean exists(@NotNull final String categoryId);

}
