package com.senich.creditcheck.service;

import com.senich.creditcheck.model.dto.TradeRequest;
import com.senich.creditcheck.model.entity.Trade;
import com.senich.creditcheck.model.enums.BookingResult;
import com.senich.creditcheck.model.enums.TradeType;
import com.senich.creditcheck.service.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExecutionService {

    private final TradeService tradeService;
    private final PortfolioService portfolioService;
    private final InventoryService inventoryService;

    public BookingResult executeBuyTrade(TradeRequest tradeRequest) {
        Trade trade = tradeService.createTrade(tradeRequest);
        updateInventory(trade, tradeRequest.getTradeType());
        portfolioService.updatePortfolio(trade, tradeRequest.getPortfolioId(), tradeRequest.getTradeType());
        return BookingResult.SUCCESS;
    }

    public BookingResult executeSellTrade(TradeRequest tradeRequest) {
        Trade trade = tradeService.getTrade(tradeRequest.getTradeId());
        updateInventory(trade, tradeRequest.getTradeType());
        portfolioService.updatePortfolio(trade, tradeRequest.getPortfolioId(), tradeRequest.getTradeType());
        return BookingResult.SUCCESS;
    }

    private void updateInventory(Trade trade, TradeType type) {
        inventoryService.updateInventorySecurityQty(trade.getSymbol(), trade.getSecurityType(),
            type == TradeType.BUY ? trade.getQuantity() : -trade.getQuantity());
    }
}
