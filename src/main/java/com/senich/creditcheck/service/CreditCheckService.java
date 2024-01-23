package com.senich.creditcheck.service;

import static com.senich.creditcheck.utils.AmountCalculator.calcAmount;

import com.senich.creditcheck.model.dto.TradeRequest;
import com.senich.creditcheck.model.dto.Security;
import com.senich.creditcheck.model.enums.TradeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditCheckService {

    private final PortfolioService portfolioService;

    public boolean isExposureLimitExceed(TradeRequest tradeRequest, Security security) {
        if (tradeRequest.getTradeType() == TradeType.SELL) {
            return false;
        }
        return portfolioService.isSectorExposureExceeded(tradeRequest.getPortfolioId(),
            calcAmount(tradeRequest.getQuantity(), security.getPrice()), security.getSectorType());
    }

}
