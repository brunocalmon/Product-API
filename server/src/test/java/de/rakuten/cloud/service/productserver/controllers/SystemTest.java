package de.rakuten.cloud.service.productserver.controllers;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.net.URI;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import de.rakuten.cloud.service.productapi.enums.ProductTypeEnum;
import de.rakuten.cloud.service.productserver.datatransferobjects.CategoryRequest;
import de.rakuten.cloud.service.productserver.datatransferobjects.CategoryResponse;
import de.rakuten.cloud.service.productserver.datatransferobjects.ProductRequest;
import de.rakuten.cloud.service.productserver.datatransferobjects.ProductResponse;
import de.rakuten.cloud.service.productserver.services.SystemIntegrationTest;

@Category(SystemTest.class)
public class SystemTest extends SystemIntegrationTest {

	@LocalServerPort
	private int port;

	private static final String BASE_URL = "http://localhost:";

	protected String getFormattedUrl(String resource) {
		return BASE_URL + port + resource;
	}

	private RestTemplate restTemplate = new RestTemplate();

	final String categoryId = "H21";
	final String categoryName = "Manga";
	final BigDecimal amount = new BigDecimal("25");
	final String currency = "BRL";
	final String name = "Zelda";
	final ProductTypeEnum productType = ProductTypeEnum.PHYSICAL;

	@Test
	public void test_creation_and_delete_flow() {
		ResponseEntity<CategoryResponse> categoryResponse = makeCreateCategoryRequest();
		ResponseEntity<ProductResponse> productResponse = makeCreateProductRequest();

		ResponseEntity<String> categoryDeleted = makeDeleteCategoryRequest(categoryResponse.getBody().getCategoryId());
		ResponseEntity<String> productDeleted = makeDeleteProductRequest(productResponse.getBody().getId());

		assertEquals(HttpStatus.OK, productDeleted.getStatusCode());
		assertEquals(HttpStatus.OK, categoryDeleted.getStatusCode());
	}

	@Test
	public void test_product_update_flow() {
		makeCreateCategoryRequest();
		ResponseEntity<ProductResponse> createdProductResponse = makeCreateProductRequest();
		assertEquals(name, createdProductResponse.getBody().getName());

		ResponseEntity<ProductResponse> updatedProduct = makeUpdateProductRequest(
				createdProductResponse.getBody().getId());
		assertEquals(HttpStatus.OK, updatedProduct.getStatusCode());

		ResponseEntity<ProductResponse> productResponse = makeFindProductRequest(
				createdProductResponse.getBody().getId());
		assertEquals("new-product", productResponse.getBody().getName());

		ResponseEntity<String> productDeleted = makeDeleteProductRequest(createdProductResponse.getBody().getId());
		assertEquals(HttpStatus.OK, productDeleted.getStatusCode());
	}

	private ResponseEntity<ProductResponse> makeCreateProductRequest() {
		ProductRequest createProductRequest = createProductRequest(amount, currency, productType.getType(), categoryId,
				name);
		ResponseEntity<ProductResponse> productResponse = performRequest(getFormattedUrl("/api/v1/products"), null,
				HttpMethod.POST, getEntity(createProductRequest), ProductResponse.class);
		assertEquals(HttpStatus.CREATED, productResponse.getStatusCode());
		return productResponse;
	}

	private ResponseEntity<ProductResponse> makeUpdateProductRequest(String id) {
		ProductRequest createProductRequest = createProductRequest(null, null, null, null, "new-product");
		ResponseEntity<ProductResponse> productResponse = performRequest(getFormattedUrl("/api/v1/products/" + id),
				null, HttpMethod.PUT, getEntity(createProductRequest), ProductResponse.class);
		assertEquals(HttpStatus.OK, productResponse.getStatusCode());
		return productResponse;
	}

	private ResponseEntity<CategoryResponse> makeCreateCategoryRequest() {
		ResponseEntity<CategoryResponse> categoryResponse = performRequest(getFormattedUrl("/api/v1/categories"), null,
				HttpMethod.POST, getEntity(createCategoryRequest("H21", "Manga")), CategoryResponse.class);
		assertEquals(HttpStatus.CREATED, categoryResponse.getStatusCode());
		return categoryResponse;
	}

	private ResponseEntity<ProductResponse> makeFindProductRequest(String id) {
		return performRequest(getFormattedUrl("/api/v1/products/" + id), null, HttpMethod.GET, getEntity(null),
				ProductResponse.class);

	}

	private ResponseEntity<String> makeDeleteProductRequest(String id) {
		return performRequest(getFormattedUrl("/api/v1/products/" + id), null, HttpMethod.DELETE, getEntity(null),
				String.class);

	}

	private ResponseEntity<String> makeDeleteCategoryRequest(String categoryId) {
		return performRequest(getFormattedUrl("/api/v1/categories/" + categoryId), null, HttpMethod.DELETE,
				getEntity(null), String.class);
	}

	private <T> ResponseEntity<T> performRequest(String url, MultiValueMap<String, String> params, HttpMethod method,
			HttpEntity<?> httpEntity, Class<T> DTO) {
		return restTemplate.exchange(getUri(url, params), method, httpEntity, DTO);
	}

	private <T> HttpEntity<Object> getEntity(T body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<Object>(body, headers);
	}

	private URI getUri(String url, MultiValueMap<String, String> params) {
		return UriComponentsBuilder.fromUriString(url).queryParams(params).build().encode().toUri();
	}

	private CategoryRequest createCategoryRequest(final String categoryId, final String name) {
		CategoryRequest request = new CategoryRequest();
		request.setCategoryId(categoryId);
		request.setName(name);
		return request;
	}

	private ProductRequest createProductRequest(final BigDecimal amount, final String currency,
			final String productType, final String categoryId, final String name) {
		ProductRequest request = new ProductRequest();
		request.setAmount(amount);
		request.setCurrency(currency);
		request.setProductType(productType);
		request.setCategoryId(categoryId);
		request.setName(name);
		return request;
	}
}
