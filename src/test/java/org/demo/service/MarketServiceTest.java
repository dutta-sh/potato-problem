package org.demo.service;

import lombok.extern.log4j.Log4j2;
import org.demo.dto.PotatoBag;
import org.demo.dto.Supplier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Log4j2
public class MarketServiceTest {

    @Autowired
    private MarketService marketService;

    private PotatoBag bag;

    @Before
    public void setup() {
        bag = PotatoBag.builder().
                potatoCount(50).
                supplier(Supplier.De_Coster).
                packDate(new Date()).
                price(10D).
                build();
    }

    @Test
    public void addBagToMarketTest() throws Exception {
        marketService.addBagToMarket(bag);
        List<PotatoBag> b = marketService.getBagsOnMarket();
        assertEquals(1, b.size());
        assertNotNull(b.get(0).getUuid());
        log.info(b.get(0));
    }

}
