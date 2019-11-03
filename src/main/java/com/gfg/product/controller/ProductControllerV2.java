package com.gfg.product.controller;

import com.gfg.product.dto.ProductDTO;
import com.gfg.product.dto.ProductDTOV2;
import com.gfg.product.dto.SellerDTOV2;
import com.gfg.product.entity.Product;
import com.gfg.product.exception.ResourceNotFoundException;
import com.gfg.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/v2/products")
@Api(value = "ProductsV2")
public class ProductControllerV2  {

    private ProductService productService;

    private static final ModelMapper mapper = new ModelMapper();

    public ProductControllerV2(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "View product information by UUID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Processed successfully", response = ProductDTOV2.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource<ProductDTOV2>> getProductBUuid(@PathVariable String uuid) {
        Product product = productService.getByUuid(uuid);
        if (product == null) {
            throw new ResourceNotFoundException("Product", "UUID", uuid);
        }
        ProductDTOV2 productDTOV2 = mapper.map(product, ProductDTOV2.class);
        createSellerSelfLink(productDTOV2);
        return new ResponseEntity<>(new Resource<>(productDTOV2), HttpStatus.OK);
    }

    @ApiOperation(value = "View all products")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Processed successfully", response = ProductDTOV2.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resources<ProductDTOV2>> getAllProducts() {
        List<Product> products = productService.getAll();

        List<ProductDTOV2> productDTOList = products.stream()
                .map(product -> {
                    ProductDTOV2 productDTOV2 = mapper.map(product, ProductDTOV2.class);
                    createSellerSelfLink(productDTOV2);
                    return productDTOV2;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(new Resources(productDTOList), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete ane product by UUID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Processed successfully", response = Object.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable String uuid) {
        Product product = productService.getByUuid(uuid);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        productService.delete(product);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Update one product by UUID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Processed successfully", response = ProductDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found (Seller or Product)")
    })
    @PutMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> update(@PathVariable String uuid, @Valid @RequestBody ProductDTO productDTO) {
        Product product = mapper.map(productDTO, Product.class);

        return new ResponseEntity<>(
                mapper.map(
                        productService.updateProductByUuid(uuid, product),
                        ProductDTO.class),
                HttpStatus.OK
        );
    }

    @ApiOperation(value = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Processed successfully", response = ProductDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found (Seller)")
    })
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@Valid @RequestBody ProductDTO productDTO) {
        Product product = mapper.map(productDTO, Product.class);
        productService.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private void createSellerSelfLink(ProductDTOV2 product) {
        SellerDTOV2 sellerDTO = new SellerDTOV2(product.getSeller().getUuid());
        sellerDTO
                .add(linkTo(methodOn(SellerControllerV2.class)
                        .getSellerById(product.getSeller().getUuid()))
                        .withSelfRel());
        product.setSeller(sellerDTO);
    }

}
