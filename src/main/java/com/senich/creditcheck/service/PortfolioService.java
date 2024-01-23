package com.senich.creditcheck.service;

import com.senich.creditcheck.model.entity.Portfolio;
import com.senich.creditcheck.model.entity.Sector;
import com.senich.creditcheck.repository.PortfolioRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public Mono<Portfolio> createPortfolio(String name, double maxFundValue, List<Sector> sectors) {
        Portfolio portfolio = new Portfolio();
        portfolio.setName(name);
        portfolio.setSecuritySectors(sectors);
        portfolio.setMaxFundValue(maxFundValue);
        return portfolioRepository.save(portfolio);
    }
}
