package com.gfg.product.service;

import com.gfg.product.entity.Product;
import com.gfg.product.notification.EmailSender;
import com.gfg.product.notification.SMSSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private EmailSender emailSender;

    private SMSSender smsSender;

    @Value("${gfg.properties.notification.seller.email}")
    private boolean emailSenderToggle;

    @Value("${gfg.properties.notification.seller.sms}")
    private boolean smsSenderToggle;

    public NotificationService(EmailSender emailSender, SMSSender smsSender) {
        this.emailSender = emailSender;
        this.smsSender = smsSender;
    }
    
    public void notificateStockChange(Product product) {
        if (emailSenderToggle) emailSender.sendPriceChangeWarning(product);
        if (smsSenderToggle) smsSender.sendStockChangeWarning(product);
    }
}
