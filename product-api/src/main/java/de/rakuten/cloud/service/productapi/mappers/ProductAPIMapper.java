package de.rakuten.cloud.service.productapi.mappers;

import java.util.Date;

import javax.validation.constraints.NotNull;

import de.rakuten.cloud.service.productapi.domainobjects.ProductDO;
import de.rakuten.cloud.service.productapi.persistents.ProductPersistent;

public final class ProductAPIMapper {

	@NotNull
	public static ProductPersistent productDomainToCreatePersistentMapper(@NotNull final ProductDO productDO) {
		return ProductPersistent.builder().amount(productDO.getAmount()).categoryId(productDO.getCategoryId())
				.currency(productDO.getCurrency()).name(productDO.getName()).productType(productDO.getProductType())
				.creationDate(new Date()).build();
	}

	@NotNull
	public static ProductPersistent productDomainToUpdatePersistentMapper(@NotNull final ProductDO productDO) {
		return ProductPersistent.builder().amount(productDO.getAmount()).categoryId(productDO.getCategoryId())
				.currency(productDO.getCurrency()).name(productDO.getName()).productType(productDO.getProductType())
				.updateDate(new Date()).build();
	}

}
