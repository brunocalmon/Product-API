package de.rakuten.cloud.service.productserver.services;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

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

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(
			WireMockConfiguration.options().withRootDirectory("src/test/resources/wiremock").port(8089));

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
