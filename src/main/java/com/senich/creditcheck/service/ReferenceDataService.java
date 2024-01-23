package com.senich.creditcheck.service;

import static com.senich.creditcheck.utils.AmountCalculator.calcAmount;

import com.google.common.util.concurrent.AtomicDouble;
import com.senich.creditcheck.model.dto.PortfolioData;
import com.senich.creditcheck.model.dto.SectorData;
import com.senich.creditcheck.model.entity.Portfolio;
import com.senich.creditcheck.model.entity.Sector;
import com.senich.creditcheck.model.enums.SectorType;
import com.senich.creditcheck.model.enums.TradeType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class ReferenceDataService {

    private final Map<Long, PortfolioData> portfolios = new ConcurrentHashMap<>();

    public void updatePortfolioReferenceData(PortfolioData portfolioData, int qty, double price,
        TradeType tradeType, SectorType sectorType) {
        SectorData sectorData = portfolioData.sectors().get(sectorType);
        sectorData.currentAmount().addAndGet(
            tradeType == TradeType.BUY ? calcAmount(qty, price) : -calcAmount(qty, price)
        );
    }

    public PortfolioData getPortfolioReferenceData(long portfolioId) {
        return portfolios.get(portfolioId);
    }

    public PortfolioData initPortfolioReferenceData(Portfolio portfolio) {
        PortfolioData portfolioData = convertToReferenceData(portfolio);
        portfolios.put(portfolio.getId(), portfolioData);
        return portfolioData;
    }

    public PortfolioData convertToReferenceData(Portfolio portfolio) {
        Map<SectorType, SectorData> data = new ConcurrentHashMap<>();
        portfolio.getSecuritySectors()
            .forEach(sector -> data.put(sector.getType(),
                new SectorData(sector.getExposureLimit(),
                    new AtomicDouble(getSectorTotalAmount(sector, portfolio)))));
        return new PortfolioData(data);
    }

    private double getSectorTotalAmount(Sector sector, Portfolio portfolio) {
        return portfolio.getTrades().stream()
            .filter(trade -> trade.getSector().getType() == sector.getType())
            .mapToDouble(trade -> calcAmount(trade.getQuantity(), trade.getPrice()))
            .sum();
    }
}
