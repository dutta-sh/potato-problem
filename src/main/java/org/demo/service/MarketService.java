package org.demo.service;

import lombok.extern.log4j.Log4j2;
import org.demo.dto.PotatoBag;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class MarketService implements IMarketService {

    private List<PotatoBag> bagsOnMarket = new ArrayList<>();

    @Override
    public void validateBag(PotatoBag bag) throws Exception {
        if(bag.getPotatoCount() < 1 || bag.getPotatoCount() > 100)
            throw new Exception("Potato count is invalid");

        if(bag.getPrice() < 1 || bag.getPrice() > 50)
            throw new Exception("Price is invalid");
    }

    @Override
    public List<PotatoBag> getBagsOnSale() {
        return null;
    }

    @Override
    public List<PotatoBag> getBagsOnSale(Integer count) {
        return null;
    }

    @Override
    public void addBagToMarket(PotatoBag bag) throws Exception {
        validateBag(bag);
        bag.setUuid(UUID.randomUUID());
        bagsOnMarket.add(bag);
    }

    public List<PotatoBag> getBagsOnMarket() {
        return bagsOnMarket;
    }
}
