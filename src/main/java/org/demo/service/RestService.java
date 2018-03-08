package org.demo.service;

import org.demo.dto.PotatoBag;
import org.demo.interfaces.IMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class RestService {

    @Autowired
    private IMarketService marketService;

    @Value("${rest.defaultBagCount}")
    private int defaultBagCount;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity healthCheck() {
        return new ResponseEntity("potato market service is up", HttpStatus.OK);
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addBags(@RequestBody PotatoBag bag) throws Exception {
        marketService.addBagToMarket(bag);
        return new ResponseEntity("added to market", HttpStatus.OK);
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PotatoBag> getBags() {
        List<PotatoBag> bags = marketService.getBagsOnSale(defaultBagCount);
        return new ResponseEntity(bags, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{count}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PotatoBag> getBags(@PathVariable("count") Integer count) {
        List<PotatoBag> bags = marketService.getBagsOnSale(count);
        return new ResponseEntity(bags, HttpStatus.OK);
    }
}
