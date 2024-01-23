package com.senich.creditcheck.service;

import com.senich.creditcheck.model.dto.Order;
import com.senich.creditcheck.service.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradingService {

    private final InventoryService inventoryService;
    private final CreditLimitCheckService creditLimitCheckService;

    public boolean bookTrade(Order order) {
        if (creditLimitCheckService.checkExposureLimit(order)) {
            return processOrder(order);
        }
        return false;
    }

    private boolean processOrder(Order order) {
        return true;
    }
}
