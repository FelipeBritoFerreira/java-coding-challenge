package com.gfg.product.service;

import com.gfg.product.entity.Product;
import com.gfg.product.entity.Seller;
import com.gfg.product.exception.ResourceNotFoundException;
import com.gfg.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository repository;

    private SellerService sellerService;

    private NotificationService notificationService;

    public ProductService(
            ProductRepository repository,
            SellerService sellerService,
            NotificationService notificationService
    ) {
        this.repository = repository;
        this.sellerService = sellerService;
        this.notificationService = notificationService;
    }

    public Product getByUuid(String productUuid) {
        List<Product> products = this.repository.findByUuid(productUuid);

        return products.size() == 0 ? null : products.get(0);
    }

    public List<Product> getAll() {
        return this.repository.findAll();
    }

    public void delete(Product product) {
        this.repository.delete(product);
    }

    public Product updateProductByUuid(String uuid, Product product) {

        Product originalProduct = getByUuid(uuid);
        if (originalProduct == null) {
            throw new ResourceNotFoundException("Product", "UUID", uuid);
        }

        product.setId(originalProduct.getId());
        mapSellerToProduct(product, product.getSeller().getUuid());

        //if stock changes, send notification by email and/or sms
        if (Math.abs(product.getStock() - originalProduct.getStock()) > 0) {
            notificationService.notificateStockChange(product);
        }

        return repository.save(product);
    }

    public Product save(Product product) {
        mapSellerToProduct(product, product.getSeller().getUuid());
        return repository.save(product);
    }

    private void mapSellerToProduct(Product product, String sellerUuid) {
        Seller seller = sellerService.getByUuid(sellerUuid);
        if (seller == null) {
            throw new ResourceNotFoundException("Seller", "UUID", sellerUuid);
        }
        product.setSeller(seller);
    }
}
