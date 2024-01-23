package com.senich.creditcheck.service.inventory;

import com.senich.creditcheck.model.dto.Order;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {



    private Map<String, Long> inventory = new HashMap<>();

    public void processOrder(Order order) {

    }

}
