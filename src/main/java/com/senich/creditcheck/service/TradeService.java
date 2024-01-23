package com.senich.creditcheck.service;

import com.senich.creditcheck.exceptions.ApplicationException;
import com.senich.creditcheck.model.dto.TradeRequest;
import com.senich.creditcheck.model.dto.Security;
import com.senich.creditcheck.model.entity.Trade;
import com.senich.creditcheck.model.enums.TradeType;
import com.senich.creditcheck.repository.TradeRepository;
import com.senich.creditcheck.repository.TraderRepository;
import com.senich.creditcheck.service.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeService {

    private final InventoryService inventoryService;
    private final SectorService sectorService;
    private final TradeRepository tradeRepository;
    private final TraderRepository traderRepository;


    public Trade createTrade(TradeRequest tradeRequest) {
        Security security = inventoryService.getSecurity(tradeRequest.getSymbol(), tradeRequest.getSecurityType());
        Trade trade = new Trade();
        trade.setSecurityType(security.getSecurityType());
        trade.setSymbol(security.getSymbol());
        trade.setPrice(tradeRequest.getPrice());
        trade.setQuantity(tradeRequest.getQuantity());
        trade.setSector(sectorService.findSector(security.getSectorType(), tradeRequest.getPortfolioId()));
        trade.setTradeType(TradeType.BUY);
        trade.setTrader(traderRepository.findById(tradeRequest.getTraderId()).orElseThrow());
        return trade;
    }

    public Trade getTrade(long tradeId) {
        return tradeRepository.findById(tradeId).orElseThrow(
            () -> new ApplicationException(String.format("Trade with id %d is not found", tradeId)));
    }

}
