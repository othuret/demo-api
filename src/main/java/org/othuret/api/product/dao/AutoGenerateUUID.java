package org.othuret.api.product.dao;

import org.othuret.api.product.domain.resource.ProductResource;

public interface AutoGenerateUUID<T extends ProductResource> {
    <S extends T> S save(S entity);
}
