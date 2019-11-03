package com.gfg.product.service;

import com.gfg.product.entity.Product;
import com.gfg.product.entity.Seller;
import com.gfg.product.repository.SellerRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SellerService {

    private SellerRepository repository;

    private ProductService productService;

    public SellerService(SellerRepository repository, @Lazy ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    public Seller getByUuid(String sellerUuid) {
        List<Seller> sellers = this.repository.findByUuid(sellerUuid);

        return sellers.size() == 0 ? null : sellers.get(0);
    }

    public List<Seller> getTopSellers(int quantity) {
        return productService.getAll()
                .parallelStream()
                .collect(Collectors.groupingBy(Product::getSeller, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(Map.Entry::getKey)
                .limit(quantity)
                .collect(Collectors.toList());
    }
}