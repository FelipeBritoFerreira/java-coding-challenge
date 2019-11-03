package com.gfg.product.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class SellerDTOV2 extends ResourceSupport {

    @NotNull
    private String uuid;

    @JsonCreator
    public SellerDTOV2(@JsonProperty("uuid") String uuid) {
        this.uuid = uuid;
    }

}
