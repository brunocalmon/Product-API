package de.rakuten.cloud.service.categoryapi.mappers;

import java.util.Date;

import javax.validation.constraints.NotNull;

import de.rakuten.cloud.service.categoryapi.domainobjects.CategoryDO;
import de.rakuten.cloud.service.categoryapi.persistents.CategoryPersistent;

public class CategoryAPIMapper {

	public static @NotNull CategoryPersistent categoryDomainToCreatePersistentMapper(
			@NotNull final CategoryDO categoryDO) {
		return CategoryPersistent.builder().categoryId(categoryDO.getCategoryId()).name(categoryDO.getName())
				.createdAt(new Date()).build();
	}

	public static @NotNull CategoryPersistent categoryDomainToUpdatePersistentMapper(
			@NotNull final CategoryDO categoryDO) {
		return CategoryPersistent.builder().categoryId(categoryDO.getCategoryId()).name(categoryDO.getName())
				.lastUpdated(new Date()).build();
	}

}
