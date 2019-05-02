package de.rakuten.cloud.service.productserver.mappers;

import javax.validation.constraints.NotNull;

import de.rakuten.cloud.service.productapi.domainobjects.ProductDO;
import de.rakuten.cloud.service.productapi.enums.ProductTypeEnum;
import de.rakuten.cloud.service.productapi.exceptions.InvalidProductTypeException;
import de.rakuten.cloud.service.productserver.datatransferobjects.ProductRequest;

public final class ProductMapper {

	@NotNull
	public static ProductDO productRequestToDomainMapper(@NotNull final ProductRequest productRequest) throws InvalidProductTypeException {
		return ProductDO.builder().amount(productRequest.getAmount()).categoryId(productRequest.getCategoryId())
				.currency(productRequest.getCurrency()).name(productRequest.getName())
				.productType(ProductTypeEnum.getCorrespondentType(productRequest.getProductType())).build();
	}
}