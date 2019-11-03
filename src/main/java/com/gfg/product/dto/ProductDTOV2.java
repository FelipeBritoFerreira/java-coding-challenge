package com.gfg.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Relation(collectionRelation = "products")
public class ProductDTOV2 {

    @JsonIgnore
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String brand;

    @Min(value = 0)
    private Integer stock;

    @NotNull
    private SellerDTOV2 seller;

    @NotNull
    private String uuid;

}
