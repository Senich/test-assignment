package com.senich.creditcheck.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.senich.creditcheck.AbstractTest;
import com.senich.creditcheck.model.entity.Sector;
import com.senich.creditcheck.model.entity.Trade;
import com.senich.creditcheck.model.enums.SectorType;
import com.senich.creditcheck.model.enums.TradeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "/sql/test_data.sql")
class ReferenceDataTest extends AbstractTest {

    @Autowired
    PortfolioService portfolioService;

    @Autowired
    SectorService sectorService;


    @Test
    void testReferenceDataUpdate() {

        Sector sector = sectorService.findSector(SectorType.FINANCIALS, 2L);

        Trade buyTrade = new Trade();
        buyTrade.setTradeType(TradeType.BUY);
        buyTrade.setSymbol("JPM");
        buyTrade.setQuantity(1000);
        buyTrade.setPrice(20.0);
        buyTrade.setSector(sector);

        portfolioService.updatePortfolioReferenceData(2L, buyTrade);
        assertEquals(32000, portfolioService.getPortfolioReferenceData(2L).sectors().get(SectorType.FINANCIALS).currentAmount().get());

        Trade sellTrade = new Trade();
        sellTrade.setTradeType(TradeType.SELL);
        sellTrade.setQuantity(100);
        sellTrade.setPrice(20.0);
        sellTrade.setSector(sector);

        portfolioService.updatePortfolioReferenceData(2L, sellTrade);
        assertEquals(30000, portfolioService.getPortfolioReferenceData(2L).sectors().get(SectorType.FINANCIALS).currentAmount().get());

    }

}
