package org.demo.service;

import org.demo.dto.PotatoBag;

import java.util.List;

public interface IMarketService {

    public void validateBag(PotatoBag bag) throws Exception;
    public List<PotatoBag> getBagsOnSale();
    public List<PotatoBag> getBagsOnSale(Integer count);
    public void addBagToMarket(PotatoBag bag) throws Exception;
}
