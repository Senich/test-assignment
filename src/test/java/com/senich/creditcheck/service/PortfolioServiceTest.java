package com.senich.creditcheck.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.senich.creditcheck.AbstractTest;
import com.senich.creditcheck.model.entity.Portfolio;
import com.senich.creditcheck.model.entity.Sector;
import com.senich.creditcheck.model.enums.SectorType;
import com.senich.creditcheck.repository.SectorRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PortfolioServiceTest extends AbstractTest {

    @Autowired
    PortfolioService portfolioService;

    @Autowired
    SectorRepository sectorRepository;

    @BeforeEach
    void setUp() {
        clearDb();
    }

    @Test
    void testCreatePortfolio() {
        Sector it = Sector.builder()
            .exposureLimit(100_000)
            .type(SectorType.INFORMATION_TECHNOLOGY)
            .build();

        Portfolio portfolio = portfolioService.createPortfolio("New portfolio", 1_000_000, List.of(it));
        assertNotNull(portfolio);
        Sector sector = portfolio.getSecuritySectors().get(0);
        assertEquals(SectorType.INFORMATION_TECHNOLOGY, sector.getType());
        assertEquals(100_000, sector.getExposureLimit());
        portfolioService.deletePortfolio(portfolio.getId());
    }

}
