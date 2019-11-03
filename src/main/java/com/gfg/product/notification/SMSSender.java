package com.gfg.product.notification;

import com.gfg.product.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SMSSender {

    public void sendStockChangeWarning(Product product) {
        log.info("SMS Warning sent to " + product.getSeller().getUuid()
                + " (Phone: " + product.getSeller().getPhone() +"): "
                + product.getName() + " Product stock changed.");
    }
}
