package com.gfg.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfg.product.ProductServiceApplication;
import com.gfg.product.dto.ProductDTOV2;
import com.gfg.product.entity.Product;
import com.gfg.product.service.ProductService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.LinkedHashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductServiceApplication.class)
@AutoConfigureMockMvc
public class ProductControllerV2Test {

    private static final String BASE_URL = "/api/v2/products/";

    @Autowired
    private ProductService productService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldReturn_ok_WhenGETAllProducts() throws Exception {
        MvcResult result =  mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andReturn();
        LinkedHashMap response = objectMapper.readValue(result.getResponse().getContentAsString(), LinkedHashMap.class);
        Assert.assertTrue( response.containsKey("_embedded") );
    }

    @Test
    public void shouldReturn_Product_WhenGETProductByUUID() throws Exception {
        Product product =  productService.getAll().get(0);
        String uuid = product.getUuid();
        MvcResult mvcResult = mockMvc.perform(get(BASE_URL+"/"+uuid))
                .andExpect(status().isOk())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        ProductDTOV2 productDTOV2 = objectMapper.readValue(response, ProductDTOV2.class);
        Assert.assertEquals(productDTOV2.getUuid(), product.getUuid() );
    }

    @Test
    public void shouldReturn_NotFound_When_GETProduct_WithWrongUUID() throws Exception {
        mockMvc.perform(get(BASE_URL+"/00000000-0000-0000-0000-000000000000"))
                .andExpect(status().isNotFound());
    }
}


