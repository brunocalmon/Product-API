package de.rakuten.cloud.service.productserver.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.rakuten.cloud.service.productserver.categories.IntegrationTest;
import de.rakuten.cloud.service.productserver.datatransferobjects.CategoryResponse;
import de.rakuten.cloud.service.productserver.exceptions.CategoryNotFoundException;

@Category(IntegrationTest.class)
public class CategoryServiceImplTest extends SystemIntegrationTest {

	final String id = "H15";
	final String name = "Food";

	@Test
	public void test_create_category() {
		final CategoryResponse categoryResponse = categoryService.createCategory(createCategory(id, name));

		assertEquals(id, categoryResponse.getCategoryId());
		assertEquals(name, categoryResponse.getName());
		assertNotNull(categoryResponse.getCreatedAt());
		assertNull(categoryResponse.getLastUpdated());

		deleteProduct(categoryResponse.getCategoryId());
	}

	@Test
	public void test_get_created_category() throws CategoryNotFoundException {
		final CategoryResponse categoryResponse = categoryService.createCategory(createCategory(id, name));
		final CategoryResponse finded = categoryService.findCategoryById(categoryResponse.getCategoryId());

		assertEquals(id, finded.getCategoryId());
		assertEquals(name, finded.getName());
		assertNotNull(finded.getCreatedAt());
		assertNull(finded.getLastUpdated());

		deleteProduct(finded.getCategoryId());
	}

	@Test(expected = CategoryNotFoundException.class)
	public void test_try_to_get_inexistent_category() throws CategoryNotFoundException {
		categoryService.findCategoryById("ZZTOP");
		fail();
	}

	@Test(expected = CategoryNotFoundException.class)
	public void test_delete_category_and_received_a_not_found_on_the_second_time() throws CategoryNotFoundException {
		final CategoryResponse categoryResponse = categoryService.createCategory(createCategory(id, name));
		categoryService.deleteCategoryById(categoryResponse.getCategoryId());
		categoryService.deleteCategoryById(categoryResponse.getCategoryId());
	}

}
