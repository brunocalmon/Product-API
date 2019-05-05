package de.rakuten.cloud.service.productserver.services;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.rakuten.cloud.service.categoryapi.domainobjects.CategoryDO;
import de.rakuten.cloud.service.categoryapi.mappers.CategoryAPIMapper;
import de.rakuten.cloud.service.categoryapi.persistents.CategoryPersistent;
import de.rakuten.cloud.service.productserver.datatransferobjects.CategoryResponse;
import de.rakuten.cloud.service.productserver.mappers.CategoryServerMapper;
import de.rakuten.cloud.service.productserver.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public @NotNull CategoryResponse createCategory(@NotNull final CategoryDO categoryDO) {
		final CategoryPersistent persistent = CategoryAPIMapper.categoryDomainToCreatePersistentMapper(categoryDO);
		final CategoryPersistent persisted = categoryRepository.save(persistent);

		return CategoryServerMapper.categoryPersistentToResponseMapper(persisted);
	}

	@Override
	public @NotNull boolean exists(@NotNull final String categoryId) {
		return categoryRepository.existsById(categoryId);
	}

}
