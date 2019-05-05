package de.rakuten.cloud.service.productserver.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import de.rakuten.cloud.service.categoryapi.persistents.CategoryPersistent;

@Repository
public interface CategoryRepository extends MongoRepository<CategoryPersistent, String> {
}
