package org.othuret.api.product.web;

import lombok.extern.slf4j.Slf4j;
import org.othuret.api.product.domain.resource.ProductResource;
import org.othuret.api.product.service.ServiceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${api.resourcePath}")
@Slf4j
public class ApiQuery {

    ServiceQuery serviceQuery;

    @Autowired
    public ApiQuery(ServiceQuery serviceQuery) {
        this.serviceQuery = serviceQuery;
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ProductResource get(@PathVariable(value = "id", required = true) String id) {
        return serviceQuery.get(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<ProductResource> getAll(@PageableDefault(size = 100) Pageable pageable) {
        return serviceQuery.getAll(pageable);
    }

}
