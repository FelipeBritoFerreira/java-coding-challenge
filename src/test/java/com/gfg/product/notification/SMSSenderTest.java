package com.gfg.product.notification;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.gfg.product.entity.Product;
import com.gfg.product.entity.Seller;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class SMSSenderTest {

    @Test
    public void shouldLogSMSSenderMessage() {
        Logger logger = (Logger) LoggerFactory.getLogger(SMSSender.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        SMSSender smsSender = new SMSSender();
        Seller seller = new Seller();
        seller.setUuid(UUID.fromString("dfb51d77-b160-4718-8467-224324232f0c").toString());
        seller.setPhone("123456789");
        Product product = new Product();
        product.setName("Samba");
        product.setSeller(seller);

        smsSender.sendStockChangeWarning(product);

        List<ILoggingEvent> logsList = listAppender.list;
        Assert.assertEquals(Level.INFO, logsList.get(0).getLevel());
        Assert.assertEquals("SMS Warning sent to dfb51d77-b160-4718-8467-224324232f0c (Phone: 123456789): Samba Product stock changed.", logsList.get(0).getMessage());
        listAppender.stop();

    }

}

