package org.othuret.api.product.dao;

import org.othuret.api.product.domain.resource.ProductResource;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DaoRepository<T>
        extends MongoRepository<ProductResource, String>, AutoGenerateUUID<ProductResource> {
}
