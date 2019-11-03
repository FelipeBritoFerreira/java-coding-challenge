package com.gfg.product.service;

import com.gfg.product.ProductServiceApplication;
import com.gfg.product.entity.Seller;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductServiceApplication.class)
public class SellerServiceIntegrationTest {

    @Autowired
    private SellerService sellerService;

    @Test
    public void shouldGetTop10Sellers() {
        List<Seller> topSellers = sellerService.getTopSellers(10);
        Assert.assertThat( topSellers.size(), Matchers.is(10));
    }

    @Test
    public void shouldGet_AnyTopSellers_ByQuantity() {
        List<String> actual = sellerService.getTopSellers(3)
                .stream()
                .map(Seller::getName)
                .collect(Collectors.toList());
        List<String> expected = Arrays.asList(
                "Owen Ringgold",
                "Christene Maggio",
                "Shani Marinello");
        Assert.assertEquals(expected, actual);
    }
}
