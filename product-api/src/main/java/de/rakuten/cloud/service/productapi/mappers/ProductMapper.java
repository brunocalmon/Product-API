package de.rakuten.cloud.service.productapi.mappers;

import javax.validation.constraints.NotNull;

import de.rakuten.cloud.service.productapi.domainobjects.ProductDO;
import de.rakuten.cloud.service.productapi.persistents.ProductPersistent;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ProductMapper {
	
	@NotNull
	public static ProductPersistent productDomainToPersistentMapper(@NotNull final ProductDO productDO) {
		return new ProductPersistent();
	}
}
