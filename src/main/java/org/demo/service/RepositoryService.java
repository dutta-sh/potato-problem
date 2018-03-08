package org.demo.service;

import lombok.extern.log4j.Log4j2;
import org.demo.dto.PotatoBag;
import org.demo.interfaces.IRespositoryService;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Log4j2
public class RepositoryService implements IRespositoryService {

    private List<PotatoBag> bagsOnMarket = new ArrayList<>();

    @Override
    public PotatoBag addToRepo(PotatoBag bag) {

//        PotatoBag bag = PotatoBag.builder().
//                uuid(UUID.randomUUID()).
//                potatoCount(bagDTO.getPotatoCount()).
//                price(bagDTO.getPrice()).
//                supplier(bagDTO.getSupplier()).
//                packDate(bagDTO.getPackDate()).
//                build();

        bag.setUuid(UUID.randomUUID());

        bagsOnMarket.add(bag);

        return bag;
    }

    @Override
    public List<PotatoBag> getFromRepo() {
        return bagsOnMarket;
    }

    public void reset() {
        bagsOnMarket = new ArrayList<>();
    }
}
