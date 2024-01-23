package com.senich.creditcheck.model.dto;

import com.senich.creditcheck.model.enums.SecurityType;
import com.senich.creditcheck.model.enums.TradeType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TradeRequest {

    private long traderId;
    private long portfolioId;
    private String symbol;
    private int quantity;
    private double price;
    private SecurityType securityType;
    private TradeType tradeType;
    private Long tradeId;

}
