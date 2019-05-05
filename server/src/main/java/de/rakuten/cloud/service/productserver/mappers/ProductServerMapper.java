package de.rakuten.cloud.service.productserver.mappers;

import javax.validation.constraints.NotNull;

import de.rakuten.cloud.service.productapi.domainobjects.ProductDO;
import de.rakuten.cloud.service.productapi.enums.ProductTypeEnum;
import de.rakuten.cloud.service.productapi.exceptions.InvalidProductTypeException;
import de.rakuten.cloud.service.productapi.persistents.ProductPersistent;
import de.rakuten.cloud.service.productserver.datatransferobjects.ProductRequest;
import de.rakuten.cloud.service.productserver.datatransferobjects.ProductResponse;

public final class ProductServerMapper {

	@NotNull
	public static ProductDO productRequestToDomainMapper(@NotNull final ProductRequest productRequest)
			throws InvalidProductTypeException {
		return ProductDO.builder().amount(productRequest.getAmount()).categoryId(productRequest.getCategoryId())
				.currency(productRequest.getCurrency()).name(productRequest.getName())
				.productType(ProductTypeEnum.getCorrespondentType(productRequest.getProductType())).build();
	}

	@NotNull
	public static ProductResponse productPersistentToResponse(@NotNull final ProductPersistent productPersistent) {
		return ProductResponse.builder()
				.id(productPersistent.getId())
				.name(productPersistent.getName())
				.productType(productPersistent.getProductType().getType())
				.updateDate(productPersistent.getUpdateDate())
				.amount(productPersistent.getAmount())
				.categoryId(productPersistent.getCategoryId())
				.creationDate(productPersistent.getCreationDate())
				.currency(productPersistent.getCurrency())
				.build();
	}
}