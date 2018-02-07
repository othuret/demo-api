package org.othuret.api.product.web;

import org.othuret.api.product.domain.resource.ProductResource;
import org.othuret.api.product.service.ServiceCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping(value = "${api.resourcePath}")
public class ApiCommand {

    @Autowired
    ServiceCommand serviceCommand;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> post(@RequestBody @Validated final ProductResource product, HttpServletRequest req) {
        HttpHeaders httpHeaders = new HttpHeaders();
        String id = serviceCommand.create(product);
        httpHeaders.setLocation(URI.create(req.getRequestURI().replaceAll("/*$", "") + "/" + id));
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void put(@PathVariable(value = "id", required = true) final String id, @RequestBody @Validated final ProductResource product) {
        serviceCommand.put(id, product);
    }

    @PatchMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void patch(@PathVariable(value = "id", required = true) final String id, @RequestBody @Validated final ProductResource product) {
        serviceCommand.patch(id, product);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(value = "id", required = true) final String id) {
        serviceCommand.delete(id);
    }

}
