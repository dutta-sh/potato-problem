package org.demo.service;

import lombok.extern.log4j.Log4j2;
import org.demo.dto.PotatoBag;
import org.demo.interfaces.IMarketService;
import org.demo.interfaces.IRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class MarketService implements IMarketService {

    @Value("${bag.minPotatoCount}")
    private int minPotatoCount;

    @Value("${bag.maxPotatoCount}")
    private int maxPotatoCount;

    @Value("${bag.minPrice}")
    private int minPrice;

    @Value("${bag.maxPrice}")
    private int maxPrice;

    @Value("#{'${bag.suppliers}'.split(',')}")
    private List<String> suppliers;

    @Value("${rest.defaultBagCount}")
    private int defaultBagCount;

    @Autowired
    private IRepositoryService repositoryService;

    @Override
    public PotatoBag addBagToMarket(PotatoBag bag) throws Exception {
        if(bag.getPotatoCount() < minPotatoCount || bag.getPotatoCount() > maxPotatoCount)
            throw new Exception("Potato count is invalid");

        if(bag.getPrice() < minPrice || bag.getPrice() > maxPrice)
            throw new Exception("Price is invalid");

        if(!suppliers.contains(bag.getSupplier()))
            throw new Exception("Supplier is invalid");

        bag = repositoryService.addToRepo(bag);
        return bag;
    }

    //return the count of bags requested or total size, whichever is smaller
    @Override
    public List<PotatoBag> getBagsOnSale(Integer count) {
        if(count == null)
            count = defaultBagCount;

        List<PotatoBag> bagsOnSale = new ArrayList<>();
        if(repositoryService.getFromRepo().size() <= count) {
            bagsOnSale.addAll(repositoryService.getFromRepo());
        } else {
            for(int i = 0; i < count; i++) {
                bagsOnSale.add(repositoryService.getFromRepo().get(i));
            }
        }
        return bagsOnSale;
    }

    @Override
    public List<PotatoBag> getBagsOnSale() {
        return getBagsOnSale(defaultBagCount);
    }
}
