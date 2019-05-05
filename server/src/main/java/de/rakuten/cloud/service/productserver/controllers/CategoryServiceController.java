package de.rakuten.cloud.service.productserver.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.rakuten.cloud.service.productserver.datatransferobjects.CategoryRequest;
import de.rakuten.cloud.service.productserver.datatransferobjects.CategoryResponse;
import de.rakuten.cloud.service.productserver.exceptions.CategoryNotFoundException;
import de.rakuten.cloud.service.productserver.exceptions.InvalidCategoryException;
import de.rakuten.cloud.service.productserver.mappers.CategoryServerMapper;
import de.rakuten.cloud.service.productserver.services.CategoryService;

@RestController
@RequestMapping(path = "/api/v1/categories")
public class CategoryServiceController extends BaseController {

	@Autowired
	CategoryService categoryService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public CategoryResponse createCategory(@Valid @RequestBody final CategoryRequest categoryRequest)
			throws InvalidCategoryException {
		return categoryService.createCategory(CategoryServerMapper.categoryRequestToDomainMapper(categoryRequest));
	}

	@RequestMapping(path = "/{categoryId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public CategoryResponse createCategory(@PathVariable(name = "categoryId", required = true) final String categoryId)
			throws CategoryNotFoundException {
		return categoryService.findCategoryById(categoryId);
	}

	@RequestMapping(path = "/{categoryId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteCategory(@PathVariable(name = "categoryId", required = true) final String categoryId)
			throws CategoryNotFoundException {
		categoryService.deleteCategoryById(categoryId);
	}

}
