package com.senich.creditcheck.controller;

import com.senich.creditcheck.model.dto.Order;
import com.senich.creditcheck.service.TradingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/market")
@RequiredArgsConstructor
@Slf4j
public class TradingController {

    private final TradingService tradingService;

    @PostMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void order(Order order) {
        tradingService.bookTrade(order);
    }
}
