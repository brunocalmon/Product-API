package de.rakuten.cloud.service.productserver.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import de.rakuten.cloud.service.productapi.persistents.ProductPersistent;

@Repository
public interface ProductRepository extends MongoRepository<ProductPersistent, String> {
}
