package org.othuret.api.product.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.othuret.api.product.domain.resource.ProductResource;
import org.othuret.api.product.service.ServiceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ApiQuery.class)
@EnableMongoAuditing
public class ApiQueryTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ApiQuery apiQuery;

    @MockBean
    private ServiceQuery query;

    @MockBean
    private MappingMongoConverter converter;


    @Test
    public void getResourceById() throws Exception {
        final Object id = UUID.randomUUID().toString();
        ProductResource resource = new ProductResource();
        resource.setId(id.toString());
        resource.setName("UnitTestProduct");
        given(apiQuery.get(resource.getId())).willReturn(resource);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/products/" + resource.getId()).accept(
                MediaType.APPLICATION_JSON);

        mvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(jsonPath("name", is(resource.getName())));
    }

}