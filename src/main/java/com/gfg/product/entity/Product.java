package com.gfg.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    @JsonIgnore
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "brand")
    private String brand;

    @Min(value = 0)
    @Column(name = "stock")
    private Integer stock;

    @ManyToOne
    @NotNull
    @JoinColumn(name="fk_seller")
    private Seller seller;

    @NotNull
    @Column(name = "uuid")
    private String uuid;
}
