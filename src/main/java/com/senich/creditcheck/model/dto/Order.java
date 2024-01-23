package com.senich.creditcheck.model.dto;

import com.senich.creditcheck.model.enums.TradeType;
import lombok.Data;

@Data
public class Order {

    private long traderId;
    private String symbol;
    private long quantity;
    private TradeType tradeType;
    private long portfolioId;

}
