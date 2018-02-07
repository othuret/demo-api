package org.othuret.api.product.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.othuret.api.product.dao.DaoRepository;
import org.othuret.api.product.domain.exception.BadRequestException;
import org.othuret.api.product.domain.resource.ProductResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

/**
 * This class is a service that own commands
 * and include the following http verb:
 * <ul>
 * <li>POST creating a resource or actions</li>
 * <li>PUT replacing a resource with the incoming payload</li>
 * <li>PATCH merging a resource with the incoming payload</li>
 * <li>DELETE deleting a resource</li>
 * </ul>
 *
 * @author Olivier THURET
 * @since 0.1
 */
@Service
public class ServiceCommand {

    private DaoRepository daoRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public ServiceCommand(DaoRepository daoRepository, ObjectMapper objectMapper) {
        this.daoRepository = daoRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Create a new resource
     *
     * @param product Resource that we want to create.
     * @return String that represent a resource's id.
     */
    public String create(final ProductResource product) {
        return daoRepository.save(product).getId();
    }

    /**
     * Full update between two resources.
     *
     * @param id      Unique id of a resource.
     * @param product Resource that we want to use as a full update to the existing resource.
     */
    public void put(final String id, final ProductResource product) {
        Optional<ProductResource> productResource = daoRepository.findById(id);
        ProductResource resource = productResource.orElseThrow(() -> new BadRequestException());
        try {
            daoRepository.save(objectMapper.setDefaultMergeable(false).updateValue(resource, product));
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * Partial update between two resources.
     *
     * @param id      The unique id of a resource.
     * @param product Resource that we want to use as a partial update to the existing resource.
     */
    public void patch(final String id, final ProductResource product) {
        Optional<ProductResource> productResource = daoRepository.findById(id);
        ProductResource resource = productResource.orElseThrow(() -> new BadRequestException());
        try {
            // merging the current resource with the new one.
            ProductResource mergedProduct = objectMapper
                    .readerForUpdating(resource)
                    .readValue(new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(product));
            daoRepository.save(mergedProduct);
        } catch (IOException e) { // Do not throw checked Exception
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * Delete a resource
     *
     * @param id Unique id of a resource.
     */
    public void delete(final String id) {
        daoRepository.deleteById(id);
    }
}
