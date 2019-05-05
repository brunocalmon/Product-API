package de.rakuten.cloud.service.productapi.mappers;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.util.StringUtils;

import de.rakuten.cloud.service.productapi.domainobjects.ProductDO;
import de.rakuten.cloud.service.productapi.persistents.ProductPersistent;
import de.rakuten.cloud.service.productapi.persistents.ProductPersistent.ProductPersistentBuilder;

public final class ProductAPIMapper {

	@NotNull
	public static ProductPersistent productDomainToCreatePersistentMapper(@NotNull final ProductDO productDO) {
		return ProductPersistent.builder().amount(productDO.getAmount()).categoryId(productDO.getCategoryId())
				.currency(productDO.getCurrency()).name(productDO.getName()).productType(productDO.getProductType())
				.createdAt(new Date()).build();
	}

	@NotNull
	public static ProductPersistent productDomainToUpdatePersistentMapper(@NotNull final ProductPersistent oldProduct,
			@NotNull final ProductDO productDO) {
		final ProductPersistentBuilder builder = ProductPersistent.builder();

		builder.id(productDO.getId());

		if (!StringUtils.isEmpty(productDO.getCategoryId())) {
			builder.categoryId(productDO.getCategoryId());
		} else {
			builder.categoryId(oldProduct.getCategoryId());
		}

		if (!StringUtils.isEmpty(productDO.getCurrency()) && !StringUtils.isEmpty(productDO.getAmount())) {
			builder.currency(productDO.getCurrency());
			builder.amount(productDO.getAmount());
		} else {
			builder.currency(oldProduct.getCurrency());
			builder.amount(oldProduct.getAmount());
		}

		if (!StringUtils.isEmpty(productDO.getName())) {
			builder.name(productDO.getName());
		} else {
			builder.name(oldProduct.getName());
		}

		if (!StringUtils.isEmpty(productDO.getProductType())) {
			builder.productType(productDO.getProductType());
		} else {
			builder.productType(oldProduct.getProductType());
		}

		builder.createdAt(oldProduct.getCreatedAt());
		builder.lastUpdated(new Date());
		return builder.build();
	}

}
