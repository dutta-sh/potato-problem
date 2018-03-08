package org.demo.service;

import lombok.extern.log4j.Log4j2;
import org.demo.dto.PotatoBag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Log4j2
public class MarketServiceTest {

    @Autowired
    private MarketService marketService;
    @Autowired
    private RepositoryService repositoryService;

    @Before
    public void setup() {
        repositoryService.reset();
    }

    @Test(expected = Exception.class)
    public void addBagToMarketTestInvalidCount() throws Exception {
        PotatoBag bag = PotatoBag.builder().potatoCount(150).price(10D).supplier("De Coster").packDate(new Date()).build();
        marketService.addBagToMarket(bag);
    }

    @Test(expected = Exception.class)
    public void addBagToMarketTestInvalidPrice() throws Exception {
        PotatoBag bag = PotatoBag.builder().potatoCount(25).price(75D).supplier("De Coster").packDate(new Date()).build();
        marketService.addBagToMarket(bag);
    }

    @Test(expected = Exception.class)
    public void addBagToMarketTestInvalidSupplier() throws Exception {
        PotatoBag bag = PotatoBag.builder().potatoCount(25).price(25D).supplier("Idaho Potatoes").packDate(new Date()).build();
        marketService.addBagToMarket(bag);
    }

    @Test
    public void addBagToMarketTest() throws Exception {
        PotatoBag bag = PotatoBag.builder().potatoCount(50).price(10D).supplier("De Coster").packDate(new Date()).build();

        bag = marketService.addBagToMarket(bag);
        log.info(bag);
        assertNotNull(bag.getUuid());

        List<PotatoBag> bags = repositoryService.getFromRepo();
        assertEquals(1, bags.size());
        assertEquals(bag, bags.get(0));
    }

    @Test
    public void getBagsOnSaleTest() {
        for(int i = 1; i <= 2; i++) {
            PotatoBag bag = PotatoBag.builder().uuid(UUID.randomUUID()).potatoCount(i*10).price(i*5D).supplier("Patatas Ruben").packDate(new Date()).build();
            repositoryService.addToRepo(bag);
        }

        List<PotatoBag> bags = marketService.getBagsOnSale(5);
        assertEquals(2, bags.size());

        bags = marketService.getBagsOnSale();
        assertEquals(2, bags.size());

        for(int i = 3; i <= 6; i++) {
            PotatoBag bag = PotatoBag.builder().uuid(UUID.randomUUID()).potatoCount(i*10).price(i*5D).supplier("Patatas Ruben").packDate(new Date()).build();
            repositoryService.addToRepo(bag);
        }

        bags = marketService.getBagsOnSale(5);
        assertEquals(5, bags.size());

        bags = marketService.getBagsOnSale();
        assertEquals(3, bags.size());
    }
}
