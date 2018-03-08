package org.demo.interfaces;

import org.demo.dto.PotatoBag;

import java.util.List;

public interface IRespositoryService {

    PotatoBag addToRepo(PotatoBag bag);
    List<PotatoBag> getFromRepo();
}
