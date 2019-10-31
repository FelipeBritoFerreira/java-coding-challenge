package com.gfg.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductDTO {

    @JsonIgnore
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String brand;

    @Min(value = 0)
    private Integer stock;

    @JsonIgnore
    private String sellerId;

    @NotNull
    private String sellerUuid;

    @NotNull
    private String uuid;
}
