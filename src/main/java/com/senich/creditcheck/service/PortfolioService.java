package com.senich.creditcheck.service;

import static com.senich.creditcheck.utils.AmountCalculator.calcAmount;

import com.senich.creditcheck.exceptions.ApplicationException;
import com.senich.creditcheck.model.dto.PortfolioData;
import com.senich.creditcheck.model.dto.SectorData;
import com.senich.creditcheck.model.entity.Portfolio;
import com.senich.creditcheck.model.entity.Sector;
import com.senich.creditcheck.model.entity.Trade;
import com.senich.creditcheck.model.enums.SectorType;
import com.senich.creditcheck.model.enums.TradeType;
import com.senich.creditcheck.repository.PortfolioRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final ReferenceDataService referenceDataService;

    @Transactional
    public void updatePortfolio(Trade trade, long portfolioId, TradeType tradeType) {
        if (tradeType == TradeType.BUY) {
            updatePortfolioOnBuy(trade, portfolioId);
        } else {
            updatePortfolioOnSell(trade, portfolioId);
        }
    }

    public Portfolio createPortfolio(String name, double maxFundValue, List<Sector> sectors) {
        Portfolio portfolio = new Portfolio();
        portfolio.setName(name);
        portfolio.setSecuritySectors(sectors);
        portfolio.setMaxFundValue(maxFundValue);
        sectors.forEach(sector -> sector.setPortfolio(portfolio));
        return portfolioRepository.save(portfolio);
    }

    private void updatePortfolioOnBuy(Trade trade, long portfolioId) {
        Portfolio portfolio = getPortfolio(portfolioId);
        portfolio.getTrades().add(trade);
        updatePortfolioReferenceData(portfolioId, trade);
        trade.setPortfolio(portfolio);
        recalculateTotalAmount(portfolio);
        portfolioRepository.save(portfolio);
    }

    private void updatePortfolioOnSell(Trade trade, long portfolioId) {
        Portfolio portfolio = getPortfolio(portfolioId);
        portfolio.getTrades().remove(trade);
        updatePortfolioReferenceData(portfolioId, trade);
        recalculateTotalAmount(portfolio);
        portfolioRepository.save(portfolio);
    }

    public boolean isSectorExposureExceeded(long portfolioId, double orderAmount, SectorType sectorType) {
        PortfolioData data = getPortfolioReferenceData(portfolioId);
        SectorData sectorData = data.sectors().get(sectorType);
        return Double.compare(sectorData.currentAmount().get() + orderAmount, sectorData.exposureLimit()) > 0;
    }

    public void updatePortfolioReferenceData(long portfolioId, Trade trade) {
        PortfolioData portfolioData = getPortfolioReferenceData(portfolioId);
        referenceDataService.updatePortfolioReferenceData(portfolioData, trade.getQuantity(), trade.getPrice(),
            trade.getTradeType(), trade.getSector().getType());
    }

    public PortfolioData getPortfolioReferenceData(long portfolioId) {
        PortfolioData portfolioData = referenceDataService.getPortfolioReferenceData(portfolioId);
        if (portfolioData == null) {
            Portfolio portfolio = getPortfolio(portfolioId);
            return referenceDataService.initPortfolioReferenceData(portfolio);
        }
        return portfolioData;
    }

    public Portfolio getPortfolio(long portfolioId) {
        return portfolioRepository.findById(portfolioId)
            .orElseThrow(() -> new ApplicationException("Portfolio is not found"));
    }

    public void deletePortfolio(long id) {
        portfolioRepository.deleteById(id);
    }

    private void recalculateTotalAmount(Portfolio portfolio) {
        double totalAmount = portfolio.getTrades().stream()
            .mapToDouble(t -> calcAmount(t.getQuantity(), t.getPrice()))
            .sum();
        portfolio.setMaxFundValue(totalAmount);
    }

}
