package com.gfg.product.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfg.product.ProductServiceApplication;
import com.gfg.product.entity.Seller;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ProductServiceApplication.class})
public class SellerControllerV2Test {

    private static final String BASE_URL = "/api/v2/sellers/";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldReturn_Seller_When_GETSellerByUUID() throws Exception {
        mockMvc.perform(get(BASE_URL+"/2e3da233-fcb8-11e9-850d-0242ac120002"))
                .andExpect(status().isOk())
                .andExpect(content().json(mockGETSellerByUUIDResponse()));
    }

    @Test
    public void shouldReturn_GETTop10Sellers() throws Exception {
        MvcResult mvcResult =  mockMvc.perform(get(BASE_URL+"/top10"))
                .andExpect(status().isOk())
                .andReturn();
        List<Seller> sellerResponse = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Seller>>(){});

        Assert.assertThat(sellerResponse.size(), Matchers.is(10));
    }

    @Test
    public void shouldReturn_NotFound_When_GETSeller_WithWrongUUID() throws Exception {
        mockMvc.perform(get(BASE_URL+"/00000000-0000-0000-0000-000000000000"))
                .andExpect(status().isNotFound());
    }

    private String mockGETSellerByUUIDResponse() {
        return "{\n" +
                "  \"name\": \"Christene Maggio\",\n" +
                "  \"email\": \"christene.maggio@seller.com\",\n" +
                "  \"phone\": \"202-555-0143\",\n" +
                "  \"uuid\": \"2e3da233-fcb8-11e9-850d-0242ac120002\"\n" +
                "}";

    }
}
