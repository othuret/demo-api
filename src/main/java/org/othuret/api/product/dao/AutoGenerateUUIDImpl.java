package org.othuret.api.product.dao;

import org.othuret.api.product.domain.resource.ProductResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.UUID;

public class AutoGenerateUUIDImpl<T extends ProductResource> implements AutoGenerateUUID<T> {

    @Autowired
    private MongoOperations operations;

    public <S extends T> S save(S entity) {
        if (entity.getId() == null)
            entity.setId(UUID.randomUUID().toString());
        operations.save(entity);
        return entity;
    }
}
