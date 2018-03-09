package org.demo.service;

import org.demo.dto.PotatoBag;
import org.demo.interfaces.IRepositoryService;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//simple in-memory solution using a threadsafe ArrayList
//database persistence could be implemented if needed

@Repository
public class RepositoryService implements IRepositoryService {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    private List<PotatoBag> bagsOnMarket = new ArrayList<>();

    @Override
    public PotatoBag addToRepo(PotatoBag bag) {

        writeLock.lock();
        try {
            bag.setUuid(UUID.randomUUID());
            bagsOnMarket.add(bag);
        } finally {
            writeLock.unlock();
        }

        return bag;
    }

    @Override
    public List<PotatoBag> getFromRepo() {
        readLock.lock();
        try {
            return bagsOnMarket;
        } finally {
            readLock.unlock();
        }
    }

    public void reset() {
        writeLock.lock();
        try {
            bagsOnMarket = new ArrayList<>();
        } finally {
            writeLock.unlock();
        }
    }
}
