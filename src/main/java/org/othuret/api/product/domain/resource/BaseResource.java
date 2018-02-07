package org.othuret.api.product.domain.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.util.Date;

@Getter
public class BaseResource {

    @Id
    @JsonSetter(nulls = Nulls.SKIP)
    private String id;

    @CreatedDate
    @JsonSetter(nulls = Nulls.SKIP)
    private Date createdAt;

    @LastModifiedDate
    @JsonSetter(nulls = Nulls.SKIP)
    private Date updatedAt;

    @Version
    @JsonIgnore
    @JsonSetter(nulls = Nulls.SKIP)
    private Long dbVersion;

    public void setId(String id) {
        this.id = id;
    }


}
