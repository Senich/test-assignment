package com.senich.creditcheck.service;

import static com.senich.creditcheck.model.enums.BookingResult.FAILED;

import com.senich.creditcheck.model.dto.TradeRequest;
import com.senich.creditcheck.model.dto.Security;
import com.senich.creditcheck.model.enums.BookingResult;
import com.senich.creditcheck.model.enums.TradeType;
import com.senich.creditcheck.service.inventory.InventoryService;
import java.util.WeakHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private static final WeakHashMap<Long, Long> locks = new WeakHashMap<>();
    private final CreditCheckService creditCheckService;
    private final ExecutionService executionService;
    private final InventoryService inventoryService;

    public String bookTrade(TradeRequest tradeRequest) {

        Security security = inventoryService.getSecurity(tradeRequest.getSymbol(), tradeRequest.getSecurityType());
        tradeRequest.setPrice(security.getPrice());

        synchronized (getLockForPortfolioId(tradeRequest.getPortfolioId())) {
            if (creditCheckService.isExposureLimitExceed(tradeRequest, security)) {
                return FAILED.getStatus();
            }
            return processOrder(tradeRequest).getStatus();
        }
    }

    public BookingResult processOrder(TradeRequest tradeRequest) {
        return tradeRequest.getTradeType() == TradeType.BUY
            ? executionService.executeBuyTrade(tradeRequest)
            : executionService.executeSellTrade(tradeRequest);
    }

    private static Object getLockForPortfolioId(long portfolioId) {
        return locks.computeIfAbsent(portfolioId, key -> {
            synchronized (locks) {
                return key;
            }
        });
    }
}
