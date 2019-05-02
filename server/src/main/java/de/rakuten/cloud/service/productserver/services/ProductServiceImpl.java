package de.rakuten.cloud.service.productserver.services;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.rakuten.cloud.service.productapi.domainobjects.ConvertedAmountDO;
import de.rakuten.cloud.service.productapi.domainobjects.ProductDO;
import de.rakuten.cloud.service.productserver.exceptions.ProductServiceException;
import de.rakuten.cloud.service.productserver.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CurrencyConverterService currencyConverterService; 
	
	@Override
	public void createProduct(@NotNull final ProductDO productDO) throws ProductServiceException {
		ConvertedAmountDO convertedAmount = currencyConverterService.getConvertedCurrencyAmount(productDO.getAmount(), productDO.getCurrency());
		
	}
}
