package org.othuret.api.product.service;

import org.othuret.api.product.dao.DaoRepository;
import org.othuret.api.product.domain.exception.ResourceNotFoundException;
import org.othuret.api.product.domain.resource.ProductResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class is a service that own queries
 * and include the following http verb:
 * <ul>
 * <li>GET getting one or several resources</li>
 * </ul>
 *
 * @author Olivier THURET
 * @since 0.1
 */
@Service
public class ServiceQuery {

    private DaoRepository daoRepository;

    @Autowired
    public ServiceQuery(DaoRepository daoRepository) {
        this.daoRepository = daoRepository;
    }

    /**
     * Get one resource
     *
     * @param id Unique id of a resource.
     * @return A resource
     */
    public ProductResource get(final String id) {
        Optional<ProductResource> productResource = daoRepository.findById(id);
        return productResource.orElseThrow(() -> new ResourceNotFoundException());
    }

    /**
     * Get all resources with pagination
     *
     * @param pageable
     * @return A list of resources
     */
    public Page<ProductResource> getAll(final Pageable pageable) {
        return daoRepository.findAll(pageable);
    }

}
