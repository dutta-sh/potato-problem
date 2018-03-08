package org.demo.service;

import lombok.extern.log4j.Log4j2;
import org.demo.dto.PotatoBag;
import org.demo.dto.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Log4j2
public class RestServiceTest {

    @Autowired
    private RestService restService;
    @Autowired
    private RepositoryService repositoryService;

    @Before
    public void setup() {
        repositoryService.reset();
    }

    @Test
    public void healthCheckTest() {
        ResponseEntity<Response> resp = restService.healthCheck();

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(0, resp.getBody().getBags().size());
        assertEquals(RestService.HEALTH_STATUS, resp.getBody().getStatus());

    }

    @Test
    public void addBagTest() {
        PotatoBag bag = PotatoBag.builder().potatoCount(50).price(10D).supplier("De Coster").packDate(new Date()).build();
        ResponseEntity<Response> resp = restService.addBag(bag);

        List<PotatoBag> bagsInRepo = repositoryService.getFromRepo();

        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertEquals(1, resp.getBody().getBags().size());
        assertEquals(RestService.ADD_TO_MARKET, resp.getBody().getStatus());
        assertNotNull(resp.getBody().getBags().get(0).getUuid());
        assertEquals(resp.getBody().getBags(), bagsInRepo);
    }

    @Test
    public void addInvalidBagTest() {
        PotatoBag bag = PotatoBag.builder().potatoCount(250).price(10D).supplier("De Coster").packDate(new Date()).build();
        ResponseEntity<Response> resp = restService.addBag(bag);

        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertEquals(1, resp.getBody().getBags().size());
        assertTrue(resp.getBody().getStatus().startsWith(RestService.NOT_ADD_TO_MARKET));
        assertNull(resp.getBody().getBags().get(0).getUuid());
    }

    @Test
    public void getBagsTest() {
        for(int i = 1; i <= 2; i++) {
            PotatoBag bag = PotatoBag.builder().uuid(UUID.randomUUID()).potatoCount(i*10).price(i*5D).supplier("Patatas Ruben").packDate(new Date()).build();
            repositoryService.addToRepo(bag);
        }

        ResponseEntity<Response> resp = restService.getBags();
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(RestService.FETCHED_FROM_MARKET, resp.getBody().getStatus());
        assertEquals(2, resp.getBody().getBags().size());

        for(int i = 3; i <= 6; i++) {
            PotatoBag bag = PotatoBag.builder().uuid(UUID.randomUUID()).potatoCount(i*10).price(i*5D).supplier("Patatas Ruben").packDate(new Date()).build();
            repositoryService.addToRepo(bag);
        }

        resp = restService.getBags();
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(RestService.FETCHED_FROM_MARKET, resp.getBody().getStatus());
        assertEquals(3, resp.getBody().getBags().size());

    }

    @Test
    public void getBagsWithCountTest() {
        for(int i = 1; i <= 2; i++) {
            PotatoBag bag = PotatoBag.builder().uuid(UUID.randomUUID()).potatoCount(i*10).price(i*5D).supplier("Patatas Ruben").packDate(new Date()).build();
            repositoryService.addToRepo(bag);
        }

        ResponseEntity<Response> resp = restService.getBags(5);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(RestService.FETCHED_FROM_MARKET, resp.getBody().getStatus());
        assertEquals(2, resp.getBody().getBags().size());


        for(int i = 3; i <= 6; i++) {
            PotatoBag bag = PotatoBag.builder().uuid(UUID.randomUUID()).potatoCount(i*10).price(i*5D).supplier("Patatas Ruben").packDate(new Date()).build();
            repositoryService.addToRepo(bag);
        }

        resp = restService.getBags(5);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(RestService.FETCHED_FROM_MARKET, resp.getBody().getStatus());
        assertEquals(5, resp.getBody().getBags().size());
    }
}
