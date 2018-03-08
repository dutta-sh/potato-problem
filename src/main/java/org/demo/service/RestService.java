package org.demo.service;

import org.demo.dto.PotatoBag;
import org.demo.dto.Response;
import org.demo.interfaces.IMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping
public class RestService {

    public static final String HEALTH_STATUS = "potato market service is up";
    public static final String ADD_TO_MARKET = "added to marketplace";
    public static final String NOT_ADD_TO_MARKET = "NOT added to marketplace";
    public static final String FETCHED_FROM_MARKET = "bags fetched from marketplace";

    @Autowired
    private IMarketService marketService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> healthCheck() {
        Response response = Response.builder().
                bags(Collections.emptyList()).
                status(HEALTH_STATUS).
                build();

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Response> addBag(@RequestBody PotatoBag bag) {
        String status = ADD_TO_MARKET;

        try {
            bag = marketService.addBagToMarket(bag);
        } catch (Exception e) {
            status = NOT_ADD_TO_MARKET + " - " + e.getMessage();
        }

        Response response = Response.builder().
                bags(Arrays.asList(bag)).
                status(status).
                build();

        return new ResponseEntity(response, status == ADD_TO_MARKET ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Response> getBags() {
        List<PotatoBag> bags = marketService.getBagsOnSale();

        Response response = Response.builder().
                bags(bags).
                status(FETCHED_FROM_MARKET).
                build();

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{count}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Response> getBags(@PathVariable("count") Integer count) {
        List<PotatoBag> bags = marketService.getBagsOnSale(count);

        Response response = Response.builder().
                bags(bags).
                status(FETCHED_FROM_MARKET).
                build();

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
