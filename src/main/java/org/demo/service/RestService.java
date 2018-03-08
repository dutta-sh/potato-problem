package org.demo.service;

import org.demo.dto.PotatoBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class RestService {

    @Autowired
    private IMarketService marketService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity healthCheck() {
        return new ResponseEntity("service is up", HttpStatus.OK);
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity postTransaction(@RequestBody PotatoBag bag) throws Exception {
        marketService.addBagToMarket(bag);
        return new ResponseEntity("adde to market", HttpStatus.OK);
    }
}
