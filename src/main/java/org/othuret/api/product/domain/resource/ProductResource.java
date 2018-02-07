package org.othuret.api.product.domain.resource;

import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.othuret.api.product.annotation.CheckMapKeyLocale;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;
import java.util.Map;

@Getter
@Setter
@Document(collection = "#{@dbCollectionName}")
@JsonPropertyOrder({"id", "name"})
public class ProductResource extends BaseResource {
    @Size(min = 2, max = 80)
    private String name;

    @Size(min = 2, max = 20)
    private String label;

    @CheckMapKeyLocale
    @JsonMerge
    private Map<String, @Size(max = 200) String> localizedShortDesc;

    @CheckMapKeyLocale
    @JsonMerge
    private Map<String, @Size(max = 2000) String> localizedLongDesc;
}
