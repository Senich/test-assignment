package com.senich.creditcheck.service;

import com.senich.creditcheck.model.dto.Order;
import org.springframework.stereotype.Service;

@Service
public class CreditLimitCheckService {

    public boolean checkExposureLimit(Order order) {
        return true;
    }
}
