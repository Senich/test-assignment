package com.senich.creditcheck;

import com.senich.creditcheck.model.entity.Portfolio;
import com.senich.creditcheck.model.entity.Sector;
import com.senich.creditcheck.model.enums.SectorType;
import com.senich.creditcheck.repository.SectorRepository;
import com.senich.creditcheck.service.PortfolioService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

@ActiveProfiles("test")
class PortfolioServiceTest extends AbstractTest {

    @Autowired
    PortfolioService portfolioService;

    @Autowired
    SectorRepository sectorRepository;

    @BeforeEach
    void setUp() {
        sectorRepository.deleteAll().block();
    }

    @Test
    void testCreatePortfolio() {
        Sector it = Sector.builder()
            .exposureLimit(100_000)
            .type(SectorType.INFORMATION_TECHNOLOGY)
            .build();
        sectorRepository.save(it).block();

        Mono<Portfolio> portfolio = portfolioService.createPortfolio("New portfolio", 1_000_000, List.of(it));
        Assertions.assertNotNull(portfolio.block());
    }

}
