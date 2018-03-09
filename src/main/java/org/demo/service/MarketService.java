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
    private Integer minPotatoCount;

    @Value("${bag.maxPotatoCount}")
    private Integer maxPotatoCount;

    @Value("${bag.minPrice}")
    private Double minPrice;

    @Value("${bag.maxPrice}")
    private Double maxPrice;

    @Value("#{'${bag.suppliers}'.split(',')}")
    private List<String> suppliers;

    @Value("${rest.defaultBagCount}")
    private Integer defaultBagCount;

    @Autowired
    private IRepositoryService repositoryService;

    //add to repository after validation
    //if validation fails, throw exception
    @Override
    public PotatoBag addBagToMarket(PotatoBag bag) throws Exception {

        List<String> errList = new ArrayList<>();
        if(bag.getPotatoCount() < minPotatoCount || bag.getPotatoCount() > maxPotatoCount)
            errList.add("Potato count is invalid");

        if(bag.getPrice() < minPrice || bag.getPrice() > maxPrice)
            errList.add("Price is invalid");

        if(!suppliers.contains(bag.getSupplier()))
            errList.add("Supplier is invalid");

        if(!errList.isEmpty())
            throw new Exception(errList.toString());

        bag = repositoryService.addToRepo(bag);
        return bag;
    }

    //return the count of bags requested or total size, whichever is smaller
    @Override
    public List<PotatoBag> getBagsOnSale(Integer count) {
        if(count == null)
            count = defaultBagCount;

        List<PotatoBag> marketplace = repositoryService.getFromRepo();
        List<PotatoBag> bagsOnSale = new ArrayList<>();
        if(marketplace.size() <= count) {
            log.warn("Returning all bags, since marketplace has " + marketplace.size() + " items, which is less than requested " + count);
            bagsOnSale.addAll(marketplace);
        } else {
            for(int i = 0; i < count; i++) {
                bagsOnSale.add(marketplace.get(i));
            }
        }
        return bagsOnSale;
    }

    //return default count of bags (defined in props file) on sale
    @Override
    public List<PotatoBag> getBagsOnSale() {
        return getBagsOnSale(defaultBagCount);
    }
}
