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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Log4j2
public class RepositoryServiceTest {

    @Autowired
    private RepositoryService repositoryService;

    @Before
    public void setup() {
        PotatoBag bag = PotatoBag.builder().potatoCount(25).price(10D).supplier("De Coster").packDate(new Date()).build();
        repositoryService.addToRepo(bag);
    }

    @Test
    public void addToRepoTest() {
        PotatoBag bag = PotatoBag.builder().potatoCount(25).price(10D).supplier("De Coster").packDate(new Date()).build();
        bag = repositoryService.addToRepo(bag);
        assertNotNull(bag.getUuid());

        List<PotatoBag> bags = repositoryService.getFromRepo();
        assertEquals(2, bags.size());
        assertNotNull(bags.get(0).getUuid());
        assertNotNull(bags.get(1).getUuid());
        assertEquals(bag, bags.get(1));
    }

    @Test
    public void getFromRepoTest() {
        List<PotatoBag> bags = repositoryService.getFromRepo();
        assertEquals(1, bags.size());

        repositoryService.reset();
        bags = repositoryService.getFromRepo();
        assertEquals(0, bags.size());
    }


    @Test
    public void resetTest() {
        repositoryService.reset();

        List<PotatoBag> bags = repositoryService.getFromRepo();
        assertEquals(0, bags.size());
    }
}
