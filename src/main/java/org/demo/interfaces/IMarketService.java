package org.demo.interfaces;

import org.demo.dto.PotatoBag;

import java.util.List;

public interface IMarketService {

    PotatoBag addBagToMarket(PotatoBag bagDTO) throws Exception;
    List<PotatoBag> getBagsOnSale(Integer count);
}
