package com.senich.creditcheck.service.inventory;

import static java.lang.String.format;

import com.senich.creditcheck.exceptions.ApplicationException;
import com.senich.creditcheck.model.dto.Security;
import com.senich.creditcheck.model.enums.SecurityType;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    ReentrantLock lock = new ReentrantLock();

    private final InventoryLoader inventoryLoader;

    private Map<String, ConcurrentMap<SecurityType, Security>> inventory = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        inventory = inventoryLoader.loadData();
    }

    public Security getSecurity(String symbol, SecurityType type) {
        return Optional.ofNullable(inventory.get(symbol))
            .map(secMap -> secMap.get(type))
            .orElseThrow(() -> new ApplicationException(format("Security %s for %s side not found", symbol, type.name())));
    }

    public void updateInventorySecurityQty(String symbol, SecurityType type, int qty) {
        Security security = getSecurity(symbol, type);
        AtomicInteger availableQty = security.getAvailableQty();
        lock.lock();
        try {
            if (availableQty.get() > qty) {
                availableQty.addAndGet(-qty);
            } else {
                throw new ApplicationException(format("Insufficient security qty for %s. Requested %d, available %d",
                    symbol, qty, availableQty.get()));
            }
        } finally {
            lock.unlock();
        }
    }

}
