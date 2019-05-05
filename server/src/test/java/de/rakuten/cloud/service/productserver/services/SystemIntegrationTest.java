package de.rakuten.cloud.service.productserver.services;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import de.rakuten.cloud.service.categoryapi.domainobjects.CategoryDO;
import de.rakuten.cloud.service.categoryapi.mappers.CategoryAPIMapper;
import de.rakuten.cloud.service.productapi.domainobjects.ProductDO;
import de.rakuten.cloud.service.productapi.domainobjects.ProductDO.ProductDOBuilder;
import de.rakuten.cloud.service.productapi.enums.ProductTypeEnum;
import de.rakuten.cloud.service.productapi.exceptions.InvalidProductTypeException;
import de.rakuten.cloud.service.productapi.mappers.ProductAPIMapper;
import de.rakuten.cloud.service.productapi.persistents.ProductPersistent;
import de.rakuten.cloud.service.productserver.repositories.CategoryRepository;
import de.rakuten.cloud.service.productserver.repositories.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class SystemIntegrationTest {
	protected String freeCurrconv;
	protected String apiPath;

	@Autowired
	protected CurrencyConverterService currencyConverterService;
	@Autowired
	protected ProductService productService;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	private String productId1;
	private String productId2;
	private String productId3;
	private String productId4;

	@Before
	public void setUp() {
		setCategories();
		setProducts();
		setWiremocks();
	}

	@After
	public void dropDown() {
		deleteCategory("E10");
		deleteCategory("E11");
		deleteCategory("E05");
		deleteCategory("E01");
		deleteProduct(productId1);
		deleteProduct(productId2);
		deleteProduct(productId3);
		deleteProduct(productId4);
	}

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(
			WireMockConfiguration.options().withRootDirectory("src/test/resources/wiremock").port(8089));

	private void setCategories() {
		saveCategory("E10", "Movies");
		saveCategory("E11", "House");
		saveCategory("E05", "Toys");
		saveCategory("E01", "Magazine");
	}

	private void setProducts() {
		productId1 = saveProduct(new BigDecimal("25.54"), "E10", "EUR", "Buzz Lightyear",
				ProductTypeEnum.VIRTUAL.getType()).getId();
		productId2 = saveProduct(new BigDecimal("240.54"), "E05", "EUR", "Uzumaki Naruto",
				ProductTypeEnum.PHYSICAL.getType()).getId();
		productId3 = saveProduct(new BigDecimal("80.54"), "E11", "EUR", "Dish", ProductTypeEnum.PHYSICAL.getType())
				.getId();
		productId4 = saveProduct(new BigDecimal("5"), "E01", "EUR", "Forbes", ProductTypeEnum.PHYSICAL.getType())
				.getId();
	}

	public void setWiremocks() {
		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(String.format(apiPath, "BRL_EUR")))
				.willReturn(WireMock.aResponse().withStatus(200).withBodyFile("BRL_Quotation.json")));

		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(String.format(apiPath, "Zeny_EUR")))
				.willReturn(WireMock.aResponse().withStatus(200).withBodyFile("Invalid_Quotation.json")));
	}

	protected ProductDO createProduct(final BigDecimal amount, final String categoryId, final String currency,
			final String name, final String productType) throws InvalidProductTypeException {
		return ProductDO.builder().amount(amount).categoryId(categoryId).currency(currency).name(name)
				.productType(ProductTypeEnum.getCorrespondentType(productType)).build();
	}

	protected ProductDO updateProduct(final String id, final BigDecimal amount, final String categoryId,
			final String currency, final String name, final String productType) throws InvalidProductTypeException {
		ProductDOBuilder builder = ProductDO.builder().id(id).amount(amount).categoryId(categoryId).currency(currency)
				.name(name);
		if (null != productType) {
			builder.productType(ProductTypeEnum.getCorrespondentType(productType));
		}
		return builder.build();
	}

	protected CategoryDO createCategory(final String categoryId, final String name) {
		return CategoryDO.builder().categoryId(categoryId).name(name).build();
	}

	private void saveCategory(final String id, final String name) {
		categoryRepository.save(CategoryAPIMapper.categoryDomainToCreatePersistentMapper(createCategory(id, name)));
	}

	private ProductPersistent saveProduct(final BigDecimal amount, final String categoryId, final String currency,
			final String name, final String productType) {
		try {
			return productRepository.save(ProductAPIMapper.productDomainToCreatePersistentMapper(
					createProduct(amount, categoryId, currency, name, productType)));
		} catch (InvalidProductTypeException e) {
			e.printStackTrace();
		}
		return ProductPersistent.builder().build();
	}

	protected void deleteCategory(String id) {
		categoryRepository.deleteById(id);
	}

	protected void deleteProduct(String id) {
		productRepository.deleteById(id);
	}

	@Test
	public void contextLoad() {
	};

	@Value("${api.path}")
	private void setApipath(String apiPath) {
		this.apiPath = apiPath;
	}

	@Value("${free.currconv}")
	private void setFreeCurrconv(String freeCurrconv) {
		this.freeCurrconv = freeCurrconv;
	}
}
